package com.lweishi.exception.http;

/**
 * 400 参数异常
 */
public class ParameterException extends HttpException {
    public ParameterException(int code){
        this.code = code;
        this.httpStatusCode = 400;
    }
}
