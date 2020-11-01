package com.lweishi.service;

import com.lweishi.constant.Constant;
import com.lweishi.dto.TagDTO;
import com.lweishi.exception.GlobalException;
import com.lweishi.model.Tag;
import com.lweishi.repository.TagRepository;
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
 * @Description 热门关键词业务
 */
@Service
@Transactional
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public List<Tag> findAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "sequence");
        return tagRepository.findAll(sort);
    }

    public Page<Tag> findAll(Pageable pageable, String keyword) {
        if (StringUtils.isBlank(keyword)) {
            return tagRepository.findAll(pageable);
        }
        return tagRepository.findByTitleLike("%" + keyword + "%", pageable);
    }

    public Tag findById(String id) {
        return tagRepository.findById(id).orElseThrow(() -> new GlobalException(ResultCode.ERROR, "该标签信息不存在"));
    }

    public void deleteById(String id) {
        tagRepository.deleteById(id);
    }

    public Tag save(TagDTO TagDTO) {
        Tag Tag = new Tag();
        BeanUtils.copyProperties(TagDTO, Tag);
        Tag.setId(IDUtil.UUID());
        Tag.setCreateTime(LocalDateTime.now());
        Tag.setHighlight(Constant.TAG_NONE);
        return tagRepository.save(Tag);
    }

    public Tag update(TagDTO TagDTO) {
        if (StringUtils.isBlank(TagDTO.getId())) {
            throw new GlobalException(ResultCode.ERROR, "更新标签信息失败");
        }
        Tag Tag = findById(TagDTO.getId());
        BeanUtils.copyProperties(TagDTO, Tag, BeanNullUtil.getNullPropertyNames(TagDTO));
        return tagRepository.save(Tag);
    }

    public Tag changeHighlight(String id, Integer highlight) {
        Tag tag = findById(id);
        tag.setHighlight(highlight);
        return tagRepository.save(tag);
    }
}
