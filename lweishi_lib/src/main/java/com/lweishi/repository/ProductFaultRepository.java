package com.lweishi.repository;

import com.lweishi.model.ProductFault;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductFaultRepository extends JpaRepository<ProductFault, String> {
    List<ProductFault> findByProductId(String productId);

    List<ProductFault> findByIdIn(List<String> ids);
}
