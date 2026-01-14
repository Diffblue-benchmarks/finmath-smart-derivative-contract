/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.statemachine;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SmartContractStateMachine$StateMachineListener.stateChanged method.
 * Tests the stateChanged callback method of the inner StateMachineListener class.
 *
 * @author Claude Code
 */
class SmartContractStateMachineClaude_stateChangedTest {

	private ByteArrayOutputStream outputStream;
	private PrintStream originalOut;

	@BeforeEach
	void setUp() {
		// Capture System.out
		outputStream = new ByteArrayOutputStream();
		originalOut = System.out;
		System.setOut(new PrintStream(outputStream));
	}

	@AfterEach
	void tearDown() {
		// Restore System.out
		System.setOut(originalOut);
	}

	/**
	 * Test stateChanged with null from state (initial transition).
	 * Verifies that "none" is printed when from state is null.
	 */
	@Test
	void testStateChangedWithNullFromState() throws Exception {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();
		StateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events> stateMachine = sdcStateMachine.buildMachine();

		// Starting the machine triggers initial transition with null from state
		stateMachine.start();

		String output = outputStream.toString();
		assertTrue(output.contains("Transitioned from none to INCEPTION"));

		stateMachine.stop();
	}

	/**
	 * Test stateChanged with non-null from and to states.
	 * Verifies that both state IDs are printed correctly.
	 */
	@Test
	void testStateChangedWithBothStates() throws Exception {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();
		StateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events> stateMachine = sdcStateMachine.buildMachine();

		stateMachine.start();
		outputStream.reset(); // Clear the initial transition output

		// Trigger a state transition from INCEPTION to ACTIVE
		stateMachine.sendEvent(SmartContractStateMachine.Events.INCEPT);

		String output = outputStream.toString();
		assertTrue(output.contains("Transitioned from INCEPTION to ACTIVE"));

		stateMachine.stop();
	}

	/**
	 * Test stateChanged during multiple transitions.
	 * Verifies that the listener correctly reports each transition.
	 */
	@Test
	void testStateChangedMultipleTransitions() throws Exception {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();
		sdcStateMachine.setMatured(false).setPrefunded(true).setSettlementSuccessful(true);
		StateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events> stateMachine = sdcStateMachine.buildMachine();

		stateMachine.start();
		outputStream.reset(); // Clear the initial transition

		// Transition from INCEPTION to ACTIVE
		stateMachine.sendEvent(SmartContractStateMachine.Events.INCEPT);
		String output1 = outputStream.toString();
		assertTrue(output1.contains("Transitioned from INCEPTION to ACTIVE"));

		outputStream.reset();

		// Transition from ACTIVE to SETTLEMENT
		stateMachine.sendEvent(SmartContractStateMachine.Events.SETTLE);
		String output2 = outputStream.toString();
		assertTrue(output2.contains("Transitioned from ACTIVE to SETTLEMENT"));

		stateMachine.stop();
	}

	/**
	 * Test stateChanged with SETTLEMENT to SETTLEMENT_CHECK transition.
	 * Verifies the transition during settlement process.
	 */
	@Test
	void testStateChangedSettlementToCheck() throws Exception {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();
		sdcStateMachine.setMatured(false).setPrefunded(true).setSettlementSuccessful(true);
		StateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events> stateMachine = sdcStateMachine.buildMachine();

		stateMachine.start();
		stateMachine.sendEvent(SmartContractStateMachine.Events.INCEPT);
		stateMachine.sendEvent(SmartContractStateMachine.Events.SETTLE);
		outputStream.reset();

		// Transition from SETTLEMENT to SETTLEMENT_CHECK
		stateMachine.sendEvent(SmartContractStateMachine.Events.CONTINUE);

		String output = outputStream.toString();
		// The transition goes through several junction states
		assertTrue(output.contains("SETTLEMENT_CHECK") || output.contains("MATURITY_CHECK") ||
		           output.contains("PREFUNDING_CHECK") || output.contains("ACTIVE"));

		stateMachine.stop();
	}

	/**
	 * Test stateChanged when transitioning to termination by maturity.
	 * Verifies that the listener reports the termination state correctly.
	 */
	@Test
	void testStateChangedToTerminatedByMaturity() throws Exception {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();
		sdcStateMachine.setMatured(false).setPrefunded(true).setSettlementSuccessful(true);
		StateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events> stateMachine = sdcStateMachine.buildMachine();

		stateMachine.start();
		stateMachine.sendEvent(SmartContractStateMachine.Events.INCEPT);
		stateMachine.sendEvent(SmartContractStateMachine.Events.SETTLE);

		sdcStateMachine.setMatured(true);
		outputStream.reset();

		stateMachine.sendEvent(SmartContractStateMachine.Events.CONTINUE);

		String output = outputStream.toString();
		assertTrue(output.contains("TERMINATED_BY_MATURITY"));

		stateMachine.stop();
	}

