package com.lweishi.app.exception;

import com.lweishi.app.util.UnifyResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    //指定出现什么异常执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody //为了返回数据
    public UnifyResult error(Exception e) {
        e.printStackTrace();
        return UnifyResult.error().message("执行了全局异常处理..");
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody //为了返回数据
    public UnifyResult error(ArithmeticException e) {
        e.printStackTrace();
        return UnifyResult.error().message("执行了自定义异常处理..");
    }

    @ExceptionHandler(GlobalException.class)
    @ResponseBody
    public UnifyResult error(GlobalException e){
        log.error(ExceptionUtil.getMessage(e));
        e.printStackTrace();
        return UnifyResult.error().message(e.getMsg()).code(e.getCode());
    }
}
