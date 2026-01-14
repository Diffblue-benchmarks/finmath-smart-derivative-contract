/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.settlement;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ZonedDateTimeAdapter.
 * Tests all methods with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class ZonedDateTimeAdapterClaudeTest {

	/**
	 * Test the default constructor creates a non-null instance.
	 */
	@Test
	void testDefaultConstructor() {
		ZonedDateTimeAdapter adapter = new ZonedDateTimeAdapter();
		assertNotNull(adapter, "Default constructor should create a non-null ZonedDateTimeAdapter instance");
	}

	/**
	 * Test marshal method with a standard ZonedDateTime.
	 */
	@Test
	void testMarshalWithStandardDateTime() {
		ZonedDateTimeAdapter adapter = new ZonedDateTimeAdapter();

		// Create a ZonedDateTime: 2024-03-15 14:30:45
		ZonedDateTime dateTime = ZonedDateTime.of(2024, 3, 15, 14, 30, 45, 0, ZoneId.systemDefault());

		String result = adapter.marshal(dateTime);

		assertNotNull(result, "Marshal should return a non-null string");
		assertEquals("20240315-143045", result, "Marshal should format date as yyyyMMdd-HHmmss");
	}

	/**
	 * Test marshal method with midnight time.
	 */
	@Test
	void testMarshalWithMidnight() {
		ZonedDateTimeAdapter adapter = new ZonedDateTimeAdapter();

		// Create a ZonedDateTime at midnight: 2024-12-31 00:00:00
		ZonedDateTime dateTime = ZonedDateTime.of(2024, 12, 31, 0, 0, 0, 0, ZoneId.systemDefault());

		String result = adapter.marshal(dateTime);

		assertEquals("20241231-000000", result, "Marshal should format midnight correctly with zeros");
	}

	/**
	 * Test marshal method with end of day time.
	 */
	@Test
	void testMarshalWithEndOfDay() {
		ZonedDateTimeAdapter adapter = new ZonedDateTimeAdapter();

		// Create a ZonedDateTime at 23:59:59
		ZonedDateTime dateTime = ZonedDateTime.of(2024, 6, 30, 23, 59, 59, 0, ZoneId.systemDefault());

		String result = adapter.marshal(dateTime);

		assertEquals("20240630-235959", result, "Marshal should format end of day time correctly");
	}

	/**
	 * Test marshal method with single digit month and day.
	 */
	@Test
	void testMarshalWithSingleDigitMonthDay() {
		ZonedDateTimeAdapter adapter = new ZonedDateTimeAdapter();

		// Create a ZonedDateTime: 2024-01-05 08:07:06
		ZonedDateTime dateTime = ZonedDateTime.of(2024, 1, 5, 8, 7, 6, 0, ZoneId.systemDefault());

		String result = adapter.marshal(dateTime);

		assertEquals("20240105-080706", result, "Marshal should pad single digit values with zeros");
	}

	/**
	 * Test marshal method with different time zones.
	 */
	@Test
	void testMarshalWithDifferentTimeZones() {
		ZonedDateTimeAdapter adapter = new ZonedDateTimeAdapter();

		// Create ZonedDateTime with UTC timezone
		ZonedDateTime dateTimeUTC = ZonedDateTime.of(2024, 7, 20, 15, 30, 45, 0, ZoneId.of("UTC"));
		String resultUTC = adapter.marshal(dateTimeUTC);

		// Create ZonedDateTime with Tokyo timezone (same local time)
		ZonedDateTime dateTimeTokyo = ZonedDateTime.of(2024, 7, 20, 15, 30, 45, 0, ZoneId.of("Asia/Tokyo"));
		String resultTokyo = adapter.marshal(dateTimeTokyo);

		// Both should format the same since we're using the local date-time component
		assertEquals("20240720-153045", resultUTC, "Marshal should format UTC time correctly");
		assertEquals("20240720-153045", resultTokyo, "Marshal should format Tokyo time correctly");
	}

	/**
	 * Test marshal method with year at century boundary.
	 */
	@Test
	void testMarshalWithCenturyBoundary() {
		ZonedDateTimeAdapter adapter = new ZonedDateTimeAdapter();

		// Test year 2000
		ZonedDateTime dateTime2000 = ZonedDateTime.of(2000, 1, 1, 12, 0, 0, 0, ZoneId.systemDefault());
		assertEquals("20000101-120000", adapter.marshal(dateTime2000), "Marshal should handle year 2000");

		// Test year 1999
		ZonedDateTime dateTime1999 = ZonedDateTime.of(1999, 12, 31, 23, 59, 59, 0, ZoneId.systemDefault());
		assertEquals("19991231-235959", adapter.marshal(dateTime1999), "Marshal should handle year 1999");
	}

	/**
	 * Test unmarshal method with a standard formatted string.
	 */
	@Test
	void testUnmarshalWithStandardString() {
		ZonedDateTimeAdapter adapter = new ZonedDateTimeAdapter();

		String dateString = "20240315-143045";

		ZonedDateTime result = adapter.unmarshal(dateString);

		assertNotNull(result, "Unmarshal should return a non-null ZonedDateTime");
		assertEquals(2024, result.getYear(), "Year should be 2024");
		assertEquals(3, result.getMonthValue(), "Month should be 3");
		assertEquals(15, result.getDayOfMonth(), "Day should be 15");
		assertEquals(14, result.getHour(), "Hour should be 14");
		assertEquals(30, result.getMinute(), "Minute should be 30");
		assertEquals(45, result.getSecond(), "Second should be 45");
		assertEquals(ZoneId.systemDefault(), result.getZone(), "Zone should be system default");
	}

	/**
	 * Test unmarshal method with midnight time.
	 */
	@Test
	void testUnmarshalWithMidnight() {
		ZonedDateTimeAdapter adapter = new ZonedDateTimeAdapter();

		String dateString = "20241231-000000";

		ZonedDateTime result = adapter.unmarshal(dateString);

		assertEquals(2024, result.getYear(), "Year should be 2024");
		assertEquals(12, result.getMonthValue(), "Month should be 12");
		assertEquals(31, result.getDayOfMonth(), "Day should be 31");
		assertEquals(0, result.getHour(), "Hour should be 0");
		assertEquals(0, result.getMinute(), "Minute should be 0");
		assertEquals(0, result.getSecond(), "Second should be 0");
	}

	/**
	 * Test unmarshal method with end of day time.
	 */
	@Test
	void testUnmarshalWithEndOfDay() {
		ZonedDateTimeAdapter adapter = new ZonedDateTimeAdapter();

		String dateString = "20240630-235959";

		ZonedDateTime result = adapter.unmarshal(dateString);

		assertEquals(2024, result.getYear(), "Year should be 2024");
		assertEquals(6, result.getMonthValue(), "Month should be 6");
		assertEquals(30, result.getDayOfMonth(), "Day should be 30");
		assertEquals(23, result.getHour(), "Hour should be 23");
		assertEquals(59, result.getMinute(), "Minute should be 59");
		assertEquals(59, result.getSecond(), "Second should be 59");
	}

	/**
	 * Test unmarshal method with single digit values (padded with zeros).
	 */
	@Test
	void testUnmarshalWithPaddedSingleDigits() {
		ZonedDateTimeAdapter adapter = new ZonedDateTimeAdapter();

		String dateString = "20240105-080706";

		ZonedDateTime result = adapter.unmarshal(dateString);

		assertEquals(2024, result.getYear(), "Year should be 2024");
		assertEquals(1, result.getMonthValue(), "Month should be 1");
		assertEquals(5, result.getDayOfMonth(), "Day should be 5");
		assertEquals(8, result.getHour(), "Hour should be 8");
		assertEquals(7, result.getMinute(), "Minute should be 7");
		assertEquals(6, result.getSecond(), "Second should be 6");
	}

	/**
	 * Test unmarshal method with invalid date string format.
	 */
	@Test
	void testUnmarshalWithInvalidFormat() {
		ZonedDateTimeAdapter adapter = new ZonedDateTimeAdapter();

		String invalidDateString = "2024-03-15 14:30:45";

		assertThrows(DateTimeParseException.class, () -> {
			adapter.unmarshal(invalidDateString);
		}, "Unmarshal should throw DateTimeParseException for invalid format");
	}

	/**
	 * Test unmarshal method with invalid date values.
	 */
	@Test
	void testUnmarshalWithInvalidDateValues() {
		ZonedDateTimeAdapter adapter = new ZonedDateTimeAdapter();

		// Invalid month (13)
		String invalidMonth = "20241305-120000";
		assertThrows(DateTimeParseException.class, () -> {
			adapter.unmarshal(invalidMonth);
		}, "Unmarshal should throw exception for invalid month");

		// Invalid day (32)
		String invalidDay = "20240132-120000";
		assertThrows(DateTimeParseException.class, () -> {
			adapter.unmarshal(invalidDay);
		}, "Unmarshal should throw exception for invalid day");
	}

	/**
	 * Test unmarshal method with invalid time values.
	 */
	@Test
	void testUnmarshalWithInvalidTimeValues() {
		ZonedDateTimeAdapter adapter = new ZonedDateTimeAdapter();

		// Invalid hour (25)
		String invalidHour = "20240315-250000";
		assertThrows(DateTimeParseException.class, () -> {
			adapter.unmarshal(invalidHour);
		}, "Unmarshal should throw exception for invalid hour");

		// Invalid minute (60)
		String invalidMinute = "20240315-126000";
		assertThrows(DateTimeParseException.class, () -> {
			adapter.unmarshal(invalidMinute);
		}, "Unmarshal should throw exception for invalid minute");

		// Invalid second (60)
		String invalidSecond = "20240315-123060";
		assertThrows(DateTimeParseException.class, () -> {
			adapter.unmarshal(invalidSecond);
		}, "Unmarshal should throw exception for invalid second");
	}

	/**
	 * Test unmarshal method with too short string.
	 */
	@Test
	void testUnmarshalWithTooShortString() {
		ZonedDateTimeAdapter adapter = new ZonedDateTimeAdapter();

		String shortString = "20240315";

		assertThrows(DateTimeParseException.class, () -> {
			adapter.unmarshal(shortString);
		}, "Unmarshal should throw exception for string that's too short");
	}

	/**
	 * Test unmarshal method with null string.
	 */
	@Test
	void testUnmarshalWithNullString() {
		ZonedDateTimeAdapter adapter = new ZonedDateTimeAdapter();

		assertThrows(NullPointerException.class, () -> {
			adapter.unmarshal(null);
		}, "Unmarshal should throw NullPointerException for null input");
	}

	/**
	 * Test unmarshal method with empty string.
	 */
	@Test
	void testUnmarshalWithEmptyString() {
		ZonedDateTimeAdapter adapter = new ZonedDateTimeAdapter();

		assertThrows(DateTimeParseException.class, () -> {
			adapter.unmarshal("");
		}, "Unmarshal should throw exception for empty string");
	}

	/**
	 * Test round-trip: marshal then unmarshal produces equivalent datetime.
	 */
	@Test
	void testRoundTripMarshalUnmarshal() {
		ZonedDateTimeAdapter adapter = new ZonedDateTimeAdapter();

		// Create original datetime
		ZonedDateTime original = ZonedDateTime.of(2024, 8, 22, 16, 45, 30, 0, ZoneId.systemDefault());

		// Marshal to string
		String marshalled = adapter.marshal(original);

		// Unmarshal back to datetime
		ZonedDateTime roundTripped = adapter.unmarshal(marshalled);

		// Compare key components (year, month, day, hour, minute, second)
		assertEquals(original.getYear(), roundTripped.getYear(), "Year should match after round-trip");
		assertEquals(original.getMonthValue(), roundTripped.getMonthValue(), "Month should match after round-trip");
		assertEquals(original.getDayOfMonth(), roundTripped.getDayOfMonth(), "Day should match after round-trip");
		assertEquals(original.getHour(), roundTripped.getHour(), "Hour should match after round-trip");
		assertEquals(original.getMinute(), roundTripped.getMinute(), "Minute should match after round-trip");
		assertEquals(original.getSecond(), roundTripped.getSecond(), "Second should match after round-trip");
		assertEquals(original.getZone(), roundTripped.getZone(), "Zone should match after round-trip");
	}

	/**
	 * Test round-trip with different time zones.
	 */
	@Test
	void testRoundTripWithDifferentTimeZones() {
		ZonedDateTimeAdapter adapter = new ZonedDateTimeAdapter();

		// Create datetime with specific timezone (not system default)
		ZonedDateTime originalUTC = ZonedDateTime.of(2024, 5, 10, 10, 20, 30, 0, ZoneId.of("UTC"));

		// Marshal and unmarshal
		String marshalled = adapter.marshal(originalUTC);
		ZonedDateTime roundTripped = adapter.unmarshal(marshalled);

		// The local time should match
		assertEquals(originalUTC.getYear(), roundTripped.getYear());
		assertEquals(originalUTC.getMonthValue(), roundTripped.getMonthValue());
		assertEquals(originalUTC.getDayOfMonth(), roundTripped.getDayOfMonth());
		assertEquals(originalUTC.getHour(), roundTripped.getHour());
		assertEquals(originalUTC.getMinute(), roundTripped.getMinute());
		assertEquals(originalUTC.getSecond(), roundTripped.getSecond());
		// Zone will be system default after unmarshal
		assertEquals(ZoneId.systemDefault(), roundTripped.getZone());
	}

	/**
	 * Test marshal with leap year date.
	 */
	@Test
	void testMarshalWithLeapYearDate() {
		ZonedDateTimeAdapter adapter = new ZonedDateTimeAdapter();

		// February 29, 2024 (leap year)
		ZonedDateTime leapDate = ZonedDateTime.of(2024, 2, 29, 12, 0, 0, 0, ZoneId.systemDefault());

		String result = adapter.marshal(leapDate);

		assertEquals("20240229-120000", result, "Marshal should handle leap year date correctly");
	}

	/**
	 * Test unmarshal with leap year date.
	 */
	@Test
	void testUnmarshalWithLeapYearDate() {
		ZonedDateTimeAdapter adapter = new ZonedDateTimeAdapter();

		String leapDateString = "20240229-120000";

		ZonedDateTime result = adapter.unmarshal(leapDateString);

		assertEquals(2024, result.getYear());
		assertEquals(2, result.getMonthValue());
		assertEquals(29, result.getDayOfMonth());
	}

	/**
	 * Test multiple adapters are independent.
	 */
	@Test
	void testMultipleAdapterIndependence() {
		ZonedDateTimeAdapter adapter1 = new ZonedDateTimeAdapter();
		ZonedDateTimeAdapter adapter2 = new ZonedDateTimeAdapter();

		ZonedDateTime date1 = ZonedDateTime.of(2024, 1, 1, 10, 0, 0, 0, ZoneId.systemDefault());
		ZonedDateTime date2 = ZonedDateTime.of(2024, 12, 31, 22, 0, 0, 0, ZoneId.systemDefault());

		String result1 = adapter1.marshal(date1);
		String result2 = adapter2.marshal(date2);

		assertEquals("20240101-100000", result1);
		assertEquals("20241231-220000", result2);
		assertNotEquals(result1, result2, "Different adapters should produce different results for different inputs");
	}

	/**
	 * Test marshal with year 2100 (not a leap year despite being divisible by 4).
	 */
	@Test
	void testMarshalWithYear2100() {
		ZonedDateTimeAdapter adapter = new ZonedDateTimeAdapter();

		ZonedDateTime date2100 = ZonedDateTime.of(2100, 3, 1, 10, 0, 0, 0, ZoneId.systemDefault());

		String result = adapter.marshal(date2100);

		assertEquals("21000301-100000", result, "Marshal should handle year 2100 correctly");
	}

	/**
	 * Test unmarshal method with nanoseconds being ignored (format doesn't include them).
	 */
	@Test
	void testRoundTripIgnoresNanoseconds() {
		ZonedDateTimeAdapter adapter = new ZonedDateTimeAdapter();

		// Create datetime with nanoseconds
		ZonedDateTime originalWithNanos = ZonedDateTime.of(2024, 7, 15, 14, 30, 45, 123456789, ZoneId.systemDefault());

		// Marshal and unmarshal
		String marshalled = adapter.marshal(originalWithNanos);
		ZonedDateTime roundTripped = adapter.unmarshal(marshalled);

		// Nanoseconds should be lost (set to 0) in the round-trip
		assertEquals(0, roundTripped.getNano(), "Nanoseconds should be 0 after round-trip");
		assertEquals(originalWithNanos.getSecond(), roundTripped.getSecond(), "Seconds should match");
	}
}
