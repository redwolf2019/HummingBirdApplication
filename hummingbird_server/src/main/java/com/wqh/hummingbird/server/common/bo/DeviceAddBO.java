package com.wqh.hummingbird.server.common.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class DeviceAddBO {

    @NotNull
    private Integer deviceTypeId;

    @NotBlank
    private String name;

    @NotBlank
    private String code;

}
