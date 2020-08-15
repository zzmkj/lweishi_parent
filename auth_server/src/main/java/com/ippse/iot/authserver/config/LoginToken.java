package com.ippse.iot.authserver.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @ClassName LoginToken
 * @Description QQ登录Token
 * @Author zzm
 * @Data 2019/7/29 13:45
 * @Version 1.0
 */
public class LoginToken extends AbstractAuthenticationToken {

    private final Object principal;

    public static final String QQ = "qq";
    public static final String WX = "wx";

    @Getter
    @Setter
    private String providerid;

    public LoginToken(String qqopenid, String providerid) {
        super(null);
        this.principal = qqopenid;
        this.providerid = providerid;
        this.setAuthenticated(false);
    }

    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     *                    represented by this authentication object.
     * @param principal
     */
    public LoginToken(Collection<? extends GrantedAuthority> authorities, Object principal, String providerid) {
        super(authorities);
        this.principal = principal;
        this.providerid = providerid;
        // must use super, as we override
        super.setAuthenticated(true);
    }

    /**
     * 判断是否已认证
     *
     * @param authenticated
     */
    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
