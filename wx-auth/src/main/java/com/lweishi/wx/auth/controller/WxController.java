package com.lweishi.wx.auth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lweishi.utils.UnifyResult;
import com.lweishi.wx.auth.constant.WxConstant;
import com.lweishi.wx.auth.domain.WxUser;
import com.lweishi.wx.auth.service.WxUserService;
import com.lweishi.wx.auth.utils.HttpClientUtils;
import com.lweishi.wx.auth.utils.JwtUtils;
import com.lweishi.wx.auth.utils.WxUtils;
import com.lweishi.wx.auth.vo.WxLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author geek
 * @CreateTime 2020/7/4 15:55
 * @Description 微信
 */
@Slf4j
@RestController
@RequestMapping("/wx")
public class WxController {

    @Autowired
    private WxConstant constant;

    @Autowired
    private WxUserService wxUserService;

    @GetMapping("/auth/phone")
    public UnifyResult authPhone(String code, String encryptedData, String iv) throws Exception {
        String url = String.format(WxConstant.WX_LOGIN_CODE_URL, constant.getAppid(), constant.getSecret(), code);
        String data = HttpClientUtils.get(url);
        JSONObject res = JSON.parseObject(data);
        String session_key = (String) res.get("session_key");
        String openid = (String) res.get("openid");

        try {
//            encryptedData = URLEncoder.encode(encryptedData, "UTF-8").replace("%3D", "=").replace("%2F", "/");
//            iv = URLEncoder.encode(iv, "UTF-8").replace("%3D", "=").replace("%2F", "/");
            String result = WxUtils.wxDecrypt(encryptedData, session_key, iv);
            JSONObject json = JSONObject.parseObject(result);
            if (!json.containsKey("phoneNumber")) {
                throw new Exception("请求失败！用户未绑定手机号");
            }
            String phone = json.getString("phoneNumber");
            if (StringUtils.isBlank(phone)) {
                throw new Exception("请求失败！用户未绑定手机号");
            }
            log.info("【openid】 = {}", openid);
            WxLoginVO vo = wxUserService.login(openid, phone);
            log.info("【手机号】 = {}", phone);
            return UnifyResult.ok().data("userInfo", vo);
        } catch (Exception e) {
            throw new Exception("请求失败！用户未绑定手机号");
        }
    }

    //根据token获取用户信息
    @GetMapping("/userInfo")
    public UnifyResult getMemberInfo(HttpServletRequest request) {
        log.info("【userInfo】 = {}", request.getAttribute("wxUserId"));
        //调用jwt工具类的方法。根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据用户id获取用户信息
        WxUser wxUser = wxUserService.findById(memberId);
        return UnifyResult.ok().data("userInfo", wxUser);
    }

}
