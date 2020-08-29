package com.lweishi.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author geek
 * @CreateTime 2020/8/21 19:14
 * @Description 手机机型产品前端数据接收
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private String id;

    @NotBlank(message = "品牌ID不能为空")
    private String brandId;

    @NotBlank(message = "名称不能为空")
    private String name;

    @NotNull(message = "颜色不能为空")
    private List<String> colorIds;

    @NotBlank(message = "图片不能为空")
    private String image;

    @Min(value = 0, message = "排序值格式有误")
    private Integer sequence;
}
