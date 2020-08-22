package com.lweishi.service;

import com.lweishi.constant.Constant;
import com.lweishi.dto.ProductDTO;
import com.lweishi.exception.GlobalException;
import com.lweishi.model.Brand;
import com.lweishi.model.Product;
import com.lweishi.repository.ProductRepository;
import com.lweishi.utils.BeanNullUtil;
import com.lweishi.utils.IDUtil;
import com.lweishi.utils.ResultCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author geek
 * @CreateTime 2020/8/7 22:49
 * @Description 产品业务
 */
@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandService brandService;

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
        productRepository.deleteById(id);
    }

    public Product save(ProductDTO productDTO) {
        Product product = new Product();
        String brandId = productDTO.getBrandId();
        Brand brand = brandService.findById(brandId);

        BeanUtils.copyProperties(productDTO, product);
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
}
