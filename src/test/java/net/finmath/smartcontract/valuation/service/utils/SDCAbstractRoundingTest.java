package net.finmath.smartcontract.valuation.service.utils;

import org.junit.jupiter.api.Test;

import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SDCAbstractRoundingTest {

	private final SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

	@Test
	void testRoundDouble() {
		double result = rounding.roundDouble(3.14159);
		assertEquals(3.14, result, 0.0001, "Should round to 2 decimal places");
	}

	@Test
	void testRoundDoubleHalfUp() {
		double result = rounding.roundDouble(2.555);
		assertEquals(2.56, result, 0.0001, "Should round half up");
	}

	@Test
	void testGetRoundedValueAsIntegerString() {
		String result = rounding.getRoundedValueAsIntegerString(3.14159);
		assertEquals("314", result, "Should return integer string without decimal point");
	}

	@Test
	void testGetRoundedValueAsIntegerStringZero() {
		String result = rounding.getRoundedValueAsIntegerString(0.05);
		assertEquals("005", result, "Should handle small values");
	}

	@Test
	void testGetDoubleFromIntegerStringSingleDigit() {
		double result = rounding.getDoubleFromIntegerString("5");
		assertEquals(0.05, result, 0.0001, "Single digit should be parsed as 0.05");
	}

	@Test
	void testGetDoubleFromIntegerStringTwoDigits() {
		double result = rounding.getDoubleFromIntegerString("50");
		assertEquals(0.50, result, 0.0001, "Two digits should be parsed as 0.50");
	}

	@Test
	void testGetDoubleFromIntegerStringMultipleDigits() {
		double result = rounding.getDoubleFromIntegerString("314");
		assertEquals(3.14, result, 0.0001, "Three digits should be parsed as 3.14");
	}

	@Test
	void testGetDoubleFromIntegerStringLargeValue() {
		double result = rounding.getDoubleFromIntegerString("123456");
		assertEquals(1234.56, result, 0.0001, "Should handle larger integer strings");
	}
}
