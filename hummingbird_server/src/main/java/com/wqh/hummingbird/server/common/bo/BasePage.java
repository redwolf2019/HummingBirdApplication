package com.wqh.hummingbird.server.common.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BasePage {

    private Integer page;
    private Integer limit;

}
