package com.ippse.iot.authserver.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @ClassName LoginAuthenticationProvider
 * @Description QQ登录认证提供者
 * @Author zzm
 * @Data 2019/7/29 14:10
 * @Version 1.0
 */
@Slf4j
public class LoginAuthenticationProvider implements AuthenticationProvider {

    @Getter
    @Setter
    private UserDetailsService qqUserDetailsService;

    @Getter
    @Setter
    private UserDetailsService wxUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //获取过滤器封装的token信息
        LoginToken authenticationToken = (LoginToken) authentication;

        UserDetails userDetails = null;

        if (StringUtils.equals(authenticationToken.getProviderid(), LoginToken.QQ)) {
            //获取用户信息（数据库认证）
            userDetails = qqUserDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());
        }

        if (StringUtils.equals(authenticationToken.getProviderid(), LoginToken.WX)) {
            //获取用户信息（数据库认证）
            userDetails = wxUserDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());
        }

        //通过
        LoginToken authenticationResult = new LoginToken(userDetails.getAuthorities(), userDetails, authenticationToken.getProviderid());

        authenticationResult.setDetails(authenticationToken.getDetails());
        log.info("【LoginToken = {}】", authenticationResult);
        return authenticationResult;
    }

    /**
     * 根据token类型，来判断使用哪个Provider
     *
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return LoginToken.class.isAssignableFrom(authentication);
    }
}
