package com.lweishi.wx.auth.vo;

import com.lweishi.model.Color;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName ProductVO
 * @Description 产品信息
 * @Author zzm
 * @Data 2020/8/28 9:37
 * @Version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductVO {
    private String id;
    private String brand;
    private String name;
    private String cover;
    private List<Color> colors;
}
