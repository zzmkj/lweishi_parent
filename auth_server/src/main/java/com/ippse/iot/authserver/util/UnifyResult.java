package com.ippse.iot.authserver.util;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author geek
 * @CreateTime 2020/5/23 23:28
 * @Description 统一返回结果类
 */
@Data
public class UnifyResult {
    private Boolean success;
    private Integer code;
    private String message;
    private Map<String, Object> data = new HashMap<String, Object>();

    private UnifyResult() {}

    //成功静态方法
    public static UnifyResult ok() {
        UnifyResult r = new UnifyResult();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }

    //失败静态方法
    public static UnifyResult error() {
        UnifyResult r = new UnifyResult();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }

    public UnifyResult success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public UnifyResult message(String message){
        this.setMessage(message);
        return this;
    }

    public UnifyResult code(Integer code){
        this.setCode(code);
        return this;
    }

    public UnifyResult data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public UnifyResult data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}
