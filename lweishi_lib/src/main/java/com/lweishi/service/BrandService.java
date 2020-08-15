package com.lweishi.service;

import com.lweishi.domain.Brand;
import com.lweishi.dto.BrandDTO;
import com.lweishi.exception.GlobalException;
import com.lweishi.repository.BrandRepository;
import com.lweishi.utils.BeanNullUtil;
import com.lweishi.utils.IDUtil;
import com.lweishi.utils.ResultCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author geek
 * @CreateTime 2020/8/7 22:49
 * @Description 品牌业务
 */
@Service
@Transactional
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public List<Brand> findAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        return brandRepository.findAll(sort);
    }

    public Page<Brand> findAll(Pageable pageable) {
        return brandRepository.findAll(pageable);
    }

    public Brand findById(String id) {
        return brandRepository.findById(id).orElseThrow(() -> new GlobalException(ResultCode.ERROR, "该品牌信息不存在"));
    }

    public void deleteById(String id) {
        brandRepository.deleteById(id);
    }

    public Brand save(BrandDTO brandDTO) {
        Brand brand = new Brand();
        BeanUtils.copyProperties(brandDTO, brand);
        brand.setId(IDUtil.UUID());
        brand.setCreateTime(LocalDateTime.now());
        return brandRepository.save(brand);
    }

    public Brand update(BrandDTO brandDTO) {
        if (StringUtils.isBlank(brandDTO.getId())) {
            throw new GlobalException(ResultCode.ERROR, "更新品牌信息失败");
        }
        Brand brand = findById(brandDTO.getId());
        BeanUtils.copyProperties(brandDTO, brand, BeanNullUtil.getNullPropertyNames(brandDTO));
        return brandRepository.save(brand);
    }

}
