package com.lweishi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author geek
 * @CreateTime 2020/8/7 23:10
 * @Description 接收传输过来的二级故障数据信息
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SecondFaultDTO {

    private String id;

    /**
     * @FiledName: 二级故障名称
     * @Description: 二级故障名称
     * @Example: 国产屏，原产屏...
     */
    @NotBlank(message = "故障名称不能为空")
    private String name;

    @NotBlank(message = "描述不能为空")
    private String description;

    @NotBlank(message = "保修时间不能为空")
    private String expiration;

    /**
     * @FiledName: 排序
     * @Description: 用F来排序品牌
     */
    @Min(value = 0, message = "排序值格式有误")
    private Integer sequence;

    @NotBlank(message = "一级故障id不能为空")
    private String faultId; //一级故障id

}
