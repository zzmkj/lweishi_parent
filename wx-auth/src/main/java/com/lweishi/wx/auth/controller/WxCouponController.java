package com.lweishi.wx.auth.controller;

import com.lweishi.enums.CouponStatus;
import com.lweishi.exception.http.ParameterException;
import com.lweishi.model.Coupon;
import com.lweishi.model.WxUser;
import com.lweishi.utils.UnifyResponse;
import com.lweishi.utils.UnifyResult;
import com.lweishi.wx.auth.service.CouponService;
import com.lweishi.wx.auth.utils.WxUserResolve;
import com.lweishi.wx.auth.vo.CouponBrandVO;
import com.lweishi.wx.auth.vo.CouponPureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/coupon")
public class WxCouponController {

    @Autowired
    private CouponService couponService;

    @Autowired
    private WxUserResolve wxUserResolve;

    @GetMapping("/by/category/{cid}")
    public List<CouponPureVO> getCouponListByCategory(@PathVariable String cid) {
        List<Coupon> coupons = couponService.getByCategory(cid);
        if (coupons.isEmpty()) {
            return Collections.emptyList();
        }
        List<CouponPureVO> vos = CouponPureVO.getList(coupons);
        return vos;
    }

    @GetMapping("/whole_store")
    public List<CouponPureVO> getWholeStoreCouponList() {
        List<Coupon> coupons = couponService.getWholeStoreCoupons();
        if (coupons.isEmpty()) {
            return Collections.emptyList();
        }
        return CouponPureVO.getList(coupons);
    }

    @PostMapping("/collect/{id}")
    public UnifyResult collectCoupon(@PathVariable Long id, HttpServletRequest request) {
        WxUser wxUser = wxUserResolve.resolveWxUser(request);
        couponService.collectOneCoupon(wxUser.getId(), id);
        return UnifyResult.ok();
    }

    @GetMapping("/myself/by/status/{status}")
    public UnifyResult getMyCouponByStatus(@PathVariable Integer status, HttpServletRequest request) {
        WxUser wxUser = wxUserResolve.resolveWxUser(request);
        List<Coupon> couponList;
        //触发机制 时机 过期
        switch (CouponStatus.toType(status)) {
            case AVAILABLE:
                couponList = couponService.getMyAvailableCoupons(wxUser.getId());
                break;
            case USED:
                couponList = couponService.getMyUsedCoupons(wxUser.getId());
                break;
            case EXPIRED:
                couponList = couponService.getMyExpiredCoupons(wxUser.getId());
                break;
            default:
                throw new ParameterException(40001);
        }

        List<CouponPureVO> result = CouponPureVO.getList(couponList);
        return UnifyResult.ok().data("data", result);
    }

    @GetMapping("/myself/available/with_category")
    public List<CouponBrandVO> getUserCouponWithCategory(HttpServletRequest request) {
        WxUser wxUser = wxUserResolve.resolveWxUser(request);

        List<Coupon> coupons = couponService.getMyAvailableCoupons(wxUser.getId());
        if (coupons.isEmpty()) {
            return Collections.emptyList();
        }
        return coupons.stream().map(coupon -> {
            CouponBrandVO vo = new CouponBrandVO(coupon);
            return vo;
        }).collect(Collectors.toList());
    }
}
