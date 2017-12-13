package com.calculator.service;

import com.calculator.model.OperatorType;

public interface OperationService {

	public boolean calcalate(int firstNumber, int secondNumber, OperatorType method);

}
