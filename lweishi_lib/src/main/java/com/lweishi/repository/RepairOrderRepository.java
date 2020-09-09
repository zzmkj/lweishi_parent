package com.lweishi.repository;

import com.lweishi.model.RepairOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface RepairOrderRepository extends JpaRepository<RepairOrder, String>, JpaSpecificationExecutor<RepairOrder> {
    List<RepairOrder> findByWxUserId(String wxUserId);

    Page<RepairOrder> findByStatus(Integer status, Pageable pageable);

    Optional<RepairOrder> findByIdAndAppUserId(String id, String appUserId);

    Page<RepairOrder> findByStatusAndAppUserId(Integer status, String appUserId, Pageable pageable);
}
