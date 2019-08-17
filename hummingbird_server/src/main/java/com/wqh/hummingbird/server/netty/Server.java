package com.wqh.hummingbird.server.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Server {

    private Server() {
    }

    private static final class Holder {
        private static final Server INS = new Server();
    }

    public static Server getInstance() {
        return Holder.INS;
    }

    private Channel channel;
    private NioEventLoopGroup workGroup, bossGroup;


    public void start(IDataProcess process) throws InterruptedException {
        workGroup = new NioEventLoopGroup();
        bossGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workGroup);
        b.channel(NioServerSocketChannel.class);
        b.childHandler(new TcpHandlerInit(process));
        channel = b.bind(9875).sync().channel();
        log.info("TCP server started...");
    }

    public void close() throws IOException {
        if (channel != null && channel.isOpen()) {
            channel.close();
        }
        if (workGroup != null) {
            workGroup.shutdownGracefully();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
    }


}
