package com.lweishi.model;

import com.lweishi.vo.FaultVO;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName Order
 * @Description 维修订单
 * @Author zzm
 * @Data 2020/8/24 15:39
 * @Version 1.0
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class RepairOrder {
    @Id
    @Column(name = "id", length = 32, nullable = false, unique = true)
    private String id;

    private String productId; //产品ID
    private String productName; //产品名称
    private String productImage; //产品图片
    private String color; //产品颜色

    private String brandId; //品牌ID
    private String brandName; //品牌名称

    private String wxUserId; //下单用户ID

    private String customer; //客户名称
    private String mobile; //手机号
    private String address; // 地址
    private String lnglat; // 经纬度

    private String time; //用户预约时间【期望时间】
    private String reserveTime; //最终预约时间【维修师傅联系用户进行协商确认】

    private BigDecimal price; //订单价格
    private BigDecimal actuallyPrice; //订单实付价格

    private Integer type; //订单类型：0：上门  1：邮寄
    private Integer status; //订单状态：0：待接单  1:进行中  2：已完成  3：已取消

    private String remark; //备注信息

    private LocalDateTime createTime; //订单创建时间
    private LocalDateTime receiveTime; //订单接单时间
    private LocalDateTime completeTime; //订单完成时间
    private LocalDateTime cancelTime; //订单取消时间

    private String appUserId; //维修师傅ID
    private String appUserName; //维修师傅名称
    private String appUserMobile; //维修师傅手机号

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<FaultVO> faults;
}
