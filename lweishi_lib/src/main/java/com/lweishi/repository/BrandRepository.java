package com.lweishi.repository;

import com.lweishi.model.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, String> {
    Page<Brand> findByNameLike(String name, Pageable pageable);

    List<Brand> findByStatus(Boolean status, Sort sort);
}
