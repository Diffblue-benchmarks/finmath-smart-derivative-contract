/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.statemachine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SmartContractStateMachine.Events static initialization (clinit).
 * Tests to ensure complete coverage of the enum's static initializer.
 *
 * @author Claude Code
 */
class SmartContractStateMachineClaude_clinitTest {

	/**
	 * Test that triggers static initialization of Events enum by accessing INCEPT constant.
	 * This ensures line 97 is covered.
	 */
	@Test
	void testEventsStaticInitIncept() {
		SmartContractStateMachine.Events event = SmartContractStateMachine.Events.INCEPT;
		assertNotNull(event);
		assertEquals("INCEPT", event.name());
	}

	/**
	 * Test that triggers static initialization of Events enum by accessing SETTLE constant.
	 * This ensures line 101 is covered.
	 */
	@Test
	void testEventsStaticInitSettle() {
		SmartContractStateMachine.Events event = SmartContractStateMachine.Events.SETTLE;
		assertNotNull(event);
		assertEquals("SETTLE", event.name());
	}

	/**
	 * Test that triggers static initialization of Events enum by accessing CONTINUE constant.
	 * This ensures line 106 is covered.
	 */
	@Test
	void testEventsStaticInitContinue() {
		SmartContractStateMachine.Events event = SmartContractStateMachine.Events.CONTINUE;
		assertNotNull(event);
		assertEquals("CONTINUE", event.name());
	}

	/**
	 * Test that triggers static initialization of Events enum by accessing TERMINATE_BY_INSUFFICIENT_PREFUNDING constant.
	 * This ensures line 107 is covered.
	 */
	@Test
	void testEventsStaticInitTerminateByInsufficientPrefunding() {
		SmartContractStateMachine.Events event = SmartContractStateMachine.Events.TERMINATE_BY_INSUFFICIENT_PREFUNDING;
		assertNotNull(event);
		assertEquals("TERMINATE_BY_INSUFFICIENT_PREFUNDING", event.name());
	}

	/**
	 * Test that triggers static initialization of Events enum by accessing TERMINATE_BY_INSUFFICIENT_MARGIN constant.
	 * This ensures line 108 is covered.
	 */
	@Test
	void testEventsStaticInitTerminateByInsufficientMargin() {
		SmartContractStateMachine.Events event = SmartContractStateMachine.Events.TERMINATE_BY_INSUFFICIENT_MARGIN;
		assertNotNull(event);
		assertEquals("TERMINATE_BY_INSUFFICIENT_MARGIN", event.name());
	}

	/**
	 * Test that triggers static initialization of Events enum by accessing MATURE constant.
	 * This ensures line 109 is covered.
	 */
	@Test
	void testEventsStaticInitMature() {
		SmartContractStateMachine.Events event = SmartContractStateMachine.Events.MATURE;
		assertNotNull(event);
		assertEquals("MATURE", event.name());
	}

	/**
	 * Test that verifies the enum class itself is properly initialized.
	 * This ensures line 93 (the enum declaration) is covered.
	 */
	@Test
	void testEventsEnumClassInitialization() {
		Class<SmartContractStateMachine.Events> enumClass = SmartContractStateMachine.Events.class;
		assertNotNull(enumClass);
		assertTrue(enumClass.isEnum());
		assertEquals("Events", enumClass.getSimpleName());
	}

	/**
	 * Test that accesses all enum constants to ensure complete static initialization.
	 * This test ensures all lines in the <clinit> method are executed.
	 */
	@Test
	void testEventsCompleteStaticInitialization() {
		// Access all enum constants to trigger complete static initialization
		SmartContractStateMachine.Events[] allEvents = SmartContractStateMachine.Events.values();

		assertNotNull(allEvents);
		assertEquals(6, allEvents.length);

		// Verify each constant is properly initialized
		assertNotNull(SmartContractStateMachine.Events.INCEPT);
		assertNotNull(SmartContractStateMachine.Events.SETTLE);
		assertNotNull(SmartContractStateMachine.Events.CONTINUE);
		assertNotNull(SmartContractStateMachine.Events.TERMINATE_BY_INSUFFICIENT_PREFUNDING);
		assertNotNull(SmartContractStateMachine.Events.TERMINATE_BY_INSUFFICIENT_MARGIN);
		assertNotNull(SmartContractStateMachine.Events.MATURE);
	}

