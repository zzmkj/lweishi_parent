package com.lweishi.exception.http;

/**
 * @Classname: ForbiddenException
 * @Author: Ming
 * @Date: 2020/12/20 3:05 下午
 * @Version: 1.0
 * @Description: http 403 没有权限
 **/
public class ForbiddenException extends HttpException {
    public ForbiddenException(int code) {
        this.code = code;
        this.httpStatusCode = 403;
    }
}
