package net.finmath.smartcontract.valuation.marketdata.data;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MarketDataPointTest {

	@Test
	void testConstructorWithParameters() {
		// Given
		final String id = "EUR-USD";
		final Double value = 1.25;
		final LocalDateTime timeStamp = LocalDateTime.of(2024, 1, 15, 10, 30);

		// When
		final MarketDataPoint dataPoint = new MarketDataPoint(id, value, timeStamp);

		// Then
		assertEquals(id, dataPoint.getId());
		assertEquals(value, dataPoint.getValue());
		assertEquals(timeStamp, dataPoint.getTimeStamp());
	}

	@Test
	void testDefaultConstructor() {
		// When
		final MarketDataPoint dataPoint = new MarketDataPoint();

		// Then
		assertNull(dataPoint.getId());
		assertNull(dataPoint.getValue());
		assertNull(dataPoint.getTimeStamp());
	}

	@Test
	void testGettersAndSetters() {
		// Given
		final MarketDataPoint dataPoint = new MarketDataPoint();
		final String id = "ESTRSWP3Y";
		final Double value = 3.14;
		final LocalDateTime timeStamp = LocalDateTime.of(2024, 3, 20, 14, 45);

		// When
		dataPoint.setId(id);
		dataPoint.setValue(value);
		dataPoint.setTimeStamp(timeStamp);

		// Then
		assertEquals(id, dataPoint.getId());
		assertEquals(value, dataPoint.getValue());
		assertEquals(timeStamp, dataPoint.getTimeStamp());
	}

	@Test
	void testToString() {
		// Given
		final String id = "EUR-USD";
		final Double value = 1.25;
		final LocalDateTime timeStamp = LocalDateTime.of(2024, 1, 15, 10, 30);
		final MarketDataPoint dataPoint = new MarketDataPoint(id, value, timeStamp);

		// When
		final String result = dataPoint.toString();

		// Then
		assertTrue(result.contains("MarketDataPoint"));
		assertTrue(result.contains("id='EUR-USD'"));
		assertTrue(result.contains("value=1.25"));
		assertTrue(result.contains("timeStamp=2024-01-15T10:30"));
	}

	@Test
	void testEqualsSameObject() {
		// Given
		final MarketDataPoint dataPoint = new MarketDataPoint("EUR-USD", 1.25, LocalDateTime.now());

		// When
		final boolean result = dataPoint.equals(dataPoint);

		// Then
		assertTrue(result);
	}

	@Test
	void testEqualsWithNull() {
		// Given
		final MarketDataPoint dataPoint = new MarketDataPoint("EUR-USD", 1.25, LocalDateTime.now());

		// When
		final boolean result = dataPoint.equals(null);

		// Then
		assertFalse(result);
	}

	@Test
	void testEqualsWithDifferentClass() {
		// Given
		final MarketDataPoint dataPoint = new MarketDataPoint("EUR-USD", 1.25, LocalDateTime.now());
		final String differentObject = "not a MarketDataPoint";

		// When
		final boolean result = dataPoint.equals(differentObject);

		// Then
		assertFalse(result);
	}

	@Test
	void testEqualsWithEqualObjects() {
		// Given
		final String id = "EUR-USD";
		final Double value = 1.25;
		final LocalDateTime timeStamp = LocalDateTime.of(2024, 1, 15, 10, 30);
		final MarketDataPoint dataPoint1 = new MarketDataPoint(id, value, timeStamp);
		final MarketDataPoint dataPoint2 = new MarketDataPoint(id, value, timeStamp);

		// When
		final boolean result = dataPoint1.equals(dataPoint2);

		// Then
		assertTrue(result);
	}

	@Test
	void testEqualsWithDifferentId() {
		// Given
		final Double value = 1.25;
		final LocalDateTime timeStamp = LocalDateTime.of(2024, 1, 15, 10, 30);
		final MarketDataPoint dataPoint1 = new MarketDataPoint("EUR-USD", value, timeStamp);
		final MarketDataPoint dataPoint2 = new MarketDataPoint("GBP-USD", value, timeStamp);

		// When
		final boolean result = dataPoint1.equals(dataPoint2);

		// Then
		assertFalse(result);
	}

	@Test
	void testEqualsWithDifferentValue() {
		// Given
		final String id = "EUR-USD";
		final LocalDateTime timeStamp = LocalDateTime.of(2024, 1, 15, 10, 30);
		final MarketDataPoint dataPoint1 = new MarketDataPoint(id, 1.25, timeStamp);
		final MarketDataPoint dataPoint2 = new MarketDataPoint(id, 1.30, timeStamp);

		// When
		final boolean result = dataPoint1.equals(dataPoint2);

		// Then
		assertFalse(result);
	}

	@Test
	void testEqualsWithDifferentTimeStamp() {
		// Given
		final String id = "EUR-USD";
		final Double value = 1.25;
		final MarketDataPoint dataPoint1 = new MarketDataPoint(id, value, LocalDateTime.of(2024, 1, 15, 10, 30));
		final MarketDataPoint dataPoint2 = new MarketDataPoint(id, value, LocalDateTime.of(2024, 1, 15, 11, 30));

		// When
		final boolean result = dataPoint1.equals(dataPoint2);

		// Then
		assertFalse(result);
	}
}
