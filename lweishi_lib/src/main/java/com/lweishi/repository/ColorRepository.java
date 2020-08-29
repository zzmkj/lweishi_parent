package com.lweishi.repository;

import com.lweishi.model.Color;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColorRepository extends JpaRepository<Color, String> {
    Page<Color> findByNameLike(String name, Pageable pageable);

    List<Color> findByIdIn(List<String> ids);
}
