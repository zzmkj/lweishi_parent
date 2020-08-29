package com.lweishi.wx.auth.repository;

import com.lweishi.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, String> {
    Optional<Address> findByWxUserIdAndPreferred(String wxUserId, Integer preferred);

    List<Address> findByWxUserId(String wxUserId);
}
