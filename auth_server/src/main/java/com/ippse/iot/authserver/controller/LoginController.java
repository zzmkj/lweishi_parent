package com.ippse.iot.authserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login() {
        log.info("login redirect==========");
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("error", true);
        log.info("login Error redirect==========");
        return "login";
    }

	@RequestMapping("/exit")
	public void exit(HttpServletRequest request, HttpServletResponse response, String redirect) {
		// token can be revoked here if needed
		new SecurityContextLogoutHandler().logout(request, null, null);
		try {
			log.info("exit redirect==========" + redirect);
			response.sendRedirect(redirect);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
