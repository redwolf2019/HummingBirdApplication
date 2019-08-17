package com.wqh.hummingbird.client;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@AllArgsConstructor
public class TcpHandlerInit extends ChannelInitializer<SocketChannel> {

    private TcpClient client;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        int timeout = 5;
//        ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
        ch.pipeline().addLast(new IdleStateHandler(timeout, timeout, 60));
        ch.pipeline().addLast(new TcpCodec());
        ch.pipeline().addLast(new TcpHandler(client));
    }
}
