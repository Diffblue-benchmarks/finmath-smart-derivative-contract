package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import net.finmath.smartcontract.model.MarketDataList;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CalibrationDatasetTest {

	private final LocalDateTime scenarioDate = LocalDateTime.of(2024, 6, 15, 17, 0);

	private CalibrationDataItem createItem(String curve, String product, String maturity, double quote) {
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec(
				curve + "_" + product + "_" + maturity, curve, product, maturity);
		return new CalibrationDataItem(spec, quote, scenarioDate);
	}

	@Test
	void constructor_shouldSeparateFixingsFromCalibrationItems() {
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createItem("Euribor6M", "Swap-Rate", "5Y", 0.03));
		items.add(createItem("Euribor6M", "Fixing", "1D", 0.025));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);

		assertEquals(1, dataset.getCalibrationDataItems().size());
		assertEquals(1, dataset.getFixingDataItems().size());
	}

	@Test
	void getScaled_shouldScaleCalibrationItems() {
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createItem("Euribor6M", "Swap-Rate", "5Y", 100.0));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);
		CalibrationDataset scaled = dataset.getScaled(2.0);

		CalibrationDataItem scaledItem = scaled.getCalibrationDataItems().iterator().next();
		assertEquals(50.0, scaledItem.getQuote());
	}

	@Test
	void getDate_shouldReturnScenarioDate() {
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createItem("Euribor6M", "Swap-Rate", "5Y", 0.03));
		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);
		assertEquals(scenarioDate, dataset.getDate());
	}

	@Test
	void getDataPoints_shouldReturnAllItems() {
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createItem("Euribor6M", "Swap-Rate", "5Y", 0.03));
		items.add(createItem("Euribor6M", "Fixing", "1D", 0.025));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);
		assertEquals(2, dataset.getDataPoints().size());
	}

	@Test
	void toMarketDataList_shouldConvertAllItems() {
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createItem("Euribor6M", "Swap-Rate", "5Y", 0.03));
		items.add(createItem("Euribor6M", "Fixing", "1D", 0.025));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);
		MarketDataList mdl = dataset.toMarketDataList();

		assertEquals(2, mdl.getSize());
	}

	@Test
	void serializeToJson_shouldReturnValidJson() {
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createItem("Euribor6M", "Swap-Rate", "5Y", 0.03));
		items.add(createItem("Euribor6M", "Swap-Rate", "10Y", 0.04));
		items.add(createItem("ESTR", "Fixing", "1D", 0.025));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);
		String json = dataset.serializeToJson();
		assertNotNull(json);
		assertTrue(json.contains("Quotes"));
		assertTrue(json.contains("Euribor6M"));
		assertTrue(json.contains("Swap-Rate"));
	}

	@Test
	void serializeToJson_shouldIncludeFixings() {
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createItem("Euribor6M", "Swap-Rate", "5Y", 0.03));
		items.add(createItem("ESTR", "Fixing", "1D", 0.025));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);
		String json = dataset.serializeToJson();
		assertNotNull(json);
		assertTrue(json.contains("Fixings"));
	}

	@Test
	void getDataAsCalibrationDataPointStream_shouldFilterOutFixingsAndDepositsAndZeroD() {
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createItem("Euribor6M", "Swap-Rate", "5Y", 0.03));
		items.add(createItem("Euribor6M", "Deposit", "6M", 0.025));
		items.add(createItem("Euribor6M", "Fixing", "1D", 0.02));
		items.add(createItem("Euribor6M", "Swap-Rate", "0D", 0.01));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);
		CalibrationParserDataItems parser = new CalibrationParserDataItems();
		Stream<CalibrationSpecProvider> specs = dataset.getDataAsCalibrationDataPointStream(parser);
		// Only "Swap-Rate 5Y" should pass filters (Deposit, Fixing, 0D are all filtered out)
		assertEquals(1, specs.count());
	}

	@Test
	void getClonedFixingsAdded_shouldNotAddDuplicateFixings() {
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		CalibrationDataItem.Spec fixSpec = new CalibrationDataItem.Spec("fix_key", "ESTR", "Fixing", "1D");
		items.add(new CalibrationDataItem(fixSpec, 0.01, LocalDateTime.of(2024, 6, 14, 0, 0)));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);

		// Adding a fixing with the same curve and date should be ignored
		Set<CalibrationDataItem> newFixings = new LinkedHashSet<>();
		CalibrationDataItem.Spec sameFixSpec = new CalibrationDataItem.Spec("fix_key2", "ESTR", "Fixing", "1D");
		newFixings.add(new CalibrationDataItem(sameFixSpec, 0.015, LocalDateTime.of(2024, 6, 14, 0, 0)));

		CalibrationDataset withFixings = dataset.getClonedFixingsAdded(newFixings);
		assertEquals(1, withFixings.getFixingDataItems().size());
	}

	@Test
	void getClonedFixingsAdded_shouldIgnoreNonFixingItems() {
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createItem("Euribor6M", "Swap-Rate", "5Y", 0.03));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);

		Set<CalibrationDataItem> newFixings = new LinkedHashSet<>();
		// This is not a fixing, it should be ignored
		newFixings.add(createItem("Euribor6M", "Swap-Rate", "10Y", 0.04));

		CalibrationDataset withFixings = dataset.getClonedFixingsAdded(newFixings);
		assertEquals(0, withFixings.getFixingDataItems().size());
	}

	@Test
	void getClonedFixingsAdded_shouldAddNewFixings() {
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(createItem("Euribor6M", "Swap-Rate", "5Y", 0.03));

		CalibrationDataset dataset = new CalibrationDataset(items, scenarioDate);

		Set<CalibrationDataItem> newFixings = new LinkedHashSet<>();
		CalibrationDataItem.Spec fixSpec = new CalibrationDataItem.Spec("fix_key", "ESTR", "Fixing", "1D");
		newFixings.add(new CalibrationDataItem(fixSpec, 0.01, LocalDateTime.of(2024, 6, 14, 0, 0)));

		CalibrationDataset withFixings = dataset.getClonedFixingsAdded(newFixings);
		assertEquals(1, withFixings.getFixingDataItems().size());
		assertEquals(1, withFixings.getCalibrationDataItems().size());
	}
}
