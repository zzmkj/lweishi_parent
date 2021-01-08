package com.lweishi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @ClassName CouponFault
 * @Description 故障分类的优惠券
 * @Author zzm
 * @Data 2021/1/7 15:50
 * @Version 1.0
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponFault {

    @Id
    private Long id;

    private Long couponId;

    private String faultId;
}
