package net.finmath.smartcontract.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SDCExceptionTest {

    @Test
    void testConstructorWithIdAndMessage() {
        SDCException exception = new SDCException(ExceptionId.SDC_WRONG_INPUT, "test message");

        Assertions.assertEquals(ExceptionId.SDC_WRONG_INPUT, exception.getId());
        Assertions.assertEquals("SDC_WRONG_INPUT test message", exception.getMessage());
    }

    @Test
    void testGetId() {
        SDCException exception = new SDCException(ExceptionId.SDC_AUTH_ERROR, "auth failed");

        Assertions.assertEquals(ExceptionId.SDC_AUTH_ERROR, exception.getId());
    }

    @Test
    void testGetStatusCodeNullWhenNotSet() {
        SDCException exception = new SDCException(ExceptionId.SDC_WRONG_INPUT, "test message");

        Assertions.assertNull(exception.getStatusCode());
    }

    @Test
    void testGetStatusCodeWhenSet() {
        SDCException exception = new SDCException(ExceptionId.SDC_VALUATION_HTTP_ERROR, "http error", 404);

        Assertions.assertEquals(404, exception.getStatusCode());
    }
}
