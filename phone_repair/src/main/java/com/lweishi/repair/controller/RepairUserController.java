package com.lweishi.repair.controller;

import com.lweishi.dto.AppUserRegisterDTO;
import com.lweishi.model.AppUser;
import com.lweishi.service.AppUserService;
import com.lweishi.utils.UnifyResult;
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
 * @CreateTime 2020/8/21 21:16
 * @Description 维修人员控制器 【RepairUser --> AppUser】
 */
@RestController
@RequestMapping("/repair")
public class RepairUserController {

    @Autowired
    private AppUserService appUserService;

    @GetMapping("/all")
    public UnifyResult findAll() {
        List<AppUser> appUserList = appUserService.findAll();
        return UnifyResult.ok().data("appUserList", appUserList);
    }

    @GetMapping("/page")
    public UnifyResult findAll(@PageableDefault(page = 1, size = 10, direction = Sort.Direction.DESC, sort = "createTime") Pageable pageable,
                               @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        Page<AppUser> pageData = appUserService.findAll(pageRequest, keyword);
        return UnifyResult.ok().data("pageData", pageData);
    }

    @DeleteMapping("/{id}")
    public UnifyResult deleteById(@PathVariable String id) {
        appUserService.deleteById(id);
        return UnifyResult.ok();
    }

    @PostMapping("/save")
    public UnifyResult save(@Valid @RequestBody AppUserRegisterDTO registerDTO) {
        appUserService.save(registerDTO);
        return UnifyResult.ok();
    }

    @PutMapping("/update")
    public UnifyResult update(@Valid @RequestBody AppUserRegisterDTO registerDTO) {
        AppUser appUser = appUserService.update(registerDTO);
        return UnifyResult.ok().data("appUser", appUser);
    }

    @GetMapping("/{id}")
    public UnifyResult findById(@PathVariable String id) {
        AppUser appUser = appUserService.findById(id);
        return UnifyResult.ok().data("appUser", appUser);
    }
}
