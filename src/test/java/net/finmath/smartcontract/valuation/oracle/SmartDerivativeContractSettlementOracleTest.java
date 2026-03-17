/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 17 Mar 2026
 */

package net.finmath.smartcontract.valuation.oracle;

import org.junit.jupiter.api.Test;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test for SmartDerivativeContractSettlementOracle.
 *
 * @author Christian Fries
 */
class SmartDerivativeContractSettlementOracleTest {

	@Test
	void testConstructor() {
		// Given
		final ValuationOracle valuationOracle = new TestValuationOracle();

		// When
		final SmartDerivativeContractSettlementOracle settlementOracle = new SmartDerivativeContractSettlementOracle(valuationOracle);

		// Then
		assertNotNull(settlementOracle);
	}

	@Test
	void testGetMargin() {
		// Given
		final LocalDateTime periodStart = LocalDateTime.of(2024, 1, 1, 10, 0);
		final LocalDateTime periodEnd = LocalDateTime.of(2024, 1, 2, 10, 0);

		final Map<String, BigDecimal> currentValues = new HashMap<>();
		currentValues.put("derivative1", new BigDecimal("100.00"));
		currentValues.put("derivative2", new BigDecimal("200.00"));

		final Map<String, BigDecimal> previousValues = new HashMap<>();
		previousValues.put("derivative1", new BigDecimal("80.00"));
		previousValues.put("derivative2", new BigDecimal("150.00"));

		final ValuationOracle valuationOracle = new TestValuationOracle(currentValues, previousValues);
		final SmartDerivativeContractSettlementOracle settlementOracle = new SmartDerivativeContractSettlementOracle(valuationOracle);

		// When
		final Map<String, BigDecimal> margin = settlementOracle.getMargin(periodStart, periodEnd);

		// Then
		assertNotNull(margin);
		assertEquals(2, margin.size());
		assertEquals(new BigDecimal("20.00"), margin.get("derivative1"));
		assertEquals(new BigDecimal("50.00"), margin.get("derivative2"));
	}

	@Test
	void testGetMarginWithNegativeValues() {
		// Given
		final LocalDateTime periodStart = LocalDateTime.of(2024, 1, 1, 10, 0);
		final LocalDateTime periodEnd = LocalDateTime.of(2024, 1, 2, 10, 0);

		final Map<String, BigDecimal> currentValues = new HashMap<>();
		currentValues.put("derivative1", new BigDecimal("50.00"));

		final Map<String, BigDecimal> previousValues = new HashMap<>();
		previousValues.put("derivative1", new BigDecimal("100.00"));

		final ValuationOracle valuationOracle = new TestValuationOracle(currentValues, previousValues);
		final SmartDerivativeContractSettlementOracle settlementOracle = new SmartDerivativeContractSettlementOracle(valuationOracle);

		// When
		final Map<String, BigDecimal> margin = settlementOracle.getMargin(periodStart, periodEnd);

		// Then
		assertNotNull(margin);
		assertEquals(1, margin.size());
		assertEquals(new BigDecimal("-50.00"), margin.get("derivative1"));
	}

	/**
	 * Simple test implementation of ValuationOracle for testing purposes.
	 */
	private static class TestValuationOracle implements ValuationOracle {

		private final Map<String, BigDecimal> currentValues;
		private final Map<String, BigDecimal> previousValues;

		TestValuationOracle() {
			this.currentValues = new HashMap<>();
			this.previousValues = new HashMap<>();
		}

		TestValuationOracle(final Map<String, BigDecimal> currentValues, final Map<String, BigDecimal> previousValues) {
			this.currentValues = currentValues;
			this.previousValues = previousValues;
		}

		@Override
		public BigDecimal getValue(final LocalDateTime evaluationTime, final LocalDateTime marketDataTime) {
			return BigDecimal.ZERO;
		}

		@Override
		public Map<String, BigDecimal> getValues(final LocalDateTime evaluationTime, final LocalDateTime marketDataTime) {
			if (evaluationTime.equals(marketDataTime)) {
				return currentValues;
			} else {
				return previousValues;
			}
		}

		@Override
		public MonetaryAmount getAmount(final LocalDateTime evaluationTime, final LocalDateTime marketDataTime) {
			return null;
		}
	}
}
