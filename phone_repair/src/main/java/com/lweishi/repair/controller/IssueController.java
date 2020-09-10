package com.lweishi.repair.controller;

import com.lweishi.dto.IssueDTO;
import com.lweishi.model.Issue;
import com.lweishi.service.IssueService;
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
 * @Description 问题控制器
 */
@Slf4j
@RestController
@RequestMapping("/issue")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @GetMapping("/all")
    public UnifyResult findAll() {
        List<Issue> issueList = issueService.findAll();
        return UnifyResult.ok().data("issueList", issueList);
    }

    @GetMapping("/page")
    public UnifyResult findAll(@PageableDefault(page = 1, size = 10, direction = Sort.Direction.DESC, sort = "createTime") Pageable pageable,
                               @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
                               @RequestParam(name = "groupId", required = false, defaultValue = "") String groupId) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        Page<Issue> pageData = issueService.findAll(pageRequest, keyword, groupId);
        return UnifyResult.ok().data("pageData", pageData);
    }

    @DeleteMapping("/{id}")
    public UnifyResult deleteById(@PathVariable String id) {
        issueService.deleteById(id);
        return UnifyResult.ok();
    }

    @PostMapping("/save")
    public UnifyResult save(@Validated(IssueDTO.Save.class) @RequestBody IssueDTO issueDTO) {
        Issue issue = issueService.save(issueDTO);
        return UnifyResult.ok().data("issue", issue);
    }

    @PutMapping("/update")
    public UnifyResult update(@Validated(IssueDTO.Update.class) @RequestBody IssueDTO issueDTO) {
        Issue issue = issueService.update(issueDTO);
        return UnifyResult.ok().data("issue", issue);
    }

    @GetMapping("/{id}")
    public UnifyResult findById(@PathVariable String id) {
        Issue issue = issueService.findById(id);
        return UnifyResult.ok().data("issue", issue);
    }
}
