package com.lweishi.repair.controller;

import com.lweishi.dto.ProductDTO;
import com.lweishi.dto.ProductFaultDTO;
import com.lweishi.model.Product;
import com.lweishi.service.ProductFaultService;
import com.lweishi.service.ProductService;
import com.lweishi.utils.UnifyResult;
import com.lweishi.vo.ProductFaultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author geek
 * @CreateTime 2020/8/7 23:21
 * @Description 产品控制器
 */
@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductFaultService productFaultService;

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
    public UnifyResult save(@Valid @RequestBody ProductDTO productDTO) {
        log.info("【productDTO】 = {}", productDTO);
        Product product = productService.save(productDTO);
        return UnifyResult.ok().data("product", product);
    }

    @PutMapping("/update")
    public UnifyResult update(@Valid @RequestBody ProductDTO productDTO) {
        Product product = productService.update(productDTO);
        return UnifyResult.ok().data("product", product);
    }

    @GetMapping("/{id}")
    public UnifyResult findById(@PathVariable String id) {
        Product product = productService.findById(id);
        return UnifyResult.ok().data("product", product);
    }

    @GetMapping("/change/{id}/status")
    public UnifyResult changeStatus(@PathVariable String id, @RequestParam Boolean status) {
        productService.changeStatus(id, status);
        return UnifyResult.ok();
    }

    @GetMapping("/{id}/fault")
    public UnifyResult findFaultInfo(@PathVariable String id) {
        Map<String, Object> faultInfoMap = productService.findFaultInfo(id);
        List<ProductFaultVO> faultInfo = (List<ProductFaultVO>) faultInfoMap.get("result");
        List<String> secondFaultIds = (List<String>) faultInfoMap.get("secondFaultIds");
        return UnifyResult.ok().data("faultInfo", faultInfo).data("secondFaultIds", secondFaultIds);
    }

    @PostMapping("/add/fault")
    public UnifyResult addProductFault(@Valid @RequestBody List<ProductFaultDTO> productFaultDTOList) {
        productFaultService.saveAll(productFaultDTOList);
        return UnifyResult.ok();
    }

    /**
     * 更新产品故障的价格
     * @return
     */
    @GetMapping("/update/fault/price")
    public UnifyResult updateProductFaultPrice(@RequestParam String productFaultId, @RequestParam String price) {
        productFaultService.updatePrice(productFaultId, price);
        return UnifyResult.ok();
    }

    /**
     * 删除产品里的故障
     * @param id 产品故障ID
     * @return
     */
    @DeleteMapping("/fault/{id}")
    public UnifyResult deleteProductFault(@PathVariable String id) {
        productFaultService.deleteById(id);
        return UnifyResult.ok();
    }
}
