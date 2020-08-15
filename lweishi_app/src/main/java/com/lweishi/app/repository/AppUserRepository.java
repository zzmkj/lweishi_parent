package com.lweishi.app.repository;


import com.lweishi.app.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @Author geek
 * @CreateTime 2020/7/4 18:47
 */
public interface AppUserRepository extends JpaRepository<AppUser, String> {
    Optional<AppUser> findByMobile(String mobile);
}
