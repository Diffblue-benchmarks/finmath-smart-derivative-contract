package net.finmath.smartcontract.model;

import net.finmath.smartcontract.valuation.marketdata.data.MarketDataPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class MarketDataListTest {

	@Test
	void setPoints() {
		MarketDataList marketDataList = new MarketDataList();

		List<MarketDataPoint> points = new ArrayList<>();
		points.add(new MarketDataPoint());

		marketDataList.setPoints(points);

		Assertions.assertNotNull(marketDataList);
		Assertions.assertNotNull(marketDataList.toString());
		Assertions.assertNotNull(marketDataList.getPoints());
		Assertions.assertEquals(points, marketDataList.getPoints());
	}

	@Test
	void testGetSize() {
		MarketDataList marketDataList = new MarketDataList();
		Assertions.assertEquals(0, marketDataList.getSize());

		marketDataList.add(new MarketDataPoint());
		Assertions.assertEquals(1, marketDataList.getSize());
	}

	@Test
	void testSerializeToJson() {
		MarketDataList marketDataList = new MarketDataList();
		marketDataList.setRequestTimeStamp(LocalDateTime.of(2023, 1, 1, 0, 0));

		String json = marketDataList.serializeToJson();

		Assertions.assertNotNull(json);
		Assertions.assertFalse(json.isEmpty());
	}

	@Test
	void testMapToCalibrationDataSet() {
		MarketDataList marketDataList = new MarketDataList();

		Assertions.assertNull(marketDataList.mapToCalibrationDataSet());
	}

	@Test
	void testToString() {
		MarketDataList marketDataList = new MarketDataList();
		marketDataList.setRequestTimeStamp(LocalDateTime.of(2023, 1, 1, 0, 0));

		String result = marketDataList.toString();

		Assertions.assertTrue(result.contains("MarketDataList"));
		Assertions.assertTrue(result.contains("points="));
	}

	@Test
	void testEquals() {
		LocalDateTime ts = LocalDateTime.of(2023, 1, 1, 0, 0);

		MarketDataList a = new MarketDataList();
		a.setRequestTimeStamp(ts);

		MarketDataList b = new MarketDataList();
		b.setRequestTimeStamp(ts);

		Assertions.assertEquals(a, a);
		Assertions.assertEquals(a, b);
		Assertions.assertFalse(a.equals(null));
		Assertions.assertFalse(a.equals("other"));

		b.add(new MarketDataPoint());
		Assertions.assertFalse(a.equals(b));
	}
}