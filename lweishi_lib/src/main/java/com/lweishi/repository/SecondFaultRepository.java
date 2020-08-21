package com.lweishi.repository;

import com.lweishi.model.SecondFault;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecondFaultRepository extends JpaRepository<SecondFault, String> {
    long deleteByFaultId(String faultId);
}
