package com.lweishi.wx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName TemplateAccessToken
 * @Description 微信模板推送的token认证
 * @Author zzm
 * @Data 2019/10/11 10:21
 * @Version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TemplateAccessToken {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private Long expiresIn;
}