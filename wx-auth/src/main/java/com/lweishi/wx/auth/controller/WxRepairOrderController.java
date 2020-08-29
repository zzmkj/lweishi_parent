package com.lweishi.wx.auth.controller;

import com.lweishi.dto.RepairOrderDTO;
import com.lweishi.model.RepairOrder;
import com.lweishi.service.RepairOrderService;
import com.lweishi.utils.UnifyResult;
import com.lweishi.wx.auth.domain.WxUser;
import com.lweishi.wx.auth.utils.WxUserResolve;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @ClassName WxOrderController
 * @Description 微信 维修订单控制器
 * @Author zzm
 * @Data 2020/8/29 15:23
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/wx/order/repair")
public class WxRepairOrderController {

    @Autowired
    private RepairOrderService repairOrderService;

    @Autowired
    private WxUserResolve wxUserResolve;

    @PostMapping("/add")
    public UnifyResult save(@Valid @RequestBody RepairOrderDTO repairOrderDTO, HttpServletRequest request) {
        WxUser wxUser = wxUserResolve.resolveWxUser(request);
        repairOrderDTO.setWxUserId(wxUser.getId());
        RepairOrder order = repairOrderService.save(repairOrderDTO);
        return UnifyResult.ok().data("order", order);
    }

}
