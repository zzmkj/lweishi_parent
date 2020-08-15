package com.ippse.iot.authserver.controller;

import com.ippse.iot.authserver.dao.UserDao;
import com.ippse.iot.authserver.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Map;

@Slf4j
public abstract class BaseApiController {
    @Autowired
    protected MessageSource messageSource;

    @Autowired
    protected UserDao userDao;
    protected String sessuserid = "";
    protected User sessuser;
    protected String accessToken;

    @ModelAttribute
    public void getSessionUser(Authentication authentication) {
        if (authentication != null && authentication.getDetails() != null) {
            Object details = authentication.getDetails();
            if (details instanceof OAuth2AuthenticationDetails) {
                OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails) details;
                log.info(oAuth2AuthenticationDetails.getTokenValue());
                log.info(authentication.getName());
                Map<String, Object> decodedDetails = (Map<String, Object>) oAuth2AuthenticationDetails
                        .getDecodedDetails();
                if (null != decodedDetails) {
                    log.info(decodedDetails.toString());
                    log.info("BaseController", "sessuserid: " + decodedDetails.get("uid"));
                    sessuserid = (String) decodedDetails.get("uid");
                    if (StringUtils.isNotBlank(sessuserid)) {
                        log.info("BaseController getSessionUser sessuserid:" + sessuserid);
                        sessuser = userDao.findById(sessuserid);
                    }
                }
            }
        }
    }
}
