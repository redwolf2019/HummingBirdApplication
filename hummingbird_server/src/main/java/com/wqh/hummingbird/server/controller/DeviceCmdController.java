package com.wqh.hummingbird.server.controller;

import com.wqh.hummingbird.server.common.Response;
import com.wqh.hummingbird.server.common.bo.DeviceAddBO;
import com.wqh.hummingbird.server.common.bo.DeviceCmdBO;
import com.wqh.hummingbird.server.netty.IDataProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/api/device/cmd")
@RestController

public class DeviceCmdController {

    @Autowired
    private IDataProcess process;

    @PostMapping
    public Response<String> add(@Valid DeviceCmdBO d){
        return process.command(d);
    }

}
