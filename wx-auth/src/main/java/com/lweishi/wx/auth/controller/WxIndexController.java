package com.lweishi.wx.auth.controller;

import com.lweishi.model.*;
import com.lweishi.service.*;
import com.lweishi.utils.UnifyResult;
import com.lweishi.vo.IssueVO;
import com.lweishi.vo.SearchVO;
import com.lweishi.wx.auth.service.WxProductService;
import com.lweishi.wx.auth.vo.CategoryVO;
import com.lweishi.wx.auth.vo.ProductInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName WxIndexController
 * @Description 微信页面控制器
 * @Author zzm
 * @Data 2020/8/23 15:29
 * @Version 1.0
 */
@RestController
public class WxIndexController {

    @Autowired
    private BannerService bannerService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private ProductService productService;

    @Autowired
    private WxProductService wxProductService;

    @Autowired
    private IssueService issueService;

    @Autowired
    private TagService tagService;

    @GetMapping("/banner")
    public UnifyResult findBanner() {
        List<Banner> bannerList = bannerService.findAllValid();
        return UnifyResult.ok().data("bannerList", bannerList);
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

    @GetMapping("/issue")
    public UnifyResult findIssue() {
        IssueVO result = issueService.findTree();
        return UnifyResult.ok().data("result", result);
    }

    @GetMapping("/issue/{id}")
    public UnifyResult findIssueDetail(@PathVariable String id) {
        Issue issue = issueService.findById(id);
        return UnifyResult.ok().data("result", issue);
    }

    @GetMapping("/tag")
    public UnifyResult findTag() {
        List<Tag> tagList = tagService.findAll();
        return UnifyResult.ok().data("tagList", tagList);
    }

    @GetMapping("/search")
    public UnifyResult productSearch(@RequestParam String keyword,
                                     @RequestParam(name = "start", required = false, defaultValue = "0") Integer start,
                                     @RequestParam(name = "count", required = false, defaultValue = "10") Integer count) {
        SearchVO searchVO = productService.search(keyword, start, count);
        return UnifyResult.ok().data("result", searchVO);
    }
}
