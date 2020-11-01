package com.lweishi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO {

    private String id;

    @NotBlank(message = "标题不能为空")
    private String title;
    @Min(value = 0, message = "排序值格式有误")
    private Integer sequence; //排序
    @NotBlank(message = "产品不能为空")
    private String productId; //跳转到的产品id
}
