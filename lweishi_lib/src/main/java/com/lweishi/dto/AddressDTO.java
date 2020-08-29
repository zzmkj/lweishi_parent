package com.lweishi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @ClassName AddressDTO
 * @Description 地址管理
 * @Author zzm
 * @Data 2020/8/28 13:38
 * @Version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private String id;

    @NotBlank(message = "地址不能为空")
    private String address;

    @NotBlank(message = "地址详情不能为空")
    private String addressDetail;

    @NotBlank(message = "地址省份不能为空")
    private String province;

    @NotBlank(message = "地址市级不能为空")
    private String city;

    @NotBlank(message = "地址区级不能为空")
    private String county;

    @NotBlank(message = "联系人不能为空")
    private String name;

    @NotBlank(message = "联系电话不能为空")
    private String mobile;

    @NotBlank(message = "地址经纬度不能为空")
    private String lnglat;

    @NotBlank(message = "标签不能为空")
    private String tag;

    @NotNull(message = "是否默认不能为空")
    private Boolean isPreferred;
}
