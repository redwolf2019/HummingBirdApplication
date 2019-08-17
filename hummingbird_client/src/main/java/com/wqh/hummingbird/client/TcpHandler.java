package com.wqh.hummingbird.client;

import com.wqh.hummingbird.api.common.device.DatPointExecuteResultEnum;
import com.wqh.hummingbird.api.common.device.DataPointAbilityEnum;
import com.wqh.hummingbird.api.protocol.DataPoint;
import com.wqh.hummingbird.api.protocol.Protocol;
import com.wqh.hummingbird.api.protocol.ProtocolUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class TcpHandler extends SimpleChannelInboundHandler<Protocol> {

    private TcpClient client;
    private Simulator simulator = new Simulator();

    public TcpHandler(TcpClient client) {
        this.client = client;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Protocol protocol) throws Exception {
        log.info("收到服务端数据：{}",protocol);
        List<DataPoint> list = protocol.getDataPoints();
        list.forEach(dp->{
            if(dp.getAbility() == DataPointAbilityEnum.R && dp.getPoint().equals(Simulator.DP_1)){
                byte[] b = new byte[3];
                b[0] = (byte)DatPointExecuteResultEnum.SUCCESS.getStatus();
                System.arraycopy(ProtocolUtil.shortToBytes((short)100),0,b,1,2);
                dp.setBody(b);
            }else if(dp.getAbility() == DataPointAbilityEnum.W && dp.getPoint().equals(Simulator.DP_3)){
                byte[] b = new byte[1];
                b[0] = (byte) DatPointExecuteResultEnum.SUCCESS.getStatus();
                dp.setBody(b);
            }
            dp.setAbility(DataPointAbilityEnum.X);
        });
        protocol.setDataPoints(list);
        channelHandlerContext.writeAndFlush(protocol);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                simulator.getData(client.getCode()).forEach(ctx::writeAndFlush);
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        simulator.getData(client.getCode()).forEach(ctx::writeAndFlush);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        client.connect();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
