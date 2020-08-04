package com.lweishi.wx.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.lweishi"})
@EnableConfigurationProperties
public class WxAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxAuthApplication.class, args);
    }
}
