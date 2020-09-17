package com.lweishi.wx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author geek
 * @CreateTime 2020/7/10 14:34
 * @Description 微信小程序订阅消息参数封装类
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MiniMessageTemplate {
    private String touser;

    private String template_id;

    private Map<String, Object> data = new HashMap<>();

    private String page;

    public static Map<String, String> initData(String value) {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("value", value);
        return data;
    }
}