package com.lweishi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @Author geek
 * @CreateTime 2020/7/4 18:44
 * @Description 用户
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    @Id
    private String id;

    private String mobile;
    private String password;
    private String name;
    private String avatar;
    private LocalDateTime createTime;
}
