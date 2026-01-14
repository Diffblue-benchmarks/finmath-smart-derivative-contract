/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.oracle.interestrates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ValuationOraclePlainSwap.ValuationTypeSwap static initialization (clinit).
 * Tests to ensure complete coverage of the enum's static initializer.
 *
 * @author Claude Code
 */
class ValuationOraclePlainSwapClaude_clinitTest {

	/**
	 * Test that triggers static initialization of ValuationTypeSwap enum by accessing VALUE constant.
	 * This ensures line 36 is covered.
	 */
	@Test
	void testValuationTypeSwapStaticInitValue() {
		ValuationOraclePlainSwap.ValuationTypeSwap type = ValuationOraclePlainSwap.ValuationTypeSwap.VALUE;
		assertNotNull(type);
		assertEquals("VALUE", type.name());
	}

	/**
	 * Test that triggers static initialization of ValuationTypeSwap enum by accessing VALUE_RECEIVER_LEG constant.
	 * This ensures line 37 is covered.
	 */
	@Test
	void testValuationTypeSwapStaticInitValueReceiverLeg() {
		ValuationOraclePlainSwap.ValuationTypeSwap type = ValuationOraclePlainSwap.ValuationTypeSwap.VALUE_RECEIVER_LEG;
		assertNotNull(type);
		assertEquals("VALUE_RECEIVER_LEG", type.name());
	}

	/**
	 * Test that triggers static initialization of ValuationTypeSwap enum by accessing VALUE_PAYER_LEG constant.
	 * This ensures line 38 is covered.
	 */
	@Test
	void testValuationTypeSwapStaticInitValuePayerLeg() {
		ValuationOraclePlainSwap.ValuationTypeSwap type = ValuationOraclePlainSwap.ValuationTypeSwap.VALUE_PAYER_LEG;
		assertNotNull(type);
		assertEquals("VALUE_PAYER_LEG", type.name());
	}

	/**
	 * Test that verifies the enum class itself is properly initialized.
	 * This ensures line 35 (the enum declaration) is covered.
	 */
	@Test
	void testValuationTypeSwapEnumClassInitialization() {
		Class<ValuationOraclePlainSwap.ValuationTypeSwap> enumClass = ValuationOraclePlainSwap.ValuationTypeSwap.class;
		assertNotNull(enumClass);
		assertTrue(enumClass.isEnum());
		assertEquals("ValuationTypeSwap", enumClass.getSimpleName());
	}

	/**
	 * Test that accesses all enum constants to ensure complete static initialization.
	 * This test ensures all lines in the <clinit> method are executed.
	 */
	@Test
	void testValuationTypeSwapCompleteStaticInitialization() {
		// Access all enum constants to trigger complete static initialization
		ValuationOraclePlainSwap.ValuationTypeSwap[] allTypes = ValuationOraclePlainSwap.ValuationTypeSwap.values();

		assertNotNull(allTypes);
		assertEquals(3, allTypes.length);

		// Verify each constant is properly initialized
		assertNotNull(ValuationOraclePlainSwap.ValuationTypeSwap.VALUE);
		assertNotNull(ValuationOraclePlainSwap.ValuationTypeSwap.VALUE_RECEIVER_LEG);
		assertNotNull(ValuationOraclePlainSwap.ValuationTypeSwap.VALUE_PAYER_LEG);
	}

	/**
	 * Test that verifies enum constants have correct ordinal values.
	 * This ensures the static initialization creates constants in the correct order.
	 */
	@Test
	void testValuationTypeSwapOrdinalValues() {
		assertEquals(0, ValuationOraclePlainSwap.ValuationTypeSwap.VALUE.ordinal());
		assertEquals(1, ValuationOraclePlainSwap.ValuationTypeSwap.VALUE_RECEIVER_LEG.ordinal());
		assertEquals(2, ValuationOraclePlainSwap.ValuationTypeSwap.VALUE_PAYER_LEG.ordinal());
	}

	/**
	 * Test that verifies enum constants can be compared.
	 * This ensures the static initialization properly sets up the enum infrastructure.
	 */
	@Test
	void testValuationTypeSwapComparison() {
		assertTrue(ValuationOraclePlainSwap.ValuationTypeSwap.VALUE.compareTo(ValuationOraclePlainSwap.ValuationTypeSwap.VALUE_RECEIVER_LEG) < 0);
		assertTrue(ValuationOraclePlainSwap.ValuationTypeSwap.VALUE_RECEIVER_LEG.compareTo(ValuationOraclePlainSwap.ValuationTypeSwap.VALUE_PAYER_LEG) < 0);
		assertEquals(0, ValuationOraclePlainSwap.ValuationTypeSwap.VALUE.compareTo(ValuationOraclePlainSwap.ValuationTypeSwap.VALUE));
	}

