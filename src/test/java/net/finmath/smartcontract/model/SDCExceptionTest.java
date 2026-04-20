package net.finmath.smartcontract.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SDCExceptionTest {

	@Test
	void testConstructorWithoutStatusCode() {
		SDCException exception = new SDCException(ExceptionId.SDC_TRADE_NOT_FOUND, "trade missing");

		assertEquals(ExceptionId.SDC_TRADE_NOT_FOUND, exception.getId());
		assertEquals("SDC_TRADE_NOT_FOUND trade missing", exception.getMessage());
		assertNull(exception.getStatusCode());
	}

	@Test
	void testConstructorWithStatusCode() {
		SDCException exception = new SDCException(ExceptionId.SDC_AUTH_ERROR, "unauthorized", 401);

		assertEquals(ExceptionId.SDC_AUTH_ERROR, exception.getId());
		assertEquals("SDC_AUTH_ERROR unauthorized", exception.getMessage());
		assertEquals(401, exception.getStatusCode());
	}
}
