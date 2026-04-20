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
	void testGettersAndSetters() {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();

		assertTrue(sdcStateMachine.isPrefunded(), "Default isPrefunded should be true");
		assertFalse(sdcStateMachine.isMatured(), "Default isMatured should be false");
		assertTrue(sdcStateMachine.isSettlementSuccessful(), "Default isSettlementSuccessful should be true");

		sdcStateMachine.setPrefunded(false);
		assertFalse(sdcStateMachine.isPrefunded());

		sdcStateMachine.setMatured(true);
		assertTrue(sdcStateMachine.isMatured());

		sdcStateMachine.setSettlementSuccessful(false);
		assertFalse(sdcStateMachine.isSettlementSuccessful());
	}

	@Test
	void testSetterChaining() {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();

		SmartContractStateMachine result = sdcStateMachine
				.setMatured(false)
				.setPrefunded(true)
				.setSettlementSuccessful(true);

		assertEquals(sdcStateMachine, result, "Setters should return self reference");
	}

	@Test
	void testGuards() {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();

		Guard<States, Events> settlement = sdcStateMachine.settlementCheck();
		assertNotNull(settlement);
		assertTrue(settlement.evaluate(null), "Settlement check should return true by default");

		Guard<States, Events> prefunding = sdcStateMachine.prefundingCheck();
		assertNotNull(prefunding);
		assertTrue(prefunding.evaluate(null), "Prefunding check should return true by default");

		Guard<States, Events> notMatured = sdcStateMachine.notMaturedCheck();
		assertNotNull(notMatured);
		assertTrue(notMatured.evaluate(null), "Not matured check should return true when not matured");

		sdcStateMachine.setMatured(true);
		assertFalse(sdcStateMachine.notMaturedCheck().evaluate(null), "Not matured check should return false when matured");

		sdcStateMachine.setSettlementSuccessful(false);
		assertFalse(sdcStateMachine.settlementCheck().evaluate(null));

		sdcStateMachine.setPrefunded(false);
		assertFalse(sdcStateMachine.prefundingCheck().evaluate(null));
	}

	@Test
	void testPerformSettlement() {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();
		Action<States, Events> action = sdcStateMachine.performSettlement();
		assertNotNull(action);
		action.execute(null);
	}

	@Test
	void testMain() throws Exception {
		SmartContractStateMachine.main(new String[]{});
	}

	@Test
	void testBuildMachine() throws Exception {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();
		StateMachine<States, Events> stateMachine = sdcStateMachine.buildMachine();
		assertNotNull(stateMachine);

		stateMachine.start();
		assertEquals(States.INCEPTION, stateMachine.getState().getId(), "Initial state should be INCEPTION");

		stateMachine.sendEvent(Events.INCEPT);
		assertEquals(States.ACTIVE, stateMachine.getState().getId(), "After INCEPT should be ACTIVE");

		stateMachine.sendEvent(Events.SETTLE);
		assertEquals(States.SETTLEMENT, stateMachine.getState().getId(), "After SETTLE should be SETTLEMENT");

		stateMachine.sendEvent(Events.CONTINUE);
		assertEquals(States.ACTIVE, stateMachine.getState().getId(), "After CONTINUE should be ACTIVE");

		stateMachine.stop();
	}
}
