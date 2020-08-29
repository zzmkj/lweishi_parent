package com.lweishi.wx.auth.controller;

import com.lweishi.model.Banner;
import com.lweishi.model.Brand;
import com.lweishi.model.Product;
import com.lweishi.service.BannerService;
import com.lweishi.service.BrandService;
import com.lweishi.service.ProductService;
import com.lweishi.utils.UnifyResult;
import com.lweishi.wx.auth.service.WxProductService;
import com.lweishi.wx.auth.vo.CategoryVO;
import com.lweishi.wx.auth.vo.ProductInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName WxBannerController
 * @Description TODO
 * @Author zzm
 * @Data 2020/8/23 15:29
 * @Version 1.0
 */
@RestController
@RequestMapping("/wx")
public class WxIndexController {

    @Autowired
    private BannerService bannerService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private ProductService productService;

    @Autowired
    private WxProductService wxProductService;

    @GetMapping("/banner")
    public UnifyResult findBanner() {
        List<Banner> bannerList = bannerService.findAllValid();
        return UnifyResult.error().data("bannerList", bannerList);
    }

    @GetMapping("/category")
    public UnifyResult findCategory() {
        List<Brand> brands = brandService.findAllValid();
        List<Product> products = productService.findAllValid();
        CategoryVO categories = new CategoryVO(brands, products);
        return UnifyResult.ok().data("categories", categories);
    }

    @GetMapping("/product/{id}/info")
    public UnifyResult findProductInfo(@PathVariable String id) {
        ProductInfoVO productInfo = wxProductService.findProductInfo(id);
        return UnifyResult.ok().data("info", productInfo);
    }
}
