package com.lweishi.wx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Result
 * @Description 微信推送返回调用结果信息
 * @Author zzm
 * @Data 2019/10/11 10:26
 * @Version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result {
    private Integer errcode;
    private String errmsg;
    private Long msgid;
}