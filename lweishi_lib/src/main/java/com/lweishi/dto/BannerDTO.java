package com.lweishi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @Author geek
 * @CreateTime 2020/8/21 19:14
 * @Description 轮播图前端数据接收
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BannerDTO {

    private String id;

    @NotBlank(message = "名称不能为空")
    private String name;

    @NotBlank(message = "图片不能为空")
    private String img;

    private String link;

    @Min(value = 0, message = "排序值格式有误")
    private Integer sequence;
}
