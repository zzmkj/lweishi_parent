package com.lweishi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @ClassName Address
 * @Description 用户地址实体类
 * @Author zzm
 * @Data 2020/8/28 12:48
 * @Version 1.0
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @Column(name = "id", length = 32, unique = true, nullable = false)
    private String id;

    private String wxUserId; //微信小程序用户ID

    private String name; //联系人

    private String mobile; //联系电话

    private String address; //地址

    private String addressDetail; //门牌号

    private String province; //省份

    private String city; //市

    private String county; //区

    private String lnglat; //经纬度

    private String tag; //标签

    private Integer preferred; // 1：首选【默认】  0：普通

    private LocalDateTime createTime;
}
