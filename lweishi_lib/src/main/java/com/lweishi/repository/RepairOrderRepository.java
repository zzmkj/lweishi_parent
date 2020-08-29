package com.lweishi.repository;

import com.lweishi.model.RepairOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RepairOrderRepository extends JpaRepository<RepairOrder, String>, JpaSpecificationExecutor<RepairOrder> {
}
