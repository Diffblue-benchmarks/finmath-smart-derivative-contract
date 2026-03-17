package net.finmath.smartcontract.valuation.marketdata.data;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LocalDateTimeAdapterTest {

	@Test
	void testMarshal() {
		final LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();
		final LocalDateTime dateTime = LocalDateTime.of(2023, 5, 15, 14, 30, 45);

		final String result = adapter.marshal(dateTime);

		assertNotNull(result);
		assertEquals("20230515-143045", result);
	}

	@Test
	void testUnmarshal() throws ParseException {
		final LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();
		final String dateTimeString = "20230515-143045";

		final LocalDateTime result = adapter.unmarshal(dateTimeString);

		assertNotNull(result);
		assertEquals(2023, result.getYear());
		assertEquals(5, result.getMonthValue());
		assertEquals(15, result.getDayOfMonth());
		assertEquals(14, result.getHour());
		assertEquals(30, result.getMinute());
		assertEquals(45, result.getSecond());
	}

	@Test
	void testMarshalUnmarshalRoundTrip() throws ParseException {
		final LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();
		final LocalDateTime original = LocalDateTime.of(2024, 12, 25, 23, 59, 59);

		final String marshaled = adapter.marshal(original);
		final LocalDateTime unmarshaled = adapter.unmarshal(marshaled);

		assertEquals(original, unmarshaled);
	}

}
