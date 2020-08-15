package com.ippse.iot.authserver.config;

import com.ippse.iot.authserver.dao.UserRepository;
import com.ippse.iot.authserver.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
//@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("requestURL==" + request.getRequestURL().toString() + "?" + request.getQueryString());
        String username = StringUtils.substringBetween(authentication.getPrincipal().toString(), "Username: ", "; Password");
        log.info("username = " + username);
        if (StringUtils.isNotBlank(username)) {
            Optional<User> userOptional = userRepository.findOptionalByUsernameOrMobile(username);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                List<String> history = user.getHistory();
                if (history == null) {
                    history = new ArrayList<>();
                }
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                history.add(LocalDateTime.now().format(formatter));
                user.setHistory(history);
                userRepository.save(user);
            }
        }

        response.sendRedirect("http://localhost:10004/index");
    }

}
