package com.lweishi.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Where(clause = "delete_time is null")
public class Coupon extends BaseEntity {
    @Id
    private Long id;
    private Long activityId; //活动id
    private String title; //标题
    private Date startTime; //开始时间
    private Date endTime; //结束时间
    private String description; //描述信息
    private BigDecimal fullMoney; //满多少金额
    private BigDecimal minus; //满减金额
    private BigDecimal rate; //折扣率
    private String remark; //备注信息
    private Boolean wholeStore; //是否是全场券
    private Integer type; //1. 满减券 2.折扣券 3.无门槛券 4.满金额折扣券

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "couponList")
    private List<Brand> brandList;
}
