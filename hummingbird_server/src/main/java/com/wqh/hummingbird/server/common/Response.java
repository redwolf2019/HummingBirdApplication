package com.wqh.hummingbird.server.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> implements Serializable {

    private boolean success;

    private T data;

    public static Response<String> ok(){
        return new Response<>(true,"操作成功");
    }

    public static Response<String> error(){
        return new Response<>(false,"操作失败");
    }

    public static Response<String> error(String err){
        return new Response<>(false,err);
    }

    public static <T> Response<T> ok(T data){
        return new Response<>(true,data);
    }

}
