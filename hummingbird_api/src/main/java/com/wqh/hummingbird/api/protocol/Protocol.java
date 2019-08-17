package com.wqh.hummingbird.api.protocol;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class Protocol implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设备编码
     */
    private String code;

    /**
     * 数据点
     */
    private List<DataPoint> dataPoints;


}
