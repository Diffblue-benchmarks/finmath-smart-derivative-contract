package net.finmath.smartcontract.settlement;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ZonedDateTimeAdapterTest {

	@Test
	void testMarshal() {
		ZonedDateTimeAdapter adapter = new ZonedDateTimeAdapter();
		ZonedDateTime dateTime = ZonedDateTime.of(2024, 3, 15, 10, 30, 45, 0, ZoneId.systemDefault());

		String result = adapter.marshal(dateTime);

		assertEquals("20240315-103045", result);
	}

	@Test
	void testUnmarshal() {
		ZonedDateTimeAdapter adapter = new ZonedDateTimeAdapter();
		String input = "20240315-103045";

		ZonedDateTime result = adapter.unmarshal(input);

		assertNotNull(result);
		assertEquals(2024, result.getYear());
		assertEquals(3, result.getMonthValue());
		assertEquals(15, result.getDayOfMonth());
		assertEquals(10, result.getHour());
		assertEquals(30, result.getMinute());
		assertEquals(45, result.getSecond());
		assertEquals(ZoneId.systemDefault(), result.getZone());
	}

	@Test
	void testRoundTrip() {
		ZonedDateTimeAdapter adapter = new ZonedDateTimeAdapter();
		ZonedDateTime original = ZonedDateTime.of(2025, 12, 31, 23, 59, 59, 0, ZoneId.systemDefault());

		String marshalled = adapter.marshal(original);
		ZonedDateTime unmarshalled = adapter.unmarshal(marshalled);

		assertEquals(original.toLocalDateTime(), unmarshalled.toLocalDateTime());
	}
}
