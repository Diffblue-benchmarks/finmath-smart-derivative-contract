package net.finmath.smartcontract.statemachine;

import net.finmath.smartcontract.statemachine.SmartContractStateMachine.States;
import net.finmath.smartcontract.statemachine.SmartContractStateMachine.Events;
import org.junit.jupiter.api.Test;
import org.springframework.statemachine.state.State;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SmartContractStateMachineListenerTest {

	@SuppressWarnings("unchecked")
	@Test
	void testStateChangedWithFromNull() {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();
		SmartContractStateMachine.StateMachineListener listener = sdcStateMachine.new StateMachineListener();

		State<States, Events> toState = mock(State.class);
		when(toState.getId()).thenReturn(States.INCEPTION);

		listener.stateChanged(null, toState);
	}

	@SuppressWarnings("unchecked")
	@Test
	void testStateChangedWithFromNonNull() {
		SmartContractStateMachine sdcStateMachine = new SmartContractStateMachine();
		SmartContractStateMachine.StateMachineListener listener = sdcStateMachine.new StateMachineListener();

		State<States, Events> fromState = mock(State.class);
		when(fromState.getId()).thenReturn(States.INCEPTION);

		State<States, Events> toState = mock(State.class);
		when(toState.getId()).thenReturn(States.ACTIVE);

		listener.stateChanged(fromState, toState);
	}
}
