package com.wqh.hummingbird.client;

import com.wqh.hummingbird.api.protocol.Protocol;
import com.wqh.hummingbird.api.protocol.ProtocolCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
public class TcpCodec extends ByteToMessageCodec<Protocol> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Protocol protocol, ByteBuf byteBuf) throws Exception {
        byte[] data = ProtocolCodec.encode(protocol);
        if (data != null) {
            byteBuf.writeBytes(data);
        }
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buf, List<Object> list) throws Exception {
        list.addAll(ProtocolCodec.decodeAll(buf));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
