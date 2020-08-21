package com.lweishi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @Author geek
 * @CreateTime 2020/8/21 21:20
 * @Description 维修人员注册数据
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppUserRegisterDTO {

    private String id;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$", message = "手机号码格式错误")
    private String mobile;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "名称不能为空")
    private String name;

    private String avatar;
}
