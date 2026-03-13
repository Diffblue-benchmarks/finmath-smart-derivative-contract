package net.finmath.smartcontract.model;

import net.finmath.smartcontract.valuation.marketdata.data.MarketDataPoint;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MarketDataListTest {

	@Test
	void newMarketDataList_shouldBeEmpty() {
		MarketDataList list = new MarketDataList();
		assertEquals(0, list.getSize());
		assertNotNull(list.getPoints());
		assertNotNull(list.getRequestTimeStamp());
	}

	@Test
	void add_shouldIncreaseSize() {
		MarketDataList list = new MarketDataList();
		list.add(new MarketDataPoint("EUR6M_1Y", 0.03, LocalDateTime.of(2024, 1, 15, 10, 0)));
		assertEquals(1, list.getSize());
	}

	@Test
	void setPoints_shouldReplacePoints() {
		MarketDataList list = new MarketDataList();
		MarketDataPoint p1 = new MarketDataPoint("id1", 1.0, LocalDateTime.of(2024, 1, 1, 0, 0));
		MarketDataPoint p2 = new MarketDataPoint("id2", 2.0, LocalDateTime.of(2024, 1, 1, 0, 0));
		list.setPoints(List.of(p1, p2));
		assertEquals(2, list.getSize());
	}

	@Test
	void serializeToJson_shouldReturnNonEmptyString() {
		MarketDataList list = new MarketDataList();
		list.add(new MarketDataPoint("EUR6M_1Y", 0.03, LocalDateTime.of(2024, 1, 15, 10, 0)));
		String json = list.serializeToJson();
		assertNotNull(json);
		assertFalse(json.isEmpty());
		assertTrue(json.contains("EUR6M_1Y"));
	}

	@Test
	void equals_shouldBeTrue_forSameContent() {
		LocalDateTime now = LocalDateTime.of(2024, 6, 15, 12, 0);
		MarketDataList list1 = new MarketDataList();
		list1.setRequestTimeStamp(now);
		list1.add(new MarketDataPoint("id1", 1.0, now));

		MarketDataList list2 = new MarketDataList();
		list2.setRequestTimeStamp(now);
		list2.add(new MarketDataPoint("id1", 1.0, now));

		assertEquals(list1, list2);
	}

	@Test
	void equals_shouldBeFalse_forDifferentContent() {
		LocalDateTime now = LocalDateTime.of(2024, 6, 15, 12, 0);
		MarketDataList list1 = new MarketDataList();
		list1.setRequestTimeStamp(now);
		list1.add(new MarketDataPoint("id1", 1.0, now));

		MarketDataList list2 = new MarketDataList();
		list2.setRequestTimeStamp(now);
		list2.add(new MarketDataPoint("id2", 2.0, now));

		assertNotEquals(list1, list2);
	}

	@Test
	void toString_shouldContainClassInfo() {
		MarketDataList list = new MarketDataList();
		String str = list.toString();
		assertTrue(str.contains("MarketDataList"));
	}

	@Test
	void equals_shouldBeFalse_forNull() {
		MarketDataList list = new MarketDataList();
		assertNotEquals(null, list);
	}

	@Test
	void equals_shouldBeFalse_forDifferentType() {
		MarketDataList list = new MarketDataList();
		assertNotEquals("not a list", list);
	}

	@Test
	void equals_shouldBeTrue_forSameReference() {
		MarketDataList list = new MarketDataList();
		assertEquals(list, list);
	}

	@Test
	void mapToCalibrationDataSet_shouldReturnNull() {
		MarketDataList list = new MarketDataList();
		assertNull(list.mapToCalibrationDataSet());
	}
}
