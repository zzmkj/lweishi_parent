package com.lweishi.exception;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author geek
 * @CreateTime 2020/5/24 19:25
 * @Description 自定义异常类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalException extends RuntimeException {
    @ApiModelProperty(value = "状态码")
    private Integer code;
    private String msg;

    @Override
    public String toString() {
        return "GlobalException{" +
        "message=" + msg +
        ", code=" + code +
        '}';
    }
}
