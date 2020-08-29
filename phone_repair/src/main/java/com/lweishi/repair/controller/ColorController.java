package com.lweishi.repair.controller;

import com.lweishi.model.Color;
import com.lweishi.dto.ColorDTO;
import com.lweishi.service.ColorService;
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
 * @Description 颜色控制器
 */
@Slf4j
@RestController
@RequestMapping("/color")
public class ColorController {

    @Autowired
    private ColorService colorService;

    @GetMapping("/all")
    public UnifyResult findAll() {
        List<Color> colorList = colorService.findAll();
        return UnifyResult.ok().data("colorList", colorList);
    }

    @GetMapping("/page")
    public UnifyResult findAll(@PageableDefault(page = 1, size = 10, direction = Sort.Direction.DESC, sort = "createTime") Pageable pageable,
                               @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        Page<Color> pageData = colorService.findAll(pageRequest, keyword);
        return UnifyResult.ok().data("pageData", pageData);
    }

    @DeleteMapping("/{id}")
    public UnifyResult deleteById(@PathVariable String id) {
        colorService.deleteById(id);
        return UnifyResult.ok();
    }

    @PostMapping("/save")
    public UnifyResult save(@Valid @RequestBody ColorDTO colorDTO) {
        Color color = colorService.save(colorDTO);
        return UnifyResult.ok().data("color", color);
    }

    @PutMapping("/update")
    public UnifyResult update(@Valid @RequestBody ColorDTO colorDTO) {
        Color color = colorService.update(colorDTO);
        return UnifyResult.ok().data("color", color);
    }

    @GetMapping("/{id}")
    public UnifyResult findById(@PathVariable String id) {
        Color color = colorService.findById(id);
        return UnifyResult.ok().data("color", color);
    }
}
