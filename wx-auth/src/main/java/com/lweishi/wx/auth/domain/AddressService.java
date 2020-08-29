package com.lweishi.wx.auth.domain;

import com.lweishi.constant.Constant;
import com.lweishi.dto.AddressDTO;
import com.lweishi.exception.GlobalException;
import com.lweishi.model.Address;
import com.lweishi.utils.BeanNullUtil;
import com.lweishi.utils.IDUtil;
import com.lweishi.utils.ResultCode;
import com.lweishi.wx.auth.repository.AddressRepository;
import com.lweishi.wx.auth.utils.WxUserResolve;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName AddressService
 * @Description 用户地址管理
 * @Author zzm
 * @Data 2020/8/28 12:59
 * @Version 1.0
 */
@Slf4j
@Service
@Transactional
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private WxUserResolve wxUserResolve;

    /**
     * 把已设置成默认的地址变为普通
     * @param wxUserId 微信用户ID
     * @return
     */
    public void updateByPreferred(String wxUserId) {
        addressRepository.findByWxUserIdAndPreferred(wxUserId, Constant.ADDRESS_PREFERRED).ifPresent(address -> {
            address.setPreferred(Constant.ADDRESS_COMMON);
            addressRepository.save(address);
        });
    }

    public void saveOrUpdate(AddressDTO addressDTO, HttpServletRequest request) {
        if (StringUtils.isNotBlank(addressDTO.getId())) {
            this.update(addressDTO, request);
        } else {
            this.save(addressDTO, request);
        }
    }

    public void save(AddressDTO addressDTO, HttpServletRequest request) {
        Address address = new Address();
        BeanUtils.copyProperties(addressDTO, address);
        address.setId(IDUtil.UUID());
        address.setCreateTime(LocalDateTime.now());

        //解析用户
        WxUser wxUser = wxUserResolve.resolveWxUser(request);
        address.setWxUserId(wxUser.getId());

        //是否设置默认地址
        if (addressDTO.getIsPreferred()) {
            //把已设置成默认的地址变为普通
            this.updateByPreferred(wxUser.getId());
            address.setPreferred(Constant.ADDRESS_PREFERRED);
        } else {
            address.setPreferred(Constant.ADDRESS_COMMON);
        }

        addressRepository.save(address);
    }

    public void update(AddressDTO addressDTO, HttpServletRequest request) {
        WxUser wxUser = wxUserResolve.resolveWxUser(request);

        Address address = findById(addressDTO.getId());
        BeanUtils.copyProperties(addressDTO, address, BeanNullUtil.getNullPropertyNames(addressDTO));

        //是否设置默认地址
        if (addressDTO.getIsPreferred()) {
            //把已设置成默认的地址变为普通
            this.updateByPreferred(wxUser.getId());
            address.setPreferred(Constant.ADDRESS_PREFERRED);
        } else {
            address.setPreferred(Constant.ADDRESS_COMMON);
        }

        addressRepository.save(address);
    }

    public List<Address> findByWxUserId(HttpServletRequest request) {
        WxUser wxUser = wxUserResolve.resolveWxUser(request);
        if (wxUser == null) {
            throw new GlobalException(ResultCode.ERROR, "获取地址列表失败，请先登录！");
        }
        return addressRepository.findByWxUserId(wxUser.getId());
    }

    public Address findById(String id) {
        return addressRepository.findById(id).orElseThrow(() -> new GlobalException(ResultCode.ERROR, "获取地址信息失败！"));
    }

    public Address findById(String id, HttpServletRequest request) {
        WxUser wxUser = wxUserResolve.resolveWxUser(request);
        return addressRepository.findById(id).orElseThrow(() -> new GlobalException(ResultCode.ERROR, "获取地址信息失败！"));
    }

    public void deleteById(String id, HttpServletRequest request) {
        WxUser wxUser = wxUserResolve.resolveWxUser(request);
        addressRepository.deleteById(id);
    }
}
