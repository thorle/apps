package com.calculator.dao;

import com.calculator.model.UserDTO;
import com.calculator.model.UserVO;

public interface UserDAO {
	public boolean saveUser(UserVO user);

	public UserDTO findUserByUsername(String username);
}
