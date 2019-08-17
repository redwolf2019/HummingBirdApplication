package com.wqh.hummingbird.client;

import io.netty.channel.nio.NioEventLoopGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {

    private static final String IP = "127.0.0.1";
    private static final int PORT = 9875;

    private static String getCode(int range) {
        return "HB_" + String.format("%04d", range);
    }

    private static void runTcp(NioEventLoopGroup group) {
        List<TcpClient> clients = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            clients.add(new TcpClient(getCode(i), IP, PORT, group));
        }
        clients.forEach(ins -> {
            ins.init();
            ins.connect();
        });
    }

    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        runTcp(group);

        Scanner in = new Scanner(System.in);
        String cmd;
        while (in.hasNext()) {
            cmd = in.nextLine();
            if (cmd.equalsIgnoreCase("quit")) {
                break;
            }
        }
    }

}
