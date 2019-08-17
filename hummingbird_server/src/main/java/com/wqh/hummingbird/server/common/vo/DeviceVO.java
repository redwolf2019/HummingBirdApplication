package com.wqh.hummingbird.server.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeviceVO {

    private Long id;
    private String type;
    private String code;
    private String name;
    private Boolean online;

}
