package net.finmath.smartcontract.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ExceptionId enum.
 */
class ExceptionIdTest {

    @Test
    void testEnumValues() {
        ExceptionId[] values = ExceptionId.values();
        assertNotNull(values);
        assertTrue(values.length > 0);
    }

    @Test
    void testEnumContainsAuthError() {
        assertNotNull(ExceptionId.valueOf("SDC_AUTH_ERROR"));
    }

    @Test
    void testEnumContainsTradeNotFound() {
        assertNotNull(ExceptionId.valueOf("SDC_TRADE_NOT_FOUND"));
    }

    @Test
    void testEnumContainsValuationErrors() {
        assertNotNull(ExceptionId.valueOf("SDC_VALUE_CALCULATION_ERROR"));
        assertNotNull(ExceptionId.valueOf("SDC_VALUATION_HTTP_ERROR"));
        assertNotNull(ExceptionId.valueOf("SDC_VALUATION_TIMEOUT_ERROR"));
    }

    @Test
    void testEnumContainsFabricErrors() {
        assertNotNull(ExceptionId.valueOf("SDC_FABRIC_GETALLPAYMENTS_ERROR"));
        assertNotNull(ExceptionId.valueOf("SDC_FABRIC_CREATEPAYMENT_ERROR"));
        assertNotNull(ExceptionId.valueOf("SDC_FABRIC_CORRELATION_ID_ALREADY_IN_USE"));
    }

    @Test
    void testEnumToString() {
        assertEquals("SDC_AUTH_ERROR", ExceptionId.SDC_AUTH_ERROR.toString());
        assertEquals("SDC_WRONG_INPUT", ExceptionId.SDC_WRONG_INPUT.toString());
    }

    @Test
    void testValueOf() {
        assertEquals(ExceptionId.SDC_NO_DATA_FOUND, ExceptionId.valueOf("SDC_NO_DATA_FOUND"));
    }

    @Test
    void testInvalidValueOfThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            ExceptionId.valueOf("INVALID_EXCEPTION_ID");
        });
    }
}
