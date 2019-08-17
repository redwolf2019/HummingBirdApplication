package com.wqh.hummingbird.server.service;

import com.alibaba.fastjson.JSONObject;

public interface IWebsocketService {

    void publish(JSONObject data);

}
