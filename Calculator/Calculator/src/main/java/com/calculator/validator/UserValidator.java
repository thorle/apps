package com.calculator.validator;

public class UserValidator {

	public static final int MIN_USERNAME_LENGTH = 3;
	public static final int MIN_PASSWORD_LENGTH = 4;

	public static boolean validate(String username, String password) {
		if (username == null || username.length() < MIN_USERNAME_LENGTH) {
			return false;
		}

		if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
			return false;
		}

		return true;
	}

}
