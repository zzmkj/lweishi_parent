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
 * @CreateTime 2020/10/31 15:59
 * @Description 热门关键词
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 32)
    private String id;
    private String title;
    private Integer sequence; //排序
    private Integer highlight; //是否高亮
    private String productId; //跳转到的产品id
    private LocalDateTime createTime;
}
