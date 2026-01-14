/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for CalibrationContextImpl.
 * Tests all methods with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class CalibrationContextImplClaudeTest {

	/**
	 * Test constructor and getReferenceDateTime with a typical date and time.
	 */
	@Test
	void testConstructorAndGetReferenceDateTime() {
		LocalDateTime expectedDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		double expectedAccuracy = 1e-6;

		CalibrationContextImpl context = new CalibrationContextImpl(expectedDateTime, expectedAccuracy);

		assertNotNull(context);
		assertEquals(expectedDateTime, context.getReferenceDateTime());
	}

	/**
	 * Test getReferenceDate returns the date portion of the reference date/time.
	 */
	@Test
	void testGetReferenceDate() {
		LocalDateTime dateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContextImpl context = new CalibrationContextImpl(dateTime, 1e-6);

		LocalDate expectedDate = LocalDate.of(2024, 6, 15);
		assertEquals(expectedDate, context.getReferenceDate());
	}

	/**
	 * Test getReferenceDate with midnight time (start of day).
	 */
	@Test
	void testGetReferenceDateAtMidnight() {
		LocalDateTime dateTime = LocalDateTime.of(2024, 6, 15, 0, 0, 0);
		CalibrationContextImpl context = new CalibrationContextImpl(dateTime, 1e-6);

		LocalDate expectedDate = LocalDate.of(2024, 6, 15);
		assertEquals(expectedDate, context.getReferenceDate());
	}

	/**
	 * Test getReferenceDate with end of day time.
	 */
	@Test
	void testGetReferenceDateAtEndOfDay() {
		LocalDateTime dateTime = LocalDateTime.of(2024, 6, 15, 23, 59, 59);
		CalibrationContextImpl context = new CalibrationContextImpl(dateTime, 1e-6);

		LocalDate expectedDate = LocalDate.of(2024, 6, 15);
		assertEquals(expectedDate, context.getReferenceDate());
	}

	/**
	 * Test getAccuracy with a typical positive value.
	 */
	@Test
	void testGetAccuracyPositive() {
		double expectedAccuracy = 1e-6;
		CalibrationContextImpl context = new CalibrationContextImpl(
			LocalDateTime.of(2024, 6, 15, 10, 30, 0),
			expectedAccuracy
		);

		assertEquals(expectedAccuracy, context.getAccuracy());
	}

	/**
	 * Test getAccuracy with zero value.
	 */
	@Test
	void testGetAccuracyZero() {
		double expectedAccuracy = 0.0;
		CalibrationContextImpl context = new CalibrationContextImpl(
			LocalDateTime.of(2024, 6, 15, 10, 30, 0),
			expectedAccuracy
		);

		assertEquals(expectedAccuracy, context.getAccuracy());
	}

	/**
	 * Test getAccuracy with a very small value.
	 */
	@Test
	void testGetAccuracyVerySmall() {
		double expectedAccuracy = 1e-15;
		CalibrationContextImpl context = new CalibrationContextImpl(
			LocalDateTime.of(2024, 6, 15, 10, 30, 0),
			expectedAccuracy
		);

		assertEquals(expectedAccuracy, context.getAccuracy());
	}

	/**
	 * Test getAccuracy with a larger value.
	 */
	@Test
	void testGetAccuracyLarge() {
		double expectedAccuracy = 0.001;
		CalibrationContextImpl context = new CalibrationContextImpl(
			LocalDateTime.of(2024, 6, 15, 10, 30, 0),
			expectedAccuracy
		);

		assertEquals(expectedAccuracy, context.getAccuracy());
	}

	/**
	 * Test getAccuracy with negative value (edge case, potentially invalid but allowed by signature).
	 */
	@Test
	void testGetAccuracyNegative() {
		double expectedAccuracy = -1e-6;
		CalibrationContextImpl context = new CalibrationContextImpl(
			LocalDateTime.of(2024, 6, 15, 10, 30, 0),
			expectedAccuracy
		);

		assertEquals(expectedAccuracy, context.getAccuracy());
	}

	/**
	 * Test with leap year date.
	 */
	@Test
	void testWithLeapYearDate() {
		LocalDateTime leapYearDateTime = LocalDateTime.of(2024, 2, 29, 12, 0, 0);
		CalibrationContextImpl context = new CalibrationContextImpl(leapYearDateTime, 1e-6);

		assertEquals(leapYearDateTime, context.getReferenceDateTime());
		assertEquals(LocalDate.of(2024, 2, 29), context.getReferenceDate());
	}

	/**
	 * Test with year boundary date (New Year's Eve).
	 */
	@Test
	void testWithYearBoundaryDate() {
		LocalDateTime yearEndDateTime = LocalDateTime.of(2024, 12, 31, 23, 59, 59);
		CalibrationContextImpl context = new CalibrationContextImpl(yearEndDateTime, 1e-6);

		assertEquals(yearEndDateTime, context.getReferenceDateTime());
		assertEquals(LocalDate.of(2024, 12, 31), context.getReferenceDate());
	}

	/**
	 * Test with year start date (New Year's Day).
	 */
	@Test
	void testWithYearStartDate() {
		LocalDateTime yearStartDateTime = LocalDateTime.of(2025, 1, 1, 0, 0, 0);
		CalibrationContextImpl context = new CalibrationContextImpl(yearStartDateTime, 1e-6);

		assertEquals(yearStartDateTime, context.getReferenceDateTime());
		assertEquals(LocalDate.of(2025, 1, 1), context.getReferenceDate());
	}

	/**
	 * Test with nanosecond precision in time.
	 */
	@Test
	void testWithNanoseconds() {
		LocalDateTime dateTimeWithNanos = LocalDateTime.of(2024, 6, 15, 10, 30, 45, 123456789);
		CalibrationContextImpl context = new CalibrationContextImpl(dateTimeWithNanos, 1e-9);

		assertEquals(dateTimeWithNanos, context.getReferenceDateTime());
		assertEquals(LocalDate.of(2024, 6, 15), context.getReferenceDate());
	}

	/**
	 * Test immutability - verify that the returned date is derived from internal state.
	 */
	@Test
	void testImmutability() {
		LocalDateTime originalDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		double originalAccuracy = 1e-6;
		CalibrationContextImpl context = new CalibrationContextImpl(originalDateTime, originalAccuracy);

		// Get values multiple times and verify they're consistent
		LocalDateTime dateTime1 = context.getReferenceDateTime();
		LocalDateTime dateTime2 = context.getReferenceDateTime();
		LocalDate date1 = context.getReferenceDate();
		LocalDate date2 = context.getReferenceDate();
		double accuracy1 = context.getAccuracy();
		double accuracy2 = context.getAccuracy();

		assertEquals(dateTime1, dateTime2);
		assertEquals(date1, date2);
		assertEquals(accuracy1, accuracy2);
		assertEquals(originalDateTime, dateTime1);
		assertEquals(originalAccuracy, accuracy1);
	}

	/**
	 * Test that CalibrationContextImpl implements CalibrationContext interface.
	 */
	@Test
	void testImplementsInterface() {
		LocalDateTime dateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContextImpl context = new CalibrationContextImpl(dateTime, 1e-6);

		assertTrue(context instanceof CalibrationContext);
	}

	/**
	 * Test with minimum LocalDateTime value.
	 */
	@Test
	void testWithMinimumDateTime() {
		LocalDateTime minDateTime = LocalDateTime.MIN;
		CalibrationContextImpl context = new CalibrationContextImpl(minDateTime, 1e-6);

		assertEquals(minDateTime, context.getReferenceDateTime());
		assertEquals(minDateTime.toLocalDate(), context.getReferenceDate());
	}

	/**
	 * Test with maximum LocalDateTime value.
	 */
	@Test
	void testWithMaximumDateTime() {
		LocalDateTime maxDateTime = LocalDateTime.MAX;
		CalibrationContextImpl context = new CalibrationContextImpl(maxDateTime, 1e-6);

		assertEquals(maxDateTime, context.getReferenceDateTime());
		assertEquals(maxDateTime.toLocalDate(), context.getReferenceDate());
	}

	/**
	 * Test with Double.MAX_VALUE accuracy.
	 */
	@Test
	void testWithMaximumAccuracy() {
		CalibrationContextImpl context = new CalibrationContextImpl(
			LocalDateTime.of(2024, 6, 15, 10, 30, 0),
			Double.MAX_VALUE
		);

		assertEquals(Double.MAX_VALUE, context.getAccuracy());
	}

	/**
	 * Test with Double.MIN_VALUE accuracy (smallest positive value).
	 */
	@Test
	void testWithMinimumPositiveAccuracy() {
		CalibrationContextImpl context = new CalibrationContextImpl(
			LocalDateTime.of(2024, 6, 15, 10, 30, 0),
			Double.MIN_VALUE
		);

		assertEquals(Double.MIN_VALUE, context.getAccuracy());
	}

	/**
	 * Test with positive infinity accuracy.
	 */
	@Test
	void testWithPositiveInfinityAccuracy() {
		CalibrationContextImpl context = new CalibrationContextImpl(
			LocalDateTime.of(2024, 6, 15, 10, 30, 0),
			Double.POSITIVE_INFINITY
		);

		assertEquals(Double.POSITIVE_INFINITY, context.getAccuracy());
	}

	/**
	 * Test with negative infinity accuracy.
	 */
	@Test
	void testWithNegativeInfinityAccuracy() {
		CalibrationContextImpl context = new CalibrationContextImpl(
			LocalDateTime.of(2024, 6, 15, 10, 30, 0),
			Double.NEGATIVE_INFINITY
		);

		assertEquals(Double.NEGATIVE_INFINITY, context.getAccuracy());
	}

	/**
	 * Test with NaN accuracy.
	 */
	@Test
	void testWithNaNAccuracy() {
		CalibrationContextImpl context = new CalibrationContextImpl(
			LocalDateTime.of(2024, 6, 15, 10, 30, 0),
			Double.NaN
		);

		assertTrue(Double.isNaN(context.getAccuracy()));
	}
}
