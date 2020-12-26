package com.lweishi.exception.http;

/**
 * @Classname: NotFoundException
 * @Author: Ming
 * @Date: 2020/12/20 3:03 下午
 * @Version: 1.0
 * @Description: http 404 异常
 **/
public class NotFoundException extends HttpException {
    public NotFoundException(int code) {
        this.httpStatusCode = 404;
        this.code = code;
    }
}
