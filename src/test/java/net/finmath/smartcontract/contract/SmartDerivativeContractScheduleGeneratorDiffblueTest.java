package net.finmath.smartcontract.contract;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class SmartDerivativeContractScheduleGeneratorDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link SmartDerivativeContractScheduleGenerator.EventTimesImpl#EventTimesImpl(LocalDateTime, LocalDateTime, Duration, LocalDateTime)}
   *   <li>
   * {@link SmartDerivativeContractScheduleGenerator.EventTimesImpl#getAccountAccessAllowedPeriod()}
   *   <li>
   * {@link SmartDerivativeContractScheduleGenerator.EventTimesImpl#getAccountAccessAllowedStart()}
   *   <li>
   * {@link SmartDerivativeContractScheduleGenerator.EventTimesImpl#getMarginCheckTime()}
   *   <li>
   * {@link SmartDerivativeContractScheduleGenerator.EventTimesImpl#getSettementTime()}
   * </ul>
   */
  @Test
  void testEventTimesImplGettersAndSetters() {
    // Arrange
    LocalDateTime settementTime = LocalDate.of(1970, 1, 1).atStartOfDay();
    LocalDateTime accountAccessAllowedStart = LocalDate.of(1970, 1, 1).atStartOfDay();
    LocalDateTime marginCheckTime = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    SmartDerivativeContractScheduleGenerator.EventTimesImpl actualEventTimesImpl = new SmartDerivativeContractScheduleGenerator.EventTimesImpl(
        settementTime, accountAccessAllowedStart, null, marginCheckTime);
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
   * Method under test:
   * {@link SmartDerivativeContractScheduleGenerator#getScheduleForBusinessDays(String, LocalDate, LocalDate, LocalTime, LocalTime, Duration, LocalTime)}
   */
  @Test
  void testGetScheduleForBusinessDays() {
    // Arrange
    LocalDate startDate = LocalDate.of(1970, 1, 1);
    LocalTime marginCheckTime = LocalTime.MIDNIGHT;

    // Act
    SmartDerivativeContractSchedule actualScheduleForBusinessDays = SmartDerivativeContractScheduleGenerator
        .getScheduleForBusinessDays("Calendar", startDate, LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT,
            LocalTime.MIDNIGHT, null, marginCheckTime);

    // Assert
    List<SmartDerivativeContractSchedule.EventTimes> eventTimes = actualScheduleForBusinessDays.getEventTimes();
    assertEquals(1, eventTimes.size());
    SmartDerivativeContractSchedule.EventTimes getResult = eventTimes.get(0);
    assertTrue(getResult instanceof SmartDerivativeContractScheduleGenerator.EventTimesImpl);
    assertTrue(actualScheduleForBusinessDays instanceof SmartDerivativeContractScheduleGenerator.SimpleSchedule);
    LocalDateTime accountAccessAllowedStart = getResult.getAccountAccessAllowedStart();
    LocalTime toLocalTimeResult = accountAccessAllowedStart.toLocalTime();
    assertEquals("00:00", toLocalTimeResult.toString());
    LocalDate toLocalDateResult = accountAccessAllowedStart.toLocalDate();
    assertEquals("1970-01-01", toLocalDateResult.toString());
    assertNull(getResult.getAccountAccessAllowedPeriod());
    assertSame(startDate, toLocalDateResult);
    LocalDateTime marginCheckTime2 = getResult.getMarginCheckTime();
    assertSame(startDate, marginCheckTime2.toLocalDate());
    LocalDateTime settementTime = getResult.getSettementTime();
    assertSame(startDate, settementTime.toLocalDate());
    LocalTime localTime = marginCheckTime.MIN;
    assertSame(localTime, toLocalTimeResult);
    assertSame(localTime, marginCheckTime2.toLocalTime());
    assertSame(localTime, settementTime.toLocalTime());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link SmartDerivativeContractScheduleGenerator.SimpleSchedule#SimpleSchedule(List)}
   *   <li>
   * {@link SmartDerivativeContractScheduleGenerator.SimpleSchedule#getEventTimes()}
   * </ul>
   */
  @Test
  void testSimpleScheduleGettersAndSetters() {
    // Arrange
    ArrayList<SmartDerivativeContractSchedule.EventTimes> eventTimes = new ArrayList<>();

    // Act
    List<SmartDerivativeContractSchedule.EventTimes> actualEventTimes = (new SmartDerivativeContractScheduleGenerator.SimpleSchedule(
        eventTimes)).getEventTimes();

    // Assert
    assertTrue(actualEventTimes.isEmpty());
    assertSame(eventTimes, actualEventTimes);
  }
}
