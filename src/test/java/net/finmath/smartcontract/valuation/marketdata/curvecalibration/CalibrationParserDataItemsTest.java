package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import net.finmath.smartcontract.model.ExceptionId;
import net.finmath.smartcontract.model.SDCException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CalibrationParserDataItemsTest {

	@Test
	void testParseDatapointESTRNonSwapRate() {
		// Given
		final CalibrationParserDataItems parser = new CalibrationParserDataItems();
		final CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("ESTR_Deposit_1Y", "ESTR", "Deposit", "1Y");
		final CalibrationDataItem dataItem = new CalibrationDataItem(spec, 0.05, LocalDateTime.now());

		// When
		final Stream<CalibrationSpecProvider> result = parser.parse(Stream.of(dataItem));

		// Then
		assertEquals(0, result.count());
	}

	@Test
	void testParseDatapointEONIANonSwapRate() {
		// Given
		final CalibrationParserDataItems parser = new CalibrationParserDataItems();
		final CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("EONIA_Deposit_1Y", "EONIA", "Deposit", "1Y");
		final CalibrationDataItem dataItem = new CalibrationDataItem(spec, 0.05, LocalDateTime.now());

		// When
		final Stream<CalibrationSpecProvider> result = parser.parse(Stream.of(dataItem));

		// Then
		assertEquals(0, result.count());
	}

	@Test
	void testParseDatapointEuribor6MForwardRateAgreement() {
		// Given
		final CalibrationParserDataItems parser = new CalibrationParserDataItems();
		final CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Euribor6M_FRA_6M", "Euribor6M", "Forward-Rate-Agreement", "6M");
		final CalibrationDataItem dataItem = new CalibrationDataItem(spec, 0.05, LocalDateTime.now());

		// When
		final Stream<CalibrationSpecProvider> result = parser.parse(Stream.of(dataItem));

		// Then
		assertEquals(1, result.count());
	}

	@Test
	void testParseDatapointEuribor6MDeposit() {
		// Given
		final CalibrationParserDataItems parser = new CalibrationParserDataItems();
		final CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Euribor6M_Deposit_6M", "Euribor6M", "Deposit", "6M");
		final CalibrationDataItem dataItem = new CalibrationDataItem(spec, 0.05, LocalDateTime.now());

		// When
		final Stream<CalibrationSpecProvider> result = parser.parse(Stream.of(dataItem));

		// Then
		assertEquals(1, result.count());
	}

	@Test
	void testParseDatapointEuribor6MDepositRate() {
		// Given
		final CalibrationParserDataItems parser = new CalibrationParserDataItems();
		final CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Euribor6M_DepositRate_6M", "Euribor6M", "Deposit-Rate", "6M");
		final CalibrationDataItem dataItem = new CalibrationDataItem(spec, 0.05, LocalDateTime.now());

		// When
		final Stream<CalibrationSpecProvider> result = parser.parse(Stream.of(dataItem));

		// Then
		assertEquals(1, result.count());
	}

	@Test
	void testParseDatapointEuribor6MUnknownProduct() {
		// Given
		final CalibrationParserDataItems parser = new CalibrationParserDataItems();
		final CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Euribor6M_Unknown_6M", "Euribor6M", "Unknown", "6M");
		final CalibrationDataItem dataItem = new CalibrationDataItem(spec, 0.05, LocalDateTime.now());

		// When
		final Stream<CalibrationSpecProvider> result = parser.parse(Stream.of(dataItem));

		// Then
		assertEquals(0, result.count());
	}

	@Test
	void testParseDatapointEuribor1M() {
		// Given
		final CalibrationParserDataItems parser = new CalibrationParserDataItems();
		final CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Euribor1M_Swap_1Y", "Euribor1M", "Swap-Rate", "1Y");
		final CalibrationDataItem dataItem = new CalibrationDataItem(spec, 0.05, LocalDateTime.now());

		// When
		final Stream<CalibrationSpecProvider> result = parser.parse(Stream.of(dataItem));

		// Then
		assertEquals(1, result.count());
	}

	@Test
	void testParseDatapointEuribor3M() {
		// Given
		final CalibrationParserDataItems parser = new CalibrationParserDataItems();
		final CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Euribor3M_Swap_1Y", "Euribor3M", "Swap-Rate", "1Y");
		final CalibrationDataItem dataItem = new CalibrationDataItem(spec, 0.05, LocalDateTime.now());

		// When
		final Stream<CalibrationSpecProvider> result = parser.parse(Stream.of(dataItem));

		// Then
		assertEquals(1, result.count());
	}

	@Test
	void testParseDatapointUnknownCurve() {
		// Given
		final CalibrationParserDataItems parser = new CalibrationParserDataItems();
		final CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Unknown_Swap_1Y", "UnknownCurve", "Swap-Rate", "1Y");
		final CalibrationDataItem dataItem = new CalibrationDataItem(spec, 0.05, LocalDateTime.now());

		// When
		final Stream<CalibrationSpecProvider> result = parser.parse(Stream.of(dataItem));

		// Then
		assertEquals(0, result.count());
	}

	@Test
	void testGetScenariosFromJsonFile() throws IOException {
		// Given
		final String fileName = "/net/finmath/smartcontract/valuation/marketdata/curvecalibration/timeseriesdatamap.json";

		// When
		final List<CalibrationDataset> scenarios = CalibrationParserDataItems.getScenariosFromJsonFile(fileName);

		// Then
		assertNotNull(scenarios);
		assertFalse(scenarios.isEmpty());
	}

	@Test
	void testGetScenariosFromJsonFileNotFound() {
		// Given
		final String fileName = "/nonexistent/file.json";

		// When/Then
		assertThrows(NullPointerException.class, () -> CalibrationParserDataItems.getScenariosFromJsonFile(fileName));
	}

	@Test
	void testGetCalibrationDataSetFromXML() {
		// Given
		final String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
				"<marketDataList>\n" +
				"    <requestTimeStamp>20080502-170000</requestTimeStamp>\n" +
				"    <item>\n" +
				"        <id>ESTRSWP3Y</id>\n" +
				"        <value>0.045</value>\n" +
				"        <timeStamp>20080502-170000</timeStamp>\n" +
				"    </item>\n" +
				"</marketDataList>";

		final CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("ESTRSWP3Y", "ESTR", "Swap-Rate", "3Y");
		final List<CalibrationDataItem.Spec> dataSpecs = List.of(spec);

		// When
		final CalibrationDataset result = CalibrationParserDataItems.getCalibrationDataSetFromXML(xmlString, dataSpecs);

		// Then
		assertNotNull(result);
		assertFalse(result.getDataPoints().isEmpty());
		assertEquals(1, result.getDataPoints().size());
	}

	@Test
	void testGetCalibrationDataSetFromXMLEmpty() {
		// Given
		final String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
				"<marketDataList>\n" +
				"    <requestTimeStamp>20080502-170000</requestTimeStamp>\n" +
				"    <item>\n" +
				"        <id>ESTRSWP3Y</id>\n" +
				"        <value>0.045</value>\n" +
				"        <timeStamp>20080502-170000</timeStamp>\n" +
				"    </item>\n" +
				"</marketDataList>";

		final CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("NONEXISTENT", "ESTR", "Swap-Rate", "3Y");
		final List<CalibrationDataItem.Spec> dataSpecs = List.of(spec);

		// When/Then
		final SDCException exception = assertThrows(SDCException.class,
				() -> CalibrationParserDataItems.getCalibrationDataSetFromXML(xmlString, dataSpecs));
		assertEquals(ExceptionId.SDC_CALIBRATION_DATA_EMPTY, exception.getId());
	}

	@Test
	void testGetScenariosFromCSVFile() {
		// Given
		final String fileName = "test.csv";

		// When/Then
		assertThrows(IOException.class, () -> CalibrationParserDataItems.getScenariosFromCSVFile(fileName));
	}

	@Test
	void testGetScenariosFromJsonString() {
		// Given
		final String jsonString = """
				{
				  "20080716": {
				    "Quotes": {
				      "Euribor6M": {
				        "Swap-Rate": {
				          "1Y": 0.0519,
				          "2Y": 0.0511
				        }
				      }
				    }
				  }
				}
				""";

		// When
		final List<CalibrationDataset> scenarios = CalibrationParserDataItems.getScenariosFromJsonString(jsonString);

		// Then
		assertNotNull(scenarios);
		assertEquals(1, scenarios.size());
		assertFalse(scenarios.get(0).getDataPoints().isEmpty());
	}

	@Test
	void testGetScenariosFromJsonStringWithFixings() {
		// Given
		final String jsonString = """
				{
				  "20080716": {
				    "Quotes": {
				      "Euribor6M": {
				        "Swap-Rate": {
				          "1Y": 0.0519
				        }
				      }
				    },
				    "Fixings": {
				      "Euribor6M": {
				        "Fixing": {
				          "2008-07-15": 0.0484
				        }
				      }
				    }
				  }
				}
				""";

		// When
		final List<CalibrationDataset> scenarios = CalibrationParserDataItems.getScenariosFromJsonString(jsonString);

		// Then
		assertNotNull(scenarios);
		assertEquals(1, scenarios.size());
		final CalibrationDataset dataset = scenarios.get(0);
		assertTrue(dataset.getDataPoints().size() > 1);
	}

	@Test
	void testGetScenariosFromJsonStringInvalidJson() {
		// Given
		final String invalidJson = "{ invalid json }";

		// When/Then
		assertThrows(IllegalArgumentException.class,
				() -> CalibrationParserDataItems.getScenariosFromJsonString(invalidJson));
	}

	@Test
	void testParseTimestampStringWithDateTimeViaJson() {
		// Given - JSON with full timestamp
		final String jsonString = """
				{
				  "20080716-143000": {
				    "Quotes": {
				      "Euribor6M": {
				        "Swap-Rate": {
				          "1Y": 0.0519
				        }
				      }
				    }
				  }
				}
				""";

		// When
		final List<CalibrationDataset> scenarios = CalibrationParserDataItems.getScenariosFromJsonString(jsonString);

		// Then
		assertNotNull(scenarios);
		assertEquals(1, scenarios.size());
		final LocalDateTime dateTime = scenarios.get(0).getDate();
		assertEquals(2008, dateTime.getYear());
		assertEquals(7, dateTime.getMonthValue());
		assertEquals(16, dateTime.getDayOfMonth());
		assertEquals(14, dateTime.getHour());
		assertEquals(30, dateTime.getMinute());
	}

	@Test
	void testParseTimestampStringDateOnlyViaJson() {
		// Given - JSON with date only (no time)
		final String jsonString = """
				{
				  "20080716": {
				    "Quotes": {
				      "Euribor6M": {
				        "Swap-Rate": {
				          "1Y": 0.0519
				        }
				      }
				    }
				  }
				}
				""";

		// When
		final List<CalibrationDataset> scenarios = CalibrationParserDataItems.getScenariosFromJsonString(jsonString);

		// Then
		assertNotNull(scenarios);
		assertEquals(1, scenarios.size());
		final LocalDateTime dateTime = scenarios.get(0).getDate();
		assertEquals(2008, dateTime.getYear());
		assertEquals(7, dateTime.getMonthValue());
		assertEquals(16, dateTime.getDayOfMonth());
		assertEquals(17, dateTime.getHour());
		assertEquals(0, dateTime.getMinute());
	}

	@Test
	void testGetFixingDataItemSetViaJson() {
		// Given - JSON with fixings that will trigger getFixingDataItemSet
		final String jsonString = """
				{
				  "20080716": {
				    "Quotes": {
				      "Euribor6M": {
				        "Swap-Rate": {
				          "1Y": 0.0519
				        }
				      }
				    },
				    "Fixings": {
				      "Euribor6M": {
				        "Fixing": {
				          "2008-07-15": 0.0484,
				          "2008-07-16": 0.0485
				        }
				      }
				    }
				  }
				}
				""";

		// When
		final List<CalibrationDataset> scenarios = CalibrationParserDataItems.getScenariosFromJsonString(jsonString);

		// Then
		assertNotNull(scenarios);
		assertEquals(1, scenarios.size());
		final CalibrationDataset dataset = scenarios.get(0);

		// Count fixing items with 1D maturity
		final long fixingCount = dataset.getDataPoints().stream()
				.filter(item -> "1D".equals(item.getMaturity()))
				.count();
		assertEquals(2, fixingCount);
	}
}
