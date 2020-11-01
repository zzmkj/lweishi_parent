package com.lweishi.repository;

import com.lweishi.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, String> {

    Page<Tag> findByTitleLike(String title, Pageable pageable);
}
