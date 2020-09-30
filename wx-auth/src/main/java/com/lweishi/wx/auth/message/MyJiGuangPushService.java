package com.lweishi.wx.auth.message;

import cn.jpush.api.push.model.PushPayload;

/**
 * 极光推送
 * 封装第三方api相关
 */
public interface MyJiGuangPushService {
    boolean pushAll(PushBean pushBean);

    boolean pushIos(PushBean pushBean);

    boolean pushIos(PushBean pushBean, String... registids);

    boolean pushAndroid(PushBean pushBean);

    boolean pushAndroid(PushBean pushBean, String... registids);

    boolean sendPush(PushPayload pushPayload);
}