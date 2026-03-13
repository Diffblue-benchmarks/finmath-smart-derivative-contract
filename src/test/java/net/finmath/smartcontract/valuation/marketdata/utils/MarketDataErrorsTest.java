package net.finmath.smartcontract.valuation.marketdata.utils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MarketDataErrorsTest {

	@Test
	void constructorWithErrors_shouldSetHasErrors() {
		MarketDataErrors errors = new MarketDataErrors(true);
		assertTrue(errors.hasErrors());
	}

	@Test
	void constructorWithoutErrors_shouldSetHasErrorsFalse() {
		MarketDataErrors errors = new MarketDataErrors(false);
		assertFalse(errors.hasErrors());
	}

	@Test
	void addMissingData_shouldAccumulate() {
		MarketDataErrors errors = new MarketDataErrors(true);
		errors.addMissingData("EUR6M_1Y");
		errors.addMissingData("ESTR_5Y");
		assertEquals(2, errors.getMissingDataPoints().size());
		assertTrue(errors.getMissingDataPoints().contains("EUR6M_1Y"));
		assertTrue(errors.getMissingDataPoints().contains("ESTR_5Y"));
	}

	@Test
	void setMissingDataPoints_shouldReplaceList() {
		MarketDataErrors errors = new MarketDataErrors(true);
		errors.addMissingData("old");
		errors.setMissingDataPoints(List.of("new1", "new2"));
		assertEquals(2, errors.getMissingDataPoints().size());
		assertFalse(errors.getMissingDataPoints().contains("old"));
	}

	@Test
	void errorMessage_shouldBeSettableAndGettable() {
		MarketDataErrors errors = new MarketDataErrors(true);
		errors.setErrorMessage("something went wrong");
		assertEquals("something went wrong", errors.getErrorMessage());
	}

	@Test
	void toString_shouldContainRelevantInfo() {
		MarketDataErrors errors = new MarketDataErrors(true);
		errors.setErrorMessage("test error");
		errors.addMissingData("EUR6M");
		String str = errors.toString();
		assertTrue(str.contains("hasErrors=true"));
		assertTrue(str.contains("test error"));
		assertTrue(str.contains("EUR6M"));
	}
}
