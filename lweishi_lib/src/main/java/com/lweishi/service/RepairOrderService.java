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
import com.lweishi.wx.WxTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private WxTemplateService wxTemplateService;

    @Autowired
    private UploadService uploadService;

    public List<RepairOrder> findAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        return repairOrderRepository.findAll(sort);
    }

    public Page<RepairOrder> findAll(Pageable pageable, String keyword, Integer status) {
        Specification<RepairOrder> specification =  (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (StringUtils.isNotBlank(keyword)) {
                String key = "%" + keyword + "%";
                predicate = cb.or(cb.like(root.get("id"), key), cb.like(root.get("brandName"), key),
                        cb.like(root.get("customer"), key), cb.like(root.get("mobile"), key), cb.like(root.get("productName"), key),
                        cb.like(root.get("address"), key));
            }
//            log.info("【状态】 = {}", status);
            if (status != null) {
                predicate = cb.and(predicate, cb.equal(root.get("status"), status));
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

    public Page<RepairOrder> findByStatusAndApp(Integer status, Pageable pageable, AppUser appUser) {
        Page<RepairOrder> page = null;
        switch (status) {
            case 0: //待接单
                page = repairOrderRepository.findByStatus(status, pageable);
                break;
            case 1: //进行中
            case 2: //已完成
                page = repairOrderRepository.findByStatusAndAppUserId(status, appUser.getId(), pageable);
                break;
            default:
                throw new GlobalException(10007, "请求失败！");
        }
        return page;
    }

    public RepairOrder findByIdAndAppUserId(String id, String appUserId) {
        return repairOrderRepository.findByIdAndAppUserId(id, appUserId).orElseThrow(() -> new GlobalException(10006, "未找到该订单信息"));
    }

    public void grabRepairOrder(String id, AppUser appUser) {
        RepairOrder order = findById(id);
        log.info("【用户】 = {}", appUser.getId());
        log.info("【用户】 = {}", appUser.getMobile());
        if (!Constant.REPAIR_ORDER_STATUS_WAITING.equals(order.getStatus()) || StringUtils.isNotBlank(order.getAppUserId())) {
            throw new GlobalException(10008, "该订单已接单！");
        }
        order.setAppUserId(appUser.getId());
        order.setAppUserName(appUser.getName());
        order.setAppUserMobile(appUser.getMobile());
        order.setStatus(Constant.REPAIR_ORDER_STATUS_PROCESSING);
        order.setReceiveTime(LocalDateTime.now());
        repairOrderRepository.save(order);

        //发送微信消息提醒用户，已接单
        wxTemplateService.sendReceiptOrderMsg(order);
    }

    public String uploadImage(String id, Integer type, MultipartFile file) {
        RepairOrder order = findById(id);
        if (order.getStatus().equals(Constant.REPAIR_ORDER_STATUS_WAITING)) {
            throw new GlobalException(30004, "上传图片失败！");
        }
        String imageUrl = uploadService.uploadImage(file);
        if (type == 0) { //type为0的时候是 维修前图片
            order.setImageBefore(imageUrl);
        } else if (type == 1) { //type为1的时候是 维修后图片
            order.setImageAfter(imageUrl);
        } else {
            throw new GlobalException(30004, "上传图片失败！");
        }
        log.info("【上传成功！】");
        RepairOrder result = repairOrderRepository.save(order);
        return type == 0 ? result.getImageBefore() : result.getImageAfter();
    }

    public void deleteImage(String id, Integer type) {
        RepairOrder order = findById(id);
        if (order.getStatus().equals(Constant.REPAIR_ORDER_STATUS_WAITING)) {
            throw new GlobalException(30005, "删除图片失败！");
        }
        if (type == 0) { //type为0的时候是 维修前图片
            uploadService.deleteImage(StringUtils.substringAfterLast(order.getImageBefore(), "/"));
            order.setImageBefore(null);
        } else if (type == 1) { //type为1的时候是 维修后图片
            uploadService.deleteImage(StringUtils.substringAfterLast(order.getImageAfter(), "/"));
            order.setImageAfter(null);
        } else {
            throw new GlobalException(30005, "删除图片失败！");
        }
        log.info("【删除图片成功！】");
        repairOrderRepository.save(order);
    }
}
