package com.lweishi.app.controller;

import com.lweishi.app.dto.AppLoginDTO;
import com.lweishi.vo.AppLoginVO;
import com.lweishi.service.AppUserService;
import com.lweishi.utils.UnifyResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserController
 * @Description app 登录控制器
 * @Author zzm
 * @Data 2020/8/14 14:48
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @PostMapping("/login")
    public UnifyResult login(@RequestBody AppLoginDTO appLoginDTO) {
        AppLoginVO userInfo = appUserService.login(appLoginDTO.getMobile(), appLoginDTO.getPassword());
        return UnifyResult.ok().data("userInfo", userInfo);
    }

}
