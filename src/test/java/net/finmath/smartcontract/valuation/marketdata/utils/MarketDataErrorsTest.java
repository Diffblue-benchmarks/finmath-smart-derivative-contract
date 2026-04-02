package net.finmath.smartcontract.valuation.marketdata.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class MarketDataErrorsTest {

	@Test
	void testConstructorWithHasErrorsTrue() {
		MarketDataErrors errors = new MarketDataErrors(true);

		Assertions.assertTrue(errors.hasErrors());
	}

	@Test
	void testConstructorWithHasErrorsFalse() {
		MarketDataErrors errors = new MarketDataErrors(false);

		Assertions.assertFalse(errors.hasErrors());
	}

	@Test
	void testGetMissingDataPointsInitiallyEmpty() {
		MarketDataErrors errors = new MarketDataErrors(false);

		Assertions.assertTrue(errors.getMissingDataPoints().isEmpty());
	}

	@Test
	void testSetMissingDataPoints() {
		MarketDataErrors errors = new MarketDataErrors(true);
		List<String> points = Arrays.asList("point1", "point2");

		errors.setMissingDataPoints(points);

		Assertions.assertEquals(points, errors.getMissingDataPoints());
	}

	@Test
	void testAddMissingData() {
		MarketDataErrors errors = new MarketDataErrors(true);

		errors.addMissingData("EURIBOR_6M");

		Assertions.assertEquals(1, errors.getMissingDataPoints().size());
		Assertions.assertEquals("EURIBOR_6M", errors.getMissingDataPoints().get(0));
	}

	@Test
	void testGetErrorMessageInitiallyNull() {
		MarketDataErrors errors = new MarketDataErrors(false);

		Assertions.assertNull(errors.getErrorMessage());
	}

	@Test
	void testSetErrorMessage() {
		MarketDataErrors errors = new MarketDataErrors(true);

		errors.setErrorMessage("Some error occurred");

		Assertions.assertEquals("Some error occurred", errors.getErrorMessage());
	}

	@Test
	void testToString() {
		MarketDataErrors errors = new MarketDataErrors(true);
		errors.setErrorMessage("test error");
		errors.addMissingData("point1");

		String result = errors.toString();

		Assertions.assertTrue(result.contains("hasErrors=true"));
		Assertions.assertTrue(result.contains("missingDataPoints=[point1]"));
		Assertions.assertTrue(result.contains("errorMessage='test error'"));
	}
}
