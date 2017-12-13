package com.calculator.model;

public class OperationVO {
	private int firstNumber;
	private int secondNumber;
	private OperatorType operatorType;
	private int result;

	public OperationVO(OperationDTO operationDTO) {
		this.firstNumber = operationDTO.getFirstNumber();
		this.secondNumber = operationDTO.getSecondNumber();
		this.operatorType = OperatorType.ADD;
		this.result = operationDTO.getResult();
	}

	public OperatorType getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(OperatorType operatorType) {
		this.operatorType = operatorType;
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
