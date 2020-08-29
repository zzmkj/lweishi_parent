package com.lweishi.wx.auth.utils;

import com.lweishi.wx.auth.domain.WxUser;
import com.lweishi.wx.auth.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName WxUserResolve
 * @Description TODO
 * @Author zzm
 * @Data 2020/8/28 16:36
 * @Version 1.0
 */
@Component
public class WxUserResolve {

    @Autowired
    private WxUserService wxUserService;

    public WxUser resolveWxUser(HttpServletRequest request) {
        //调用jwt工具类的方法。根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据用户id获取用户信息
        return wxUserService.findById(memberId);
    }
}
