package com.calculator.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.calculator.model.OperationDTO;

@Repository
public class OperationDAOImpl implements OperationDAO {

	@Override
	public boolean saveOperation(OperationDTO operation) {
		return false;
	}

	@Override
	public List<OperationDTO> getAll() {
		return null;
	}

}
