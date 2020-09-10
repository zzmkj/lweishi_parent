package com.lweishi.repository;

import com.lweishi.model.IssueGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueGroupRepository extends JpaRepository<IssueGroup, String> {
    Page<IssueGroup> findByNameLike(String name, Pageable pageable);
}
