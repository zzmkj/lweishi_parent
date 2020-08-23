package com.lweishi.wx.auth.vo;

import com.lweishi.model.Brand;
import com.lweishi.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName CategoryVO
 * @Description TODO
 * @Author zzm
 * @Data 2020/8/23 16:13
 * @Version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryVO {

    private List<Brand> roots;

    private List<Product> subs;

}
