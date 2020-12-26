package com.lweishi.repository;

import com.lweishi.model.Activity;
import com.lweishi.model.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    Page<Activity> findByTitleLike(String title, Pageable pageable);

    List<Activity> findByNameAndOnline(String name, Boolean online);

    Optional<Activity> findByCouponListId(Long couponId);

    Optional<Activity> findByName(String name);
}
