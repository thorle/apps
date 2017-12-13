package com.calculator.service;

import java.util.List;

import com.calculator.model.OperationRequest;
import com.calculator.model.OperationVO;

public interface OperationService {

	public OperationVO calculate(OperationRequest operationRq);

	public List<OperationVO> operationHistory();

}
