package com.lweishi.wx.auth.repository;


import com.lweishi.wx.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @Author geek
 * @CreateTime 2020/7/4 18:47
 */
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByOpenid(String openid);
}
