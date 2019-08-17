package com.wqh.hummingbird.api.protocol;

import com.wqh.hummingbird.api.common.device.DataPointAbilityEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class DataPoint implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SPLIT = "\r\n";

    /**
     * 标志位
     */
    private Integer point;

    /**
     * 功能位
     */
    private DataPointAbilityEnum ability;

    /**
     * 数据位
     */
    private byte[] body;

}
