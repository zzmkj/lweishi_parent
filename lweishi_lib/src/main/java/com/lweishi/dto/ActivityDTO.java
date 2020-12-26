package com.lweishi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDTO {

    private Long id;

    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "名称标识不能为空")
    private String name;

    @NotBlank(message = "描述不能为空")
    private String description;

    @Future(message = "请填写正确的时间格式")
    private LocalDateTime startTime;

    @Future(message = "请填写正确的时间格式")
    private LocalDateTime endTime;

    private Boolean online;

    @NotBlank(message = "入口图片不能为空")
    private String entranceImg;

    private String internalTopImg;

    @NotBlank(message = "备注不能为空")
    private String remark;
}
