package net.finmath.smartcontract.valuation.marketdata.data;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LocalDateTimeAdapterTest {

	private final LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();

	@Test
	void marshal_shouldFormatCorrectly() {
		LocalDateTime ldt = LocalDateTime.of(2024, 3, 15, 9, 45, 30);
		String result = adapter.marshal(ldt);
		assertEquals("20240315-094530", result);
	}

	@Test
	void unmarshal_shouldParseCorrectly() throws Exception {
		LocalDateTime result = adapter.unmarshal("20240315-094530");
		assertEquals(LocalDateTime.of(2024, 3, 15, 9, 45, 30), result);
	}

	@Test
	void roundTrip_shouldPreserveValue() throws Exception {
		LocalDateTime original = LocalDateTime.of(2025, 12, 31, 23, 59, 59);
		String marshalled = adapter.marshal(original);
		LocalDateTime unmarshalled = adapter.unmarshal(marshalled);
		assertEquals(original, unmarshalled);
	}
}
