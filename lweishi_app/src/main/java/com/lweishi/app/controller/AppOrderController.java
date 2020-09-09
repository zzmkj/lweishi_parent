package com.lweishi.app.controller;

import com.lweishi.constant.Constant;
import com.lweishi.model.AppUser;
import com.lweishi.model.RepairOrder;
import com.lweishi.service.RepairOrderService;
import com.lweishi.utils.UnifyResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName AppOrderController
 * @Description app 订单控制器
 * @Author zzm
 * @Data 2020/8/30 19:03
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class AppOrderController {

    @Autowired
    private RepairOrderService repairOrderService;

    @GetMapping("/repair/all/{status}")
    public UnifyResult findByStatus(@PageableDefault(page = 1, size = 10, sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable,
                                    @PathVariable Integer status, HttpServletRequest request) {
        AppUser appUser = (AppUser) request.getAttribute("appUser");
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        log.info("[status = {}]", status);
        Page<RepairOrder> orderPage = repairOrderService.findByStatusAndApp(status, pageRequest, appUser);
        return UnifyResult.ok().data("page", orderPage);
    }

    @GetMapping("/repair/{id}")
    public UnifyResult findByStatus(@PathVariable String id) {
        RepairOrder order = repairOrderService.findById(id);
        return UnifyResult.ok().data("order", order);
    }

    @PostMapping("/repair/{id}/grab")
    public UnifyResult grabRepairOrder(@PathVariable String id, HttpServletRequest request) {
        AppUser appUser = (AppUser) request.getAttribute("appUser");
        repairOrderService.grabRepairOrder(id, appUser);
        return UnifyResult.ok();
    }

    @GetMapping("/repair/{id}/complete")
    public UnifyResult completeOrder(@PathVariable String id) {
        repairOrderService.updateStatus(id, Constant.REPAIR_ORDER_STATUS_COMPLETED);
        return UnifyResult.ok();
    }

}
