package com.lweishi.wx.auth.controller;

import com.lweishi.bo.PageCounter;
import com.lweishi.constant.Constant;
import com.lweishi.dto.RepairOrderDTO;
import com.lweishi.model.RepairOrder;
import com.lweishi.model.WxUser;
import com.lweishi.service.RepairOrderService;
import com.lweishi.utils.CommonUtil;
import com.lweishi.utils.UnifyResult;
import com.lweishi.wx.auth.message.JiGuangPushService;
import com.lweishi.wx.auth.message.PushBean;
import com.lweishi.wx.auth.utils.WxUserResolve;
import com.lweishi.wx.auth.vo.Paging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/order/repair")
public class WxRepairOrderController {

    @Autowired
    private RepairOrderService repairOrderService;

    @Autowired
    private WxUserResolve wxUserResolve;

    @Autowired
    private JiGuangPushService jiGuangPushService;

    @PostMapping("/add")
    public UnifyResult save(@Valid @RequestBody RepairOrderDTO repairOrderDTO, HttpServletRequest request) {
        WxUser wxUser = wxUserResolve.resolveWxUser(request);
        repairOrderDTO.setWxUserId(wxUser.getId());
        RepairOrder order = repairOrderService.save(repairOrderDTO);
        sendAppMessage("您有新订单来了！", "产品型号：" +order.getBrandName() + order.getProductName());
        return UnifyResult.ok().data("order", order);
    }

    @GetMapping("/by/status/{status}")
    public UnifyResult findAll(@PathVariable Integer status,
                               @RequestParam(name = "start", defaultValue = "0") Integer start,
                               @RequestParam(name = "count", defaultValue = "10") Integer count, HttpServletRequest request) {
        WxUser wxUser = wxUserResolve.resolveWxUser(request);
        // 转换成分页对象
        PageCounter page = CommonUtil.convertToPageParameter(start, count);

        Page<RepairOrder> orderPage = repairOrderService.findByWxUserIdAndStatus(wxUser.getId(), status, page.getPage(), page.getCount());
        Paging<RepairOrder> orderPaging = new Paging<>(orderPage);
        return UnifyResult.ok().data("result", orderPaging);
    }

    @GetMapping("/{id}")
    public UnifyResult findById(@PathVariable String id) {
        RepairOrder order = repairOrderService.findById(id);
        return UnifyResult.ok().data("order", order);
    }

    @GetMapping("/{id}/cancel")
    public UnifyResult deleteById(@PathVariable String id, HttpServletRequest request) {
        WxUser wxUser = wxUserResolve.resolveWxUser(request);
        repairOrderService.updateStatus(id, Constant.REPAIR_ORDER_STATUS_CANCELLED);
        return UnifyResult.ok();
    }

    public void sendAppMessage(String title, String content) {
        PushBean pushBean = new PushBean();
        pushBean.setTitle(title);
        pushBean.setAlert(content);
        boolean flag = jiGuangPushService.pushAndroid(pushBean);
    }
}
