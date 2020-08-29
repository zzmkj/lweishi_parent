package com.lweishi.wx.auth.vo;

import com.lweishi.model.FirstFault;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @ClassName ProductInfoVO
 * @Description 返回给微信小程序端的产品信息
 * @Author zzm
 * @Data 2020/8/28 9:33
 * @Version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfoVO {
    private List<FirstFault> roots;

    private List<SecondFaultVO> subs;

    private ProductVO product;
}
