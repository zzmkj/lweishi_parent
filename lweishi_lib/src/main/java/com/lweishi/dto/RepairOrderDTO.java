package com.lweishi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName RepairOrderDTO
 * @Description 前端传来的维修订单数据信息
 * @Author zzm
 * @Data 2020/8/24 16:02
 * @Version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepairOrderDTO {

    @NotBlank(message = "客户名称不能为空")
    private String customer; //客户名称

    @NotBlank(message = "手机号不能为空")
    private String mobile; //手机号

    @NotBlank(message = "地址不能为空")
    private String address; // 地址

    @NotBlank(message = "产品ID不能为空")
    private String productId;

    @NotBlank(message = "产品颜色不能为空")
    private String color;

    @Range(min = 0, max = 1, message = "订单类型错误")
    private Integer type;

    private String time; //预约时间

    private String remark; //备注信息
}
