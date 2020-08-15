package com.ippse.iot.authserver.services;

import com.ippse.iot.authserver.dao.UserRepository;
import com.ippse.iot.authserver.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service("wxUserDetailsService")
public class WxUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String openid) throws UsernameNotFoundException {
        log.info("传入的wxopenid={}", openid);
        if (!StringUtils.isNotBlank(openid)) {
            throw new BadCredentialsException("openid must be provided");
        }

        Optional<User> userOptional = userRepository.findByWxopenid(openid);

        if (userOptional.isPresent()) {
            /* return new UserPrincipal(user); */
            User user = userOptional.get();
            log.info("【该微信已绑定】 = {}", user);
            List<SimpleGrantedAuthority> authList = getAuthorities(user.getRoles());
            // String encodedPassword = passwordEncoder.encode(password);
            org.springframework.security.core.userdetails.User userPrincipal = new org.springframework.security.core.userdetails.User(user.getUsername(), openid, authList);
            return userPrincipal;
        } else {
            log.info("【没有绑定】");
            throw new BadCredentialsException(String.format("No user found with openid '%s'.", openid));
        }
    }

    private List<SimpleGrantedAuthority> getAuthorities(Set<String> roles) {
        List<SimpleGrantedAuthority> authList = new ArrayList<>();
        authList.add(new SimpleGrantedAuthority("ROLE_USER"));

        for (String role : roles) {
            if (StringUtils.equals(role, "admin")) {
                authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                break;
            }
        }

        return authList;
    }

}
