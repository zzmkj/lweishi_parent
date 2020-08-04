package com.lweishi.wx.auth.utils;

import java.util.UUID;

/**
 * 生成UUID 工具类
 */
public class IDUtil {
    public static String UUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
