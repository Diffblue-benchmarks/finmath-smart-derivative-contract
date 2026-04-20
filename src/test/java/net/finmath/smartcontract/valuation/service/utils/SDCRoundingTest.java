package net.finmath.smartcontract.valuation.service.utils;

import org.junit.jupiter.api.Test;

import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SDCRoundingTest {

	@Test
	void testConstructorAndRoundDouble() {
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

		double result = rounding.roundDouble(3.456);
		assertEquals(3.46, result, 0.0001, "Should round to 2 decimal places with HALF_UP");
	}

	@Test
	void testConstructorWithDifferentScale() {
		SDCRounding rounding = new SDCRounding(4, RoundingMode.HALF_DOWN);

		double result = rounding.roundDouble(1.23456789);
		assertEquals(1.2346, result, 0.00001, "Should round to 4 decimal places with HALF_DOWN");
	}

	@Test
	void testGetRoundedValueAsIntegerString() {
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

		String result = rounding.getRoundedValueAsIntegerString(12.345);
		assertEquals("1235", result, "Should return integer string representation of rounded value");
	}
}
