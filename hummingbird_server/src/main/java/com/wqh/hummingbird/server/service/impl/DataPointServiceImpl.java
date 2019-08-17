package com.wqh.hummingbird.server.service.impl;

import com.wqh.hummingbird.server.common.LayuiPage;
import com.wqh.hummingbird.server.common.Response;
import com.wqh.hummingbird.server.common.bo.DataPointAddBO;
import com.wqh.hummingbird.server.common.bo.DataPointPageBO;
import com.wqh.hummingbird.api.common.device.DataPointAbilityEnum;
import com.wqh.hummingbird.api.common.device.DataPointTypeEnum;
import com.wqh.hummingbird.server.common.vo.DataPointVO;
import com.wqh.hummingbird.server.generator.tables.records.DataPointRecord;
import com.wqh.hummingbird.server.service.IDataPointService;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.types.UShort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.wqh.hummingbird.server.generator.tables.DataPoint.DATA_POINT;

@Slf4j
@Service
public class DataPointServiceImpl implements IDataPointService {

    @Autowired
    private DSLContext dslContext;

    @Override
    public Response<String> addDataPoint(DataPointAddBO r) {
        boolean exist = dslContext.fetchExists(
                dslContext.selectOne().from(DATA_POINT)
                        .where(DATA_POINT.DEVICE_TYPE_ID.eq(UShort.valueOf(r.getDeviceTypeId()))
                                .and(DATA_POINT.DATA_POINT_.eq(UShort.valueOf(r.getDataPoint())))
                                .or(DATA_POINT.DEVICE_TYPE_ID.eq(UShort.valueOf(r.getDeviceTypeId()))
                                        .and(DATA_POINT.NAME.eq(r.getName()))))

        );
        if (exist) {
            return Response.error("数据已存在");
        }
        dslContext.insertInto(DATA_POINT,
                DATA_POINT.DEVICE_TYPE_ID,
                DATA_POINT.DATA_POINT_,
                DATA_POINT.ABILITY,
                DATA_POINT.DATA_TYPE,
                DATA_POINT.NAME,
                DATA_POINT.DESCRIPTION
        ).values(
                UShort.valueOf(r.getDeviceTypeId()),
                UShort.valueOf(r.getDataPoint()),
                UShort.valueOf(r.getAbility()),
                UShort.valueOf(r.getDataType()),
                r.getName(),
                r.getDescription()
        ).execute();

        return Response.ok();
    }

    @Override
    public LayuiPage<DataPointVO> getPage(DataPointPageBO r) {
        long count = dslContext.selectCount().from(DATA_POINT)
                .where(DATA_POINT.DEVICE_TYPE_ID.eq(UShort.valueOf(r.getDeviceTypeId())))
                .fetchOne(0, long.class);
        List<DataPointVO> list = new ArrayList<>();
        Result<DataPointRecord> data = dslContext.selectFrom(DATA_POINT)
                .where(DATA_POINT.DEVICE_TYPE_ID.eq(UShort.valueOf(r.getDeviceTypeId())))
                .orderBy(DATA_POINT.ID.desc())
                .limit(r.getPage() - 1, r.getLimit())
                .fetch();
        if (data != null) {
            data.forEach(v -> {
                DataPointVO d = new DataPointVO();
                d.setId(v.getId());
                d.setDataPoint(v.getDataPoint().intValue());
                d.setAbility(DataPointAbilityEnum.getAbilityName(v.getAbility().intValue()));
                DataPointTypeEnum dpte = DataPointTypeEnum.getName(v.getDataType().intValue());
                d.setDataType(dpte.name() + "/" + dpte.getDescription());
                d.setName(v.getName());
                d.setDescription(v.getDescription());
                list.add(d);
            });
        }

        return new LayuiPage<>(count, list);
    }

    @Override
    public Response<String> deleteDataPointById(Long id) {
        dslContext.deleteFrom(DATA_POINT)
                .where(DATA_POINT.ID.eq(id))
                .execute();
        return Response.ok();
    }

    @Override
    public Response<String> deleteDataPointByDeviceTypeId(Integer id) {
        dslContext.deleteFrom(DATA_POINT)
                .where(DATA_POINT.DEVICE_TYPE_ID.eq(UShort.valueOf(id)))
                .execute();
        return Response.ok();
    }
}
