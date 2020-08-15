package com.lweishi.repair.config;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OkUser implements OAuth2User, Serializable {
    private static final long serialVersionUID = 1767052910033405847L;
    private List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
    private Map<String, Object> attributes;

    public static final class Views {
        public interface Public {
        }

        public interface Friend extends Public {
        }

        public interface Profile extends Friend {
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        if (this.attributes == null) {
            this.attributes = new HashMap<>();
            this.attributes.put("id", this.getId());
            this.attributes.put("name", this.getName());
            this.attributes.put("username", this.getUsername());
            this.attributes.put("email", this.getEmail());
        }
        return attributes;
    }

    @JsonView(Views.Public.class)
    private String id;

    @JsonView(Views.Public.class)
    private String username;

    private String password;

    @JsonView(Views.Profile.class)
    private String email;

    @JsonView(Views.Profile.class)
    private String code;

    @JsonView(Views.Profile.class)
    private String mobile;

    @JsonView(Views.Profile.class)
    private Date lastPasswordResetDate;

    @JsonView(Views.Profile.class)
    private String type;

    @JsonView(Views.Public.class)
    private String status;

    @JsonView(Views.Profile.class)
    private String certcode;

    @JsonView(Views.Public.class)
    private Integer rank;

    @JsonView(Views.Public.class)
    private Integer follow;

    @JsonView(Views.Public.class)
    private Integer fans;

    @JsonView(Views.Public.class)
    private Integer posts;

    @JsonView(Views.Public.class)
    private String name;

    @JsonView(Views.Public.class)
    private String image;

    @JsonView(Views.Public.class)
    private String nickname;

    @JsonView(Views.Profile.class)
    private String homepage;

    @JsonView(Views.Profile.class)
    private String intro;

    @JsonView(Views.Profile.class)
    private String sex;

    @JsonView(Views.Profile.class)
    private Date birthday;

    @JsonView(Views.Profile.class)
    private String marriage;

    @JsonView(Views.Profile.class)
    private String bloodtype;

    @JsonView(Views.Profile.class)
    private String hometown;

    @JsonView(Views.Profile.class)
    private String edulevel;

    @JsonView(Views.Profile.class)
    private String religion;

    @JsonView(Views.Profile.class)
    private String hobby;

    @JsonView(Views.Public.class)
    private String signature;

    @JsonView(Views.Profile.class)
    private Timestamp ctime;

    @JsonView(Views.Profile.class)
    private Set<String> roles = new HashSet<String>();

    @JsonView(Views.Profile.class)
    private Set<OkUserConfig> configs = new HashSet<OkUserConfig>(0);

    @JsonView(Views.Profile.class)
    private String districtid; //地区id

    @JsonView(Views.Profile.class)
    private String districtname; //地区名称-包含三级的

    @JsonView(Views.Profile.class)
    private String admin; //是否是管理员

    @JsonView(Views.Profile.class)
    private List<String> history = new ArrayList<>(); //登录历史记录

    private String scope;

    // 不用于json输出，仅业务层需要
    public Set<String> getConfigValue(String property) {
        for (OkUserConfig config : this.getConfigs()) {
            if (StringUtils.equals(config.getProperty(), property))
                return config.getVals();
        } // TODO 增加默认值
        return new HashSet<String>();
    }

}
