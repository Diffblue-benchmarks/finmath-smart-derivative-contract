/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.valuation.marketdata.data.MarketDataPoint;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for CalibrationDataset.
 * Tests all methods with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class CalibrationDatasetClaudeTest {

	/**
	 * Helper method to create a valid CalibrationDataItem.Spec instance.
	 */
	private CalibrationDataItem.Spec createSpec(String key, String curveName, String productName, String maturity) {
		return new CalibrationDataItem.Spec(key, curveName, productName, maturity);
	}

	/**
	 * Helper method to create a calibration data item (non-fixing).
	 */
	private CalibrationDataItem createCalibrationItem(String curveName, String productName, String maturity,
													  Double quote, LocalDateTime dateTime) {
		CalibrationDataItem.Spec spec = createSpec(
			curveName + "-" + productName + "-" + maturity,
			curveName,
			productName,
			maturity
		);
		return new CalibrationDataItem(spec, quote, dateTime);
	}

	/**
	 * Helper method to create a fixing data item.
	 */
	private CalibrationDataItem createFixingItem(String curveName, String maturity, Double quote, LocalDateTime dateTime) {
		CalibrationDataItem.Spec spec = createSpec(
			curveName + "-Fixing-" + maturity,
			curveName,
			"Fixing",
			maturity
		);
		return new CalibrationDataItem(spec, quote, dateTime);
	}

	/**
	 * Helper method to create a basic dataset.
	 */
	private CalibrationDataset createBasicDataset() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();

		items.add(createCalibrationItem("EUR-EONIA", "SWAP", "1Y", 0.01, scenarioDate));
		items.add(createCalibrationItem("EUR-EONIA", "SWAP", "5Y", 0.02, scenarioDate));
		items.add(createCalibrationItem("EUR-EONIA", "SWAP", "10Y", 0.025, scenarioDate));

		return new CalibrationDataset(items, scenarioDate);
	}

	// Constructor tests

	@Test
	void testConstructor_WithEmptySet() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);

		assertNotNull(dataset);
		assertTrue(dataset.getCalibrationDataItems().isEmpty());
		assertTrue(dataset.getFixingDataItems().isEmpty());
		assertEquals(scenarioDate, dataset.getDate());
	}

	@Test
	void testConstructor_WithOnlyCalibrationItems() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createCalibrationItem("EUR-EONIA", "SWAP", "1Y", 0.01, scenarioDate));
		items.add(createCalibrationItem("EUR-EONIA", "SWAP", "5Y", 0.02, scenarioDate));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);

		assertNotNull(dataset);
		assertEquals(2, dataset.getCalibrationDataItems().size());
		assertTrue(dataset.getFixingDataItems().isEmpty());
		assertEquals(scenarioDate, dataset.getDate());
	}

	@Test
	void testConstructor_WithOnlyFixingItems() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createFixingItem("EUR-EONIA", "0D", 0.001, scenarioDate));
		items.add(createFixingItem("EUR-EONIA", "1D", 0.002, scenarioDate.plusDays(1)));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);

		assertNotNull(dataset);
		assertTrue(dataset.getCalibrationDataItems().isEmpty());
		assertEquals(2, dataset.getFixingDataItems().size());
	}

	@Test
	void testConstructor_WithMixedItems() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createCalibrationItem("EUR-EONIA", "SWAP", "1Y", 0.01, scenarioDate));
		items.add(createFixingItem("EUR-EONIA", "0D", 0.001, scenarioDate));
		items.add(createCalibrationItem("EUR-EONIA", "SWAP", "5Y", 0.02, scenarioDate));
		items.add(createFixingItem("EUR-EONIA", "1D", 0.002, scenarioDate.plusDays(1)));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);

		assertNotNull(dataset);
		assertEquals(2, dataset.getCalibrationDataItems().size());
		assertEquals(2, dataset.getFixingDataItems().size());
	}

	@Test
	void testConstructor_CalibrationItemsSortedByDaysToMaturity() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		// Add in reverse order
		items.add(createCalibrationItem("EUR-EONIA", "SWAP", "10Y", 0.025, scenarioDate));
		items.add(createCalibrationItem("EUR-EONIA", "SWAP", "5Y", 0.02, scenarioDate));
		items.add(createCalibrationItem("EUR-EONIA", "SWAP", "1Y", 0.01, scenarioDate));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);

		List<CalibrationDataItem> sortedItems = new ArrayList<>(dataset.getCalibrationDataItems());
		assertEquals("1Y", sortedItems.get(0).getMaturity());
		assertEquals("5Y", sortedItems.get(1).getMaturity());
		assertEquals("10Y", sortedItems.get(2).getMaturity());
	}

	@Test
	void testConstructor_FixingItemsSortedByDate() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		// Add in reverse chronological order
		items.add(createFixingItem("EUR-EONIA", "2D", 0.003, scenarioDate.plusDays(2)));
		items.add(createFixingItem("EUR-EONIA", "1D", 0.002, scenarioDate.plusDays(1)));
		items.add(createFixingItem("EUR-EONIA", "0D", 0.001, scenarioDate));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);

		List<CalibrationDataItem> sortedFixings = new ArrayList<>(dataset.getFixingDataItems());
		assertEquals(scenarioDate, sortedFixings.get(0).getDateTime());
		assertEquals(scenarioDate.plusDays(1), sortedFixings.get(1).getDateTime());
		assertEquals(scenarioDate.plusDays(2), sortedFixings.get(2).getDateTime());
	}

	// getScaled tests

	@Test
	void testGetScaled_WithFactorTwo() {
		CalibrationDataset dataset = createBasicDataset();

		CalibrationDataset scaled = dataset.getScaled(2.0);

		assertNotNull(scaled);
		assertNotSame(dataset, scaled);
		assertEquals(dataset.getCalibrationDataItems().size(), scaled.getCalibrationDataItems().size());

		// Verify quotes are scaled
		Iterator<CalibrationDataItem> originalIter = dataset.getCalibrationDataItems().iterator();
		Iterator<CalibrationDataItem> scaledIter = scaled.getCalibrationDataItems().iterator();
		while (originalIter.hasNext() && scaledIter.hasNext()) {
			CalibrationDataItem original = originalIter.next();
			CalibrationDataItem scaledItem = scaledIter.next();
			assertEquals(original.getQuote() / 2.0, scaledItem.getQuote(), 1e-10);
		}
	}

	@Test
	void testGetScaled_WithFactorHalf() {
		CalibrationDataset dataset = createBasicDataset();

		CalibrationDataset scaled = dataset.getScaled(0.5);

		assertNotNull(scaled);

		Iterator<CalibrationDataItem> originalIter = dataset.getCalibrationDataItems().iterator();
		Iterator<CalibrationDataItem> scaledIter = scaled.getCalibrationDataItems().iterator();
		while (originalIter.hasNext() && scaledIter.hasNext()) {
			CalibrationDataItem original = originalIter.next();
			CalibrationDataItem scaledItem = scaledIter.next();
			assertEquals(original.getQuote() / 0.5, scaledItem.getQuote(), 1e-10);
		}
	}

	@Test
	void testGetScaled_DoesNotIncludeFixings() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createCalibrationItem("EUR-EONIA", "SWAP", "1Y", 0.01, scenarioDate));
		items.add(createFixingItem("EUR-EONIA", "0D", 0.001, scenarioDate));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);
		CalibrationDataset scaled = dataset.getScaled(2.0);

		assertEquals(1, scaled.getCalibrationDataItems().size());
		assertEquals(0, scaled.getFixingDataItems().size());
	}

	@Test
	void testGetScaled_PreservesScenarioDate() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		CalibrationDataset dataset = createBasicDataset();

		CalibrationDataset scaled = dataset.getScaled(2.0);

		assertEquals(scenarioDate, scaled.getDate());
	}

	@Test
	void testGetScaled_WithEmptyDataset() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);

		CalibrationDataset scaled = dataset.getScaled(2.0);

		assertNotNull(scaled);
		assertTrue(scaled.getCalibrationDataItems().isEmpty());
	}

	// getCalibrationDataItems tests

	@Test
	void testGetCalibrationDataItems_ReturnsCorrectItems() {
		CalibrationDataset dataset = createBasicDataset();

		Set<CalibrationDataItem> items = dataset.getCalibrationDataItems();

		assertNotNull(items);
		assertEquals(3, items.size());
	}

	@Test
	void testGetCalibrationDataItems_DoesNotIncludeFixings() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createCalibrationItem("EUR-EONIA", "SWAP", "1Y", 0.01, scenarioDate));
		items.add(createFixingItem("EUR-EONIA", "0D", 0.001, scenarioDate));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);
		Set<CalibrationDataItem> calibrationItems = dataset.getCalibrationDataItems();

		assertEquals(1, calibrationItems.size());
		calibrationItems.forEach(item -> assertNotEquals("Fixing", item.getProductName()));
	}

	// getFixingDataItems tests

	@Test
	void testGetFixingDataItems_ReturnsCorrectItems() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createFixingItem("EUR-EONIA", "0D", 0.001, scenarioDate));
		items.add(createFixingItem("EUR-EONIA", "1D", 0.002, scenarioDate.plusDays(1)));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);
		Set<CalibrationDataItem> fixingItems = dataset.getFixingDataItems();

		assertNotNull(fixingItems);
		assertEquals(2, fixingItems.size());
		fixingItems.forEach(item -> assertEquals("Fixing", item.getProductName()));
	}

	@Test
	void testGetFixingDataItems_DoesNotIncludeCalibrationItems() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createCalibrationItem("EUR-EONIA", "SWAP", "1Y", 0.01, scenarioDate));
		items.add(createFixingItem("EUR-EONIA", "0D", 0.001, scenarioDate));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);
		Set<CalibrationDataItem> fixingItems = dataset.getFixingDataItems();

		assertEquals(1, fixingItems.size());
		fixingItems.forEach(item -> assertEquals("Fixing", item.getProductName()));
	}

	// getClonedFixingsAdded tests

	@Test
	void testGetClonedFixingsAdded_WithNewFixings() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createCalibrationItem("EUR-EONIA", "SWAP", "1Y", 0.01, scenarioDate));
		items.add(createFixingItem("EUR-EONIA", "0D", 0.001, scenarioDate));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);

		Set<CalibrationDataItem> newFixings = new LinkedHashSet<>();
		newFixings.add(createFixingItem("EUR-EONIA", "1D", 0.002, scenarioDate.plusDays(1)));

		CalibrationDataset cloned = dataset.getClonedFixingsAdded(newFixings);

		assertNotNull(cloned);
		assertNotSame(dataset, cloned);
		assertEquals(1, dataset.getFixingDataItems().size());
		assertEquals(2, cloned.getFixingDataItems().size());
		assertEquals(1, cloned.getCalibrationDataItems().size());
	}

	@Test
	void testGetClonedFixingsAdded_WithDuplicateFixing() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createFixingItem("EUR-EONIA", "0D", 0.001, scenarioDate));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);

		Set<CalibrationDataItem> newFixings = new LinkedHashSet<>();
		// Same curve and date as existing fixing
		newFixings.add(createFixingItem("EUR-EONIA", "0D", 0.003, scenarioDate));

		CalibrationDataset cloned = dataset.getClonedFixingsAdded(newFixings);

		// Duplicate should not be added
		assertEquals(1, cloned.getFixingDataItems().size());
	}

	@Test
	void testGetClonedFixingsAdded_WithNonFixingItem() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createFixingItem("EUR-EONIA", "0D", 0.001, scenarioDate));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);

		Set<CalibrationDataItem> newItems = new LinkedHashSet<>();
		// Not a fixing item
		newItems.add(createCalibrationItem("EUR-EONIA", "SWAP", "1Y", 0.01, scenarioDate));

		CalibrationDataset cloned = dataset.getClonedFixingsAdded(newItems);

		// Non-fixing items should not be added as fixings
		assertEquals(1, cloned.getFixingDataItems().size());
		assertEquals(0, cloned.getCalibrationDataItems().size());
	}

	@Test
	void testGetClonedFixingsAdded_WithDifferentCurve() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createFixingItem("EUR-EONIA", "0D", 0.001, scenarioDate));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);

		Set<CalibrationDataItem> newFixings = new LinkedHashSet<>();
		// Different curve
		newFixings.add(createFixingItem("USD-SOFR", "0D", 0.002, scenarioDate));

		CalibrationDataset cloned = dataset.getClonedFixingsAdded(newFixings);

		// Should be added since it's a different curve
		assertEquals(2, cloned.getFixingDataItems().size());
	}

	@Test
	void testGetClonedFixingsAdded_WithEmptySet() {
		CalibrationDataset dataset = createBasicDataset();
		Set<CalibrationDataItem> newFixings = new LinkedHashSet<>();

		CalibrationDataset cloned = dataset.getClonedFixingsAdded(newFixings);

		assertNotNull(cloned);
		assertEquals(dataset.getCalibrationDataItems().size(), cloned.getCalibrationDataItems().size());
		assertEquals(dataset.getFixingDataItems().size(), cloned.getFixingDataItems().size());
	}

	@Test
	void testGetClonedFixingsAdded_PreservesScenarioDate() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		CalibrationDataset dataset = createBasicDataset();
		Set<CalibrationDataItem> newFixings = new LinkedHashSet<>();
		newFixings.add(createFixingItem("EUR-EONIA", "0D", 0.001, scenarioDate));

		CalibrationDataset cloned = dataset.getClonedFixingsAdded(newFixings);

		assertEquals(scenarioDate, cloned.getDate());
	}

	// toMarketDataList tests

	@Test
	void testToMarketDataList_WithCalibrationItems() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createCalibrationItem("EUR-EONIA", "SWAP", "1Y", 0.01, scenarioDate));
		items.add(createCalibrationItem("EUR-EONIA", "SWAP", "5Y", 0.02, scenarioDate));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);
		MarketDataList marketDataList = dataset.toMarketDataList();

		assertNotNull(marketDataList);
		assertEquals(2, marketDataList.getSize());

		List<MarketDataPoint> points = marketDataList.getPoints();
		assertEquals(0.01, points.get(0).getValue());
		assertEquals(0.02, points.get(1).getValue());
	}

	@Test
	void testToMarketDataList_WithFixingItems() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createFixingItem("EUR-EONIA", "0D", 0.001, scenarioDate));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);
		MarketDataList marketDataList = dataset.toMarketDataList();

		assertNotNull(marketDataList);
		assertEquals(1, marketDataList.getSize());
	}

	@Test
	void testToMarketDataList_WithMixedItems() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createCalibrationItem("EUR-EONIA", "SWAP", "1Y", 0.01, scenarioDate));
		items.add(createFixingItem("EUR-EONIA", "0D", 0.001, scenarioDate));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);
		MarketDataList marketDataList = dataset.toMarketDataList();

		assertNotNull(marketDataList);
		assertEquals(2, marketDataList.getSize());
	}

	@Test
	void testToMarketDataList_WithEmptyDataset() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);

		MarketDataList marketDataList = dataset.toMarketDataList();

		assertNotNull(marketDataList);
		assertEquals(0, marketDataList.getSize());
	}

	@Test
	void testToMarketDataList_MarketDataPointsHaveCorrectAttributes() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		CalibrationDataItem item = createCalibrationItem("EUR-EONIA", "SWAP", "1Y", 0.01, scenarioDate);
		items.add(item);

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);
		MarketDataList marketDataList = dataset.toMarketDataList();

		MarketDataPoint point = marketDataList.getPoints().get(0);
		assertEquals(item.getSpec().getKey(), point.getId());
		assertEquals(item.getQuote(), point.getValue());
		assertEquals(item.getDateTime(), point.getTimeStamp());
	}

	// serializeToJson tests

	@Test
	void testSerializeToJson_WithCalibrationItems() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createCalibrationItem("EUR-EONIA", "SWAP", "1Y", 0.01, scenarioDate));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);
		String json = dataset.serializeToJson();

		assertNotNull(json);
		assertTrue(json.contains("Quotes"));
		assertTrue(json.contains("EUR-EONIA"));
		assertTrue(json.contains("SWAP"));
		assertTrue(json.contains("1Y"));
	}

	@Test
	void testSerializeToJson_WithFixingItems() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createFixingItem("EUR-EONIA", "0D", 0.001, scenarioDate));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);
		String json = dataset.serializeToJson();

		assertNotNull(json);
		assertTrue(json.contains("Fixings"));
		assertTrue(json.contains("EUR-EONIA"));
		assertTrue(json.contains("Fixing"));
	}

	@Test
	void testSerializeToJson_WithEmptyDataset() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);

		String json = dataset.serializeToJson();

		assertNotNull(json);
		assertTrue(json.contains("Quotes"));
		assertTrue(json.contains("Fixings"));
	}

	@Test
	void testSerializeToJson_ReturnsValidJson() {
		CalibrationDataset dataset = createBasicDataset();

		String json = dataset.serializeToJson();

		assertNotNull(json);
		// Check for valid JSON structure markers
		assertTrue(json.trim().startsWith("{"));
		assertTrue(json.trim().endsWith("}"));
	}

	// getDataAsCalibrationDataPointStream tests

	@Test
	void testGetDataAsCalibrationDataPointStream_FiltersFixings() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createCalibrationItem("EUR-EONIA", "SWAP", "1Y", 0.01, scenarioDate));
		items.add(createFixingItem("EUR-EONIA", "0D", 0.001, scenarioDate));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);

		// Create a simple mock parser
		CalibrationParser parser = datapoints -> datapoints.map(item ->
			new CalibrationSpecProvider() {
				@Override
				public net.finmath.marketdata.calibration.CalibratedCurves.CalibrationSpec getCalibrationSpec(CalibrationContext ctx) {
					return null;
				}
			}
		);

		Stream<CalibrationSpecProvider> stream = dataset.getDataAsCalibrationDataPointStream(parser);

		assertNotNull(stream);
		long count = stream.count();
		// Should only include calibration items, not fixings
		assertEquals(1, count);
	}

	@Test
	void testGetDataAsCalibrationDataPointStream_FiltersDeposits() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createCalibrationItem("EUR-EONIA", "SWAP", "1Y", 0.01, scenarioDate));
		items.add(createCalibrationItem("EUR-EONIA", "Deposit", "1D", 0.001, scenarioDate));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);

		CalibrationParser parser = datapoints -> datapoints.map(item ->
			new CalibrationSpecProvider() {
				@Override
				public net.finmath.marketdata.calibration.CalibratedCurves.CalibrationSpec getCalibrationSpec(CalibrationContext ctx) {
					return null;
				}
			}
		);

		Stream<CalibrationSpecProvider> stream = dataset.getDataAsCalibrationDataPointStream(parser);

		long count = stream.count();
		// Should only include SWAP, not Deposit
		assertEquals(1, count);
	}

	@Test
	void testGetDataAsCalibrationDataPointStream_Filters0DMaturity() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createCalibrationItem("EUR-EONIA", "SWAP", "1Y", 0.01, scenarioDate));
		items.add(createCalibrationItem("EUR-EONIA", "SWAP", "0D", 0.001, scenarioDate));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);

		CalibrationParser parser = datapoints -> datapoints.map(item ->
			new CalibrationSpecProvider() {
				@Override
				public net.finmath.marketdata.calibration.CalibratedCurves.CalibrationSpec getCalibrationSpec(CalibrationContext ctx) {
					return null;
				}
			}
		);

		Stream<CalibrationSpecProvider> stream = dataset.getDataAsCalibrationDataPointStream(parser);

		long count = stream.count();
		// Should filter out 0D maturity
		assertEquals(1, count);
	}

	@Test
	void testGetDataAsCalibrationDataPointStream_WithEmptyDataset() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);

		CalibrationParser parser = datapoints -> datapoints.map(item ->
			new CalibrationSpecProvider() {
				@Override
				public net.finmath.marketdata.calibration.CalibratedCurves.CalibrationSpec getCalibrationSpec(CalibrationContext ctx) {
					return null;
				}
			}
		);

		Stream<CalibrationSpecProvider> stream = dataset.getDataAsCalibrationDataPointStream(parser);

		long count = stream.count();
		assertEquals(0, count);
	}

	// getDataPoints tests

	@Test
	void testGetDataPoints_ReturnsBothCalibrationAndFixings() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createCalibrationItem("EUR-EONIA", "SWAP", "1Y", 0.01, scenarioDate));
		items.add(createFixingItem("EUR-EONIA", "0D", 0.001, scenarioDate));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);
		Set<CalibrationDataItem> dataPoints = dataset.getDataPoints();

		assertNotNull(dataPoints);
		assertEquals(2, dataPoints.size());
	}

	@Test
	void testGetDataPoints_WithOnlyCalibrationItems() {
		CalibrationDataset dataset = createBasicDataset();

		Set<CalibrationDataItem> dataPoints = dataset.getDataPoints();

		assertNotNull(dataPoints);
		assertEquals(3, dataPoints.size());
	}

	@Test
	void testGetDataPoints_WithOnlyFixingItems() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createFixingItem("EUR-EONIA", "0D", 0.001, scenarioDate));
		items.add(createFixingItem("EUR-EONIA", "1D", 0.002, scenarioDate.plusDays(1)));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);
		Set<CalibrationDataItem> dataPoints = dataset.getDataPoints();

		assertNotNull(dataPoints);
		assertEquals(2, dataPoints.size());
	}

	@Test
	void testGetDataPoints_WithEmptyDataset() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);

		Set<CalibrationDataItem> dataPoints = dataset.getDataPoints();

		assertNotNull(dataPoints);
		assertEquals(0, dataPoints.size());
	}

	@Test
	void testGetDataPoints_ContainsAllUniqueItems() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		CalibrationDataItem item1 = createCalibrationItem("EUR-EONIA", "SWAP", "1Y", 0.01, scenarioDate);
		CalibrationDataItem item2 = createFixingItem("EUR-EONIA", "0D", 0.001, scenarioDate);
		items.add(item1);
		items.add(item2);

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);
		Set<CalibrationDataItem> dataPoints = dataset.getDataPoints();

		assertTrue(dataPoints.contains(item1));
		assertTrue(dataPoints.contains(item2));
	}

	// getDate tests

	@Test
	void testGetDate_ReturnsScenarioDate() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);

		LocalDateTime returnedDate = dataset.getDate();

		assertEquals(scenarioDate, returnedDate);
	}

	@Test
	void testGetDate_WithDifferentDate() {
		LocalDateTime scenarioDate = LocalDateTime.of(2023, 12, 31, 23, 59, 59);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);

		LocalDateTime returnedDate = dataset.getDate();

		assertEquals(scenarioDate, returnedDate);
	}

	// Additional edge case tests

	@Test
	void testConstructor_WithMultipleCurves() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createCalibrationItem("EUR-EONIA", "SWAP", "1Y", 0.01, scenarioDate));
		items.add(createCalibrationItem("USD-SOFR", "SWAP", "1Y", 0.015, scenarioDate));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);

		assertEquals(2, dataset.getCalibrationDataItems().size());
	}

	@Test
	void testGetScaled_WithNegativeFactor() {
		CalibrationDataset dataset = createBasicDataset();

		CalibrationDataset scaled = dataset.getScaled(-2.0);

		assertNotNull(scaled);
		// Verify quotes have correct signs
		for (CalibrationDataItem item : scaled.getCalibrationDataItems()) {
			assertTrue(item.getQuote() < 0);
		}
	}

	@Test
	void testGetClonedFixingsAdded_WithMultipleNewFixingsSameCurveDifferentDates() {
		LocalDateTime scenarioDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createFixingItem("EUR-EONIA", "0D", 0.001, scenarioDate));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);

		Set<CalibrationDataItem> newFixings = new LinkedHashSet<>();
		newFixings.add(createFixingItem("EUR-EONIA", "1D", 0.002, scenarioDate.plusDays(1)));
		newFixings.add(createFixingItem("EUR-EONIA", "2D", 0.003, scenarioDate.plusDays(2)));

		CalibrationDataset cloned = dataset.getClonedFixingsAdded(newFixings);

		assertEquals(3, cloned.getFixingDataItems().size());
	}
}
