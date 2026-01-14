/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 13 Jan 2026
 */

package net.finmath.smartcontract.contract;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SmartDerivativeContractScheduleGenerator.
 * Tests all methods in the schedule generator with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class SmartDerivativeContractScheduleGeneratorClaudeTest {

	/**
	 * Test the 5-parameter getScheduleForBusinessDays method with a simple one-day schedule.
	 */
	@Test
	void testGetScheduleForBusinessDays_SingleDay() {
		// Arrange
		final String calendar = "TARGET";
		final LocalDate startDate = LocalDate.of(2024, 1, 2); // Tuesday
		final LocalDate maturity = LocalDate.of(2024, 1, 2); // Same day
		final LocalTime settlementTime = LocalTime.of(10, 0);
		final Duration accountAccessDuration = Duration.ofHours(2);

		// Act
		final SmartDerivativeContractSchedule schedule = SmartDerivativeContractScheduleGenerator
				.getScheduleForBusinessDays(calendar, startDate, maturity, settlementTime, accountAccessDuration);

		// Assert
		assertNotNull(schedule);
		final List<SmartDerivativeContractSchedule.EventTimes> eventTimes = schedule.getEventTimes();
		assertNotNull(eventTimes);
		assertEquals(1, eventTimes.size());

		final SmartDerivativeContractSchedule.EventTimes event = eventTimes.get(0);
		assertEquals(LocalDateTime.of(2024, 1, 2, 10, 0), event.getSettementTime());
		assertEquals(LocalDateTime.of(2024, 1, 2, 10, 1), event.getAccountAccessAllowedStart());
		assertEquals(accountAccessDuration, event.getAccountAccessAllowedPeriod());
		assertEquals(LocalDateTime.of(2024, 1, 2, 12, 2), event.getMarginCheckTime());
	}

	/**
	 * Test the 5-parameter getScheduleForBusinessDays method with multiple business days.
	 */
	@Test
	void testGetScheduleForBusinessDays_MultipleDays() {
		// Arrange
		final String calendar = "TARGET";
		final LocalDate startDate = LocalDate.of(2024, 1, 2); // Tuesday
		final LocalDate maturity = LocalDate.of(2024, 1, 4); // Thursday
		final LocalTime settlementTime = LocalTime.of(14, 30);
		final Duration accountAccessDuration = Duration.ofMinutes(90);

		// Act
		final SmartDerivativeContractSchedule schedule = SmartDerivativeContractScheduleGenerator
				.getScheduleForBusinessDays(calendar, startDate, maturity, settlementTime, accountAccessDuration);

		// Assert
		assertNotNull(schedule);
		final List<SmartDerivativeContractSchedule.EventTimes> eventTimes = schedule.getEventTimes();
		assertNotNull(eventTimes);
		assertEquals(3, eventTimes.size());

		// Verify first event
		final SmartDerivativeContractSchedule.EventTimes firstEvent = eventTimes.get(0);
		assertEquals(LocalDateTime.of(2024, 1, 2, 14, 30), firstEvent.getSettementTime());
		assertEquals(LocalDateTime.of(2024, 1, 2, 14, 31), firstEvent.getAccountAccessAllowedStart());
		assertEquals(accountAccessDuration, firstEvent.getAccountAccessAllowedPeriod());
		assertEquals(LocalDateTime.of(2024, 1, 2, 16, 2), firstEvent.getMarginCheckTime());

		// Verify last event
		final SmartDerivativeContractSchedule.EventTimes lastEvent = eventTimes.get(2);
		assertEquals(LocalDateTime.of(2024, 1, 4, 14, 30), lastEvent.getSettementTime());
	}

	/**
	 * Test the 5-parameter getScheduleForBusinessDays method with weekend handling.
	 * Start date is Friday, maturity is Monday - should skip weekend.
	 */
	@Test
	void testGetScheduleForBusinessDays_SkipsWeekend() {
		// Arrange
		final String calendar = "TARGET";
		final LocalDate startDate = LocalDate.of(2024, 1, 5); // Friday
		final LocalDate maturity = LocalDate.of(2024, 1, 8); // Monday
		final LocalTime settlementTime = LocalTime.of(9, 0);
		final Duration accountAccessDuration = Duration.ofHours(1);

		// Act
		final SmartDerivativeContractSchedule schedule = SmartDerivativeContractScheduleGenerator
				.getScheduleForBusinessDays(calendar, startDate, maturity, settlementTime, accountAccessDuration);

		// Assert
		assertNotNull(schedule);
		final List<SmartDerivativeContractSchedule.EventTimes> eventTimes = schedule.getEventTimes();
		assertNotNull(eventTimes);
		assertEquals(2, eventTimes.size()); // Friday and Monday, no weekend

		// Verify dates are Friday and Monday
		assertEquals(LocalDate.of(2024, 1, 5), eventTimes.get(0).getSettementTime().toLocalDate());
		assertEquals(LocalDate.of(2024, 1, 8), eventTimes.get(1).getSettementTime().toLocalDate());
	}

	/**
	 * Test the 5-parameter getScheduleForBusinessDays method with very short duration.
	 */
	@Test
	void testGetScheduleForBusinessDays_ShortDuration() {
		// Arrange
		final String calendar = "TARGET";
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final LocalDate maturity = LocalDate.of(2024, 1, 2);
		final LocalTime settlementTime = LocalTime.of(12, 0);
		final Duration accountAccessDuration = Duration.ofMinutes(1);

		// Act
		final SmartDerivativeContractSchedule schedule = SmartDerivativeContractScheduleGenerator
				.getScheduleForBusinessDays(calendar, startDate, maturity, settlementTime, accountAccessDuration);

		// Assert
		assertNotNull(schedule);
		final List<SmartDerivativeContractSchedule.EventTimes> eventTimes = schedule.getEventTimes();
		assertEquals(1, eventTimes.size());

		final SmartDerivativeContractSchedule.EventTimes event = eventTimes.get(0);
		assertEquals(LocalDateTime.of(2024, 1, 2, 12, 0), event.getSettementTime());
		assertEquals(LocalDateTime.of(2024, 1, 2, 12, 1), event.getAccountAccessAllowedStart());
		assertEquals(Duration.ofMinutes(1), event.getAccountAccessAllowedPeriod());
		assertEquals(LocalDateTime.of(2024, 1, 2, 12, 3), event.getMarginCheckTime());
	}

	/**
	 * Test the 5-parameter getScheduleForBusinessDays method with zero duration.
	 */
	@Test
	void testGetScheduleForBusinessDays_ZeroDuration() {
		// Arrange
		final String calendar = "TARGET";
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final LocalDate maturity = LocalDate.of(2024, 1, 2);
		final LocalTime settlementTime = LocalTime.of(12, 0);
		final Duration accountAccessDuration = Duration.ZERO;

		// Act
		final SmartDerivativeContractSchedule schedule = SmartDerivativeContractScheduleGenerator
				.getScheduleForBusinessDays(calendar, startDate, maturity, settlementTime, accountAccessDuration);

		// Assert
		assertNotNull(schedule);
		final List<SmartDerivativeContractSchedule.EventTimes> eventTimes = schedule.getEventTimes();
		assertEquals(1, eventTimes.size());

		final SmartDerivativeContractSchedule.EventTimes event = eventTimes.get(0);
		assertEquals(LocalDateTime.of(2024, 1, 2, 12, 0), event.getSettementTime());
		assertEquals(LocalDateTime.of(2024, 1, 2, 12, 1), event.getAccountAccessAllowedStart());
		assertEquals(Duration.ZERO, event.getAccountAccessAllowedPeriod());
		assertEquals(LocalDateTime.of(2024, 1, 2, 12, 2), event.getMarginCheckTime());
	}

	/**
	 * Test the 5-parameter getScheduleForBusinessDays method with late settlement time.
	 * Note: LocalTime arithmetic wraps within the same day, so times past midnight wrap to early morning of the same calendar date.
	 */
	@Test
	void testGetScheduleForBusinessDays_LateSettlementTime() {
		// Arrange
		final String calendar = "TARGET";
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final LocalDate maturity = LocalDate.of(2024, 1, 2);
		final LocalTime settlementTime = LocalTime.of(23, 58);
		final Duration accountAccessDuration = Duration.ofMinutes(30);

		// Act
		final SmartDerivativeContractSchedule schedule = SmartDerivativeContractScheduleGenerator
				.getScheduleForBusinessDays(calendar, startDate, maturity, settlementTime, accountAccessDuration);

		// Assert
		assertNotNull(schedule);
		final List<SmartDerivativeContractSchedule.EventTimes> eventTimes = schedule.getEventTimes();
		assertEquals(1, eventTimes.size());

		final SmartDerivativeContractSchedule.EventTimes event = eventTimes.get(0);
		assertEquals(LocalDateTime.of(2024, 1, 2, 23, 58), event.getSettementTime());
		assertEquals(LocalDateTime.of(2024, 1, 2, 23, 59), event.getAccountAccessAllowedStart());
		// LocalTime wraps around within the same day, so 23:59 + 30 minutes + 1 minute = 00:30 on the same date
		assertEquals(LocalDateTime.of(2024, 1, 2, 0, 30), event.getMarginCheckTime());
	}

	/**
	 * Test the 7-parameter getScheduleForBusinessDays method with custom times.
	 */
	@Test
	void testGetScheduleForBusinessDays_CustomTimes_SingleDay() {
		// Arrange
		final String calendar = "TARGET";
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final LocalDate maturity = LocalDate.of(2024, 1, 2);
		final LocalTime settlementTime = LocalTime.of(10, 0);
		final LocalTime accountAccessStartTime = LocalTime.of(10, 30);
		final Duration accountAccessDuration = Duration.ofHours(2);
		final LocalTime marginCheckTime = LocalTime.of(13, 0);

		// Act
		final SmartDerivativeContractSchedule schedule = SmartDerivativeContractScheduleGenerator
				.getScheduleForBusinessDays(calendar, startDate, maturity, settlementTime,
						accountAccessStartTime, accountAccessDuration, marginCheckTime);

		// Assert
		assertNotNull(schedule);
		final List<SmartDerivativeContractSchedule.EventTimes> eventTimes = schedule.getEventTimes();
		assertNotNull(eventTimes);
		assertEquals(1, eventTimes.size());

		final SmartDerivativeContractSchedule.EventTimes event = eventTimes.get(0);
		assertEquals(LocalDateTime.of(2024, 1, 2, 10, 0), event.getSettementTime());
		assertEquals(LocalDateTime.of(2024, 1, 2, 10, 30), event.getAccountAccessAllowedStart());
		assertEquals(accountAccessDuration, event.getAccountAccessAllowedPeriod());
		assertEquals(LocalDateTime.of(2024, 1, 2, 13, 0), event.getMarginCheckTime());
	}

	/**
	 * Test the 7-parameter getScheduleForBusinessDays method with multiple days.
	 */
	@Test
	void testGetScheduleForBusinessDays_CustomTimes_MultipleDays() {
		// Arrange
		final String calendar = "TARGET";
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final LocalDate maturity = LocalDate.of(2024, 1, 4);
		final LocalTime settlementTime = LocalTime.of(9, 0);
		final LocalTime accountAccessStartTime = LocalTime.of(9, 15);
		final Duration accountAccessDuration = Duration.ofMinutes(45);
		final LocalTime marginCheckTime = LocalTime.of(10, 30);

		// Act
		final SmartDerivativeContractSchedule schedule = SmartDerivativeContractScheduleGenerator
				.getScheduleForBusinessDays(calendar, startDate, maturity, settlementTime,
						accountAccessStartTime, accountAccessDuration, marginCheckTime);

		// Assert
		assertNotNull(schedule);
		final List<SmartDerivativeContractSchedule.EventTimes> eventTimes = schedule.getEventTimes();
		assertEquals(3, eventTimes.size());

		// Verify each event has consistent times
		for (int i = 0; i < eventTimes.size(); i++) {
			final SmartDerivativeContractSchedule.EventTimes event = eventTimes.get(i);
			assertEquals(settlementTime, event.getSettementTime().toLocalTime());
			assertEquals(accountAccessStartTime, event.getAccountAccessAllowedStart().toLocalTime());
			assertEquals(accountAccessDuration, event.getAccountAccessAllowedPeriod());
			assertEquals(marginCheckTime, event.getMarginCheckTime().toLocalTime());
		}
	}

	/**
	 * Test the 7-parameter getScheduleForBusinessDays method where account access starts before settlement.
	 */
	@Test
	void testGetScheduleForBusinessDays_AccountAccessBeforeSettlement() {
		// Arrange
		final String calendar = "TARGET";
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final LocalDate maturity = LocalDate.of(2024, 1, 2);
		final LocalTime settlementTime = LocalTime.of(10, 0);
		final LocalTime accountAccessStartTime = LocalTime.of(9, 0); // Before settlement
		final Duration accountAccessDuration = Duration.ofHours(1);
		final LocalTime marginCheckTime = LocalTime.of(11, 0);

		// Act
		final SmartDerivativeContractSchedule schedule = SmartDerivativeContractScheduleGenerator
				.getScheduleForBusinessDays(calendar, startDate, maturity, settlementTime,
						accountAccessStartTime, accountAccessDuration, marginCheckTime);

		// Assert
		assertNotNull(schedule);
		final List<SmartDerivativeContractSchedule.EventTimes> eventTimes = schedule.getEventTimes();
		assertEquals(1, eventTimes.size());

		final SmartDerivativeContractSchedule.EventTimes event = eventTimes.get(0);
		assertEquals(LocalDateTime.of(2024, 1, 2, 10, 0), event.getSettementTime());
		assertEquals(LocalDateTime.of(2024, 1, 2, 9, 0), event.getAccountAccessAllowedStart());
		assertEquals(LocalDateTime.of(2024, 1, 2, 11, 0), event.getMarginCheckTime());
	}

	/**
	 * Test the 7-parameter getScheduleForBusinessDays method where margin check is at midnight.
	 */
	@Test
	void testGetScheduleForBusinessDays_MarginCheckAtMidnight() {
		// Arrange
		final String calendar = "TARGET";
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final LocalDate maturity = LocalDate.of(2024, 1, 2);
		final LocalTime settlementTime = LocalTime.of(10, 0);
		final LocalTime accountAccessStartTime = LocalTime.of(10, 30);
		final Duration accountAccessDuration = Duration.ofHours(2);
		final LocalTime marginCheckTime = LocalTime.MIDNIGHT;

		// Act
		final SmartDerivativeContractSchedule schedule = SmartDerivativeContractScheduleGenerator
				.getScheduleForBusinessDays(calendar, startDate, maturity, settlementTime,
						accountAccessStartTime, accountAccessDuration, marginCheckTime);

		// Assert
		assertNotNull(schedule);
		final List<SmartDerivativeContractSchedule.EventTimes> eventTimes = schedule.getEventTimes();
		assertEquals(1, eventTimes.size());

		final SmartDerivativeContractSchedule.EventTimes event = eventTimes.get(0);
		assertEquals(LocalDateTime.of(2024, 1, 2, 10, 0), event.getSettementTime());
		assertEquals(LocalDateTime.of(2024, 1, 2, 0, 0), event.getMarginCheckTime());
	}

	/**
	 * Test the 7-parameter getScheduleForBusinessDays method with same times for all events.
	 */
	@Test
	void testGetScheduleForBusinessDays_AllTimesSame() {
		// Arrange
		final String calendar = "TARGET";
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final LocalDate maturity = LocalDate.of(2024, 1, 2);
		final LocalTime time = LocalTime.of(12, 0);
		final Duration accountAccessDuration = Duration.ZERO;

		// Act
		final SmartDerivativeContractSchedule schedule = SmartDerivativeContractScheduleGenerator
				.getScheduleForBusinessDays(calendar, startDate, maturity, time, time, accountAccessDuration, time);

		// Assert
		assertNotNull(schedule);
		final List<SmartDerivativeContractSchedule.EventTimes> eventTimes = schedule.getEventTimes();
		assertEquals(1, eventTimes.size());

		final SmartDerivativeContractSchedule.EventTimes event = eventTimes.get(0);
		assertEquals(LocalDateTime.of(2024, 1, 2, 12, 0), event.getSettementTime());
		assertEquals(LocalDateTime.of(2024, 1, 2, 12, 0), event.getAccountAccessAllowedStart());
		assertEquals(Duration.ZERO, event.getAccountAccessAllowedPeriod());
		assertEquals(LocalDateTime.of(2024, 1, 2, 12, 0), event.getMarginCheckTime());
	}

	/**
	 * Test the 7-parameter getScheduleForBusinessDays with spanning a week.
	 */
	@Test
	void testGetScheduleForBusinessDays_WeekSpan() {
		// Arrange
		final String calendar = "TARGET";
		final LocalDate startDate = LocalDate.of(2024, 1, 2); // Tuesday
		final LocalDate maturity = LocalDate.of(2024, 1, 9); // Next Tuesday
		final LocalTime settlementTime = LocalTime.of(15, 0);
		final LocalTime accountAccessStartTime = LocalTime.of(15, 5);
		final Duration accountAccessDuration = Duration.ofMinutes(30);
		final LocalTime marginCheckTime = LocalTime.of(16, 0);

		// Act
		final SmartDerivativeContractSchedule schedule = SmartDerivativeContractScheduleGenerator
				.getScheduleForBusinessDays(calendar, startDate, maturity, settlementTime,
						accountAccessStartTime, accountAccessDuration, marginCheckTime);

		// Assert
		assertNotNull(schedule);
		final List<SmartDerivativeContractSchedule.EventTimes> eventTimes = schedule.getEventTimes();
		// Should have 6 days: Tue, Wed, Thu, Fri, Mon, Tue (skips weekend)
		assertEquals(6, eventTimes.size());

		// Verify first and last
		assertEquals(LocalDate.of(2024, 1, 2), eventTimes.get(0).getSettementTime().toLocalDate());
		assertEquals(LocalDate.of(2024, 1, 9), eventTimes.get(5).getSettementTime().toLocalDate());

		// Verify no weekend dates
		for (SmartDerivativeContractSchedule.EventTimes event : eventTimes) {
			final LocalDate date = event.getSettementTime().toLocalDate();
			assertTrue(date.getDayOfWeek().getValue() <= 5, "Should only contain weekdays");
		}
	}

	/**
	 * Test the 7-parameter getScheduleForBusinessDays with long duration spanning multiple hours.
	 */
	@Test
	void testGetScheduleForBusinessDays_LongDuration() {
		// Arrange
		final String calendar = "TARGET";
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final LocalDate maturity = LocalDate.of(2024, 1, 2);
		final LocalTime settlementTime = LocalTime.of(8, 0);
		final LocalTime accountAccessStartTime = LocalTime.of(8, 30);
		final Duration accountAccessDuration = Duration.ofHours(8);
		final LocalTime marginCheckTime = LocalTime.of(17, 0);

		// Act
		final SmartDerivativeContractSchedule schedule = SmartDerivativeContractScheduleGenerator
				.getScheduleForBusinessDays(calendar, startDate, maturity, settlementTime,
						accountAccessStartTime, accountAccessDuration, marginCheckTime);

		// Assert
		assertNotNull(schedule);
		final List<SmartDerivativeContractSchedule.EventTimes> eventTimes = schedule.getEventTimes();
		assertEquals(1, eventTimes.size());

		final SmartDerivativeContractSchedule.EventTimes event = eventTimes.get(0);
		assertEquals(Duration.ofHours(8), event.getAccountAccessAllowedPeriod());
	}

	/**
	 * Test EventTimesImpl getters directly.
	 */
	@Test
	void testEventTimesImpl_Getters() {
		// Arrange
		final LocalDateTime settlement = LocalDateTime.of(2024, 1, 2, 10, 0);
		final LocalDateTime accountAccessStart = LocalDateTime.of(2024, 1, 2, 10, 30);
		final Duration duration = Duration.ofHours(2);
		final LocalDateTime marginCheck = LocalDateTime.of(2024, 1, 2, 13, 0);

		// Act
		final SmartDerivativeContractScheduleGenerator.EventTimesImpl eventTimes =
				new SmartDerivativeContractScheduleGenerator.EventTimesImpl(
						settlement, accountAccessStart, duration, marginCheck);

		// Assert
		assertEquals(settlement, eventTimes.getSettementTime());
		assertEquals(accountAccessStart, eventTimes.getAccountAccessAllowedStart());
		assertEquals(duration, eventTimes.getAccountAccessAllowedPeriod());
		assertEquals(marginCheck, eventTimes.getMarginCheckTime());
	}

	/**
	 * Test EventTimesImpl with null values - verifies constructor accepts nulls.
	 */
	@Test
	void testEventTimesImpl_WithNullValues() {
		// Act
		final SmartDerivativeContractScheduleGenerator.EventTimesImpl eventTimes =
				new SmartDerivativeContractScheduleGenerator.EventTimesImpl(null, null, null, null);

		// Assert
		assertNull(eventTimes.getSettementTime());
		assertNull(eventTimes.getAccountAccessAllowedStart());
		assertNull(eventTimes.getAccountAccessAllowedPeriod());
		assertNull(eventTimes.getMarginCheckTime());
	}

	/**
	 * Test EventTimesImpl with zero duration.
	 */
	@Test
	void testEventTimesImpl_WithZeroDuration() {
		// Arrange
		final LocalDateTime settlement = LocalDateTime.of(2024, 6, 15, 9, 0);
		final LocalDateTime accountAccessStart = LocalDateTime.of(2024, 6, 15, 9, 0);
		final Duration duration = Duration.ZERO;
		final LocalDateTime marginCheck = LocalDateTime.of(2024, 6, 15, 9, 0);

		// Act
		final SmartDerivativeContractScheduleGenerator.EventTimesImpl eventTimes =
				new SmartDerivativeContractScheduleGenerator.EventTimesImpl(
						settlement, accountAccessStart, duration, marginCheck);

		// Assert
		assertEquals(settlement, eventTimes.getSettementTime());
		assertEquals(accountAccessStart, eventTimes.getAccountAccessAllowedStart());
		assertEquals(Duration.ZERO, eventTimes.getAccountAccessAllowedPeriod());
		assertEquals(marginCheck, eventTimes.getMarginCheckTime());
	}

	/**
	 * Test EventTimesImpl with negative duration (edge case).
	 */
	@Test
	void testEventTimesImpl_WithNegativeDuration() {
		// Arrange
		final LocalDateTime settlement = LocalDateTime.of(2024, 3, 10, 14, 30);
		final LocalDateTime accountAccessStart = LocalDateTime.of(2024, 3, 10, 14, 0);
		final Duration duration = Duration.ofMinutes(-30);
		final LocalDateTime marginCheck = LocalDateTime.of(2024, 3, 10, 13, 30);

		// Act
		final SmartDerivativeContractScheduleGenerator.EventTimesImpl eventTimes =
				new SmartDerivativeContractScheduleGenerator.EventTimesImpl(
						settlement, accountAccessStart, duration, marginCheck);

		// Assert
		assertEquals(settlement, eventTimes.getSettementTime());
		assertEquals(accountAccessStart, eventTimes.getAccountAccessAllowedStart());
		assertEquals(Duration.ofMinutes(-30), eventTimes.getAccountAccessAllowedPeriod());
		assertEquals(marginCheck, eventTimes.getMarginCheckTime());
	}

	/**
	 * Test EventTimesImpl with very long duration (24+ hours).
	 */
	@Test
	void testEventTimesImpl_WithLongDuration() {
		// Arrange
		final LocalDateTime settlement = LocalDateTime.of(2024, 7, 20, 8, 0);
		final LocalDateTime accountAccessStart = LocalDateTime.of(2024, 7, 20, 8, 30);
		final Duration duration = Duration.ofDays(2).plusHours(5);
		final LocalDateTime marginCheck = LocalDateTime.of(2024, 7, 22, 14, 0);

		// Act
		final SmartDerivativeContractScheduleGenerator.EventTimesImpl eventTimes =
				new SmartDerivativeContractScheduleGenerator.EventTimesImpl(
						settlement, accountAccessStart, duration, marginCheck);

		// Assert
		assertEquals(settlement, eventTimes.getSettementTime());
		assertEquals(accountAccessStart, eventTimes.getAccountAccessAllowedStart());
		assertEquals(Duration.ofDays(2).plusHours(5), eventTimes.getAccountAccessAllowedPeriod());
		assertEquals(marginCheck, eventTimes.getMarginCheckTime());
	}

	/**
	 * Test EventTimesImpl with same time for all events.
	 */
	@Test
	void testEventTimesImpl_AllTimesSame() {
		// Arrange
		final LocalDateTime time = LocalDateTime.of(2024, 12, 1, 12, 0);
		final Duration duration = Duration.ofMinutes(30);

		// Act
		final SmartDerivativeContractScheduleGenerator.EventTimesImpl eventTimes =
				new SmartDerivativeContractScheduleGenerator.EventTimesImpl(time, time, duration, time);

		// Assert
		assertEquals(time, eventTimes.getSettementTime());
		assertEquals(time, eventTimes.getAccountAccessAllowedStart());
		assertEquals(duration, eventTimes.getAccountAccessAllowedPeriod());
		assertEquals(time, eventTimes.getMarginCheckTime());
	}

	/**
	 * Test EventTimesImpl with times spanning across midnight.
	 */
	@Test
	void testEventTimesImpl_TimesCrossingMidnight() {
		// Arrange
		final LocalDateTime settlement = LocalDateTime.of(2024, 5, 5, 23, 30);
		final LocalDateTime accountAccessStart = LocalDateTime.of(2024, 5, 5, 23, 45);
		final Duration duration = Duration.ofMinutes(60);
		final LocalDateTime marginCheck = LocalDateTime.of(2024, 5, 6, 1, 0);

		// Act
		final SmartDerivativeContractScheduleGenerator.EventTimesImpl eventTimes =
				new SmartDerivativeContractScheduleGenerator.EventTimesImpl(
						settlement, accountAccessStart, duration, marginCheck);

		// Assert
		assertEquals(settlement, eventTimes.getSettementTime());
		assertEquals(accountAccessStart, eventTimes.getAccountAccessAllowedStart());
		assertEquals(Duration.ofMinutes(60), eventTimes.getAccountAccessAllowedPeriod());
		assertEquals(marginCheck, eventTimes.getMarginCheckTime());
	}

	/**
	 * Test EventTimesImpl with times in different order (margin before settlement).
	 */
	@Test
	void testEventTimesImpl_TimesNotInChronologicalOrder() {
		// Arrange - margin check before settlement (unusual but should be allowed)
		final LocalDateTime settlement = LocalDateTime.of(2024, 8, 15, 10, 0);
		final LocalDateTime accountAccessStart = LocalDateTime.of(2024, 8, 15, 9, 0);
		final Duration duration = Duration.ofMinutes(30);
		final LocalDateTime marginCheck = LocalDateTime.of(2024, 8, 15, 8, 0);

		// Act
		final SmartDerivativeContractScheduleGenerator.EventTimesImpl eventTimes =
				new SmartDerivativeContractScheduleGenerator.EventTimesImpl(
						settlement, accountAccessStart, duration, marginCheck);

		// Assert
		assertEquals(settlement, eventTimes.getSettementTime());
		assertEquals(accountAccessStart, eventTimes.getAccountAccessAllowedStart());
		assertEquals(duration, eventTimes.getAccountAccessAllowedPeriod());
		assertEquals(marginCheck, eventTimes.getMarginCheckTime());
	}

	/**
	 * Test SimpleSchedule implementation with a single event.
	 */
	@Test
	void testSimpleSchedule_GetEventTimes() {
		// Arrange
		final LocalDateTime settlement = LocalDateTime.of(2024, 1, 2, 10, 0);
		final LocalDateTime accountAccessStart = LocalDateTime.of(2024, 1, 2, 10, 30);
		final Duration duration = Duration.ofHours(2);
		final LocalDateTime marginCheck = LocalDateTime.of(2024, 1, 2, 13, 0);

		final SmartDerivativeContractSchedule.EventTimes event =
				new SmartDerivativeContractScheduleGenerator.EventTimesImpl(
						settlement, accountAccessStart, duration, marginCheck);

		final List<SmartDerivativeContractSchedule.EventTimes> eventList = List.of(event);

		// Act
		final SmartDerivativeContractScheduleGenerator.SimpleSchedule schedule =
				new SmartDerivativeContractScheduleGenerator.SimpleSchedule(eventList);

		// Assert
		assertNotNull(schedule.getEventTimes());
		assertEquals(1, schedule.getEventTimes().size());
		assertEquals(event, schedule.getEventTimes().get(0));
	}

	/**
	 * Test SimpleSchedule with empty list.
	 */
	@Test
	void testSimpleSchedule_EmptyList() {
		// Arrange
		final List<SmartDerivativeContractSchedule.EventTimes> emptyList = List.of();

		// Act
		final SmartDerivativeContractScheduleGenerator.SimpleSchedule schedule =
				new SmartDerivativeContractScheduleGenerator.SimpleSchedule(emptyList);

		// Assert
		assertNotNull(schedule.getEventTimes());
		assertEquals(0, schedule.getEventTimes().size());
		assertTrue(schedule.getEventTimes().isEmpty());
	}

	/**
	 * Test SimpleSchedule with multiple events.
	 */
	@Test
	void testSimpleSchedule_MultipleEvents() {
		// Arrange
		final LocalDateTime settlement1 = LocalDateTime.of(2024, 1, 2, 10, 0);
		final LocalDateTime accountAccessStart1 = LocalDateTime.of(2024, 1, 2, 10, 30);
		final Duration duration1 = Duration.ofHours(2);
		final LocalDateTime marginCheck1 = LocalDateTime.of(2024, 1, 2, 13, 0);

		final LocalDateTime settlement2 = LocalDateTime.of(2024, 1, 3, 10, 0);
		final LocalDateTime accountAccessStart2 = LocalDateTime.of(2024, 1, 3, 10, 30);
		final Duration duration2 = Duration.ofHours(1);
		final LocalDateTime marginCheck2 = LocalDateTime.of(2024, 1, 3, 12, 0);

		final LocalDateTime settlement3 = LocalDateTime.of(2024, 1, 4, 10, 0);
		final LocalDateTime accountAccessStart3 = LocalDateTime.of(2024, 1, 4, 10, 30);
		final Duration duration3 = Duration.ofMinutes(90);
		final LocalDateTime marginCheck3 = LocalDateTime.of(2024, 1, 4, 12, 30);

		final SmartDerivativeContractSchedule.EventTimes event1 =
				new SmartDerivativeContractScheduleGenerator.EventTimesImpl(
						settlement1, accountAccessStart1, duration1, marginCheck1);
		final SmartDerivativeContractSchedule.EventTimes event2 =
				new SmartDerivativeContractScheduleGenerator.EventTimesImpl(
						settlement2, accountAccessStart2, duration2, marginCheck2);
		final SmartDerivativeContractSchedule.EventTimes event3 =
				new SmartDerivativeContractScheduleGenerator.EventTimesImpl(
						settlement3, accountAccessStart3, duration3, marginCheck3);

		final List<SmartDerivativeContractSchedule.EventTimes> eventList = List.of(event1, event2, event3);

		// Act
		final SmartDerivativeContractScheduleGenerator.SimpleSchedule schedule =
				new SmartDerivativeContractScheduleGenerator.SimpleSchedule(eventList);

		// Assert
		assertNotNull(schedule.getEventTimes());
		assertEquals(3, schedule.getEventTimes().size());
		assertEquals(event1, schedule.getEventTimes().get(0));
		assertEquals(event2, schedule.getEventTimes().get(1));
		assertEquals(event3, schedule.getEventTimes().get(2));
	}

	/**
	 * Test SimpleSchedule verifies the returned list is the same reference.
	 */
	@Test
	void testSimpleSchedule_ReturnsSameListReference() {
		// Arrange
		final LocalDateTime settlement = LocalDateTime.of(2024, 1, 2, 10, 0);
		final LocalDateTime accountAccessStart = LocalDateTime.of(2024, 1, 2, 10, 30);
		final Duration duration = Duration.ofHours(2);
		final LocalDateTime marginCheck = LocalDateTime.of(2024, 1, 2, 13, 0);

		final SmartDerivativeContractSchedule.EventTimes event =
				new SmartDerivativeContractScheduleGenerator.EventTimesImpl(
						settlement, accountAccessStart, duration, marginCheck);

		final List<SmartDerivativeContractSchedule.EventTimes> eventList = List.of(event);

		// Act
		final SmartDerivativeContractScheduleGenerator.SimpleSchedule schedule =
				new SmartDerivativeContractScheduleGenerator.SimpleSchedule(eventList);

		// Assert
		assertSame(eventList, schedule.getEventTimes(), "getEventTimes should return the same list reference");
	}

	/**
	 * Test SimpleSchedule with null list (edge case to verify behavior).
	 */
	@Test
	void testSimpleSchedule_NullList() {
		// Act
		final SmartDerivativeContractScheduleGenerator.SimpleSchedule schedule =
				new SmartDerivativeContractScheduleGenerator.SimpleSchedule(null);

		// Assert
		assertNull(schedule.getEventTimes(), "getEventTimes should return null when initialized with null");
	}

	/**
	 * Test with early morning times (boundary test).
	 */
	@Test
	void testGetScheduleForBusinessDays_EarlyMorning() {
		// Arrange
		final String calendar = "TARGET";
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final LocalDate maturity = LocalDate.of(2024, 1, 2);
		final LocalTime settlementTime = LocalTime.of(0, 1);
		final LocalTime accountAccessStartTime = LocalTime.of(0, 2);
		final Duration accountAccessDuration = Duration.ofMinutes(5);
		final LocalTime marginCheckTime = LocalTime.of(0, 10);

		// Act
		final SmartDerivativeContractSchedule schedule = SmartDerivativeContractScheduleGenerator
				.getScheduleForBusinessDays(calendar, startDate, maturity, settlementTime,
						accountAccessStartTime, accountAccessDuration, marginCheckTime);

		// Assert
		assertNotNull(schedule);
		final List<SmartDerivativeContractSchedule.EventTimes> eventTimes = schedule.getEventTimes();
		assertEquals(1, eventTimes.size());

		final SmartDerivativeContractSchedule.EventTimes event = eventTimes.get(0);
		assertEquals(LocalDateTime.of(2024, 1, 2, 0, 1), event.getSettementTime());
		assertEquals(LocalDateTime.of(2024, 1, 2, 0, 2), event.getAccountAccessAllowedStart());
		assertEquals(LocalDateTime.of(2024, 1, 2, 0, 10), event.getMarginCheckTime());
	}
}
