package com.lweishi.service;

import com.lweishi.dto.ProductFaultDTO;
import com.lweishi.exception.GlobalException;
import com.lweishi.model.ProductFault;
import com.lweishi.repository.ProductFaultRepository;
import com.lweishi.utils.BeanNullUtil;
import com.lweishi.utils.IDUtil;
import com.lweishi.utils.ResultCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author geek
 * @CreateTime 2020/8/23 23:24
 * @Description 产品和故障实体的中间表
 */
@Service
@Transactional
public class ProductFaultService {

    @Autowired
    private ProductFaultRepository productFaultRepository;
    
    public List<ProductFault> findAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "sequence");
        return productFaultRepository.findAll(sort);
    }


    public ProductFault findById(String id) {
        return productFaultRepository.findById(id).orElseThrow(() -> new GlobalException(ResultCode.ERROR, "该产品的故障信息不存在"));
    }

    public void deleteById(String id) {
        productFaultRepository.deleteById(id);
    }

    public ProductFault save(ProductFaultDTO productFaultDTO) {
        ProductFault productFault = new ProductFault();
        BeanUtils.copyProperties(productFaultDTO, productFault);
        productFault.setId(IDUtil.UUID());
        productFault.setCreateTime(LocalDateTime.now());
        return productFaultRepository.save(productFault);
    }

    public void saveAll(List<ProductFaultDTO> list) {
        list.forEach(this::save);
    }

    public ProductFault update(ProductFaultDTO productFaultDTO) {
        if (StringUtils.isBlank(productFaultDTO.getId())) {
            throw new GlobalException(ResultCode.ERROR, "更新产品故障信息失败");
        }
        ProductFault productFault = findById(productFaultDTO.getId());
        BeanUtils.copyProperties(productFaultDTO, productFault, BeanNullUtil.getNullPropertyNames(productFaultDTO));
        return productFaultRepository.save(productFault);
    }

    public List<ProductFault> findByProductId(String productId) {
        return productFaultRepository.findByProductId(productId);
    }

    public void updatePrice(String id, String price) {
        ProductFault productFault = findById(id);
        productFault.setPrice(new BigDecimal(price));
        productFaultRepository.save(productFault);
    }

    public List<ProductFault> findByIds(List<String> ids) {
        return productFaultRepository.findByIdIn(ids);
    }

    public void deleteByProductId(String productId) {
        productFaultRepository.deleteByProductId(productId);
    }
}
