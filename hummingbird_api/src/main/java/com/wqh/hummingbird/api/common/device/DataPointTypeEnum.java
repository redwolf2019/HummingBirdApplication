package com.wqh.hummingbird.api.common.device;


import com.wqh.hummingbird.api.common.IdModel;
import com.wqh.hummingbird.api.protocol.ProtocolUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum DataPointTypeEnum {

    UNKNOWN(0, "未知"),
    UNSIGNED_BYTE(1, "0~255"),
    BYTE(2, "-127~+128"),
    UNSIGNED_SHORT(3, "0~65535"),
    SHORT(4, "-32767~+32768"),
    BOOL(5, "true/false"),
    EMPTY(6, "empty body"),

    ;

    private int id;
    private String description;

    public static DataPointTypeEnum getName(int id) {
        return Stream.of(DataPointTypeEnum.values())
                .filter(p -> p.id == id)
                .findAny()
                .orElse(DataPointTypeEnum.UNKNOWN);
    }

    public static List<IdModel> getList() {
        return Stream.of(DataPointTypeEnum.values())
                .filter(p -> p != DataPointTypeEnum.UNKNOWN)
                .map(p -> new IdModel((long) p.id, p.name() + ":" + p.getDescription()))
                .collect(Collectors.toList());
    }

    public static Object getValue(int id, byte[] data) {
        DataPointTypeEnum type = getName(id);
        switch (type) {
            case BOOL:
                return data[0] == 1;
            case UNSIGNED_BYTE:
                return ProtocolUtil.byteToUnsigned(data, 0);
            case BYTE:
                return (int) data[0];
            case SHORT:
                return ProtocolUtil.bytesToShort(data, 0);
            case UNSIGNED_SHORT:
                return ProtocolUtil.bytesToUnShort(data, 0);

            default:
                return null;
        }
    }

}
