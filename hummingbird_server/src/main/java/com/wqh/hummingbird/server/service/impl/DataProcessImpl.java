package com.wqh.hummingbird.server.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.primitives.Shorts;
import com.wqh.hummingbird.api.common.device.DataPointAbilityEnum;
import com.wqh.hummingbird.api.common.device.DataPointTypeEnum;
import com.wqh.hummingbird.api.protocol.DataPoint;
import com.wqh.hummingbird.api.protocol.Protocol;
import com.wqh.hummingbird.server.common.Response;
import com.wqh.hummingbird.server.common.bo.DeviceCmdBO;
import com.wqh.hummingbird.server.generator.tables.pojos.Device;
import com.wqh.hummingbird.server.generator.tables.pojos.DeviceType;
import com.wqh.hummingbird.server.generator.tables.records.DataPointRecord;
import com.wqh.hummingbird.server.generator.tables.records.DeviceRecord;
import com.wqh.hummingbird.server.generator.tables.records.DeviceTypeRecord;
import com.wqh.hummingbird.server.netty.IDataProcess;
import com.wqh.hummingbird.server.service.IWebsocketService;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.types.UShort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.wqh.hummingbird.server.generator.tables.DataPoint.DATA_POINT;
import static com.wqh.hummingbird.server.generator.tables.Device.DEVICE;
import static com.wqh.hummingbird.server.generator.tables.DeviceType.DEVICE_TYPE;

@Slf4j
@Service
public class DataProcessImpl implements IDataProcess {

    @Autowired
    private DSLContext dslContext;

    @Autowired
    private IWebsocketService websocketService;

    private Map<String, Channel> session = new ConcurrentHashMap<>();
    private Map<String, CompletableFuture<Protocol>> futureMap = new ConcurrentHashMap<>();

    @Override
    public void upload(Protocol data) {
        System.out.println(data);
        DeviceRecord device = dslContext.selectFrom(DEVICE)
                .where(DEVICE.CODE.eq(data.getCode()))
                .fetchAny();
        if (device == null) {
            log.info("Can't find device:{}", data.getCode());
            return;
        }

        DeviceTypeRecord deviceType = dslContext.selectFrom(DEVICE_TYPE)
                .where(DEVICE_TYPE.ID.eq(device.getTypeId()))
                .fetchAny();
        if (deviceType == null) {
            log.info("Can't find {} deviceType", data.getCode());
            return;
        }

        List<DataPointRecord> dataPointRecords = dslContext.selectFrom(DATA_POINT)
                .where(DATA_POINT.DEVICE_TYPE_ID.eq(deviceType.getId()))
                .fetchInto(DataPointRecord.class);
        if (dataPointRecords == null || dataPointRecords.isEmpty()) {
            log.info("Can't find {} dataPoint", data.getCode());
            return;
        }

        Map<Integer, DataPointRecord> dpMap = dataPointRecords.stream().collect(
                Collectors.toMap(x -> x.getDataPoint().intValue(), x -> x)
        );

        JSONObject json = new JSONObject();
        json.put("device", device.into(Device.class));
        json.put("deviceType", deviceType.into(DeviceType.class));
        List<Object> body = new ArrayList<>();
        data.getDataPoints().forEach(dp -> {
            DataPointRecord r = dpMap.get(dp.getPoint());
            if (r != null) {
                Map<String, Object> m = new HashMap<>(4);
                m.put("dpType", DataPointTypeEnum.getName(r.getDataType().intValue()));
                m.put("dpName", r.getName());
                m.put("point",dp.getPoint());
                if(dp.getAbility() == DataPointAbilityEnum.X){
                    m.put("cmdResp", ByteBufUtil.hexDump(dp.getBody()));
                }else{
                    m.put("dpValue", DataPointTypeEnum.getValue(r.getDataType().intValue(), dp.getBody()));
                }
                body.add(m);
            }
        });
        json.put("body", body);

        websocketService.publish(json);

        CompletableFuture<Protocol> future = futureMap.get(data.getCode());
        if (future != null) {
            future.complete(data);
        }

    }

