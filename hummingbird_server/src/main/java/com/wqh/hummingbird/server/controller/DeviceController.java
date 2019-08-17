package com.wqh.hummingbird.server.controller;

import com.wqh.hummingbird.server.common.LayuiPage;
import com.wqh.hummingbird.server.common.Response;
import com.wqh.hummingbird.server.common.bo.BasePage;
import com.wqh.hummingbird.server.common.bo.DeviceAddBO;
import com.wqh.hummingbird.server.common.vo.DeviceVO;
import com.wqh.hummingbird.server.service.IDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/api/device")
@RestController
public class DeviceController {

    @Autowired
    private IDeviceService deviceService;

    @PostMapping
    public Response<String> add(@Valid DeviceAddBO d){
        return deviceService.addDevice(d);
    }

    @DeleteMapping("/{id}")
    public Response<String> delete(@PathVariable("id") Long id){
        return deviceService.deleteById(id);
    }

    @GetMapping
    public LayuiPage<DeviceVO> page(@Valid BasePage p) {
        return deviceService.getPage(p);
    }


}
