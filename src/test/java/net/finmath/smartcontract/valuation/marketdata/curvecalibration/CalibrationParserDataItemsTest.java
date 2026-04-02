package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import net.finmath.smartcontract.model.SDCException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

class CalibrationParserDataItemsTest {

	// ---- parseDatapointIfPresent (via parse()) ----

	@Test
	void parseReturnsEmptyForEstrNonSwapRate() {
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("k", "ESTR", "Deposit", "1Y");
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.01, LocalDateTime.now());

		CalibrationParserDataItems parser = new CalibrationParserDataItems();
		List<CalibrationSpecProvider> result = parser.parse(Stream.of(item)).toList();

		Assertions.assertTrue(result.isEmpty());
	}

	@Test
	void parseReturnsEmptyForEoniaNonSwapRate() {
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("k", "EONIA", "Forward-Rate-Agreement", "3M");
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.02, LocalDateTime.now());

		CalibrationParserDataItems parser = new CalibrationParserDataItems();
		List<CalibrationSpecProvider> result = parser.parse(Stream.of(item)).toList();

		Assertions.assertTrue(result.isEmpty());
	}

	@Test
	void parseReturnsFRAForEuribor6MFra() {
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("k", "Euribor6M", "Forward-Rate-Agreement", "6M");
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.03, LocalDateTime.now());

		CalibrationParserDataItems parser = new CalibrationParserDataItems();
		List<CalibrationSpecProvider> result = parser.parse(Stream.of(item)).toList();

		Assertions.assertEquals(1, result.size());
		Assertions.assertInstanceOf(CalibrationSpecProviderFRA.class, result.get(0));
	}

	@Test
	void parseReturnsDepositForEuribor6MDeposit() {
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("k", "Euribor6M", "Deposit", "6M");
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.03, LocalDateTime.now());

		CalibrationParserDataItems parser = new CalibrationParserDataItems();
		List<CalibrationSpecProvider> result = parser.parse(Stream.of(item)).toList();

		Assertions.assertEquals(1, result.size());
		Assertions.assertInstanceOf(CalibrationSpecProviderDeposit.class, result.get(0));
	}

	@Test
	void parseReturnsDepositForEuribor6MDepositRate() {
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("k", "Euribor6M", "Deposit-Rate", "6M");
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.03, LocalDateTime.now());

		CalibrationParserDataItems parser = new CalibrationParserDataItems();
		List<CalibrationSpecProvider> result = parser.parse(Stream.of(item)).toList();

		Assertions.assertEquals(1, result.size());
		Assertions.assertInstanceOf(CalibrationSpecProviderDeposit.class, result.get(0));
	}

	@Test
	void parseReturnsEmptyForEuribor6MUnknownProduct() {
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("k", "Euribor6M", "UnknownProduct", "6M");
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.03, LocalDateTime.now());

		CalibrationParserDataItems parser = new CalibrationParserDataItems();
		List<CalibrationSpecProvider> result = parser.parse(Stream.of(item)).toList();

		Assertions.assertTrue(result.isEmpty());
	}

	@Test
	void parseReturnsSwapForEuribor1M() {
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("k", "Euribor1M", "Swap-Rate", "1Y");
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.01, LocalDateTime.now());

		CalibrationParserDataItems parser = new CalibrationParserDataItems();
		List<CalibrationSpecProvider> result = parser.parse(Stream.of(item)).toList();

		Assertions.assertEquals(1, result.size());
		Assertions.assertInstanceOf(CalibrationSpecProviderSwap.class, result.get(0));
	}

	@Test
	void parseReturnsSwapForEuribor3M() {
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("k", "Euribor3M", "Swap-Rate", "2Y");
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.02, LocalDateTime.now());

		CalibrationParserDataItems parser = new CalibrationParserDataItems();
		List<CalibrationSpecProvider> result = parser.parse(Stream.of(item)).toList();

		Assertions.assertEquals(1, result.size());
		Assertions.assertInstanceOf(CalibrationSpecProviderSwap.class, result.get(0));
	}

	@Test
	void parseReturnsEmptyForUnknownCurve() {
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("k", "UnknownCurve", "Swap-Rate", "5Y");
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.05, LocalDateTime.now());

		CalibrationParserDataItems parser = new CalibrationParserDataItems();
		List<CalibrationSpecProvider> result = parser.parse(Stream.of(item)).toList();

		Assertions.assertTrue(result.isEmpty());
	}

	// ---- getScenariosFromJsonFile ----

	@Test
	void getScenariosFromJsonFileLoadsData() throws IOException {
		List<CalibrationDataset> scenarios = CalibrationParserDataItems.getScenariosFromJsonFile("timeseriesdatamap.json");

		Assertions.assertFalse(scenarios.isEmpty());
	}

	@Test
	void getScenariosFromJsonFileThrowsForMissingFile() {
		Assertions.assertThrows(Exception.class, () ->
				CalibrationParserDataItems.getScenariosFromJsonFile("nonexistent_file_xyz.json")
		);
	}

	// ---- getScenariosFromCSVFile ----

	@Test
	void getScenariosFromCSVFileThrowsIOException() {
		Assertions.assertThrows(IOException.class, () ->
				CalibrationParserDataItems.getScenariosFromCSVFile("anything.csv")
		);
	}

	// ---- getScenariosFromJsonString ----

	@Test
	void getScenariosFromJsonStringParsesValidJson() {
		String json = "{"
				+ "\"20230101\": {"
				+ "  \"Quotes\": {"
				+ "    \"Euribor6M\": {"
				+ "      \"Swap-Rate\": {"
				+ "        \"1Y\": 0.03"
				+ "      }"
				+ "    }"
				+ "  }"
				+ "}"
				+ "}";

		List<CalibrationDataset> result = CalibrationParserDataItems.getScenariosFromJsonString(json);

		Assertions.assertEquals(1, result.size());
		Assertions.assertFalse(result.get(0).getDataPoints().isEmpty());
	}

	@Test
	void getScenariosFromJsonStringThrowsForInvalidJson() {
		Assertions.assertThrows(IllegalArgumentException.class, () ->
				CalibrationParserDataItems.getScenariosFromJsonString("not valid json {{{")
		);
	}

	@Test
	void getScenariosFromJsonStringParsesDateOnlyTimestamp() {
		// Timestamp in yyyyMMdd format should fall back to 17:00
		String json = "{"
				+ "\"20230615\": {"
				+ "  \"Quotes\": {"
				+ "    \"ESTR\": {"
				+ "      \"Swap-Rate\": {"
				+ "        \"1Y\": 0.025"
				+ "      }"
				+ "    }"
				+ "  }"
				+ "}"
				+ "}";

		List<CalibrationDataset> result = CalibrationParserDataItems.getScenariosFromJsonString(json);

		Assertions.assertEquals(1, result.size());
		Assertions.assertEquals(17, result.get(0).getDate().getHour());
		Assertions.assertEquals(0, result.get(0).getDate().getMinute());
	}

	@Test
	void getScenariosFromJsonStringParsesDateTimeTimestamp() {
		// Timestamp in yyyyMMdd-HHmmss format should use exact time
		String json = "{"
				+ "\"20230615-093000\": {"
				+ "  \"Quotes\": {"
				+ "    \"ESTR\": {"
				+ "      \"Swap-Rate\": {"
				+ "        \"1Y\": 0.025"
				+ "      }"
				+ "    }"
				+ "  }"
				+ "}"
				+ "}";

		List<CalibrationDataset> result = CalibrationParserDataItems.getScenariosFromJsonString(json);

		Assertions.assertEquals(1, result.size());
		Assertions.assertEquals(9, result.get(0).getDate().getHour());
		Assertions.assertEquals(30, result.get(0).getDate().getMinute());
	}

	@Test
	void getScenariosFromJsonStringHandlesFixings() {
		String json = "{"
				+ "\"20230615\": {"
				+ "  \"Quotes\": {"
				+ "    \"Euribor6M\": {"
				+ "      \"Swap-Rate\": {"
				+ "        \"1Y\": 0.03"
				+ "      }"
				+ "    }"
				+ "  },"
				+ "  \"Fixings\": {"
				+ "    \"Euribor6M\": {"
				+ "      \"Fixing\": {"
				+ "        \"2023-06-01\": 0.028"
				+ "      }"
				+ "    }"
				+ "  }"
				+ "}"
				+ "}";

		List<CalibrationDataset> result = CalibrationParserDataItems.getScenariosFromJsonString(json);

		Assertions.assertEquals(1, result.size());

		Set<CalibrationDataItem> fixings = result.get(0).getFixingDataItems();
		Assertions.assertFalse(fixings.isEmpty());
	}

	// ---- getCalibrationDataSetFromXML (line 104 - empty result throws SDCException) ----

	@Test
	void getCalibrationDataSetFromXMLThrowsWhenNoMatchingItems() {
		String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<marketDataList>"
				+ "  <requestTimeStamp>20230101-120000</requestTimeStamp>"
				+ "  <item><id>SOMEPOINT</id><value>0.01</value><timeStamp>20230101-120000</timeStamp></item>"
				+ "</marketDataList>";

		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("NONEXISTENT_KEY", "Euribor6M", "Swap-Rate", "1Y");
		List<CalibrationDataItem.Spec> dataSpecs = Collections.singletonList(spec);

		Assertions.assertThrows(SDCException.class, () ->
				CalibrationParserDataItems.getCalibrationDataSetFromXML(xmlString, dataSpecs)
		);
	}
}
