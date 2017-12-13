package com.calculator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calculator.dao.UserDAO;
import com.calculator.model.UserDTO;
import com.calculator.model.UserSession;
import com.calculator.model.UserVO;
import com.calculator.validator.UserValidator;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private UserSession userSession;
	
	@Override
	public String saveUser(UserVO user) {
		if (!UserValidator.validate(user.getUsername(), user.getPassword())) {
			return "username or password is invalid";
		}
		UserDTO userDB = userDAO.findUserByUsername(user.getUsername());
		if (userDB != null) {
			return "username existed";
		}
		if (userDAO.saveUser(user)) {
			return user.getUsername();
		}
		return "cannot register username";
	}

	@Override
	public String login(String username, String password) {
		String result = "username or password is invalid";
		if (UserValidator.validate(username, password)) {
			if (userSession.getUserId() > 0) {
				if (username.equals(userSession.getUsername())) {
					result = "The user logged in to system";
				} else {
					result = "Other user is logining";
				}
			} else {
				UserDTO userInfo = userDAO.findUserByUsername(username);
				if (userInfo == null) {
					result = "The user does not exist";
				} else {
					String pwDB = userInfo.getPassword();
					if (password.equalsIgnoreCase(pwDB)) {
						userSession.setUserId(userInfo.getUserId());
						userSession.setUsername(userInfo.getUsername());
						result = "OK";
					} else {
						result = "Wrong password";
					}
				}
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
		if (userSession.getUserId() > 0) {
			userSession.setUserId(0);
			userSession.setUsername("");
		}
	}

}
