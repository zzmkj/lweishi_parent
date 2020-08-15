package com.lweishi.app.repository;


import com.lweishi.app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @Author geek
 * @CreateTime 2020/7/4 18:47
 */
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByMobile(String mobile);
}
