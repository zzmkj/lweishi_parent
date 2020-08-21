package com.lweishi.service;

import com.lweishi.dto.AppUserRegisterDTO;
import com.lweishi.exception.GlobalException;
import com.lweishi.model.AppUser;
import com.lweishi.model.Color;
import com.lweishi.repository.AppUserRepository;
import com.lweishi.utils.BeanNullUtil;
import com.lweishi.utils.IDUtil;
import com.lweishi.utils.JwtUtils;
import com.lweishi.utils.ResultCode;
import com.lweishi.vo.AppLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @Author geek
 * @CreateTime 2020/7/4 18:48
 * @Description 用户业务
 */
@Slf4j
@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    public Optional<AppUser> checkUserIsExist(String mobile) {
        return appUserRepository.findByMobile(mobile);
    }

    public AppLoginVO login(String mobile, String password) {
        log.info("【mobile = {}password = {} 】", mobile, password);
        Optional<AppUser> userOptional = this.checkUserIsExist(mobile.trim());
        AppUser appUser = userOptional.orElseThrow(() -> new GlobalException(30001, "用户名或者密码错误"));
        if (!StringUtils.equals(password, appUser.getPassword())) {
            throw new GlobalException(30001, "用户名或者密码错误");
        }
        String token = JwtUtils.getAppJwtToken(appUser.getId(), appUser.getName(), appUser.getAvatar());
        return new AppLoginVO(token, appUser.getName(), appUser.getAvatar(), appUser.getMobile());
    }

    public AppUser save(AppUserRegisterDTO appUserRegisterDTO) {
        //检验手机号是否存在该用户
        Optional<AppUser> userOptional = checkUserIsExist(appUserRegisterDTO.getMobile());
        if (userOptional.isPresent()) {
            //如果存在，抛出异常信息
            throw new GlobalException(ResultCode.ERROR, "该手机号已经注册！");
        }
        AppUser appUser = new AppUser(IDUtil.UUID(), appUserRegisterDTO.getMobile(), appUserRegisterDTO.getPassword(), appUserRegisterDTO.getName(), appUserRegisterDTO.getAvatar(), LocalDateTime.now());
        return appUserRepository.save(appUser);
    }

    public AppUser findById(String id) {
        return appUserRepository.findById(id).orElseThrow(() -> new RuntimeException("没有找到该维修人员信息"));
    }

    public void deleteById(String id) {
        appUserRepository.deleteById(id);
    }

    public AppUser update(AppUserRegisterDTO registerDTO) {
        if (StringUtils.isBlank(registerDTO.getId())) {
            throw new GlobalException(ResultCode.ERROR, "更新维修人员信息失败");
        }
        AppUser appUser = findById(registerDTO.getId());
        BeanUtils.copyProperties(registerDTO, appUser, BeanNullUtil.getNullPropertyNames(registerDTO));
        return appUserRepository.save(appUser);
    }

    public List<AppUser> findAll() {
        return appUserRepository.findAll();
    }

    public Page<AppUser> findAll(Pageable pageable, String keyword) {
        if (StringUtils.isBlank(keyword)) {
            return appUserRepository.findAll(pageable);
        }
        return appUserRepository.findByNameLike("%" + keyword + "%", pageable);
    }
}
