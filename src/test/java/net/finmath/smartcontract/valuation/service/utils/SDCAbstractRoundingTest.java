package net.finmath.smartcontract.valuation.service.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SDCAbstractRoundingTest {

	private TestRounding rounding;

	@BeforeEach
	void setUp() {
		rounding = new TestRounding(2, RoundingMode.HALF_UP);
	}

	@Test
	void testRoundDouble() {
		// Given
		final double value = 123.456;

		// When
		final double result = rounding.roundDouble(value);

		// Then
		assertEquals(123.46, result, 0.0001);
	}

	@Test
	void testRoundDoubleNegativeValue() {
		// Given
		final double value = -123.456;

		// When
		final double result = rounding.roundDouble(value);

		// Then
		assertEquals(-123.46, result, 0.0001);
	}

	@Test
	void testGetRoundedValueAsIntegerString() {
		// Given
		final double value = 123.45;

		// When
		final String result = rounding.getRoundedValueAsIntegerString(value);

		// Then
		assertEquals("12345", result);
	}

	@Test
	void testGetRoundedValueAsIntegerStringWithRounding() {
		// Given
		final double value = 123.456;

		// When
		final String result = rounding.getRoundedValueAsIntegerString(value);

		// Then
		assertEquals("12346", result);
	}

	@Test
	void testGetRoundedValueAsIntegerStringNegative() {
		// Given
		final double value = -123.45;

		// When
		final String result = rounding.getRoundedValueAsIntegerString(value);

		// Then
		assertEquals("-12345", result);
	}

	@Test
	void testGetDoubleFromIntegerStringSingleDigit() {
		// Given
		final String value = "5";

		// When
		final double result = rounding.getDoubleFromIntegerString(value);

		// Then
		assertEquals(0.05, result, 0.0001);
	}

	@Test
	void testGetDoubleFromIntegerStringTwoDigits() {
		// Given
		final String value = "25";

		// When
		final double result = rounding.getDoubleFromIntegerString(value);

		// Then
		assertEquals(0.25, result, 0.0001);
	}

	@Test
	void testGetDoubleFromIntegerStringMultipleDigits() {
		// Given
		final String value = "12345";

		// When
		final double result = rounding.getDoubleFromIntegerString(value);

		// Then
		assertEquals(123.45, result, 0.0001);
	}

	@Test
	void testGetDoubleFromIntegerStringLargeValue() {
		// Given
		final String value = "123456789";

		// When
		final double result = rounding.getDoubleFromIntegerString(value);

		// Then
		assertEquals(1234567.89, result, 0.01);
	}

	@Test
	void testRoundTripConversion() {
		// Given
		final double originalValue = 987.65;

		// When
		final String intString = rounding.getRoundedValueAsIntegerString(originalValue);
		final double result = rounding.getDoubleFromIntegerString(intString);

		// Then
		assertEquals(originalValue, result, 0.0001);
	}

	/**
	 * Concrete implementation of SDCAbstractRounding for testing purposes.
	 */
	private static class TestRounding extends SDCAbstractRounding {
		TestRounding(int scale, RoundingMode roundingMode) {
			this.scale = scale;
			this.roundingMode = roundingMode;
		}
	}
}
