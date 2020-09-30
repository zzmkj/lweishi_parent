package com.lweishi.wx.auth.message;

/**
 * 推送服务
 * 封装业务功能相关
 */
public interface JiGuangPushService {
    boolean pushAll(PushBean pushBean);
 
    boolean pushIos(PushBean pushBean);
 
    boolean pushIos(PushBean pushBean, String... registids);
 
    boolean pushAndroid(PushBean pushBean);
 
    boolean pushAndroid(PushBean pushBean, String... registids);
 
    String[] checkRegistids(String[] registids);
}