package com.lweishi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author geek
 * @CreateTime 2020/8/23 23:01
 * @Description 产品和故障实体关联
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductFault {
    @Id
    @Column(name = "id", length = 32, nullable = false, unique = true)
    private String id;

    private String productId; //产品id

    private String firstFaultId; //一级故障id

    private String secondFaultId; //二级故障id

    private BigDecimal price; //故障价格

    private LocalDateTime createTime;
}
