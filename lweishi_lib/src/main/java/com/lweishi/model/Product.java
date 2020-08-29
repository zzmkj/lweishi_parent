package com.lweishi.model;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName Product
 * @Description 手机机型
 * @Author zzm
 * @Data 2020/8/22 13:12
 * @Version 1.0
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class Product {

    @Id
    @Column(name = "id", length = 32, nullable = false, unique = true)
    private String id;

    private String brandId; //品牌ID

    private String brandName; //品牌名称

    private String name; //产品名称

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<String> colorIds;

    private String image; //手机机型图片

    private Integer sequence; //产品序号

    private Boolean status; // 1：启用 ，0：停用

    private LocalDateTime createTime; //发布时间
}
