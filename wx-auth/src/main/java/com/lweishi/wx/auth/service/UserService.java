package com.lweishi.wx.auth.service;

import com.lweishi.wx.auth.domain.WxUser;
import com.lweishi.wx.auth.repository.UserRepository;
import com.lweishi.wx.auth.utils.IDUtil;
import com.lweishi.wx.auth.utils.JwtUtils;
import com.lweishi.wx.auth.vo.WxLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @Author geek
 * @CreateTime 2020/7/4 18:48
 * @Description 用户业务
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<WxUser> checkUserIsExist(String openid) {
        return userRepository.findByOpenid(openid);
    }

    public WxLoginVO login(String openid, String mobile) {
        Optional<WxUser> userOptional = this.checkUserIsExist(openid);
        WxUser wxUser = userOptional.orElseGet(() -> this.register(openid, "用户" + mobile, mobile, "https://zzm888.oss-cn-shenzhen.aliyuncs.com/default.png"));
        String token = JwtUtils.getJwtToken(wxUser.getId(), wxUser.getName(), wxUser.getAvatar());
        return new WxLoginVO(token, wxUser.getName(), wxUser.getAvatar(), wxUser.getMobile());
    }

    public WxUser register(String openid, String name, String mobile, String avatar) {
        WxUser wxUser = new WxUser(IDUtil.UUID(), mobile, "liweishi", name, avatar, openid, LocalDateTime.now());
        return userRepository.save(wxUser);
    }

    public WxUser findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("没有找到该用户"));
    }
}
