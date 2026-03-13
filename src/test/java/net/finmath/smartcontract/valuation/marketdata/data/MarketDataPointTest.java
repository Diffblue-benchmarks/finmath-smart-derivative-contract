package net.finmath.smartcontract.valuation.marketdata.data;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MarketDataPointTest {

	private final LocalDateTime timestamp = LocalDateTime.of(2024, 6, 15, 10, 30);

	@Test
	void parameterizedConstructor_shouldSetAllFields() {
		MarketDataPoint point = new MarketDataPoint("EUR6M_1Y", 0.035, timestamp);
		assertEquals("EUR6M_1Y", point.getId());
		assertEquals(0.035, point.getValue());
		assertEquals(timestamp, point.getTimeStamp());
	}

	@Test
	void defaultConstructor_shouldCreateEmptyPoint() {
		MarketDataPoint point = new MarketDataPoint();
		assertNull(point.getId());
		assertNull(point.getValue());
		assertNull(point.getTimeStamp());
	}

	@Test
	void setters_shouldUpdateFields() {
		MarketDataPoint point = new MarketDataPoint();
		point.setId("ESTR_2Y");
		point.setValue(0.025);
		point.setTimeStamp(timestamp);

		assertEquals("ESTR_2Y", point.getId());
		assertEquals(0.025, point.getValue());
		assertEquals(timestamp, point.getTimeStamp());
	}

	@Test
	void equals_shouldBeTrue_forSameContent() {
		MarketDataPoint p1 = new MarketDataPoint("id1", 1.0, timestamp);
		MarketDataPoint p2 = new MarketDataPoint("id1", 1.0, timestamp);
		assertEquals(p1, p2);
	}

	@Test
	void equals_shouldBeFalse_forDifferentId() {
		MarketDataPoint p1 = new MarketDataPoint("id1", 1.0, timestamp);
		MarketDataPoint p2 = new MarketDataPoint("id2", 1.0, timestamp);
		assertNotEquals(p1, p2);
	}

	@Test
	void equals_shouldBeFalse_forNull() {
		MarketDataPoint p1 = new MarketDataPoint("id1", 1.0, timestamp);
		assertNotEquals(null, p1);
	}

	@Test
	void toString_shouldContainFields() {
		MarketDataPoint point = new MarketDataPoint("EUR6M_1Y", 0.035, timestamp);
		String str = point.toString();
		assertTrue(str.contains("EUR6M_1Y"));
		assertTrue(str.contains("0.035"));
	}
}