	/**
	 * Test that verifies enum constants have correct ordinal values.
	 * This ensures the static initialization creates constants in the correct order.
	 */
	@Test
	void testEventsOrdinalValues() {
		assertEquals(0, SmartContractStateMachine.Events.INCEPT.ordinal());
		assertEquals(1, SmartContractStateMachine.Events.SETTLE.ordinal());
		assertEquals(2, SmartContractStateMachine.Events.CONTINUE.ordinal());
		assertEquals(3, SmartContractStateMachine.Events.TERMINATE_BY_INSUFFICIENT_PREFUNDING.ordinal());
		assertEquals(4, SmartContractStateMachine.Events.TERMINATE_BY_INSUFFICIENT_MARGIN.ordinal());
		assertEquals(5, SmartContractStateMachine.Events.MATURE.ordinal());
	}

	/**
	 * Test that verifies enum constants can be compared.
	 * This ensures the static initialization properly sets up the enum infrastructure.
	 */
	@Test
	void testEventsComparison() {
		assertTrue(SmartContractStateMachine.Events.INCEPT.compareTo(SmartContractStateMachine.Events.SETTLE) < 0);
		assertTrue(SmartContractStateMachine.Events.SETTLE.compareTo(SmartContractStateMachine.Events.CONTINUE) < 0);
		assertTrue(SmartContractStateMachine.Events.CONTINUE.compareTo(SmartContractStateMachine.Events.TERMINATE_BY_INSUFFICIENT_PREFUNDING) < 0);
		assertTrue(SmartContractStateMachine.Events.TERMINATE_BY_INSUFFICIENT_PREFUNDING.compareTo(SmartContractStateMachine.Events.TERMINATE_BY_INSUFFICIENT_MARGIN) < 0);
		assertTrue(SmartContractStateMachine.Events.TERMINATE_BY_INSUFFICIENT_MARGIN.compareTo(SmartContractStateMachine.Events.MATURE) < 0);
		assertEquals(0, SmartContractStateMachine.Events.INCEPT.compareTo(SmartContractStateMachine.Events.INCEPT));
	}

	/**
	 * Test that verifies enum constants have correct string representation.
	 * This ensures the static initialization properly initializes the name() method.
	 */
	@Test
	void testEventsToString() {
		assertEquals("INCEPT", SmartContractStateMachine.Events.INCEPT.toString());
		assertEquals("SETTLE", SmartContractStateMachine.Events.SETTLE.toString());
		assertEquals("CONTINUE", SmartContractStateMachine.Events.CONTINUE.toString());
		assertEquals("TERMINATE_BY_INSUFFICIENT_PREFUNDING", SmartContractStateMachine.Events.TERMINATE_BY_INSUFFICIENT_PREFUNDING.toString());
		assertEquals("TERMINATE_BY_INSUFFICIENT_MARGIN", SmartContractStateMachine.Events.TERMINATE_BY_INSUFFICIENT_MARGIN.toString());
		assertEquals("MATURE", SmartContractStateMachine.Events.MATURE.toString());
	}

	/**
	 * Test that verifies the enum's declaring class is correct.
	 * This ensures proper static initialization of the enum metadata.
	 */
	@Test
	void testEventsDeclaringClass() {
		assertEquals(SmartContractStateMachine.Events.class, SmartContractStateMachine.Events.INCEPT.getDeclaringClass());
		assertEquals(SmartContractStateMachine.Events.class, SmartContractStateMachine.Events.SETTLE.getDeclaringClass());
		assertEquals(SmartContractStateMachine.Events.class, SmartContractStateMachine.Events.CONTINUE.getDeclaringClass());
		assertEquals(SmartContractStateMachine.Events.class, SmartContractStateMachine.Events.TERMINATE_BY_INSUFFICIENT_PREFUNDING.getDeclaringClass());
		assertEquals(SmartContractStateMachine.Events.class, SmartContractStateMachine.Events.TERMINATE_BY_INSUFFICIENT_MARGIN.getDeclaringClass());
		assertEquals(SmartContractStateMachine.Events.class, SmartContractStateMachine.Events.MATURE.getDeclaringClass());
	}
}
