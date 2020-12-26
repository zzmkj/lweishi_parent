package com.lweishi.exception.http;

/**
 * 500 服务器内部错误
 */
public class ServerErrorException extends HttpException {
    public ServerErrorException(int code){
        this.code = code;
        this.httpStatusCode = 500;
    }
}
