package com.lweishi.service;

import com.lweishi.dto.IssueDTO;
import com.lweishi.exception.GlobalException;
import com.lweishi.model.Issue;
import com.lweishi.model.IssueGroup;
import com.lweishi.repository.IssueRepository;
import com.lweishi.utils.BeanNullUtil;
import com.lweishi.utils.IDUtil;
import com.lweishi.utils.ResultCode;
import com.lweishi.vo.IssueGroupVO;
import com.lweishi.vo.IssueVO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName IssueService
 * @Description 问题
 * @Author zzm
 * @Data 2020/9/9 17:06
 * @Version 1.0
 */
@Service
@Transactional
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private IssueGroupService issueGroupService;

    public List<Issue> findAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        return issueRepository.findAll(sort);
    }

    public Page<Issue> findAll(Pageable pageable, String keyword, String groupId) {
        Specification<Issue> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (StringUtils.isNotBlank(keyword)) {
                predicate = cb.like(root.get("title"), "%" + keyword + "%");
            }

            if (StringUtils.isNotBlank(groupId)) {
                predicate = cb.and(predicate, cb.equal(root.get("groupId"), groupId));
            }
            return predicate;
        };
        return issueRepository.findAll(specification, pageable);
    }

    public Issue findById(String id) {
        return issueRepository.findById(id).orElseThrow(() -> new GlobalException(ResultCode.ERROR, "该问题信息不存在"));
    }

    public void deleteById(String id) {
        issueRepository.deleteById(id);
    }

    public Issue save(IssueDTO issueDTO) {
        Issue issue = new Issue();
        BeanUtils.copyProperties(issueDTO, issue);

        if (StringUtils.equals("0", issueDTO.getGroupId())) {
            issue.setGroupName("其他问题");
        } else {
            IssueGroup group = issueGroupService.findById(issueDTO.getGroupId());
            issue.setGroupName(group.getName());
        }


        issue.setId(IDUtil.UUID());
        issue.setCreateTime(LocalDateTime.now());
        return issueRepository.save(issue);
    }

    public Issue update(IssueDTO issueDTO) {
        if (StringUtils.isBlank(issueDTO.getId())) {
            throw new GlobalException(ResultCode.ERROR, "更新问题信息失败");
        }
        Issue issue = findById(issueDTO.getId());
        BeanUtils.copyProperties(issueDTO, issue, BeanNullUtil.getNullPropertyNames(issueDTO));

        if (StringUtils.equals("0", issueDTO.getGroupId())) {
            issue.setGroupName("其他问题");
        } else {
            IssueGroup group = issueGroupService.findById(issueDTO.getGroupId());
            issue.setGroupName(group.getName());
        }

        return issueRepository.save(issue);
    }

    public IssueVO findTree() {
        List<Issue> issues = findAll();
        List<Issue> otherList = issues.stream().filter(issue -> StringUtils.equals("0", issue.getGroupId())).collect(Collectors.toList());
        Map<String, List<Issue>> issueMap = issues.stream().filter(issue -> !StringUtils.equals("0", issue.getGroupId())).collect(
                Collectors.groupingBy(Issue::getGroupId)
        );
        List<IssueGroupVO> groupVOS = new ArrayList<>();
        issueMap.forEach((k, v) -> {
            IssueGroup group = issueGroupService.findById(k);
            IssueGroupVO groupVO = new IssueGroupVO(k, group.getName(), group.getIcon(), v);
            groupVOS.add(groupVO);
        });
        return new IssueVO(otherList, groupVOS);
    }
}
