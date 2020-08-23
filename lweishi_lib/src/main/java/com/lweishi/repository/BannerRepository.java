package com.lweishi.repository;

import com.lweishi.model.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BannerRepository extends JpaRepository<Banner, String> {
    Page<Banner> findByNameLike(String name, Pageable pageable);

    List<Banner> findByStatus(Boolean status, Sort sort);
}