	/**
	 * Test that verifies enum constants have correct string representation.
	 * This ensures the static initialization properly initializes the name() method.
	 */
	@Test
	void testValuationTypeSwapToString() {
		assertEquals("VALUE", ValuationOraclePlainSwap.ValuationTypeSwap.VALUE.toString());
		assertEquals("VALUE_RECEIVER_LEG", ValuationOraclePlainSwap.ValuationTypeSwap.VALUE_RECEIVER_LEG.toString());
		assertEquals("VALUE_PAYER_LEG", ValuationOraclePlainSwap.ValuationTypeSwap.VALUE_PAYER_LEG.toString());
	}

	/**
	 * Test that verifies the enum's declaring class is correct.
	 * This ensures proper static initialization of the enum metadata.
	 */
	@Test
	void testValuationTypeSwapDeclaringClass() {
		assertEquals(ValuationOraclePlainSwap.ValuationTypeSwap.class, ValuationOraclePlainSwap.ValuationTypeSwap.VALUE.getDeclaringClass());
		assertEquals(ValuationOraclePlainSwap.ValuationTypeSwap.class, ValuationOraclePlainSwap.ValuationTypeSwap.VALUE_RECEIVER_LEG.getDeclaringClass());
		assertEquals(ValuationOraclePlainSwap.ValuationTypeSwap.class, ValuationOraclePlainSwap.ValuationTypeSwap.VALUE_PAYER_LEG.getDeclaringClass());
	}

	/**
	 * Test that verifies the enum implements ValuationType interface.
	 * This ensures the static initialization properly sets up the interface implementation.
	 */
	@Test
	void testValuationTypeSwapImplementsValuationType() {
		assertTrue(ValuationOraclePlainSwap.ValuationTypeSwap.VALUE instanceof net.finmath.smartcontract.valuation.oracle.ValuationType);
		assertTrue(ValuationOraclePlainSwap.ValuationTypeSwap.VALUE_RECEIVER_LEG instanceof net.finmath.smartcontract.valuation.oracle.ValuationType);
		assertTrue(ValuationOraclePlainSwap.ValuationTypeSwap.VALUE_PAYER_LEG instanceof net.finmath.smartcontract.valuation.oracle.ValuationType);
	}

	/**
	 * Test that verifies valueOf works correctly after static initialization.
	 * This ensures the static initialization properly sets up the valueOf mapping.
	 */
	@Test
	void testValuationTypeSwapValueOfAfterInit() {
		assertEquals(ValuationOraclePlainSwap.ValuationTypeSwap.VALUE,
			ValuationOraclePlainSwap.ValuationTypeSwap.valueOf("VALUE"));
		assertEquals(ValuationOraclePlainSwap.ValuationTypeSwap.VALUE_RECEIVER_LEG,
			ValuationOraclePlainSwap.ValuationTypeSwap.valueOf("VALUE_RECEIVER_LEG"));
		assertEquals(ValuationOraclePlainSwap.ValuationTypeSwap.VALUE_PAYER_LEG,
			ValuationOraclePlainSwap.ValuationTypeSwap.valueOf("VALUE_PAYER_LEG"));
	}

	/**
	 * Test that verifies values() returns all constants after static initialization.
	 * This ensures the static initialization properly sets up the values array.
	 */
	@Test
	void testValuationTypeSwapValuesAfterInit() {
		ValuationOraclePlainSwap.ValuationTypeSwap[] values = ValuationOraclePlainSwap.ValuationTypeSwap.values();

		assertNotNull(values);
		assertEquals(3, values.length);
		assertEquals(ValuationOraclePlainSwap.ValuationTypeSwap.VALUE, values[0]);
		assertEquals(ValuationOraclePlainSwap.ValuationTypeSwap.VALUE_RECEIVER_LEG, values[1]);
		assertEquals(ValuationOraclePlainSwap.ValuationTypeSwap.VALUE_PAYER_LEG, values[2]);
	}

	/**
	 * Test that verifies enum constants are immutable singletons after static initialization.
	 * This ensures the static initialization creates proper singleton instances.
	 */
	@Test
	void testValuationTypeSwapSingletonProperty() {
		ValuationOraclePlainSwap.ValuationTypeSwap value1 = ValuationOraclePlainSwap.ValuationTypeSwap.VALUE;
		ValuationOraclePlainSwap.ValuationTypeSwap value2 = ValuationOraclePlainSwap.ValuationTypeSwap.VALUE;

		assertSame(value1, value2, "Enum constants should be singletons");

		ValuationOraclePlainSwap.ValuationTypeSwap valueFromValueOf = ValuationOraclePlainSwap.ValuationTypeSwap.valueOf("VALUE");
		assertSame(value1, valueFromValueOf, "valueOf should return the same singleton instance");
	}
}
