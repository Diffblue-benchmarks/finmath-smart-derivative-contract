/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.settlement;

import net.finmath.smartcontract.settlement.Settlement.SettlementType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class specifically for covering the static initializer (clinit) of Settlement$SettlementType.
 * The static initializer is executed when the enum class is first loaded and initializes
 * the enum constants INITIAL, REGULAR, and TERMINAL (lines 32-35 in Settlement.java).
 *
 * @author Claude Code
 */
class SettlementClaude_clinitTest {

	/**
	 * Test that the enum constants are properly initialized by the static initializer.
	 * This test ensures that the static initializer (<clinit>) is executed and
	 * all three enum constants are created and accessible.
	 */
	@Test
	void testEnumStaticInitialization() {
		// Access each enum constant - this triggers the static initializer if not already loaded
		// Lines 32-35: public enum SettlementType { INITIAL, REGULAR, TERMINAL }

		// Verify INITIAL constant is initialized (line 33)
		assertNotNull(SettlementType.INITIAL, "INITIAL enum constant should be initialized");
		assertEquals("INITIAL", SettlementType.INITIAL.name(), "INITIAL should have correct name");

		// Verify REGULAR constant is initialized (line 34)
		assertNotNull(SettlementType.REGULAR, "REGULAR enum constant should be initialized");
		assertEquals("REGULAR", SettlementType.REGULAR.name(), "REGULAR should have correct name");

		// Verify TERMINAL constant is initialized (line 35)
		assertNotNull(SettlementType.TERMINAL, "TERMINAL enum constant should be initialized");
		assertEquals("TERMINAL", SettlementType.TERMINAL.name(), "TERMINAL should have correct name");
	}

	/**
	 * Test that values() array is properly initialized by the static initializer.
	 * The values() method returns an array created during static initialization.
	 */
	@Test
	void testEnumValuesArrayInitialization() {
		// Call values() which depends on the static initializer having run
		SettlementType[] values = SettlementType.values();

		// Verify the array contains exactly 3 elements (all enum constants from lines 33-35)
		assertNotNull(values, "values() should return a non-null array");
		assertEquals(3, values.length, "values() should return array with 3 enum constants");

		// Verify all three enum constants are in the array
		assertEquals(SettlementType.INITIAL, values[0], "First element should be INITIAL");
		assertEquals(SettlementType.REGULAR, values[1], "Second element should be REGULAR");
		assertEquals(SettlementType.TERMINAL, values[2], "Third element should be TERMINAL");
	}

	/**
	 * Test valueOf() which depends on the static initializer having created the enum constants.
	 */
	@Test
	void testEnumValueOfWithStaticConstants() {
		// valueOf uses the enum constants created by the static initializer

		// Test valueOf for INITIAL (line 33)
		SettlementType initial = SettlementType.valueOf("INITIAL");
		assertSame(SettlementType.INITIAL, initial, "valueOf('INITIAL') should return the INITIAL constant");

		// Test valueOf for REGULAR (line 34)
		SettlementType regular = SettlementType.valueOf("REGULAR");
		assertSame(SettlementType.REGULAR, regular, "valueOf('REGULAR') should return the REGULAR constant");

		// Test valueOf for TERMINAL (line 35)
		SettlementType terminal = SettlementType.valueOf("TERMINAL");
		assertSame(SettlementType.TERMINAL, terminal, "valueOf('TERMINAL') should return the TERMINAL constant");
	}

	/**
	 * Test enum constant identity - enum constants are singletons created during static initialization.
	 */
	@Test
	void testEnumConstantIdentity() {
		// Enum constants created by static initializer should be singletons

		// Multiple references to the same constant should be identical
		SettlementType initial1 = SettlementType.INITIAL;
		SettlementType initial2 = SettlementType.valueOf("INITIAL");
		assertSame(initial1, initial2, "Multiple references to INITIAL should be identical");

		SettlementType regular1 = SettlementType.REGULAR;
		SettlementType regular2 = SettlementType.valueOf("REGULAR");
		assertSame(regular1, regular2, "Multiple references to REGULAR should be identical");

		SettlementType terminal1 = SettlementType.TERMINAL;
		SettlementType terminal2 = SettlementType.valueOf("TERMINAL");
		assertSame(terminal1, terminal2, "Multiple references to TERMINAL should be identical");
	}

	/**
	 * Test that enum constants are distinct objects created during static initialization.
	 */
	@Test
	void testEnumConstantsAreDistinct() {
		// Each enum constant should be a distinct object
		assertNotSame(SettlementType.INITIAL, SettlementType.REGULAR, "INITIAL and REGULAR should be distinct");
		assertNotSame(SettlementType.INITIAL, SettlementType.TERMINAL, "INITIAL and TERMINAL should be distinct");
		assertNotSame(SettlementType.REGULAR, SettlementType.TERMINAL, "REGULAR and TERMINAL should be distinct");
	}

	/**
	 * Test ordinal values which are assigned during enum static initialization.
	 */
	@Test
	void testEnumOrdinalAssignment() {
		// Ordinals are assigned during static initialization in declaration order

		// INITIAL is first (line 33), so ordinal 0
		assertEquals(0, SettlementType.INITIAL.ordinal(), "INITIAL should have ordinal 0");

		// REGULAR is second (line 34), so ordinal 1
		assertEquals(1, SettlementType.REGULAR.ordinal(), "REGULAR should have ordinal 1");

		// TERMINAL is third (line 35), so ordinal 2
		assertEquals(2, SettlementType.TERMINAL.ordinal(), "TERMINAL should have ordinal 2");
	}

	/**
	 * Test enum constant comparison using the ordinals assigned during static initialization.
	 */
	@Test
	void testEnumComparison() {
		// Enum comparison uses ordinals assigned during static initialization

		assertTrue(SettlementType.INITIAL.compareTo(SettlementType.REGULAR) < 0,
				"INITIAL should be less than REGULAR");
		assertTrue(SettlementType.REGULAR.compareTo(SettlementType.TERMINAL) < 0,
				"REGULAR should be less than TERMINAL");
		assertTrue(SettlementType.INITIAL.compareTo(SettlementType.TERMINAL) < 0,
				"INITIAL should be less than TERMINAL");

		assertEquals(0, SettlementType.INITIAL.compareTo(SettlementType.INITIAL),
				"INITIAL should equal itself");
		assertEquals(0, SettlementType.REGULAR.compareTo(SettlementType.REGULAR),
				"REGULAR should equal itself");
		assertEquals(0, SettlementType.TERMINAL.compareTo(SettlementType.TERMINAL),
				"TERMINAL should equal itself");
	}
}
