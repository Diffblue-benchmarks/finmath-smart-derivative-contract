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
		final MarketDataList marketDataList = new MarketDataList();

		assertNotNull(marketDataList);
		assertNotNull(marketDataList.getRequestTimeStamp());
		assertNotNull(marketDataList.getPoints());
		assertEquals(0, marketDataList.getSize());
	}

	@Test
	void testSetRequestTimeStamp() {
		final MarketDataList marketDataList = new MarketDataList();
		final LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);

		marketDataList.setRequestTimeStamp(timestamp);

		assertEquals(timestamp, marketDataList.getRequestTimeStamp());
	}

	@Test
	void testAdd() {
		final MarketDataList marketDataList = new MarketDataList();
		final MarketDataPoint point = new MarketDataPoint("TEST_ID", 1.5, LocalDateTime.now());

		marketDataList.add(point);

		assertEquals(1, marketDataList.getSize());
		assertTrue(marketDataList.getPoints().contains(point));
	}

	@Test
	void setPoints() {
		final MarketDataList marketDataList = new MarketDataList();
		final List<MarketDataPoint> points = new ArrayList<>();
		points.add(new MarketDataPoint());

		marketDataList.setPoints(points);

		assertNotNull(marketDataList);
		assertNotNull(marketDataList.toString());
		assertNotNull(marketDataList.getPoints());
		assertEquals(points, marketDataList.getPoints());
	}

	@Test
	void testGetSize() {
		final MarketDataList marketDataList = new MarketDataList();

		assertEquals(0, marketDataList.getSize());

		marketDataList.add(new MarketDataPoint("ID1", 1.0, LocalDateTime.now()));
		assertEquals(1, marketDataList.getSize());

		marketDataList.add(new MarketDataPoint("ID2", 2.0, LocalDateTime.now()));
		assertEquals(2, marketDataList.getSize());
	}

	@Test
	void testGetPoints() {
		final MarketDataList marketDataList = new MarketDataList();
		final MarketDataPoint point = new MarketDataPoint("TEST", 3.14, LocalDateTime.now());

		marketDataList.add(point);

		final List<MarketDataPoint> points = marketDataList.getPoints();
		assertNotNull(points);
		assertEquals(1, points.size());
		assertEquals(point, points.get(0));
	}

	@Test
	void testSerializeToJson() {
		final MarketDataList marketDataList = new MarketDataList();
		final LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
		marketDataList.setRequestTimeStamp(timestamp);

		final MarketDataPoint point = new MarketDataPoint("TEST_ID", 1.5, timestamp);
		marketDataList.add(point);

		final String json = marketDataList.serializeToJson();

		assertNotNull(json);
		assertFalse(json.isEmpty());
		assertTrue(json.contains("TEST_ID"));
		assertTrue(json.contains("requestTimeStamp"));
	}

	@Test
	void testSerializeToJsonWithException() {
		final MarketDataList marketDataList = new MarketDataList();

		final String json = marketDataList.serializeToJson();

		assertNotNull(json);
	}

	@Test
	void testMapToCalibrationDataSet() {
		final MarketDataList marketDataList = new MarketDataList();

		assertNull(marketDataList.mapToCalibrationDataSet());
	}

	@Test
	void testToString() {
		final MarketDataList marketDataList = new MarketDataList();
		final LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
		marketDataList.setRequestTimeStamp(timestamp);

		final String result = marketDataList.toString();

		assertNotNull(result);
		assertTrue(result.contains("MarketDataList"));
		assertTrue(result.contains("points="));
		assertTrue(result.contains("requestTimeStamp="));
	}

	@Test
	void testEquals() {
		final LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
		final MarketDataPoint point = new MarketDataPoint("ID", 1.0, timestamp);

		final MarketDataList list1 = new MarketDataList();
		list1.setRequestTimeStamp(timestamp);
		list1.add(point);

		final MarketDataList list2 = new MarketDataList();
		list2.setRequestTimeStamp(timestamp);
		list2.add(point);

		final MarketDataList list3 = new MarketDataList();
		list3.setRequestTimeStamp(LocalDateTime.now());

		assertTrue(list1.equals(list1));
		assertTrue(list1.equals(list2));
		assertFalse(list1.equals(list3));
		assertFalse(list1.equals(null));
		assertFalse(list1.equals("string"));
	}
}