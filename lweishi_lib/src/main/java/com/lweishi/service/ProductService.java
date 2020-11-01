package com.lweishi.service;

import com.google.common.collect.Lists;
import com.lweishi.constant.Constant;
import com.lweishi.dto.ProductDTO;
import com.lweishi.exception.GlobalException;
import com.lweishi.model.*;
import com.lweishi.repository.ProductRepository;
import com.lweishi.utils.BeanNullUtil;
import com.lweishi.utils.IDUtil;
import com.lweishi.utils.ResultCode;
import com.lweishi.vo.ProductFaultItemVO;
import com.lweishi.vo.ProductFaultVO;
import com.lweishi.vo.SearchItemVO;
import com.lweishi.vo.SearchVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author geek
 * @CreateTime 2020/8/7 22:49
 * @Description 产品业务
 */
@Slf4j
@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandService brandService;

    @Autowired
    private ProductFaultService productFaultService;

    @Autowired
    private FirstFaultService firstFaultService;

    @Autowired
    private SecondFaultService secondFaultService;

    public List<Product> findAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "sequence");
        return productRepository.findAll(sort);
    }

    public Page<Product> findAll(Pageable pageable, String keyword) {
        Specification<Product> specification =  (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (StringUtils.isNotBlank(keyword)) {
                String key = "%" + keyword + "%";
                predicate = cb.or(cb.like(root.get("name"), key), cb.like(root.get("brandName"), key));
            }
            return predicate;
        };
        return productRepository.findAll(specification, pageable);
    }

    public Product findById(String id) {
        return productRepository.findById(id).orElseThrow(() -> new GlobalException(ResultCode.ERROR, "该产品信息不存在"));
    }

    public void deleteById(String id) {
        //删除产品故障信息
        productFaultService.deleteByProductId(id);
        productRepository.deleteById(id);
    }

    public Product save(ProductDTO productDTO) {
        Product product = new Product();

        BeanUtils.copyProperties(productDTO, product);
        //查询品牌
        String brandId = productDTO.getBrandId();
        Brand brand = brandService.findById(brandId);

        product.setId(IDUtil.UUID());
        product.setBrandName(brand.getName());
        product.setStatus(Constant.BANNER_VALID);
        product.setCreateTime(LocalDateTime.now());
        return productRepository.save(product);
    }

    public Product update(ProductDTO productDTO) {
        if (StringUtils.isBlank(productDTO.getId())) {
            throw new GlobalException(ResultCode.ERROR, "更新产品信息失败");
        }
        Product product = findById(productDTO.getId());

        if (!StringUtils.equals(productDTO.getBrandId(), product.getBrandId())) {
            Brand brand = brandService.findById(productDTO.getBrandId());
            product.setBrandName(brand.getName());
        }
        BeanUtils.copyProperties(productDTO, product, BeanNullUtil.getNullPropertyNames(productDTO));
        return productRepository.save(product);
    }

    public void changeStatus(String id, Boolean status) {
        Product product = findById(id);
        product.setStatus(status);
        productRepository.save(product);
    }

    public List<Product> findAllValid() {
        Sort sort = Sort.by(Sort.Direction.ASC, "sequence");
        return productRepository.findByStatus(Constant.PRODUCT_VALID, sort);
    }

    /**
     * 查询产品的故障信息
     * @param id 产品id
     * @return
     */
    public Map<String, Object> findFaultInfo(String id) {
        List<ProductFault> productFaults = productFaultService.findByProductId(id);
        List<String> secondFaultIds = productFaults.stream().map(ProductFault::getSecondFaultId).collect(Collectors.toList());

        Map<String, List<ProductFaultItemVO>> faultsMap = productFaults.stream().map(productFault -> {
            SecondFault secondFault = secondFaultService.findById(productFault.getSecondFaultId());
            ProductFaultItemVO itemVO = new ProductFaultItemVO(productFault.getId(), secondFault.getFaultId(), secondFault.getId(), secondFault.getName(), productFault.getPrice());
            return itemVO;
        }).collect(Collectors.toMap(ProductFaultItemVO::getFirstFaultId, Lists::newArrayList, (List<ProductFaultItemVO> newValueList, List<ProductFaultItemVO> oldValueList) -> {
            oldValueList.addAll(newValueList);
            return oldValueList;
        }));
        List<String> firstFaultIds = productFaults.stream().map(ProductFault::getFirstFaultId).distinct().collect(Collectors.toList());
        List<FirstFault> firstFaults = firstFaultService.findByIds(firstFaultIds);
        List<ProductFaultVO> result = firstFaults.stream().map(firstFault -> {
            ProductFaultVO productFaultVO = new ProductFaultVO();
            productFaultVO.setFirstFaultId(firstFault.getId());
            productFaultVO.setFirstFaultName(firstFault.getName());
            productFaultVO.setChildren(faultsMap.get(firstFault.getId()));
            return productFaultVO;
        }).collect(Collectors.toList());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", result);
        resultMap.put("secondFaultIds", secondFaultIds);
        return resultMap;
    }

    /**
     * 客户端（微信小程序）搜索产品
     * @return
     */
    public SearchVO search(String keyword, Integer start, Integer count) {
        PageRequest pageRequest = PageRequest.of(start, count, Sort.by(Sort.Direction.ASC, "sequence"));
        Page<SearchItemVO> pageResult = productRepository.findByNameLike("%" + keyword + "%", pageRequest);
        SearchVO searchVO = SearchVO.builder()
                                    .total(pageResult.getTotalElements())
                                    .count(pageResult.getSize())
                                    .page(pageResult.getNumber())
                                    .total_page(pageResult.getTotalPages())
                                    .items(pageResult.getContent()).build();
        return searchVO;
    }
}
