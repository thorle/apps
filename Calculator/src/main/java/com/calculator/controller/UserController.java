package com.calculator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.calculator.model.UserVO;
import com.calculator.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestBody UserVO user) {
		return userService.login(user.getUsername(), user.getPassword());
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String register(@RequestBody UserVO user) {
		return userService.saveUser(user);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		return userService.logout();
	}

}
