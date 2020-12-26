package com.lweishi.utils;

import lombok.Data;

/**
 * @Classname: UnifyResponse
 * @Author: Ming
 * @Date: 2020/12/20 3:12 下午
 * @Version: 1.0
 * @Description: http 异常返回结果封装
 **/
@Data
public class UnifyResponse {
    private int code;
    private String message;
    private String request;

    public UnifyResponse(int code, String message, String request) {
        this.code = code;
        this.message = message;
        this.request = request;
    }

//    public static void createSuccess(int code) {
//        throw new CreateSuccess(code);
//    }
}