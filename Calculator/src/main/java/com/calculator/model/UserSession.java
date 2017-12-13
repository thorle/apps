package com.calculator.model;

import org.springframework.stereotype.Component;

@Component
public class UserSession {

	private int userId;
	private String username;
	
	public UserSession() {
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
