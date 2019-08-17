package com.wqh.hummingbird.api.protocol;

import java.nio.ByteBuffer;

public class ProtocolUtil {

    public static int bytesToUnShort(byte[] src, int index) {
        return bytesToShort(src, index) & 0xFFFF;
    }

    public static byte[] copy(byte[] src, int index, int len) {
        byte[] b = new byte[len];
        System.arraycopy(src, index, b, 0, len);
        return b;
    }

    public static int byteToUnsigned(byte[] src, int index) {
        return src[index] & 0xFF;
    }

    public static int bytesToShort(byte[] src, int index) {
        ByteBuffer buffer = ByteBuffer.allocate(Short.BYTES);
        buffer.put(src, index, Short.BYTES);
        return buffer.getShort(0);
    }

    public static byte[] shortToBytes(short src){
        ByteBuffer buffer = ByteBuffer.allocate(Short.BYTES);
        buffer.putShort(src);
        return buffer.array();
    }

}
