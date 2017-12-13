package com.calculator.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDTO {
	private int userId;
	private String username;
	private String password;

	public UserDTO() {
	}

	public UserDTO(ResultSet row, int rowNum) throws SQLException {
		this.userId = row.getInt("user_id");
		this.username = row.getString("username");
		this.password = row.getString("password");
	}

	public UserDTO(int userId, String username, String password) {
		this.userId = userId;
		this.username = username;
		this.password = password;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
