package com.lweishi.service;

import com.lweishi.dto.IssueGroupDTO;
import com.lweishi.exception.GlobalException;
import com.lweishi.model.IssueGroup;
import com.lweishi.repository.IssueGroupRepository;
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
 * @ClassName IssueGroupService
 * @Description 问题组
 * @Author zzm
 * @Data 2020/9/9 17:06
 * @Version 1.0
 */
@Service
@Transactional
public class IssueGroupService {

    @Autowired
    private IssueGroupRepository issueGroupRepository;

    public List<IssueGroup> findAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        return issueGroupRepository.findAll(sort);
    }

    public Page<IssueGroup> findAll(Pageable pageable, String keyword) {
        if (StringUtils.isBlank(keyword)) {
            return issueGroupRepository.findAll(pageable);
        }
        return issueGroupRepository.findByNameLike("%" + keyword + "%", pageable);
    }

    public IssueGroup findById(String id) {
        return issueGroupRepository.findById(id).orElseThrow(() -> new GlobalException(ResultCode.ERROR, "该问题组信息不存在"));
    }

    public void deleteById(String id) {
        issueGroupRepository.deleteById(id);
    }

    public IssueGroup save(IssueGroupDTO issueGroupDTO) {
        IssueGroup issueGroup = new IssueGroup();
        BeanUtils.copyProperties(issueGroupDTO, issueGroup);
        issueGroup.setId(IDUtil.UUID());
        issueGroup.setCreateTime(LocalDateTime.now());
        return issueGroupRepository.save(issueGroup);
    }

    public IssueGroup update(IssueGroupDTO issueGroupDTO) {
        if (StringUtils.isBlank(issueGroupDTO.getId())) {
            throw new GlobalException(ResultCode.ERROR, "更新问题组信息失败");
        }
        IssueGroup issueGroup = findById(issueGroupDTO.getId());
        BeanUtils.copyProperties(issueGroupDTO, issueGroup, BeanNullUtil.getNullPropertyNames(issueGroupDTO));
        return issueGroupRepository.save(issueGroup);
    }

}
