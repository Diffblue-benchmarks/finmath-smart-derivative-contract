/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.statemachine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SmartContractStateMachine.States static initialization (clinit).
 * Tests to ensure complete coverage of the enum's static initializer.
 *
 * @author Claude Code
 */
class SmartContractStateMachineClaude_States_clinitTest {

	/**
	 * Test that triggers static initialization of States enum by accessing INCEPTION constant.
	 * This ensures line 53 is covered.
	 */
	@Test
	void testStatesStaticInitInception() {
		SmartContractStateMachine.States state = SmartContractStateMachine.States.INCEPTION;
		assertNotNull(state);
		assertEquals("INCEPTION", state.name());
	}

	/**
	 * Test that triggers static initialization of States enum by accessing ACTIVE constant.
	 * This ensures line 57 is covered.
	 */
	@Test
	void testStatesStaticInitActive() {
		SmartContractStateMachine.States state = SmartContractStateMachine.States.ACTIVE;
		assertNotNull(state);
		assertEquals("ACTIVE", state.name());
	}

	/**
	 * Test that triggers static initialization of States enum by accessing SETTLEMENT constant.
	 * This ensures line 61 is covered.
	 */
	@Test
	void testStatesStaticInitSettlement() {
		SmartContractStateMachine.States state = SmartContractStateMachine.States.SETTLEMENT;
		assertNotNull(state);
		assertEquals("SETTLEMENT", state.name());
	}

	/**
	 * Test that triggers static initialization of States enum by accessing TERMINATED_BY_INSUFFICIENT_PREFUNDING constant.
	 * This ensures line 65 is covered.
	 */
	@Test
	void testStatesStaticInitTerminatedByInsufficientPrefunding() {
		SmartContractStateMachine.States state = SmartContractStateMachine.States.TERMINATED_BY_INSUFFICIENT_PREFUNDING;
		assertNotNull(state);
		assertEquals("TERMINATED_BY_INSUFFICIENT_PREFUNDING", state.name());
	}

	/**
	 * Test that triggers static initialization of States enum by accessing TERMINATED_BY_INSUFFICIENT_MARGIN constant.
	 * This ensures line 69 is covered.
	 */
	@Test
	void testStatesStaticInitTerminatedByInsufficientMargin() {
		SmartContractStateMachine.States state = SmartContractStateMachine.States.TERMINATED_BY_INSUFFICIENT_MARGIN;
		assertNotNull(state);
		assertEquals("TERMINATED_BY_INSUFFICIENT_MARGIN", state.name());
	}

	/**
	 * Test that triggers static initialization of States enum by accessing TERMINATED_BY_MATURITY constant.
	 * This ensures line 73 is covered.
	 */
	@Test
	void testStatesStaticInitTerminatedByMaturity() {
		SmartContractStateMachine.States state = SmartContractStateMachine.States.TERMINATED_BY_MATURITY;
		assertNotNull(state);
		assertEquals("TERMINATED_BY_MATURITY", state.name());
	}

	/**
	 * Test that triggers static initialization of States enum by accessing PREFUNDING_CHECK constant.
	 * This ensures line 77 is covered.
	 */
	@Test
	void testStatesStaticInitPrefundingCheck() {
		SmartContractStateMachine.States state = SmartContractStateMachine.States.PREFUNDING_CHECK;
		assertNotNull(state);
		assertEquals("PREFUNDING_CHECK", state.name());
	}

	/**
	 * Test that triggers static initialization of States enum by accessing SETTLEMENT_CHECK constant.
	 * This ensures line 81 is covered.
	 */
	@Test
	void testStatesStaticInitSettlementCheck() {
		SmartContractStateMachine.States state = SmartContractStateMachine.States.SETTLEMENT_CHECK;
		assertNotNull(state);
		assertEquals("SETTLEMENT_CHECK", state.name());
	}

