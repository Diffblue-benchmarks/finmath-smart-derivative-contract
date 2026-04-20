package net.finmath.smartcontract.contract;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SmartDerivativeContractScheduleGeneratorSimpleScheduleTest {

	@Test
	void testSimpleScheduleConstructorAndGetEventTimes() {
		LocalDateTime settlement = LocalDateTime.of(2024, 1, 15, 17, 30);
		LocalDateTime accessStart = settlement.plusMinutes(1);
		Duration accessDuration = Duration.ofMinutes(10);
		LocalDateTime marginCheck = accessStart.plus(accessDuration).plusMinutes(1);

		SmartDerivativeContractSchedule.EventTimes event = new SmartDerivativeContractScheduleGenerator.EventTimesImpl(
				settlement, accessStart, accessDuration, marginCheck);

		List<SmartDerivativeContractSchedule.EventTimes> eventList = new ArrayList<>();
		eventList.add(event);

		SmartDerivativeContractScheduleGenerator.SimpleSchedule schedule =
				new SmartDerivativeContractScheduleGenerator.SimpleSchedule(eventList);

		List<SmartDerivativeContractSchedule.EventTimes> result = schedule.getEventTimes();
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(settlement, result.get(0).getSettementTime());
	}

	@Test
	void testSimpleScheduleWithEmptyList() {
		List<SmartDerivativeContractSchedule.EventTimes> emptyList = new ArrayList<>();

		SmartDerivativeContractScheduleGenerator.SimpleSchedule schedule =
				new SmartDerivativeContractScheduleGenerator.SimpleSchedule(emptyList);

		List<SmartDerivativeContractSchedule.EventTimes> result = schedule.getEventTimes();
		assertNotNull(result);
		assertEquals(0, result.size());
	}
}
