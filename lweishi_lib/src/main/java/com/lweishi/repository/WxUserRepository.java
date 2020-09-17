package com.lweishi.repository;


import com.lweishi.model.WxUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @Author geek
 * @CreateTime 2020/7/4 18:47
 */
public interface WxUserRepository extends JpaRepository<WxUser, String> {
    Optional<WxUser> findByOpenid(String openid);
}
