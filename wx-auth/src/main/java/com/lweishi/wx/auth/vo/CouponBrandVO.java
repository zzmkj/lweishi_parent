package com.lweishi.wx.auth.vo;

import com.lweishi.model.Brand;
import com.lweishi.model.Coupon;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CouponBrandVO extends CouponPureVO {
    private List<BrandPureVO> brands = new ArrayList<>();

    public CouponBrandVO(Coupon coupon) {
        super(coupon);
        List<Brand> brands = coupon.getBrandList();
        brands.forEach(brand -> {
            BrandPureVO vo = new BrandPureVO(brand);
            this.brands.add(vo);
        });
    }
}
