package net.finmath.smartcontract.statemachine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import net.finmath.smartcontract.statemachine.SmartContractStateMachine.Events;
import net.finmath.smartcontract.statemachine.SmartContractStateMachine.StateMachineListener;
import net.finmath.smartcontract.statemachine.SmartContractStateMachine.States;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.statemachine.ObjectStateMachine;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateContext.Stage;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.state.EnumState;
import org.springframework.statemachine.state.ObjectState;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.DefaultExtendedState;
import org.springframework.statemachine.support.DefaultStateContext;
import org.springframework.statemachine.transition.AbstractInternalTransition;
import org.springframework.statemachine.transition.Transition;
import org.springframework.statemachine.trigger.EventTrigger;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

@ContextConfiguration(classes = {SmartContractStateMachine.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
class SmartContractStateMachineDiffblueTest {
  @Autowired private SmartContractStateMachine smartContractStateMachine;

  /**
   * Test {@link SmartContractStateMachine#buildMachine()}.
   *
   * <p>Method under test: {@link SmartContractStateMachine#buildMachine()}
   */
  @Test
  @DisplayName("Test buildMachine()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"StateMachine SmartContractStateMachine.buildMachine()"})
  void testBuildMachine() throws Exception {
    // Arrange and Act
    StateMachine<States, Events> actualBuildMachineResult =
        smartContractStateMachine.buildMachine();

    // Assert
    Collection<Transition<States, Events>> transitions = actualBuildMachineResult.getTransitions();
    assertEquals(3, transitions.size());
    assertTrue(transitions instanceof List);
    assertTrue(actualBuildMachineResult instanceof ObjectStateMachine);
    assertTrue(actualBuildMachineResult.getInitialState() instanceof ObjectState);
    assertTrue(actualBuildMachineResult.getExtendedState() instanceof DefaultExtendedState);
    assertNull(actualBuildMachineResult.getId());
    assertNull(((ObjectStateMachine<States, Events>) actualBuildMachineResult).getHistoryState());
    assertNull(actualBuildMachineResult.getState());
    assertEquals(0, ((ObjectStateMachine<States, Events>) actualBuildMachineResult).getPhase());
    assertEquals(9, actualBuildMachineResult.getStates().size());
    assertFalse(actualBuildMachineResult.hasStateMachineError());
    assertFalse(((ObjectStateMachine<States, Events>) actualBuildMachineResult).isAutoStartup());
    assertFalse(((ObjectStateMachine<States, Events>) actualBuildMachineResult).isRunning());
    assertTrue(actualBuildMachineResult.isComplete());
  }

  /**
   * Test {@link SmartContractStateMachine#settlementCheck()}.
   *
   * <p>Method under test: {@link SmartContractStateMachine#settlementCheck()}
   */
  @Test
  @DisplayName("Test settlementCheck()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Guard SmartContractStateMachine.settlementCheck()"})
  void testSettlementCheck() {
    // Arrange and Act
    Guard<States, Events> actualSettlementCheckResult = smartContractStateMachine.settlementCheck();
    GenericMessage<Events> message = new GenericMessage<>(Events.INCEPT, new HashMap<>());
    DefaultExtendedState extendedState = new DefaultExtendedState();
    EnumState<States, Events> source = new EnumState<>(States.INCEPTION);
    ArrayList<Function<StateContext<States, Events>, Mono<Void>>> actions = new ArrayList<>();
    Function<StateContext<States, Events>, Mono<Boolean>> guard = mock(Function.class);
    AbstractInternalTransition<States, Events> transition =
        new AbstractInternalTransition<>(
            source, actions, Events.INCEPT, guard, new EventTrigger<>(Events.INCEPT));
    ArrayList<State<States, Events>> states = new ArrayList<>();
    ArrayList<Transition<States, Events>> transitions = new ArrayList<>();
    ObjectStateMachine<States, Events> stateMachine =
        new ObjectStateMachine<>(states, transitions, new EnumState<>(States.INCEPTION));
    EnumState<States, Events> source2 = new EnumState<>(States.INCEPTION);
    EnumState<States, Events> target = new EnumState<>(States.INCEPTION);
    DefaultStateContext<States, Events> defaultStateContext =
        new DefaultStateContext<>(
            Stage.EVENT_NOT_ACCEPTED,
            message,
            null,
            extendedState,
            transition,
            stateMachine,
            source2,
            target,
            new Exception());
    boolean actualEvaluateResult = actualSettlementCheckResult.evaluate(defaultStateContext);

    // Assert
    assertTrue(actualEvaluateResult);
  }

  /**
   * Test {@link SmartContractStateMachine#prefundingCheck()}.
   *
   * <p>Method under test: {@link SmartContractStateMachine#prefundingCheck()}
   */
  @Test
  @DisplayName("Test prefundingCheck()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Guard SmartContractStateMachine.prefundingCheck()"})
  void testPrefundingCheck() {
    // Arrange and Act
    Guard<States, Events> actualPrefundingCheckResult = smartContractStateMachine.prefundingCheck();
    GenericMessage<Events> message = new GenericMessage<>(Events.INCEPT, new HashMap<>());
    DefaultExtendedState extendedState = new DefaultExtendedState();
    EnumState<States, Events> source = new EnumState<>(States.INCEPTION);
    ArrayList<Function<StateContext<States, Events>, Mono<Void>>> actions = new ArrayList<>();
    Function<StateContext<States, Events>, Mono<Boolean>> guard = mock(Function.class);
    AbstractInternalTransition<States, Events> transition =
        new AbstractInternalTransition<>(
            source, actions, Events.INCEPT, guard, new EventTrigger<>(Events.INCEPT));
    ArrayList<State<States, Events>> states = new ArrayList<>();
    ArrayList<Transition<States, Events>> transitions = new ArrayList<>();
    ObjectStateMachine<States, Events> stateMachine =
        new ObjectStateMachine<>(states, transitions, new EnumState<>(States.INCEPTION));
    EnumState<States, Events> source2 = new EnumState<>(States.INCEPTION);
    EnumState<States, Events> target = new EnumState<>(States.INCEPTION);
    DefaultStateContext<States, Events> defaultStateContext =
        new DefaultStateContext<>(
            Stage.EVENT_NOT_ACCEPTED,
            message,
            null,
            extendedState,
            transition,
            stateMachine,
            source2,
            target,
            new Exception());
    boolean actualEvaluateResult = actualPrefundingCheckResult.evaluate(defaultStateContext);

    // Assert
    assertTrue(actualEvaluateResult);
  }

  /**
   * Test {@link SmartContractStateMachine#notMaturedCheck()}.
   *
   * <p>Method under test: {@link SmartContractStateMachine#notMaturedCheck()}
   */
  @Test
  @DisplayName("Test notMaturedCheck()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Guard SmartContractStateMachine.notMaturedCheck()"})
  void testNotMaturedCheck() {
    // Arrange and Act
    Guard<States, Events> actualNotMaturedCheckResult = smartContractStateMachine.notMaturedCheck();
    GenericMessage<Events> message = new GenericMessage<>(Events.INCEPT, new HashMap<>());
    DefaultExtendedState extendedState = new DefaultExtendedState();
    EnumState<States, Events> source = new EnumState<>(States.INCEPTION);
    ArrayList<Function<StateContext<States, Events>, Mono<Void>>> actions = new ArrayList<>();
    Function<StateContext<States, Events>, Mono<Boolean>> guard = mock(Function.class);
    AbstractInternalTransition<States, Events> transition =
        new AbstractInternalTransition<>(
            source, actions, Events.INCEPT, guard, new EventTrigger<>(Events.INCEPT));
    ArrayList<State<States, Events>> states = new ArrayList<>();
    ArrayList<Transition<States, Events>> transitions = new ArrayList<>();
    ObjectStateMachine<States, Events> stateMachine =
        new ObjectStateMachine<>(states, transitions, new EnumState<>(States.INCEPTION));
    EnumState<States, Events> source2 = new EnumState<>(States.INCEPTION);
    EnumState<States, Events> target = new EnumState<>(States.INCEPTION);
    DefaultStateContext<States, Events> defaultStateContext =
        new DefaultStateContext<>(
            Stage.EVENT_NOT_ACCEPTED,
            message,
            null,
            extendedState,
            transition,
            stateMachine,
            source2,
            target,
            new Exception());
    boolean actualEvaluateResult = actualNotMaturedCheckResult.evaluate(defaultStateContext);

    // Assert
    assertTrue(actualEvaluateResult);
  }

  /**
   * Test {@link SmartContractStateMachine#notMaturedCheck()}.
   *
   * <p>Method under test: {@link SmartContractStateMachine#notMaturedCheck()}
   */
  @Test
  @DisplayName("Test notMaturedCheck()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Guard SmartContractStateMachine.notMaturedCheck()"})
  void testNotMaturedCheck2() {
    // Arrange
    SmartContractStateMachine smartContractStateMachine = new SmartContractStateMachine();
    smartContractStateMachine.setMatured(true);

    // Act
    Guard<States, Events> actualNotMaturedCheckResult = smartContractStateMachine.notMaturedCheck();
    GenericMessage<Events> message = new GenericMessage<>(Events.INCEPT, new HashMap<>());
    DefaultExtendedState extendedState = new DefaultExtendedState();
    EnumState<States, Events> source = new EnumState<>(States.INCEPTION);
    ArrayList<Function<StateContext<States, Events>, Mono<Void>>> actions = new ArrayList<>();
    Function<StateContext<States, Events>, Mono<Boolean>> guard = mock(Function.class);
    AbstractInternalTransition<States, Events> transition =
        new AbstractInternalTransition<>(
            source, actions, Events.INCEPT, guard, new EventTrigger<>(Events.INCEPT));
    ArrayList<State<States, Events>> states = new ArrayList<>();
    ArrayList<Transition<States, Events>> transitions = new ArrayList<>();
    ObjectStateMachine<States, Events> stateMachine =
        new ObjectStateMachine<>(states, transitions, new EnumState<>(States.INCEPTION));
    EnumState<States, Events> source2 = new EnumState<>(States.INCEPTION);
    EnumState<States, Events> target = new EnumState<>(States.INCEPTION);
    DefaultStateContext<States, Events> defaultStateContext =
        new DefaultStateContext<>(
            Stage.EVENT_NOT_ACCEPTED,
            message,
            null,
            extendedState,
            transition,
            stateMachine,
            source2,
            target,
            new Exception());
    boolean actualEvaluateResult = actualNotMaturedCheckResult.evaluate(defaultStateContext);

    // Assert
    assertFalse(actualEvaluateResult);
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SmartContractStateMachine.<init>()",
    "boolean SmartContractStateMachine.isMatured()",
    "boolean SmartContractStateMachine.isPrefunded()",
    "boolean SmartContractStateMachine.isSettlementSuccessful()",
    "SmartContractStateMachine SmartContractStateMachine.setMatured(boolean)",
    "SmartContractStateMachine SmartContractStateMachine.setPrefunded(boolean)",
    "SmartContractStateMachine SmartContractStateMachine.setSettlementSuccessful(boolean)"
  })
  void testGettersAndSetters() {
    // Arrange and Act
    SmartContractStateMachine actualSmartContractStateMachine = new SmartContractStateMachine();
    SmartContractStateMachine actualSetMaturedResult =
        actualSmartContractStateMachine.setMatured(true);
    SmartContractStateMachine actualSetPrefundedResult =
        actualSmartContractStateMachine.setPrefunded(true);
    SmartContractStateMachine actualSetSettlementSuccessfulResult =
        actualSmartContractStateMachine.setSettlementSuccessful(true);
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
   * Test StateMachineListener getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link StateMachineListener#StateMachineListener(SmartContractStateMachine)}
   *   <li>{@link StateMachineListener#getThis$0()}
   * </ul>
   */
  @Test
  @DisplayName("Test StateMachineListener getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void StateMachineListener.<init>(SmartContractStateMachine)",
    "SmartContractStateMachine StateMachineListener.getThis$0()"
  })
  void testStateMachineListenerGettersAndSetters() {
    // Arrange
    SmartContractStateMachine smartContractStateMachine = new SmartContractStateMachine();

    // Act and Assert
    assertSame(
        smartContractStateMachine,
        smartContractStateMachine.new StateMachineListener().getThis$0());
  }
}
