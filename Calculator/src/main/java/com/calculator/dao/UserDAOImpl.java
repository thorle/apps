package com.calculator.dao;

import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.calculator.model.UserDTO;
import com.calculator.model.UserVO;

@Transactional
@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private JdbcTemplate jdbc;

	public static final String FIND_USER_BY_USERNAME = "SELECT user_id, username, password " + "FROM user "
			+ "WHERE username =?";
	public static final String SAVE_USER = "INSERT INTO user (username, password) VALUES(?,?)";

	@Override
	public boolean saveUser(UserVO user) {
		return jdbc.update(SAVE_USER, new Object[] { user.getUsername(), user.getPassword() }) > 0;
	}

	@Override
	public UserDTO findUserByUsername(String username) {
		return jdbc.query(FIND_USER_BY_USERNAME, (ResultSet row, int rowNum) -> {
			return new UserDTO(row, rowNum);
		}, new Object[] { username }).stream().findFirst().orElse(null);
	}

}
