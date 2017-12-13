package com.calculator;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.calculator.CalculatorApplication;
import com.calculator.validator.OperationValidator;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CalculatorApplication.class)
public class OperationValidatorTest {

	@Test
	public void testValidNumber() {
		boolean isInValid = OperationValidator.isNumberInValid(1);
		assertFalse(isInValid);
	}

	@Test
	public void testInvalidNumber() {
		boolean isInValid = OperationValidator.isNumberInValid(0);
		assertTrue(isInValid);
	}
}
