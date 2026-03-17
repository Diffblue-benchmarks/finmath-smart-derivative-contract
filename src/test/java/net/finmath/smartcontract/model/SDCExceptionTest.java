package net.finmath.smartcontract.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SDCExceptionTest {

	@Test
	void testConstructorWithIdAndMessage() {
		ExceptionId id = ExceptionId.SDC_AUTH_ERROR;
		String message = "Authentication failed";

		SDCException exception = new SDCException(id, message);

		assertEquals(id, exception.getId());
		assertEquals("SDC_AUTH_ERROR Authentication failed", exception.getMessage());
		assertNull(exception.getStatusCode());
	}

	@Test
	void testConstructorWithIdMessageAndStatusCode() {
		ExceptionId id = ExceptionId.SDC_VALUATION_HTTP_ERROR;
		String message = "HTTP request failed";
		int statusCode = 500;

		SDCException exception = new SDCException(id, message, statusCode);

		assertEquals(id, exception.getId());
		assertEquals("SDC_VALUATION_HTTP_ERROR HTTP request failed", exception.getMessage());
		assertEquals(statusCode, exception.getStatusCode());
	}

	@Test
	void testGetId() {
		ExceptionId id = ExceptionId.SDC_TRADE_NOT_FOUND;
		SDCException exception = new SDCException(id, "Trade not found");

		assertEquals(id, exception.getId());
	}

	@Test
	void testGetStatusCode() {
		SDCException exception = new SDCException(ExceptionId.SDC_XML_PARSE_ERROR, "Parse error", 400);

		assertEquals(400, exception.getStatusCode());
	}

	@Test
	void testGetMessage() {
		ExceptionId id = ExceptionId.SDC_CALIBRATION_ERROR;
		String message = "Calibration failed";
		SDCException exception = new SDCException(id, message);

		assertEquals("SDC_CALIBRATION_ERROR Calibration failed", exception.getMessage());
	}
}
