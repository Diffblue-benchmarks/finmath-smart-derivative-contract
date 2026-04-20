package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import net.finmath.smartcontract.model.MarketDataList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CalibrationDatasetTest {

	private CalibrationDataset dataset;
	private LocalDateTime scenarioDate;
	private CalibrationDataItem swapItem;
	private CalibrationDataItem fixingItem;
	private CalibrationDataItem fixingItem2;

	@BeforeAll
	void setUp() {
		scenarioDate = LocalDateTime.of(2023, 5, 5, 14, 35, 23);

		CalibrationDataItem.Spec swapSpec = new CalibrationDataItem.Spec("EUR6M10Y", "Euribor6M", "Swap", "10Y");
		swapItem = new CalibrationDataItem(swapSpec, 0.035, scenarioDate);

		CalibrationDataItem.Spec fraSpec = new CalibrationDataItem.Spec("EUR6M7M", "Euribor6M", "FRA", "7M");
		CalibrationDataItem fraItem = new CalibrationDataItem(fraSpec, 0.032, scenarioDate);

		CalibrationDataItem.Spec fixingSpec = new CalibrationDataItem.Spec("ESTR_FIX1", "ESTR", "Fixing", "1D");
		fixingItem = new CalibrationDataItem(fixingSpec, 0.031, LocalDateTime.of(2023, 5, 4, 13, 0, 0));

		CalibrationDataItem.Spec fixingSpec2 = new CalibrationDataItem.Spec("ESTR_FIX2", "ESTR", "Fixing", "1D");
		fixingItem2 = new CalibrationDataItem(fixingSpec2, 0.030, LocalDateTime.of(2023, 5, 3, 13, 0, 0));

		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(swapItem);
		items.add(fraItem);
		items.add(fixingItem);
		items.add(fixingItem2);

		dataset = new CalibrationDataset(items, scenarioDate);
	}

	@Test
	void testGetCalibrationDataItems() {
		Set<CalibrationDataItem> calibrationItems = dataset.getCalibrationDataItems();
		assertNotNull(calibrationItems);
		assertEquals(2, calibrationItems.size());
		assertTrue(calibrationItems.stream().noneMatch(item -> item.getProductName().equals("Fixing")));
	}

	@Test
	void testGetClonedFixingsAdded() {
		CalibrationDataItem.Spec newFixingSpec = new CalibrationDataItem.Spec("ESTR_FIX3", "ESTR", "Fixing", "1D");
		CalibrationDataItem newFixing = new CalibrationDataItem(newFixingSpec, 0.029, LocalDateTime.of(2023, 5, 2, 13, 0, 0));

		Set<CalibrationDataItem> newFixings = new LinkedHashSet<>();
		newFixings.add(newFixing);

		CalibrationDataset cloned = dataset.getClonedFixingsAdded(newFixings);
		assertNotNull(cloned);
		assertEquals(3, cloned.getFixingDataItems().size());
		assertEquals(2, cloned.getCalibrationDataItems().size());
	}

	@Test
	void testGetClonedFixingsAddedDuplicateIgnored() {
		Set<CalibrationDataItem> duplicateFixings = new LinkedHashSet<>();
		duplicateFixings.add(fixingItem);

		CalibrationDataset cloned = dataset.getClonedFixingsAdded(duplicateFixings);
		assertEquals(2, cloned.getFixingDataItems().size());
	}

	@Test
	void testGetClonedFixingsAddedNonFixingIgnored() {
		CalibrationDataItem.Spec nonFixingSpec = new CalibrationDataItem.Spec("SWAP1", "Euribor6M", "Swap", "5Y");
		CalibrationDataItem nonFixing = new CalibrationDataItem(nonFixingSpec, 0.04, scenarioDate);

		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(nonFixing);

		CalibrationDataset cloned = dataset.getClonedFixingsAdded(items);
		assertEquals(2, cloned.getFixingDataItems().size());
		assertEquals(2, cloned.getCalibrationDataItems().size());
	}

	@Test
	void testToMarketDataList() {
		MarketDataList marketDataList = dataset.toMarketDataList();
		assertNotNull(marketDataList);
		assertEquals(4, marketDataList.getSize());
	}

	@Test
	void testSerializeToJson() {
		String json = dataset.serializeToJson();
		assertNotNull(json);
		assertTrue(json.contains("Quotes"));
		assertTrue(json.contains("Fixings"));
		assertTrue(json.contains("Euribor6M"));
		assertTrue(json.contains("ESTR"));
	}

	@Test
	void testSerializeToJsonWithEmptyDataset() {
		CalibrationDataset emptyDataset = new CalibrationDataset(new LinkedHashSet<>(), scenarioDate);
		String json = emptyDataset.serializeToJson();
		assertNotNull(json);
		assertTrue(json.contains("Quotes"));
		assertTrue(json.contains("Fixings"));
	}
}
