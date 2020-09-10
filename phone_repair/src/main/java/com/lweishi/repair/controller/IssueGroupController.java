package com.lweishi.repair.controller;

import com.lweishi.dto.IssueGroupDTO;
import com.lweishi.model.IssueGroup;
import com.lweishi.service.IssueGroupService;
import com.lweishi.utils.UnifyResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author geek
 * @CreateTime 2020/8/7 23:21
 * @Description 问题组控制器
 */
@Slf4j
@RestController
@RequestMapping("/group")
public class IssueGroupController {

    @Autowired
    private IssueGroupService issueGroupService;

    @GetMapping("/all")
    public UnifyResult findAll() {
        List<IssueGroup> groupList = issueGroupService.findAll();
        return UnifyResult.ok().data("groupList", groupList);
    }

    @GetMapping("/page")
    public UnifyResult findAll(@PageableDefault(page = 1, size = 10, direction = Sort.Direction.DESC, sort = "createTime") Pageable pageable,
                               @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        Page<IssueGroup> pageData = issueGroupService.findAll(pageRequest, keyword);
        return UnifyResult.ok().data("pageData", pageData);
    }

    @DeleteMapping("/{id}")
    public UnifyResult deleteById(@PathVariable String id) {
        issueGroupService.deleteById(id);
        return UnifyResult.ok();
    }

    @PostMapping("/save")
    public UnifyResult save(@Validated(IssueGroupDTO.Save.class) @RequestBody IssueGroupDTO issueGroupDTO) {
        IssueGroup issueGroup = issueGroupService.save(issueGroupDTO);
        return UnifyResult.ok().data("issueGroup", issueGroup);
    }

    @PutMapping("/update")
    public UnifyResult update(@Validated(IssueGroupDTO.Update.class) @RequestBody IssueGroupDTO issueGroupDTO) {
        IssueGroup issueGroup = issueGroupService.update(issueGroupDTO);
        return UnifyResult.ok().data("issueGroup", issueGroup);
    }

    @GetMapping("/{id}")
    public UnifyResult findById(@PathVariable String id) {
        IssueGroup issueGroup = issueGroupService.findById(id);
        return UnifyResult.ok().data("issueGroup", issueGroup);
    }
}
