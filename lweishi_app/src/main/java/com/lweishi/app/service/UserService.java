package com.lweishi.app.service;

import com.lweishi.app.domain.User;
import com.lweishi.app.exception.GlobalException;
import com.lweishi.app.repository.UserRepository;
import com.lweishi.app.util.IDUtil;
import com.lweishi.app.util.JwtUtils;
import com.lweishi.app.vo.AppLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    public Optional<User> checkUserIsExist(String mobile) {
        return userRepository.findByMobile(mobile);
    }

    public AppLoginVO login(String mobile, String password) {
        log.info("【mobile = {}password = {} 】", mobile, password);
        Optional<User> userOptional = this.checkUserIsExist(mobile.trim());
        User user = userOptional.orElseThrow(() -> new GlobalException(30001, "用户名或者密码错误"));
        if (!StringUtils.equals(password, user.getPassword())) {
            throw new GlobalException(30001, "用户名或者密码错误");
        }
        String token = JwtUtils.getJwtToken(user.getId(), user.getName(), user.getAvatar());
        return new AppLoginVO(token, user.getName(), user.getAvatar(), user.getMobile());
    }

    public User register(String openid, String name, String mobile, String avatar) {
        User user = new User(IDUtil.UUID(), mobile, "123456", name, avatar, openid, LocalDateTime.now());
        return userRepository.save(user);
    }

    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("没有找到该用户"));
    }
}
