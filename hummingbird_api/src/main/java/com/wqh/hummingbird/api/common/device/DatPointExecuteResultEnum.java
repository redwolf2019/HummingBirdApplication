package com.wqh.hummingbird.api.common.device;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DatPointExecuteResultEnum {

    UN_SUPPORT(0),
    FAILED(1),
    SUCCESS(2),


    ;

    private int status;




}
