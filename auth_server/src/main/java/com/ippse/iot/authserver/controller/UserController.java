package com.ippse.iot.authserver.controller;

import com.ippse.iot.authserver.dao.UserRepository;
import com.ippse.iot.authserver.util.UnifyResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController extends BaseApiController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user")
    public UnifyResult user() {
        return UnifyResult.ok().data("user", sessuser);
    }

	/*@GetMapping("/user/{userid}")
	public User findById(@PathVariable("userid") String userid) {
		return userRepository.findById(userid).orElseThrow(() -> new RuntimeException("user not exist"));
	}*/

}
