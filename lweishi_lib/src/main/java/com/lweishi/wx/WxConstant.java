package com.lweishi.wx;

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

    public static final String WX_LOGIN_CODE_URL = "https://api.weixin.qq.com/sns/jscode2session" + "?appid=%s" + "&secret=%s" + "&js_code=%s" + "&grant_type=authorization_code";

    // 获取accessToken的接口
    public static final String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    //小程序发送订阅消息的接口
    public static final String MINIPROGRAM_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=%s";
}
