/*
 * (c) Copyright Christian P. Fries, Germany. All rights reserved. Contact: email@christianfries.com.
 *
 * Created on 16 Oct 2018
 */

package net.finmath.smartcontract.contract;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Christian Fries
 */
class SmartDerivativeContractScheduleGeneratorTest {

	@Test
	void testEventTimesImpl() {
		final LocalDateTime settlementTime = LocalDateTime.of(2024, 1, 15, 17, 30);
		final LocalDateTime accountAccessStart = LocalDateTime.of(2024, 1, 15, 17, 31);
		final Duration accountAccessDuration = Duration.ofMinutes(10);
		final LocalDateTime marginCheck = LocalDateTime.of(2024, 1, 15, 17, 42);

		final SmartDerivativeContractScheduleGenerator.EventTimesImpl eventTimes =
			new SmartDerivativeContractScheduleGenerator.EventTimesImpl(
				settlementTime, accountAccessStart, accountAccessDuration, marginCheck);

		Assertions.assertEquals(settlementTime, eventTimes.getSettementTime());
		Assertions.assertEquals(accountAccessStart, eventTimes.getAccountAccessAllowedStart());
		Assertions.assertEquals(accountAccessDuration, eventTimes.getAccountAccessAllowedPeriod());
		Assertions.assertEquals(marginCheck, eventTimes.getMarginCheckTime());
	}

	@Test
	void test() {
		final LocalDate startDate = LocalDate.of(2018, 9, 15);
		final LocalDate maturity = LocalDate.of(2028, 9, 15);
		final LocalTime settlementTime = LocalTime.of(17, 30);
		final Duration accountAccessAllowedDuration = Duration.ofSeconds(10 * 60);

		final SmartDerivativeContractSchedule schedule = SmartDerivativeContractScheduleGenerator.getScheduleForBusinessDays("target2", startDate, maturity, settlementTime, accountAccessAllowedDuration);

		for (final SmartDerivativeContractSchedule.EventTimes event : schedule.getEventTimes()) {
			final LocalDateTime settementTime = event.getSettementTime();
			final LocalDateTime accountAccessAllowedStart = event.getAccountAccessAllowedStart();
			final LocalDateTime accountAccessAllowedEnd = event.getAccountAccessAllowedStart().plusSeconds(event.getAccountAccessAllowedPeriod().getSeconds());
			final LocalDateTime marginCheckTime = event.getMarginCheckTime();

			System.out.println("Settlement............:" + settementTime);
			System.out.println("Account access start..:" + accountAccessAllowedStart);
			System.out.println("Account access end....:" + accountAccessAllowedEnd);
			System.out.println("Margin check..........:" + marginCheckTime);
			System.out.println();

			Assertions.assertTrue(accountAccessAllowedStart.isAfter(settementTime), "Access after settlement");
			Assertions.assertTrue(accountAccessAllowedEnd.isAfter(accountAccessAllowedStart), "Account access");
			Assertions.assertTrue(marginCheckTime.isAfter(accountAccessAllowedEnd), "Margin check after account access");
		}
	}

	@Test
	void testSimpleSchedule() {
		// Given
		final LocalDateTime settlementTime = LocalDateTime.of(2024, 1, 15, 17, 30);
		final LocalDateTime accountAccessStart = LocalDateTime.of(2024, 1, 15, 17, 31);
		final Duration accountAccessPeriod = Duration.ofMinutes(10);
		final LocalDateTime marginCheckTime = LocalDateTime.of(2024, 1, 15, 17, 42);

		final SmartDerivativeContractScheduleGenerator.EventTimesImpl eventTimes1 = new SmartDerivativeContractScheduleGenerator.EventTimesImpl(
				settlementTime, accountAccessStart, accountAccessPeriod, marginCheckTime);

		final SmartDerivativeContractScheduleGenerator.EventTimesImpl eventTimes2 = new SmartDerivativeContractScheduleGenerator.EventTimesImpl(
				settlementTime.plusDays(1), accountAccessStart.plusDays(1), accountAccessPeriod, marginCheckTime.plusDays(1));

		final List<SmartDerivativeContractSchedule.EventTimes> eventTimesList = new ArrayList<>();
		eventTimesList.add(eventTimes1);
		eventTimesList.add(eventTimes2);

		// When
		final SmartDerivativeContractScheduleGenerator.SimpleSchedule schedule = new SmartDerivativeContractScheduleGenerator.SimpleSchedule(eventTimesList);

		// Then
		final List<SmartDerivativeContractSchedule.EventTimes> result = schedule.getEventTimes();
		Assertions.assertNotNull(result);
		Assertions.assertEquals(2, result.size());
		Assertions.assertEquals(eventTimes1, result.get(0));
		Assertions.assertEquals(eventTimes2, result.get(1));
	}

}
