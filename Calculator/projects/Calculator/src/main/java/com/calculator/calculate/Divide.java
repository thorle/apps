package com.calculator.calculate;

import org.springframework.stereotype.Component;

@Component(Divide.CODE)
public class Divide implements Calculate {
	public static final String CODE = "DIVIDE";

	@Override
	public int calculate(int firstNumber, int secondNumber) {
		return firstNumber / secondNumber;
	}

}
