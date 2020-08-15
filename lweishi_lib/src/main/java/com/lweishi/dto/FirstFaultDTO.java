package com.lweishi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @Author geek
 * @CreateTime 2020/8/7 23:10
 * @Description 接收传输过来的一级故障数据信息
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FirstFaultDTO {

    private String id;

    /**
     * @FiledName: 一级故障名称
     * @Description: 一级故障名称
     * @Example: 内存，显示屏...
     */
    @NotBlank(message = "一级故障名称不能为空")
    private String name;

    /**
     * @FiledName: 排序
     * @Description: 用F来排序品牌
     */
    @Min(value = 0, message = "排序值格式有误")
    private Integer sequence;

}