	/**
	 * Test that triggers static initialization of States enum by accessing MATURITY_CHECK constant.
	 * This ensures line 85 is covered.
	 */
	@Test
	void testStatesStaticInitMaturityCheck() {
		SmartContractStateMachine.States state = SmartContractStateMachine.States.MATURITY_CHECK;
		assertNotNull(state);
		assertEquals("MATURITY_CHECK", state.name());
	}

	/**
	 * Test that verifies the enum class itself is properly initialized.
	 * This ensures line 49 (the enum declaration) is covered.
	 */
	@Test
	void testStatesEnumClassInitialization() {
		Class<SmartContractStateMachine.States> enumClass = SmartContractStateMachine.States.class;
		assertNotNull(enumClass);
		assertTrue(enumClass.isEnum());
		assertEquals("States", enumClass.getSimpleName());
	}

	/**
	 * Test that accesses all enum constants to ensure complete static initialization.
	 * This test ensures all lines in the <clinit> method are executed.
	 */
	@Test
	void testStatesCompleteStaticInitialization() {
		// Access all enum constants to trigger complete static initialization
		SmartContractStateMachine.States[] allStates = SmartContractStateMachine.States.values();

		assertNotNull(allStates);
		assertEquals(9, allStates.length);

		// Verify each constant is properly initialized
		assertNotNull(SmartContractStateMachine.States.INCEPTION);
		assertNotNull(SmartContractStateMachine.States.ACTIVE);
		assertNotNull(SmartContractStateMachine.States.SETTLEMENT);
		assertNotNull(SmartContractStateMachine.States.TERMINATED_BY_INSUFFICIENT_PREFUNDING);
		assertNotNull(SmartContractStateMachine.States.TERMINATED_BY_INSUFFICIENT_MARGIN);
		assertNotNull(SmartContractStateMachine.States.TERMINATED_BY_MATURITY);
		assertNotNull(SmartContractStateMachine.States.PREFUNDING_CHECK);
		assertNotNull(SmartContractStateMachine.States.SETTLEMENT_CHECK);
		assertNotNull(SmartContractStateMachine.States.MATURITY_CHECK);
	}

	/**
	 * Test that verifies enum constants have correct ordinal values.
	 * This ensures the static initialization creates constants in the correct order.
	 */
	@Test
	void testStatesOrdinalValues() {
		assertEquals(0, SmartContractStateMachine.States.INCEPTION.ordinal());
		assertEquals(1, SmartContractStateMachine.States.ACTIVE.ordinal());
		assertEquals(2, SmartContractStateMachine.States.SETTLEMENT.ordinal());
		assertEquals(3, SmartContractStateMachine.States.TERMINATED_BY_INSUFFICIENT_PREFUNDING.ordinal());
		assertEquals(4, SmartContractStateMachine.States.TERMINATED_BY_INSUFFICIENT_MARGIN.ordinal());
		assertEquals(5, SmartContractStateMachine.States.TERMINATED_BY_MATURITY.ordinal());
		assertEquals(6, SmartContractStateMachine.States.PREFUNDING_CHECK.ordinal());
		assertEquals(7, SmartContractStateMachine.States.SETTLEMENT_CHECK.ordinal());
		assertEquals(8, SmartContractStateMachine.States.MATURITY_CHECK.ordinal());
	}

