package com.lweishi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author geek
 * @CreateTime 2020/8/5 23:58
 * @Description 一级故障
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class FirstFault {
    @Id
    @Column(name = "id", length = 32, nullable = false, unique = true)
    private String id;

    private String name;

    private Integer sequence;

    private LocalDateTime createTime;

    @Transient
    private List<SecondFault> children = new ArrayList<>();
}
