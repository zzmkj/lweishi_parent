package com.lweishi.exception.http;

/**
 * 401 未登录异常
 */
public class UnAuthenticatedException extends HttpException{
    public UnAuthenticatedException(int code){
        this.code = code;
        this.httpStatusCode = 401;
    }
}
