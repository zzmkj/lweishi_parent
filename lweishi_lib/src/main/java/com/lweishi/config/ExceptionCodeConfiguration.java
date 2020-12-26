package com.lweishi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname: ExceptionCodeConfiguration
 * @Author: Ming
 * @Date: 2020/2/13 3:42 下午
 * @Version: 1.0
 * @Description: [错误码对应错误信息]的配置管理类
 **/
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "geek")
@PropertySource(value = "classpath:config/exception-code.properties")
public class ExceptionCodeConfiguration {

    private Map<Integer, String> codes = new HashMap<>();

    public String getMessage(int code) {
        return codes.get(code);
    }
}
