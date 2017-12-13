package com.calculator.calculate;

import org.springframework.stereotype.Component;

@Component(Multiply.CODE)
public class Multiply implements Calculate {
	public static final String CODE = "MULTIPLY";

	@Override
	public int calculate(int firstNumber, int secondNumber) {
		return firstNumber * secondNumber;
	}

}
