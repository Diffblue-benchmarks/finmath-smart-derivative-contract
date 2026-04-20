package net.finmath.smartcontract.valuation.marketdata.data;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocalDateTimeAdapterTest {

	@Test
	void testMarshal() {
		LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();
		LocalDateTime dateTime = LocalDateTime.of(2024, 3, 15, 10, 30, 45);

		String result = adapter.marshal(dateTime);

		assertEquals("20240315-103045", result);
	}

	@Test
	void testUnmarshal() throws Exception {
		LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();

		LocalDateTime result = adapter.unmarshal("20240315-103045");

		assertEquals(LocalDateTime.of(2024, 3, 15, 10, 30, 45), result);
	}

	@Test
	void testRoundTrip() throws Exception {
		LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();
		LocalDateTime original = LocalDateTime.of(2023, 12, 1, 0, 0, 0);

		String marshalled = adapter.marshal(original);
		LocalDateTime unmarshalled = adapter.unmarshal(marshalled);

		assertEquals(original, unmarshalled);
	}
}
