package com.lweishi.repository;

import com.lweishi.model.FirstFault;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FirstFaultRepository extends JpaRepository<FirstFault, String> {
    List<FirstFault> findByIdIn(List<String> ids, Sort sort);
}
