/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import net.finmath.smartcontract.model.ExceptionId;
import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.model.SDCException;
import net.finmath.smartcontract.valuation.marketdata.data.MarketDataPoint;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for CalibrationParserDataItems.
 * Tests all methods with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class CalibrationParserDataItemsClaudeTest {

	/**
	 * Test constructor - verify it creates an instance successfully.
	 */
	@Test
	void testConstructor_Success() {
		// Act
		CalibrationParserDataItems parser = new CalibrationParserDataItems();

		// Assert
		assertNotNull(parser, "Parser should be created successfully");
	}

	/**
	 * Test parse method with ESTR Swap-Rate data point.
	 * Should return CalibrationSpecProviderOis.
	 */
	@Test
	void testParse_ESTRSwapRate_ReturnsOisSpec() {
		// Arrange
		CalibrationParserDataItems parser = new CalibrationParserDataItems();
		LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 17, 0);
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("ESTR_Swap-Rate_1Y", "ESTR", "Swap-Rate", "1Y");
		CalibrationDataItem dataItem = new CalibrationDataItem(spec, 0.045, timestamp);
		Stream<CalibrationDataItem> datapoints = Stream.of(dataItem);

		// Act
		List<CalibrationSpecProvider> result = parser.parse(datapoints).toList();

		// Assert
		assertEquals(1, result.size(), "Should return one calibration spec");
		assertTrue(result.get(0) instanceof CalibrationSpecProviderOis, "Should return OIS spec for ESTR Swap-Rate");
	}

	/**
	 * Test parse method with EONIA Swap-Rate data point.
	 * Should return CalibrationSpecProviderOis.
	 */
	@Test
	void testParse_EONIASwapRate_ReturnsOisSpec() {
		// Arrange
		CalibrationParserDataItems parser = new CalibrationParserDataItems();
		LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 17, 0);
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("EONIA_Swap-Rate_2Y", "EONIA", "Swap-Rate", "2Y");
		CalibrationDataItem dataItem = new CalibrationDataItem(spec, 0.035, timestamp);
		Stream<CalibrationDataItem> datapoints = Stream.of(dataItem);

		// Act
		List<CalibrationSpecProvider> result = parser.parse(datapoints).toList();

		// Assert
		assertEquals(1, result.size(), "Should return one calibration spec");
		assertTrue(result.get(0) instanceof CalibrationSpecProviderOis, "Should return OIS spec for EONIA Swap-Rate");
	}

	/**
	 * Test parse method with ESTR non-Swap-Rate product (should be filtered out).
	 */
	@Test
	void testParse_ESTRNonSwapRate_ReturnsEmpty() {
		// Arrange
		CalibrationParserDataItems parser = new CalibrationParserDataItems();
		LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 17, 0);
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("ESTR_Deposit_1Y", "ESTR", "Deposit", "1Y");
		CalibrationDataItem dataItem = new CalibrationDataItem(spec, 0.045, timestamp);
		Stream<CalibrationDataItem> datapoints = Stream.of(dataItem);

		// Act
		List<CalibrationSpecProvider> result = parser.parse(datapoints).toList();

		// Assert
		assertEquals(0, result.size(), "Should return empty list for ESTR non-Swap-Rate");
	}

	/**
	 * Test parse method with Euribor6M Swap-Rate data point.
	 * Should return CalibrationSpecProviderSwap.
	 */
	@Test
	void testParse_Euribor6MSwapRate_ReturnsSwapSpec() {
		// Arrange
		CalibrationParserDataItems parser = new CalibrationParserDataItems();
		LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 17, 0);
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Euribor6M_Swap-Rate_5Y", "Euribor6M", "Swap-Rate", "5Y");
		CalibrationDataItem dataItem = new CalibrationDataItem(spec, 0.05, timestamp);
		Stream<CalibrationDataItem> datapoints = Stream.of(dataItem);

		// Act
		List<CalibrationSpecProvider> result = parser.parse(datapoints).toList();

		// Assert
		assertEquals(1, result.size(), "Should return one calibration spec");
		assertTrue(result.get(0) instanceof CalibrationSpecProviderSwap, "Should return Swap spec for Euribor6M Swap-Rate");
	}

	/**
	 * Test parse method with Euribor6M Forward-Rate-Agreement data point.
	 * Should return CalibrationSpecProviderFRA.
	 */
	@Test
	void testParse_Euribor6MFRA_ReturnsFRASpec() {
		// Arrange
		CalibrationParserDataItems parser = new CalibrationParserDataItems();
		LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 17, 0);
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Euribor6M_FRA_3M", "Euribor6M", "Forward-Rate-Agreement", "3M");
		CalibrationDataItem dataItem = new CalibrationDataItem(spec, 0.025, timestamp);
		Stream<CalibrationDataItem> datapoints = Stream.of(dataItem);

		// Act
		List<CalibrationSpecProvider> result = parser.parse(datapoints).toList();

		// Assert
		assertEquals(1, result.size(), "Should return one calibration spec");
		assertTrue(result.get(0) instanceof CalibrationSpecProviderFRA, "Should return FRA spec for Euribor6M FRA");
	}

	/**
	 * Test parse method with Euribor6M Deposit data point.
	 * Should return CalibrationSpecProviderDeposit.
	 */
	@Test
	void testParse_Euribor6MDeposit_ReturnsDepositSpec() {
		// Arrange
		CalibrationParserDataItems parser = new CalibrationParserDataItems();
		LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 17, 0);
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Euribor6M_Deposit_6M", "Euribor6M", "Deposit", "6M");
		CalibrationDataItem dataItem = new CalibrationDataItem(spec, 0.02, timestamp);
		Stream<CalibrationDataItem> datapoints = Stream.of(dataItem);

		// Act
		List<CalibrationSpecProvider> result = parser.parse(datapoints).toList();

		// Assert
		assertEquals(1, result.size(), "Should return one calibration spec");
		assertTrue(result.get(0) instanceof CalibrationSpecProviderDeposit, "Should return Deposit spec for Euribor6M Deposit");
	}

	/**
	 * Test parse method with Euribor6M Deposit-Rate data point (lowercase variant).
	 * Should return CalibrationSpecProviderDeposit.
	 */
	@Test
	void testParse_Euribor6MDepositRate_ReturnsDepositSpec() {
		// Arrange
		CalibrationParserDataItems parser = new CalibrationParserDataItems();
		LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 17, 0);
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Euribor6M_Deposit-Rate_6M", "Euribor6M", "Deposit-Rate", "6M");
		CalibrationDataItem dataItem = new CalibrationDataItem(spec, 0.02, timestamp);
		Stream<CalibrationDataItem> datapoints = Stream.of(dataItem);

		// Act
		List<CalibrationSpecProvider> result = parser.parse(datapoints).toList();

		// Assert
		assertEquals(1, result.size(), "Should return one calibration spec");
		assertTrue(result.get(0) instanceof CalibrationSpecProviderDeposit, "Should return Deposit spec for Euribor6M Deposit-Rate");
	}

	/**
	 * Test parse method with Euribor6M unknown product type (should be filtered out).
	 */
	@Test
	void testParse_Euribor6MUnknownProduct_ReturnsEmpty() {
		// Arrange
		CalibrationParserDataItems parser = new CalibrationParserDataItems();
		LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 17, 0);
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Euribor6M_Unknown_1Y", "Euribor6M", "Unknown", "1Y");
		CalibrationDataItem dataItem = new CalibrationDataItem(spec, 0.05, timestamp);
		Stream<CalibrationDataItem> datapoints = Stream.of(dataItem);

		// Act
		List<CalibrationSpecProvider> result = parser.parse(datapoints).toList();

		// Assert
		assertEquals(0, result.size(), "Should return empty list for Euribor6M unknown product");
	}

	/**
	 * Test parse method with Euribor1M data point.
	 * Should return CalibrationSpecProviderSwap with "1M" and "monthly" parameters.
	 */
	@Test
	void testParse_Euribor1M_ReturnsSwapSpec() {
		// Arrange
		CalibrationParserDataItems parser = new CalibrationParserDataItems();
		LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 17, 0);
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Euribor1M_Swap-Rate_3Y", "Euribor1M", "Swap-Rate", "3Y");
		CalibrationDataItem dataItem = new CalibrationDataItem(spec, 0.04, timestamp);
		Stream<CalibrationDataItem> datapoints = Stream.of(dataItem);

		// Act
		List<CalibrationSpecProvider> result = parser.parse(datapoints).toList();

		// Assert
		assertEquals(1, result.size(), "Should return one calibration spec");
		assertTrue(result.get(0) instanceof CalibrationSpecProviderSwap, "Should return Swap spec for Euribor1M");
	}

	/**
	 * Test parse method with Euribor3M data point.
	 * Should return CalibrationSpecProviderSwap with "3M" and "quarterly" parameters.
	 */
	@Test
	void testParse_Euribor3M_ReturnsSwapSpec() {
		// Arrange
		CalibrationParserDataItems parser = new CalibrationParserDataItems();
		LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 17, 0);
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Euribor3M_Swap-Rate_2Y", "Euribor3M", "Swap-Rate", "2Y");
		CalibrationDataItem dataItem = new CalibrationDataItem(spec, 0.038, timestamp);
		Stream<CalibrationDataItem> datapoints = Stream.of(dataItem);

		// Act
		List<CalibrationSpecProvider> result = parser.parse(datapoints).toList();

		// Assert
		assertEquals(1, result.size(), "Should return one calibration spec");
		assertTrue(result.get(0) instanceof CalibrationSpecProviderSwap, "Should return Swap spec for Euribor3M");
	}

	/**
	 * Test parse method with unknown curve name (default case).
	 * Should be filtered out and return empty.
	 */
	@Test
	void testParse_UnknownCurveName_ReturnsEmpty() {
		// Arrange
		CalibrationParserDataItems parser = new CalibrationParserDataItems();
		LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 17, 0);
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("UnknownCurve_Swap-Rate_1Y", "UnknownCurve", "Swap-Rate", "1Y");
		CalibrationDataItem dataItem = new CalibrationDataItem(spec, 0.03, timestamp);
		Stream<CalibrationDataItem> datapoints = Stream.of(dataItem);

		// Act
		List<CalibrationSpecProvider> result = parser.parse(datapoints).toList();

		// Assert
		assertEquals(0, result.size(), "Should return empty list for unknown curve name");
	}

	/**
	 * Test parse method with multiple data points.
	 * Should parse all valid data points and filter out invalid ones.
	 */
	@Test
	void testParse_MultipleDataPoints_ParsesValidOnes() {
		// Arrange
		CalibrationParserDataItems parser = new CalibrationParserDataItems();
		LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 17, 0);

		CalibrationDataItem.Spec spec1 = new CalibrationDataItem.Spec("ESTR_Swap-Rate_1Y", "ESTR", "Swap-Rate", "1Y");
		CalibrationDataItem dataItem1 = new CalibrationDataItem(spec1, 0.045, timestamp);

		CalibrationDataItem.Spec spec2 = new CalibrationDataItem.Spec("Euribor6M_Swap-Rate_5Y", "Euribor6M", "Swap-Rate", "5Y");
		CalibrationDataItem dataItem2 = new CalibrationDataItem(spec2, 0.05, timestamp);

		CalibrationDataItem.Spec spec3 = new CalibrationDataItem.Spec("Unknown_Product_1Y", "UnknownCurve", "Product", "1Y");
		CalibrationDataItem dataItem3 = new CalibrationDataItem(spec3, 0.03, timestamp);

		Stream<CalibrationDataItem> datapoints = Stream.of(dataItem1, dataItem2, dataItem3);

		// Act
		List<CalibrationSpecProvider> result = parser.parse(datapoints).toList();

		// Assert
		assertEquals(2, result.size(), "Should return two calibration specs (filter out unknown curve)");
	}

	/**
	 * Test parse method with empty stream.
	 * Should return empty result.
	 */
	@Test
	void testParse_EmptyStream_ReturnsEmpty() {
		// Arrange
		CalibrationParserDataItems parser = new CalibrationParserDataItems();
		Stream<CalibrationDataItem> datapoints = Stream.empty();

		// Act
		List<CalibrationSpecProvider> result = parser.parse(datapoints).toList();

		// Assert
		assertEquals(0, result.size(), "Should return empty list for empty stream");
	}

	/**
	 * Test getScenariosFromJsonFile with valid file from resources.
	 */
	@Test
	void testGetScenariosFromJsonFile_ValidFile_ReturnsScenarios() throws IOException {
		// Arrange
		String fileName = "/net/finmath/smartcontract/valuation/marketdata/curvecalibration/timeseriesdatamap.json";

		// Act
		List<CalibrationDataset> scenarios = CalibrationParserDataItems.getScenariosFromJsonFile(fileName);

		// Assert
		assertNotNull(scenarios, "Should return list of scenarios");
		assertFalse(scenarios.isEmpty(), "Should not be empty");
	}

	/**
	 * Test getScenariosFromJsonFile with non-existent file.
	 * Should throw NullPointerException (because getResourceAsStream returns null for missing files).
	 */
	@Test
	void testGetScenariosFromJsonFile_FileNotFound_ThrowsException() {
		// Arrange
		String fileName = "/non/existent/file.json";

		// Act & Assert
		// Note: When resource is not found, getResourceAsStream returns null, causing NullPointerException
		assertThrows(NullPointerException.class, () -> {
			CalibrationParserDataItems.getScenariosFromJsonFile(fileName);
		}, "Should throw NullPointerException for non-existent file");
	}

	/**
	 * Test getScenariosFromJsonString with valid JSON containing Quotes.
	 */
	@Test
	void testGetScenariosFromJsonString_ValidJsonWithQuotes_ReturnsScenarios() {
		// Arrange
		String jsonContent = """
		{
		  "20240115": {
		    "Quotes": {
		      "Euribor6M": {
		        "Swap-Rate": {
		          "1Y": 0.05,
		          "2Y": 0.055
		        }
		      }
		    }
		  }
		}
		""";

		// Act
		List<CalibrationDataset> scenarios = CalibrationParserDataItems.getScenariosFromJsonString(jsonContent);

		// Assert
		assertNotNull(scenarios, "Should return list of scenarios");
		assertEquals(1, scenarios.size(), "Should have one scenario");
		CalibrationDataset scenario = scenarios.get(0);
		assertEquals(2, scenario.getCalibrationDataItems().size(), "Should have two calibration data items");
	}

	/**
	 * Test getScenariosFromJsonString with valid JSON containing Quotes and Fixings.
	 */
	@Test
	void testGetScenariosFromJsonString_ValidJsonWithQuotesAndFixings_ReturnsScenarios() {
		// Arrange
		String jsonContent = """
		{
		  "20240115": {
		    "Quotes": {
		      "Euribor6M": {
		        "Swap-Rate": {
		          "1Y": 0.05
		        }
		      }
		    },
		    "Fixings": {
		      "Euribor6M": {
		        "Fixing": {
		          "2024-01-10": 0.045,
		          "2024-01-11": 0.046
		        }
		      }
		    }
		  }
		}
		""";

		// Act
		List<CalibrationDataset> scenarios = CalibrationParserDataItems.getScenariosFromJsonString(jsonContent);

		// Assert
		assertNotNull(scenarios, "Should return list of scenarios");
		assertEquals(1, scenarios.size(), "Should have one scenario");
		CalibrationDataset scenario = scenarios.get(0);
		assertEquals(1, scenario.getCalibrationDataItems().size(), "Should have one calibration data item");
		assertEquals(2, scenario.getFixingDataItems().size(), "Should have two fixing data items");
	}

	/**
	 * Test getScenariosFromJsonString with timestamp in date format (yyyyMMdd).
	 * Should parse and default time to 17:00.
	 */
	@Test
	void testGetScenariosFromJsonString_TimestampDateFormat_DefaultsTo1700() {
		// Arrange
		String jsonContent = """
		{
		  "20240115": {
		    "Quotes": {
		      "Euribor6M": {
		        "Swap-Rate": {
		          "1Y": 0.05
		        }
		      }
		    }
		  }
		}
		""";

		// Act
		List<CalibrationDataset> scenarios = CalibrationParserDataItems.getScenariosFromJsonString(jsonContent);

		// Assert
		assertEquals(1, scenarios.size(), "Should have one scenario");
		CalibrationDataset scenario = scenarios.get(0);
		assertEquals(17, scenario.getDate().getHour(), "Hour should default to 17");
		assertEquals(0, scenario.getDate().getMinute(), "Minute should default to 0");
	}

	/**
	 * Test getScenariosFromJsonString with timestamp in datetime format (yyyyMMdd-HHmmss).
	 * Should parse exact time.
	 */
	@Test
	void testGetScenariosFromJsonString_TimestampDateTimeFormat_ParsesExactTime() {
		// Arrange
		String jsonContent = """
		{
		  "20240115-143000": {
		    "Quotes": {
		      "Euribor6M": {
		        "Swap-Rate": {
		          "1Y": 0.05
		        }
		      }
		    }
		  }
		}
		""";

		// Act
		List<CalibrationDataset> scenarios = CalibrationParserDataItems.getScenariosFromJsonString(jsonContent);

		// Assert
		assertEquals(1, scenarios.size(), "Should have one scenario");
		CalibrationDataset scenario = scenarios.get(0);
		assertEquals(14, scenario.getDate().getHour(), "Hour should be 14");
		assertEquals(30, scenario.getDate().getMinute(), "Minute should be 30");
		assertEquals(0, scenario.getDate().getSecond(), "Second should be 0");
	}

	/**
	 * Test getScenariosFromJsonString with multiple scenarios.
	 * Should return sorted by date.
	 */
	@Test
	void testGetScenariosFromJsonString_MultipleScenarios_SortedByDate() {
		// Arrange
		String jsonContent = """
		{
		  "20240117": {
		    "Quotes": {
		      "Euribor6M": {
		        "Swap-Rate": {
		          "1Y": 0.053
		        }
		      }
		    }
		  },
		  "20240115": {
		    "Quotes": {
		      "Euribor6M": {
		        "Swap-Rate": {
		          "1Y": 0.05
		        }
		      }
		    }
		  },
		  "20240116": {
		    "Quotes": {
		      "Euribor6M": {
		        "Swap-Rate": {
		          "1Y": 0.051
		        }
		      }
		    }
		  }
		}
		""";

		// Act
		List<CalibrationDataset> scenarios = CalibrationParserDataItems.getScenariosFromJsonString(jsonContent);

		// Assert
		assertEquals(3, scenarios.size(), "Should have three scenarios");
		assertEquals(LocalDateTime.of(2024, 1, 15, 17, 0), scenarios.get(0).getDate(), "First scenario should be 2024-01-15");
		assertEquals(LocalDateTime.of(2024, 1, 16, 17, 0), scenarios.get(1).getDate(), "Second scenario should be 2024-01-16");
		assertEquals(LocalDateTime.of(2024, 1, 17, 17, 0), scenarios.get(2).getDate(), "Third scenario should be 2024-01-17");
	}

	/**
	 * Test getScenariosFromJsonString with invalid JSON format.
	 * Should throw IllegalArgumentException.
	 */
	@Test
	void testGetScenariosFromJsonString_InvalidJson_ThrowsIllegalArgumentException() {
		// Arrange
		String invalidJsonContent = "{ invalid json content }";

		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> {
			CalibrationParserDataItems.getScenariosFromJsonString(invalidJsonContent);
		}, "Should throw IllegalArgumentException for invalid JSON");
	}

	/**
	 * Test getScenariosFromJsonString with empty JSON.
	 * Should return empty list.
	 */
	@Test
	void testGetScenariosFromJsonString_EmptyJson_ReturnsEmptyList() {
		// Arrange
		String emptyJsonContent = "{}";

		// Act
		List<CalibrationDataset> scenarios = CalibrationParserDataItems.getScenariosFromJsonString(emptyJsonContent);

		// Assert
		assertNotNull(scenarios, "Should return list");
		assertEquals(0, scenarios.size(), "Should be empty");
	}

	/**
	 * Test getCalibrationDataSetFromXML with valid XML string and specs.
	 */
	@Test
	void testGetCalibrationDataSetFromXML_ValidXmlAndSpecs_ReturnsDataset() {
		// Arrange
		String xmlString = """
		<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
		<marketDataList>
		    <requestTimeStamp>20240115-170000</requestTimeStamp>
		    <item>
		        <id>ESTR_Swap-Rate_1Y</id>
		        <value>0.045</value>
		        <timeStamp>20240115-170000</timeStamp>
		    </item>
		    <item>
		        <id>Euribor6M_Swap-Rate_5Y</id>
		        <value>0.05</value>
		        <timeStamp>20240115-170000</timeStamp>
		    </item>
		</marketDataList>
		""";

		List<CalibrationDataItem.Spec> dataSpecs = new ArrayList<>();
		dataSpecs.add(new CalibrationDataItem.Spec("ESTR_Swap-Rate_1Y", "ESTR", "Swap-Rate", "1Y"));
		dataSpecs.add(new CalibrationDataItem.Spec("Euribor6M_Swap-Rate_5Y", "Euribor6M", "Swap-Rate", "5Y"));

		// Act
		CalibrationDataset dataset = CalibrationParserDataItems.getCalibrationDataSetFromXML(xmlString, dataSpecs);

		// Assert
		assertNotNull(dataset, "Dataset should not be null");
		assertEquals(2, dataset.getCalibrationDataItems().size(), "Should have two calibration data items");
		LocalDateTime expectedDateTime = LocalDateTime.parse("20240115-170000", DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
		assertEquals(expectedDateTime, dataset.getDate(), "Scenario date should match request timestamp");
	}

	/**
	 * Test getCalibrationDataSetFromXML with XML containing extra items not in specs.
	 * Should only include items matching specs.
	 */
	@Test
	void testGetCalibrationDataSetFromXML_XmlWithExtraItems_OnlyIncludesMatchingSpecs() {
		// Arrange
		String xmlString = """
		<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
		<marketDataList>
		    <requestTimeStamp>20240115-170000</requestTimeStamp>
		    <item>
		        <id>ESTR_Swap-Rate_1Y</id>
		        <value>0.045</value>
		        <timeStamp>20240115-170000</timeStamp>
		    </item>
		    <item>
		        <id>ExtraItem</id>
		        <value>0.055</value>
		        <timeStamp>20240115-170000</timeStamp>
		    </item>
		</marketDataList>
		""";

		List<CalibrationDataItem.Spec> dataSpecs = new ArrayList<>();
		dataSpecs.add(new CalibrationDataItem.Spec("ESTR_Swap-Rate_1Y", "ESTR", "Swap-Rate", "1Y"));

		// Act
		CalibrationDataset dataset = CalibrationParserDataItems.getCalibrationDataSetFromXML(xmlString, dataSpecs);

		// Assert
		assertNotNull(dataset, "Dataset should not be null");
		assertEquals(1, dataset.getCalibrationDataItems().size(), "Should have one calibration data item (ExtraItem filtered out)");
	}

	/**
	 * Test getCalibrationDataSetFromXML with no matching items.
	 * Should throw SDCException.
	 */
	@Test
	void testGetCalibrationDataSetFromXML_NoMatchingItems_ThrowsSDCException() {
		// Arrange
		String xmlString = """
		<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
		<marketDataList>
		    <requestTimeStamp>20240115-170000</requestTimeStamp>
		    <item>
		        <id>SomeOtherItem</id>
		        <value>0.045</value>
		        <timeStamp>20240115-170000</timeStamp>
		    </item>
		</marketDataList>
		""";

		List<CalibrationDataItem.Spec> dataSpecs = new ArrayList<>();
		dataSpecs.add(new CalibrationDataItem.Spec("ESTR_Swap-Rate_1Y", "ESTR", "Swap-Rate", "1Y"));

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			CalibrationParserDataItems.getCalibrationDataSetFromXML(xmlString, dataSpecs);
		}, "Should throw SDCException when no calibration items detected");

		assertEquals(ExceptionId.SDC_CALIBRATION_DATA_EMPTY, exception.getId(), "Exception should have correct ID");
	}

	/**
	 * Test getCalibrationDataSetFromXML with empty specs list.
	 * Should throw SDCException.
	 */
	@Test
	void testGetCalibrationDataSetFromXML_EmptySpecsList_ThrowsSDCException() {
		// Arrange
		String xmlString = """
		<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
		<marketDataList>
		    <requestTimeStamp>20240115-170000</requestTimeStamp>
		    <item>
		        <id>ESTR_Swap-Rate_1Y</id>
		        <value>0.045</value>
		        <timeStamp>20240115-170000</timeStamp>
		    </item>
		</marketDataList>
		""";

		List<CalibrationDataItem.Spec> dataSpecs = new ArrayList<>();

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			CalibrationParserDataItems.getCalibrationDataSetFromXML(xmlString, dataSpecs);
		}, "Should throw SDCException when specs list is empty");

		assertEquals(ExceptionId.SDC_CALIBRATION_DATA_EMPTY, exception.getId(), "Exception should have correct ID");
	}

	/**
	 * Test getCalibrationDataSetFromXML with multiple data points of same spec (fixing type).
	 * Should include all matching data points.
	 */
	@Test
	void testGetCalibrationDataSetFromXML_MultipleDataPointsSameSpec_IncludesAll() {
		// Arrange
		String xmlString = """
		<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
		<marketDataList>
		    <requestTimeStamp>20240115-170000</requestTimeStamp>
		    <item>
		        <id>Euribor6M_Fixing_1D</id>
		        <value>0.045</value>
		        <timeStamp>20240110-000000</timeStamp>
		    </item>
		    <item>
		        <id>Euribor6M_Fixing_1D</id>
		        <value>0.046</value>
		        <timeStamp>20240111-000000</timeStamp>
		    </item>
		</marketDataList>
		""";

		List<CalibrationDataItem.Spec> dataSpecs = new ArrayList<>();
		dataSpecs.add(new CalibrationDataItem.Spec("Euribor6M_Fixing_1D", "Euribor6M", "Fixing", "1D"));

		// Act
		CalibrationDataset dataset = CalibrationParserDataItems.getCalibrationDataSetFromXML(xmlString, dataSpecs);

		// Assert
		assertNotNull(dataset, "Dataset should not be null");
		// Both fixing items should be included in fixingDataItems since they have the same spec key but different timestamps
		int totalItems = dataset.getCalibrationDataItems().size() + dataset.getFixingDataItems().size();
		assertEquals(2, totalItems, "Should have two data items total (both fixings with different timestamps)");
	}

	/**
	 * Test getScenariosFromCSVFile.
	 * Should throw IOException as it's not yet implemented.
	 */
	@Test
	void testGetScenariosFromCSVFile_NotImplemented_ThrowsIOException() {
		// Arrange
		String fileName = "test.csv";

		// Act & Assert
		IOException exception = assertThrows(IOException.class, () -> {
			CalibrationParserDataItems.getScenariosFromCSVFile(fileName);
		}, "Should throw IOException as method is not implemented");

		assertEquals("to be implemented", exception.getMessage(), "Exception message should indicate not implemented");
	}
}
