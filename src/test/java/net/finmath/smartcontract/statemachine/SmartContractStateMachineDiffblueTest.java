package net.finmath.smartcontract.statemachine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.ObjectStateMachine;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.region.Region;
import org.springframework.statemachine.state.DefaultPseudoState;
import org.springframework.statemachine.state.EnumState;
import org.springframework.statemachine.state.JunctionPseudoState;
import org.springframework.statemachine.state.ObjectState;
import org.springframework.statemachine.state.PseudoState;
import org.springframework.statemachine.state.PseudoStateKind;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.DefaultExtendedState;
import org.springframework.statemachine.support.DefaultStateContext;
import org.springframework.statemachine.transition.AbstractInternalTransition;
import org.springframework.statemachine.transition.DefaultExternalTransition;
import org.springframework.statemachine.transition.Transition;
import org.springframework.statemachine.transition.TransitionKind;
import org.springframework.statemachine.trigger.EventTrigger;
import org.springframework.statemachine.trigger.Trigger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

@DisabledInAotMode
@ContextConfiguration(classes = {SmartContractStateMachine.class, SmartContractStateMachine.StateMachineListener.class})
@ExtendWith(SpringExtension.class)
class SmartContractStateMachineDiffblueTest {
  @Autowired
  private SmartContractStateMachine.StateMachineListener stateMachineListener;

  @Autowired
  private SmartContractStateMachine smartContractStateMachine;

