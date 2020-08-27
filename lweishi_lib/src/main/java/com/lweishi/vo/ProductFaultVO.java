package com.lweishi.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author geek
 * @CreateTime 2020/8/23 23:17
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFaultVO {
    private String firstFaultId;
    private String firstFaultName;
    private List<ProductFaultItemVO> children;
}
