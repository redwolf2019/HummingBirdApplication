package com.wqh.hummingbird.api.protocol;

import com.wqh.hummingbird.api.common.device.DataPointAbilityEnum;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProtocolCodec {

    public static List<Protocol> decodeAll(ByteBuf buf) {
        List<Protocol> list = new ArrayList<>();
        for (; ; ) {
            if (buf.readableBytes() < 10) {
                break;
            }
            int len = buf.getShort(0);
            if (len > buf.readableBytes()) {
                break;
            }
            Protocol data = decode(buf.slice(0, len));
            if (data != null) {
                list.add(data);
            }
            buf.readerIndex(len);
            buf.discardReadBytes();
        }
        return list;
    }


    private static Protocol decode(ByteBuf buf) {
        try {
            log.info("decode one frame:{}", ByteBufUtil.hexDump(buf));
            final Protocol root = new Protocol();
            final List<DataPoint> body = new ArrayList<>();
            String hex = ByteBufUtil.hexDump(buf);
            final int fistDpIdx = hex.indexOf(ByteBufUtil.hexDump(DataPoint.SPLIT.getBytes())) / 2;
            final String code = buf.toString(2, fistDpIdx - 2, CharsetUtil.UTF_8);
            root.setCode(code);
            ByteBuf dpBuf = buf.slice(fistDpIdx, buf.capacity() - fistDpIdx);
            List<byte[]> bodyBytes = new ArrayList<>();
            List<Integer> splitIdx = new ArrayList<>();
            int index = 0;
            while (index < dpBuf.capacity() - 1) {
                if (dpBuf.toString(index, 2, CharsetUtil.UTF_8).equals(DataPoint.SPLIT)) {
                    splitIdx.add(index);
                }
                index++;
            }
            final int splitIdxLen = splitIdx.size();
            for (int i = 0; i < splitIdxLen; i++) {
                int start = splitIdx.get(i);
                int end;
                int eIdx = i + 1;
                if (eIdx >= splitIdxLen) {
                    end = dpBuf.capacity();
                } else {
                    end = splitIdx.get(eIdx);
                }
                byte[] bytes = new byte[end - start - 2];
                dpBuf.getBytes(start + 2, bytes);
                bodyBytes.add(bytes);
            }
            bodyBytes.forEach(bytes -> {
                DataPoint dp = new DataPoint();
                dp.setPoint(ProtocolUtil.bytesToUnShort(bytes, 0));
                dp.setAbility(DataPointAbilityEnum.getAbility(bytes[2]));
                dp.setBody(ProtocolUtil.copy(bytes, 3, bytes.length - 3));
                body.add(dp);
            });
            root.setDataPoints(body);
            return root;
        } catch (Exception e) {
            log.error("decode error: ", e);
            return null;
        }
    }

    public static byte[] encode(Protocol data) {
        try {
            if (data == null) {
                return null;
            }
            int size = data.getCode().length() + 2;
            if (data.getDataPoints() != null) {
                for (DataPoint d : data.getDataPoints()) {
                    size += 5;
                    if (d.getBody() != null) {
                        size += d.getBody().length;
                    }
                }
            }
            ByteBuffer buffer = ByteBuffer.allocate(size);
            buffer.putShort((short) size);
            buffer.put(data.getCode().getBytes(CharsetUtil.UTF_8));
            if (data.getDataPoints() != null) {
                data.getDataPoints().forEach(d -> {
                    buffer.put(DataPoint.SPLIT.getBytes());
                    buffer.putShort(d.getPoint().shortValue());
                    buffer.put((byte) d.getAbility().getAbility());
                    if (d.getBody() != null) {
                        buffer.put(d.getBody());
                    }
                });
            }
            return buffer.array();
        } catch (Exception e) {
            log.error("encode error: ", e);
            return null;
        }
    }


}
