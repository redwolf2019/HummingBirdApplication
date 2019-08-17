package com.wqh.hummingbird.client;

import com.wqh.hummingbird.api.common.device.DataPointAbilityEnum;
import com.wqh.hummingbird.api.protocol.DataPoint;
import com.wqh.hummingbird.api.protocol.Protocol;
import com.wqh.hummingbird.api.protocol.ProtocolCodec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;

@Slf4j
@NoArgsConstructor
public final class Simulator {

    public static final int DP_1 = 1001;
    public static final int DP_2 = 1002;
    public static final int DP_3 = 1003;


    public List<Protocol> getData(String code) {
        ByteBuf buf = Unpooled.directBuffer(29);
        buf.writeShort(buf.capacity());
        buf.writeBytes(code.getBytes());
        buf.writeBytes(DataPoint.SPLIT.getBytes());
        buf.writeShort(DP_1);
        buf.writeByte(DataPointAbilityEnum.R.getAbility());
        buf.writeShort((short)new Random().nextInt(Short.MAX_VALUE + 1));
        buf.writeBytes(DataPoint.SPLIT.getBytes());
        buf.writeShort(DP_2);
        buf.writeByte(DataPointAbilityEnum.R.getAbility());
        buf.writeShort((short)new Random().nextInt(Short.MAX_VALUE + 1));
        buf.writeBytes(DataPoint.SPLIT.getBytes());
        buf.writeShort(DP_3);
        buf.writeByte(DataPointAbilityEnum.RW.getAbility());
        buf.writeByte(0);

        return ProtocolCodec.decodeAll(buf);
    }

}
