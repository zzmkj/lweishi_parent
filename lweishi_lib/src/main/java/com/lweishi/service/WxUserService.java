package com.lweishi.service;

import com.lweishi.model.WxUser;
import com.lweishi.repository.WxUserRepository;
import com.lweishi.utils.IDUtil;
import com.lweishi.utils.JwtUtils;
import com.lweishi.vo.WxLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class WxUserService {

    @Autowired
    private WxUserRepository wxUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String AVATAR = "http://qiniu.lweishi.com/a.png";

    public Optional<WxUser> checkUserIsExist(String openid) {
        return wxUserRepository.findByOpenid(openid);
    }

    public WxLoginVO login(String openid, String mobile) {
        Optional<WxUser> userOptional = this.checkUserIsExist(openid);
        WxUser wxUser = userOptional.orElseGet(() -> this.register(openid, "用户" + mobile, mobile, AVATAR));
        String token = JwtUtils.getJwtToken(wxUser.getId(), wxUser.getName(), wxUser.getAvatar());
        return new WxLoginVO(token, wxUser.getName(), wxUser.getAvatar(), wxUser.getMobile());
    }

    public WxUser register(String openid, String name, String mobile, String avatar) {
        WxUser wxUser = new WxUser(IDUtil.UUID(), mobile, passwordEncoder.encode("liweishi"), name, avatar, openid, LocalDateTime.now());
        return wxUserRepository.save(wxUser);
    }

    public WxUser findById(String id) {
        return wxUserRepository.findById(id).orElseThrow(() -> new RuntimeException("没有找到该用户"));
    }
}
