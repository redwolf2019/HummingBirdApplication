package com.wqh.hummingbird.server.service.impl;

import com.wqh.hummingbird.api.common.IdModel;
import com.wqh.hummingbird.server.common.LayuiPage;
import com.wqh.hummingbird.server.common.Response;
import com.wqh.hummingbird.server.common.bo.BasePage;
import com.wqh.hummingbird.server.common.bo.DeviceTypeAddBO;
import com.wqh.hummingbird.server.common.vo.DeviceTypeVO;
import com.wqh.hummingbird.server.generator.tables.records.DeviceTypeRecord;
import com.wqh.hummingbird.server.service.IDataPointService;
import com.wqh.hummingbird.server.service.IDeviceTypeService;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.types.UShort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.wqh.hummingbird.server.generator.tables.DeviceType.DEVICE_TYPE;

@Slf4j
@Service
public class DeviceTypeServiceImpl implements IDeviceTypeService {

    @Autowired
    private DSLContext dslContext;

    @Autowired
    private IDataPointService dataPointService;

    @Override
    public Response<String> addDeviceType(DeviceTypeAddBO data) {
        boolean exist = dslContext.fetchExists(
                dslContext.selectOne().from(DEVICE_TYPE)
                        .where(DEVICE_TYPE.ID.eq(UShort.valueOf(data.getId())))
        );
        if (exist) {
            return Response.error("数据已存在");
        }

        dslContext.insertInto(DEVICE_TYPE,
                DEVICE_TYPE.ID,
                DEVICE_TYPE.NAME,
                DEVICE_TYPE.DESCRIPTION)
                .values(UShort.valueOf(data.getId()),
                        data.getName(),
                        data.getDescription())
                .execute();

        return Response.ok();
    }

    @Override
    public Response<String> deleteDeviceTypeById(Integer id) {
        dslContext.deleteFrom(DEVICE_TYPE)
                .where(DEVICE_TYPE.ID.eq(UShort.valueOf(id)))
                .execute();
        dataPointService.deleteDataPointByDeviceTypeId(id);
        return Response.ok();
    }

    @Override
    public LayuiPage<DeviceTypeVO> getDeviceTypePage(BasePage data) {
        long count = dslContext.selectCount().from(DEVICE_TYPE).fetchOne(0, long.class);
        Result<DeviceTypeRecord> r = dslContext.selectFrom(DEVICE_TYPE)
                .orderBy(DEVICE_TYPE.ID.desc())
                .limit(data.getPage() - 1, data.getLimit())
                .fetch();
        if (r == null) {
            return new LayuiPage<>(count, Collections.emptyList());
        }

        List<DeviceTypeVO> list = new ArrayList<>();
        r.forEach(v->{
            DeviceTypeVO d = new DeviceTypeVO();
            d.setId(v.getId().intValue());
            d.setName(v.getName());
            d.setDescription(v.getDescription());
            list.add(d);
        });

        return new LayuiPage<>(count, list);
    }

    @Override
    public List<IdModel> getTypeList() {
        List<IdModel> list = new ArrayList<>();
        Result<Record2<UShort,String>> r = dslContext.select(
                DEVICE_TYPE.ID,
                DEVICE_TYPE.NAME
        ).from(DEVICE_TYPE)
                .fetch();
        if(r !=null){
            r.forEach(v-> list.add(new IdModel(v.value1().longValue(),v.value2())));
        }

        return list;
    }
}
