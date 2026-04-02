package net.finmath.smartcontract.valuation.marketdata.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class MarketDataPointTest {

	@Test
	void testConstructorSetsFields() {
		LocalDateTime ts = LocalDateTime.of(2024, 1, 15, 10, 0);
		MarketDataPoint point = new MarketDataPoint("EUR-OIS-1Y", 0.035, ts);

		Assertions.assertEquals("EUR-OIS-1Y", point.getId());
		Assertions.assertEquals(0.035, point.getValue());
		Assertions.assertEquals(ts, point.getTimeStamp());
	}

	@Test
	void testEqualsReflexive() {
		LocalDateTime ts = LocalDateTime.of(2024, 1, 15, 10, 0);
		MarketDataPoint point = new MarketDataPoint("EUR-OIS-1Y", 0.035, ts);

		Assertions.assertTrue(point.equals(point));
	}

	@Test
	void testEqualsNullReturnsFalse() {
		LocalDateTime ts = LocalDateTime.of(2024, 1, 15, 10, 0);
		MarketDataPoint point = new MarketDataPoint("EUR-OIS-1Y", 0.035, ts);

		Assertions.assertFalse(point.equals(null));
	}

	@Test
	void testEqualsDifferentClassReturnsFalse() {
		LocalDateTime ts = LocalDateTime.of(2024, 1, 15, 10, 0);
		MarketDataPoint point = new MarketDataPoint("EUR-OIS-1Y", 0.035, ts);

		Assertions.assertFalse(point.equals("not a MarketDataPoint"));
	}

	@Test
	void testEqualsEqualObjects() {
		LocalDateTime ts = LocalDateTime.of(2024, 1, 15, 10, 0);
		MarketDataPoint point1 = new MarketDataPoint("EUR-OIS-1Y", 0.035, ts);
		MarketDataPoint point2 = new MarketDataPoint("EUR-OIS-1Y", 0.035, ts);

		Assertions.assertTrue(point1.equals(point2));
	}

	@Test
	void testEqualsDifferentId() {
		LocalDateTime ts = LocalDateTime.of(2024, 1, 15, 10, 0);
		MarketDataPoint point1 = new MarketDataPoint("EUR-OIS-1Y", 0.035, ts);
		MarketDataPoint point2 = new MarketDataPoint("EUR-OIS-2Y", 0.035, ts);

		Assertions.assertFalse(point1.equals(point2));
	}

	@Test
	void testEqualsDifferentValue() {
		LocalDateTime ts = LocalDateTime.of(2024, 1, 15, 10, 0);
		MarketDataPoint point1 = new MarketDataPoint("EUR-OIS-1Y", 0.035, ts);
		MarketDataPoint point2 = new MarketDataPoint("EUR-OIS-1Y", 0.040, ts);

		Assertions.assertFalse(point1.equals(point2));
	}

	@Test
	void testEqualsDifferentTimestamp() {
		LocalDateTime ts1 = LocalDateTime.of(2024, 1, 15, 10, 0);
		LocalDateTime ts2 = LocalDateTime.of(2024, 1, 16, 10, 0);
		MarketDataPoint point1 = new MarketDataPoint("EUR-OIS-1Y", 0.035, ts1);
		MarketDataPoint point2 = new MarketDataPoint("EUR-OIS-1Y", 0.035, ts2);

		Assertions.assertFalse(point1.equals(point2));
	}
}
