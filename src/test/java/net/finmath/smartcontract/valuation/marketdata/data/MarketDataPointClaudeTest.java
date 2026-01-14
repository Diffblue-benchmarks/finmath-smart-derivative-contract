/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.marketdata.data;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MarketDataPoint.
 * Tests all methods with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class MarketDataPointClaudeTest {

	/**
	 * Test the parameterized constructor with all valid values.
	 */
	@Test
	void testParameterizedConstructor() {
		String id = "EUR-USD-FX";
		Double value = 1.2345;
		LocalDateTime timeStamp = LocalDateTime.of(2024, 1, 15, 10, 30, 0);

		MarketDataPoint dataPoint = new MarketDataPoint(id, value, timeStamp);

		assertEquals(id, dataPoint.getId());
		assertEquals(value, dataPoint.getValue());
		assertEquals(timeStamp, dataPoint.getTimeStamp());
	}

	/**
	 * Test the parameterized constructor with null values.
	 */
	@Test
	void testParameterizedConstructorWithNullValues() {
		MarketDataPoint dataPoint = new MarketDataPoint(null, null, null);

		assertNull(dataPoint.getId());
		assertNull(dataPoint.getValue());
		assertNull(dataPoint.getTimeStamp());
	}

	/**
	 * Test the no-argument constructor.
	 */
	@Test
	void testNoArgConstructor() {
		MarketDataPoint dataPoint = new MarketDataPoint();

		assertNull(dataPoint.getId());
		assertNull(dataPoint.getValue());
		assertNull(dataPoint.getTimeStamp());
	}

	/**
	 * Test getId returns the correct value.
	 */
	@Test
	void testGetId() {
		String id = "EURIBOR-3M";
		MarketDataPoint dataPoint = new MarketDataPoint(id, 0.05, LocalDateTime.now());

		assertEquals(id, dataPoint.getId());
	}

	/**
	 * Test getId when id is null.
	 */
	@Test
	void testGetIdWhenNull() {
		MarketDataPoint dataPoint = new MarketDataPoint(null, 0.05, LocalDateTime.now());

		assertNull(dataPoint.getId());
	}

	/**
	 * Test getValue returns the correct value.
	 */
	@Test
	void testGetValue() {
		Double value = 123.456;
		MarketDataPoint dataPoint = new MarketDataPoint("TEST-ID", value, LocalDateTime.now());

		assertEquals(value, dataPoint.getValue());
	}

	/**
	 * Test getValue when value is null.
	 */
	@Test
	void testGetValueWhenNull() {
		MarketDataPoint dataPoint = new MarketDataPoint("TEST-ID", null, LocalDateTime.now());

		assertNull(dataPoint.getValue());
	}

	/**
	 * Test getValue with negative value.
	 */
	@Test
	void testGetValueNegative() {
		Double value = -99.99;
		MarketDataPoint dataPoint = new MarketDataPoint("TEST-ID", value, LocalDateTime.now());

		assertEquals(value, dataPoint.getValue());
	}

	/**
	 * Test getValue with zero.
	 */
	@Test
	void testGetValueZero() {
		Double value = 0.0;
		MarketDataPoint dataPoint = new MarketDataPoint("TEST-ID", value, LocalDateTime.now());

		assertEquals(value, dataPoint.getValue());
	}

	/**
	 * Test setId with valid value.
	 */
	@Test
	void testSetId() {
		MarketDataPoint dataPoint = new MarketDataPoint();
		String newId = "NEW-ID";

		dataPoint.setId(newId);

		assertEquals(newId, dataPoint.getId());
	}

	/**
	 * Test setId with null.
	 */
	@Test
	void testSetIdNull() {
		MarketDataPoint dataPoint = new MarketDataPoint("INITIAL-ID", 1.0, LocalDateTime.now());

		dataPoint.setId(null);

		assertNull(dataPoint.getId());
	}

	/**
	 * Test setId overwrites previous value.
	 */
	@Test
	void testSetIdOverwrite() {
		MarketDataPoint dataPoint = new MarketDataPoint("OLD-ID", 1.0, LocalDateTime.now());
		String newId = "NEW-ID";

		dataPoint.setId(newId);

		assertEquals(newId, dataPoint.getId());
	}

	/**
	 * Test setValue with valid value.
	 */
	@Test
	void testSetValue() {
		MarketDataPoint dataPoint = new MarketDataPoint();
		Double newValue = 456.789;

		dataPoint.setValue(newValue);

		assertEquals(newValue, dataPoint.getValue());
	}

	/**
	 * Test setValue with null.
	 */
	@Test
	void testSetValueNull() {
		MarketDataPoint dataPoint = new MarketDataPoint("ID", 100.0, LocalDateTime.now());

		dataPoint.setValue(null);

		assertNull(dataPoint.getValue());
	}

	/**
	 * Test setValue overwrites previous value.
	 */
	@Test
	void testSetValueOverwrite() {
		MarketDataPoint dataPoint = new MarketDataPoint("ID", 100.0, LocalDateTime.now());
		Double newValue = 200.0;

		dataPoint.setValue(newValue);

		assertEquals(newValue, dataPoint.getValue());
	}

	/**
	 * Test getTimeStamp returns the correct value.
	 */
	@Test
	void testGetTimeStamp() {
		LocalDateTime timeStamp = LocalDateTime.of(2024, 6, 15, 14, 30, 45);
		MarketDataPoint dataPoint = new MarketDataPoint("ID", 1.0, timeStamp);

		assertEquals(timeStamp, dataPoint.getTimeStamp());
	}

	/**
	 * Test getTimeStamp when timeStamp is null.
	 */
	@Test
	void testGetTimeStampWhenNull() {
		MarketDataPoint dataPoint = new MarketDataPoint("ID", 1.0, null);

		assertNull(dataPoint.getTimeStamp());
	}

	/**
	 * Test setTimeStamp with valid value.
	 */
	@Test
	void testSetTimeStamp() {
		MarketDataPoint dataPoint = new MarketDataPoint();
		LocalDateTime newTimeStamp = LocalDateTime.of(2024, 12, 25, 8, 0, 0);

		dataPoint.setTimeStamp(newTimeStamp);

		assertEquals(newTimeStamp, dataPoint.getTimeStamp());
	}

	/**
	 * Test setTimeStamp with null.
	 */
	@Test
	void testSetTimeStampNull() {
		MarketDataPoint dataPoint = new MarketDataPoint("ID", 1.0, LocalDateTime.now());

		dataPoint.setTimeStamp(null);

		assertNull(dataPoint.getTimeStamp());
	}

	/**
	 * Test setTimeStamp overwrites previous value.
	 */
	@Test
	void testSetTimeStampOverwrite() {
		LocalDateTime oldTimeStamp = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
		LocalDateTime newTimeStamp = LocalDateTime.of(2024, 12, 31, 23, 59, 59);
		MarketDataPoint dataPoint = new MarketDataPoint("ID", 1.0, oldTimeStamp);

		dataPoint.setTimeStamp(newTimeStamp);

		assertEquals(newTimeStamp, dataPoint.getTimeStamp());
	}

	/**
	 * Test toString returns expected format.
	 */
	@Test
	void testToString() {
		String id = "TEST-ID";
		Double value = 123.45;
		LocalDateTime timeStamp = LocalDateTime.of(2024, 1, 15, 10, 30, 0);
		MarketDataPoint dataPoint = new MarketDataPoint(id, value, timeStamp);

		String result = dataPoint.toString();

		assertTrue(result.contains("MarketDataPoint{"));
		assertTrue(result.contains("timeStamp=" + timeStamp));
		assertTrue(result.contains("id='TEST-ID'"));
		assertTrue(result.contains("value=123.45"));
	}

	/**
	 * Test toString with all null values.
	 */
	@Test
	void testToStringWithNullValues() {
		MarketDataPoint dataPoint = new MarketDataPoint(null, null, null);

		String result = dataPoint.toString();

		assertTrue(result.contains("MarketDataPoint{"));
		assertTrue(result.contains("timeStamp=null"));
		assertTrue(result.contains("id='null'"));
		assertTrue(result.contains("value=null"));
	}

	/**
	 * Test equals with same object returns true.
	 */
	@Test
	void testEqualsSameObject() {
		MarketDataPoint dataPoint = new MarketDataPoint("ID", 1.0, LocalDateTime.now());

		assertTrue(dataPoint.equals(dataPoint));
	}

	/**
	 * Test equals with null returns false.
	 */
	@Test
	void testEqualsNull() {
		MarketDataPoint dataPoint = new MarketDataPoint("ID", 1.0, LocalDateTime.now());

		assertFalse(dataPoint.equals(null));
	}

	/**
	 * Test equals with different class returns false.
	 */
	@Test
	void testEqualsDifferentClass() {
		MarketDataPoint dataPoint = new MarketDataPoint("ID", 1.0, LocalDateTime.now());
		String differentObject = "Not a MarketDataPoint";

		assertFalse(dataPoint.equals(differentObject));
	}

	/**
	 * Test equals with equal objects returns true.
	 */
	@Test
	void testEqualsEqualObjects() {
		LocalDateTime timeStamp = LocalDateTime.of(2024, 1, 15, 10, 30, 0);
		MarketDataPoint dataPoint1 = new MarketDataPoint("ID", 1.0, timeStamp);
		MarketDataPoint dataPoint2 = new MarketDataPoint("ID", 1.0, timeStamp);

		assertTrue(dataPoint1.equals(dataPoint2));
	}

	/**
	 * Test equals with different id returns false.
	 */
	@Test
	void testEqualsDifferentId() {
		LocalDateTime timeStamp = LocalDateTime.of(2024, 1, 15, 10, 30, 0);
		MarketDataPoint dataPoint1 = new MarketDataPoint("ID1", 1.0, timeStamp);
		MarketDataPoint dataPoint2 = new MarketDataPoint("ID2", 1.0, timeStamp);

		assertFalse(dataPoint1.equals(dataPoint2));
	}

	/**
	 * Test equals with different value returns false.
	 */
	@Test
	void testEqualsDifferentValue() {
		LocalDateTime timeStamp = LocalDateTime.of(2024, 1, 15, 10, 30, 0);
		MarketDataPoint dataPoint1 = new MarketDataPoint("ID", 1.0, timeStamp);
		MarketDataPoint dataPoint2 = new MarketDataPoint("ID", 2.0, timeStamp);

		assertFalse(dataPoint1.equals(dataPoint2));
	}

	/**
	 * Test equals with different timeStamp returns false.
	 */
	@Test
	void testEqualsDifferentTimeStamp() {
		MarketDataPoint dataPoint1 = new MarketDataPoint("ID", 1.0, LocalDateTime.of(2024, 1, 15, 10, 30, 0));
		MarketDataPoint dataPoint2 = new MarketDataPoint("ID", 1.0, LocalDateTime.of(2024, 1, 16, 10, 30, 0));

		assertFalse(dataPoint1.equals(dataPoint2));
	}

	/**
	 * Test equals with all null values returns true.
	 */
	@Test
	void testEqualsAllNullValues() {
		MarketDataPoint dataPoint1 = new MarketDataPoint(null, null, null);
		MarketDataPoint dataPoint2 = new MarketDataPoint(null, null, null);

		assertTrue(dataPoint1.equals(dataPoint2));
	}

	/**
	 * Test equals with one null id and one non-null id returns false.
	 */
	@Test
	void testEqualsOneNullId() {
		LocalDateTime timeStamp = LocalDateTime.of(2024, 1, 15, 10, 30, 0);
		MarketDataPoint dataPoint1 = new MarketDataPoint(null, 1.0, timeStamp);
		MarketDataPoint dataPoint2 = new MarketDataPoint("ID", 1.0, timeStamp);

		assertFalse(dataPoint1.equals(dataPoint2));
		assertFalse(dataPoint2.equals(dataPoint1));
	}

	/**
	 * Test equals with one null value and one non-null value returns false.
	 */
	@Test
	void testEqualsOneNullValue() {
		LocalDateTime timeStamp = LocalDateTime.of(2024, 1, 15, 10, 30, 0);
		MarketDataPoint dataPoint1 = new MarketDataPoint("ID", null, timeStamp);
		MarketDataPoint dataPoint2 = new MarketDataPoint("ID", 1.0, timeStamp);

		assertFalse(dataPoint1.equals(dataPoint2));
		assertFalse(dataPoint2.equals(dataPoint1));
	}

	/**
	 * Test equals with one null timeStamp and one non-null timeStamp returns false.
	 */
	@Test
	void testEqualsOneNullTimeStamp() {
		MarketDataPoint dataPoint1 = new MarketDataPoint("ID", 1.0, null);
		MarketDataPoint dataPoint2 = new MarketDataPoint("ID", 1.0, LocalDateTime.of(2024, 1, 15, 10, 30, 0));

		assertFalse(dataPoint1.equals(dataPoint2));
		assertFalse(dataPoint2.equals(dataPoint1));
	}

	/**
	 * Test equals with matching null values returns true.
	 */
	@Test
	void testEqualsBothIdNull() {
		LocalDateTime timeStamp = LocalDateTime.of(2024, 1, 15, 10, 30, 0);
		MarketDataPoint dataPoint1 = new MarketDataPoint(null, 1.0, timeStamp);
		MarketDataPoint dataPoint2 = new MarketDataPoint(null, 1.0, timeStamp);

		assertTrue(dataPoint1.equals(dataPoint2));
	}

	/**
	 * Test equals with matching null values returns true.
	 */
	@Test
	void testEqualsBothValueNull() {
		LocalDateTime timeStamp = LocalDateTime.of(2024, 1, 15, 10, 30, 0);
		MarketDataPoint dataPoint1 = new MarketDataPoint("ID", null, timeStamp);
		MarketDataPoint dataPoint2 = new MarketDataPoint("ID", null, timeStamp);

		assertTrue(dataPoint1.equals(dataPoint2));
	}

	/**
	 * Test equals with matching null timeStamps returns true.
	 */
	@Test
	void testEqualsBothTimeStampNull() {
		MarketDataPoint dataPoint1 = new MarketDataPoint("ID", 1.0, null);
		MarketDataPoint dataPoint2 = new MarketDataPoint("ID", 1.0, null);

		assertTrue(dataPoint1.equals(dataPoint2));
	}
}
