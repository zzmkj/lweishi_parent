package com.lweishi.repository;

import com.lweishi.domain.Color;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, String> {
    Page<Color> findByNameLike(String name, Pageable pageable);
}
