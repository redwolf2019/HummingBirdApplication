package com.wqh.hummingbird.server.service.impl;

import com.wqh.hummingbird.server.common.LayuiPage;
import com.wqh.hummingbird.server.common.Response;
import com.wqh.hummingbird.server.common.bo.BasePage;
import com.wqh.hummingbird.server.common.bo.DeviceAddBO;
import com.wqh.hummingbird.server.common.vo.DeviceVO;
import com.wqh.hummingbird.server.generator.tables.records.DeviceRecord;
import com.wqh.hummingbird.server.service.IDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.types.UShort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.wqh.hummingbird.server.generator.tables.Device.DEVICE;
import static com.wqh.hummingbird.server.generator.tables.DeviceType.DEVICE_TYPE;

@Slf4j
@Service
public class DeviceServiceImpl implements IDeviceService {

    @Autowired
    private DSLContext dslContext;

    @Override
    public Response<String> addDevice(DeviceAddBO r) {
        boolean exist = dslContext.fetchExists(
          dslContext.selectOne().from(DEVICE)
                .where(DEVICE.CODE.eq(r.getCode()).or(DEVICE.NAME.eq(r.getName())))
        );
        if(exist){
            return Response.error("数据已存在");
        }

        dslContext.insertInto(DEVICE,DEVICE.TYPE_ID,DEVICE.CODE,DEVICE.NAME)
                .values(UShort.valueOf(r.getDeviceTypeId()),r.getCode(),r.getName())
                .execute();

        return Response.ok();
    }

    @Override
    public Response<String> deleteById(Long id) {
        dslContext.deleteFrom(DEVICE).where(DEVICE.ID.eq(id)).execute();
        return Response.ok();
    }

    @Override
    public LayuiPage<DeviceVO> getPage(BasePage r) {
        long count = dslContext.selectCount().from(DEVICE)
                .fetchOne(0, long.class);
        List<DeviceVO> list = new ArrayList<>();
        Result<DeviceRecord> data = dslContext.selectFrom(DEVICE)
                .orderBy(DEVICE.ID.desc())
                .limit(r.getPage() - 1, r.getLimit())
                .fetch();
        if (data != null) {
            Set<UShort> typeIds = data.stream().map(DeviceRecord::getTypeId).collect(Collectors.toSet());
            Map<UShort, String> typeMap = dslContext.select(DEVICE_TYPE.ID, DEVICE_TYPE.NAME)
                    .from(DEVICE_TYPE)
                    .where(DEVICE_TYPE.ID.in(typeIds))
                    .fetchMap(DEVICE_TYPE.ID, DEVICE_TYPE.NAME);

            data.forEach(v -> {
                DeviceVO d = new DeviceVO();
                d.setId(v.getId());
                if (typeMap != null) {
                    d.setType(typeMap.get(v.getTypeId()));
                }
                d.setName(v.getName());
                d.setCode(v.getCode());
                d.setOnline(v.getOnline());
                list.add(d);
            });
        }

        return new LayuiPage<>(count, list);
    }
}