  /**
   * Method under test: {@link SmartContractStateMachine#buildMachine()}
   */
  @Test
  void testBuildMachine() throws Exception {
    // Arrange and Act
    StateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events> actualBuildMachineResult = smartContractStateMachine
        .buildMachine();

    // Assert
    Collection<Transition<SmartContractStateMachine.States, SmartContractStateMachine.Events>> transitions = actualBuildMachineResult
        .getTransitions();
    assertEquals(3, transitions.size());
    assertTrue(transitions instanceof List);
    State<SmartContractStateMachine.States, SmartContractStateMachine.Events> initialState = actualBuildMachineResult
        .getInitialState();
    Collection<Region<SmartContractStateMachine.States, SmartContractStateMachine.Events>> regions = ((ObjectState<SmartContractStateMachine.States, SmartContractStateMachine.Events>) initialState)
        .getRegions();
    assertTrue(regions instanceof List);
    Transition<SmartContractStateMachine.States, SmartContractStateMachine.Events> getResult = ((List<Transition<SmartContractStateMachine.States, SmartContractStateMachine.Events>>) transitions)
        .get(0);
    State<SmartContractStateMachine.States, SmartContractStateMachine.Events> target = getResult.getTarget();
    Collection<Region<SmartContractStateMachine.States, SmartContractStateMachine.Events>> regions2 = ((ObjectState<SmartContractStateMachine.States, SmartContractStateMachine.Events>) target)
        .getRegions();
    assertTrue(regions2 instanceof List);
    Transition<SmartContractStateMachine.States, SmartContractStateMachine.Events> getResult2 = ((List<Transition<SmartContractStateMachine.States, SmartContractStateMachine.Events>>) transitions)
        .get(1);
    State<SmartContractStateMachine.States, SmartContractStateMachine.Events> target2 = getResult2.getTarget();
    Collection<Region<SmartContractStateMachine.States, SmartContractStateMachine.Events>> regions3 = ((ObjectState<SmartContractStateMachine.States, SmartContractStateMachine.Events>) target2)
        .getRegions();
    assertTrue(regions3 instanceof List);
    Transition<SmartContractStateMachine.States, SmartContractStateMachine.Events> getResult3 = ((List<Transition<SmartContractStateMachine.States, SmartContractStateMachine.Events>>) transitions)
        .get(2);
    State<SmartContractStateMachine.States, SmartContractStateMachine.Events> target3 = getResult3.getTarget();
    Collection<Region<SmartContractStateMachine.States, SmartContractStateMachine.Events>> regions4 = ((ObjectState<SmartContractStateMachine.States, SmartContractStateMachine.Events>) target3)
        .getRegions();
    assertTrue(regions4 instanceof List);
    Collection<Function<StateContext<SmartContractStateMachine.States, SmartContractStateMachine.Events>, Mono<Void>>> stateActions = target2
        .getStateActions();
    assertEquals(1, stateActions.size());
    assertTrue(stateActions instanceof List);
    Collection<State<SmartContractStateMachine.States, SmartContractStateMachine.Events>> states = initialState
        .getStates();
    assertEquals(1, states.size());
    assertTrue(states instanceof List);
    Collection<State<SmartContractStateMachine.States, SmartContractStateMachine.Events>> states2 = target.getStates();
    assertEquals(1, states2.size());
    assertTrue(states2 instanceof List);
    Collection<State<SmartContractStateMachine.States, SmartContractStateMachine.Events>> states3 = target2.getStates();
    assertEquals(1, states3.size());
    assertTrue(states3 instanceof List);
    Collection<State<SmartContractStateMachine.States, SmartContractStateMachine.Events>> states4 = target3.getStates();
    assertEquals(1, states4.size());
    assertTrue(states4 instanceof List);
    Collection<Function<StateContext<SmartContractStateMachine.States, SmartContractStateMachine.Events>, Mono<Void>>> actions = getResult
        .getActions();
    assertTrue(actions instanceof List);
    Collection<Function<StateContext<SmartContractStateMachine.States, SmartContractStateMachine.Events>, Mono<Void>>> actions2 = getResult2
        .getActions();
    assertTrue(actions2 instanceof List);
    Collection<Function<StateContext<SmartContractStateMachine.States, SmartContractStateMachine.Events>, Mono<Void>>> actions3 = getResult3
        .getActions();
    assertTrue(actions3 instanceof List);
    Collection<SmartContractStateMachine.Events> deferredEvents = initialState.getDeferredEvents();
    assertTrue(deferredEvents instanceof Set);
    assertTrue(actualBuildMachineResult instanceof ObjectStateMachine);
    PseudoState<SmartContractStateMachine.States, SmartContractStateMachine.Events> pseudoState = initialState
        .getPseudoState();
    assertTrue(pseudoState instanceof DefaultPseudoState);
    PseudoState<SmartContractStateMachine.States, SmartContractStateMachine.Events> pseudoState2 = target3
        .getPseudoState();
    assertTrue(pseudoState2 instanceof JunctionPseudoState);
    assertTrue(initialState instanceof ObjectState);
    assertTrue(target instanceof ObjectState);
    assertTrue(target2 instanceof ObjectState);
    assertTrue(target3 instanceof ObjectState);
    ExtendedState extendedState = actualBuildMachineResult.getExtendedState();
    assertTrue(extendedState instanceof DefaultExtendedState);
    assertTrue(getResult instanceof DefaultExternalTransition);
    assertTrue(getResult2 instanceof DefaultExternalTransition);
    assertTrue(getResult3 instanceof DefaultExternalTransition);
    Trigger<SmartContractStateMachine.States, SmartContractStateMachine.Events> trigger = getResult.getTrigger();
    assertTrue(trigger instanceof EventTrigger);
    Trigger<SmartContractStateMachine.States, SmartContractStateMachine.Events> trigger2 = getResult2.getTrigger();
    assertTrue(trigger2 instanceof EventTrigger);
    Trigger<SmartContractStateMachine.States, SmartContractStateMachine.Events> trigger3 = getResult3.getTrigger();
    assertTrue(trigger3 instanceof EventTrigger);
    assertEquals("", getResult.getName());
    assertEquals("", getResult2.getName());
    assertEquals("", getResult3.getName());
    assertNull(actualBuildMachineResult.getId());
    assertNull(getResult.getGuard());
    assertNull(getResult2.getGuard());
    assertNull(getResult3.getGuard());
    assertNull(((ObjectState<SmartContractStateMachine.States, SmartContractStateMachine.Events>) initialState)
        .getSubmachine());
    assertNull(
        ((ObjectState<SmartContractStateMachine.States, SmartContractStateMachine.Events>) target).getSubmachine());
    assertNull(
        ((ObjectState<SmartContractStateMachine.States, SmartContractStateMachine.Events>) target2).getSubmachine());
    assertNull(
        ((ObjectState<SmartContractStateMachine.States, SmartContractStateMachine.Events>) target3).getSubmachine());
    assertNull(getResult.getSecurityRule());
    assertNull(getResult2.getSecurityRule());
    assertNull(getResult3.getSecurityRule());
    assertNull(target.getPseudoState());
    assertNull(target2.getPseudoState());
    assertNull(
        ((ObjectStateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events>) actualBuildMachineResult)
            .getHistoryState());
    assertNull(actualBuildMachineResult.getState());
    assertEquals(0,
        ((ObjectStateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events>) actualBuildMachineResult)
            .getPhase());
    assertEquals(0,
        ((ObjectState<SmartContractStateMachine.States, SmartContractStateMachine.Events>) initialState).getPhase());
    assertEquals(0,
        ((ObjectState<SmartContractStateMachine.States, SmartContractStateMachine.Events>) target).getPhase());
    assertEquals(0,
        ((ObjectState<SmartContractStateMachine.States, SmartContractStateMachine.Events>) target2).getPhase());
    assertEquals(0,
        ((ObjectState<SmartContractStateMachine.States, SmartContractStateMachine.Events>) target3).getPhase());
    assertEquals(1, initialState.getIds().size());
    assertEquals(1, target.getIds().size());
    assertEquals(1, target2.getIds().size());
    assertEquals(1, target3.getIds().size());
    assertEquals(9, actualBuildMachineResult.getStates().size());
    assertEquals(SmartContractStateMachine.Events.CONTINUE, trigger3.getEvent());
    assertEquals(SmartContractStateMachine.Events.INCEPT, trigger.getEvent());
    assertEquals(SmartContractStateMachine.Events.SETTLE, trigger2.getEvent());
    assertEquals(SmartContractStateMachine.States.ACTIVE, target.getId());
    assertEquals(SmartContractStateMachine.States.INCEPTION, initialState.getId());
    assertEquals(SmartContractStateMachine.States.SETTLEMENT, target2.getId());
    assertEquals(SmartContractStateMachine.States.SETTLEMENT_CHECK, target3.getId());
    assertEquals(PseudoStateKind.INITIAL, pseudoState.getKind());
    assertEquals(PseudoStateKind.JUNCTION, pseudoState2.getKind());
    assertEquals(TransitionKind.EXTERNAL, getResult.getKind());
    assertEquals(TransitionKind.EXTERNAL, getResult2.getKind());
    assertEquals(TransitionKind.EXTERNAL, getResult3.getKind());
    assertFalse(actualBuildMachineResult.hasStateMachineError());
    assertFalse(initialState.isComposite());
    assertFalse(target.isComposite());
    assertFalse(target2.isComposite());
    assertFalse(target3.isComposite());
    assertFalse(initialState.isOrthogonal());
    assertFalse(target.isOrthogonal());
    assertFalse(target2.isOrthogonal());
    assertFalse(target3.isOrthogonal());
    assertFalse(initialState.isSubmachineState());
    assertFalse(target.isSubmachineState());
    assertFalse(target2.isSubmachineState());
    assertFalse(target3.isSubmachineState());
    assertFalse(
        ((ObjectStateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events>) actualBuildMachineResult)
            .isAutoStartup());
    assertFalse(((ObjectState<SmartContractStateMachine.States, SmartContractStateMachine.Events>) initialState)
        .isAutoStartup());
    assertFalse(
        ((ObjectState<SmartContractStateMachine.States, SmartContractStateMachine.Events>) target).isAutoStartup());
    assertFalse(
        ((ObjectState<SmartContractStateMachine.States, SmartContractStateMachine.Events>) target2).isAutoStartup());
    assertFalse(
        ((ObjectState<SmartContractStateMachine.States, SmartContractStateMachine.Events>) target3).isAutoStartup());
    assertFalse(
        ((ObjectStateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events>) actualBuildMachineResult)
            .isRunning());
    assertFalse(
        ((ObjectState<SmartContractStateMachine.States, SmartContractStateMachine.Events>) initialState).isRunning());
    assertFalse(((ObjectState<SmartContractStateMachine.States, SmartContractStateMachine.Events>) target).isRunning());
    assertFalse(
        ((ObjectState<SmartContractStateMachine.States, SmartContractStateMachine.Events>) target2).isRunning());
    assertFalse(
        ((ObjectState<SmartContractStateMachine.States, SmartContractStateMachine.Events>) target3).isRunning());
    assertTrue(regions.isEmpty());
    assertTrue(regions2.isEmpty());
    assertTrue(regions3.isEmpty());
    assertTrue(regions4.isEmpty());
    assertTrue(deferredEvents.isEmpty());
    assertTrue(actions.isEmpty());
    assertTrue(actions2.isEmpty());
    assertTrue(actions3.isEmpty());
    assertTrue(
        ((ObjectState<SmartContractStateMachine.States, SmartContractStateMachine.Events>) initialState).getTriggers()
            .isEmpty());
    assertTrue(((ObjectState<SmartContractStateMachine.States, SmartContractStateMachine.Events>) target).getTriggers()
        .isEmpty());
    assertTrue(((ObjectState<SmartContractStateMachine.States, SmartContractStateMachine.Events>) target2).getTriggers()
        .isEmpty());
    assertTrue(((ObjectState<SmartContractStateMachine.States, SmartContractStateMachine.Events>) target3).getTriggers()
        .isEmpty());
    assertTrue(extendedState.getVariables().isEmpty());
    assertTrue(actualBuildMachineResult.isComplete());
    assertTrue(initialState.isSimple());
    assertTrue(target.isSimple());
    assertTrue(target2.isSimple());
    assertTrue(target3.isSimple());
    assertSame(deferredEvents, target.getDeferredEvents());
    assertSame(deferredEvents, target2.getDeferredEvents());
    assertSame(deferredEvents, target3.getDeferredEvents());
    assertSame(deferredEvents, initialState.getEntryActions());
    assertSame(deferredEvents, target.getEntryActions());
    assertSame(deferredEvents, target2.getEntryActions());
    assertSame(deferredEvents, target3.getEntryActions());
    assertSame(deferredEvents, initialState.getExitActions());
    assertSame(deferredEvents, target.getExitActions());
    assertSame(deferredEvents, target2.getExitActions());
    assertSame(deferredEvents, target3.getExitActions());
    assertSame(deferredEvents, initialState.getStateActions());
    assertSame(deferredEvents, target.getStateActions());
    assertSame(deferredEvents, target3.getStateActions());
    assertSame(initialState,
        ((List<State<SmartContractStateMachine.States, SmartContractStateMachine.Events>>) states).get(0));
    assertSame(initialState, getResult.getSource());
    assertSame(target,
        ((List<State<SmartContractStateMachine.States, SmartContractStateMachine.Events>>) states2).get(0));
    assertSame(target, getResult2.getSource());
    assertSame(target2,
        ((List<State<SmartContractStateMachine.States, SmartContractStateMachine.Events>>) states3).get(0));
    assertSame(target2, getResult3.getSource());
    assertSame(target3,
        ((List<State<SmartContractStateMachine.States, SmartContractStateMachine.Events>>) states4).get(0));
  }

