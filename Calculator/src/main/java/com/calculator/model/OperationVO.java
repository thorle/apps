package com.calculator.model;

public class OperationVO {
	private int firstNumber;
	private int secondNumber;
	private String operationType;
	private int result;

	public OperationVO(OperationDTO operationDTO) {
		this.firstNumber = operationDTO.getFirstNumber();
		this.secondNumber = operationDTO.getSecondNumber();
		this.operationType = operationDTO.getOperationName();
		this.result = operationDTO.getResult();
	}

	public String getOperationType() {
		return operationType;
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
