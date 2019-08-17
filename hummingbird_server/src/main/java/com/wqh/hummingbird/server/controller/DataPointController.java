package com.wqh.hummingbird.server.controller;

import com.wqh.hummingbird.api.common.IdModel;
import com.wqh.hummingbird.server.common.LayuiPage;
import com.wqh.hummingbird.server.common.Response;
import com.wqh.hummingbird.server.common.bo.DataPointAddBO;
import com.wqh.hummingbird.server.common.bo.DataPointPageBO;
import com.wqh.hummingbird.api.common.device.DataPointAbilityEnum;
import com.wqh.hummingbird.api.common.device.DataPointTypeEnum;
import com.wqh.hummingbird.server.common.vo.DataPointVO;
import com.wqh.hummingbird.server.service.IDataPointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequestMapping("/api/data/point")
@RestController
public class DataPointController {

    @Autowired
    private IDataPointService dataPointService;

    @PostMapping
    public Response<String> add(@Valid DataPointAddBO p){
        return dataPointService.addDataPoint(p);
    }

    @DeleteMapping("/{id}")
    public Response<String> delete(@PathVariable("id") Long id){
        return dataPointService.deleteDataPointById(id);
    }

    @GetMapping
    public LayuiPage<DataPointVO> page(@Valid DataPointPageBO p) {
        return dataPointService.getPage(p);
    }

    @GetMapping("/ability")
    public Response<List<IdModel>> getAbility(){
        return Response.ok(DataPointAbilityEnum.getList());
    }

    @GetMapping("/type")
    public Response<List<IdModel>> getType(){
        return Response.ok(DataPointTypeEnum.getList());
    }


}
