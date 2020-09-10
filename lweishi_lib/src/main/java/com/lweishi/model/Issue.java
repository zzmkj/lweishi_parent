package com.lweishi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @ClassName Issue
 * @Description 问题
 * @Author zzm
 * @Data 2020/9/9 15:32
 * @Version 1.0
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Issue {

    @Id
    @Column(name = "id", length = 32, unique = true, nullable = false)
    private String id;

    private String title;

    private String content;

    private String groupId;

    private String groupName;

    private LocalDateTime createTime;
}
