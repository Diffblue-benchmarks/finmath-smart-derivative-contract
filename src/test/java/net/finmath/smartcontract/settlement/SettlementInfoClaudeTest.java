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
 * Test class for SettlementInfo.
 * Tests all methods with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class SettlementInfoClaudeTest {

	/**
	 * Test the default constructor creates a non-null instance with null fields.
	 */
	@Test
	void testDefaultConstructor() {
		SettlementInfo info = new SettlementInfo();
		assertNotNull(info, "Default constructor should create a non-null SettlementInfo instance");
		assertNull(info.getKey(), "Default constructor should set key to null");
		assertNull(info.getValue(), "Default constructor should set value to null");
	}

	/**
	 * Test the parameterized constructor with valid values.
	 */
	@Test
	void testParameterizedConstructorWithValidValues() {
		String key = "risk1";
		BigDecimal value = new BigDecimal("1000.50");

		SettlementInfo info = new SettlementInfo(key, value);

		assertNotNull(info, "Parameterized constructor should create a non-null instance");
		assertEquals(key, info.getKey(), "Constructor should set key correctly");
		assertEquals(value, info.getValue(), "Constructor should set value correctly");
	}

	/**
	 * Test the parameterized constructor with null key.
	 */
	@Test
	void testParameterizedConstructorWithNullKey() {
		BigDecimal value = new BigDecimal("500.00");

		SettlementInfo info = new SettlementInfo(null, value);

		assertNotNull(info, "Constructor should create instance even with null key");
		assertNull(info.getKey(), "Constructor should accept and store null key");
		assertEquals(value, info.getValue(), "Constructor should set value correctly");
	}

	/**
	 * Test the parameterized constructor with null value.
	 */
	@Test
	void testParameterizedConstructorWithNullValue() {
		String key = "risk2";

		SettlementInfo info = new SettlementInfo(key, null);

		assertNotNull(info, "Constructor should create instance even with null value");
		assertEquals(key, info.getKey(), "Constructor should set key correctly");
		assertNull(info.getValue(), "Constructor should accept and store null value");
	}

	/**
	 * Test the parameterized constructor with both parameters null.
	 */
	@Test
	void testParameterizedConstructorWithBothNull() {
		SettlementInfo info = new SettlementInfo(null, null);

		assertNotNull(info, "Constructor should create instance even with both parameters null");
		assertNull(info.getKey(), "Constructor should accept and store null key");
		assertNull(info.getValue(), "Constructor should accept and store null value");
	}

	/**
	 * Test getKey and setKey methods.
	 */
	@Test
	void testGetSetKey() {
		SettlementInfo info = new SettlementInfo();

		// Test initial null value
		assertNull(info.getKey(), "Initial key should be null");

		// Test setting a value
		String key = "marketValue";
		info.setKey(key);
		assertEquals(key, info.getKey(), "getKey should return the set value");

		// Test setting a different value
		String newKey = "riskFactor";
		info.setKey(newKey);
		assertEquals(newKey, info.getKey(), "getKey should return the updated value");

		// Test setting empty string
		info.setKey("");
		assertEquals("", info.getKey(), "getKey should return empty string when set");

		// Test setting null
		info.setKey(null);
		assertNull(info.getKey(), "getKey should return null after setting null");
	}

	/**
	 * Test getValue and setValue methods.
	 */
	@Test
	void testGetSetValue() {
		SettlementInfo info = new SettlementInfo();

		// Test initial null value
		assertNull(info.getValue(), "Initial value should be null");

		// Test setting a positive value
		BigDecimal value = new BigDecimal("1234.56");
		info.setValue(value);
		assertEquals(value, info.getValue(), "getValue should return the set value");

		// Test setting a negative value
		BigDecimal negativeValue = new BigDecimal("-9876.54");
		info.setValue(negativeValue);
		assertEquals(negativeValue, info.getValue(), "getValue should return the negative value");

		// Test setting zero
		BigDecimal zeroValue = BigDecimal.ZERO;
		info.setValue(zeroValue);
		assertEquals(zeroValue, info.getValue(), "getValue should return zero");

		// Test setting a very large value
		BigDecimal largeValue = new BigDecimal("999999999999.999999999");
		info.setValue(largeValue);
		assertEquals(largeValue, info.getValue(), "getValue should return large value");

		// Test setting null
		info.setValue(null);
		assertNull(info.getValue(), "getValue should return null after setting null");
	}

	/**
	 * Test that setters and getters work together correctly.
	 */
	@Test
	void testSettersAndGettersTogether() {
		SettlementInfo info = new SettlementInfo();

		String key1 = "exposure";
		BigDecimal value1 = new BigDecimal("500.00");

		info.setKey(key1);
		info.setValue(value1);

		assertEquals(key1, info.getKey(), "getKey should return first set key");
		assertEquals(value1, info.getValue(), "getValue should return first set value");

		String key2 = "delta";
		BigDecimal value2 = new BigDecimal("750.00");

		info.setKey(key2);
		info.setValue(value2);

		assertEquals(key2, info.getKey(), "getKey should return updated key");
		assertEquals(value2, info.getValue(), "getValue should return updated value");
	}

	/**
	 * Test independence of key and value fields.
	 */
	@Test
	void testFieldIndependence() {
		SettlementInfo info = new SettlementInfo();

		// Set key, verify value is still null
		info.setKey("testKey");
		assertEquals("testKey", info.getKey(), "Key should be set");
		assertNull(info.getValue(), "Value should remain null");

		// Set value, verify key is still set
		info.setValue(new BigDecimal("100.00"));
		assertEquals("testKey", info.getKey(), "Key should remain set");
		assertEquals(new BigDecimal("100.00"), info.getValue(), "Value should be set");

		// Set key to null, verify value is still set
		info.setKey(null);
		assertNull(info.getKey(), "Key should be null");
		assertEquals(new BigDecimal("100.00"), info.getValue(), "Value should remain set");
	}

	/**
	 * Test with special characters in key.
	 */
	@Test
	void testKeyWithSpecialCharacters() {
		SettlementInfo info = new SettlementInfo();

		String specialKey = "risk-value_2024.01@test#1";
		info.setKey(specialKey);
		assertEquals(specialKey, info.getKey(), "getKey should return key with special characters");
	}

	/**
	 * Test with BigDecimal edge cases.
	 */
	@Test
	void testBigDecimalEdgeCases() {
		SettlementInfo info = new SettlementInfo();

		// Test with BigDecimal.ONE
		info.setValue(BigDecimal.ONE);
		assertEquals(BigDecimal.ONE, info.getValue(), "getValue should return BigDecimal.ONE");

		// Test with BigDecimal.TEN
		info.setValue(BigDecimal.TEN);
		assertEquals(BigDecimal.TEN, info.getValue(), "getValue should return BigDecimal.TEN");

		// Test with very small decimal
		BigDecimal smallDecimal = new BigDecimal("0.00000001");
		info.setValue(smallDecimal);
		assertEquals(smallDecimal, info.getValue(), "getValue should return very small decimal");
	}

	/**
	 * Test creating multiple independent SettlementInfo instances.
	 */
	@Test
	void testMultipleIndependentInstances() {
		SettlementInfo info1 = new SettlementInfo("key1", new BigDecimal("100.00"));
		SettlementInfo info2 = new SettlementInfo("key2", new BigDecimal("200.00"));

		// Verify independence
		assertEquals("key1", info1.getKey(), "info1 should have its own key");
		assertEquals(new BigDecimal("100.00"), info1.getValue(), "info1 should have its own value");
		assertEquals("key2", info2.getKey(), "info2 should have its own key");
		assertEquals(new BigDecimal("200.00"), info2.getValue(), "info2 should have its own value");

		// Modify info1
		info1.setKey("modifiedKey");
		info1.setValue(new BigDecimal("300.00"));

		// Verify info2 is unaffected
		assertEquals("key2", info2.getKey(), "info2 key should be unaffected by info1 changes");
		assertEquals(new BigDecimal("200.00"), info2.getValue(), "info2 value should be unaffected by info1 changes");
	}

	/**
	 * Test state transitions from parameterized to modified state.
	 */
	@Test
	void testStateTransitions() {
		// Start with parameterized constructor
		SettlementInfo info = new SettlementInfo("initialKey", new BigDecimal("50.00"));
		assertEquals("initialKey", info.getKey());
		assertEquals(new BigDecimal("50.00"), info.getValue());

		// Transition: modify key only
		info.setKey("newKey");
		assertEquals("newKey", info.getKey());
		assertEquals(new BigDecimal("50.00"), info.getValue());

		// Transition: modify value only
		info.setValue(new BigDecimal("75.00"));
		assertEquals("newKey", info.getKey());
		assertEquals(new BigDecimal("75.00"), info.getValue());

		// Transition: set both to null
		info.setKey(null);
		info.setValue(null);
		assertNull(info.getKey());
		assertNull(info.getValue());

		// Transition: restore from null
		info.setKey("restoredKey");
		info.setValue(new BigDecimal("100.00"));
		assertEquals("restoredKey", info.getKey());
		assertEquals(new BigDecimal("100.00"), info.getValue());
	}
}
