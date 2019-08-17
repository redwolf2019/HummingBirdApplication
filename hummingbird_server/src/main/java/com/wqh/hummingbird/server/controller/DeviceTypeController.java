package com.wqh.hummingbird.server.controller;

import com.wqh.hummingbird.api.common.IdModel;
import com.wqh.hummingbird.server.common.LayuiPage;
import com.wqh.hummingbird.server.common.Response;
import com.wqh.hummingbird.server.common.bo.BasePage;
import com.wqh.hummingbird.server.common.bo.DeviceTypeAddBO;
import com.wqh.hummingbird.server.common.vo.DeviceTypeVO;
import com.wqh.hummingbird.server.service.IDeviceTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequestMapping("/api/device/type")
@RestController
public class DeviceTypeController {

    @Autowired
    private IDeviceTypeService deviceTypeService;

    @PostMapping
    public Response<String> add(@Valid DeviceTypeAddBO p){
        return deviceTypeService.addDeviceType(p);
    }

    @GetMapping
    public LayuiPage<DeviceTypeVO> page(@Valid BasePage p){
        return deviceTypeService.getDeviceTypePage(p);
    }

    @DeleteMapping("/{id}")
    public Response<String> delete(@PathVariable("id") Integer id){
        return deviceTypeService.deleteDeviceTypeById(id);
    }

    @GetMapping("/list")
    public Response<List<IdModel>> getList(){
        return Response.ok(deviceTypeService.getTypeList());
    }

}
