package com.ippse.iot.authserver.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

/**
 * User entity. @author zhoubing
 */
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = "json", typeClass = JsonStringType.class)
@Entity
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = -3936141646843762694L;

    public static final class Views {
        public interface Public {
        }

        public interface Friend extends Public {
        }

        public interface Profile extends Friend {
        }
    }

    @JsonView(User.Views.Public.class)
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 32)
    private String id;

    @JsonView(User.Views.Public.class)
    @Column(nullable = false, unique = true)
    @NotNull(message = "user.account.notnull")
    @Size(min = 3, max = 30, message = "user.account.size")
    @Pattern(regexp = "[a-zA-Z0-9_@]{3,30}.[a-zA-Z0-9]{1,}", message = "user.account.illegal")
    private String username;

    @Size(min = 60, max = 60)
    @Column(length = 60)
    private String password;

    @JsonView(User.Views.Profile.class)
    @Email
    @Size(min = 5, max = 100)
    @Column(length = 100, unique = true)
    private String email;

    @JsonView(User.Views.Profile.class)
    private String code;// 身份证号码
    @JsonView(User.Views.Profile.class)
    @Column(length = 100, unique = true)
    private String mobile;
    @JsonView(User.Views.Profile.class)
    private Date lastPasswordResetDate;
    @JsonView(User.Views.Profile.class)
    private String type;

    @JsonView(User.Views.Public.class)
    private String status;
    @JsonView(User.Views.Profile.class)
    private String certcode;

    @JsonView(User.Views.Public.class)
    private String name;
    @JsonView(User.Views.Public.class)
    private String banner;
    @JsonView(User.Views.Public.class)
    private String avatar;
    @JsonView(User.Views.Public.class)
    private String nickname;

    @JsonView(User.Views.Profile.class)
    private String sex;
    @JsonView(User.Views.Profile.class)
    private Date birthday;

    @JsonView(User.Views.Profile.class)
    private String districtid; //地区id
    @JsonView(User.Views.Profile.class)
    private String address; //详细地址
    @JsonView(User.Views.Profile.class)
    private String postcode; //邮政编码

    @JsonView(User.Views.Profile.class)
    private Timestamp ctime;
    private String verifycode;//验证码
    private Timestamp verifysendtime;//发送验证码时间
    private Timestamp verifiedtime;//用户正确验证完成注册时间

    @JsonView(User.Views.Profile.class)
    private String qqopenid;

    @JsonView(User.Views.Profile.class)
    private String wxopenid;

    @JsonView(Views.Profile.class)
    private String admin; //是否是管理员

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private Set<String> roles = new HashSet<String>();

    @JsonView(User.Views.Profile.class)
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private Set<Config> configs = new HashSet<Config>(0);

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<String> history = new ArrayList<>(); //登录历史记录

    private String scope;

    //不用于json输出，仅业务层需要
    public Set<String> getConfigValue(String property) {
        for (Config config : this.getConfigs()) {
            if (StringUtils.equals(config.getProperty(), property))
                return config.getVals();
        } // TODO 增加默认值
        return new HashSet<String>();
    }

    @Transient
    private String codesafe;// 身份证加密

    public String getCodesafe() {
        if (StringUtils.isNotBlank(code)) {
            StringBuilder str = new StringBuilder(code);
            if (StringUtils.length(code) == 18 || StringUtils.length(code) == 15) {
                for (int i = 2; i < StringUtils.length(code); i++) {
                    if (i < StringUtils.length(code) - 2) {
                        str.setCharAt(i, '*');
                    }
                }
                this.codesafe = str.toString();
            } else {
                this.codesafe = "******";
            }
        }
        return codesafe;
    }

}