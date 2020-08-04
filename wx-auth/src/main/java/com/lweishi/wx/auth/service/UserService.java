package com.lweishi.wx.auth.service;

import com.lweishi.wx.auth.domain.User;
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

    public Optional<User> checkUserIsExist(String openid) {
        return userRepository.findByOpenid(openid);
    }

    public WxLoginVO login(String openid, String mobile) {
        Optional<User> userOptional = this.checkUserIsExist(openid);
        User user = userOptional.orElseGet(() -> this.register(openid, "用户" + mobile, mobile, "https://zzm888.oss-cn-shenzhen.aliyuncs.com/default.png"));
        String token = JwtUtils.getJwtToken(user.getId(), user.getName(), user.getAvatar());
        return new WxLoginVO(token, user.getName(), user.getAvatar(), user.getMobile());
    }

    public User register(String openid, String name, String mobile, String avatar) {
        User user = new User(IDUtil.UUID(), mobile, "liweishi", name, avatar, openid, LocalDateTime.now());
        return userRepository.save(user);
    }

    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("没有找到该用户"));
    }
}
