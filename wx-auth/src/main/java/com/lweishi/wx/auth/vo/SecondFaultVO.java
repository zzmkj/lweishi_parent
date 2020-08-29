package com.lweishi.wx.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @ClassName SecondFaultVO
 * @Description 二级故障信息
 * @Author zzm
 * @Data 2020/8/28 9:59
 * @Version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SecondFaultVO {
    private String id;

    private String faultId; //一级故障id

    private String name; //故障名称

    private String description; //描述

    private String expiration; //保修时间

    private Integer sequence; //故障排序

    private BigDecimal price; //故障价格

}
