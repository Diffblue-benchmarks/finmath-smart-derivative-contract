package net.finmath.smartcontract.statemachine;

import org.junit.jupiter.api.Test;
import org.springframework.statemachine.StateMachine;

import static org.junit.jupiter.api.Assertions.*;

class SmartContractStateMachineTest {

	@Test
	void buildMachine_shouldCreateStateMachine() throws Exception {
		SmartContractStateMachine sdcSM = new SmartContractStateMachine();
		StateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events> machine = sdcSM.buildMachine();
		assertNotNull(machine);
	}

	@Test
	void stateMachine_shouldTransitionFromInceptionToActive() throws Exception {
		SmartContractStateMachine sdcSM = new SmartContractStateMachine();
		sdcSM.setMatured(false).setPrefunded(true).setSettlementSuccessful(true);

		StateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events> machine = sdcSM.buildMachine();
		machine.start();
		machine.sendEvent(SmartContractStateMachine.Events.INCEPT);

		assertEquals(SmartContractStateMachine.States.ACTIVE, machine.getState().getId());
		machine.stop();
	}

	@Test
	void stateMachine_shouldTransitionToSettlement() throws Exception {
		SmartContractStateMachine sdcSM = new SmartContractStateMachine();
		sdcSM.setMatured(false).setPrefunded(true).setSettlementSuccessful(true);

		StateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events> machine = sdcSM.buildMachine();
		machine.start();
		machine.sendEvent(SmartContractStateMachine.Events.INCEPT);
		machine.sendEvent(SmartContractStateMachine.Events.SETTLE);

		assertEquals(SmartContractStateMachine.States.SETTLEMENT, machine.getState().getId());
		machine.stop();
	}

	@Test
	void stateMachine_shouldReturnToActive_afterSuccessfulSettlement() throws Exception {
		SmartContractStateMachine sdcSM = new SmartContractStateMachine();
		sdcSM.setMatured(false).setPrefunded(true).setSettlementSuccessful(true);

		StateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events> machine = sdcSM.buildMachine();
		machine.start();
		machine.sendEvent(SmartContractStateMachine.Events.INCEPT);
		machine.sendEvent(SmartContractStateMachine.Events.SETTLE);
		machine.sendEvent(SmartContractStateMachine.Events.CONTINUE);

		assertEquals(SmartContractStateMachine.States.ACTIVE, machine.getState().getId());
		machine.stop();
	}

	@Test
	void stateMachine_shouldTerminateByMaturity() throws Exception {
		SmartContractStateMachine sdcSM = new SmartContractStateMachine();
		sdcSM.setMatured(false).setPrefunded(true).setSettlementSuccessful(true);

		StateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events> machine = sdcSM.buildMachine();
		machine.start();
		machine.sendEvent(SmartContractStateMachine.Events.INCEPT);
		machine.sendEvent(SmartContractStateMachine.Events.SETTLE);
		sdcSM.setMatured(true);
		machine.sendEvent(SmartContractStateMachine.Events.CONTINUE);

		assertEquals(SmartContractStateMachine.States.TERMINATED_BY_MATURITY, machine.getState().getId());
		machine.stop();
	}

	@Test
	void stateMachine_shouldTerminateByInsufficientPrefunding() throws Exception {
		SmartContractStateMachine sdcSM = new SmartContractStateMachine();
		sdcSM.setMatured(false).setPrefunded(true).setSettlementSuccessful(true);

		StateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events> machine = sdcSM.buildMachine();
		machine.start();
		machine.sendEvent(SmartContractStateMachine.Events.INCEPT);
		machine.sendEvent(SmartContractStateMachine.Events.SETTLE);
		sdcSM.setPrefunded(false);
		machine.sendEvent(SmartContractStateMachine.Events.CONTINUE);

		assertEquals(SmartContractStateMachine.States.TERMINATED_BY_INSUFFICIENT_PREFUNDING, machine.getState().getId());
		machine.stop();
	}

	@Test
	void stateMachine_shouldTerminateByInsufficientMargin() throws Exception {
		SmartContractStateMachine sdcSM = new SmartContractStateMachine();
		sdcSM.setMatured(false).setPrefunded(true).setSettlementSuccessful(true);

		StateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events> machine = sdcSM.buildMachine();
		machine.start();
		machine.sendEvent(SmartContractStateMachine.Events.INCEPT);
		machine.sendEvent(SmartContractStateMachine.Events.SETTLE);
		sdcSM.setSettlementSuccessful(false);
		machine.sendEvent(SmartContractStateMachine.Events.CONTINUE);

		assertEquals(SmartContractStateMachine.States.TERMINATED_BY_INSUFFICIENT_MARGIN, machine.getState().getId());
		machine.stop();
	}

	@Test
	void guards_shouldReturnCorrectValues() {
		SmartContractStateMachine sdcSM = new SmartContractStateMachine();

		sdcSM.setPrefunded(true);
		assertTrue(sdcSM.isPrefunded());
		assertTrue(sdcSM.prefundingCheck().evaluate(null));

		sdcSM.setPrefunded(false);
		assertFalse(sdcSM.prefundingCheck().evaluate(null));

		sdcSM.setMatured(false);
		assertFalse(sdcSM.isMatured());
		assertTrue(sdcSM.notMaturedCheck().evaluate(null));

		sdcSM.setMatured(true);
		assertFalse(sdcSM.notMaturedCheck().evaluate(null));

		sdcSM.setSettlementSuccessful(true);
		assertTrue(sdcSM.isSettlementSuccessful());
		assertTrue(sdcSM.settlementCheck().evaluate(null));

		sdcSM.setSettlementSuccessful(false);
		assertFalse(sdcSM.settlementCheck().evaluate(null));
	}

	@Test
	void performSettlement_shouldReturnAction() {
		SmartContractStateMachine sdcSM = new SmartContractStateMachine();
		assertNotNull(sdcSM.performSettlement());
	}

	@Test
	void statesEnum_shouldContainAllValues() {
		assertEquals(9, SmartContractStateMachine.States.values().length);
	}

	@Test
	void eventsEnum_shouldContainAllValues() {
		assertEquals(6, SmartContractStateMachine.Events.values().length);
	}

	@Test
	void fluentSetters_shouldReturnSelfReference() {
		SmartContractStateMachine sdcSM = new SmartContractStateMachine();
		SmartContractStateMachine result = sdcSM.setPrefunded(true).setMatured(false).setSettlementSuccessful(true);
		assertSame(sdcSM, result);
	}
}
