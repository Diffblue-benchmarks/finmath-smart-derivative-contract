/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.statemachine;

import org.junit.jupiter.api.Test;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.guard.Guard;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SmartContractStateMachine.
 * Tests all methods with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class SmartContractStateMachineClaudeTest {

	/**
	 * Test the default constructor.
	 * Verifies that a new instance is created with default field values.
	 */
	@Test
	void testConstructor() {
		SmartContractStateMachine stateMachine = new SmartContractStateMachine();
		assertNotNull(stateMachine);
		// Default values based on class inspection
		assertTrue(stateMachine.isPrefunded());
		assertFalse(stateMachine.isMatured());
		assertTrue(stateMachine.isSettlementSuccessful());
	}

	/**
	 * Test the main method.
	 * Verifies that the main method executes without throwing exceptions.
	 */
	@Test
	void testMain() {
		assertDoesNotThrow(() -> SmartContractStateMachine.main(new String[0]));
	}

	/**
	 * Test buildMachine method.
	 * Verifies that a StateMachine is successfully built.
	 */
	@Test
	void testBuildMachine() throws Exception {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();
		StateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events> stateMachine = sdcStateMachine.buildMachine();

		assertNotNull(stateMachine);
		// State is null until machine is started
		stateMachine.start();
		assertNotNull(stateMachine.getState());
		assertEquals(SmartContractStateMachine.States.INCEPTION, stateMachine.getState().getId());
		stateMachine.stop();
	}

	/**
	 * Test isPrefunded getter method.
	 * Verifies that the method returns the correct prefunded state.
	 */
	@Test
	void testIsPrefunded() {
		SmartContractStateMachine stateMachine = new SmartContractStateMachine();
		assertTrue(stateMachine.isPrefunded());

		stateMachine.setPrefunded(false);
		assertFalse(stateMachine.isPrefunded());
	}

	/**
	 * Test setPrefunded method with true value.
	 * Verifies that the method sets the prefunded state and returns self reference.
	 */
	@Test
	void testSetPrefundedTrue() {
		SmartContractStateMachine stateMachine = new SmartContractStateMachine();
		SmartContractStateMachine result = stateMachine.setPrefunded(true);

		assertTrue(stateMachine.isPrefunded());
		assertSame(stateMachine, result);
	}

	/**
	 * Test setPrefunded method with false value.
	 * Verifies that the method sets the prefunded state to false and returns self reference.
	 */
	@Test
	void testSetPrefundedFalse() {
		SmartContractStateMachine stateMachine = new SmartContractStateMachine();
		SmartContractStateMachine result = stateMachine.setPrefunded(false);

		assertFalse(stateMachine.isPrefunded());
		assertSame(stateMachine, result);
	}

	/**
	 * Test isMatured getter method.
	 * Verifies that the method returns the correct matured state.
	 */
	@Test
	void testIsMatured() {
		SmartContractStateMachine stateMachine = new SmartContractStateMachine();
		assertFalse(stateMachine.isMatured());

		stateMachine.setMatured(true);
		assertTrue(stateMachine.isMatured());
	}

	/**
	 * Test setMatured method with true value.
	 * Verifies that the method sets the matured state and returns self reference.
	 */
	@Test
	void testSetMaturedTrue() {
		SmartContractStateMachine stateMachine = new SmartContractStateMachine();
		SmartContractStateMachine result = stateMachine.setMatured(true);

		assertTrue(stateMachine.isMatured());
		assertSame(stateMachine, result);
	}

	/**
	 * Test setMatured method with false value.
	 * Verifies that the method sets the matured state to false and returns self reference.
	 */
	@Test
	void testSetMaturedFalse() {
		SmartContractStateMachine stateMachine = new SmartContractStateMachine();
		stateMachine.setMatured(true);
		SmartContractStateMachine result = stateMachine.setMatured(false);

		assertFalse(stateMachine.isMatured());
		assertSame(stateMachine, result);
	}

	/**
	 * Test isSettlementSuccessful getter method.
	 * Verifies that the method returns the correct settlement successful state.
	 */
	@Test
	void testIsSettlementSuccessful() {
		SmartContractStateMachine stateMachine = new SmartContractStateMachine();
		assertTrue(stateMachine.isSettlementSuccessful());

		stateMachine.setSettlementSuccessful(false);
		assertFalse(stateMachine.isSettlementSuccessful());
	}

	/**
	 * Test setSettlementSuccessful method with true value.
	 * Verifies that the method sets the settlement successful state and returns self reference.
	 */
	@Test
	void testSetSettlementSuccessfulTrue() {
		SmartContractStateMachine stateMachine = new SmartContractStateMachine();
		SmartContractStateMachine result = stateMachine.setSettlementSuccessful(true);

		assertTrue(stateMachine.isSettlementSuccessful());
		assertSame(stateMachine, result);
	}

	/**
	 * Test setSettlementSuccessful method with false value.
	 * Verifies that the method sets the settlement successful state to false and returns self reference.
	 */
	@Test
	void testSetSettlementSuccessfulFalse() {
		SmartContractStateMachine stateMachine = new SmartContractStateMachine();
		SmartContractStateMachine result = stateMachine.setSettlementSuccessful(false);

		assertFalse(stateMachine.isSettlementSuccessful());
		assertSame(stateMachine, result);
	}

	/**
	 * Test settlementCheck guard when settlement is successful.
	 * Verifies that the guard evaluates to true when settlement is successful.
	 */
	@Test
	void testSettlementCheckWhenSuccessful() {
		SmartContractStateMachine stateMachine = new SmartContractStateMachine();
		stateMachine.setSettlementSuccessful(true);

		Guard<SmartContractStateMachine.States, SmartContractStateMachine.Events> guard = stateMachine.settlementCheck();
		assertNotNull(guard);
		assertTrue(guard.evaluate(null));
	}

	/**
	 * Test settlementCheck guard when settlement is not successful.
	 * Verifies that the guard evaluates to false when settlement is not successful.
	 */
	@Test
	void testSettlementCheckWhenNotSuccessful() {
		SmartContractStateMachine stateMachine = new SmartContractStateMachine();
		stateMachine.setSettlementSuccessful(false);

		Guard<SmartContractStateMachine.States, SmartContractStateMachine.Events> guard = stateMachine.settlementCheck();
		assertNotNull(guard);
		assertFalse(guard.evaluate(null));
	}

	/**
	 * Test prefundingCheck guard when prefunded.
	 * Verifies that the guard evaluates to true when account is prefunded.
	 */
	@Test
	void testPrefundingCheckWhenPrefunded() {
		SmartContractStateMachine stateMachine = new SmartContractStateMachine();
		stateMachine.setPrefunded(true);

		Guard<SmartContractStateMachine.States, SmartContractStateMachine.Events> guard = stateMachine.prefundingCheck();
		assertNotNull(guard);
		assertTrue(guard.evaluate(null));
	}

	/**
	 * Test prefundingCheck guard when not prefunded.
	 * Verifies that the guard evaluates to false when account is not prefunded.
	 */
	@Test
	void testPrefundingCheckWhenNotPrefunded() {
		SmartContractStateMachine stateMachine = new SmartContractStateMachine();
		stateMachine.setPrefunded(false);

		Guard<SmartContractStateMachine.States, SmartContractStateMachine.Events> guard = stateMachine.prefundingCheck();
		assertNotNull(guard);
		assertFalse(guard.evaluate(null));
	}

	/**
	 * Test notMaturedCheck guard when not matured.
	 * Verifies that the guard evaluates to true when contract is not matured.
	 */
	@Test
	void testNotMaturedCheckWhenNotMatured() {
		SmartContractStateMachine stateMachine = new SmartContractStateMachine();
		stateMachine.setMatured(false);

		Guard<SmartContractStateMachine.States, SmartContractStateMachine.Events> guard = stateMachine.notMaturedCheck();
		assertNotNull(guard);
		assertTrue(guard.evaluate(null));
	}

	/**
	 * Test notMaturedCheck guard when matured.
	 * Verifies that the guard evaluates to false when contract is matured.
	 */
	@Test
	void testNotMaturedCheckWhenMatured() {
		SmartContractStateMachine stateMachine = new SmartContractStateMachine();
		stateMachine.setMatured(true);

		Guard<SmartContractStateMachine.States, SmartContractStateMachine.Events> guard = stateMachine.notMaturedCheck();
		assertNotNull(guard);
		assertFalse(guard.evaluate(null));
	}

	/**
	 * Test performSettlement action.
	 * Verifies that the action is created and can be executed without errors.
	 */
	@Test
	void testPerformSettlement() {
		SmartContractStateMachine stateMachine = new SmartContractStateMachine();
		Action<SmartContractStateMachine.States, SmartContractStateMachine.Events> action = stateMachine.performSettlement();

		assertNotNull(action);
		assertDoesNotThrow(() -> action.execute(null));
	}

	/**
	 * Test state machine transition from INCEPTION to ACTIVE.
	 * Verifies that the INCEPT event moves the machine to ACTIVE state.
	 */
	@Test
	void testTransitionInceptionToActive() throws Exception {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();
		StateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events> stateMachine = sdcStateMachine.buildMachine();

		stateMachine.start();
		stateMachine.sendEvent(SmartContractStateMachine.Events.INCEPT);

		assertEquals(SmartContractStateMachine.States.ACTIVE, stateMachine.getState().getId());
		stateMachine.stop();
	}

	/**
	 * Test state machine transition from ACTIVE to SETTLEMENT.
	 * Verifies that the SETTLE event moves the machine to SETTLEMENT state.
	 */
	@Test
	void testTransitionActiveToSettlement() throws Exception {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();
		StateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events> stateMachine = sdcStateMachine.buildMachine();

		stateMachine.start();
		stateMachine.sendEvent(SmartContractStateMachine.Events.INCEPT);
		stateMachine.sendEvent(SmartContractStateMachine.Events.SETTLE);

		assertEquals(SmartContractStateMachine.States.SETTLEMENT, stateMachine.getState().getId());
		stateMachine.stop();
	}

	/**
	 * Test state machine transition terminating by maturity.
	 * Verifies that setting matured to true causes termination by maturity.
	 */
	@Test
	void testTransitionTerminateByMaturity() throws Exception {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();
		sdcStateMachine.setMatured(false).setPrefunded(true).setSettlementSuccessful(true);

		StateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events> stateMachine = sdcStateMachine.buildMachine();

		stateMachine.start();
		stateMachine.sendEvent(SmartContractStateMachine.Events.INCEPT);
		stateMachine.sendEvent(SmartContractStateMachine.Events.SETTLE);

		sdcStateMachine.setMatured(true);
		stateMachine.sendEvent(SmartContractStateMachine.Events.CONTINUE);

		assertEquals(SmartContractStateMachine.States.TERMINATED_BY_MATURITY, stateMachine.getState().getId());
		stateMachine.stop();
	}

	/**
	 * Test state machine transition terminating by insufficient prefunding.
	 * Verifies that setting prefunded to false causes termination by insufficient prefunding.
	 */
	@Test
	void testTransitionTerminateByInsufficientPrefunding() throws Exception {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();
		sdcStateMachine.setMatured(false).setPrefunded(true).setSettlementSuccessful(true);

		StateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events> stateMachine = sdcStateMachine.buildMachine();

		stateMachine.start();
		stateMachine.sendEvent(SmartContractStateMachine.Events.INCEPT);
		stateMachine.sendEvent(SmartContractStateMachine.Events.SETTLE);

		sdcStateMachine.setPrefunded(false);
		stateMachine.sendEvent(SmartContractStateMachine.Events.CONTINUE);

		assertEquals(SmartContractStateMachine.States.TERMINATED_BY_INSUFFICIENT_PREFUNDING, stateMachine.getState().getId());
		stateMachine.stop();
	}

	/**
	 * Test state machine transition terminating by insufficient margin.
	 * Verifies that setting settlement unsuccessful causes termination by insufficient margin.
	 */
	@Test
	void testTransitionTerminateByInsufficientMargin() throws Exception {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();
		sdcStateMachine.setMatured(false).setPrefunded(true).setSettlementSuccessful(true);

		StateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events> stateMachine = sdcStateMachine.buildMachine();

		stateMachine.start();
		stateMachine.sendEvent(SmartContractStateMachine.Events.INCEPT);
		stateMachine.sendEvent(SmartContractStateMachine.Events.SETTLE);

		sdcStateMachine.setSettlementSuccessful(false);
		stateMachine.sendEvent(SmartContractStateMachine.Events.CONTINUE);

		assertEquals(SmartContractStateMachine.States.TERMINATED_BY_INSUFFICIENT_MARGIN, stateMachine.getState().getId());
		stateMachine.stop();
	}

	/**
	 * Test state machine successful settlement cycle returning to ACTIVE.
	 * Verifies that a successful settlement cycle returns the machine to ACTIVE state.
	 */
	@Test
	void testSuccessfulSettlementCycle() throws Exception {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();
		sdcStateMachine.setMatured(false).setPrefunded(true).setSettlementSuccessful(true);

		StateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events> stateMachine = sdcStateMachine.buildMachine();

		stateMachine.start();
		stateMachine.sendEvent(SmartContractStateMachine.Events.INCEPT);
		assertEquals(SmartContractStateMachine.States.ACTIVE, stateMachine.getState().getId());

		stateMachine.sendEvent(SmartContractStateMachine.Events.SETTLE);
		assertEquals(SmartContractStateMachine.States.SETTLEMENT, stateMachine.getState().getId());

		stateMachine.sendEvent(SmartContractStateMachine.Events.CONTINUE);
		assertEquals(SmartContractStateMachine.States.ACTIVE, stateMachine.getState().getId());

		stateMachine.stop();
	}

	/**
	 * Test method chaining for setter methods.
	 * Verifies that all setters can be chained together.
	 */
	@Test
	void testMethodChaining() {
		SmartContractStateMachine stateMachine = new SmartContractStateMachine();

		SmartContractStateMachine result = stateMachine
				.setPrefunded(false)
				.setMatured(true)
				.setSettlementSuccessful(false);

		assertSame(stateMachine, result);
		assertFalse(stateMachine.isPrefunded());
		assertTrue(stateMachine.isMatured());
		assertFalse(stateMachine.isSettlementSuccessful());
	}

	/**
	 * Test multiple settlement cycles.
	 * Verifies that the machine can handle multiple settlement cycles.
	 */
	@Test
	void testMultipleSettlementCycles() throws Exception {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();
		sdcStateMachine.setMatured(false).setPrefunded(true).setSettlementSuccessful(true);

		StateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events> stateMachine = sdcStateMachine.buildMachine();

		stateMachine.start();
		stateMachine.sendEvent(SmartContractStateMachine.Events.INCEPT);

		// First cycle
		stateMachine.sendEvent(SmartContractStateMachine.Events.SETTLE);
		stateMachine.sendEvent(SmartContractStateMachine.Events.CONTINUE);
		assertEquals(SmartContractStateMachine.States.ACTIVE, stateMachine.getState().getId());

		// Second cycle
		stateMachine.sendEvent(SmartContractStateMachine.Events.SETTLE);
		stateMachine.sendEvent(SmartContractStateMachine.Events.CONTINUE);
		assertEquals(SmartContractStateMachine.States.ACTIVE, stateMachine.getState().getId());

		// Third cycle
		stateMachine.sendEvent(SmartContractStateMachine.Events.SETTLE);
		stateMachine.sendEvent(SmartContractStateMachine.Events.CONTINUE);
		assertEquals(SmartContractStateMachine.States.ACTIVE, stateMachine.getState().getId());

		stateMachine.stop();
	}

	// Tests for SmartContractStateMachine.Events enum

	/**
	 * Test Events.values() method.
	 * Verifies that values() returns all enum constants in the correct order.
	 */
	@Test
	void testEventsValues() {
		SmartContractStateMachine.Events[] events = SmartContractStateMachine.Events.values();

		assertNotNull(events);
		assertEquals(6, events.length);
		assertEquals(SmartContractStateMachine.Events.INCEPT, events[0]);
		assertEquals(SmartContractStateMachine.Events.SETTLE, events[1]);
		assertEquals(SmartContractStateMachine.Events.CONTINUE, events[2]);
		assertEquals(SmartContractStateMachine.Events.TERMINATE_BY_INSUFFICIENT_PREFUNDING, events[3]);
		assertEquals(SmartContractStateMachine.Events.TERMINATE_BY_INSUFFICIENT_MARGIN, events[4]);
		assertEquals(SmartContractStateMachine.Events.MATURE, events[5]);
	}

	/**
	 * Test Events.valueOf(String) with valid event name "INCEPT".
	 * Verifies that valueOf returns the correct enum constant for INCEPT.
	 */
	@Test
	void testEventsValueOfIncept() {
		SmartContractStateMachine.Events event = SmartContractStateMachine.Events.valueOf("INCEPT");
		assertEquals(SmartContractStateMachine.Events.INCEPT, event);
	}

	/**
	 * Test Events.valueOf(String) with valid event name "SETTLE".
	 * Verifies that valueOf returns the correct enum constant for SETTLE.
	 */
	@Test
	void testEventsValueOfSettle() {
		SmartContractStateMachine.Events event = SmartContractStateMachine.Events.valueOf("SETTLE");
		assertEquals(SmartContractStateMachine.Events.SETTLE, event);
	}

	/**
	 * Test Events.valueOf(String) with valid event name "CONTINUE".
	 * Verifies that valueOf returns the correct enum constant for CONTINUE.
	 */
	@Test
	void testEventsValueOfContinue() {
		SmartContractStateMachine.Events event = SmartContractStateMachine.Events.valueOf("CONTINUE");
		assertEquals(SmartContractStateMachine.Events.CONTINUE, event);
	}

	/**
	 * Test Events.valueOf(String) with valid event name "TERMINATE_BY_INSUFFICIENT_PREFUNDING".
	 * Verifies that valueOf returns the correct enum constant.
	 */
	@Test
	void testEventsValueOfTerminateByInsufficientPrefunding() {
		SmartContractStateMachine.Events event = SmartContractStateMachine.Events.valueOf("TERMINATE_BY_INSUFFICIENT_PREFUNDING");
		assertEquals(SmartContractStateMachine.Events.TERMINATE_BY_INSUFFICIENT_PREFUNDING, event);
	}

	/**
	 * Test Events.valueOf(String) with valid event name "TERMINATE_BY_INSUFFICIENT_MARGIN".
	 * Verifies that valueOf returns the correct enum constant.
	 */
	@Test
	void testEventsValueOfTerminateByInsufficientMargin() {
		SmartContractStateMachine.Events event = SmartContractStateMachine.Events.valueOf("TERMINATE_BY_INSUFFICIENT_MARGIN");
		assertEquals(SmartContractStateMachine.Events.TERMINATE_BY_INSUFFICIENT_MARGIN, event);
	}

	/**
	 * Test Events.valueOf(String) with valid event name "MATURE".
	 * Verifies that valueOf returns the correct enum constant for MATURE.
	 */
	@Test
	void testEventsValueOfMature() {
		SmartContractStateMachine.Events event = SmartContractStateMachine.Events.valueOf("MATURE");
		assertEquals(SmartContractStateMachine.Events.MATURE, event);
	}

	/**
	 * Test Events.valueOf(String) with invalid event name.
	 * Verifies that valueOf throws IllegalArgumentException for non-existent enum constant.
	 */
	@Test
	void testEventsValueOfInvalidName() {
		assertThrows(IllegalArgumentException.class, () -> {
			SmartContractStateMachine.Events.valueOf("INVALID_EVENT");
		});
	}

	/**
	 * Test Events.valueOf(String) with null argument.
	 * Verifies that valueOf throws NullPointerException for null input.
	 */
	@Test
	void testEventsValueOfNull() {
		assertThrows(NullPointerException.class, () -> {
			SmartContractStateMachine.Events.valueOf(null);
		});
	}

	/**
	 * Test Events.valueOf(String) with empty string.
	 * Verifies that valueOf throws IllegalArgumentException for empty string.
	 */
	@Test
	void testEventsValueOfEmptyString() {
		assertThrows(IllegalArgumentException.class, () -> {
			SmartContractStateMachine.Events.valueOf("");
		});
	}

	/**
	 * Test Events.valueOf(String) with lowercase name.
	 * Verifies that valueOf is case-sensitive and throws IllegalArgumentException.
	 */
	@Test
	void testEventsValueOfLowercase() {
		assertThrows(IllegalArgumentException.class, () -> {
			SmartContractStateMachine.Events.valueOf("incept");
		});
	}

	/**
	 * Test that values() returns a new array each time.
	 * Verifies defensive copying behavior of the values() method.
	 */
	@Test
	void testEventsValuesReturnsNewArray() {
		SmartContractStateMachine.Events[] events1 = SmartContractStateMachine.Events.values();
		SmartContractStateMachine.Events[] events2 = SmartContractStateMachine.Events.values();

		assertNotSame(events1, events2);
		assertArrayEquals(events1, events2);
	}

	/**
	 * Test that modifying the array returned by values() doesn't affect subsequent calls.
	 * Verifies that the enum's internal array is protected.
	 */
	@Test
	void testEventsValuesArrayModificationDoesNotAffectEnum() {
		SmartContractStateMachine.Events[] events1 = SmartContractStateMachine.Events.values();
		events1[0] = SmartContractStateMachine.Events.MATURE;

		SmartContractStateMachine.Events[] events2 = SmartContractStateMachine.Events.values();
		assertEquals(SmartContractStateMachine.Events.INCEPT, events2[0]);
	}

	// Tests for SmartContractStateMachine.States enum

	/**
	 * Test States.values() method.
	 * Verifies that values() returns all enum constants in the correct order.
	 */
	@Test
	void testStatesValues() {
		SmartContractStateMachine.States[] states = SmartContractStateMachine.States.values();

		assertNotNull(states);
		assertEquals(9, states.length);
		assertEquals(SmartContractStateMachine.States.INCEPTION, states[0]);
		assertEquals(SmartContractStateMachine.States.ACTIVE, states[1]);
		assertEquals(SmartContractStateMachine.States.SETTLEMENT, states[2]);
		assertEquals(SmartContractStateMachine.States.TERMINATED_BY_INSUFFICIENT_PREFUNDING, states[3]);
		assertEquals(SmartContractStateMachine.States.TERMINATED_BY_INSUFFICIENT_MARGIN, states[4]);
		assertEquals(SmartContractStateMachine.States.TERMINATED_BY_MATURITY, states[5]);
		assertEquals(SmartContractStateMachine.States.PREFUNDING_CHECK, states[6]);
		assertEquals(SmartContractStateMachine.States.SETTLEMENT_CHECK, states[7]);
		assertEquals(SmartContractStateMachine.States.MATURITY_CHECK, states[8]);
	}

	/**
	 * Test States.valueOf(String) with valid state name "INCEPTION".
	 * Verifies that valueOf returns the correct enum constant for INCEPTION.
	 */
	@Test
	void testStatesValueOfInception() {
		SmartContractStateMachine.States state = SmartContractStateMachine.States.valueOf("INCEPTION");
		assertEquals(SmartContractStateMachine.States.INCEPTION, state);
	}

	/**
	 * Test States.valueOf(String) with valid state name "ACTIVE".
	 * Verifies that valueOf returns the correct enum constant for ACTIVE.
	 */
	@Test
	void testStatesValueOfActive() {
		SmartContractStateMachine.States state = SmartContractStateMachine.States.valueOf("ACTIVE");
		assertEquals(SmartContractStateMachine.States.ACTIVE, state);
	}

	/**
	 * Test States.valueOf(String) with valid state name "SETTLEMENT".
	 * Verifies that valueOf returns the correct enum constant for SETTLEMENT.
	 */
	@Test
	void testStatesValueOfSettlement() {
		SmartContractStateMachine.States state = SmartContractStateMachine.States.valueOf("SETTLEMENT");
		assertEquals(SmartContractStateMachine.States.SETTLEMENT, state);
	}

	/**
	 * Test States.valueOf(String) with valid state name "TERMINATED_BY_INSUFFICIENT_PREFUNDING".
	 * Verifies that valueOf returns the correct enum constant.
	 */
	@Test
	void testStatesValueOfTerminatedByInsufficientPrefunding() {
		SmartContractStateMachine.States state = SmartContractStateMachine.States.valueOf("TERMINATED_BY_INSUFFICIENT_PREFUNDING");
		assertEquals(SmartContractStateMachine.States.TERMINATED_BY_INSUFFICIENT_PREFUNDING, state);
	}

	/**
	 * Test States.valueOf(String) with valid state name "TERMINATED_BY_INSUFFICIENT_MARGIN".
	 * Verifies that valueOf returns the correct enum constant.
	 */
	@Test
	void testStatesValueOfTerminatedByInsufficientMargin() {
		SmartContractStateMachine.States state = SmartContractStateMachine.States.valueOf("TERMINATED_BY_INSUFFICIENT_MARGIN");
		assertEquals(SmartContractStateMachine.States.TERMINATED_BY_INSUFFICIENT_MARGIN, state);
	}

	/**
	 * Test States.valueOf(String) with valid state name "TERMINATED_BY_MATURITY".
	 * Verifies that valueOf returns the correct enum constant for TERMINATED_BY_MATURITY.
	 */
	@Test
	void testStatesValueOfTerminatedByMaturity() {
		SmartContractStateMachine.States state = SmartContractStateMachine.States.valueOf("TERMINATED_BY_MATURITY");
		assertEquals(SmartContractStateMachine.States.TERMINATED_BY_MATURITY, state);
	}

	/**
	 * Test States.valueOf(String) with valid state name "PREFUNDING_CHECK".
	 * Verifies that valueOf returns the correct enum constant for PREFUNDING_CHECK.
	 */
	@Test
	void testStatesValueOfPrefundingCheck() {
		SmartContractStateMachine.States state = SmartContractStateMachine.States.valueOf("PREFUNDING_CHECK");
		assertEquals(SmartContractStateMachine.States.PREFUNDING_CHECK, state);
	}

	/**
	 * Test States.valueOf(String) with valid state name "SETTLEMENT_CHECK".
	 * Verifies that valueOf returns the correct enum constant for SETTLEMENT_CHECK.
	 */
	@Test
	void testStatesValueOfSettlementCheck() {
		SmartContractStateMachine.States state = SmartContractStateMachine.States.valueOf("SETTLEMENT_CHECK");
		assertEquals(SmartContractStateMachine.States.SETTLEMENT_CHECK, state);
	}

	/**
	 * Test States.valueOf(String) with valid state name "MATURITY_CHECK".
	 * Verifies that valueOf returns the correct enum constant for MATURITY_CHECK.
	 */
	@Test
	void testStatesValueOfMaturityCheck() {
		SmartContractStateMachine.States state = SmartContractStateMachine.States.valueOf("MATURITY_CHECK");
		assertEquals(SmartContractStateMachine.States.MATURITY_CHECK, state);
	}

	/**
	 * Test States.valueOf(String) with invalid state name.
	 * Verifies that valueOf throws IllegalArgumentException for non-existent enum constant.
	 */
	@Test
	void testStatesValueOfInvalidName() {
		assertThrows(IllegalArgumentException.class, () -> {
			SmartContractStateMachine.States.valueOf("INVALID_STATE");
		});
	}

	/**
	 * Test States.valueOf(String) with null argument.
	 * Verifies that valueOf throws NullPointerException for null input.
	 */
	@Test
	void testStatesValueOfNull() {
		assertThrows(NullPointerException.class, () -> {
			SmartContractStateMachine.States.valueOf(null);
		});
	}

	/**
	 * Test States.valueOf(String) with empty string.
	 * Verifies that valueOf throws IllegalArgumentException for empty string.
	 */
	@Test
	void testStatesValueOfEmptyString() {
		assertThrows(IllegalArgumentException.class, () -> {
			SmartContractStateMachine.States.valueOf("");
		});
	}

	/**
	 * Test States.valueOf(String) with lowercase name.
	 * Verifies that valueOf is case-sensitive and throws IllegalArgumentException.
	 */
	@Test
	void testStatesValueOfLowercase() {
		assertThrows(IllegalArgumentException.class, () -> {
			SmartContractStateMachine.States.valueOf("inception");
		});
	}

	/**
	 * Test that values() returns a new array each time.
	 * Verifies defensive copying behavior of the values() method.
	 */
	@Test
	void testStatesValuesReturnsNewArray() {
		SmartContractStateMachine.States[] states1 = SmartContractStateMachine.States.values();
		SmartContractStateMachine.States[] states2 = SmartContractStateMachine.States.values();

		assertNotSame(states1, states2);
		assertArrayEquals(states1, states2);
	}

	/**
	 * Test that modifying the array returned by values() doesn't affect subsequent calls.
	 * Verifies that the enum's internal array is protected.
	 */
	@Test
	void testStatesValuesArrayModificationDoesNotAffectEnum() {
		SmartContractStateMachine.States[] states1 = SmartContractStateMachine.States.values();
		states1[0] = SmartContractStateMachine.States.MATURITY_CHECK;

		SmartContractStateMachine.States[] states2 = SmartContractStateMachine.States.values();
		assertEquals(SmartContractStateMachine.States.INCEPTION, states2[0]);
	}

	/**
	 * Test States enum ordinal values.
	 * Verifies that each state has the correct ordinal value matching its position.
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
	 * Test States enum toString() method.
	 * Verifies that toString returns the name of the enum constant.
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
	 * Test States enum name() method.
	 * Verifies that name returns the name of the enum constant.
	 */
	@Test
	void testStatesName() {
		assertEquals("INCEPTION", SmartContractStateMachine.States.INCEPTION.name());
		assertEquals("ACTIVE", SmartContractStateMachine.States.ACTIVE.name());
		assertEquals("SETTLEMENT", SmartContractStateMachine.States.SETTLEMENT.name());
		assertEquals("TERMINATED_BY_INSUFFICIENT_PREFUNDING", SmartContractStateMachine.States.TERMINATED_BY_INSUFFICIENT_PREFUNDING.name());
		assertEquals("TERMINATED_BY_INSUFFICIENT_MARGIN", SmartContractStateMachine.States.TERMINATED_BY_INSUFFICIENT_MARGIN.name());
		assertEquals("TERMINATED_BY_MATURITY", SmartContractStateMachine.States.TERMINATED_BY_MATURITY.name());
		assertEquals("PREFUNDING_CHECK", SmartContractStateMachine.States.PREFUNDING_CHECK.name());
		assertEquals("SETTLEMENT_CHECK", SmartContractStateMachine.States.SETTLEMENT_CHECK.name());
		assertEquals("MATURITY_CHECK", SmartContractStateMachine.States.MATURITY_CHECK.name());
	}

	/**
	 * Test States enum compareTo() method.
	 * Verifies that enum constants can be compared based on their ordinal values.
	 */
	@Test
	void testStatesCompareTo() {
		assertTrue(SmartContractStateMachine.States.INCEPTION.compareTo(SmartContractStateMachine.States.ACTIVE) < 0);
		assertTrue(SmartContractStateMachine.States.MATURITY_CHECK.compareTo(SmartContractStateMachine.States.INCEPTION) > 0);
		assertEquals(0, SmartContractStateMachine.States.SETTLEMENT.compareTo(SmartContractStateMachine.States.SETTLEMENT));
	}

	/**
	 * Test States enum getDeclaringClass() method.
	 * Verifies that all enum constants report the correct declaring class.
	 */
	@Test
	void testStatesGetDeclaringClass() {
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
