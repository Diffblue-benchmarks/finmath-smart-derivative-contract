package net.finmath.smartcontract.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for SDCException class.
 */
class SDCExceptionTest {

    @Test
    void testConstructorWithIdAndMessage() {
        String message = "Test error message";
        SDCException exception = new SDCException(ExceptionId.SDC_AUTH_ERROR, message);

        assertNotNull(exception);
        assertEquals(ExceptionId.SDC_AUTH_ERROR, exception.getId());
        assertNull(exception.getStatusCode());
    }

    @Test
    void testConstructorWithIdMessageAndStatusCode() {
        String message = "Test error with status code";
        int statusCode = 404;
        SDCException exception = new SDCException(ExceptionId.SDC_TRADE_NOT_FOUND, message, statusCode);

        assertNotNull(exception);
        assertEquals(ExceptionId.SDC_TRADE_NOT_FOUND, exception.getId());
        assertEquals(statusCode, exception.getStatusCode());
    }

    @Test
    void testGetMessage_CombinesIdAndMessage() {
        String message = "Authentication failed";
        SDCException exception = new SDCException(ExceptionId.SDC_AUTH_ERROR, message);

        String fullMessage = exception.getMessage();
        assertTrue(fullMessage.contains("SDC_AUTH_ERROR"));
        assertTrue(fullMessage.contains(message));
    }

    @Test
    void testGetMessage_WithDifferentExceptionIds() {
        SDCException exception1 = new SDCException(ExceptionId.SDC_CALIBRATION_ERROR, "Calibration failed");
        SDCException exception2 = new SDCException(ExceptionId.SDC_XML_PARSE_ERROR, "XML parsing failed");

        assertTrue(exception1.getMessage().contains("SDC_CALIBRATION_ERROR"));
        assertTrue(exception2.getMessage().contains("SDC_XML_PARSE_ERROR"));
    }

    @Test
    void testIsRuntimeException() {
        SDCException exception = new SDCException(ExceptionId.SDC_AUTH_ERROR, "Test");
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testThrowingException() {
        assertThrows(SDCException.class, () -> {
            throw new SDCException(ExceptionId.SDC_VALUATION_TIMEOUT_ERROR, "Timeout occurred");
        });
    }

    @Test
    void testExceptionWithNullMessage() {
        SDCException exception = new SDCException(ExceptionId.SDC_NO_DATA_FOUND, null);
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("SDC_NO_DATA_FOUND"));
    }

    @Test
    void testExceptionWithEmptyMessage() {
        SDCException exception = new SDCException(ExceptionId.SDC_WRONG_INPUT, "");
        String message = exception.getMessage();
        assertTrue(message.contains("SDC_WRONG_INPUT"));
    }

    @Test
    void testStatusCodeForHttpErrors() {
        SDCException exception = new SDCException(ExceptionId.SDC_VALUATION_HTTP_ERROR, "HTTP Error", 500);
        assertEquals(500, exception.getStatusCode());
    }
}
