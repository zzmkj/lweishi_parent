package com.ippse.iot.authserver.config;

import com.ippse.iot.authserver.services.DomainUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Map;

@Slf4j
@Configuration
@EnableAuthorizationServer //提供/oauth/authorize,/oauth/token,/oauth/check_token,/oauth/confirm_access,/oauth/error
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    @Qualifier("userDetailsService")
    private DomainUserDetailsService userDetailsService;
    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private DataSource dataSource;
    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 暴露公共key接口/oauth/token_key
     * 暴露token检查接口/oauth/check_token，需授权
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        log.info("开始OAuth2Config的AuthorizationServerSecurityConfigurer");
        security.tokenKeyAccess("permitAll()")//url:/oauth/token_key,exposes public key for token verification if using JWT tokens
                .checkTokenAccess("isAuthenticated()")//url:/oauth/check_token allow check token
                .allowFormAuthenticationForClients();
    }

    /**
     * 注入自定义的token结构。
     * TODO
     *
     * @return
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        log.info("tokenEnhancer 创建。。。。");
        return new CustomTokenEnhancer();
    }

    @Bean
    public TokenEnhancer jwtTokenEnhancer() {
        log.info("tokenEnhancer 创建。。。。");
        return accessTokenConverter();
    }

//    @Bean
//    public TokenStore tokenStore() {
//        log.info("tokenStore 创建。。。。");
//        return new JwtTokenStore(accessTokenConverter());
//    }
/*
	@Bean
	public DefaultTokenServices tokenServices(final TokenStore tokenStore,
											  final ClientDetailsService clientDetailsService) {
		log.info("tokenServices 创建。。。。");
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		tokenServices.setSupportRefreshToken(true);
		tokenServices.setTokenStore(tokenStore());
		tokenServices.setTokenEnhancer(tokenEnhancer());
		tokenServices.setClientDetailsService(clientDetailsService);
		tokenServices.setAuthenticationManager(this.authenticationManager);
		return tokenServices;
	}*/

    /**
     * http://www.baeldung.com/spring-security-oauth-jwt
     * <p>
     * AccessToken转换器-定义token的生成方式，这里使用JWT生成token，对称加密只需要加入key等其他信息（自定义）。
     * <p>
     * JWT中，需要在token中携带额外的信息，这样可以在服务之间共享部分用户信息，spring security默认在JWT的token中加入了user_name，
     * 如果我们需要额外的信息，需要自定义这部分内容。JwtAccessTokenConverter是我们用来生成token的转换器，所以我们需要配置这里面的
     * 部分信息来达到我们的目的。JwtAccessTokenConverter默认使用DefaultAccessTokenConverter来处理token的生成、装换、获取。
     * DefaultAccessTokenConverter中使用UserAuthenticationConverter来对应处理token与userinfo的获取、转换。因此我们需要重写下
     * UserAuthenticationConverter对应的转换方法。
     * <p>
     * 非对称加密方式（公钥密钥）
     * 生成JKS文件
     * keytool -genkeypair -alias mytest -keyalg RSA -keypass mypass -keystore mytest.jks -storepass mypass
     * 导出公钥
     * keytool -list -rfc --keystore mytest.jks | openssl x509 -inform pem -pubkey
     * 生成公钥文本（BEGIN PUBLIC KEY这段）存储为public.txt。把 mytest.jks和public.txt放入resource目录下
     * <p>
     * 修改JwtAccessTokenConverter，把证书导入
     *
     * @return
     * @Bean public TokenEnhancer accessTokenConverter() {
     * final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
     * KeyStoreKeyFactory keyStoreKeyFactory =
     * new KeyStoreKeyFactory(new ClassPathResource("mytest.jks"), "mypass".toCharArray());
     * converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));
     * <p>
     * converter.setAccessTokenConverter(new CustomerAccessTokenConverter());
     * return converter;
     * }
     * <p>
     * 自定义CustomerAccessTokenConverter 这个类的作用主要用于AccessToken的转换，
     * 默认使用DefaultAccessTokenConverter 这个装换器
     * DefaultAccessTokenConverter有个UserAuthenticationConverter，这个转换器作用是把用户的信息放入token中，
     * 默认只是放入username
     * 通过自定义CustomTokenEnhancer也可以加入用户信息。
     * public class CustomerAccessTokenConverter extends DefaultAccessTokenConverter {
     * public CustomerAccessTokenConverter() {
     * super.setUserTokenConverter(new CustomerUserAuthenticationConverter());
     * }
     * <p>
     * private class CustomerUserAuthenticationConverter extends DefaultUserAuthenticationConverter {
     * @Override public Map<String, ?> convertUserAuthentication(Authentication authentication) {
     * LinkedHashMap response = new LinkedHashMap();
     * response.put("user_name", authentication.getName());
     * response.put("name", ((User) authentication.getPrincipal()).getName());
     * response.put("id", ((User) authentication.getPrincipal()).getId());
     * response.put("createAt", ((User) authentication.getPrincipal()).getCreateAt());
     * if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
     * response.put("authorities", AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
     * }
     * <p>
     * return response;
     * }
     * }
     * <p>
     * }
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        log.info("【accessTokenConverter 创建。。】");
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("123");
        converter.setAccessTokenConverter(new JwtConverter());
        return converter;
    }

    public static class JwtConverter extends DefaultAccessTokenConverter implements JwtAccessTokenConverterConfigurer {

        @Override
        public void configure(JwtAccessTokenConverter converter) {
            converter.setAccessTokenConverter(this);
        }

        @Override
        public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
            log.info("=====================" + map.toString());
            OAuth2Authentication auth = super.extractAuthentication(map);
            auth.setDetails(map); //this will get spring to copy JWT content into Authentication
            return auth;
        }
    }

    /**
     * 告诉spring security，把这个自定义的CustomTokenEnhancer加入到TokenEnhancer链中（链有多个）
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer configurer) throws Exception {
        log.info("开始OAuth2Config的AuthorizationServerEndpointsConfigurer");
        configurer.authenticationManager(authenticationManager);
        configurer.userDetailsService(userDetailsService);
        configurer.tokenStore(tokenStore);

        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(
                Arrays.asList(tokenEnhancer(), accessTokenConverter()));

        configurer.tokenStore(tokenStore)
                .tokenEnhancer(tokenEnhancerChain)
                .authenticationManager(authenticationManager).allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST).exceptionTranslator(loggingExceptionTranslator());
        //加了显示异常详情转换器
    }

    //自定义一个异常响应转换器，显示异常详情
    @Bean
    public WebResponseExceptionTranslator<OAuth2Exception> loggingExceptionTranslator() {
        return new DefaultWebResponseExceptionTranslator() {
            @Override
            public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
                // This is the line that prints the stack trace to the log. You can customise this to format the trace etc if you like
                e.printStackTrace();

                // Carry on handling the exception
                ResponseEntity<OAuth2Exception> responseEntity = super.translate(e);
                HttpHeaders headers = new HttpHeaders();
                headers.setAll(responseEntity.getHeaders().toSingleValueMap());
                OAuth2Exception excBody = responseEntity.getBody();
                return new ResponseEntity<>(excBody, headers, responseEntity.getStatusCode());
            }
        };
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        log.info("开始OAuth2Config的ClientDetailsServiceConfigurer");
        clients.jdbc(dataSource);
    }
}
