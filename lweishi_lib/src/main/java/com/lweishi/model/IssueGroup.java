package com.lweishi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @ClassName IssueGroup
 * @Description 问题分组
 * @Author zzm
 * @Data 2020/9/9 15:33
 * @Version 1.0
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class IssueGroup {

    @Id
    @Column(name = "id", length = 32, unique = true, nullable = false)
    private String id;

    private String name;

    private String icon;

    private LocalDateTime createTime;
}
