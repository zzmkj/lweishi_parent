package com.ippse.iot.authserver.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName LoginFilter
 * @Description 第三方登录过滤器
 * @Author zzm
 * @Data 2019/7/29 12:56
 * @Version 1.0
 */
@Slf4j
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    private boolean postOnly = true;

    @Getter
    @Setter
    private String param;

    public LoginFilter(String url, String param, String httpMethod) {
        super(new AntPathRequestMatcher(url, httpMethod));
        this.param = param;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        if (postOnly && !request.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        //get qqopenid
        String openid = obtainOpenid(request);
        String providerid;
        log.info("【openid】 = {}", openid);

        if (StringUtils.startsWithIgnoreCase(param, LoginToken.QQ)) {
            providerid = LoginToken.QQ;
        } else if (StringUtils.startsWithIgnoreCase(param, LoginToken.WX)) {
            providerid = LoginToken.WX;
        } else {
            throw new BadCredentialsException("requestParam：" + param + "  Format Incorrect");
        }

        //assemble token
        LoginToken authRequest = new LoginToken(openid, providerid);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * 设置身份认证的详情信息
     */
    private void setDetails(HttpServletRequest request, LoginToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    /**
     * 获取手机号
     */
    private String obtainOpenid(HttpServletRequest request) {
        return request.getParameter(param);
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

}
