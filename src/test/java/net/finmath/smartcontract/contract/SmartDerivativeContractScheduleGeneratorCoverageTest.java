package net.finmath.smartcontract.contract;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class SmartDerivativeContractScheduleGeneratorCoverageTest {

	@Test
	void testGetScheduleForBusinessDaysFiveParams() {
		LocalDate startDate = LocalDate.of(2024, 1, 2);
		LocalDate maturity = LocalDate.of(2024, 1, 5);
		LocalTime settlementTime = LocalTime.of(17, 0);
		Duration accountAccessAllowedDuration = Duration.ofMinutes(10);

		SmartDerivativeContractSchedule schedule = SmartDerivativeContractScheduleGenerator
				.getScheduleForBusinessDays("target2", startDate, maturity, settlementTime, accountAccessAllowedDuration);

		assertNotNull(schedule);
		assertFalse(schedule.getEventTimes().isEmpty());

		SmartDerivativeContractSchedule.EventTimes firstEvent = schedule.getEventTimes().get(0);
		assertEquals(startDate.atTime(settlementTime), firstEvent.getSettementTime());
		assertEquals(startDate.atTime(settlementTime.plusMinutes(1)), firstEvent.getAccountAccessAllowedStart());
		assertEquals(accountAccessAllowedDuration, firstEvent.getAccountAccessAllowedPeriod());
	}

	@Test
	void testGetScheduleForBusinessDaysSevenParams() {
		LocalDate startDate = LocalDate.of(2024, 3, 4);
		LocalDate maturity = LocalDate.of(2024, 3, 8);
		LocalTime settlementTime = LocalTime.of(16, 0);
		LocalTime accountAccessStartTime = LocalTime.of(16, 5);
		Duration accountAccessAllowedDuration = Duration.ofMinutes(15);
		LocalTime marginCheckTime = LocalTime.of(16, 25);

		SmartDerivativeContractSchedule schedule = SmartDerivativeContractScheduleGenerator
				.getScheduleForBusinessDays("target2", startDate, maturity, settlementTime,
						accountAccessStartTime, accountAccessAllowedDuration, marginCheckTime);

		assertNotNull(schedule);
		assertFalse(schedule.getEventTimes().isEmpty());

		SmartDerivativeContractSchedule.EventTimes firstEvent = schedule.getEventTimes().get(0);
		assertEquals(startDate.atTime(settlementTime), firstEvent.getSettementTime());
		assertEquals(startDate.atTime(accountAccessStartTime), firstEvent.getAccountAccessAllowedStart());
		assertEquals(accountAccessAllowedDuration, firstEvent.getAccountAccessAllowedPeriod());
		assertEquals(startDate.atTime(marginCheckTime), firstEvent.getMarginCheckTime());
	}

	@Test
	void testScheduleSkipsWeekends() {
		LocalDate friday = LocalDate.of(2024, 1, 5);
		LocalDate monday = LocalDate.of(2024, 1, 8);
		LocalTime settlementTime = LocalTime.of(10, 0);
		LocalTime accessStart = LocalTime.of(10, 5);
		Duration duration = Duration.ofMinutes(5);
		LocalTime marginCheck = LocalTime.of(10, 15);

		SmartDerivativeContractSchedule schedule = SmartDerivativeContractScheduleGenerator
				.getScheduleForBusinessDays("target2", friday, monday, settlementTime,
						accessStart, duration, marginCheck);

		assertNotNull(schedule);
		assertEquals(2, schedule.getEventTimes().size());
		assertEquals(friday.atTime(settlementTime), schedule.getEventTimes().get(0).getSettementTime());
		assertEquals(monday.atTime(settlementTime), schedule.getEventTimes().get(1).getSettementTime());
	}

	@Test
	void testScheduleWithSameStartAndMaturity() {
		LocalDate date = LocalDate.of(2024, 1, 2);
		LocalTime settlementTime = LocalTime.of(12, 0);
		LocalTime accessStart = LocalTime.of(12, 1);
		Duration duration = Duration.ofMinutes(10);
		LocalTime marginCheck = LocalTime.of(12, 15);

		SmartDerivativeContractSchedule schedule = SmartDerivativeContractScheduleGenerator
				.getScheduleForBusinessDays("target2", date, date, settlementTime,
						accessStart, duration, marginCheck);

		assertNotNull(schedule);
		assertEquals(1, schedule.getEventTimes().size());
	}
}
