package com.lweishi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author geek
 * @CreateTime 2020/8/5 23:46
 * @Description 品牌
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Brand {
    @Id
    @Column(name = "id", length = 32, unique = true, nullable = false)
    private String id;

    /**
     * @FiledName: 品牌名称
     * @Description: 手机品牌名称
     * @Example: 华为，苹果...
     */
    private String name;

    /**
     * @FiledName: 排序
     * @Description: 用来排序品牌
     */
    private Integer sequence;

    /**
     * @FiledName: 图片
     * @Description: 品牌封面
     */
    private String image;

    private Boolean status;

    /**
     * @FiledName: 时间
     * @Description: 创立时间
     */
    private LocalDateTime createTime;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "coupon_category",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id"))
    private List<Coupon> couponList;
}
