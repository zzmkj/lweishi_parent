package com.lweishi.repair.controller;

import com.lweishi.dto.ProductDTO;
import com.lweishi.model.Product;
import com.lweishi.service.ProductService;
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
 * @Description 产品控制器
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public UnifyResult findAll() {
        List<Product> productList = productService.findAll();
        return UnifyResult.ok().data("productList", productList);
    }

    @GetMapping("/page")
    public UnifyResult findAll(@PageableDefault(page = 1, size = 10, direction = Sort.Direction.ASC, sort = "sequence") Pageable pageable,
                               @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        Page<Product> pageData = productService.findAll(pageRequest, keyword);
        return UnifyResult.ok().data("pageData", pageData);
    }

    @DeleteMapping("/{id}")
    public UnifyResult deleteById(@PathVariable String id) {
        productService.deleteById(id);
        return UnifyResult.ok();
    }

    @PostMapping("/save")
    public UnifyResult save(@Validated @RequestBody  ProductDTO productDTO) {
        Product product = productService.save(productDTO);
        return UnifyResult.ok().data("product", product);
    }

    @PutMapping("/update")
    public UnifyResult update(@Validated @RequestBody ProductDTO productDTO) {
        Product product = productService.update(productDTO);
        return UnifyResult.ok().data("product", product);
    }

    @GetMapping("/{id}")
    public UnifyResult findById(@PathVariable String id) {
        Product product = productService.findById(id);
        return UnifyResult.ok().data("product", product);
    }

}
