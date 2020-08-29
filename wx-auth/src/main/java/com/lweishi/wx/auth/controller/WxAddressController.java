package com.lweishi.wx.auth.controller;

import com.lweishi.dto.AddressDTO;
import com.lweishi.model.Address;
import com.lweishi.utils.UnifyResult;
import com.lweishi.wx.auth.domain.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @ClassName WxAddressController
 * @Description 地址管理控制器
 * @Author zzm
 * @Data 2020/8/28 16:51
 * @Version 1.0
 */
@RestController
@RequestMapping("/wx/address")
public class WxAddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/add")
    public UnifyResult saveAddress(@Valid @RequestBody AddressDTO addressDTO, HttpServletRequest request) {
        addressService.saveOrUpdate(addressDTO, request);
        return UnifyResult.ok();
    }

    @GetMapping("/all")
    public UnifyResult findAllByWxUserId(HttpServletRequest request) {
        List<Address> addressList = addressService.findByWxUserId(request);
        return UnifyResult.ok().data("addressList", addressList);
    }

    @GetMapping("/{id}")
    public UnifyResult findById(@PathVariable String id, HttpServletRequest request) {
        Address address = addressService.findById(id, request);
        return UnifyResult.ok().data("address", address);
    }

    @DeleteMapping("/{id}")
    public UnifyResult deleteById(@PathVariable String id, HttpServletRequest request) {
        addressService.deleteById(id, request);
        return UnifyResult.ok();
    }
}
