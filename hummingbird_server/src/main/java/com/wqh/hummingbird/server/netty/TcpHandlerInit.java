package com.wqh.hummingbird.server.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TcpHandlerInit extends ChannelInitializer<SocketChannel> {

    private IDataProcess process;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
//        socketChannel.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
        socketChannel.pipeline().addLast(new IdleStateHandler(30,5,60));
        socketChannel.pipeline().addLast(new TcpCoder());
        socketChannel.pipeline().addLast(new TcpHandler(process));
    }
}
