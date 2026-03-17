package net.finmath.smartcontract.valuation.marketdata.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MarketDataErrorsTest {

	@Test
	void testConstructorWithErrors() {
		final MarketDataErrors errors = new MarketDataErrors(true);

		assertTrue(errors.hasErrors());
	}

	@Test
	void testConstructorWithoutErrors() {
		final MarketDataErrors errors = new MarketDataErrors(false);

		assertFalse(errors.hasErrors());
	}

	@Test
	void testHasErrors() {
		final MarketDataErrors errorsTrue = new MarketDataErrors(true);
		final MarketDataErrors errorsFalse = new MarketDataErrors(false);

		assertTrue(errorsTrue.hasErrors());
		assertFalse(errorsFalse.hasErrors());
	}

	@Test
	void testGetMissingDataPoints() {
		final MarketDataErrors errors = new MarketDataErrors(true);

		final List<String> missingDataPoints = errors.getMissingDataPoints();

		assertNotNull(missingDataPoints);
		assertTrue(missingDataPoints.isEmpty());
	}

	@Test
	void testSetMissingDataPoints() {
		final MarketDataErrors errors = new MarketDataErrors(true);
		final List<String> missingDataPoints = new ArrayList<>();
		missingDataPoints.add("EURUSD");
		missingDataPoints.add("GBPUSD");

		errors.setMissingDataPoints(missingDataPoints);

		assertEquals(2, errors.getMissingDataPoints().size());
		assertTrue(errors.getMissingDataPoints().contains("EURUSD"));
		assertTrue(errors.getMissingDataPoints().contains("GBPUSD"));
	}

	@Test
	void testAddMissingData() {
		final MarketDataErrors errors = new MarketDataErrors(true);

		errors.addMissingData("ESTRSWP3Y");
		errors.addMissingData("EURIBOR6M");

		assertEquals(2, errors.getMissingDataPoints().size());
		assertTrue(errors.getMissingDataPoints().contains("ESTRSWP3Y"));
		assertTrue(errors.getMissingDataPoints().contains("EURIBOR6M"));
	}

	@Test
	void testGetErrorMessage() {
		final MarketDataErrors errors = new MarketDataErrors(true);

		assertNull(errors.getErrorMessage());
	}

	@Test
	void testSetErrorMessage() {
		final MarketDataErrors errors = new MarketDataErrors(true);
		final String errorMessage = "Market data unavailable";

		errors.setErrorMessage(errorMessage);

		assertEquals("Market data unavailable", errors.getErrorMessage());
	}

	@Test
	void testToString() {
		final MarketDataErrors errors = new MarketDataErrors(true);
		errors.setErrorMessage("Test error");
		errors.addMissingData("EURUSD");

		final String result = errors.toString();

		assertTrue(result.contains("hasErrors=true"));
		assertTrue(result.contains("missingDataPoints="));
		assertTrue(result.contains("EURUSD"));
		assertTrue(result.contains("errorMessage='Test error'"));
	}

	@Test
	void testToStringWithoutErrors() {
		final MarketDataErrors errors = new MarketDataErrors(false);

		final String result = errors.toString();

		assertTrue(result.contains("hasErrors=false"));
		assertTrue(result.contains("missingDataPoints="));
	}
}
