package com.lweishi.wx.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.lweishi"})
@EnableJpaRepositories(basePackages = "com.lweishi")
public class WxAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxAuthApplication.class, args);
    }
}
