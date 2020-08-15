package com.ippse.iot.authserver.services;

import com.ippse.iot.authserver.dao.UserDao;
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
import java.util.Set;

@Slf4j
@Service("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("传入的username={}", username);
        if (!StringUtils.isNotBlank(username)) {
            throw new BadCredentialsException("Username must be provided");
        }
        String lowcaseUsername = username.trim().toLowerCase();
        User user = userDao.findByUsername(lowcaseUsername);

        if (user != null) {
            /* return new UserPrincipal(user); */
            List<SimpleGrantedAuthority> authList = getAuthorities(user.getRoles());
            // String encodedPassword = passwordEncoder.encode(password);
            org.springframework.security.core.userdetails.User userPrincipal = new org.springframework.security.core.userdetails.User(
                    username, user.getPassword(), authList);
            return userPrincipal;
        } else {
            throw new BadCredentialsException(String.format("No user found with username '%s'.", username));
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
