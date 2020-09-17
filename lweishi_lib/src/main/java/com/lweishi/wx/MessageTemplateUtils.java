package com.lweishi.wx;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @ClassName MessageTemplateUtils
 * @Description TODO
 * @Author zzm
 * @Data 2020/9/15 20:33
 * @Version 1.0
 */
@Component
public class MessageTemplateUtils {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WxConstant wxConstant;

    public void sendMiniprogram(String templateId, String toUser, Map<String, Object> params, String page) {
        //请求方式: GET  URL：https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
        // 发起一个get请求，返回的数据json文本，使用json工具将json文本直接转化为Class<?>
        TemplateAccessToken accessToken = restTemplate.getForObject(
                String.format(WxConstant.GET_TOKEN_URL, wxConstant.getAppid(), wxConstant.getSecret()),
                TemplateAccessToken.class);

        MiniMessageTemplate template = new MiniMessageTemplate();
        template.setTemplate_id(templateId);
        template.setTouser(toUser);
        template.setData(params);
        if (StringUtils.isNotBlank(page)) {
            template.setPage(page);
        }

        Result result = restTemplate.postForObject(String.format(WxConstant.MINIPROGRAM_MESSAGE_URL, accessToken.getAccessToken()), template, Result.class);
        System.out.println("【result】 = "+result);
    }
}
