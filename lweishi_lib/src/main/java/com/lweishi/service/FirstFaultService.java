package com.lweishi.service;

import com.lweishi.model.FirstFault;
import com.lweishi.model.SecondFault;
import com.lweishi.dto.FirstFaultDTO;
import com.lweishi.exception.GlobalException;
import com.lweishi.repository.FirstFaultRepository;
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
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @Author geek
 * @CreateTime 2020/8/7 22:49
 * @Description 一级故障业务
 */
@Service
@Transactional
public class FirstFaultService {

    @Autowired
    private FirstFaultRepository firstFaultRepository;

    @Autowired
    private SecondFaultService secondFaultService;

    public List<FirstFault> findAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        return firstFaultRepository.findAll(sort);
    }

    public Page<FirstFault> findAll(Pageable pageable) {
        return firstFaultRepository.findAll(pageable);
    }

    public FirstFault findById(String id) {
        return firstFaultRepository.findById(id).orElseThrow(() -> new GlobalException(ResultCode.ERROR, "该一级故障信息不存在"));
    }

    public void deleteById(String id) {
        //根据一级故障ID，删除该所属的二级故障
        secondFaultService.deleteByFaultId(id);
        //删除一级故障
        firstFaultRepository.deleteById(id);
    }

    public FirstFault save(FirstFaultDTO firstFaultDTO) {
        FirstFault firstFault = new FirstFault();
        BeanUtils.copyProperties(firstFaultDTO, firstFault);
        firstFault.setId(IDUtil.UUID());
        firstFault.setCreateTime(LocalDateTime.now());
        return firstFaultRepository.save(firstFault);
    }

    public FirstFault update(FirstFaultDTO firstFaultDTO) {
        if (StringUtils.isBlank(firstFaultDTO.getId())) {
            throw new GlobalException(ResultCode.ERROR, "更新品牌信息失败");
        }
        FirstFault firstFault = findById(firstFaultDTO.getId());
        BeanUtils.copyProperties(firstFaultDTO, firstFault, BeanNullUtil.getNullPropertyNames(firstFaultDTO));
        return firstFaultRepository.save(firstFault);
    }

    /**
     * 查询所有一级故障信息，并将二级故障信息封装到一级故障中，形成一棵Tree树
     * @param secondFaultMap 二级故障信息Map结构，【Key是一级故障ID,Value是二级故障信息集合】
     * @return Tree-故障信息树
     */
    public List<FirstFault> findAllToTree(Map<String, List<SecondFault>> secondFaultMap) {
        Sort sort = Sort.by(Sort.Direction.ASC, "sequence");
        List<FirstFault> firstFaults = firstFaultRepository.findAll(sort);
        firstFaults.forEach(firstFault -> {
            if (secondFaultMap.containsKey(firstFault.getId())) {
                List<SecondFault> secondFaultList = secondFaultMap.get(firstFault.getId());
                firstFault.setChildren(secondFaultList);
            }
        });
        return firstFaults;
    }

    public List<FirstFault> findByIds(List<String> ids) {
        Sort sort = Sort.by(Sort.Direction.ASC, "sequence");
        return firstFaultRepository.findByIdIn(ids, sort);
    }
}
