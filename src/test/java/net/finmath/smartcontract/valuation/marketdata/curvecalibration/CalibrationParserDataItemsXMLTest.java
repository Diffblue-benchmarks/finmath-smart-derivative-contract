package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import net.finmath.smartcontract.model.SDCException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalibrationParserDataItemsXMLTest {

	@Test
	void getCalibrationDataSetFromXML_shouldParseMarketDataXml() throws IOException {
		String xmlContent;
		try (InputStream is = getClass().getResourceAsStream(
				"/net/finmath/smartcontract/valuation/client/md_testset1.xml")) {
			xmlContent = new String(is.readAllBytes(), StandardCharsets.UTF_8);
		}

		// Create specs matching some of the data points in md_testset1.xml
		List<CalibrationDataItem.Spec> specs = List.of(
				new CalibrationDataItem.Spec("ESTRSWP1Y", "ESTR", "Swap-Rate", "1Y"),
				new CalibrationDataItem.Spec("ESTRSWP5Y", "ESTR", "Swap-Rate", "5Y")
		);

		CalibrationDataset dataset = CalibrationParserDataItems.getCalibrationDataSetFromXML(xmlContent, specs);
		assertNotNull(dataset);
		assertFalse(dataset.getCalibrationDataItems().isEmpty());
	}

	@Test
	void getCalibrationDataSetFromXML_shouldThrow_whenNoMatchingSpecs() throws IOException {
		String xmlContent;
		try (InputStream is = getClass().getResourceAsStream(
				"/net/finmath/smartcontract/valuation/client/md_testset1.xml")) {
			xmlContent = new String(is.readAllBytes(), StandardCharsets.UTF_8);
		}

		List<CalibrationDataItem.Spec> specs = List.of(
				new CalibrationDataItem.Spec("NONEXISTENT", "X", "Y", "Z")
		);

		assertThrows(SDCException.class,
				() -> CalibrationParserDataItems.getCalibrationDataSetFromXML(xmlContent, specs));
	}

	@Test
	void getScenariosFromJsonFile_shouldThrow_forMissingFile() {
		assertThrows(Exception.class,
				() -> CalibrationParserDataItems.getScenariosFromJsonFile("/nonexistent_file.json"));
	}

	@Test
	void getScenariosFromJsonFile_shouldReturnSortedScenarios() throws IOException {
		List<CalibrationDataset> scenarios = CalibrationParserDataItems.getScenariosFromJsonFile(
				"/net/finmath/smartcontract/valuation/marketdata/curvecalibration/timeseriesdatamap.json");
		assertTrue(scenarios.size() > 1);
		// Verify sorted by date
		for (int i = 1; i < scenarios.size(); i++) {
			assertTrue(scenarios.get(i).getDate().compareTo(scenarios.get(i - 1).getDate()) >= 0);
		}
	}
}
