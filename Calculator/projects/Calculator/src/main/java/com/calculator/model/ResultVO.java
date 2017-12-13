package com.calculator.model;

public class ResultVO {
	private String code;
	private String message;

	public ResultVO(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
