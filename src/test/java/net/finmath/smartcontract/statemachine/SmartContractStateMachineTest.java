/*
 * (c) Copyright Christian P. Fries, Germany. All rights reserved. Contact: email@christianfries.com.
 *
 * Created on 26 Dec 2019
 */

package net.finmath.smartcontract.statemachine;

import net.finmath.smartcontract.statemachine.SmartContractStateMachine.Events;
import net.finmath.smartcontract.statemachine.SmartContractStateMachine.States;
import org.junit.jupiter.api.Test;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.guard.Guard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Christian Fries
 */
class SmartContractStateMachineTest {

	@Test
	void testTerminationByMaturity() throws Exception {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();

		// Create stateMachine. The state machine receives events.
		StateMachine<States, Events> stateMachine = sdcStateMachine.buildMachine();

		/*
		 * perform some transitions, then terminate due to maturity.
		 */
		stateMachine.start();
		stateMachine.sendEvent(Events.INCEPT);
		stateMachine.sendEvent(Events.SETTLE);
		stateMachine.sendEvent(Events.CONTINUE);
		stateMachine.sendEvent(Events.SETTLE);
		stateMachine.sendEvent(Events.CONTINUE);
		stateMachine.sendEvent(Events.SETTLE);
		sdcStateMachine.setMatured(true);
		stateMachine.sendEvent(Events.CONTINUE);

		assertEquals(States.TERMINATED_BY_MATURITY, stateMachine.getState().getId(), "Terminated state");
		stateMachine.stop();
	}

	@Test
	void testTerminationByInsufficientPreFunding() throws Exception {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();

		// Create stateMachine. The state machine receives events.
		StateMachine<States, Events> stateMachine = sdcStateMachine.buildMachine();

		/*
		 * perform some transitions, then terminate due to insufficient pre-funding.
		 */
		stateMachine.start();
		stateMachine.sendEvent(Events.INCEPT);
		stateMachine.sendEvent(Events.SETTLE);
		stateMachine.sendEvent(Events.CONTINUE);
		stateMachine.sendEvent(Events.SETTLE);
		stateMachine.sendEvent(Events.CONTINUE);
		stateMachine.sendEvent(Events.SETTLE);
		sdcStateMachine.setPrefunded(false);
		stateMachine.sendEvent(Events.CONTINUE);

		assertEquals(States.TERMINATED_BY_INSUFFICIENT_PREFUNDING, stateMachine.getState().getId(), "Terminated state");
		stateMachine.stop();
	}

	@Test
	void testTerminationByInsufficientMargin() throws Exception {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();

		// Create stateMachine. The state machine receives events.
		StateMachine<States, Events> stateMachine = sdcStateMachine.buildMachine();

		/*
		 * perform some transitions, then terminate due to insufficient settlement amounts.
		 */
		stateMachine.start();
		stateMachine.sendEvent(Events.INCEPT);
		stateMachine.sendEvent(Events.SETTLE);
		stateMachine.sendEvent(Events.CONTINUE);
		stateMachine.sendEvent(Events.SETTLE);
		stateMachine.sendEvent(Events.CONTINUE);
		stateMachine.sendEvent(Events.SETTLE);
		sdcStateMachine.setSettlementSuccessful(false);
		stateMachine.sendEvent(Events.CONTINUE);

		assertEquals(States.TERMINATED_BY_INSUFFICIENT_MARGIN, stateMachine.getState().getId(), "Terminated state");
		stateMachine.stop();
	}

	@Test
	void testIsPrefunded() {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();

		// Default value
		assertTrue(sdcStateMachine.isPrefunded());

		// After setting to false
		sdcStateMachine.setPrefunded(false);
		assertFalse(sdcStateMachine.isPrefunded());

		// After setting to true
		sdcStateMachine.setPrefunded(true);
		assertTrue(sdcStateMachine.isPrefunded());
	}

	@Test
	void testSetPrefundedReturnsThis() {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();

		SmartContractStateMachine result = sdcStateMachine.setPrefunded(false);

		assertSame(sdcStateMachine, result);
	}

	@Test
	void testIsMatured() {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();

		// Default value
		assertFalse(sdcStateMachine.isMatured());

		// After setting to true
		sdcStateMachine.setMatured(true);
		assertTrue(sdcStateMachine.isMatured());

		// After setting to false
		sdcStateMachine.setMatured(false);
		assertFalse(sdcStateMachine.isMatured());
	}

	@Test
	void testSetMaturedReturnsThis() {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();

		SmartContractStateMachine result = sdcStateMachine.setMatured(true);

		assertSame(sdcStateMachine, result);
	}

	@Test
	void testIsSettlementSuccessful() {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();

		// Default value
		assertTrue(sdcStateMachine.isSettlementSuccessful());

		// After setting to false
		sdcStateMachine.setSettlementSuccessful(false);
		assertFalse(sdcStateMachine.isSettlementSuccessful());

		// After setting to true
		sdcStateMachine.setSettlementSuccessful(true);
		assertTrue(sdcStateMachine.isSettlementSuccessful());
	}

	@Test
	void testSetSettlementSuccessfulReturnsThis() {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();

		SmartContractStateMachine result = sdcStateMachine.setSettlementSuccessful(false);

		assertSame(sdcStateMachine, result);
	}

	@Test
	void testSettlementCheck() {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();

		Guard<States, Events> guard = sdcStateMachine.settlementCheck();

		assertNotNull(guard);
		sdcStateMachine.setSettlementSuccessful(true);
		assertTrue(guard.evaluate(null));

		sdcStateMachine.setSettlementSuccessful(false);
		assertFalse(guard.evaluate(null));
	}

	@Test
	void testPrefundingCheck() {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();

		Guard<States, Events> guard = sdcStateMachine.prefundingCheck();

		assertNotNull(guard);
		sdcStateMachine.setPrefunded(true);
		assertTrue(guard.evaluate(null));

		sdcStateMachine.setPrefunded(false);
		assertFalse(guard.evaluate(null));
	}

	@Test
	void testNotMaturedCheck() {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();

		Guard<States, Events> guard = sdcStateMachine.notMaturedCheck();

		assertNotNull(guard);
		sdcStateMachine.setMatured(false);
		assertTrue(guard.evaluate(null));

		sdcStateMachine.setMatured(true);
		assertFalse(guard.evaluate(null));
	}

	@Test
	void testPerformSettlement() {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();

		Action<States, Events> action = sdcStateMachine.performSettlement();

		assertNotNull(action);
		// Execute the action to cover the execute method
		action.execute(null);
	}

	@Test
	void testBuildMachine() throws Exception {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();

		StateMachine<States, Events> stateMachine = sdcStateMachine.buildMachine();

		assertNotNull(stateMachine);
		stateMachine.start();
		assertEquals(States.INCEPTION, stateMachine.getState().getId());
		stateMachine.stop();
	}
}
