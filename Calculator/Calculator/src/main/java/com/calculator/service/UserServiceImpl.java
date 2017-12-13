package com.calculator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calculator.cache.UserSession;
import com.calculator.dao.UserDAO;
import com.calculator.model.UserVO;
import com.calculator.model.UserDTO;
import com.calculator.validator.UserValidator;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private UserSession userSession;

	@Override
	public boolean saveUser(UserVO user) {
		return userDAO.saveUser(user);
	}

	@Override
	public String login(String username, String password) {
		String result = "";
		if (UserValidator.validate(username, password)) {
			if (userSession.getUserId() > 0) {
				if (username.equals(userSession.getUsername())) {
					result = "The user logined";
				} else {
					result = "Other user is logining";
				}
			}
			UserDTO userInfo = userDAO.findUserByUsername(username);
			if (userInfo == null) {
				result = "The user does not exist";
			}
			String pwDB = userInfo.getPassword();
			if (password.equalsIgnoreCase(pwDB)) {
				userSession.setUserId(userInfo.getUserId());
				userSession.setUsername(userInfo.getUsername());
				result = "OK";
			}
		}
		return result;
	}

	@Override
	public String logout() {
		clearUserSession();
		return "OK";
	}

	private void clearUserSession() {
		userSession.setUserId(0);
		userSession.setUsername("");
	}

}
