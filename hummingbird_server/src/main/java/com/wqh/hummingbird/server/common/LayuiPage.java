package com.wqh.hummingbird.server.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class LayuiPage<T> implements Serializable {

    private Integer code;
    private String msg;
    private Long count;
    private List<T> data;

    public LayuiPage(Long count, List<T> data) {
        this.count = count;
        this.data = data;
        this.code = 0;
        this.msg = "";
    }
}
