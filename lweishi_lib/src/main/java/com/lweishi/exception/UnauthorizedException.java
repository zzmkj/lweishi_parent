package com.lweishi.exception;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author geek
 * @CreateTime 2020/9/5 15:34
 * @Description 401未身份认证
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnauthorizedException extends RuntimeException {
    private String msg;

    @Override
    public String toString() {
        return "UnauthorizedException{" +
                "message=" + msg + '}';
    }
}
