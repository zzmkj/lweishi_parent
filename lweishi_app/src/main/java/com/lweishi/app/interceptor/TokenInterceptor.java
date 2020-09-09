package com.lweishi.app.interceptor;

import com.lweishi.exception.UnauthorizedException;
import com.lweishi.model.AppUser;
import com.lweishi.repository.AppUserRepository;
import com.lweishi.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName TokenInterceptor
 * @Description token解析拦截器
 * @Author zzm
 * @Data 2020/8/31 9:05
 * @Version 1.0
 */
@Slf4j
@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Token 验证
        String id = JwtUtils.getMemberIdByJwtToken(request);
        log.info("【token拦截器】 = {}", id);
        if (StringUtils.isBlank(id)) {
            throw new UnauthorizedException("无效的token");
        }
        AppUser appUser = appUserRepository.findById(id).orElseThrow(() -> new UnauthorizedException("未登录！"));
        request.setAttribute("appUser", appUser);
        return true;
    }
}
