package com.lweishi.repair.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${logout-url}")
    private String logoutUrl;

    @Autowired
    private AjaxAuthenticationEntryPoint unauthorizedHandler;

    // 匹配ajax请求
    public static class AjaxRequestMatcher implements RequestMatcher {
        @Override
        public boolean matches(HttpServletRequest request) {
            return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"))
                    || request.getHeader("Accept") != null && request.getHeader("Accept").contains("application/json");
        }
    }

    // 客户端注销后，如果不注销服务端，则重新登录可能不需要输入账号密码
    // 如果直接加logoutSuccessUrl("https://accounts.15913.com/logout")，则可以从服务端注销，但不能正常返回url
    // 需要在服务端增加一个控制器，返回url
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/error**", "/actuator/**", "/favicon.ico", "/register", "/host/bind").permitAll()
                .anyRequest().authenticated().and().oauth2Login().authorizationEndpoint().and().userInfoEndpoint()
                .customUserType(OkUser.class, "ippse").and().and().exceptionHandling()
                .defaultAuthenticationEntryPointFor(unauthorizedHandler, new AjaxRequestMatcher()).and()
                .logout()
                .logoutSuccessUrl(logoutUrl).deleteCookies("JSESSIONID").invalidateHttpSession(true)
                .and()
                .exceptionHandling().accessDeniedPage("/401");
    }

/*	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() {
		return new CustomLogoutSuccessHandler();
	}*/

    // URL含有特殊符号如;号，新版会默认拒绝，估计5.1会解决此问题，以下为暂时关闭的解决方案，但有风险。
    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        firewall.setAllowSemicolon(true);
        return firewall;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        log.info("开始WebSecurityConfig的WebSecurity");
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
        web.ignoring().antMatchers("/css/**", "/js/**", "/fonts/**", "/images/**", "/dist/**", "/scss/**");
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

}
