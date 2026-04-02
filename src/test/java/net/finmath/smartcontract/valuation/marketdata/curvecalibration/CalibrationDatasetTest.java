package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import net.finmath.smartcontract.model.MarketDataList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

class CalibrationDatasetTest {

	private static CalibrationDataItem makeItem(String curveName, String productName, String maturity, double quote, LocalDateTime dateTime) {
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec(curveName + "-" + productName + "-" + maturity, curveName, productName, maturity);
		return new CalibrationDataItem(spec, quote, dateTime);
	}

	private static CalibrationDataset buildDataset() {
		LocalDateTime dt = LocalDateTime.of(2023, 6, 1, 0, 0);
		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(makeItem("EUR-6M", "Swap", "1Y", 0.02, dt));
		items.add(makeItem("EUR-6M", "Swap", "2Y", 0.025, dt));
		items.add(makeItem("Euribor6M", "Fixing", "2023-01-01", 0.01, dt.minusMonths(5)));
		return new CalibrationDataset(items, dt);
	}

	@Test
	void testGetScaled() {
		CalibrationDataset dataset = buildDataset();
		CalibrationDataset scaled = dataset.getScaled(2.0);

		Assertions.assertNotNull(scaled);
		scaled.calibrationDataItems.forEach(item ->
				dataset.calibrationDataItems.stream()
						.filter(orig -> orig.getSpec().equals(item.getSpec()))
						.findFirst()
						.ifPresent(orig -> Assertions.assertEquals(orig.getQuote() / 2.0, item.getQuote(), 1e-10))
		);
	}

	@Test
	void testGetFixingDataItems() {
		CalibrationDataset dataset = buildDataset();
		Set<CalibrationDataItem> fixings = dataset.getFixingDataItems();

		Assertions.assertNotNull(fixings);
		Assertions.assertEquals(1, fixings.size());
		fixings.forEach(item -> Assertions.assertEquals("Fixing", item.getProductName()));
	}

	@Test
	void testGetClonedFixingsAdded_addsNewFixing() {
		LocalDateTime dt = LocalDateTime.of(2023, 6, 1, 0, 0);
		CalibrationDataset dataset = buildDataset();

		Set<CalibrationDataItem> newFixings = new LinkedHashSet<>();
		newFixings.add(makeItem("Euribor6M", "Fixing", "2023-02-01", 0.012, dt.minusMonths(4)));

		CalibrationDataset cloned = dataset.getClonedFixingsAdded(newFixings);

		Assertions.assertNotNull(cloned);
		Assertions.assertEquals(2, cloned.getFixingDataItems().size());
	}

	@Test
	void testGetClonedFixingsAdded_doesNotAddDuplicateFixing() {
		CalibrationDataset dataset = buildDataset();

		Set<CalibrationDataItem> existingFixings = dataset.getFixingDataItems();

		CalibrationDataset cloned = dataset.getClonedFixingsAdded(existingFixings);

		Assertions.assertNotNull(cloned);
		Assertions.assertEquals(dataset.getFixingDataItems().size(), cloned.getFixingDataItems().size());
	}

	@Test
	void testGetClonedFixingsAdded_ignoresNonFixingItems() {
		LocalDateTime dt = LocalDateTime.of(2023, 6, 1, 0, 0);
		CalibrationDataset dataset = buildDataset();

		Set<CalibrationDataItem> nonFixings = new LinkedHashSet<>();
		nonFixings.add(makeItem("EUR-6M", "Swap", "5Y", 0.03, dt));

		CalibrationDataset cloned = dataset.getClonedFixingsAdded(nonFixings);

		Assertions.assertEquals(dataset.getFixingDataItems().size(), cloned.getFixingDataItems().size());
	}

	@Test
	void testToMarketDataList() {
		CalibrationDataset dataset = buildDataset();
		MarketDataList marketDataList = dataset.toMarketDataList();

		Assertions.assertNotNull(marketDataList);
		int expectedSize = dataset.calibrationDataItems.size() + dataset.fixingDataItems.size();
		Assertions.assertEquals(expectedSize, marketDataList.getSize());
	}

	@Test
	void testSerializeToJson() {
		CalibrationDataset dataset = buildDataset();
		String json = dataset.serializeToJson();

		Assertions.assertNotNull(json);
		Assertions.assertTrue(json.contains("Quotes"));
		Assertions.assertTrue(json.contains("Fixings"));
	}
}
