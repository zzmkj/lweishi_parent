package com.lweishi.wx.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author geek
 * @CreateTime 2020/7/5 12:18
 * @Description 微信登录返回信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxLoginVO {
    private String token;
    private String name;
    private String avatar;
    private String mobile;
}
