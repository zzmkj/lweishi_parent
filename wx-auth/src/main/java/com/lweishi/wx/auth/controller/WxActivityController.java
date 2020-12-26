package com.lweishi.wx.auth.controller;

import com.lweishi.model.Activity;
import com.lweishi.service.ActivityService;
import com.lweishi.utils.UnifyResult;
import com.lweishi.wx.auth.vo.ActivityCouponVO;
import com.lweishi.wx.auth.vo.ActivityPureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/activity")
@RestController
public class WxActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping("/name/{name}")
    public UnifyResult getHomeActivity(@PathVariable String name) {
        Activity activity = activityService.getByName(name);
        ActivityPureVO vo = new ActivityPureVO(activity);
        return UnifyResult.ok().data("data", vo);
    }

    @GetMapping("/name/{name}/with_coupon")
    public UnifyResult getActivityWithCoupons(@PathVariable String name) {
        Activity activity = activityService.getByName(name);
        ActivityCouponVO vo = new ActivityCouponVO(activity);
        return UnifyResult.ok().data("data", vo);
    }

}
