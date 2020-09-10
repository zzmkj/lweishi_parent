package com.lweishi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName IssueGroupDTO
 * @Description 问题组前端传输数据对象
 * @Author zzm
 * @Data 2020/9/9 17:11
 * @Version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IssueGroupDTO {
    @NotBlank(message = "id不能为空", groups = {Update.class})
    private String id;

    @NotBlank(message = "名称不能为空", groups = {Save.class, Update.class})
    private String name;

    @NotBlank(message = "图标不能为空", groups = {Save.class, Update.class})
    private String icon;

    public interface Save {}
    public interface Update {}
}
