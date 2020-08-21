package com.lweishi.service;

import com.lweishi.exception.GlobalException;
import com.lweishi.model.AppUser;
import com.lweishi.repository.AppUserRepository;
import com.lweishi.utils.IDUtil;
import com.lweishi.utils.JwtUtils;
import com.lweishi.vo.AppLoginVO;
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
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    public Optional<AppUser> checkUserIsExist(String mobile) {
        return appUserRepository.findByMobile(mobile);
    }

    public AppLoginVO login(String mobile, String password) {
        log.info("【mobile = {}password = {} 】", mobile, password);
        Optional<AppUser> userOptional = this.checkUserIsExist(mobile.trim());
        AppUser appUser = userOptional.orElseThrow(() -> new GlobalException(30001, "用户名或者密码错误"));
        if (!StringUtils.equals(password, appUser.getPassword())) {
            throw new GlobalException(30001, "用户名或者密码错误");
        }
        String token = JwtUtils.getAppJwtToken(appUser.getId(), appUser.getName(), appUser.getAvatar());
        return new AppLoginVO(token, appUser.getName(), appUser.getAvatar(), appUser.getMobile());
    }

    public AppUser register(String name, String mobile, String avatar) {
        AppUser appUser = new AppUser(IDUtil.UUID(), mobile, "123456", name, avatar, LocalDateTime.now());
        return appUserRepository.save(appUser);
    }

    public AppUser findById(String id) {
        return appUserRepository.findById(id).orElseThrow(() -> new RuntimeException("没有找到该用户"));
    }
}
