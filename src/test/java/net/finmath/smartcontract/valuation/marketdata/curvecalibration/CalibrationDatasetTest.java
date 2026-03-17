package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.valuation.marketdata.data.MarketDataPoint;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CalibrationDatasetTest {

	@Test
	void testGetFixingDataItems() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		Set<CalibrationDataItem> dataItems = new LinkedHashSet<>();

		CalibrationDataItem.Spec fixingSpec = new CalibrationDataItem.Spec("EUR-ESTR", "EUR-ESTR", "Fixing", "1D");
		CalibrationDataItem fixingItem = new CalibrationDataItem(fixingSpec, 0.035, scenarioDate);
		dataItems.add(fixingItem);

		CalibrationDataItem.Spec swapSpec = new CalibrationDataItem.Spec("EUR-ESTR-SWAP-5Y", "EUR-ESTR", "Swap", "5Y");
		CalibrationDataItem swapItem = new CalibrationDataItem(swapSpec, 0.025, scenarioDate);
		dataItems.add(swapItem);

		CalibrationDataset dataset = new CalibrationDataset(dataItems, scenarioDate);

		Set<CalibrationDataItem> fixings = dataset.getFixingDataItems();

		assertNotNull(fixings);
		assertEquals(1, fixings.size());
		assertTrue(fixings.contains(fixingItem));
		assertFalse(fixings.contains(swapItem));
	}

	@Test
	void testGetClonedFixingsAdded() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		Set<CalibrationDataItem> dataItems = new LinkedHashSet<>();

		CalibrationDataItem.Spec fixingSpec1 = new CalibrationDataItem.Spec("EUR-ESTR-FIX1", "EUR-ESTR", "Fixing", "1D");
		CalibrationDataItem fixingItem1 = new CalibrationDataItem(fixingSpec1, 0.035, scenarioDate);
		dataItems.add(fixingItem1);

		CalibrationDataItem.Spec swapSpec = new CalibrationDataItem.Spec("EUR-ESTR-SWAP-5Y", "EUR-ESTR", "Swap", "5Y");
		CalibrationDataItem swapItem = new CalibrationDataItem(swapSpec, 0.025, scenarioDate);
		dataItems.add(swapItem);

		CalibrationDataset dataset = new CalibrationDataset(dataItems, scenarioDate);

		Set<CalibrationDataItem> newFixings = new LinkedHashSet<>();
		CalibrationDataItem.Spec fixingSpec2 = new CalibrationDataItem.Spec("EUR-ESTR-FIX2", "EUR-ESTR", "Fixing", "2D");
		CalibrationDataItem fixingItem2 = new CalibrationDataItem(fixingSpec2, 0.036, scenarioDate.plusDays(1));
		newFixings.add(fixingItem2);

		CalibrationDataset clonedDataset = dataset.getClonedFixingsAdded(newFixings);

		assertNotNull(clonedDataset);
		Set<CalibrationDataItem> allItems = clonedDataset.getDataPoints();
		assertEquals(3, allItems.size());
		assertTrue(allItems.contains(fixingItem1));
		assertTrue(allItems.contains(fixingItem2));
		assertTrue(allItems.contains(swapItem));
	}

	@Test
	void testGetClonedFixingsAddedWithDuplicateDate() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		Set<CalibrationDataItem> dataItems = new LinkedHashSet<>();

		CalibrationDataItem.Spec fixingSpec1 = new CalibrationDataItem.Spec("EUR-ESTR-FIX1", "EUR-ESTR", "Fixing", "1D");
		CalibrationDataItem fixingItem1 = new CalibrationDataItem(fixingSpec1, 0.035, scenarioDate);
		dataItems.add(fixingItem1);

		CalibrationDataset dataset = new CalibrationDataset(dataItems, scenarioDate);

		Set<CalibrationDataItem> newFixings = new LinkedHashSet<>();
		CalibrationDataItem.Spec fixingSpec2 = new CalibrationDataItem.Spec("EUR-ESTR-FIX1-DUP", "EUR-ESTR", "Fixing", "1D");
		CalibrationDataItem fixingItem2 = new CalibrationDataItem(fixingSpec2, 0.036, scenarioDate);
		newFixings.add(fixingItem2);

		CalibrationDataset clonedDataset = dataset.getClonedFixingsAdded(newFixings);

		assertNotNull(clonedDataset);
		Set<CalibrationDataItem> allItems = clonedDataset.getDataPoints();
		assertEquals(1, allItems.size());
	}

	@Test
	void testGetClonedFixingsAddedWithNonFixingItem() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		Set<CalibrationDataItem> dataItems = new LinkedHashSet<>();

		CalibrationDataItem.Spec fixingSpec = new CalibrationDataItem.Spec("EUR-ESTR-FIX1", "EUR-ESTR", "Fixing", "1D");
		CalibrationDataItem fixingItem = new CalibrationDataItem(fixingSpec, 0.035, scenarioDate);
		dataItems.add(fixingItem);

		CalibrationDataset dataset = new CalibrationDataset(dataItems, scenarioDate);

		Set<CalibrationDataItem> newItems = new LinkedHashSet<>();
		CalibrationDataItem.Spec swapSpec = new CalibrationDataItem.Spec("EUR-ESTR-SWAP-5Y", "EUR-ESTR", "Swap", "5Y");
		CalibrationDataItem swapItem = new CalibrationDataItem(swapSpec, 0.025, scenarioDate);
		newItems.add(swapItem);

		CalibrationDataset clonedDataset = dataset.getClonedFixingsAdded(newItems);

		assertNotNull(clonedDataset);
		Set<CalibrationDataItem> allItems = clonedDataset.getDataPoints();
		assertEquals(1, allItems.size());
		assertTrue(allItems.contains(fixingItem));
	}

	@Test
	void testToMarketDataList() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		Set<CalibrationDataItem> dataItems = new LinkedHashSet<>();

		CalibrationDataItem.Spec fixingSpec = new CalibrationDataItem.Spec("EUR-ESTR-FIX", "EUR-ESTR", "Fixing", "1D");
		CalibrationDataItem fixingItem = new CalibrationDataItem(fixingSpec, 0.035, scenarioDate);
		dataItems.add(fixingItem);

		CalibrationDataItem.Spec swapSpec = new CalibrationDataItem.Spec("EUR-ESTR-SWAP-5Y", "EUR-ESTR", "Swap", "5Y");
		CalibrationDataItem swapItem = new CalibrationDataItem(swapSpec, 0.025, scenarioDate.plusDays(1));
		dataItems.add(swapItem);

		CalibrationDataset dataset = new CalibrationDataset(dataItems, scenarioDate);

		MarketDataList marketDataList = dataset.toMarketDataList();

		assertNotNull(marketDataList);
		assertEquals(2, marketDataList.getSize());

		MarketDataPoint point1 = marketDataList.getPoints().get(0);
		assertEquals("EUR-ESTR-SWAP-5Y", point1.getId());
		assertEquals(0.025, point1.getValue());
		assertEquals(scenarioDate.plusDays(1), point1.getTimeStamp());

		MarketDataPoint point2 = marketDataList.getPoints().get(1);
		assertEquals("EUR-ESTR-FIX", point2.getId());
		assertEquals(0.035, point2.getValue());
		assertEquals(scenarioDate, point2.getTimeStamp());
	}

	@Test
	void testToMarketDataListEmpty() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		Set<CalibrationDataItem> dataItems = new LinkedHashSet<>();

		CalibrationDataset dataset = new CalibrationDataset(dataItems, scenarioDate);

		MarketDataList marketDataList = dataset.toMarketDataList();

		assertNotNull(marketDataList);
		assertEquals(0, marketDataList.getSize());
	}

	@Test
	void testSerializeToJson() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		Set<CalibrationDataItem> dataItems = new LinkedHashSet<>();

		CalibrationDataItem.Spec fixingSpec = new CalibrationDataItem.Spec("EUR-ESTR-FIX", "EUR-ESTR", "Fixing", "1D");
		CalibrationDataItem fixingItem = new CalibrationDataItem(fixingSpec, 0.035, scenarioDate);
		dataItems.add(fixingItem);

		CalibrationDataItem.Spec swapSpec = new CalibrationDataItem.Spec("EUR-ESTR-SWAP-5Y", "EUR-ESTR", "Swap", "5Y");
		CalibrationDataItem swapItem = new CalibrationDataItem(swapSpec, 0.025, scenarioDate.plusDays(1));
		dataItems.add(swapItem);

		CalibrationDataset dataset = new CalibrationDataset(dataItems, scenarioDate);

		String json = dataset.serializeToJson();

		assertNotNull(json);
		assertFalse(json.isEmpty());
		assertTrue(json.contains("Quotes"));
		assertTrue(json.contains("Fixings"));
		assertTrue(json.contains("EUR-ESTR"));
		assertTrue(json.contains("Swap"));
		assertTrue(json.contains("5Y"));
		assertTrue(json.contains("0.025"));
		assertTrue(json.contains("0.035"));
	}

	@Test
	void testSerializeToJsonEmpty() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		Set<CalibrationDataItem> dataItems = new LinkedHashSet<>();

		CalibrationDataset dataset = new CalibrationDataset(dataItems, scenarioDate);

		String json = dataset.serializeToJson();

		assertNotNull(json);
		assertFalse(json.isEmpty());
		assertTrue(json.contains("Quotes"));
		assertTrue(json.contains("Fixings"));
	}

	@Test
	void testSerializeToJsonMultipleCurves() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		Set<CalibrationDataItem> dataItems = new LinkedHashSet<>();

		CalibrationDataItem.Spec fixingSpec1 = new CalibrationDataItem.Spec("EUR-ESTR-FIX", "EUR-ESTR", "Fixing", "1D");
		CalibrationDataItem fixingItem1 = new CalibrationDataItem(fixingSpec1, 0.035, scenarioDate);
		dataItems.add(fixingItem1);

		CalibrationDataItem.Spec fixingSpec2 = new CalibrationDataItem.Spec("USD-SOFR-FIX", "USD-SOFR", "Fixing", "1D");
		CalibrationDataItem fixingItem2 = new CalibrationDataItem(fixingSpec2, 0.045, scenarioDate);
		dataItems.add(fixingItem2);

		CalibrationDataItem.Spec swapSpec1 = new CalibrationDataItem.Spec("EUR-ESTR-SWAP-5Y", "EUR-ESTR", "Swap", "5Y");
		CalibrationDataItem swapItem1 = new CalibrationDataItem(swapSpec1, 0.025, scenarioDate.plusDays(1));
		dataItems.add(swapItem1);

		CalibrationDataItem.Spec swapSpec2 = new CalibrationDataItem.Spec("EUR-ESTR-SWAP-10Y", "EUR-ESTR", "Swap", "10Y");
		CalibrationDataItem swapItem2 = new CalibrationDataItem(swapSpec2, 0.03, scenarioDate.plusDays(1));
		dataItems.add(swapItem2);

		CalibrationDataset dataset = new CalibrationDataset(dataItems, scenarioDate);

		String json = dataset.serializeToJson();

		assertNotNull(json);
		assertFalse(json.isEmpty());
		assertTrue(json.contains("EUR-ESTR"));
		assertTrue(json.contains("USD-SOFR"));
		assertTrue(json.contains("5Y"));
		assertTrue(json.contains("10Y"));
		assertTrue(json.contains("0.025"));
		assertTrue(json.contains("0.03"));
		assertTrue(json.contains("0.035"));
		assertTrue(json.contains("0.045"));
	}
}
