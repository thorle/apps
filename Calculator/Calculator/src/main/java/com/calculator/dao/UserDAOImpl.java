package com.calculator.dao;

import org.springframework.stereotype.Repository;

import com.calculator.model.UserVO;
import com.calculator.model.UserDTO;

@Repository
public class UserDAOImpl implements UserDAO {

	@Override
	public boolean saveUser(UserVO user) {
		return true;
	}

	@Override
	public UserDTO findUserByUsername(String username) {
		return new UserDTO(1, "admin", "admin");
	}

}
