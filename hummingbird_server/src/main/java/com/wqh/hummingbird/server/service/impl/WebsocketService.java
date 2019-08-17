package com.wqh.hummingbird.server.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wqh.hummingbird.server.service.IWebsocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WebsocketService implements IWebsocketService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void publish(JSONObject data) {
        simpMessagingTemplate.convertAndSend("/common",data.toJSONString());
    }


}
