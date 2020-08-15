package com.lweishi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @Author geek
 * @CreateTime 2020/8/5 23:54
 * @Description 颜色
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Color {
    @Id
    @Column(name = "id", length = 32, nullable = false, unique = true)
    private String id;

    private String name;

    private String icon;

    private LocalDateTime createTime;
}
