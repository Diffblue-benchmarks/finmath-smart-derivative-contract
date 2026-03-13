package net.finmath.smartcontract.settlement;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ZonedDateTimeAdapterTest {

	private final ZonedDateTimeAdapter adapter = new ZonedDateTimeAdapter();

	@Test
	void marshal_shouldFormatCorrectly() {
		ZonedDateTime zdt = ZonedDateTime.of(2024, 6, 15, 14, 30, 45, 0, ZoneId.of("Europe/Berlin"));
		String result = adapter.marshal(zdt);
		assertEquals("20240615-143045", result);
	}

	@Test
	void unmarshal_shouldParseCorrectly() {
		ZonedDateTime result = adapter.unmarshal("20240615-143045");
		assertEquals(LocalDateTime.of(2024, 6, 15, 14, 30, 45), result.toLocalDateTime());
	}

	@Test
	void roundTrip_shouldPreserveLocalDateTime() {
		ZonedDateTime original = ZonedDateTime.of(2025, 3, 10, 9, 15, 0, 0, ZoneId.systemDefault());
		String marshalled = adapter.marshal(original);
		ZonedDateTime unmarshalled = adapter.unmarshal(marshalled);
		assertEquals(original.toLocalDateTime(), unmarshalled.toLocalDateTime());
	}
}
