package com.lweishi.wx;

import com.lweishi.model.RepairOrder;
import com.lweishi.model.WxUser;
import com.lweishi.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName WxTemplateService
 * @Description 微信模板信息发送
 * @Author zzm
 * @Data 2020/9/16 13:55
 * @Version 1.0
 */
@Component
public class WxTemplateService {

    @Autowired
    private MessageTemplateUtils messageTemplateUtils;

    @Autowired
    private WxUserService wxUserService;

    /**
     * 订单已被工程师接收，提醒用户【发送给买方】
     * @param order 订单
     */
    public void sendReceiptOrderMsg(RepairOrder order) {
        WxUser wxUser = wxUserService.findById(order.getWxUserId());

        //消息参数
        Map<String, Object> params = new HashMap<>();

        params.put("character_string7", MiniMessageTemplate.initData(order.getId()));
        params.put("name5",MiniMessageTemplate.initData(order.getAppUserName()));
        params.put("phone_number9", MiniMessageTemplate.initData(order.getAppUserMobile()));
        params.put("date11", MiniMessageTemplate.initData(order.getTime()));
        params.put("amount3",MiniMessageTemplate.initData(order.getActuallyPrice().toString()));

        String page = "pages/order-detail/order-detail?id=" + order.getId();
        // 发微信推送消息
        messageTemplateUtils.sendMiniprogram("Quv0SypOKCE_Tljldz-5z1QRHngou709mb-3_grZsZQ", wxUser.getOpenid(), params, page);
    }
}
