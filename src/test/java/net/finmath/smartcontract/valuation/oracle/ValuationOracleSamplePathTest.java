/*
 * (c) Copyright Christian P. Fries, Germany. All rights reserved. Contact: email@christianfries.com.
 *
 * Created on 17 Mar 2026
 */

package net.finmath.smartcontract.valuation.oracle;

import net.finmath.smartcontract.valuation.oracle.simulated.GeometricBrownianMotionOracle;
import org.junit.jupiter.api.Test;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ValuationOracleSamplePath.
 */
class ValuationOracleSamplePathTest {

	@Test
	void testConstructorAndGetValue() {
		// Given
		final LocalDateTime evaluationTime = LocalDateTime.of(2018, 8, 12, 12, 0);
		final int path = 0;
		final StochasticValuationOracle stochasticOracle = new GeometricBrownianMotionOracle(evaluationTime);

		// When
		final ValuationOracleSamplePath oracle = new ValuationOracleSamplePath(stochasticOracle, path);
		final BigDecimal value = oracle.getValue(evaluationTime, evaluationTime);

		// Then
		assertNotNull(value);
		assertTrue(value.doubleValue() > 0.0);
	}

	@Test
	void testGetValueWithDifferentPaths() {
		// Given
		final LocalDateTime evaluationTime = LocalDateTime.of(2018, 8, 12, 12, 0);
		final LocalDateTime laterTime = evaluationTime.plusYears(1);
		final StochasticValuationOracle stochasticOracle = new GeometricBrownianMotionOracle(evaluationTime);

		// When
		final ValuationOracleSamplePath oracle1 = new ValuationOracleSamplePath(stochasticOracle, 0);
		final ValuationOracleSamplePath oracle2 = new ValuationOracleSamplePath(stochasticOracle, 1);

		final BigDecimal value1 = oracle1.getValue(laterTime, laterTime);
		final BigDecimal value2 = oracle2.getValue(laterTime, laterTime);

		// Then
		assertNotNull(value1);
		assertNotNull(value2);
		assertNotEquals(value1, value2);
	}

	@Test
	void testGetValues() {
		// Given
		final LocalDateTime evaluationTime = LocalDateTime.of(2018, 8, 12, 12, 0);
		final int path = 0;
		final StochasticValuationOracle stochasticOracle = new GeometricBrownianMotionOracle(evaluationTime);
		final ValuationOracleSamplePath oracle = new ValuationOracleSamplePath(stochasticOracle, path);

		// When
		final Map<String, BigDecimal> values = oracle.getValues(evaluationTime, evaluationTime);

		// Then
		assertNotNull(values);
		assertEquals(1, values.size());
		assertTrue(values.containsKey("value"));
		assertNotNull(values.get("value"));
		assertEquals(oracle.getValue(evaluationTime, evaluationTime), values.get("value"));
	}

	@Test
	void testGetAmount() {
		// Given
		final LocalDateTime evaluationTime = LocalDateTime.of(2018, 8, 12, 12, 0);
		final int path = 5;
		final StochasticValuationOracle stochasticOracle = new GeometricBrownianMotionOracle(evaluationTime);
		final ValuationOracleSamplePath oracle = new ValuationOracleSamplePath(stochasticOracle, path);

		// When
		final MonetaryAmount amount = oracle.getAmount(evaluationTime, evaluationTime);

		// Then
		assertNotNull(amount);
		assertEquals("EUR", amount.getCurrency().getCurrencyCode());
		assertEquals(0, oracle.getValue(evaluationTime, evaluationTime).compareTo(amount.getNumber().numberValue(BigDecimal.class)));
	}

	@Test
	void testGetAmountWithDifferentTimes() {
		// Given
		final LocalDateTime initialTime = LocalDateTime.of(2018, 8, 12, 12, 0);
		final LocalDateTime evaluationTime = initialTime.plusDays(100);
		final LocalDateTime marketDataTime = initialTime.plusDays(50);
		final int path = 2;
		final StochasticValuationOracle stochasticOracle = new GeometricBrownianMotionOracle(initialTime);
		final ValuationOracleSamplePath oracle = new ValuationOracleSamplePath(stochasticOracle, path);

		// When
		final MonetaryAmount amount = oracle.getAmount(evaluationTime, marketDataTime);

		// Then
		assertNotNull(amount);
		assertEquals("EUR", amount.getCurrency().getCurrencyCode());
		assertTrue(amount.getNumber().doubleValue() > 0.0);
	}
}
