package com.ippse.iot.authserver.controller;

import com.ippse.iot.authserver.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 全局异常处理
 *
 * @ClassName GlobalAdvice
 * @Author zzm
 * @Data 2019/4/29 9:17
 * @Version 1.0
 */
@Slf4j
@ControllerAdvice
public class GlobalAdvice {

    @Autowired
    private MessageSource messageSource;

    /**
     * ServiceException异常捕捉处理
     *
     * @param se
     * @return
     */
    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> businessExceptionHandler(ServiceException se) {
        log.error(se.getMessage(), se);

        final Locale locale = LocaleContextHolder.getLocale();
        final String message = messageSource.getMessage(se.getMsg(), se.getParams(), locale);
        return new HashMap<String, Object>() {
            private static final long serialVersionUID = -2147774529887359231L;

            {
                put(se.getField(), message);
            }
        };
    }


    /**
     * bean校验未通过异常
     *
     * @param be bind检验异常类
     * @return
     * @see javax.validation.Valid
     * @see org.springframework.validation.Validator
     * @see org.springframework.validation.DataBinder
     */
    @ResponseBody
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> validExceptionHandler(BindException be) {
        final Locale locale = LocaleContextHolder.getLocale();
        List<FieldError> fieldErrors = be.getBindingResult().getFieldErrors();
        return new HashMap<String, Object>() {
            private static final long serialVersionUID = -3222861572243435035L;

            {
                for (FieldError error : fieldErrors) {
                    Object value = be.getFieldValue(error.getField());
                    log.info("field = {} , message = {}", error.getField(), error.getDefaultMessage());
                    String message = messageSource.getMessage(error.getDefaultMessage(), new Object[]{value}, locale);
                    put(error.getField(), message);
                }
            }
        };
    }


}
