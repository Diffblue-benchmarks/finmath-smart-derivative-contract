package net.finmath.smartcontract.valuation.oracle;

import net.finmath.smartcontract.valuation.oracle.simulated.GeometricBrownianMotionOracle;
import org.junit.jupiter.api.Test;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValuationOracleSamplePathTest {

	private static final LocalDateTime initialTime = LocalDateTime.of(2018, 8, 12, 12, 0);
	private static final LocalDateTime laterTime = LocalDateTime.of(2020, 8, 12, 12, 0);

	@Test
	void testGetValue() {
		final StochasticValuationOracle stochasticOracle = new GeometricBrownianMotionOracle(initialTime);
		final ValuationOracleSamplePath oracle = new ValuationOracleSamplePath(stochasticOracle, 0);

		BigDecimal value = oracle.getValue(initialTime, laterTime);

		assertNotNull(value);
		assertTrue(value.doubleValue() > 0.0);
	}

	@Test
	void testGetValues() {
		final StochasticValuationOracle stochasticOracle = new GeometricBrownianMotionOracle(initialTime);
		final ValuationOracleSamplePath oracle = new ValuationOracleSamplePath(stochasticOracle, 0);

		Map<String, BigDecimal> values = oracle.getValues(initialTime, laterTime);

		assertNotNull(values);
		assertTrue(values.containsKey("value"));
		assertEquals(oracle.getValue(initialTime, laterTime), values.get("value"));
	}

	@Test
	void testGetAmount() {
		final StochasticValuationOracle stochasticOracle = new GeometricBrownianMotionOracle(initialTime);
		final ValuationOracleSamplePath oracle = new ValuationOracleSamplePath(stochasticOracle, 0);

		MonetaryAmount amount = oracle.getAmount(initialTime, laterTime);

		assertNotNull(amount);
		assertEquals("EUR", amount.getCurrency().getCurrencyCode());
		assertEquals(oracle.getValue(initialTime, laterTime).doubleValue(), amount.getNumber().doubleValue(), 1e-10);
	}
}
