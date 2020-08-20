package com.lweishi.repair.controller;

import com.lweishi.domain.Brand;
import com.lweishi.dto.BrandDTO;
import com.lweishi.service.BrandService;
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
 * @Description 品牌控制器
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/all")
    public UnifyResult findAll() {
        List<Brand> brandList = brandService.findAll();
        return UnifyResult.ok().data("brandList", brandList);
    }

    @GetMapping("/page")
    public UnifyResult findAll(@PageableDefault(page = 1, size = 10, direction = Sort.Direction.ASC, sort = "sequence") Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        Page<Brand> pageData = brandService.findAll(pageRequest);
        return UnifyResult.ok().data("pageData", pageData);
    }

    @DeleteMapping("/{id}")
    public UnifyResult deleteById(@PathVariable String id) {
        brandService.deleteById(id);
        return UnifyResult.ok();
    }

    @PostMapping("/save")
    public UnifyResult save(@Validated @RequestBody  BrandDTO brandDTO) {
        Brand brand = brandService.save(brandDTO);
        return UnifyResult.ok().data("brand", brand);
    }

    @PutMapping("/update")
    public UnifyResult update(@Validated @RequestBody BrandDTO brandDTO) {
        Brand brand = brandService.update(brandDTO);
        return UnifyResult.ok().data("brand", brand);
    }

    @GetMapping("/{id}")
    public UnifyResult findById(@PathVariable String id) {
        Brand brand = brandService.findById(id);
        return UnifyResult.ok().data("brand", brand);
    }

}
