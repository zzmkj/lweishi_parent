package com.lweishi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @Author geek
 * @CreateTime 2020/8/21 19:07
 * @Description 轮播图
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Banner {
    @Id
    @Column(name = "id", length = 32, unique = true, nullable = false)
    private String id;

    private String name;

    private String img;

    private String link;

    private Integer sequence;

    private Boolean status; // 1：启用 ，0：停用

    private LocalDateTime createTime;
}
