package net.finmath.smartcontract.valuation.marketdata.data;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MarketDataPointTest {

	@Test
	void testParameterizedConstructorAndGetters() {
		LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30, 0);
		MarketDataPoint point = new MarketDataPoint("EUR-USD", 1.12, timestamp);

		assertEquals("EUR-USD", point.getId());
		assertEquals(1.12, point.getValue());
		assertEquals(timestamp, point.getTimeStamp());
	}

	@Test
	void testDefaultConstructor() {
		MarketDataPoint point = new MarketDataPoint();

		assertNull(point.getId());
		assertNull(point.getValue());
		assertNull(point.getTimeStamp());
	}

	@Test
	void testSetters() {
		MarketDataPoint point = new MarketDataPoint();
		LocalDateTime timestamp = LocalDateTime.of(2024, 6, 1, 12, 0, 0);

		point.setId("LIBOR-3M");
		point.setValue(0.05);
		point.setTimeStamp(timestamp);

		assertEquals("LIBOR-3M", point.getId());
		assertEquals(0.05, point.getValue());
		assertEquals(timestamp, point.getTimeStamp());
	}

	@Test
	void testToString() {
		LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30, 0);
		MarketDataPoint point = new MarketDataPoint("EUR-USD", 1.12, timestamp);

		String result = point.toString();

		assertTrue(result.contains("EUR-USD"));
		assertTrue(result.contains("1.12"));
		assertTrue(result.contains("MarketDataPoint{"));
	}

	@Test
	void testEqualsSameObject() {
		MarketDataPoint point = new MarketDataPoint("EUR-USD", 1.12, LocalDateTime.now());
		assertEquals(point, point);
	}

	@Test
	void testEqualsNull() {
		MarketDataPoint point = new MarketDataPoint("EUR-USD", 1.12, LocalDateTime.now());
		assertNotEquals(null, point);
	}

	@Test
	void testEqualsDifferentClass() {
		MarketDataPoint point = new MarketDataPoint("EUR-USD", 1.12, LocalDateTime.now());
		assertNotEquals("not a MarketDataPoint", point);
	}

	@Test
	void testEqualsEqualObjects() {
		LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30, 0);
		MarketDataPoint point1 = new MarketDataPoint("EUR-USD", 1.12, timestamp);
		MarketDataPoint point2 = new MarketDataPoint("EUR-USD", 1.12, timestamp);

		assertEquals(point1, point2);
	}

	@Test
	void testEqualsDifferentValues() {
		LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30, 0);
		MarketDataPoint point1 = new MarketDataPoint("EUR-USD", 1.12, timestamp);
		MarketDataPoint point2 = new MarketDataPoint("EUR-USD", 1.15, timestamp);

		assertNotEquals(point1, point2);
	}
}
