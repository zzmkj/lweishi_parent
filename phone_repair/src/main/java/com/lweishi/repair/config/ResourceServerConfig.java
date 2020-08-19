package com.lweishi.repair.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

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

    /**
     * 仅为资源服务器的情况下，如果不开启这里的配置，将会出错。
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        log.info("开始ResourceServerConfig的HttpSecurity");
        http.cors().and().csrf().disable().authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/webjars/**", "/error**", "/actuator/**", "/favicon.ico", "/swagger**", "/swagger**/**",
                        "/register/**", "/register", "/login/**", "/public/**", "/dist/**", "/upload/**")
                .permitAll().anyRequest().authenticated().and().oauth2Login().authorizationEndpoint().and()
                .userInfoEndpoint().customUserType(OkUser.class, "ippse").and().and().exceptionHandling()
                .defaultAuthenticationEntryPointFor(unauthorizedHandler, new AjaxRequestMatcher());
    }

    /*
     * @Override public void configure(ResourceServerSecurityConfigurer
     * resources) { resources.resourceId(null); }
     */

    @Override
    public void configure(ResourceServerSecurityConfigurer config) {
        config.resourceId("resource").tokenServices(tokenServices());
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("123");
        converter.setAccessTokenConverter(new JwtConverter());
        return converter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }

    public static class JwtConverter extends DefaultAccessTokenConverter implements JwtAccessTokenConverterConfigurer {

        @Override
        public void configure(JwtAccessTokenConverter converter) {
            converter.setAccessTokenConverter(this);
        }

        @Override
        public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
            OAuth2Authentication auth = super.extractAuthentication(map);
            log.info("99999999999999999" + map);
            auth.setDetails(map); // this will get spring to copy JWT content
            // into Authentication
            return auth;
        }
    }

}
