package com.ippse.iot.authserver.config;

import com.ippse.iot.authserver.dao.UserDao;
import com.ippse.iot.authserver.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

public class CustomTokenEnhancer implements TokenEnhancer {
    @Autowired
    private UserDao userDao;

    /**
     * 暂未加入额外用户信息。
     * TokenEnhancer 接口提供一个 enhance(OAuth2AccessToken var1, OAuth2Authentication var2) 方法，
     * 用于对token信息的添加，信息来源于 OAuth2Authentication这里我们加入了用户的授权信息。
     * User user = (User) authentication.getUserAuthentication().getPrincipal();
     * additionalInfo.put("username", user.getUsername());
     * additionalInfo.put("authorities", user.getAuthorities());
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();
        final User user = userDao.findByUsername(authentication.getName());
        //additionalInfo.put("organization", authentication.getName() + randomAlphabetic(4));
        //additionalInfo.put("uname", user.getName());
        //additionalInfo.put("uimage", user.getImage());
        //如果客户端授权类型是client_credentials，则没有用户信息，可能为空
        if (null != user && StringUtils.isNotBlank(user.getId())) {
            additionalInfo.put("uid", user.getId());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        }
        return accessToken;
    }

}
