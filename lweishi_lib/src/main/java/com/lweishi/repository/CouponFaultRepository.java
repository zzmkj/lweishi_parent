package com.lweishi.repository;

import com.lweishi.model.CouponFault;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponFaultRepository extends JpaRepository<CouponFault, Long> {
    List<CouponFault> findByFaultId(String faultId);
}
