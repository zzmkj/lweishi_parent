package com.lweishi.exception;

import com.lweishi.config.ExceptionCodeConfiguration;
import com.lweishi.exception.http.HttpException;
import com.lweishi.utils.UnifyResponse;
import com.lweishi.utils.UnifyResult;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private ExceptionCodeConfiguration codeConfiguration;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public UnifyResult error(MethodArgumentNotValidException e) {
        List<ObjectError> errorList = e.getBindingResult().getAllErrors();
        log.error(errorList + "");
        return UnifyResult.error().message(errorList.get(0).getDefaultMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public UnifyResult error(UnauthorizedException e){
        log.error(ExceptionUtil.getMessage(e));
        return UnifyResult.error().code(10005).message(e.getMsg());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public UnifyResult error(ExpiredJwtException e){
        log.error(ExceptionUtil.getMessage(e));
        return UnifyResult.error().code(10005).message("无效token");
    }

    /**
     * Http异常捕获
     */
    @ExceptionHandler(HttpException.class)
    public ResponseEntity<UnifyResponse> handleHttpException(HttpServletRequest req, HttpException e) {
        String url = req.getRequestURI();
        String method = req.getMethod();
        String message = codeConfiguration.getMessage(e.getCode());

        UnifyResponse response = new UnifyResponse(e.getCode(), message, method + " " + url);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus httpStatus = HttpStatus.resolve(e.getHttpStatusCode());

        ResponseEntity<UnifyResponse> responseEntity = new ResponseEntity(response, headers, httpStatus);
        return responseEntity;
    }
}
