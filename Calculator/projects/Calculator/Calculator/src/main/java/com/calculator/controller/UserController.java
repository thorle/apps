package com.calculator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.calculator.service.UserService;

@RestController
@RequestMapping("/")
public class UserController {
	
	@Autowired
	private UserService userService;

	@ResponseBody
	@RequestMapping("/login")
	public String login(@RequestParam("username") String username, 
						@RequestParam("password") String password) {
		return userService.login(username, password);
	}
	
	@RequestMapping("/logout")
	public String logout() {
		return userService.logout();
	}
	
}