  /**
   * Method under test: {@link SmartContractStateMachine#settlementCheck()}
   */
  @Test
  void testSettlementCheck() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    Guard<SmartContractStateMachine.States, SmartContractStateMachine.Events> actualSettlementCheckResult = (new SmartContractStateMachine())
        .settlementCheck();
    GenericMessage<SmartContractStateMachine.Events> message = new GenericMessage<>(
        SmartContractStateMachine.Events.INCEPT, new HashMap<>());

    DefaultExtendedState extendedState = new DefaultExtendedState();
    EnumState<SmartContractStateMachine.States, SmartContractStateMachine.Events> source = new EnumState<>(
        SmartContractStateMachine.States.INCEPTION);
    ArrayList<Function<StateContext<SmartContractStateMachine.States, SmartContractStateMachine.Events>, Mono<Void>>> actions = new ArrayList<>();
    Function<StateContext<SmartContractStateMachine.States, SmartContractStateMachine.Events>, Mono<Boolean>> guard = mock(
        Function.class);
    AbstractInternalTransition<SmartContractStateMachine.States, SmartContractStateMachine.Events> transition = new AbstractInternalTransition<>(
        source, actions, SmartContractStateMachine.Events.INCEPT, guard,
        new EventTrigger<>(SmartContractStateMachine.Events.INCEPT));

