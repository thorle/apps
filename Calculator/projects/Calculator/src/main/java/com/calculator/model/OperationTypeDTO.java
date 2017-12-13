package com.calculator.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OperationTypeDTO {
	private int operationId;
	private String operationName;

	public OperationTypeDTO(ResultSet rs, int row) throws SQLException {
		this.operationId = rs.getInt("operation_id");
		this.operationName = rs.getString("operation_name");
	}

	public int getOperationId() {
		return operationId;
	}

	public void setOperationId(int operationId) {
		this.operationId = operationId;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
}
