package com.lweishi.repository;

import com.lweishi.model.Product;
import com.lweishi.vo.SearchItemVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {
    List<Product> findByStatus(Boolean status, Sort sort);

    Page<SearchItemVO> findByNameLike(String name, Pageable pageable);
}
