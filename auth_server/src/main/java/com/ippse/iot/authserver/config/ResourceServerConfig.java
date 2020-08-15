package com.ippse.iot.authserver.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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

import java.util.Map;

@Slf4j
@Configuration
@EnableResourceServer
//@EnableWebSecurity(debug = true)
//@EnableOAuth2Sso
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    /**
     * 仅为资源服务器的情况下，如果不开启这里的配置，将会出错。
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        log.info("开始ResourceServerConfig的HttpSecurity");
        //http.requestMatchers().antMatchers("/user").and().authorizeRequests().anyRequest().authenticated();
        // @formatter:off
        /*http
            .antMatcher("/**").authorizeRequests()
            .antMatchers("/actuator/**", "/login", "/login/**", "/register", "/register/**", "/reg_vericode.jpg", "/favicon.ico", "/swagger**", "/").permitAll()
            .anyRequest().anonymous()
            .and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);*/
        // @formatter:on
		/*http
		.authorizeRequests()
		.antMatchers(HttpMethod.OPTIONS).permitAll()
		.antMatchers("/actuator/health", "/favicon.ico", "/css/**", "/js/**","/image/**", "/fonts/**", "/register","/login**","/login/**").permitAll()
		.anyRequest().authenticated()*//*.and()
		.cors().and()
		.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)*/
        ;
        http.requestMatchers().antMatchers("/user").and().authorizeRequests().anyRequest().authenticated();
    }

	/*@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(null);
	}*/

    @Override
    public void configure(ResourceServerSecurityConfigurer config) {
        config.resourceId("resource").tokenServices(tokenServices());
    }

    @Primary
    @Bean
    public TokenStore tokenStore1() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("123");
        converter.setAccessTokenConverter(new JwtConverter());
        return converter;
    }

//    @Primary
    @Bean
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore1());
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
            auth.setDetails(map); //this will get spring to copy JWT content into Authentication
            return auth;
        }
    }

}