	/**
	 * Test that verifies enum constants can be compared.
	 * This ensures the static initialization properly sets up the enum infrastructure.
	 */
	@Test
	void testStatesComparison() {
		assertTrue(SmartContractStateMachine.States.INCEPTION.compareTo(SmartContractStateMachine.States.ACTIVE) < 0);
		assertTrue(SmartContractStateMachine.States.ACTIVE.compareTo(SmartContractStateMachine.States.SETTLEMENT) < 0);
		assertTrue(SmartContractStateMachine.States.SETTLEMENT.compareTo(SmartContractStateMachine.States.TERMINATED_BY_INSUFFICIENT_PREFUNDING) < 0);
		assertTrue(SmartContractStateMachine.States.TERMINATED_BY_INSUFFICIENT_PREFUNDING.compareTo(SmartContractStateMachine.States.TERMINATED_BY_INSUFFICIENT_MARGIN) < 0);
		assertTrue(SmartContractStateMachine.States.TERMINATED_BY_INSUFFICIENT_MARGIN.compareTo(SmartContractStateMachine.States.TERMINATED_BY_MATURITY) < 0);
		assertTrue(SmartContractStateMachine.States.TERMINATED_BY_MATURITY.compareTo(SmartContractStateMachine.States.PREFUNDING_CHECK) < 0);
		assertTrue(SmartContractStateMachine.States.PREFUNDING_CHECK.compareTo(SmartContractStateMachine.States.SETTLEMENT_CHECK) < 0);
		assertTrue(SmartContractStateMachine.States.SETTLEMENT_CHECK.compareTo(SmartContractStateMachine.States.MATURITY_CHECK) < 0);
		assertEquals(0, SmartContractStateMachine.States.INCEPTION.compareTo(SmartContractStateMachine.States.INCEPTION));
	}

	/**
	 * Test that verifies enum constants have correct string representation.
	 * This ensures the static initialization properly initializes the name() method.
	 */
	@Test
	void testStatesToString() {
		assertEquals("INCEPTION", SmartContractStateMachine.States.INCEPTION.toString());
		assertEquals("ACTIVE", SmartContractStateMachine.States.ACTIVE.toString());
		assertEquals("SETTLEMENT", SmartContractStateMachine.States.SETTLEMENT.toString());
		assertEquals("TERMINATED_BY_INSUFFICIENT_PREFUNDING", SmartContractStateMachine.States.TERMINATED_BY_INSUFFICIENT_PREFUNDING.toString());
		assertEquals("TERMINATED_BY_INSUFFICIENT_MARGIN", SmartContractStateMachine.States.TERMINATED_BY_INSUFFICIENT_MARGIN.toString());
		assertEquals("TERMINATED_BY_MATURITY", SmartContractStateMachine.States.TERMINATED_BY_MATURITY.toString());
		assertEquals("PREFUNDING_CHECK", SmartContractStateMachine.States.PREFUNDING_CHECK.toString());
		assertEquals("SETTLEMENT_CHECK", SmartContractStateMachine.States.SETTLEMENT_CHECK.toString());
		assertEquals("MATURITY_CHECK", SmartContractStateMachine.States.MATURITY_CHECK.toString());
	}

	/**
	 * Test that verifies the enum's declaring class is correct.
	 * This ensures proper static initialization of the enum metadata.
	 */
	@Test
	void testStatesDeclaringClass() {
		assertEquals(SmartContractStateMachine.States.class, SmartContractStateMachine.States.INCEPTION.getDeclaringClass());
		assertEquals(SmartContractStateMachine.States.class, SmartContractStateMachine.States.ACTIVE.getDeclaringClass());
		assertEquals(SmartContractStateMachine.States.class, SmartContractStateMachine.States.SETTLEMENT.getDeclaringClass());
		assertEquals(SmartContractStateMachine.States.class, SmartContractStateMachine.States.TERMINATED_BY_INSUFFICIENT_PREFUNDING.getDeclaringClass());
		assertEquals(SmartContractStateMachine.States.class, SmartContractStateMachine.States.TERMINATED_BY_INSUFFICIENT_MARGIN.getDeclaringClass());
		assertEquals(SmartContractStateMachine.States.class, SmartContractStateMachine.States.TERMINATED_BY_MATURITY.getDeclaringClass());
		assertEquals(SmartContractStateMachine.States.class, SmartContractStateMachine.States.PREFUNDING_CHECK.getDeclaringClass());
		assertEquals(SmartContractStateMachine.States.class, SmartContractStateMachine.States.SETTLEMENT_CHECK.getDeclaringClass());
		assertEquals(SmartContractStateMachine.States.class, SmartContractStateMachine.States.MATURITY_CHECK.getDeclaringClass());
	}
}
