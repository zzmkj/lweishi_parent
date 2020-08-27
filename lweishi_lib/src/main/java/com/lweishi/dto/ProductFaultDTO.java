package com.lweishi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * @Author geek
 * @CreateTime 2020/8/23 23:27
 * @Description 产品和故障实体的中间表
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductFaultDTO {
    private String id;

    @NotBlank(message = "产品ID不能为空")
    private String productId; //产品id

    @NotBlank(message = "一级故障ID不能为空")
    private String firstFaultId; //一级故障id

    @NotBlank(message = "二级故障ID不能为空")
    private String secondFaultId; //二级故障id

    private BigDecimal price; //故障价格
}
