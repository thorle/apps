package com.calculator.dao;

import java.util.List;

import com.calculator.model.OperationDTO;

public interface OperationDAO {
	public boolean saveOperation(OperationDTO operation);

	public List<OperationDTO> getAll();
}
