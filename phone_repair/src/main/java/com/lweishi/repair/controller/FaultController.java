package com.lweishi.repair.controller;

import com.lweishi.domain.Color;
import com.lweishi.domain.FirstFault;
import com.lweishi.domain.SecondFault;
import com.lweishi.dto.FirstFaultDTO;
import com.lweishi.dto.SecondFaultDTO;
import com.lweishi.service.ColorService;
import com.lweishi.service.FirstFaultService;
import com.lweishi.service.SecondFaultService;
import com.lweishi.utils.UnifyResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author geek
 * @CreateTime 2020/8/7 23:21
 * @Description 一级故障控制器
 */
@RestController
@RequestMapping("/admin/fault")
public class FaultController {

    @Autowired
    private FirstFaultService firstFaultService;

    @Autowired
    private SecondFaultService secondFaultService;

    @GetMapping("/all")
    public UnifyResult findAll() {
        Map<String, List<SecondFault>> secondFaultMap = secondFaultService.findAllToMap();
        List<FirstFault> faultList = firstFaultService.findAllToTree(secondFaultMap);
        return UnifyResult.ok().data("faultList", faultList);
    }

    @DeleteMapping("/first/{id}")
    public UnifyResult deleteFirstFault(@PathVariable String id) {
        firstFaultService.deleteById(id);
        return UnifyResult.ok();
    }

    @PostMapping("/first/save")
    public UnifyResult saveFirstFault(@Validated FirstFaultDTO firstFaultDTO) {
        FirstFault firstFault = firstFaultService.save(firstFaultDTO);
        return UnifyResult.ok().data("firstFault", firstFault);
    }

    @PutMapping("/first/update")
    public UnifyResult update(@Validated FirstFaultDTO firstFaultDTO) {
        FirstFault firstFault = firstFaultService.update(firstFaultDTO);
        return UnifyResult.ok().data("firstFault", firstFault);
    }

    @DeleteMapping("/second/{id}")
    public UnifyResult deleteSecondFault(@PathVariable String id) {
        secondFaultService.deleteById(id);
        return UnifyResult.ok();
    }

    @PostMapping("/second/save")
    public UnifyResult saveSecondFault(@Validated SecondFaultDTO secondFaultDTO) {
        SecondFault secondFault = secondFaultService.save(secondFaultDTO);
        return UnifyResult.ok().data("secondFault", secondFault);
    }

    @PutMapping("/second/update")
    public UnifyResult update(@Validated SecondFaultDTO secondFaultDTO) {
        SecondFault secondFault = secondFaultService.update(secondFaultDTO);
        return UnifyResult.ok().data("secondFault", secondFault);
    }

}
