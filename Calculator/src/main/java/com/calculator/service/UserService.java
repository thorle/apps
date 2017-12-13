package com.calculator.service;

import com.calculator.model.UserVO;

public interface UserService {

	public String logout();

	public String login(String username, String password);

	public String saveUser(UserVO user);

}
