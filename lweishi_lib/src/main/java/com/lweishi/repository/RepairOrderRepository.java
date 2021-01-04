package com.lweishi.repository;

import com.lweishi.model.RepairOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface RepairOrderRepository extends JpaRepository<RepairOrder, String>, JpaSpecificationExecutor<RepairOrder> {
    Page<RepairOrder> findByWxUserId(String wxUserId, Pageable pageable);

    Page<RepairOrder> findByWxUserIdAndStatus(String wxUserId, Integer status, Pageable pageable);

    Page<RepairOrder> findByStatus(Integer status, Pageable pageable);

    Optional<RepairOrder> findByIdAndAppUserId(String id, String appUserId);

    Page<RepairOrder> findByStatusAndAppUserId(Integer status, String appUserId, Pageable pageable);
}
