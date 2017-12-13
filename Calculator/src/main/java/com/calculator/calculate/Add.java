package com.calculator.calculate;

import org.springframework.stereotype.Component;

@Component(Add.CODE)
public class Add implements Calculate {
	public static final String CODE = "ADD";

	@Override
	public int calculate(int firstNumber, int secondNumber) {
		return firstNumber + secondNumber;
	}

}
