package net.finmath.smartcontract.contract;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SmartDerivativeContractScheduleGeneratorEventTimesImplTest {

	@Test
	void testEventTimesImplConstructorAndGetters() {
		LocalDateTime settlementTime = LocalDateTime.of(2023, 6, 15, 17, 30);
		LocalDateTime accountAccessStart = LocalDateTime.of(2023, 6, 15, 17, 31);
		Duration accountAccessPeriod = Duration.ofMinutes(10);
		LocalDateTime marginCheckTime = LocalDateTime.of(2023, 6, 15, 17, 42);

		SmartDerivativeContractScheduleGenerator.EventTimesImpl eventTimes =
				new SmartDerivativeContractScheduleGenerator.EventTimesImpl(
						settlementTime, accountAccessStart, accountAccessPeriod, marginCheckTime);

		assertEquals(settlementTime, eventTimes.getSettementTime(), "Settlement time");
		assertEquals(accountAccessStart, eventTimes.getAccountAccessAllowedStart(), "Account access start");
		assertEquals(accountAccessPeriod, eventTimes.getAccountAccessAllowedPeriod(), "Account access period");
		assertEquals(marginCheckTime, eventTimes.getMarginCheckTime(), "Margin check time");
	}
}
