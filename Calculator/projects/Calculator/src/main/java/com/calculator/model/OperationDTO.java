package com.calculator.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OperationDTO {
	private int userId;
	private int operationId;
	private String operationName;
	private int firstNumber;
	private int secondNumber;
	private int result;
	
	public OperationDTO() {
	}

	public OperationDTO(int userId, int operationId, String operationName, int firstNumber, int secondNumber,
			int result) {
		this.userId = userId;
		this.operationId = operationId;
		this.operationName = operationName;
		this.firstNumber = firstNumber;
		this.secondNumber = secondNumber;
		this.result = result;
	}

	public OperationDTO(ResultSet row, int rowNum) throws SQLException {
		this.userId = row.getInt("user_id");
		this.operationId = row.getInt("operation_id");
		this.operationName = row.getString("operation_name");
		this.firstNumber = row.getInt("first_number");
		this.secondNumber = row.getInt("second_number");
		this.result = row.getInt("result");
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

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
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + firstNumber;
		result = prime * result + operationId;
		result = prime * result + this.result;
		result = prime * result + secondNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OperationDTO other = (OperationDTO) obj;
		if (firstNumber != other.firstNumber)
			return false;
		if (operationId != other.operationId)
			return false;
		if (result != other.result)
			return false;
		if (secondNumber != other.secondNumber)
			return false;
		return true;
	}

	public void setResult(int result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "OperationDTO [userId=" + userId + ", operationId=" + operationId + ", operationName=" + operationName
				+ ", firstNumber=" + firstNumber + ", secondNumber=" + secondNumber + ", result=" + result + "]";
	}

}
