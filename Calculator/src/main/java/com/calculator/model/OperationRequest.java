package com.calculator.model;

public class OperationRequest {
	private int firstNumber;
	private int secondNumber;
	private String operationName;

	public OperationRequest() {
	}

	public OperationRequest(int firstNumber, int secondNumber, String operationName) {
		this.firstNumber = firstNumber;
		this.secondNumber = secondNumber;
		this.operationName = operationName;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public int getFirstNumber() {
		return firstNumber;
	}

	public void setFirstNumber(int firstNumber) {
		this.firstNumber = firstNumber;
	}

	public int getSecondNumber() {
		return secondNumber;
	}

	public void setSecondNumber(int secondNumber) {
		this.secondNumber = secondNumber;
	}
}
