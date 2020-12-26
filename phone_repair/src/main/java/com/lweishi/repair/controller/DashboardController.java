package com.lweishi.repair.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName DashboardController
 * @Description 首页仪表盘控制器
 * @Author zzm
 * @Data 2020/9/14 16:50
 * @Version 1.0
 */
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @GetMapping("/count")
    public void test() {

    }

}
