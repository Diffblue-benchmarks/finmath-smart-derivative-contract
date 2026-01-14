/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.service.utils;

import org.junit.jupiter.api.Test;

import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SDCAbstractRounding.
 * Tests the roundDouble, getRoundedValueAsIntegerString, and getDoubleFromIntegerString methods
 * with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class SDCAbstractRoundingClaudeTest {

	/**
	 * Test roundDouble with positive values and HALF_UP rounding.
	 */
	@Test
	void testRoundDouble_PositiveValue_HalfUp() {
		// Arrange
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

		// Act & Assert
		assertEquals(1.23, rounding.roundDouble(1.234), 0.0, "1.234 should round to 1.23");
		assertEquals(1.24, rounding.roundDouble(1.235), 0.0, "1.235 should round up to 1.24");
		assertEquals(1.24, rounding.roundDouble(1.236), 0.0, "1.236 should round up to 1.24");
	}

	/**
	 * Test roundDouble with negative values and HALF_UP rounding.
	 */
	@Test
	void testRoundDouble_NegativeValue_HalfUp() {
		// Arrange
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

		// Act & Assert
		assertEquals(-1.23, rounding.roundDouble(-1.234), 0.0, "-1.234 should round to -1.23");
		assertEquals(-1.24, rounding.roundDouble(-1.235), 0.0, "-1.235 should round to -1.24");
		assertEquals(-1.24, rounding.roundDouble(-1.236), 0.0, "-1.236 should round to -1.24");
	}

	/**
	 * Test roundDouble with zero.
	 */
	@Test
	void testRoundDouble_Zero() {
		// Arrange
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

		// Act & Assert
		assertEquals(0.0, rounding.roundDouble(0.0), 0.0, "0.0 should remain 0.0");
	}

	/**
	 * Test roundDouble with different scales.
	 */
	@Test
	void testRoundDouble_DifferentScales() {
		// Arrange
		SDCRounding rounding0 = new SDCRounding(0, RoundingMode.HALF_UP);
		SDCRounding rounding1 = new SDCRounding(1, RoundingMode.HALF_UP);
		SDCRounding rounding3 = new SDCRounding(3, RoundingMode.HALF_UP);

		// Act & Assert
		assertEquals(1.0, rounding0.roundDouble(1.234), 0.0, "Scale 0 should round to integer");
		assertEquals(1.2, rounding1.roundDouble(1.234), 0.0, "Scale 1 should round to 1 decimal place");
		assertEquals(1.234, rounding3.roundDouble(1.2344), 0.0, "Scale 3 should round to 3 decimal places");
	}

	/**
	 * Test roundDouble with HALF_DOWN rounding mode.
	 */
	@Test
	void testRoundDouble_HalfDown() {
		// Arrange
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_DOWN);

		// Act & Assert
		assertEquals(1.23, rounding.roundDouble(1.234), 0.0, "1.234 should round to 1.23");
		assertEquals(1.23, rounding.roundDouble(1.235), 0.0, "1.235 should round down to 1.23 with HALF_DOWN");
		assertEquals(1.24, rounding.roundDouble(1.236), 0.0, "1.236 should round up to 1.24");
	}

	/**
	 * Test roundDouble with UP rounding mode.
	 */
	@Test
	void testRoundDouble_Up() {
		// Arrange
		SDCRounding rounding = new SDCRounding(2, RoundingMode.UP);

		// Act & Assert
		assertEquals(1.24, rounding.roundDouble(1.234), 0.0, "1.234 should round up to 1.24 with UP");
		assertEquals(1.24, rounding.roundDouble(1.235), 0.0, "1.235 should round up to 1.24 with UP");
	}

	/**
	 * Test roundDouble with DOWN rounding mode.
	 */
	@Test
	void testRoundDouble_Down() {
		// Arrange
		SDCRounding rounding = new SDCRounding(2, RoundingMode.DOWN);

		// Act & Assert
		assertEquals(1.23, rounding.roundDouble(1.234), 0.0, "1.234 should round down to 1.23 with DOWN");
		assertEquals(1.23, rounding.roundDouble(1.239), 0.0, "1.239 should round down to 1.23 with DOWN");
	}

	/**
	 * Test roundDouble with very small positive values.
	 */
	@Test
	void testRoundDouble_VerySmallPositiveValue() {
		// Arrange
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

		// Act & Assert
		assertEquals(0.01, rounding.roundDouble(0.005), 0.0, "0.005 should round to 0.01");
		assertEquals(0.0, rounding.roundDouble(0.004), 0.0, "0.004 should round to 0.00");
	}

	/**
	 * Test roundDouble with very small negative values.
	 */
	@Test
	void testRoundDouble_VerySmallNegativeValue() {
		// Arrange
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

		// Act & Assert
		assertEquals(-0.01, rounding.roundDouble(-0.005), 0.0, "-0.005 should round to -0.01");
		assertEquals(0.0, rounding.roundDouble(-0.004), 0.0, "-0.004 should round to 0.00");
	}

	/**
	 * Test roundDouble with large values.
	 */
	@Test
	void testRoundDouble_LargeValues() {
		// Arrange
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

		// Act & Assert
		assertEquals(12345.68, rounding.roundDouble(12345.6789), 0.0, "Large value should be rounded correctly");
		// Note: very large values may be represented in scientific notation
		double result = rounding.roundDouble(9999999.9999);
		assertEquals(10000000.00, result, 0.01, "Very large value should be rounded correctly");
	}

	/**
	 * Test getRoundedValueAsIntegerString with positive values.
	 */
	@Test
	void testGetRoundedValueAsIntegerString_PositiveValue() {
		// Arrange
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

		// Act & Assert
		assertEquals("123", rounding.getRoundedValueAsIntegerString(1.234), "1.234 rounded to scale 2 should be '123'");
		assertEquals("124", rounding.getRoundedValueAsIntegerString(1.235), "1.235 rounded to scale 2 should be '124'");
	}

	/**
	 * Test getRoundedValueAsIntegerString with negative values.
	 */
	@Test
	void testGetRoundedValueAsIntegerString_NegativeValue() {
		// Arrange
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

		// Act & Assert
		assertEquals("-123", rounding.getRoundedValueAsIntegerString(-1.234), "-1.234 rounded to scale 2 should be '-123'");
		assertEquals("-124", rounding.getRoundedValueAsIntegerString(-1.235), "-1.235 rounded to scale 2 should be '-124'");
	}

	/**
	 * Test getRoundedValueAsIntegerString with zero.
	 */
	@Test
	void testGetRoundedValueAsIntegerString_Zero() {
		// Arrange
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

		// Act & Assert
		assertEquals("000", rounding.getRoundedValueAsIntegerString(0.0), "0.0 rounded to scale 2 should be '000'");
	}

	/**
	 * Test getRoundedValueAsIntegerString with different scales.
	 */
	@Test
	void testGetRoundedValueAsIntegerString_DifferentScales() {
		// Arrange
		SDCRounding rounding0 = new SDCRounding(0, RoundingMode.HALF_UP);
		SDCRounding rounding1 = new SDCRounding(1, RoundingMode.HALF_UP);
		SDCRounding rounding3 = new SDCRounding(3, RoundingMode.HALF_UP);

		// Act & Assert
		assertEquals("1", rounding0.getRoundedValueAsIntegerString(1.234), "Scale 0 should produce '1'");
		assertEquals("12", rounding1.getRoundedValueAsIntegerString(1.234), "Scale 1 should produce '12'");
		assertEquals("1234", rounding3.getRoundedValueAsIntegerString(1.2344), "Scale 3 should produce '1234'");
	}

	/**
	 * Test getRoundedValueAsIntegerString with values less than 1.
	 */
	@Test
	void testGetRoundedValueAsIntegerString_ValueLessThanOne() {
		// Arrange
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

		// Act & Assert
		assertEquals("012", rounding.getRoundedValueAsIntegerString(0.12), "0.12 should produce '012'");
		assertEquals("001", rounding.getRoundedValueAsIntegerString(0.005), "0.005 should produce '001'");
	}

	/**
	 * Test getRoundedValueAsIntegerString with large values.
	 */
	@Test
	void testGetRoundedValueAsIntegerString_LargeValue() {
		// Arrange
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

		// Act & Assert
		assertEquals("1234568", rounding.getRoundedValueAsIntegerString(12345.6789), "Large value should produce correct integer string");
	}

	/**
	 * Test getDoubleFromIntegerString with normal length string (scale 2).
	 */
	@Test
	void testGetDoubleFromIntegerString_NormalLength() {
		// Arrange
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

		// Act & Assert
		assertEquals(1.23, rounding.getDoubleFromIntegerString("123"), 0.0, "'123' should convert to 1.23");
		assertEquals(12.34, rounding.getDoubleFromIntegerString("1234"), 0.0, "'1234' should convert to 12.34");
		assertEquals(123.45, rounding.getDoubleFromIntegerString("12345"), 0.0, "'12345' should convert to 123.45");
	}

	/**
	 * Test getDoubleFromIntegerString with length 1 string (scale 2).
	 * This tests the first branch: s.length()==1
	 */
	@Test
	void testGetDoubleFromIntegerString_LengthOne() {
		// Arrange
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

		// Act & Assert
		assertEquals(0.01, rounding.getDoubleFromIntegerString("1"), 0.0, "'1' should convert to 0.01");
		assertEquals(0.05, rounding.getDoubleFromIntegerString("5"), 0.0, "'5' should convert to 0.05");
		assertEquals(0.09, rounding.getDoubleFromIntegerString("9"), 0.0, "'9' should convert to 0.09");
	}

	/**
	 * Test getDoubleFromIntegerString with length 2 string (scale 2).
	 * This tests the second branch: s.length()==2
	 */
	@Test
	void testGetDoubleFromIntegerString_LengthTwo() {
		// Arrange
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

		// Act & Assert
		assertEquals(0.12, rounding.getDoubleFromIntegerString("12"), 0.0, "'12' should convert to 0.12");
		assertEquals(0.99, rounding.getDoubleFromIntegerString("99"), 0.0, "'99' should convert to 0.99");
		assertEquals(0.01, rounding.getDoubleFromIntegerString("01"), 0.0, "'01' should convert to 0.01");
	}

	/**
	 * Test getDoubleFromIntegerString with different scales.
	 * Note: The implementation has limitations with length 1 and 2 strings - see TODO in code.
	 * It hardcodes "0.0" prefix for length 1 and "0." prefix for length 2.
	 */
	@Test
	void testGetDoubleFromIntegerString_DifferentScales() {
		// Arrange
		SDCRounding rounding0 = new SDCRounding(0, RoundingMode.HALF_UP);
		SDCRounding rounding1 = new SDCRounding(1, RoundingMode.HALF_UP);
		SDCRounding rounding3 = new SDCRounding(3, RoundingMode.HALF_UP);

		// Act & Assert - Scale 0
		// For longer strings (length > 2), the method works correctly
		assertEquals(123.0, rounding0.getDoubleFromIntegerString("123"), 0.0, "Scale 0: '123' should convert to 123.0");
		assertEquals(1234.0, rounding0.getDoubleFromIntegerString("1234"), 0.0, "Scale 0: '1234' should convert to 1234.0");

		// Act & Assert - Scale 1
		// For longer strings (length > 2), the method works correctly
		assertEquals(12.3, rounding1.getDoubleFromIntegerString("123"), 0.0, "Scale 1: '123' should convert to 12.3");
		assertEquals(123.4, rounding1.getDoubleFromIntegerString("1234"), 0.0, "Scale 1: '1234' should convert to 123.4");

		// Act & Assert - Scale 3
		assertEquals(1.234, rounding3.getDoubleFromIntegerString("1234"), 0.0, "Scale 3: '1234' should convert to 1.234");
		assertEquals(12.345, rounding3.getDoubleFromIntegerString("12345"), 0.0, "Scale 3: '12345' should convert to 12.345");
	}

	/**
	 * Test getDoubleFromIntegerString with negative values (string starting with '-').
	 */
	@Test
	void testGetDoubleFromIntegerString_NegativeValue() {
		// Arrange
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

		// Act & Assert
		assertEquals(-1.23, rounding.getDoubleFromIntegerString("-123"), 0.0, "'-123' should convert to -1.23");
		assertEquals(-12.34, rounding.getDoubleFromIntegerString("-1234"), 0.0, "'-1234' should convert to -12.34");
	}

	/**
	 * Test getDoubleFromIntegerString with large values.
	 */
	@Test
	void testGetDoubleFromIntegerString_LargeValue() {
		// Arrange
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

		// Act & Assert
		assertEquals(12345.67, rounding.getDoubleFromIntegerString("1234567"), 0.0, "Large string should convert correctly");
	}

	/**
	 * Test getDoubleFromIntegerString with zero represented as "00" for scale 2.
	 */
	@Test
	void testGetDoubleFromIntegerString_ZeroAsString() {
		// Arrange
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

		// Act & Assert
		assertEquals(0.0, rounding.getDoubleFromIntegerString("00"), 0.0, "'00' should convert to 0.0");
		assertEquals(0.0, rounding.getDoubleFromIntegerString("000"), 0.0, "'000' should convert to 0.0");
	}

	/**
	 * Test round-trip conversion: double -> integerString -> double.
	 * Verifies that conversion is symmetric.
	 */
	@Test
	void testRoundTrip_DoubleToStringToDouble() {
		// Arrange
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);
		double original = 1.23;

		// Act
		String intString = rounding.getRoundedValueAsIntegerString(original);
		double result = rounding.getDoubleFromIntegerString(intString);

		// Assert
		assertEquals(original, result, 0.0, "Round-trip conversion should preserve value");
	}

	/**
	 * Test round-trip conversion with various values.
	 */
	@Test
	void testRoundTrip_MultipleValues() {
		// Arrange
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);
		double[] testValues = {0.01, 0.99, 1.00, 10.50, 123.45, 9999.99};

		// Act & Assert
		for (double value : testValues) {
			String intString = rounding.getRoundedValueAsIntegerString(value);
			double result = rounding.getDoubleFromIntegerString(intString);
			assertEquals(value, result, 0.0, "Round-trip for " + value + " should preserve value");
		}
	}

	/**
	 * Test round-trip conversion with scale 3.
	 */
	@Test
	void testRoundTrip_Scale3() {
		// Arrange
		SDCRounding rounding = new SDCRounding(3, RoundingMode.HALF_UP);
		double original = 1.234;

		// Act
		String intString = rounding.getRoundedValueAsIntegerString(original);
		double result = rounding.getDoubleFromIntegerString(intString);

		// Assert
		assertEquals(original, result, 0.0, "Round-trip with scale 3 should preserve value");
	}

	/**
	 * Test getDoubleFromIntegerString with string "0" for scale 2.
	 * This tests the edge case where length is 1.
	 */
	@Test
	void testGetDoubleFromIntegerString_SingleZero() {
		// Arrange
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

		// Act & Assert
		assertEquals(0.0, rounding.getDoubleFromIntegerString("0"), 0.0, "'0' should convert to 0.0");
	}

	/**
	 * Test roundDouble with a value that requires no rounding.
	 */
	@Test
	void testRoundDouble_ValueAlreadyRounded() {
		// Arrange
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

		// Act & Assert
		assertEquals(1.23, rounding.roundDouble(1.23), 0.0, "1.23 should remain 1.23");
		assertEquals(5.00, rounding.roundDouble(5.00), 0.0, "5.00 should remain 5.00");
	}

	/**
	 * Test getRoundedValueAsIntegerString with a value that has no fractional part.
	 */
	@Test
	void testGetRoundedValueAsIntegerString_WholeNumber() {
		// Arrange
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

		// Act & Assert
		assertEquals("500", rounding.getRoundedValueAsIntegerString(5.00), "5.00 should produce '500'");
		assertEquals("1000", rounding.getRoundedValueAsIntegerString(10.00), "10.00 should produce '1000'");
	}

	/**
	 * Test getDoubleFromIntegerString with minimum fractional value for scale 2.
	 */
	@Test
	void testGetDoubleFromIntegerString_MinimumFractionalValue() {
		// Arrange
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

		// Act & Assert
		assertEquals(0.01, rounding.getDoubleFromIntegerString("1"), 0.0, "'1' with scale 2 should be 0.01");
	}

	/**
	 * Test consistency between roundDouble and getRoundedValueAsIntegerString.
	 */
	@Test
	void testConsistency_RoundDoubleAndGetIntegerString() {
		// Arrange
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);
		double value = 1.235;

		// Act
		double rounded = rounding.roundDouble(value);
		String intString = rounding.getRoundedValueAsIntegerString(value);

		// Assert
		// Rounded value is 1.24, so integer string should be "124"
		assertEquals(1.24, rounded, 0.0, "Rounded value should be 1.24");
		assertEquals("124", intString, "Integer string should be '124'");
	}

	/**
	 * Test getDoubleFromIntegerString edge case with scale equal to string length.
	 * When string length equals scale + 1, we're at the boundary between branch conditions.
	 */
	@Test
	void testGetDoubleFromIntegerString_BoundaryLength() {
		// Arrange
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

		// Act & Assert
		// Length 3 is scale (2) + 1, so it uses the else branch
		assertEquals(1.23, rounding.getDoubleFromIntegerString("123"), 0.0, "Length 3 with scale 2 should be 1.23");
	}
}
