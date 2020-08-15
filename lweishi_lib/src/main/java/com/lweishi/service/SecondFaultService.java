package com.lweishi.service;

import com.google.common.collect.Lists;
import com.lweishi.domain.SecondFault;
import com.lweishi.dto.SecondFaultDTO;
import com.lweishi.exception.GlobalException;
import com.lweishi.repository.SecondFaultRepository;
import com.lweishi.utils.BeanNullUtil;
import com.lweishi.utils.IDUtil;
import com.lweishi.utils.ResultCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author geek
 * @CreateTime 2020/8/7 22:49
 * @Description 二级故障业务
 */
@Service
@Transactional
public class SecondFaultService {

    @Autowired
    private SecondFaultRepository secondFaultRepository;

    public List<SecondFault> findAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        return secondFaultRepository.findAll(sort);
    }

    public Page<SecondFault> findAll(Pageable pageable) {
        return secondFaultRepository.findAll(pageable);
    }

    public SecondFault findById(String id) {
        return secondFaultRepository.findById(id).orElseThrow(() -> new GlobalException(ResultCode.ERROR, "该二级故障信息不存在"));
    }

    public void deleteById(String id) {
        secondFaultRepository.deleteById(id);
    }

    public SecondFault save(SecondFaultDTO secondFaultDTO) {
        SecondFault secondFault = new SecondFault();
        BeanUtils.copyProperties(secondFaultDTO, secondFault);
        secondFault.setId(IDUtil.UUID());
        secondFault.setCreateTime(LocalDateTime.now());
        return secondFaultRepository.save(secondFault);
    }

    public SecondFault update(SecondFaultDTO secondFaultDTO) {
        if (StringUtils.isBlank(secondFaultDTO.getId())) {
            throw new GlobalException(ResultCode.ERROR, "更新二级故障信息失败");
        }
        SecondFault secondFault = findById(secondFaultDTO.getId());
        BeanUtils.copyProperties(secondFaultDTO, secondFault, BeanNullUtil.getNullPropertyNames(secondFaultDTO));
        return secondFaultRepository.save(secondFault);
    }

    /**
     * 查询所有二级故障信息，并转换成Map结构，
     * @return Map结构，【Key是一级故障ID,Value是二级故障信息集合】
     */
    public Map<String, List<SecondFault>> findAllToMap() {
        List<SecondFault> secondFaults = this.findAll();
        Map<String, List<SecondFault>> secondFaultMap = secondFaults.stream().collect(
                Collectors.toMap(SecondFault::getFaultId, Lists::newArrayList,
                        (List<SecondFault> newValueList, List<SecondFault> oldValueList) -> {
                            oldValueList.addAll(newValueList);
                            return oldValueList;
                        })
        );
        return secondFaultMap;
    }

}
