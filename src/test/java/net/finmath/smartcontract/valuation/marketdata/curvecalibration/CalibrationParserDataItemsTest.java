package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CalibrationParserDataItemsTest {

	private final CalibrationParserDataItems parser = new CalibrationParserDataItems();
	private final LocalDateTime dateTime = LocalDateTime.of(2024, 6, 15, 17, 0);

	private CalibrationDataItem createItem(String curve, String product, String maturity, double quote) {
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec(
				curve + "_" + product + "_" + maturity, curve, product, maturity);
		return new CalibrationDataItem(spec, quote, dateTime);
	}

	@Test
	void parse_shouldParseESTRSwapRate() {
		CalibrationDataItem item = createItem("ESTR", "Swap-Rate", "5Y", 0.03);
		Stream<CalibrationSpecProvider> result = parser.parse(Stream.of(item));
		assertEquals(1, result.count());
	}

	@Test
	void parse_shouldParseEONIASwapRate() {
		CalibrationDataItem item = createItem("EONIA", "Swap-Rate", "2Y", 0.02);
		Stream<CalibrationSpecProvider> result = parser.parse(Stream.of(item));
		assertEquals(1, result.count());
	}

	@Test
	void parse_shouldParseEuribor6MSwapRate() {
		CalibrationDataItem item = createItem("Euribor6M", "Swap-Rate", "5Y", 0.035);
		Stream<CalibrationSpecProvider> result = parser.parse(Stream.of(item));
		assertEquals(1, result.count());
	}

	@Test
	void parse_shouldParseEuribor6MFRA() {
		CalibrationDataItem item = createItem("Euribor6M", "Forward-Rate-Agreement", "12M", 0.03);
		Stream<CalibrationSpecProvider> result = parser.parse(Stream.of(item));
		assertEquals(1, result.count());
	}

	@Test
	void parse_shouldParseEuribor6MDeposit() {
		CalibrationDataItem item = createItem("Euribor6M", "Deposit", "6M", 0.025);
		Stream<CalibrationSpecProvider> result = parser.parse(Stream.of(item));
		assertEquals(1, result.count());
	}

	@Test
	void parse_shouldParseEuribor6MDepositRate() {
		CalibrationDataItem item = createItem("Euribor6M", "Deposit-Rate", "6M", 0.025);
		Stream<CalibrationSpecProvider> result = parser.parse(Stream.of(item));
		assertEquals(1, result.count());
	}

	@Test
	void parse_shouldReturnEmpty_forUnknownEuribor6MProduct() {
		CalibrationDataItem item = createItem("Euribor6M", "Unknown-Product", "5Y", 0.03);
		Stream<CalibrationSpecProvider> result = parser.parse(Stream.of(item));
		assertEquals(0, result.count());
	}

	@Test
	void parse_shouldReturnEmpty_forUnknownESTRProduct() {
		CalibrationDataItem item = createItem("ESTR", "FRA", "5Y", 0.03);
		Stream<CalibrationSpecProvider> result = parser.parse(Stream.of(item));
		assertEquals(0, result.count());
	}

	@Test
	void parse_shouldParseEuribor1MSwapRate() {
		CalibrationDataItem item = createItem("Euribor1M", "Swap-Rate", "3Y", 0.028);
		Stream<CalibrationSpecProvider> result = parser.parse(Stream.of(item));
		assertEquals(1, result.count());
	}

	@Test
	void parse_shouldParseEuribor3MSwapRate() {
		CalibrationDataItem item = createItem("Euribor3M", "Swap-Rate", "5Y", 0.032);
		Stream<CalibrationSpecProvider> result = parser.parse(Stream.of(item));
		assertEquals(1, result.count());
	}

	@Test
	void parse_shouldReturnEmpty_forUnknownCurve() {
		CalibrationDataItem item = createItem("UnknownCurve", "Swap-Rate", "5Y", 0.03);
		Stream<CalibrationSpecProvider> result = parser.parse(Stream.of(item));
		assertEquals(0, result.count());
	}

	@Test
	void parse_shouldHandleMultipleItems() {
		CalibrationDataItem item1 = createItem("ESTR", "Swap-Rate", "5Y", 0.03);
		CalibrationDataItem item2 = createItem("Euribor6M", "Swap-Rate", "10Y", 0.04);
		CalibrationDataItem item3 = createItem("UnknownCurve", "Swap-Rate", "1Y", 0.01);

		Stream<CalibrationSpecProvider> result = parser.parse(Stream.of(item1, item2, item3));
		assertEquals(2, result.count());
	}

	@Test
	void getScenariosFromJsonFile_shouldParseTestFile() throws IOException {
		List<CalibrationDataset> scenarios = CalibrationParserDataItems.getScenariosFromJsonFile(
				"/net/finmath/smartcontract/valuation/marketdata/curvecalibration/timeseriesdatamap.json");
		assertFalse(scenarios.isEmpty());
		assertNotNull(scenarios.get(0).getDate());
		assertFalse(scenarios.get(0).getCalibrationDataItems().isEmpty());
	}

	@Test
	void getScenariosFromJsonString_shouldParseValidJson() {
		String json = """
				{
				  "20240615": {
				    "Quotes": {
				      "Euribor6M": {
				        "Swap-Rate": {
				          "5Y": 0.03,
				          "10Y": 0.04
				        }
				      }
				    }
				  }
				}
				""";
		List<CalibrationDataset> scenarios = CalibrationParserDataItems.getScenariosFromJsonString(json);
		assertEquals(1, scenarios.size());
		assertFalse(scenarios.get(0).getCalibrationDataItems().isEmpty());
	}

	@Test
	void getScenariosFromJsonString_shouldThrow_forInvalidJson() {
		assertThrows(IllegalArgumentException.class,
				() -> CalibrationParserDataItems.getScenariosFromJsonString("not valid json"));
	}

	@Test
	void getScenariosFromJsonString_shouldParseDateTimeFormat() {
		String json = """
				{
				  "20240615-170000": {
				    "Quotes": {
				      "ESTR": {
				        "Swap-Rate": {
				          "1Y": 0.025
				        }
				      }
				    }
				  }
				}
				""";
		List<CalibrationDataset> scenarios = CalibrationParserDataItems.getScenariosFromJsonString(json);
		assertEquals(1, scenarios.size());
		assertEquals(17, scenarios.get(0).getDate().getHour());
	}

	@Test
	void getScenariosFromJsonString_shouldParseFixings() {
		String json = """
				{
				  "20240615": {
				    "Quotes": {
				      "Euribor6M": {
				        "Swap-Rate": {
				          "5Y": 0.03
				        }
				      }
				    },
				    "Fixings": {
				      "Euribor6M": {
				        "Fixing": {
				          "2024-06-14": 0.025
				        }
				      }
				    }
				  }
				}
				""";
		List<CalibrationDataset> scenarios = CalibrationParserDataItems.getScenariosFromJsonString(json);
		assertEquals(1, scenarios.size());
		assertFalse(scenarios.get(0).getFixingDataItems().isEmpty());
	}

	@Test
	void getScenariosFromCSVFile_shouldThrowNotImplemented() {
		assertThrows(IOException.class,
				() -> CalibrationParserDataItems.getScenariosFromCSVFile("anyfile.csv"));
	}
}
