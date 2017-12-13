package com.calculator.model;

public enum ErrorCode {

	INVALID_VALUE("C001", "value is invalid"), OPERATION_PERMISSION("C002",
			"user don't have permission for performing this operation");

	private final String errorCode;
	private final String message;

	ErrorCode(String errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getMessage() {
		return message;
	}

}
