package com.lweishi.wx.auth.service;

import com.lweishi.model.*;
import com.lweishi.service.*;
import com.lweishi.wx.auth.vo.ProductInfoVO;
import com.lweishi.wx.auth.vo.ProductVO;
import com.lweishi.wx.auth.vo.SecondFaultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName WxProductService
 * @Description 产品业务
 * @Author zzm
 * @Data 2020/8/28 9:44
 * @Version 1.0
 */
@Service
@Transactional
public class WxProductService {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductFaultService productFaultService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private FirstFaultService firstFaultService;

    @Autowired
    private SecondFaultService secondFaultService;

    public ProductInfoVO findProductInfo(String id) {
        ProductInfoVO info = new ProductInfoVO();

        //查询产品基本信息
        ProductVO productVO = new ProductVO();
        Product product = productService.findById(id);

        productVO.setId(product.getId());
        productVO.setBrand(product.getBrandName());
        productVO.setCover(product.getImage());
        productVO.setName(product.getName());
        List<Color> colors = colorService.findByIdIn(product.getColorIds());
        productVO.setColors(colors);

        //查询产品故障信息
        List<ProductFault> faultList = productFaultService.findByProductId(id);
        //查询一级故障信息
        List<String> firstFaultIds = faultList.stream().map(ProductFault::getFirstFaultId).distinct().collect(Collectors.toList());
        List<FirstFault> firstFaults = firstFaultService.findByIds(firstFaultIds);

        //查询二级故障信息
        List<SecondFaultVO> secondFaults = faultList.stream().map(p -> {
            SecondFaultVO secondFaultVO = new SecondFaultVO();
            SecondFault secondFault = secondFaultService.findById(p.getSecondFaultId());
            BeanUtils.copyProperties(secondFault, secondFaultVO);
            secondFaultVO.setId(p.getId());
            secondFaultVO.setSecondFaultId(secondFault.getId());
            secondFaultVO.setPrice(p.getPrice());
            return secondFaultVO;
        }).collect(Collectors.toList());

        info.setRoots(firstFaults);
        info.setSubs(secondFaults);
        info.setProduct(productVO);

        return info;
    }
}
