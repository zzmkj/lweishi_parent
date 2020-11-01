package com.lweishi.repair.controller;

import com.lweishi.dto.TagDTO;
import com.lweishi.model.Tag;
import com.lweishi.service.TagService;
import com.lweishi.utils.UnifyResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author geek
 * @CreateTime 2020/8/7 23:21
 * @Description 热门关键词控制器
 */
@Slf4j
@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/all")
    public UnifyResult findAll() {
        List<Tag> tagList = tagService.findAll();
        return UnifyResult.ok().data("tagList", tagList);
    }

    @GetMapping("/page")
    public UnifyResult findAll(@PageableDefault(page = 1, size = 10, direction = Sort.Direction.ASC, sort = "sequence") Pageable pageable,
                               @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        Page<Tag> pageData = tagService.findAll(pageRequest, keyword);
        return UnifyResult.ok().data("pageData", pageData);
    }

    @DeleteMapping("/{id}")
    public UnifyResult deleteById(@PathVariable String id) {
        tagService.deleteById(id);
        return UnifyResult.ok();
    }

    @PostMapping("/save")
    public UnifyResult save(@Valid @RequestBody TagDTO tagDTO) {
        log.info("【tagDTO】 = {}", tagDTO);
        Tag tag = tagService.save(tagDTO);
        return UnifyResult.ok().data("tag", tag);
    }

    @PutMapping("/update")
    public UnifyResult update(@Valid @RequestBody TagDTO tagDTO) {
        Tag tag = tagService.update(tagDTO);
        return UnifyResult.ok().data("tag", tag);
    }

    @GetMapping("/{id}")
    public UnifyResult findById(@PathVariable String id) {
        Tag tag = tagService.findById(id);
        return UnifyResult.ok().data("tag", tag);
    }

    @GetMapping("/{id}/change/{highlight}")
    public UnifyResult findById(@PathVariable String id, @PathVariable Integer highlight) {
        Tag tag = tagService.changeHighlight(id, highlight);
        return UnifyResult.ok().data("tag", tag);
    }
}
