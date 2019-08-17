package com.wqh.hummingbird.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping
@Controller
public class IndexController {

    @RequestMapping("/device/type")
    public String deviceType(){
        return "device_type";
    }

    @RequestMapping("/device/cmd")
    public String deviceCmd(){
        return "device_cmd";
    }

    @RequestMapping("/device/data")
    public String deviceData(){
        return "device_data";
    }

}