    @Override
    public void addSession(String code, Channel channel) {
        if (session.putIfAbsent(code, channel) == null) {
            log.info("online:{}", code);
            dslContext.update(DEVICE)
                    .set(DEVICE.ONLINE, true)
                    .where(DEVICE.CODE.eq(code))
                    .execute();
        }
    }

    @Override
    public void removeSession(Channel channel) {
        Iterator<String> it = session.keySet().iterator();
        while (it.hasNext()) {
            String code = it.next();
            if (session.get(code).id().asLongText().equals(channel.id().asLongText())) {
                it.remove();
                dslContext.update(DEVICE)
                        .set(DEVICE.ONLINE, false)
                        .where(DEVICE.CODE.eq(code))
                        .execute();
                log.info("offline:{}", code);
                break;
            }
        }
    }


    @Override
    public Response<String> command(DeviceCmdBO data) {
        try {
            Channel channel = session.get(data.getCode());
            if (channel == null) {
                return Response.error("设备不在线");
            }
            Map<Integer, String> map = paramMap(data.getCmd());
            List<UShort> dpList = map.keySet().stream().map(UShort::valueOf).collect(Collectors.toList());
            if (dpList.isEmpty()) {
                return Response.error("参数错误");
            }

            int count = dslContext.selectCount().from(DATA_POINT)
                    .where(DATA_POINT.DATA_POINT_.in(dpList))
                    .fetchOne(0, int.class);
            if (count != dpList.size()) {
                return Response.error("DP点错误");
            }

            Protocol cmd = new Protocol();
            cmd.setCode(data.getCode());
            List<DataPoint> dps = new ArrayList<>();
            map.forEach((k, v) -> {
                DataPoint dp = new DataPoint();
                dp.setPoint(k);
                if (v.equalsIgnoreCase(DataPointAbilityEnum.R.name())) {
                    dp.setAbility(DataPointAbilityEnum.R);
                } else {
                    dp.setAbility(DataPointAbilityEnum.W);
                    dp.setBody(getDpBytes(data.getCode(), k, v));
                }
                dps.add(dp);
            });
            cmd.setDataPoints(dps);

            CompletableFuture<Protocol> future = new CompletableFuture<>();
            futureMap.put(data.getCode(), future);
            channel.writeAndFlush(cmd);
            Protocol rsp = future.get(5, TimeUnit.SECONDS);
            if (rsp == null) {
                return Response.error();
            }


            return Response.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(e.getMessage());
        }
    }

    private byte[] getDpBytes(String code, Integer point, String value) {
        UShort deviceTypeId = dslContext.select(DEVICE.TYPE_ID)
                .from(DEVICE)
                .where(DEVICE.CODE.eq(code))
                .fetchOneInto(UShort.class);
        int type = dslContext.select(DATA_POINT.DATA_TYPE)
                .from(DATA_POINT)
                .where(DATA_POINT.DEVICE_TYPE_ID.eq(deviceTypeId))
                .and(DATA_POINT.DATA_POINT_.eq(UShort.valueOf(point)))
                .fetchOneInto(int.class);
        DataPointTypeEnum enu = DataPointTypeEnum.getName(type);
        switch (enu) {
            case BYTE:
            case UNSIGNED_BYTE:
                byte[] b = new byte[1];
                b[0] = Byte.parseByte(value);
                return b;
            case SHORT:
            case UNSIGNED_SHORT:
                ByteBuffer s = ByteBuffer.allocate(Shorts.BYTES);
                s.putShort(Short.parseShort(value));
                return s.array();
            case BOOL:
                byte[] f = new byte[1];
                f[0] = (byte) (Boolean.valueOf(value) ? 1 : 0);
                return f;

            default:
                return null;
        }
    }

    private Map<Integer, String> paramMap(String data) {
        if (StringUtils.isEmpty(data)) {
            return Collections.emptyMap();
        }
        Map<Integer, String> map = Maps.newHashMap();
        try {
            Set<String> kv = Sets.newHashSet();
            Splitter.on(";")
                    .trimResults()
                    .omitEmptyStrings()
                    .split(data)
                    .forEach(kv::add);

            kv.forEach(e -> map.put(Integer.parseInt(e.substring(0, e.indexOf(":"))), e.substring(e.indexOf(":") + 1)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

}
