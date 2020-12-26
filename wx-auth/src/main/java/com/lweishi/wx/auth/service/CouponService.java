package com.lweishi.wx.auth.service;

import com.lweishi.enums.CouponStatus;
import com.lweishi.exception.http.NotFoundException;
import com.lweishi.exception.http.ParameterException;
import com.lweishi.model.Activity;
import com.lweishi.model.Coupon;
import com.lweishi.model.UserCoupon;
import com.lweishi.repository.ActivityRepository;
import com.lweishi.repository.CouponRepository;
import com.lweishi.repository.UserCouponRepository;
import com.lweishi.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    public List<Coupon> getByCategory(String cid) {
        Date now = new Date();
        return couponRepository.findByCategory(cid, now);
    }

    public List<Coupon> getWholeStoreCoupons() {
        Date now = new Date();
        return couponRepository.findByWholeStore(true, now);
    }

    public List<Coupon> getMyAvailableCoupons(String uid) {
        Date now = new Date();
        return this.couponRepository.findMyAvailable(uid, now);
    }

    public List<Coupon> getMyUsedCoupons(String uid) {
        Date now = new Date();
        return this.couponRepository.findMyUsed(uid, now);
    }

    public List<Coupon> getMyExpiredCoupons(String uid) {
        Date now = new Date();
        return this.couponRepository.findMyExpired(uid, now);
    }

    public void collectOneCoupon(String uid, Long couponId) {
        couponRepository.findById(couponId).orElseThrow(() -> new NotFoundException(40003));

        Activity activity = activityRepository.findByCouponListId(couponId).orElseThrow(() -> new NotFoundException(40010));
        Date now = new Date();
        Boolean isIn = CommonUtil.isInTimeLine(now, activity.getStartTime(), activity.getEndTime());
        if (!isIn) {
            throw new ParameterException(40005);
        }

        userCouponRepository.findByUserIdAndCouponId(uid, couponId).ifPresent(uc -> {throw new ParameterException(40006);});

        UserCoupon userCouponNew = UserCoupon.builder()
                .userId(uid)
                .couponId(couponId)
                .status(CouponStatus.AVAILABLE.getValue())
                .createTime(now)
                .build();

        userCouponRepository.save(userCouponNew);
    }
}
