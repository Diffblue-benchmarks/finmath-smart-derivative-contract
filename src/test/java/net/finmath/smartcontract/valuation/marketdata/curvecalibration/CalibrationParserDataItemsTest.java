package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import net.finmath.smartcontract.model.ExceptionId;
import net.finmath.smartcontract.model.SDCException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalibrationParserDataItemsTest {

	private static final String MARKET_DATA_XML_TEMPLATE =
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
					"<marketDataList>" +
					"<requestTimeStamp>%s</requestTimeStamp>" +
					"%s" +
					"</marketDataList>";

	private static final String ITEM_TEMPLATE =
			"<item><id>%s</id><value>%s</value><timeStamp>%s</timeStamp></item>";

	@Test
	void testGetCalibrationDataSetFromXMLWithMatchingSpecs() {
		String timestamp = "20230615-120000";
		String items = String.format(ITEM_TEMPLATE, "ESTR_Swap-Rate_1Y", "0.035", timestamp)
				+ String.format(ITEM_TEMPLATE, "Euribor6M_Swap-Rate_2Y", "0.04", timestamp);
		String xml = String.format(MARKET_DATA_XML_TEMPLATE, timestamp, items);

		List<CalibrationDataItem.Spec> specs = List.of(
				new CalibrationDataItem.Spec("ESTR_Swap-Rate_1Y", "ESTR", "Swap-Rate", "1Y"),
				new CalibrationDataItem.Spec("Euribor6M_Swap-Rate_2Y", "Euribor6M", "Swap-Rate", "2Y")
		);

		CalibrationDataset result = CalibrationParserDataItems.getCalibrationDataSetFromXML(xml, specs);

		assertNotNull(result);
		assertEquals(2, result.getDataPoints().size());
		assertNotNull(result.getDate());
	}

	@Test
	void testGetCalibrationDataSetFromXMLWithSingleSpec() {
		String timestamp = "20230615-120000";
		String items = String.format(ITEM_TEMPLATE, "ESTR_Swap-Rate_5Y", "0.032", timestamp);
		String xml = String.format(MARKET_DATA_XML_TEMPLATE, timestamp, items);

		List<CalibrationDataItem.Spec> specs = List.of(
				new CalibrationDataItem.Spec("ESTR_Swap-Rate_5Y", "ESTR", "Swap-Rate", "5Y")
		);

		CalibrationDataset result = CalibrationParserDataItems.getCalibrationDataSetFromXML(xml, specs);

		assertNotNull(result);
		assertEquals(1, result.getDataPoints().size());
	}

	@Test
	void testGetCalibrationDataSetFromXMLThrowsWhenNoMatchingItems() {
		String timestamp = "20230615-120000";
		String items = String.format(ITEM_TEMPLATE, "ESTR_Swap-Rate_1Y", "0.035", timestamp);
		String xml = String.format(MARKET_DATA_XML_TEMPLATE, timestamp, items);

		List<CalibrationDataItem.Spec> specs = List.of(
				new CalibrationDataItem.Spec("NON_EXISTENT_KEY", "ESTR", "Swap-Rate", "10Y")
		);

		SDCException exception = assertThrows(SDCException.class,
				() -> CalibrationParserDataItems.getCalibrationDataSetFromXML(xml, specs));
		assertEquals(ExceptionId.SDC_CALIBRATION_DATA_EMPTY, exception.getId());
	}

	@Test
	void testGetCalibrationDataSetFromXMLThrowsWhenEmptySpecs() {
		String timestamp = "20230615-120000";
		String items = String.format(ITEM_TEMPLATE, "ESTR_Swap-Rate_1Y", "0.035", timestamp);
		String xml = String.format(MARKET_DATA_XML_TEMPLATE, timestamp, items);

		List<CalibrationDataItem.Spec> specs = new ArrayList<>();

		SDCException exception = assertThrows(SDCException.class,
				() -> CalibrationParserDataItems.getCalibrationDataSetFromXML(xml, specs));
		assertEquals(ExceptionId.SDC_CALIBRATION_DATA_EMPTY, exception.getId());
	}

	@Test
	void testGetCalibrationDataSetFromXMLPartialMatch() {
		String timestamp = "20230615-120000";
		String items = String.format(ITEM_TEMPLATE, "ESTR_Swap-Rate_1Y", "0.035", timestamp)
				+ String.format(ITEM_TEMPLATE, "Euribor6M_Swap-Rate_2Y", "0.04", timestamp);
		String xml = String.format(MARKET_DATA_XML_TEMPLATE, timestamp, items);

		List<CalibrationDataItem.Spec> specs = List.of(
				new CalibrationDataItem.Spec("ESTR_Swap-Rate_1Y", "ESTR", "Swap-Rate", "1Y"),
				new CalibrationDataItem.Spec("NON_EXISTENT_KEY", "ESTR", "Swap-Rate", "99Y")
		);

		CalibrationDataset result = CalibrationParserDataItems.getCalibrationDataSetFromXML(xml, specs);

		assertNotNull(result);
		assertEquals(1, result.getDataPoints().size());
	}
}
