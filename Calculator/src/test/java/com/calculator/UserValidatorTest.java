package com.calculator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.calculator.CalculatorApplication;
import com.calculator.validator.UserValidator;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CalculatorApplication.class)
public class UserValidatorTest {

	@Test
	public void testValidUsername() {
		boolean isValid = UserValidator.validate("admin", "admin1");
		assertTrue(isValid);
	}

	@Test
	public void testValidPassword() {
		boolean isValid = UserValidator.validate("admin", "admin1");
		assertTrue(isValid);
	}

	@Test
	public void testInvalidUsername() {
		boolean isValid = UserValidator.validate("ad", "admin1");
		assertFalse(isValid);
	}

	@Test
	public void testInvalidPassword() {
		boolean isValid = UserValidator.validate("admin", "adm");
		assertFalse(isValid);
	}
}
