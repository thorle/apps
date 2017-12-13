package com.calculator.model;

public enum OperatorType {
	ADD(1), SUBTRACT(2), MULTIPLY(3), DIVIDE(4);

	private int code;

	OperatorType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
