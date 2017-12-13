package com.calculator.cache;

public class UserSession {

	private int userId;
	private String username;

	public UserSession(int userId, String username) {
		this.userId = userId;
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
