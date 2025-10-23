package net.finmath.smartcontract.contract;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import net.finmath.smartcontract.contract.SmartDerivativeContractSchedule.EventTimes;
import net.finmath.smartcontract.contract.SmartDerivativeContractScheduleGenerator.EventTimesImpl;
import net.finmath.smartcontract.contract.SmartDerivativeContractScheduleGenerator.SimpleSchedule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SmartDerivativeContractScheduleGeneratorDiffblueTest {
  /**
   * Test EventTimesImpl getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link EventTimesImpl#EventTimesImpl(LocalDateTime, LocalDateTime, Duration,
   *       LocalDateTime)}
   *   <li>{@link EventTimesImpl#getAccountAccessAllowedPeriod()}
   *   <li>{@link EventTimesImpl#getAccountAccessAllowedStart()}
   *   <li>{@link EventTimesImpl#getMarginCheckTime()}
   *   <li>{@link EventTimesImpl#getSettementTime()}
   * </ul>
   */
  @Test
  @DisplayName("Test EventTimesImpl getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventTimesImpl.<init>(LocalDateTime, LocalDateTime, Duration, LocalDateTime)",
    "Duration EventTimesImpl.getAccountAccessAllowedPeriod()",
    "LocalDateTime EventTimesImpl.getAccountAccessAllowedStart()",
    "LocalDateTime EventTimesImpl.getMarginCheckTime()",
    "LocalDateTime EventTimesImpl.getSettementTime()"
  })
  void testEventTimesImplGettersAndSetters() {
    // Arrange
    LocalDateTime settementTime = LocalDate.of(1970, 1, 1).atStartOfDay();
    LocalDateTime accountAccessAllowedStart = LocalDate.of(1970, 1, 1).atStartOfDay();
    Duration accountAccessAllowedPeriod = Duration.ofSeconds(1L);
    LocalDateTime marginCheckTime = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    EventTimesImpl actualEventTimesImpl =
        new EventTimesImpl(
            settementTime, accountAccessAllowedStart, accountAccessAllowedPeriod, marginCheckTime);
    Duration actualAccountAccessAllowedPeriod =
        actualEventTimesImpl.getAccountAccessAllowedPeriod();
    LocalDateTime actualAccountAccessAllowedStart =
        actualEventTimesImpl.getAccountAccessAllowedStart();
    LocalDateTime actualMarginCheckTime = actualEventTimesImpl.getMarginCheckTime();

    // Assert
    assertEquals(1000000000L, actualAccountAccessAllowedPeriod.toNanos());
    assertSame(accountAccessAllowedStart, actualAccountAccessAllowedStart);
    assertSame(marginCheckTime, actualMarginCheckTime);
    assertSame(settementTime, actualEventTimesImpl.getSettementTime());
    assertSame(accountAccessAllowedPeriod, actualAccountAccessAllowedPeriod);
  }

  /**
   * Test {@link SmartDerivativeContractScheduleGenerator#getScheduleForBusinessDays(String,
   * LocalDate, LocalDate, LocalTime, Duration)} with {@code calendar}, {@code startDate}, {@code
   * maturity}, {@code settlementTime}, {@code accountAccessAllowedDuration}.
   *
   * <p>Method under test: {@link
   * SmartDerivativeContractScheduleGenerator#getScheduleForBusinessDays(String, LocalDate,
   * LocalDate, LocalTime, Duration)}
   */
  @Test
  @DisplayName(
      "Test getScheduleForBusinessDays(String, LocalDate, LocalDate, LocalTime, Duration) with 'calendar', 'startDate', 'maturity', 'settlementTime', 'accountAccessAllowedDuration'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SmartDerivativeContractSchedule SmartDerivativeContractScheduleGenerator.getScheduleForBusinessDays(String, LocalDate, LocalDate, LocalTime, Duration)"
  })
  void
      testGetScheduleForBusinessDaysWithCalendarStartDateMaturitySettlementTimeAccountAccessAllowedDuration() {
    // Arrange
    LocalDate startDate = LocalDate.of(1970, 1, 1);

    // Act
    SmartDerivativeContractSchedule actualScheduleForBusinessDays =
        SmartDerivativeContractScheduleGenerator.getScheduleForBusinessDays(
            "\"TARGET2\"",
            startDate,
            LocalDate.of(1970, 1, 1),
            LocalTime.MIDNIGHT,
            Duration.ofSeconds(1L));
    List<EventTimes> actualEventTimes = actualScheduleForBusinessDays.getEventTimes();

    // Assert
    List<EventTimes> eventTimes = actualScheduleForBusinessDays.getEventTimes();
    assertEquals(1, eventTimes.size());
    EventTimes getResult = eventTimes.get(0);
    assertTrue(getResult instanceof EventTimesImpl);
    assertTrue(actualScheduleForBusinessDays instanceof SimpleSchedule);
    assertSame(eventTimes, actualEventTimes);
    assertSame(startDate, getResult.getAccountAccessAllowedStart().toLocalDate());
    assertSame(startDate, getResult.getMarginCheckTime().toLocalDate());
    assertSame(startDate, getResult.getSettementTime().toLocalDate());
  }

  /**
   * Test {@link SmartDerivativeContractScheduleGenerator#getScheduleForBusinessDays(String,
   * LocalDate, LocalDate, LocalTime, Duration)} with {@code calendar}, {@code startDate}, {@code
   * maturity}, {@code settlementTime}, {@code accountAccessAllowedDuration}.
   *
   * <p>Method under test: {@link
   * SmartDerivativeContractScheduleGenerator#getScheduleForBusinessDays(String, LocalDate,
   * LocalDate, LocalTime, Duration)}
   */
  @Test
  @DisplayName(
      "Test getScheduleForBusinessDays(String, LocalDate, LocalDate, LocalTime, Duration) with 'calendar', 'startDate', 'maturity', 'settlementTime', 'accountAccessAllowedDuration'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SmartDerivativeContractSchedule SmartDerivativeContractScheduleGenerator.getScheduleForBusinessDays(String, LocalDate, LocalDate, LocalTime, Duration)"
  })
  void
      testGetScheduleForBusinessDaysWithCalendarStartDateMaturitySettlementTimeAccountAccessAllowedDuration2() {
    // Arrange
    Duration accountAccessAllowedDuration = Duration.ofSeconds(1L);

    // Act
    SmartDerivativeContractSchedule actualScheduleForBusinessDays =
        SmartDerivativeContractScheduleGenerator.getScheduleForBusinessDays(
            "\"TARGET2\"",
            LocalDate.of(1970, 1, 1),
            LocalDate.ofEpochDay(1L),
            LocalTime.MIDNIGHT,
            accountAccessAllowedDuration);
    List<EventTimes> actualEventTimes = actualScheduleForBusinessDays.getEventTimes();

    // Assert
    List<EventTimes> eventTimes = actualScheduleForBusinessDays.getEventTimes();
    assertEquals(2, eventTimes.size());
    assertTrue(eventTimes.get(0) instanceof EventTimesImpl);
    EventTimes getResult = eventTimes.get(1);
    assertTrue(getResult instanceof EventTimesImpl);
    assertTrue(actualScheduleForBusinessDays instanceof SimpleSchedule);
    assertSame(eventTimes, actualEventTimes);
    assertSame(accountAccessAllowedDuration, getResult.getAccountAccessAllowedPeriod());
    assertSame(LocalTime.MIN, getResult.getSettementTime().toLocalTime());
  }

  /**
   * Test {@link SmartDerivativeContractScheduleGenerator#getScheduleForBusinessDays(String,
   * LocalDate, LocalDate, LocalTime, Duration)} with {@code calendar}, {@code startDate}, {@code
   * maturity}, {@code settlementTime}, {@code accountAccessAllowedDuration}.
   *
   * <p>Method under test: {@link
   * SmartDerivativeContractScheduleGenerator#getScheduleForBusinessDays(String, LocalDate,
   * LocalDate, LocalTime, Duration)}
   */
  @Test
  @DisplayName(
      "Test getScheduleForBusinessDays(String, LocalDate, LocalDate, LocalTime, Duration) with 'calendar', 'startDate', 'maturity', 'settlementTime', 'accountAccessAllowedDuration'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SmartDerivativeContractSchedule SmartDerivativeContractScheduleGenerator.getScheduleForBusinessDays(String, LocalDate, LocalDate, LocalTime, Duration)"
  })
  void
      testGetScheduleForBusinessDaysWithCalendarStartDateMaturitySettlementTimeAccountAccessAllowedDuration3() {
    // Arrange
    LocalDate startDate = LocalDate.ofEpochDay(-1L);

    // Act
    SmartDerivativeContractSchedule actualScheduleForBusinessDays =
        SmartDerivativeContractScheduleGenerator.getScheduleForBusinessDays(
            "\"TARGET2\"",
            startDate,
            LocalDate.of(1970, 1, 1),
            LocalTime.MIDNIGHT,
            Duration.ofSeconds(1L));
    List<EventTimes> actualEventTimes = actualScheduleForBusinessDays.getEventTimes();

    // Assert
    List<EventTimes> eventTimes = actualScheduleForBusinessDays.getEventTimes();
    assertEquals(1, eventTimes.size());
    EventTimes getResult = eventTimes.get(0);
    assertTrue(getResult instanceof EventTimesImpl);
    assertTrue(actualScheduleForBusinessDays instanceof SimpleSchedule);
    assertSame(eventTimes, actualEventTimes);
    assertSame(startDate, getResult.getAccountAccessAllowedStart().toLocalDate());
    assertSame(startDate, getResult.getMarginCheckTime().toLocalDate());
    assertSame(startDate, getResult.getSettementTime().toLocalDate());
  }

  /**
   * Test {@link SmartDerivativeContractScheduleGenerator#getScheduleForBusinessDays(String,
   * LocalDate, LocalDate, LocalTime, LocalTime, Duration, LocalTime)} with {@code calendar}, {@code
   * startDate}, {@code maturity}, {@code settlementTime}, {@code accountAccessAllowedStartTime},
   * {@code accountAccessAllowedDuration}, {@code marginCheckTime}.
   *
   * <p>Method under test: {@link
   * SmartDerivativeContractScheduleGenerator#getScheduleForBusinessDays(String, LocalDate,
   * LocalDate, LocalTime, LocalTime, Duration, LocalTime)}
   */
  @Test
  @DisplayName(
      "Test getScheduleForBusinessDays(String, LocalDate, LocalDate, LocalTime, LocalTime, Duration, LocalTime) with 'calendar', 'startDate', 'maturity', 'settlementTime', 'accountAccessAllowedStartTime', 'accountAccessAllowedDuration', 'marginCheckTime'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SmartDerivativeContractSchedule SmartDerivativeContractScheduleGenerator.getScheduleForBusinessDays(String, LocalDate, LocalDate, LocalTime, LocalTime, Duration, LocalTime)"
  })
  void
      testGetScheduleForBusinessDaysWithCalendarStartDateMaturitySettlementTimeAccountAccessAllowedStartTimeAccountAccessAllowedDurationMarginCheckTime() {
    // Arrange and Act
    SmartDerivativeContractSchedule actualScheduleForBusinessDays =
        SmartDerivativeContractScheduleGenerator.getScheduleForBusinessDays(
            "\"New York Stock Exchange\"",
            LocalDate.of(1970, 1, 1),
            LocalDate.of(1970, 1, 1),
            LocalTime.MIDNIGHT,
            LocalTime.MIDNIGHT,
            Duration.ofSeconds(1L),
            LocalTime.MIDNIGHT);
    List<EventTimes> actualEventTimes = actualScheduleForBusinessDays.getEventTimes();

    // Assert
    List<EventTimes> eventTimes = actualScheduleForBusinessDays.getEventTimes();
    assertEquals(1, eventTimes.size());
    assertTrue(eventTimes.get(0) instanceof EventTimesImpl);
    assertTrue(actualScheduleForBusinessDays instanceof SimpleSchedule);
    assertSame(eventTimes, actualEventTimes);
  }

  /**
   * Test {@link SmartDerivativeContractScheduleGenerator#getScheduleForBusinessDays(String,
   * LocalDate, LocalDate, LocalTime, LocalTime, Duration, LocalTime)} with {@code calendar}, {@code
   * startDate}, {@code maturity}, {@code settlementTime}, {@code accountAccessAllowedStartTime},
   * {@code accountAccessAllowedDuration}, {@code marginCheckTime}.
   *
   * <p>Method under test: {@link
   * SmartDerivativeContractScheduleGenerator#getScheduleForBusinessDays(String, LocalDate,
   * LocalDate, LocalTime, LocalTime, Duration, LocalTime)}
   */
  @Test
  @DisplayName(
      "Test getScheduleForBusinessDays(String, LocalDate, LocalDate, LocalTime, LocalTime, Duration, LocalTime) with 'calendar', 'startDate', 'maturity', 'settlementTime', 'accountAccessAllowedStartTime', 'accountAccessAllowedDuration', 'marginCheckTime'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SmartDerivativeContractSchedule SmartDerivativeContractScheduleGenerator.getScheduleForBusinessDays(String, LocalDate, LocalDate, LocalTime, LocalTime, Duration, LocalTime)"
  })
  void
      testGetScheduleForBusinessDaysWithCalendarStartDateMaturitySettlementTimeAccountAccessAllowedStartTimeAccountAccessAllowedDurationMarginCheckTime2() {
    // Arrange
    Duration accountAccessAllowedDuration = Duration.ofSeconds(1L);

    // Act
    SmartDerivativeContractSchedule actualScheduleForBusinessDays =
        SmartDerivativeContractScheduleGenerator.getScheduleForBusinessDays(
            "\"New York Stock Exchange\"",
            LocalDate.of(1970, 1, 1),
            LocalDate.ofEpochDay(1L),
            LocalTime.MIDNIGHT,
            LocalTime.MIDNIGHT,
            accountAccessAllowedDuration,
            LocalTime.MIDNIGHT);
    List<EventTimes> actualEventTimes = actualScheduleForBusinessDays.getEventTimes();

    // Assert
    List<EventTimes> eventTimes = actualScheduleForBusinessDays.getEventTimes();
    assertEquals(2, eventTimes.size());
    assertTrue(eventTimes.get(0) instanceof EventTimesImpl);
    EventTimes getResult = eventTimes.get(1);
    assertTrue(getResult instanceof EventTimesImpl);
    assertTrue(actualScheduleForBusinessDays instanceof SimpleSchedule);
    assertSame(eventTimes, actualEventTimes);
    assertSame(accountAccessAllowedDuration, getResult.getAccountAccessAllowedPeriod());
    LocalTime localTime = LocalTime.MIN;
    assertSame(localTime, getResult.getAccountAccessAllowedStart().toLocalTime());
    assertSame(localTime, getResult.getMarginCheckTime().toLocalTime());
    assertSame(localTime, getResult.getSettementTime().toLocalTime());
  }

  /**
   * Test SimpleSchedule getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link SimpleSchedule#SimpleSchedule(List)}
   *   <li>{@link SimpleSchedule#getEventTimes()}
   * </ul>
   */
  @Test
  @DisplayName("Test SimpleSchedule getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SimpleSchedule.<init>(List)", "List SimpleSchedule.getEventTimes()"})
  void testSimpleScheduleGettersAndSetters() {
    // Arrange
    ArrayList<EventTimes> eventTimes = new ArrayList<>();

    // Act
    List<EventTimes> actualEventTimes = new SimpleSchedule(eventTimes).getEventTimes();

    // Assert
    assertTrue(actualEventTimes.isEmpty());
    assertSame(eventTimes, actualEventTimes);
  }
}
