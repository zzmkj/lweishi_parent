package com.ippse.iot.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @ClassName QQLoginAuthenticationSecurityConfig
 * @Description 配置
 * @Author zzm
 * @Data 2019/7/29 16:20
 * @Version 1.0
 */
@Component
public class QQLoginAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private static final String QQLOGINURL = "/login/qq";
    private static final String QQLOGINPARAMETER = "qqopenid";

    private static final String WXLOGINURL = "/login/wx";
    private static final String WXLOGINPARAMETER = "wxopenid";

    private static final String LOGINHTTPMETHOD = "POST";

    @Autowired
    private UserDetailsService qqUserDetailsService;

    @Autowired
    private UserDetailsService wxUserDetailsService;

    @Autowired
    private AuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private AuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        //qq过滤器
        LoginFilter loginFilter = new LoginFilter(QQLOGINURL, QQLOGINPARAMETER, LOGINHTTPMETHOD);
        //微信过滤器
        LoginFilter wxLoginFilter = new LoginFilter(WXLOGINURL, WXLOGINPARAMETER, LOGINHTTPMETHOD);

        LoginAuthenticationProvider loginAuthenticationProvider = new LoginAuthenticationProvider();
        //qq
        loginFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        loginFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
        loginFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        loginAuthenticationProvider.setQqUserDetailsService(qqUserDetailsService);

        //wx
        wxLoginFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        wxLoginFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
        wxLoginFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        loginAuthenticationProvider.setWxUserDetailsService(wxUserDetailsService);

        http.authenticationProvider(loginAuthenticationProvider)
                .addFilterAfter(loginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(wxLoginFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
