package com.wqh.hummingbird.server.common.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class DataPointAddBO {

    @NotNull
    private Integer deviceTypeId;

    @NotNull
    private Integer dataPoint;

    @NotNull
    private Integer ability;

    @NotNull
    private Integer dataType;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

}
