package com.lweishi.wx.auth.vo;

import com.lweishi.model.Brand;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class BrandPureVO {
    private String id;

    private String name;

    private Integer sequence;

    private String image;

    private Boolean status;

    public BrandPureVO(Brand brand) {
        BeanUtils.copyProperties(brand, this);
    }
}
