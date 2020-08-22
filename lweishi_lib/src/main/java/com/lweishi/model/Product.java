package com.lweishi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @ClassName Product
 * @Description 手机机型
 * @Author zzm
 * @Data 2020/8/22 13:12
 * @Version 1.0
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @Column(name = "id", length = 32, nullable = false, unique = true)
    private String id;

    private String brandId; //品牌ID

    private String brandName; //品牌名称

    private String name;

    private String image; //手机机型图片

    private Integer sequence;

    private Boolean status; // 1：启用 ，0：停用

    private LocalDateTime createTime;
}
