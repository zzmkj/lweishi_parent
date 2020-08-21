package com.lweishi.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppLoginVO {
    private String token;
    private String name;
    private String avatar;
    private String mobile;
}