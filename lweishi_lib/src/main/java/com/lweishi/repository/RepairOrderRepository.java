package com.lweishi.repository;

import com.lweishi.model.RepairOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RepairOrderRepository extends JpaRepository<RepairOrder, String>, JpaSpecificationExecutor<RepairOrder> {
    List<RepairOrder> findByWxUserId(String wxUserId);
}
