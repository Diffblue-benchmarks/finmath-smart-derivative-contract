/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.statemachine;

import org.junit.jupiter.api.Test;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SmartContractStateMachine$StateMachineListener constructor.
 * Tests the constructor of the inner StateMachineListener class.
 *
 * @author Claude Code
 */
class SmartContractStateMachineClaude_constructorTest {

	/**
	 * Test that the StateMachineListener can be instantiated with a valid outer class instance.
	 * Verifies that the constructor creates a non-null instance.
	 */
	@Test
	void testStateMachineListenerConstructor() {
		SmartContractStateMachine outerInstance = new SmartContractStateMachine();
		SmartContractStateMachine.StateMachineListener listener = outerInstance.new StateMachineListener();

		assertNotNull(listener);
	}

	/**
	 * Test that the constructed listener is an instance of StateMachineListenerAdapter.
	 * Verifies that the listener correctly extends the expected parent class.
	 */
	@Test
	void testStateMachineListenerExtendsAdapter() {
		SmartContractStateMachine outerInstance = new SmartContractStateMachine();
		SmartContractStateMachine.StateMachineListener listener = outerInstance.new StateMachineListener();

		assertTrue(listener instanceof StateMachineListenerAdapter);
	}

	/**
	 * Test that multiple instances of StateMachineListener can be created from the same outer instance.
	 * Verifies that the constructor can be called multiple times.
	 */
	@Test
	void testMultipleListenerInstances() {
		SmartContractStateMachine outerInstance = new SmartContractStateMachine();
		SmartContractStateMachine.StateMachineListener listener1 = outerInstance.new StateMachineListener();
		SmartContractStateMachine.StateMachineListener listener2 = outerInstance.new StateMachineListener();

		assertNotNull(listener1);
		assertNotNull(listener2);
		assertNotSame(listener1, listener2);
	}

	/**
	 * Test that listeners from different outer instances are distinct.
	 * Verifies that each listener is associated with its own outer instance.
	 */
	@Test
	void testListenersFromDifferentOuterInstances() {
		SmartContractStateMachine outerInstance1 = new SmartContractStateMachine();
		SmartContractStateMachine outerInstance2 = new SmartContractStateMachine();

		SmartContractStateMachine.StateMachineListener listener1 = outerInstance1.new StateMachineListener();
		SmartContractStateMachine.StateMachineListener listener2 = outerInstance2.new StateMachineListener();

		assertNotNull(listener1);
		assertNotNull(listener2);
		assertNotSame(listener1, listener2);
	}

	/**
	 * Test that the listener can be created from an outer instance with modified state.
	 * Verifies that the constructor works regardless of the outer instance's state.
	 */
	@Test
	void testListenerWithModifiedOuterInstance() {
		SmartContractStateMachine outerInstance = new SmartContractStateMachine();
		outerInstance.setPrefunded(false);
		outerInstance.setMatured(true);
		outerInstance.setSettlementSuccessful(false);

		SmartContractStateMachine.StateMachineListener listener = outerInstance.new StateMachineListener();

		assertNotNull(listener);
	}
}
