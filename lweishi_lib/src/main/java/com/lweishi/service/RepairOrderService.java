package com.lweishi.service;

import com.lweishi.constant.Constant;
import com.lweishi.dto.PersonalInfoDTO;
import com.lweishi.dto.RepairOrderDTO;
import com.lweishi.exception.GlobalException;
import com.lweishi.model.*;
import com.lweishi.repository.RepairOrderRepository;
import com.lweishi.utils.BeanNullUtil;
import com.lweishi.utils.IDUtil;
import com.lweishi.utils.ResultCode;
import com.lweishi.vo.FaultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName RepairOrderService
 * @Description 维修订单业务逻辑
 * @Author zzm
 * @Data 2020/8/24 15:58
 * @Version 1.0
 */
@Slf4j
@Service
@Transactional
public class RepairOrderService {

    @Autowired
    private RepairOrderRepository repairOrderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private ProductFaultService productFaultService;

    @Autowired
    private SecondFaultService secondFaultService;

    public List<RepairOrder> findAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        return repairOrderRepository.findAll(sort);
    }

    public Page<RepairOrder> findAll(Pageable pageable, String keyword) {
        Specification<RepairOrder> specification =  (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (StringUtils.isNotBlank(keyword)) {
                String key = "%" + keyword + "%";
                predicate = cb.or(cb.like(root.get("name"), key), cb.like(root.get("brandName"), key));
            }
            return predicate;
        };
        return repairOrderRepository.findAll(specification, pageable);
    }

    public RepairOrder findById(String id) {
        return repairOrderRepository.findById(id).orElseThrow(() -> new GlobalException(ResultCode.ERROR, "该维修订单信息不存在"));
    }

    public void deleteById(String id) {
        repairOrderRepository.deleteById(id);
    }

    public RepairOrder save(RepairOrderDTO repairOrderDTO) {
        RepairOrder repairOrder = new RepairOrder();

        BeanUtils.copyProperties(repairOrderDTO, repairOrder);

        repairOrder.setId(IDUtil.UUID().substring(0, 19));
        repairOrder.setStatus(Constant.REPAIR_ORDER_STATUS_WAITING);
        repairOrder.setCreateTime(LocalDateTime.now());

        String productId = repairOrderDTO.getProductId();
        Product product = productService.findById(productId);

        repairOrder.setProductName(product.getName());
        repairOrder.setProductImage(product.getImage());
        repairOrder.setBrandId(product.getBrandId());
        repairOrder.setBrandName(product.getBrandName());

        List<ProductFault> productFaults = productFaultService.findByIds(repairOrderDTO.getProductFaultIds());
        final BigDecimal[] totalPrice = {new BigDecimal(0)};

        List<FaultVO> faultVOList = productFaults.stream().map(fault -> {
            SecondFault secondFault = secondFaultService.findById(fault.getSecondFaultId());
            FaultVO faultVO = new FaultVO(fault.getId(), secondFault.getName(), fault.getPrice());
            totalPrice[0] = totalPrice[0].add(fault.getPrice());
            return faultVO;
        }).collect(Collectors.toList());
        log.info("【fault】 = {}", faultVOList);

        repairOrder.setPrice(totalPrice[0]);
        repairOrder.setActuallyPrice(totalPrice[0]);
        repairOrder.setFaults(faultVOList);

        return repairOrderRepository.save(repairOrder);
    }

   public RepairOrder updatePersonInfo(PersonalInfoDTO personalInfoDTO) {
        if (StringUtils.isBlank(personalInfoDTO.getId())) {
            throw new GlobalException(ResultCode.ERROR, "更新维修订单个人信息失败");
        }
        RepairOrder repairOrder = findById(personalInfoDTO.getId());
        BeanUtils.copyProperties(personalInfoDTO, repairOrder, BeanNullUtil.getNullPropertyNames(personalInfoDTO));
        return repairOrderRepository.save(repairOrder);
    }

    public void updateStatus(String id, Integer status, String... appUserId) {
        RepairOrder order = findById(id);
        switch (status) {
            case 1: //接单操作
                AppUser appUser = appUserService.findById(appUserId[0]);
                order.setAppUserId(appUserId[0]);
                order.setAppUserMobile(appUser.getMobile());
                order.setAppUserName(appUser.getName());
                order.setReceiveTime(LocalDateTime.now());
                break;
            case 2: //完成操作
                order.setCompleteTime(LocalDateTime.now());
                break;
            case 3: //取消操作
                order.setCancelTime(LocalDateTime.now());
                break;
        }
        order.setStatus(status);
        repairOrderRepository.save(order);
    }

    public List<RepairOrder> findByWxUserId(String wxUserId) {
        return repairOrderRepository.findByWxUserId(wxUserId);
    }
}
