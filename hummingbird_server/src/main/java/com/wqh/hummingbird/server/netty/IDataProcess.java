package com.wqh.hummingbird.server.netty;

import com.wqh.hummingbird.api.protocol.Protocol;
import com.wqh.hummingbird.server.common.Response;
import com.wqh.hummingbird.server.common.bo.DeviceCmdBO;
import io.netty.channel.Channel;

public interface IDataProcess {

    void upload(Protocol data);

    void addSession(String code, Channel channel);

    void removeSession(Channel channel);

    Response<String> command(DeviceCmdBO data);

}
