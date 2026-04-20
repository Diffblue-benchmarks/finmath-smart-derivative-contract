package net.finmath.smartcontract.valuation.oracle;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SmartDerivativeContractSettlementOracleTest {

	private final LocalDateTime periodStart = LocalDateTime.of(2024, 1, 1, 10, 0);
	private final LocalDateTime periodEnd = LocalDateTime.of(2024, 1, 2, 10, 0);

	@Test
	void testConstructor() {
		ValuationOracle oracle = new StubValuationOracle(Map.of("party1", BigDecimal.TEN));
		SmartDerivativeContractSettlementOracle settlementOracle = new SmartDerivativeContractSettlementOracle(oracle);
		assertNotNull(settlementOracle);
	}

	@Test
	void testGetMarginSingleKey() {
		Map<String, BigDecimal> currentValues = Map.of("party1", new BigDecimal("105"));
		Map<String, BigDecimal> previousValues = Map.of("party1", new BigDecimal("100"));

		ValuationOracle oracle = new StubValuationOracle(currentValues, previousValues);
		SmartDerivativeContractSettlementOracle settlementOracle = new SmartDerivativeContractSettlementOracle(oracle);

		Map<String, BigDecimal> margin = settlementOracle.getMargin(periodStart, periodEnd);

		assertNotNull(margin);
		assertEquals(1, margin.size());
		assertEquals(new BigDecimal("5"), margin.get("party1"));
	}

	@Test
	void testGetMarginMultipleKeys() {
		Map<String, BigDecimal> currentValues = Map.of(
				"party1", new BigDecimal("200"),
				"party2", new BigDecimal("50")
		);
		Map<String, BigDecimal> previousValues = Map.of(
				"party1", new BigDecimal("180"),
				"party2", new BigDecimal("70")
		);

		ValuationOracle oracle = new StubValuationOracle(currentValues, previousValues);
		SmartDerivativeContractSettlementOracle settlementOracle = new SmartDerivativeContractSettlementOracle(oracle);

		Map<String, BigDecimal> margin = settlementOracle.getMargin(periodStart, periodEnd);

		assertEquals(2, margin.size());
		assertEquals(new BigDecimal("20"), margin.get("party1"));
		assertEquals(new BigDecimal("-20"), margin.get("party2"));
	}

	/**
	 * Simple stub implementation of ValuationOracle for testing.
	 * Returns currentValues when evaluationTime equals marketDataTime (current valuation),
	 * and previousValues when they differ (previous valuation).
	 */
	private static class StubValuationOracle implements ValuationOracle {

		private final Map<String, BigDecimal> currentValues;
		private final Map<String, BigDecimal> previousValues;

		StubValuationOracle(Map<String, BigDecimal> values) {
			this(values, values);
		}

		StubValuationOracle(Map<String, BigDecimal> currentValues, Map<String, BigDecimal> previousValues) {
			this.currentValues = currentValues;
			this.previousValues = previousValues;
		}

		@Override
		public BigDecimal getValue(LocalDateTime evaluationTime, LocalDateTime marketDataTime) {
			if (evaluationTime.equals(marketDataTime)) {
				return currentValues.values().iterator().next();
			}
			return previousValues.values().iterator().next();
		}

		@Override
		public Map<String, BigDecimal> getValues(LocalDateTime evaluationTime, LocalDateTime marketDataTime) {
			if (evaluationTime.equals(marketDataTime)) {
				return currentValues;
			}
			return previousValues;
		}

		@Override
		public javax.money.MonetaryAmount getAmount(LocalDateTime evaluationTime, LocalDateTime marketDataTime) {
			throw new UnsupportedOperationException("Not needed for this test");
		}
	}
}
