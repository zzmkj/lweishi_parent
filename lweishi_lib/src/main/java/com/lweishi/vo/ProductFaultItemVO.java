package com.lweishi.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Author geek
 * @CreateTime 2020/8/23 23:20
 * @Description 产品故障详情
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFaultItemVO {
    private String firstFaultId;
    private String secondFaultId;
    private String secondFaultName;
    private BigDecimal price;
}
