package net.finmath.smartcontract.model;

import net.finmath.smartcontract.valuation.marketdata.data.MarketDataPoint;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MarketDataListTest {

	@Test
	void testConstructor() {
		MarketDataList marketDataList = new MarketDataList();

		assertNotNull(marketDataList.getRequestTimeStamp());
		assertNotNull(marketDataList.getPoints());
		assertEquals(0, marketDataList.getSize());
	}

	@Test
	void testSetRequestTimeStamp() {
		MarketDataList marketDataList = new MarketDataList();
		LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);

		marketDataList.setRequestTimeStamp(timestamp);

		assertEquals(timestamp, marketDataList.getRequestTimeStamp());
	}

	@Test
	void testAdd() {
		MarketDataList marketDataList = new MarketDataList();
		MarketDataPoint point = new MarketDataPoint("RATE_1", 0.05, LocalDateTime.now());

		marketDataList.add(point);

		assertEquals(1, marketDataList.getSize());
		assertEquals(point, marketDataList.getPoints().get(0));
	}

	@Test
	void testSetPoints() {
		MarketDataList marketDataList = new MarketDataList();
		List<MarketDataPoint> points = new ArrayList<>();
		points.add(new MarketDataPoint("RATE_1", 0.05, LocalDateTime.now()));
		points.add(new MarketDataPoint("RATE_2", 0.03, LocalDateTime.now()));

		marketDataList.setPoints(points);

		assertEquals(2, marketDataList.getSize());
		assertSame(points, marketDataList.getPoints());
	}

	@Test
	void testSerializeToJson() {
		MarketDataList marketDataList = new MarketDataList();
		LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
		marketDataList.setRequestTimeStamp(timestamp);
		marketDataList.add(new MarketDataPoint("RATE_1", 0.05, timestamp));

		String json = marketDataList.serializeToJson();

		assertNotNull(json);
		assertFalse(json.isEmpty());
		assertTrue(json.contains("RATE_1"));
	}

	@Test
	void testMapToCalibrationDataSet() {
		MarketDataList marketDataList = new MarketDataList();

		assertNull(marketDataList.mapToCalibrationDataSet());
	}

	@Test
	void testToString() {
		MarketDataList marketDataList = new MarketDataList();

		String result = marketDataList.toString();

		assertNotNull(result);
		assertTrue(result.contains("MarketDataList"));
		assertTrue(result.contains("points="));
		assertTrue(result.contains("requestTimeStamp="));
	}

	@Test
	void testEqualsSameObject() {
		MarketDataList marketDataList = new MarketDataList();

		assertEquals(marketDataList, marketDataList);
	}

	@Test
	void testEqualsNull() {
		MarketDataList marketDataList = new MarketDataList();

		assertNotEquals(null, marketDataList);
	}

	@Test
	void testEqualsDifferentClass() {
		MarketDataList marketDataList = new MarketDataList();

		assertNotEquals("string", marketDataList);
	}

	@Test
	void testEqualsEqualObjects() {
		LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
		MarketDataList list1 = new MarketDataList();
		list1.setRequestTimeStamp(timestamp);
		MarketDataList list2 = new MarketDataList();
		list2.setRequestTimeStamp(timestamp);

		assertEquals(list1, list2);
	}
}