    ArrayList<State<SmartContractStateMachine.States, SmartContractStateMachine.Events>> states = new ArrayList<>();
    ArrayList<Transition<SmartContractStateMachine.States, SmartContractStateMachine.Events>> transitions = new ArrayList<>();
    ObjectStateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events> stateMachine = new ObjectStateMachine<>(
        states, transitions, new EnumState<>(SmartContractStateMachine.States.INCEPTION));

    EnumState<SmartContractStateMachine.States, SmartContractStateMachine.Events> source2 = new EnumState<>(
        SmartContractStateMachine.States.INCEPTION);
    EnumState<SmartContractStateMachine.States, SmartContractStateMachine.Events> target = new EnumState<>(
        SmartContractStateMachine.States.INCEPTION);

    // Assert
    assertTrue(actualSettlementCheckResult.evaluate(new DefaultStateContext<>(StateContext.Stage.EVENT_NOT_ACCEPTED,
        message, null, extendedState, transition, stateMachine, source2, target, new Exception("foo"))));
  }

  /**
   * Method under test: {@link SmartContractStateMachine#prefundingCheck()}
   */
  @Test
  void testPrefundingCheck() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    Guard<SmartContractStateMachine.States, SmartContractStateMachine.Events> actualPrefundingCheckResult = (new SmartContractStateMachine())
        .prefundingCheck();
    GenericMessage<SmartContractStateMachine.Events> message = new GenericMessage<>(
        SmartContractStateMachine.Events.INCEPT, new HashMap<>());

    DefaultExtendedState extendedState = new DefaultExtendedState();
    EnumState<SmartContractStateMachine.States, SmartContractStateMachine.Events> source = new EnumState<>(
        SmartContractStateMachine.States.INCEPTION);
    ArrayList<Function<StateContext<SmartContractStateMachine.States, SmartContractStateMachine.Events>, Mono<Void>>> actions = new ArrayList<>();
    Function<StateContext<SmartContractStateMachine.States, SmartContractStateMachine.Events>, Mono<Boolean>> guard = mock(
        Function.class);
    AbstractInternalTransition<SmartContractStateMachine.States, SmartContractStateMachine.Events> transition = new AbstractInternalTransition<>(
        source, actions, SmartContractStateMachine.Events.INCEPT, guard,
        new EventTrigger<>(SmartContractStateMachine.Events.INCEPT));

    ArrayList<State<SmartContractStateMachine.States, SmartContractStateMachine.Events>> states = new ArrayList<>();
    ArrayList<Transition<SmartContractStateMachine.States, SmartContractStateMachine.Events>> transitions = new ArrayList<>();
    ObjectStateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events> stateMachine = new ObjectStateMachine<>(
        states, transitions, new EnumState<>(SmartContractStateMachine.States.INCEPTION));

    EnumState<SmartContractStateMachine.States, SmartContractStateMachine.Events> source2 = new EnumState<>(
        SmartContractStateMachine.States.INCEPTION);
    EnumState<SmartContractStateMachine.States, SmartContractStateMachine.Events> target = new EnumState<>(
        SmartContractStateMachine.States.INCEPTION);

    // Assert
    assertTrue(actualPrefundingCheckResult.evaluate(new DefaultStateContext<>(StateContext.Stage.EVENT_NOT_ACCEPTED,
        message, null, extendedState, transition, stateMachine, source2, target, new Exception("foo"))));
  }

  /**
   * Method under test: {@link SmartContractStateMachine#notMaturedCheck()}
   */
  @Test
  void testNotMaturedCheck() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    Guard<SmartContractStateMachine.States, SmartContractStateMachine.Events> actualNotMaturedCheckResult = (new SmartContractStateMachine())
        .notMaturedCheck();
    GenericMessage<SmartContractStateMachine.Events> message = new GenericMessage<>(
        SmartContractStateMachine.Events.INCEPT, new HashMap<>());

    DefaultExtendedState extendedState = new DefaultExtendedState();
    EnumState<SmartContractStateMachine.States, SmartContractStateMachine.Events> source = new EnumState<>(
        SmartContractStateMachine.States.INCEPTION);
    ArrayList<Function<StateContext<SmartContractStateMachine.States, SmartContractStateMachine.Events>, Mono<Void>>> actions = new ArrayList<>();
    Function<StateContext<SmartContractStateMachine.States, SmartContractStateMachine.Events>, Mono<Boolean>> guard = mock(
        Function.class);
    AbstractInternalTransition<SmartContractStateMachine.States, SmartContractStateMachine.Events> transition = new AbstractInternalTransition<>(
        source, actions, SmartContractStateMachine.Events.INCEPT, guard,
        new EventTrigger<>(SmartContractStateMachine.Events.INCEPT));

    ArrayList<State<SmartContractStateMachine.States, SmartContractStateMachine.Events>> states = new ArrayList<>();
    ArrayList<Transition<SmartContractStateMachine.States, SmartContractStateMachine.Events>> transitions = new ArrayList<>();
    ObjectStateMachine<SmartContractStateMachine.States, SmartContractStateMachine.Events> stateMachine = new ObjectStateMachine<>(
        states, transitions, new EnumState<>(SmartContractStateMachine.States.INCEPTION));

    EnumState<SmartContractStateMachine.States, SmartContractStateMachine.Events> source2 = new EnumState<>(
        SmartContractStateMachine.States.INCEPTION);
    EnumState<SmartContractStateMachine.States, SmartContractStateMachine.Events> target = new EnumState<>(
        SmartContractStateMachine.States.INCEPTION);

    // Assert
    assertTrue(actualNotMaturedCheckResult.evaluate(new DefaultStateContext<>(StateContext.Stage.EVENT_NOT_ACCEPTED,
        message, null, extendedState, transition, stateMachine, source2, target, new Exception("foo"))));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link SmartContractStateMachine}
   *   <li>{@link SmartContractStateMachine#setMatured(boolean)}
   *   <li>{@link SmartContractStateMachine#setPrefunded(boolean)}
   *   <li>{@link SmartContractStateMachine#setSettlementSuccessful(boolean)}
   *   <li>{@link SmartContractStateMachine#isMatured()}
   *   <li>{@link SmartContractStateMachine#isPrefunded()}
   *   <li>{@link SmartContractStateMachine#isSettlementSuccessful()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    SmartContractStateMachine actualSmartContractStateMachine = new SmartContractStateMachine();
    SmartContractStateMachine actualSetMaturedResult = actualSmartContractStateMachine.setMatured(true);
    SmartContractStateMachine actualSetPrefundedResult = actualSmartContractStateMachine.setPrefunded(true);
    SmartContractStateMachine actualSetSettlementSuccessfulResult = actualSmartContractStateMachine
        .setSettlementSuccessful(true);
    boolean actualIsMaturedResult = actualSmartContractStateMachine.isMatured();
    boolean actualIsPrefundedResult = actualSmartContractStateMachine.isPrefunded();

    // Assert
    assertTrue(actualIsMaturedResult);
    assertTrue(actualIsPrefundedResult);
    assertTrue(actualSmartContractStateMachine.isSettlementSuccessful());
    assertSame(actualSmartContractStateMachine, actualSetMaturedResult);
    assertSame(actualSmartContractStateMachine, actualSetPrefundedResult);
    assertSame(actualSmartContractStateMachine, actualSetSettlementSuccessfulResult);
  }

  /**
   * Method under test:
   * {@link SmartContractStateMachine.StateMachineListener#stateChanged(State, State)}
   */
  @Test
  void testStateMachineListenerStateChanged() {
    // Arrange
    EnumState<SmartContractStateMachine.States, SmartContractStateMachine.Events> from = mock(EnumState.class);
    when(from.getId()).thenReturn(SmartContractStateMachine.States.INCEPTION);

    // Act
    stateMachineListener.stateChanged(from, new EnumState<>(SmartContractStateMachine.States.INCEPTION));

    // Assert
    verify(from).getId();
  }
}
