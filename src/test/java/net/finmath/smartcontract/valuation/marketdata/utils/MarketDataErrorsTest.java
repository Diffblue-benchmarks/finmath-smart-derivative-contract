package net.finmath.smartcontract.valuation.marketdata.utils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MarketDataErrorsTest {

	@Test
	void testConstructorWithErrors() {
		MarketDataErrors errors = new MarketDataErrors(true);
		assertTrue(errors.hasErrors());
		assertNotNull(errors.getMissingDataPoints());
		assertTrue(errors.getMissingDataPoints().isEmpty());
	}

	@Test
	void testConstructorWithoutErrors() {
		MarketDataErrors errors = new MarketDataErrors(false);
		assertFalse(errors.hasErrors());
	}

	@Test
	void testSetAndGetMissingDataPoints() {
		MarketDataErrors errors = new MarketDataErrors(true);
		List<String> points = List.of("EUR-EURIBOR-6M", "USD-LIBOR-3M");
		errors.setMissingDataPoints(points);
		assertEquals(points, errors.getMissingDataPoints());
	}

	@Test
	void testAddMissingData() {
		MarketDataErrors errors = new MarketDataErrors(true);
		errors.addMissingData("EUR-EURIBOR-6M");
		errors.addMissingData("USD-LIBOR-3M");
		assertEquals(2, errors.getMissingDataPoints().size());
		assertEquals("EUR-EURIBOR-6M", errors.getMissingDataPoints().get(0));
	}

	@Test
	void testSetAndGetErrorMessage() {
		MarketDataErrors errors = new MarketDataErrors(true);
		errors.setErrorMessage("Market data incomplete");
		assertEquals("Market data incomplete", errors.getErrorMessage());
	}

	@Test
	void testToString() {
		MarketDataErrors errors = new MarketDataErrors(true);
		errors.setErrorMessage("Some error");
		errors.addMissingData("POINT1");
		String result = errors.toString();
		assertTrue(result.contains("hasErrors=true"));
		assertTrue(result.contains("POINT1"));
		assertTrue(result.contains("Some error"));
	}
}
