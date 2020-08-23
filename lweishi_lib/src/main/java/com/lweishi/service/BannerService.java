package com.lweishi.service;

import com.lweishi.constant.Constant;
import com.lweishi.dto.BannerDTO;
import com.lweishi.exception.GlobalException;
import com.lweishi.model.Banner;
import com.lweishi.repository.BannerRepository;
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
 * @Description 轮播图业务
 */
@Service
@Transactional
public class BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    public List<Banner> findAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "sequence");
        return bannerRepository.findAll(sort);
    }

    public Page<Banner> findAll(Pageable pageable, String keyword) {
        if (StringUtils.isBlank(keyword)) {
            return bannerRepository.findAll(pageable);
        }
        return bannerRepository.findByNameLike("%" + keyword + "%", pageable);
    }

    public Banner findById(String id) {
        return bannerRepository.findById(id).orElseThrow(() -> new GlobalException(ResultCode.ERROR, "该轮播图信息不存在"));
    }

    public void deleteById(String id) {
        bannerRepository.deleteById(id);
    }

    public Banner save(BannerDTO bannerDTO) {
        Banner banner = new Banner();
        BeanUtils.copyProperties(bannerDTO, banner);
        banner.setId(IDUtil.UUID());
        banner.setStatus(Constant.BANNER_VALID);
        banner.setCreateTime(LocalDateTime.now());
        return bannerRepository.save(banner);
    }

    public Banner update(BannerDTO bannerDTO) {
        if (StringUtils.isBlank(bannerDTO.getId())) {
            throw new GlobalException(ResultCode.ERROR, "更新轮播图信息失败");
        }
        Banner banner = findById(bannerDTO.getId());
        BeanUtils.copyProperties(bannerDTO, banner, BeanNullUtil.getNullPropertyNames(bannerDTO));
        return bannerRepository.save(banner);
    }

    public void changeStatus(String id, Boolean status) {
        Banner banner = findById(id);
        banner.setStatus(status);
        bannerRepository.save(banner);
    }

    public List<Banner> findAllValid() {
        Sort sort = Sort.by(Sort.Direction.ASC, "sequence");
        return bannerRepository.findByStatus(Constant.BANNER_VALID, sort);
    }
}