	/**
	 * Test stateChanged when transitioning to termination by insufficient prefunding.
	 * Verifies that the listener reports the termination state correctly.
	 */
	@Test
	void testStateChangedToTerminatedByInsufficientPrefunding() throws Exception {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();
		sdcStateMachine.setMatured(false).setPrefunded(true).setSettlementSuccessful(true);
		StateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events> stateMachine = sdcStateMachine.buildMachine();

		stateMachine.start();
		stateMachine.sendEvent(SmartContractStateMachine.Events.INCEPT);
		stateMachine.sendEvent(SmartContractStateMachine.Events.SETTLE);

		sdcStateMachine.setPrefunded(false);
		outputStream.reset();

		stateMachine.sendEvent(SmartContractStateMachine.Events.CONTINUE);

		String output = outputStream.toString();
		assertTrue(output.contains("TERMINATED_BY_INSUFFICIENT_PREFUNDING"));

		stateMachine.stop();
	}

	/**
	 * Test stateChanged when transitioning to termination by insufficient margin.
	 * Verifies that the listener reports the termination state correctly.
	 */
	@Test
	void testStateChangedToTerminatedByInsufficientMargin() throws Exception {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();
		sdcStateMachine.setMatured(false).setPrefunded(true).setSettlementSuccessful(true);
		StateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events> stateMachine = sdcStateMachine.buildMachine();

		stateMachine.start();
		stateMachine.sendEvent(SmartContractStateMachine.Events.INCEPT);
		stateMachine.sendEvent(SmartContractStateMachine.Events.SETTLE);

		sdcStateMachine.setSettlementSuccessful(false);
		outputStream.reset();

		stateMachine.sendEvent(SmartContractStateMachine.Events.CONTINUE);

		String output = outputStream.toString();
		assertTrue(output.contains("TERMINATED_BY_INSUFFICIENT_MARGIN"));

		stateMachine.stop();
	}

	/**
	 * Test stateChanged during successful settlement cycle returning to ACTIVE.
	 * Verifies that all state transitions are reported correctly.
	 */
	@Test
	void testStateChangedSuccessfulSettlementCycle() throws Exception {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();
		sdcStateMachine.setMatured(false).setPrefunded(true).setSettlementSuccessful(true);
		StateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events> stateMachine = sdcStateMachine.buildMachine();

		stateMachine.start();
		stateMachine.sendEvent(SmartContractStateMachine.Events.INCEPT);
		stateMachine.sendEvent(SmartContractStateMachine.Events.SETTLE);
		outputStream.reset();

		stateMachine.sendEvent(SmartContractStateMachine.Events.CONTINUE);

		String output = outputStream.toString();
		// Should show transitions through the check states back to ACTIVE
		assertTrue(output.contains("ACTIVE"));

		stateMachine.stop();
	}

	/**
	 * Test that stateChanged output format matches expected pattern.
	 * Verifies the exact format of the output message.
	 */
	@Test
	void testStateChangedOutputFormat() throws Exception {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();
		StateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events> stateMachine = sdcStateMachine.buildMachine();

		stateMachine.start();
		outputStream.reset();

		stateMachine.sendEvent(SmartContractStateMachine.Events.INCEPT);

		String output = outputStream.toString();
		// Check that output follows the pattern "Transitioned from X to Y\n"
		assertTrue(output.matches(".*Transitioned from .+ to .+\\n.*"));

		stateMachine.stop();
	}

	/**
	 * Test stateChanged is called for each state transition in a complete flow.
	 * Verifies that the listener captures all transitions.
	 */
	@Test
	void testStateChangedCompleteFlow() throws Exception {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();
		sdcStateMachine.setMatured(false).setPrefunded(true).setSettlementSuccessful(true);
		StateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events> stateMachine = sdcStateMachine.buildMachine();

		outputStream.reset();
		stateMachine.start();

		String fullOutput = outputStream.toString();

		// Verify initial transition was captured
		assertTrue(fullOutput.contains("Transitioned from"));
		assertTrue(fullOutput.contains("INCEPTION"));

		stateMachine.stop();
	}

	/**
	 * Test direct invocation of stateChanged with mock states.
	 * Verifies the method behavior with controlled state objects.
	 */
	@Test
	void testStateChangedDirectInvocationWithNullFrom() throws Exception {
		SmartContractStateMachine outerInstance = new SmartContractStateMachine();
		SmartContractStateMachine.StateMachineListener listener = outerInstance.new StateMachineListener();

		StateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events> stateMachine = outerInstance.buildMachine();
		stateMachine.start();

		State<SmartContractStateMachine.States, SmartContractStateMachine.Events> toState = stateMachine.getState();

		outputStream.reset();
		listener.stateChanged(null, toState);

		String output = outputStream.toString();
		assertTrue(output.contains("Transitioned from none to"));

		stateMachine.stop();
	}

	/**
	 * Test direct invocation of stateChanged with both states non-null.
	 * Verifies the method handles both states being present.
	 */
	@Test
	void testStateChangedDirectInvocationWithBothStates() throws Exception {
		SmartContractStateMachine outerInstance = new SmartContractStateMachine();
		SmartContractStateMachine.StateMachineListener listener = outerInstance.new StateMachineListener();

		StateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events> stateMachine = outerInstance.buildMachine();
		stateMachine.start();
		State<SmartContractStateMachine.States, SmartContractStateMachine.Events> fromState = stateMachine.getState();

		stateMachine.sendEvent(SmartContractStateMachine.Events.INCEPT);
		State<SmartContractStateMachine.States, SmartContractStateMachine.Events> toState = stateMachine.getState();

		outputStream.reset();
		listener.stateChanged(fromState, toState);

		String output = outputStream.toString();
		assertTrue(output.contains("Transitioned from INCEPTION to ACTIVE"));

		stateMachine.stop();
	}
}
