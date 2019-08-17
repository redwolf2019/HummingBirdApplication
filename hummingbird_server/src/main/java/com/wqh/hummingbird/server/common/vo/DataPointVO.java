package com.wqh.hummingbird.server.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataPointVO {

    private Long id;
    private Integer dataPoint;
    private String ability;
    private String dataType;
    private String name;
    private String description;

}
