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
 * @Description 接收传输过来的品牌数据信息
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrandDTO {

    private String id;

    /**
     * @FiledName: 品牌名称
     * @Description: 手机品牌名称
     * @Example: 华为，苹果...
     */
    @NotBlank(message = "品牌名称不能为空")
    private String name;

    /**
     * @FiledName: 排序
     * @Description: 用来排序品牌
     */
    @Min(value = 0, message = "排序值格式有误")
    private Integer sequence;

    /**
     * @FiledName: 图片
     * @Description: 品牌商标
     */
    @NotBlank(message = "图片不能为空")
    private String image;

}
