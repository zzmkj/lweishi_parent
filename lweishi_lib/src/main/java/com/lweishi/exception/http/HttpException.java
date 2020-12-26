package com.lweishi.exception.http;

import lombok.Getter;
import lombok.Setter;

/**
 * @Classname: HttpException
 * @Author: Ming
 * @Date: 2020/12/20 3:01 下午
 * @Version: 1.0
 * @Description: http请求异常
 **/
@Getter
@Setter
public class HttpException extends RuntimeException {
    protected Integer code;
    protected Integer httpStatusCode = 500;
}
