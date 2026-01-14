/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.settlement;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for BigDecimalAdapter.
 * Tests all methods with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class BigDecimalAdapterClaudeTest {

	/**
	 * Test default constructor creates a valid instance.
	 */
	@Test
	void testConstructor_CreatesValidInstance() {
		// Arrange & Act
		BigDecimalAdapter adapter = new BigDecimalAdapter();

		// Assert
		assertNotNull(adapter, "Constructor should create a non-null BigDecimalAdapter instance");
	}

	// ========== Tests for marshal method ==========

	/**
	 * Test marshal with a positive BigDecimal value.
	 */
	@Test
	void testMarshal_PositiveValue_ReturnsStringRepresentation() throws Exception {
		// Arrange
		BigDecimalAdapter adapter = new BigDecimalAdapter();
		BigDecimal value = new BigDecimal("123.45");

		// Act
		String result = adapter.marshal(value);

		// Assert
		assertEquals("123.45", result, "marshal should return string representation of positive BigDecimal");
	}

	/**
	 * Test marshal with a negative BigDecimal value.
	 */
	@Test
	void testMarshal_NegativeValue_ReturnsStringRepresentation() throws Exception {
		// Arrange
		BigDecimalAdapter adapter = new BigDecimalAdapter();
		BigDecimal value = new BigDecimal("-987.65");

		// Act
		String result = adapter.marshal(value);

		// Assert
		assertEquals("-987.65", result, "marshal should return string representation of negative BigDecimal");
	}

	/**
	 * Test marshal with zero BigDecimal value.
	 */
	@Test
	void testMarshal_ZeroValue_ReturnsStringRepresentation() throws Exception {
		// Arrange
		BigDecimalAdapter adapter = new BigDecimalAdapter();
		BigDecimal value = BigDecimal.ZERO;

		// Act
		String result = adapter.marshal(value);

		// Assert
		assertEquals("0", result, "marshal should return '0' for BigDecimal.ZERO");
	}

	/**
	 * Test marshal with null value - tests the branch condition (value != null).
	 */
	@Test
	void testMarshal_NullValue_ReturnsNull() throws Exception {
		// Arrange
		BigDecimalAdapter adapter = new BigDecimalAdapter();

		// Act
		String result = adapter.marshal(null);

		// Assert
		assertNull(result, "marshal should return null when input is null");
	}

	/**
	 * Test marshal with a very large BigDecimal value.
	 */
	@Test
	void testMarshal_VeryLargeValue_ReturnsStringRepresentation() throws Exception {
		// Arrange
		BigDecimalAdapter adapter = new BigDecimalAdapter();
		BigDecimal value = new BigDecimal("999999999999999999.123456789");

		// Act
		String result = adapter.marshal(value);

		// Assert
		assertEquals("999999999999999999.123456789", result,
				"marshal should return string representation of very large BigDecimal");
	}

	/**
	 * Test marshal with a very small BigDecimal value (close to zero).
	 * Note: BigDecimal.toString() may use scientific notation for very small values.
	 */
	@Test
	void testMarshal_VerySmallValue_ReturnsStringRepresentation() throws Exception {
		// Arrange
		BigDecimalAdapter adapter = new BigDecimalAdapter();
		BigDecimal value = new BigDecimal("0.000000001");

		// Act
		String result = adapter.marshal(value);

		// Assert
		// BigDecimal.toString() returns "1E-9" for this value
		assertNotNull(result, "marshal should return non-null string");
		// Verify the unmarshalled value is equal to the original
		assertEquals(value, new BigDecimal(result),
				"marshalled string should be parseable back to the same BigDecimal value");
	}

	/**
	 * Test marshal with BigDecimal created from integer.
	 */
	@Test
	void testMarshal_IntegerValue_ReturnsStringRepresentation() throws Exception {
		// Arrange
		BigDecimalAdapter adapter = new BigDecimalAdapter();
		BigDecimal value = new BigDecimal(42);

		// Act
		String result = adapter.marshal(value);

		// Assert
		assertEquals("42", result, "marshal should return string representation of integer BigDecimal");
	}

	/**
	 * Test marshal with BigDecimal that has trailing zeros.
	 */
	@Test
	void testMarshal_ValueWithTrailingZeros_ReturnsStringWithZeros() throws Exception {
		// Arrange
		BigDecimalAdapter adapter = new BigDecimalAdapter();
		BigDecimal value = new BigDecimal("100.00");

		// Act
		String result = adapter.marshal(value);

		// Assert
		assertEquals("100.00", result,
				"marshal should return string representation preserving trailing zeros");
	}

	/**
	 * Test marshal with scientific notation BigDecimal.
	 */
	@Test
	void testMarshal_ScientificNotation_ReturnsStringRepresentation() throws Exception {
		// Arrange
		BigDecimalAdapter adapter = new BigDecimalAdapter();
		BigDecimal value = new BigDecimal("1.23E+10");

		// Act
		String result = adapter.marshal(value);

		// Assert
		assertNotNull(result, "marshal should return non-null string for scientific notation BigDecimal");
		// The actual format depends on BigDecimal's toString implementation
		assertTrue(result.equals("1.23E+10") || result.equals("12300000000"),
				"marshal should return valid string representation of scientific notation BigDecimal");
	}

	// ========== Tests for unmarshal method ==========

	/**
	 * Test unmarshal with a valid positive numeric string.
	 */
	@Test
	void testUnmarshal_PositiveNumericString_ReturnsBigDecimal() throws Exception {
		// Arrange
		BigDecimalAdapter adapter = new BigDecimalAdapter();
		String input = "123.45";

		// Act
		BigDecimal result = adapter.unmarshal(input);

		// Assert
		assertNotNull(result, "unmarshal should return non-null BigDecimal");
		assertEquals(new BigDecimal("123.45"), result,
				"unmarshal should return correct BigDecimal for positive numeric string");
	}

	/**
	 * Test unmarshal with a valid negative numeric string.
	 */
	@Test
	void testUnmarshal_NegativeNumericString_ReturnsBigDecimal() throws Exception {
		// Arrange
		BigDecimalAdapter adapter = new BigDecimalAdapter();
		String input = "-987.65";

		// Act
		BigDecimal result = adapter.unmarshal(input);

		// Assert
		assertNotNull(result, "unmarshal should return non-null BigDecimal");
		assertEquals(new BigDecimal("-987.65"), result,
				"unmarshal should return correct BigDecimal for negative numeric string");
	}

	/**
	 * Test unmarshal with zero string.
	 */
	@Test
	void testUnmarshal_ZeroString_ReturnsBigDecimal() throws Exception {
		// Arrange
		BigDecimalAdapter adapter = new BigDecimalAdapter();
		String input = "0";

		// Act
		BigDecimal result = adapter.unmarshal(input);

		// Assert
		assertNotNull(result, "unmarshal should return non-null BigDecimal");
		assertEquals(BigDecimal.ZERO, result, "unmarshal should return BigDecimal.ZERO for '0' string");
	}

	/**
	 * Test unmarshal with integer string (no decimal point).
	 */
	@Test
	void testUnmarshal_IntegerString_ReturnsBigDecimal() throws Exception {
		// Arrange
		BigDecimalAdapter adapter = new BigDecimalAdapter();
		String input = "42";

		// Act
		BigDecimal result = adapter.unmarshal(input);

		// Assert
		assertNotNull(result, "unmarshal should return non-null BigDecimal");
		assertEquals(new BigDecimal("42"), result,
				"unmarshal should return correct BigDecimal for integer string");
	}

	/**
	 * Test unmarshal with very large numeric string.
	 */
	@Test
	void testUnmarshal_VeryLargeNumericString_ReturnsBigDecimal() throws Exception {
		// Arrange
		BigDecimalAdapter adapter = new BigDecimalAdapter();
		String input = "999999999999999999.123456789";

		// Act
		BigDecimal result = adapter.unmarshal(input);

		// Assert
		assertNotNull(result, "unmarshal should return non-null BigDecimal");
		assertEquals(new BigDecimal("999999999999999999.123456789"), result,
				"unmarshal should return correct BigDecimal for very large numeric string");
	}

	/**
	 * Test unmarshal with very small numeric string (close to zero).
	 */
	@Test
	void testUnmarshal_VerySmallNumericString_ReturnsBigDecimal() throws Exception {
		// Arrange
		BigDecimalAdapter adapter = new BigDecimalAdapter();
		String input = "0.000000001";

		// Act
		BigDecimal result = adapter.unmarshal(input);

		// Assert
		assertNotNull(result, "unmarshal should return non-null BigDecimal");
		assertEquals(new BigDecimal("0.000000001"), result,
				"unmarshal should return correct BigDecimal for very small numeric string");
	}

	/**
	 * Test unmarshal with scientific notation string.
	 */
	@Test
	void testUnmarshal_ScientificNotationString_ReturnsBigDecimal() throws Exception {
		// Arrange
		BigDecimalAdapter adapter = new BigDecimalAdapter();
		String input = "1.23E+10";

		// Act
		BigDecimal result = adapter.unmarshal(input);

		// Assert
		assertNotNull(result, "unmarshal should return non-null BigDecimal");
		assertEquals(new BigDecimal("1.23E+10"), result,
				"unmarshal should return correct BigDecimal for scientific notation string");
	}

	/**
	 * Test unmarshal with string containing trailing zeros.
	 */
	@Test
	void testUnmarshal_StringWithTrailingZeros_ReturnsBigDecimal() throws Exception {
		// Arrange
		BigDecimalAdapter adapter = new BigDecimalAdapter();
		String input = "100.00";

		// Act
		BigDecimal result = adapter.unmarshal(input);

		// Assert
		assertNotNull(result, "unmarshal should return non-null BigDecimal");
		assertEquals(new BigDecimal("100.00"), result,
				"unmarshal should return correct BigDecimal preserving scale");
	}

	/**
	 * Test unmarshal with invalid string (non-numeric) throws NumberFormatException.
	 */
	@Test
	void testUnmarshal_InvalidString_ThrowsNumberFormatException() {
		// Arrange
		BigDecimalAdapter adapter = new BigDecimalAdapter();
		String input = "not-a-number";

		// Act & Assert
		assertThrows(NumberFormatException.class, () -> {
			adapter.unmarshal(input);
		}, "unmarshal should throw NumberFormatException for invalid numeric string");
	}

	/**
	 * Test unmarshal with empty string throws NumberFormatException.
	 */
	@Test
	void testUnmarshal_EmptyString_ThrowsNumberFormatException() {
		// Arrange
		BigDecimalAdapter adapter = new BigDecimalAdapter();
		String input = "";

		// Act & Assert
		assertThrows(NumberFormatException.class, () -> {
			adapter.unmarshal(input);
		}, "unmarshal should throw NumberFormatException for empty string");
	}

	/**
	 * Test unmarshal with null string throws NullPointerException.
	 */
	@Test
	void testUnmarshal_NullString_ThrowsNullPointerException() {
		// Arrange
		BigDecimalAdapter adapter = new BigDecimalAdapter();

		// Act & Assert
		assertThrows(NullPointerException.class, () -> {
			adapter.unmarshal(null);
		}, "unmarshal should throw NullPointerException for null string");
	}

	/**
	 * Test unmarshal with string containing only whitespace throws NumberFormatException.
	 */
	@Test
	void testUnmarshal_OnlyWhitespace_ThrowsNumberFormatException() {
		// Arrange
		BigDecimalAdapter adapter = new BigDecimalAdapter();
		String input = "   ";

		// Act & Assert
		assertThrows(NumberFormatException.class, () -> {
			adapter.unmarshal(input);
		}, "unmarshal should throw NumberFormatException for string with only whitespace");
	}

	// ========== Round-trip tests (marshal then unmarshal) ==========

	/**
	 * Test round-trip conversion: marshal then unmarshal returns equivalent value.
	 */
	@Test
	void testRoundTrip_PositiveValue_PreservesValue() throws Exception {
		// Arrange
		BigDecimalAdapter adapter = new BigDecimalAdapter();
		BigDecimal original = new BigDecimal("123.45");

		// Act
		String marshalled = adapter.marshal(original);
		BigDecimal unmarshalled = adapter.unmarshal(marshalled);

		// Assert
		assertEquals(original, unmarshalled,
				"Round-trip conversion should preserve the original BigDecimal value");
	}

	/**
	 * Test round-trip conversion with negative value.
	 */
	@Test
	void testRoundTrip_NegativeValue_PreservesValue() throws Exception {
		// Arrange
		BigDecimalAdapter adapter = new BigDecimalAdapter();
		BigDecimal original = new BigDecimal("-987.65");

		// Act
		String marshalled = adapter.marshal(original);
		BigDecimal unmarshalled = adapter.unmarshal(marshalled);

		// Assert
		assertEquals(original, unmarshalled,
				"Round-trip conversion should preserve negative BigDecimal value");
	}

	/**
	 * Test round-trip conversion with zero.
	 */
	@Test
	void testRoundTrip_Zero_PreservesValue() throws Exception {
		// Arrange
		BigDecimalAdapter adapter = new BigDecimalAdapter();
		BigDecimal original = BigDecimal.ZERO;

		// Act
		String marshalled = adapter.marshal(original);
		BigDecimal unmarshalled = adapter.unmarshal(marshalled);

		// Assert
		assertEquals(original, unmarshalled,
				"Round-trip conversion should preserve zero value");
	}

	/**
	 * Test round-trip conversion with very large value.
	 */
	@Test
	void testRoundTrip_VeryLargeValue_PreservesValue() throws Exception {
		// Arrange
		BigDecimalAdapter adapter = new BigDecimalAdapter();
		BigDecimal original = new BigDecimal("999999999999999999.123456789");

		// Act
		String marshalled = adapter.marshal(original);
		BigDecimal unmarshalled = adapter.unmarshal(marshalled);

		// Assert
		assertEquals(original, unmarshalled,
				"Round-trip conversion should preserve very large BigDecimal value");
	}
}
