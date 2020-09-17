package com.lweishi.wx.auth.utils;

import com.lweishi.model.WxUser;
import com.lweishi.service.WxUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName WxUserResolve
 * @Description 根据token解析小程序用户登录信息
 * @Author zzm
 * @Data 2020/8/28 16:36
 * @Version 1.0
 */
@Slf4j
@Component
public class WxUserResolve {

    @Autowired
    private WxUserService wxUserService;

    public WxUser resolveWxUser(HttpServletRequest request) {
        //调用jwt工具类的方法。根据request对象获取头信息，返回用户id
        String wxUserId = (String) request.getAttribute("wxUserId");
        log.info("【userInfo.wxUserId】 = {}", wxUserId);
        //查询数据库根据用户id获取用户信息
        return wxUserService.findById(wxUserId);
    }
}
