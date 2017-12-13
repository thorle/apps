package com.calculator.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.calculator.cache.UserSession;
import com.calculator.dao.OperationDAO;
import com.calculator.model.OperationDTO;
import com.calculator.model.OperatorType;

public class OperationServiceImpl implements OperationService {

	@Autowired
	private UserSession userSession;

	@Autowired
	private OperationDAO operationDAO;

	@Override
	public boolean calcalate(int firstNumber, int secondNumber, OperatorType method) {
		int result = getResult(firstNumber, secondNumber, method);
		OperationDTO operationDTO = new OperationDTO(getUserId(), method.getCode(), firstNumber, secondNumber, result);
		return operationDAO.saveOperation(operationDTO);
	}

	private int getResult(int firstNumber, int secondNumber, OperatorType method) {
		int result = 0;
		switch (method) {
		case ADD:
			result = firstNumber + secondNumber;
			break;
		case SUBTRACT:
			result = firstNumber - secondNumber;
			break;
		case MULTIPLY:
			result = firstNumber * secondNumber;
			break;
		case DIVIDE:
			result = firstNumber / secondNumber;
			break;
		default:
			break;
		}
		return result;
	}

	private int getUserId() {
		return userSession.getUserId();
	}

}
