package com.calculator.calculate;

import org.springframework.stereotype.Component;

@Component(Subtract.CODE)
public class Subtract implements Calculate {

	public static final String CODE = "SUBTRACT";

	@Override
	public int calculate(int firstNumber, int secondNumber) {
		return firstNumber - secondNumber;
	}

}
