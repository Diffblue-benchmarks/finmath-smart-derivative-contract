/*
 * (c) Copyright Christian P. Fries, Germany. All rights reserved. Contact: email@christianfries.com.
 *
 * Created on 26 Dec 2019
 */

package net.finmath.smartcontract.statemachine;

import net.finmath.smartcontract.statemachine.SmartContractStateMachine.Events;
import net.finmath.smartcontract.statemachine.SmartContractStateMachine.States;
import org.junit.jupiter.api.Test;
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
	void testIsPrefundedDefaultTrue() {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();

		assertTrue(sdcStateMachine.isPrefunded());
	}

	@Test
	void testSetPrefunded() {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();

		SmartContractStateMachine result = sdcStateMachine.setPrefunded(false);

		assertFalse(sdcStateMachine.isPrefunded());
		assertSame(sdcStateMachine, result);
	}

	@Test
	void testIsMaturedDefaultFalse() {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();

		assertFalse(sdcStateMachine.isMatured());
	}

	@Test
	void testSetMatured() {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();

		SmartContractStateMachine result = sdcStateMachine.setMatured(true);

		assertTrue(sdcStateMachine.isMatured());
		assertSame(sdcStateMachine, result);
	}

	@Test
	void testIsSettlementSuccessfulDefaultTrue() {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();

		assertTrue(sdcStateMachine.isSettlementSuccessful());
	}

	@Test
	void testSetSettlementSuccessful() {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();

		SmartContractStateMachine result = sdcStateMachine.setSettlementSuccessful(false);

		assertFalse(sdcStateMachine.isSettlementSuccessful());
		assertSame(sdcStateMachine, result);
	}

	@Test
	void testSettlementCheckReturnsTrueWhenSuccessful() {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();
		sdcStateMachine.setSettlementSuccessful(true);

		Guard<States, Events> guard = sdcStateMachine.settlementCheck();

		assertTrue(guard.evaluate(null));
	}

	@Test
	void testSettlementCheckReturnsFalseWhenNotSuccessful() {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();
		sdcStateMachine.setSettlementSuccessful(false);

		Guard<States, Events> guard = sdcStateMachine.settlementCheck();

		assertFalse(guard.evaluate(null));
	}

	@Test
	void testPrefundingCheckReturnsTrueWhenPrefunded() {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();
		sdcStateMachine.setPrefunded(true);

		Guard<States, Events> guard = sdcStateMachine.prefundingCheck();

		assertTrue(guard.evaluate(null));
	}

	@Test
	void testPrefundingCheckReturnsFalseWhenNotPrefunded() {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();
		sdcStateMachine.setPrefunded(false);

		Guard<States, Events> guard = sdcStateMachine.prefundingCheck();

		assertFalse(guard.evaluate(null));
	}

	@Test
	void testNotMaturedCheckReturnsTrueWhenNotMatured() {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();
		sdcStateMachine.setMatured(false);

		Guard<States, Events> guard = sdcStateMachine.notMaturedCheck();

		assertTrue(guard.evaluate(null));
	}

	@Test
	void testNotMaturedCheckReturnsFalseWhenMatured() {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();
		sdcStateMachine.setMatured(true);

		Guard<States, Events> guard = sdcStateMachine.notMaturedCheck();

		assertFalse(guard.evaluate(null));
	}

	@Test
	void testBuildMachineReturnsStateMachine() throws Exception {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();

		StateMachine<States, Events> stateMachine = sdcStateMachine.buildMachine();

		assertNotNull(stateMachine);
	}

	@Test
	void testPerformSettlementExecutesWithoutException() {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();

		Action<States, Events> action = sdcStateMachine.performSettlement();

		action.execute(null);
	}

	@Test
	void testMainRunsWithoutException() throws Exception {
		SmartContractStateMachine.main(new String[]{});
	}
}
