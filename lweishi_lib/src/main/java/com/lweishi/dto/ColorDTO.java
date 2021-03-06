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
 * @Description 接收传输过来的颜色数据信息
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ColorDTO {

    private String id;

    /**
     * @FiledName: name
     * @Description: 颜色名称
     * @Example: 黄色，蓝色...
     */
    @NotBlank(message = "颜色名称不能为空")
    private String name;

    /**
     * @FiledName: colorHex
     * @Description: 颜色值
     */
    @NotBlank(message = "颜色值不能为空")
    private String colorHex;

}
