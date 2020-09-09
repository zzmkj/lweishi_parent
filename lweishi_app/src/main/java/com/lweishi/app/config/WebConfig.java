package com.lweishi.app.config;

import com.lweishi.app.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName WebConfig
 * @Description Web配置管理
 * @Author zzm
 * @Data 2020/8/31 9:06
 * @Version 1.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/user/**", "/order/**").excludePathPatterns("/user/login");
    }
}
