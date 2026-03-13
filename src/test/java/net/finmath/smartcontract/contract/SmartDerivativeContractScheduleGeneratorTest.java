package net.finmath.smartcontract.contract;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SmartDerivativeContractScheduleGeneratorTest {

	@Test
	void getScheduleForBusinessDays_shouldGenerateNonEmptySchedule() {
		LocalDate startDate = LocalDate.of(2024, 1, 2); // a Tuesday
		LocalDate maturity = LocalDate.of(2024, 1, 5);  // a Friday
		LocalTime settlementTime = LocalTime.of(17, 0);
		Duration accountAccessDuration = Duration.ofMinutes(30);

		SmartDerivativeContractSchedule schedule =
				SmartDerivativeContractScheduleGenerator.getScheduleForBusinessDays(
						"TARGET2", startDate, maturity, settlementTime, accountAccessDuration);

		List<SmartDerivativeContractSchedule.EventTimes> eventTimes = schedule.getEventTimes();
		assertFalse(eventTimes.isEmpty());
	}

	@Test
	void getScheduleForBusinessDays_shouldSetCorrectSettlementTime() {
		LocalDate startDate = LocalDate.of(2024, 1, 2);
		LocalDate maturity = LocalDate.of(2024, 1, 2);
		LocalTime settlementTime = LocalTime.of(17, 0);
		Duration accountAccessDuration = Duration.ofMinutes(30);

		SmartDerivativeContractSchedule schedule =
				SmartDerivativeContractScheduleGenerator.getScheduleForBusinessDays(
						"TARGET2", startDate, maturity, settlementTime, accountAccessDuration);

		SmartDerivativeContractSchedule.EventTimes eventTime = schedule.getEventTimes().get(0);
		assertEquals(LocalTime.of(17, 0), eventTime.getSettementTime().toLocalTime());
	}

	@Test
	void eventTimesImpl_shouldReturnCorrectValues() {
		SmartDerivativeContractScheduleGenerator.EventTimesImpl impl =
				new SmartDerivativeContractScheduleGenerator.EventTimesImpl(
						LocalDate.of(2024, 1, 2).atTime(17, 0),
						LocalDate.of(2024, 1, 2).atTime(17, 1),
						Duration.ofMinutes(30),
						LocalDate.of(2024, 1, 2).atTime(17, 32));

		assertEquals(LocalTime.of(17, 0), impl.getSettementTime().toLocalTime());
		assertEquals(LocalTime.of(17, 1), impl.getAccountAccessAllowedStart().toLocalTime());
		assertEquals(Duration.ofMinutes(30), impl.getAccountAccessAllowedPeriod());
		assertEquals(LocalTime.of(17, 32), impl.getMarginCheckTime().toLocalTime());
	}

	@Test
	void getScheduleForBusinessDays_shouldSkipWeekends() {
		// Friday to Monday span
		LocalDate friday = LocalDate.of(2024, 1, 5);
		LocalDate monday = LocalDate.of(2024, 1, 8);
		LocalTime settlementTime = LocalTime.of(17, 0);
		Duration accountAccessDuration = Duration.ofMinutes(30);

		SmartDerivativeContractSchedule schedule =
				SmartDerivativeContractScheduleGenerator.getScheduleForBusinessDays(
						"TARGET2", friday, monday, settlementTime, accountAccessDuration);

		List<SmartDerivativeContractSchedule.EventTimes> events = schedule.getEventTimes();
		// Should have Friday and Monday (skipping Saturday and Sunday)
		assertEquals(2, events.size());
		assertEquals(friday, events.get(0).getSettementTime().toLocalDate());
		assertEquals(monday, events.get(1).getSettementTime().toLocalDate());
	}
}
