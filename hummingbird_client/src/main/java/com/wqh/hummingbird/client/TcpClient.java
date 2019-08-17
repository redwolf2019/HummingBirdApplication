package com.wqh.hummingbird.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class TcpClient {

    @Getter
    private boolean exit = false;

    @Getter
    private Channel channel;

    @Getter
    private String code;

    private String ip;

    private int port;

    private Bootstrap bootstrap;

    private NioEventLoopGroup workGroup;

    public TcpClient(String code, String ip, int port, NioEventLoopGroup workGroup) {
        this.code = code;
        this.ip = ip;
        this.port = port;
        this.workGroup = workGroup;
    }

    public void init() {
        bootstrap = new Bootstrap();
        bootstrap.group(this.workGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new TcpHandlerInit(this));
    }

    public void connect() {
        if (channel != null && channel.isActive()) {
            return;
        }
        log.info("tcp client {} connecting to {}:{}...", code,ip,port);

        ChannelFuture future = bootstrap.connect(ip, port);
        future.addListener((ChannelFutureListener) future1 -> {
            if (future1.isSuccess()) {
                channel = future1.channel();
            } else {
                future1.channel().eventLoop().schedule(this::connect, 2, TimeUnit.SECONDS);
            }
        });
    }


    public synchronized void close() throws InterruptedException {
        setExit(true);
        if (channel != null && channel.isActive()) {
            channel.close().sync();
        }
    }

    public synchronized void setExit(boolean exit) {
        this.exit = exit;
    }

}
