package com.calculator.util;

import com.calculator.model.OperatorType;

public class Main {
	public static void main(String[] args) {
		OperatorType operatorType = OperatorType.SUBTRACT;
		operatorType.getCode();
		System.out.println("Method:" + operatorType.getCode());
	}
}
