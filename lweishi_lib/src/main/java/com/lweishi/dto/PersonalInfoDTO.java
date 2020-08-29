package com.lweishi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName PersonalInfoDTO
 * @Description 个人信息数据
 * @Author zzm
 * @Data 2020/8/24 16:04
 * @Version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInfoDTO {
    @NotBlank(message = "订单ID不能为空")
    private String id;

    @NotBlank(message = "客户名称不能为空")
    private String customer; //客户名称
    @NotBlank(message = "手机号不能为空")
    private String mobile; //手机号
    @NotBlank(message = "地址不能为空")
    private String address; // 地址
}
