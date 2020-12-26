package com.lweishi.repair.controller;

import com.lweishi.dto.ActivityDTO;
import com.lweishi.dto.BrandDTO;
import com.lweishi.model.Activity;
import com.lweishi.model.Brand;
import com.lweishi.service.ActivityService;
import com.lweishi.service.BrandService;
import com.lweishi.utils.UnifyResult;
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
 * @CreateTime 2020/12/6 23:21
 * @Description 活动控制器
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping("/all")
    public UnifyResult findAll() {
        List<Activity> activityList = activityService.findAll();
        return UnifyResult.ok().data("activityList", activityList);
    }

    @GetMapping("/page")
    public UnifyResult findAll(@PageableDefault(page = 1, size = 10, direction = Sort.Direction.DESC, sort = "createTime") Pageable pageable,
                               @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        Page<Activity> pageData = activityService.findAll(pageRequest, keyword);
        return UnifyResult.ok().data("pageData", pageData);
    }

    @DeleteMapping("/{id}")
    public UnifyResult deleteById(@PathVariable Long id) {
        activityService.deleteById(id);
        return UnifyResult.ok();
    }

    @PostMapping("/save")
    public UnifyResult save(@Validated @RequestBody ActivityDTO activityDTO) {
        Activity activity = activityService.save(activityDTO);
        return UnifyResult.ok().data("activity", activity);
    }

    @PutMapping("/update")
    public UnifyResult update(@Validated @RequestBody ActivityDTO activityDTO) {
        Activity activity = activityService.update(activityDTO);
        return UnifyResult.ok().data("activity", activity);
    }

    @GetMapping("/{id}")
    public UnifyResult findById(@PathVariable Long id) {
        Activity activity = activityService.findById(id);
        return UnifyResult.ok().data("activity", activity);
    }

    @GetMapping("/change/{id}/online")
    public UnifyResult changeOnline(@PathVariable Long id, @RequestParam Boolean online) {
        activityService.changeOnline(id, online);
        return UnifyResult.ok();
    }
}
