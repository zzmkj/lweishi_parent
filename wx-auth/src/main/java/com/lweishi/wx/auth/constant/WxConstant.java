package com.lweishi.wx.auth.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "wx.login")
public class WxConstant {

    private String appid;
    private String secret;

    public static final String AES = "AES";
    public static final String AES_CBC_PADDING = "AES/CBC/PKCS7Padding";

    public static final String WX_LOGIN_CODE_URL = "https://api.weixin.qq.com/sns/jscode2session"+
            "?appid=%s"+
            "&secret=%s"+
            "&js_code=%s"+
            "&grant_type=authorization_code";


}
