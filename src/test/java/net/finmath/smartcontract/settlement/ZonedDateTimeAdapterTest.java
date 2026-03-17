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
		final ZonedDateTimeAdapter adapter = new ZonedDateTimeAdapter();
		final ZonedDateTime zonedDateTime = ZonedDateTime.of(2024, 3, 15, 14, 30, 45, 0, ZoneId.systemDefault());

		final String result = adapter.marshal(zonedDateTime);

		assertNotNull(result);
		assertEquals("20240315-143045", result);
	}

	@Test
	void testUnmarshal() {
		final ZonedDateTimeAdapter adapter = new ZonedDateTimeAdapter();
		final String dateString = "20240315-143045";

		final ZonedDateTime result = adapter.unmarshal(dateString);

		assertNotNull(result);
		assertEquals(LocalDateTime.of(2024, 3, 15, 14, 30, 45), result.toLocalDateTime());
		assertEquals(ZoneId.systemDefault(), result.getZone());
	}

	@Test
	void testMarshalUnmarshalRoundTrip() {
		final ZonedDateTimeAdapter adapter = new ZonedDateTimeAdapter();
		final ZonedDateTime original = ZonedDateTime.of(2023, 12, 31, 23, 59, 59, 0, ZoneId.systemDefault());

		final String marshaled = adapter.marshal(original);
		final ZonedDateTime unmarshaled = adapter.unmarshal(marshaled);

		assertEquals(original.toLocalDateTime(), unmarshaled.toLocalDateTime());
	}
}
