package com.lweishi.domain;

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
 * @CreateTime 2020/8/5 23:59
 * @Description 二级故障
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class SecondFault {
    @Id
    @Column(name = "id", length = 32, nullable = false, unique = true)
    private String id;

    private String faultId; //一级故障id

    private String name;

    private Integer sequence;

    private BigDecimal price;

    private LocalDateTime createTime;
}
