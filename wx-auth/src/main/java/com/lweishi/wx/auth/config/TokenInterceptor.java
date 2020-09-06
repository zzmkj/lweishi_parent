package com.lweishi.wx.auth.config;

import com.lweishi.exception.GlobalException;
import com.lweishi.exception.UnauthorizedException;
import com.lweishi.wx.auth.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.auth.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author geek
 * @CreateTime 2020/9/5 11:27
 * @Description token拦截器
 */
@Slf4j
@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Token 验证
        String id = JwtUtils.getMemberIdByJwtToken(request);
        log.info("【token拦截器】 = {}", id);
        if (StringUtils.isBlank(id)) {
            throw new UnauthorizedException("无效的token");
        }
        request.setAttribute("wxUserId", id);
        return true;
    }
}
