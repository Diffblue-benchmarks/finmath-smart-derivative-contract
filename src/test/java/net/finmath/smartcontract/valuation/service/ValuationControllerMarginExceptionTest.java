package net.finmath.smartcontract.valuation.service;

import net.finmath.smartcontract.model.ExceptionId;
import net.finmath.smartcontract.model.MarginRequest;
import net.finmath.smartcontract.model.SDCException;
import net.finmath.smartcontract.valuation.service.controllers.ValuationController;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValuationControllerMarginExceptionTest {

	@Test
	void testMarginWithInvalidXmlThrowsSDCException() {
		ValuationController controller = new ValuationController();
		String invalidXml = "this is not valid xml <><>";

		MarginRequest marginRequest = new MarginRequest()
				.marketDataStart("<marketData/>")
				.marketDataEnd("<marketData/>")
				.tradeData(invalidXml)
				.valuationDate(OffsetDateTime.now().toString());

		SDCException exception = assertThrows(SDCException.class, () -> controller.margin(marginRequest));
		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId());
	}

	@Test
	void testMarginWithValidXmlButWrongStructureThrowsSDCException() {
		ValuationController controller = new ValuationController();
		String validButWrongXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><element>data</element></root>";

		MarginRequest marginRequest = new MarginRequest()
				.marketDataStart(validButWrongXml)
				.marketDataEnd(validButWrongXml)
				.tradeData(validButWrongXml)
				.valuationDate(OffsetDateTime.now().toString());

		SDCException exception = assertThrows(SDCException.class, () -> controller.margin(marginRequest));
		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId());
	}
}
