/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.marketdata.data;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for LocalDateTimeAdapter.
 * Tests all methods with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class LocalDateTimeAdapterClaudeTest {

	// Constructor tests

	@Test
	void testConstructor() {
		LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();

		assertNotNull(adapter);
	}

	// marshal tests

	@Test
	void testMarshal_WithValidDateTime() {
		LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();
		LocalDateTime dateTime = LocalDateTime.of(2024, 1, 15, 10, 30, 45);

		String result = adapter.marshal(dateTime);

		assertEquals("20240115-103045", result);
	}

	@Test
	void testMarshal_WithDifferentDateTime() {
		LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();
		LocalDateTime dateTime = LocalDateTime.of(2023, 12, 31, 23, 59, 59);

		String result = adapter.marshal(dateTime);

		assertEquals("20231231-235959", result);
	}

	@Test
	void testMarshal_WithMidnight() {
		LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();
		LocalDateTime dateTime = LocalDateTime.of(2024, 6, 15, 0, 0, 0);

		String result = adapter.marshal(dateTime);

		assertEquals("20240615-000000", result);
	}

	@Test
	void testMarshal_WithNoon() {
		LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();
		LocalDateTime dateTime = LocalDateTime.of(2024, 3, 10, 12, 0, 0);

		String result = adapter.marshal(dateTime);

		assertEquals("20240310-120000", result);
	}

	@Test
	void testMarshal_WithLeapYearDate() {
		LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();
		LocalDateTime dateTime = LocalDateTime.of(2024, 2, 29, 14, 30, 0);

		String result = adapter.marshal(dateTime);

		assertEquals("20240229-143000", result);
	}

	@Test
	void testMarshal_WithSingleDigitMonth() {
		LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();
		LocalDateTime dateTime = LocalDateTime.of(2024, 1, 5, 9, 8, 7);

		String result = adapter.marshal(dateTime);

		assertEquals("20240105-090807", result);
	}

	@Test
	void testMarshal_WithOldDate() {
		LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();
		LocalDateTime dateTime = LocalDateTime.of(2000, 1, 1, 0, 0, 0);

		String result = adapter.marshal(dateTime);

		assertEquals("20000101-000000", result);
	}

	@Test
	void testMarshal_WithFutureDate() {
		LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();
		LocalDateTime dateTime = LocalDateTime.of(2099, 12, 31, 23, 59, 59);

		String result = adapter.marshal(dateTime);

		assertEquals("20991231-235959", result);
	}

	// unmarshal tests

	@Test
	void testUnmarshal_WithValidString() throws ParseException {
		LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();
		String dateString = "20240115-103045";

		LocalDateTime result = adapter.unmarshal(dateString);

		assertNotNull(result);
		assertEquals(2024, result.getYear());
		assertEquals(1, result.getMonthValue());
		assertEquals(15, result.getDayOfMonth());
		assertEquals(10, result.getHour());
		assertEquals(30, result.getMinute());
		assertEquals(45, result.getSecond());
	}

	@Test
	void testUnmarshal_WithDifferentString() throws ParseException {
		LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();
		String dateString = "20231231-235959";

		LocalDateTime result = adapter.unmarshal(dateString);

		assertEquals(LocalDateTime.of(2023, 12, 31, 23, 59, 59), result);
	}

	@Test
	void testUnmarshal_WithMidnight() throws ParseException {
		LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();
		String dateString = "20240615-000000";

		LocalDateTime result = adapter.unmarshal(dateString);

		assertEquals(LocalDateTime.of(2024, 6, 15, 0, 0, 0), result);
	}

	@Test
	void testUnmarshal_WithNoon() throws ParseException {
		LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();
		String dateString = "20240310-120000";

		LocalDateTime result = adapter.unmarshal(dateString);

		assertEquals(LocalDateTime.of(2024, 3, 10, 12, 0, 0), result);
	}

	@Test
	void testUnmarshal_WithLeapYearDate() throws ParseException {
		LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();
		String dateString = "20240229-143000";

		LocalDateTime result = adapter.unmarshal(dateString);

		assertEquals(LocalDateTime.of(2024, 2, 29, 14, 30, 0), result);
	}

	@Test
	void testUnmarshal_WithInvalidFormat_ThrowsException() {
		LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();
		String invalidString = "2024-01-15 10:30:45"; // Wrong format

		assertThrows(DateTimeParseException.class, () -> {
			adapter.unmarshal(invalidString);
		});
	}

	@Test
	void testUnmarshal_WithPartialString_ThrowsException() {
		LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();
		String partialString = "20240115"; // Missing time part

		assertThrows(DateTimeParseException.class, () -> {
			adapter.unmarshal(partialString);
		});
	}

	@Test
	void testUnmarshal_WithEmptyString_ThrowsException() {
		LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();
		String emptyString = "";

		assertThrows(DateTimeParseException.class, () -> {
			adapter.unmarshal(emptyString);
		});
	}

	@Test
	void testUnmarshal_WithInvalidDate_ThrowsException() {
		LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();
		String invalidDate = "202402ab-103045"; // Invalid characters in date

		assertThrows(DateTimeParseException.class, () -> {
			adapter.unmarshal(invalidDate);
		});
	}

	@Test
	void testUnmarshal_WithInvalidTime_ThrowsException() {
		LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();
		String invalidTime = "20240115-256030"; // Hour 25 is invalid

		assertThrows(DateTimeParseException.class, () -> {
			adapter.unmarshal(invalidTime);
		});
	}

	@Test
	void testUnmarshal_WithExtraCharacters_ThrowsException() {
		LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();
		String extraChars = "20240115-103045-extra";

		assertThrows(DateTimeParseException.class, () -> {
			adapter.unmarshal(extraChars);
		});
	}

	// Round-trip tests (marshal then unmarshal)

	@Test
	void testRoundTrip_MarshalThenUnmarshal() throws ParseException {
		LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();
		LocalDateTime original = LocalDateTime.of(2024, 5, 20, 14, 35, 22);

		String marshaled = adapter.marshal(original);
		LocalDateTime unmarshaled = adapter.unmarshal(marshaled);

		assertEquals(original, unmarshaled);
	}

	@Test
	void testRoundTrip_WithMidnight() throws ParseException {
		LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();
		LocalDateTime original = LocalDateTime.of(2024, 1, 1, 0, 0, 0);

		String marshaled = adapter.marshal(original);
		LocalDateTime unmarshaled = adapter.unmarshal(marshaled);

		assertEquals(original, unmarshaled);
	}

	@Test
	void testRoundTrip_WithEndOfDay() throws ParseException {
		LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();
		LocalDateTime original = LocalDateTime.of(2024, 12, 31, 23, 59, 59);

		String marshaled = adapter.marshal(original);
		LocalDateTime unmarshaled = adapter.unmarshal(marshaled);

		assertEquals(original, unmarshaled);
	}

	@Test
	void testRoundTrip_WithLeapYear() throws ParseException {
		LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();
		LocalDateTime original = LocalDateTime.of(2024, 2, 29, 12, 30, 45);

		String marshaled = adapter.marshal(original);
		LocalDateTime unmarshaled = adapter.unmarshal(marshaled);

		assertEquals(original, unmarshaled);
	}

	@Test
	void testRoundTrip_UnmarshalThenMarshal() throws ParseException {
		LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();
		String original = "20240520-143522";

		LocalDateTime unmarshaled = adapter.unmarshal(original);
		String marshaled = adapter.marshal(unmarshaled);

		assertEquals(original, marshaled);
	}
}
