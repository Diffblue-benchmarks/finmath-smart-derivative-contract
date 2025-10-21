package net.finmath.smartcontract.contract;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link EventTimesImpl#EventTimesImpl(LocalDateTime, LocalDateTime, Duration, LocalDateTime)}
   *   <li>{@link EventTimesImpl#getAccountAccessAllowedPeriod()}
   *   <li>{@link EventTimesImpl#getAccountAccessAllowedStart()}
   *   <li>{@link EventTimesImpl#getMarginCheckTime()}
   *   <li>{@link EventTimesImpl#getSettementTime()}
   * </ul>
   */
  @Test
  @DisplayName("Test EventTimesImpl getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void EventTimesImpl.<init>(LocalDateTime, LocalDateTime, Duration, LocalDateTime)",
      "Duration EventTimesImpl.getAccountAccessAllowedPeriod()",
      "LocalDateTime EventTimesImpl.getAccountAccessAllowedStart()",
      "LocalDateTime EventTimesImpl.getMarginCheckTime()", "LocalDateTime EventTimesImpl.getSettementTime()"})
  void testEventTimesImplGettersAndSetters() {
    // Arrange
    LocalDateTime settementTime = LocalDate.of(1970, 1, 1).atStartOfDay();
    LocalDateTime accountAccessAllowedStart = LocalDate.of(1970, 1, 1).atStartOfDay();
    LocalDateTime marginCheckTime = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    EventTimesImpl actualEventTimesImpl = new EventTimesImpl(settementTime, accountAccessAllowedStart, null,
        marginCheckTime);
    Duration actualAccountAccessAllowedPeriod = actualEventTimesImpl.getAccountAccessAllowedPeriod();
    LocalDateTime actualAccountAccessAllowedStart = actualEventTimesImpl.getAccountAccessAllowedStart();
    LocalDateTime actualMarginCheckTime = actualEventTimesImpl.getMarginCheckTime();

    // Assert
    assertNull(actualAccountAccessAllowedPeriod);
    assertSame(accountAccessAllowedStart, actualAccountAccessAllowedStart);
    assertSame(marginCheckTime, actualMarginCheckTime);
    assertSame(settementTime, actualEventTimesImpl.getSettementTime());
  }

  /**
   * Test {@link SmartDerivativeContractScheduleGenerator#getScheduleForBusinessDays(String, LocalDate, LocalDate, LocalTime, LocalTime, Duration, LocalTime)} with {@code calendar}, {@code startDate}, {@code maturity}, {@code settlementTime}, {@code accountAccessAllowedStartTime}, {@code accountAccessAllowedDuration}, {@code marginCheckTime}.
   * <p>
   * Method under test: {@link SmartDerivativeContractScheduleGenerator#getScheduleForBusinessDays(String, LocalDate, LocalDate, LocalTime, LocalTime, Duration, LocalTime)}
   */
  @Test
  @DisplayName("Test getScheduleForBusinessDays(String, LocalDate, LocalDate, LocalTime, LocalTime, Duration, LocalTime) with 'calendar', 'startDate', 'maturity', 'settlementTime', 'accountAccessAllowedStartTime', 'accountAccessAllowedDuration', 'marginCheckTime'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "SmartDerivativeContractSchedule SmartDerivativeContractScheduleGenerator.getScheduleForBusinessDays(String, LocalDate, LocalDate, LocalTime, LocalTime, Duration, LocalTime)"})
  void testGetScheduleForBusinessDaysWithCalendarStartDateMaturitySettlementTimeAccountAccessAllowedStartTimeAccountAccessAllowedDurationMarginCheckTime() {
    // Arrange
    LocalDate startDate = LocalDate.of(1970, 1, 1);
    LocalTime marginCheckTime = LocalTime.MIDNIGHT;

    // Act
    SmartDerivativeContractSchedule actualScheduleForBusinessDays = SmartDerivativeContractScheduleGenerator
        .getScheduleForBusinessDays("Calendar", startDate, LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT,
            LocalTime.MIDNIGHT, null, marginCheckTime);

    // Assert
    List<EventTimes> eventTimes = actualScheduleForBusinessDays.getEventTimes();
    assertEquals(1, eventTimes.size());
    EventTimes getResult = eventTimes.get(0);
    assertTrue(getResult instanceof EventTimesImpl);
    assertTrue(actualScheduleForBusinessDays instanceof SimpleSchedule);
    assertNull(getResult.getAccountAccessAllowedPeriod());
    LocalDateTime accountAccessAllowedStart = getResult.getAccountAccessAllowedStart();
    assertSame(startDate, accountAccessAllowedStart.toLocalDate());
    LocalDateTime marginCheckTime2 = getResult.getMarginCheckTime();
    assertSame(startDate, marginCheckTime2.toLocalDate());
    LocalDateTime settementTime = getResult.getSettementTime();
    assertSame(startDate, settementTime.toLocalDate());
    LocalTime localTime = marginCheckTime.MIN;
    assertSame(localTime, accountAccessAllowedStart.toLocalTime());
    assertSame(localTime, marginCheckTime2.toLocalTime());
    assertSame(localTime, settementTime.toLocalTime());
  }

  /**
   * Test SimpleSchedule getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SimpleSchedule#SimpleSchedule(List)}
   *   <li>{@link SimpleSchedule#getEventTimes()}
   * </ul>
   */
  @Test
  @DisplayName("Test SimpleSchedule getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SimpleSchedule.<init>(List)", "List SimpleSchedule.getEventTimes()"})
  void testSimpleScheduleGettersAndSetters() {
    // Arrange
    ArrayList<EventTimes> eventTimes = new ArrayList<>();

    // Act
    List<EventTimes> actualEventTimes = (new SimpleSchedule(eventTimes)).getEventTimes();

    // Assert
    assertTrue(actualEventTimes.isEmpty());
    assertSame(eventTimes, actualEventTimes);
  }
}
