package com.lweishi.repository;

import com.lweishi.model.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IssueRepository extends JpaRepository<Issue, String>, JpaSpecificationExecutor<Issue> {
    Page<Issue> findByTitleLike(String title, Pageable pageable);
}
