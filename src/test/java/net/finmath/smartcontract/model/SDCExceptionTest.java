package net.finmath.smartcontract.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SDCExceptionTest {

	@Test
	void getMessage_shouldContainIdAndMessage() {
		SDCException exception = new SDCException(ExceptionId.SDC_WRONG_INPUT, "bad data");
		assertEquals("SDC_WRONG_INPUT bad data", exception.getMessage());
	}

	@Test
	void getStatusCode_shouldReturnNullWhenNotSet() {
		SDCException exception = new SDCException(ExceptionId.SDC_WRONG_INPUT, "test");
		assertNull(exception.getStatusCode());
	}

	@Test
	void getStatusCode_shouldReturnValueWhenSet() {
		SDCException exception = new SDCException(ExceptionId.SDC_XML_PARSE_ERROR, "parse error", 400);
		assertEquals(400, exception.getStatusCode());
	}

	@Test
	void getId_shouldReturnCorrectExceptionId() {
		SDCException exception = new SDCException(ExceptionId.SDC_CALIBRATION_ERROR, "calibration failed");
		assertEquals(ExceptionId.SDC_CALIBRATION_ERROR, exception.getId());
	}

	@Test
	void shouldBeRuntimeException() {
		SDCException exception = new SDCException(ExceptionId.SDC_WRONG_INPUT, "test");
		assertInstanceOf(RuntimeException.class, exception);
	}
}
