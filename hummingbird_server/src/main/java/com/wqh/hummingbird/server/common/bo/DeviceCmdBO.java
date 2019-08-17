package com.wqh.hummingbird.server.common.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class DeviceCmdBO {


    @NotBlank
    private String cmd;

    @NotBlank
    private String code;

}
