package com.calculator.dao;

import com.calculator.model.UserDTO;
import com.calculator.model.UserVO;

public interface UserDAO {

	boolean saveUser(UserVO user);

	UserDTO findUserByUsername(String username);
}
