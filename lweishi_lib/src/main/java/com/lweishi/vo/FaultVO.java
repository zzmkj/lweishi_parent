package com.lweishi.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName FaultVO
 * @Description 订单返回产品故障信息
 * @Author zzm
 * @Data 2020/8/29 20:28
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FaultVO implements Serializable {
    private String id;
    private String name;
    private BigDecimal price;
}
