package com.calculator.dao;

import java.util.List;

import com.calculator.model.OperationDTO;
import com.calculator.model.OperationTypeDTO;

public interface OperationDAO {

	boolean saveOperation(OperationDTO operation);

	List<OperationDTO> getAllOperation();

	List<OperationTypeDTO> getAllOperationType();

}
