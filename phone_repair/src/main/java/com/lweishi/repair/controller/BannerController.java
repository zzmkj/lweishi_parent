package com.lweishi.repair.controller;

import com.lweishi.dto.BannerDTO;
import com.lweishi.model.Banner;
import com.lweishi.service.BannerService;
import com.lweishi.service.BannerService;
import com.lweishi.utils.UnifyResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author geek
 * @CreateTime 2020/8/7 23:21
 * @Description 轮播图控制器
 */
@RestController
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @GetMapping("/all")
    public UnifyResult findAll() {
        List<Banner> brandList = bannerService.findAll();
        return UnifyResult.ok().data("brandList", brandList);
    }

    @GetMapping("/page")
    public UnifyResult findAll(@PageableDefault(page = 1, size = 10, direction = Sort.Direction.ASC, sort = "sequence") Pageable pageable,
                               @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        Page<Banner> pageData = bannerService.findAll(pageRequest, keyword);
        return UnifyResult.ok().data("pageData", pageData);
    }

    @DeleteMapping("/{id}")
    public UnifyResult deleteById(@PathVariable String id) {
        bannerService.deleteById(id);
        return UnifyResult.ok();
    }

    @PostMapping("/save")
    public UnifyResult save(@Validated @RequestBody  BannerDTO bannerDTO) {
        Banner banner = bannerService.save(bannerDTO);
        return UnifyResult.ok().data("banner", banner);
    }

    @PutMapping("/update")
    public UnifyResult update(@Validated @RequestBody BannerDTO bannerDTO) {
        Banner banner = bannerService.update(bannerDTO);
        return UnifyResult.ok().data("banner", banner);
    }

    @GetMapping("/{id}")
    public UnifyResult findById(@PathVariable String id) {
        Banner banner = bannerService.findById(id);
        return UnifyResult.ok().data("banner", banner);
    }

    @GetMapping("/change/{id}/status")
    public UnifyResult changeStatus(@PathVariable String id, @RequestParam Boolean status) {
        bannerService.changeStatus(id, status);
        return UnifyResult.ok();
    }

}
