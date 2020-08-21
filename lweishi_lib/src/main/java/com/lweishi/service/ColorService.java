package com.lweishi.service;

import com.lweishi.model.Color;
import com.lweishi.dto.ColorDTO;
import com.lweishi.exception.GlobalException;
import com.lweishi.repository.ColorRepository;
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
 * @Description 颜色业务
 */
@Service
@Transactional
public class ColorService {

    @Autowired
    private ColorRepository colorRepository;

    public List<Color> findAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        return colorRepository.findAll(sort);
    }

    public Page<Color> findAll(Pageable pageable, String keyword) {
        if (StringUtils.isBlank(keyword)) {
            return colorRepository.findAll(pageable);
        }
        return colorRepository.findByNameLike("%" + keyword + "%", pageable);
    }

    public Color findById(String id) {
        return colorRepository.findById(id).orElseThrow(() -> new GlobalException(ResultCode.ERROR, "该颜色信息不存在"));
    }

    public void deleteById(String id) {
        colorRepository.deleteById(id);
    }

    public Color save(ColorDTO colorDTO) {
        Color color = new Color();
        BeanUtils.copyProperties(colorDTO, color);
        color.setId(IDUtil.UUID());
        color.setCreateTime(LocalDateTime.now());
        return colorRepository.save(color);
    }

    public Color update(ColorDTO colorDTO) {
        if (StringUtils.isBlank(colorDTO.getId())) {
            throw new GlobalException(ResultCode.ERROR, "更新颜色信息失败");
        }
        Color color = findById(colorDTO.getId());
        BeanUtils.copyProperties(colorDTO, color, BeanNullUtil.getNullPropertyNames(colorDTO));
        return colorRepository.save(color);
    }

}
