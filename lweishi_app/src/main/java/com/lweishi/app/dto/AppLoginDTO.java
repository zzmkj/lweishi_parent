package com.lweishi.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName AppLoginDTO
 * @Description TODO
 * @Author zzm
 * @Data 2020/8/14 15:25
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppLoginDTO {
    private String mobile;
    private String password;
}
