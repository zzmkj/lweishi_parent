package com.ippse.iot.authserver.dao;

import com.ippse.iot.authserver.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface UserRepository extends PagingAndSortingRepository<User, String> {

    Optional<User> findOptionalByUsername(String username);

    @Query("select t from User t where t.username = ?1 OR t.mobile = ?1")
    Optional<User> findOptionalByUsernameOrMobile(String username);

    Optional<User> findByQqopenid(String qqopenid);

    Optional<User> findByWxopenid(String openid);
}
