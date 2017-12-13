package com.calculator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class OperationDTO {
	private int userId;
	private int operationId;
	private int firstNumber;
	private int secondNumber;
	private int result;
	

	public OperationDTO(int userId, int operationId, int firstNumber, int secondNumber, int result) {
		this.userId = userId;
		this.operationId = operationId;
		this.firstNumber = firstNumber;
		this.secondNumber = secondNumber;
		this.result = result;
	}

	@JsonIgnore
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@JsonIgnore
	public int getOperationId() {
		return operationId;
	}

	public void setOperationId(int operationId) {
		this.operationId = operationId;
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

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

}
