package net.finmath.smartcontract.valuation.marketdata.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for MarketDataErrors class.
 */
class MarketDataErrorsTest {

    @Test
    void testConstructorWithFalse() {
        MarketDataErrors errors = new MarketDataErrors(false);

        assertFalse(errors.hasErrors());
        assertNotNull(errors.getMissingDataPoints());
        assertEquals(0, errors.getMissingDataPoints().size());
    }

    @Test
    void testConstructorWithTrue() {
        MarketDataErrors errors = new MarketDataErrors(true);

        assertTrue(errors.hasErrors());
        assertNotNull(errors.getMissingDataPoints());
    }

    @Test
    void testHasErrors() {
        MarketDataErrors errorsTrue = new MarketDataErrors(true);
        MarketDataErrors errorsFalse = new MarketDataErrors(false);

        assertTrue(errorsTrue.hasErrors());
        assertFalse(errorsFalse.hasErrors());
    }

    @Test
    void testAddMissingData() {
        MarketDataErrors errors = new MarketDataErrors(true);

        errors.addMissingData("EUR-SWAP-5Y");

        assertEquals(1, errors.getMissingDataPoints().size());
        assertTrue(errors.getMissingDataPoints().contains("EUR-SWAP-5Y"));
    }

    @Test
    void testAddMultipleMissingData() {
        MarketDataErrors errors = new MarketDataErrors(true);

        errors.addMissingData("EUR-SWAP-5Y");
        errors.addMissingData("USD-LIBOR-3M");
        errors.addMissingData("GBP-SONIA-1Y");

        assertEquals(3, errors.getMissingDataPoints().size());
        assertTrue(errors.getMissingDataPoints().contains("EUR-SWAP-5Y"));
        assertTrue(errors.getMissingDataPoints().contains("USD-LIBOR-3M"));
        assertTrue(errors.getMissingDataPoints().contains("GBP-SONIA-1Y"));
    }

    @Test
    void testSetAndGetMissingDataPoints() {
        MarketDataErrors errors = new MarketDataErrors(true);
        List<String> missingPoints = Arrays.asList("POINT-1", "POINT-2", "POINT-3");

        errors.setMissingDataPoints(missingPoints);

        assertEquals(missingPoints, errors.getMissingDataPoints());
        assertEquals(3, errors.getMissingDataPoints().size());
    }

    @Test
    void testSetAndGetErrorMessage() {
        MarketDataErrors errors = new MarketDataErrors(true);
        String errorMessage = "Unable to fetch market data from provider";

        errors.setErrorMessage(errorMessage);

        assertEquals(errorMessage, errors.getErrorMessage());
    }

    @Test
    void testToString() {
        MarketDataErrors errors = new MarketDataErrors(true);
        errors.addMissingData("EUR-SWAP-10Y");
        errors.setErrorMessage("Data unavailable");

        String result = errors.toString();

        assertNotNull(result);
        assertTrue(result.contains("MarketDataErrors"));
        assertTrue(result.contains("hasErrors=true"));
        assertTrue(result.contains("EUR-SWAP-10Y"));
        assertTrue(result.contains("Data unavailable"));
    }

    @Test
    void testToStringWithNoErrors() {
        MarketDataErrors errors = new MarketDataErrors(false);

        String result = errors.toString();

        assertNotNull(result);
        assertTrue(result.contains("hasErrors=false"));
    }

    @Test
    void testDefaultMissingDataPointsIsEmpty() {
        MarketDataErrors errors = new MarketDataErrors(true);

        assertTrue(errors.getMissingDataPoints().isEmpty());
    }

    @Test
    void testErrorMessageDefaultIsNull() {
        MarketDataErrors errors = new MarketDataErrors(true);

        assertNull(errors.getErrorMessage());
    }

    @Test
    void testSetEmptyMissingDataPoints() {
        MarketDataErrors errors = new MarketDataErrors(true);
        errors.addMissingData("TEST-1");

        errors.setMissingDataPoints(Arrays.asList());

        assertEquals(0, errors.getMissingDataPoints().size());
    }

    @Test
    void testSetNullErrorMessage() {
        MarketDataErrors errors = new MarketDataErrors(true);
        errors.setErrorMessage("Test error");

        errors.setErrorMessage(null);

        assertNull(errors.getErrorMessage());
    }

    @Test
    void testSetEmptyErrorMessage() {
        MarketDataErrors errors = new MarketDataErrors(true);

        errors.setErrorMessage("");

        assertEquals("", errors.getErrorMessage());
    }

    @Test
    void testAddNullMissingData() {
        MarketDataErrors errors = new MarketDataErrors(true);

        errors.addMissingData(null);

        assertEquals(1, errors.getMissingDataPoints().size());
        assertTrue(errors.getMissingDataPoints().contains(null));
    }

    @Test
    void testCombinedErrorState() {
        MarketDataErrors errors = new MarketDataErrors(true);
        errors.addMissingData("EUR-SWAP-5Y");
        errors.addMissingData("USD-LIBOR-3M");
        errors.setErrorMessage("Multiple data points missing");

        assertTrue(errors.hasErrors());
        assertEquals(2, errors.getMissingDataPoints().size());
        assertEquals("Multiple data points missing", errors.getErrorMessage());
    }
}
