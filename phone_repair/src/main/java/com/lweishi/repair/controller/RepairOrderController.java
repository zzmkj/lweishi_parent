package com.lweishi.repair.controller;

import com.lweishi.constant.Constant;
import com.lweishi.model.RepairOrder;
import com.lweishi.service.RepairOrderService;
import com.lweishi.utils.UnifyResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName OrderController
 * @Description 维修订单控制器
 * @Author zzm
 * @Data 2020/9/11 9:35
 * @Version 1.0
 */
@RestController
@RequestMapping("/order/repair")
public class RepairOrderController {

    @Autowired
    private RepairOrderService repairOrderService;

    @GetMapping("/all")
    public UnifyResult findAll() {
        List<RepairOrder> repairOrderList = repairOrderService.findAll();
        return UnifyResult.ok().data("orderList", repairOrderList);
    }

    @GetMapping("/page")
    public UnifyResult findAll(@PageableDefault(page = 1, size = 10, direction = Sort.Direction.DESC, sort = "createTime") Pageable pageable,
                               @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
                               @RequestParam(name = "status", required = false, defaultValue = "") Integer status) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        Page<RepairOrder> pageData = repairOrderService.findAll(pageRequest, keyword, status);
        return UnifyResult.ok().data("pageData", pageData);
    }

    @DeleteMapping("/{id}")
    public UnifyResult deleteById(@PathVariable String id) {
        repairOrderService.deleteById(id);
        return UnifyResult.ok();
    }


    @GetMapping("/{id}")
    public UnifyResult findById(@PathVariable String id) {
        RepairOrder repairOrder = repairOrderService.findById(id);
        return UnifyResult.ok().data("order", repairOrder);
    }

    @GetMapping("/{id}/cancel")
    public UnifyResult cancelOrder(@PathVariable String id) {
        repairOrderService.updateStatus(id, Constant.REPAIR_ORDER_STATUS_CANCELLED);
        return UnifyResult.ok();
    }

}
