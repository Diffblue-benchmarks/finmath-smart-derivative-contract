/*
 * (c) Copyright Christian P. Fries, Germany. All rights reserved. Contact: email@christianfries.com.
 *
 * Created on 6 Oct 2018
 */

package net.finmath.smartcontract.valuation.oracle.simulated;

import net.finmath.smartcontract.valuation.oracle.StochasticValuationOracle;
import net.finmath.smartcontract.valuation.oracle.ValuationOracleSamplePath;
import net.finmath.stochastic.RandomVariable;
import net.finmath.time.TimeDiscretizationFromArray;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for BrownianMotionOracle.
 *
 * @author Christian Fries
 */
class BrownianMotionOracleTest {

	@Test
	void testDefaultConstructor() {
		// Given / When
		final BrownianMotionOracle oracle = new BrownianMotionOracle();

		// Then
		assertNotNull(oracle);
	}

	@Test
	void testConstructorWithInitialTime() {
		// Given
		final LocalDateTime initialTime = LocalDateTime.of(2018, 8, 12, 12, 0);

		// When
		final BrownianMotionOracle oracle = new BrownianMotionOracle(initialTime);

		// Then
		assertNotNull(oracle);
	}

	@Test
	void testConstructorWithAllParameters() {
		// Given
		final LocalDateTime initialTime = LocalDateTime.of(2018, 8, 12, 12, 0);
		final double initialValue = 0.0;
		final double timeHorizon = 20.0;
		final double riskFreeRate = 0.02;
		final double volatility = 0.10;
		final int numberOfPaths = 1000;

		// When
		final BrownianMotionOracle oracle = new BrownianMotionOracle(
				initialTime, initialValue, timeHorizon, riskFreeRate, volatility, numberOfPaths);

		// Then
		assertNotNull(oracle);
	}

	@Test
	void testConstructorWithTimeDiscretization() {
		// Given
		final LocalDateTime initialTime = LocalDateTime.of(2018, 8, 12, 12, 0);
		final double initialValue = 0.0;
		final double riskFreeRate = 0.02;
		final double volatility = 0.10;
		final int numberOfPaths = 1000;
		final double timeHorizon = 20.0;
		final var timeDiscretization = new TimeDiscretizationFromArray(0.0, timeHorizon, 1.0 / 365.0,
				TimeDiscretizationFromArray.ShortPeriodLocation.SHORT_PERIOD_AT_END);

		// When
		final BrownianMotionOracle oracle = new BrownianMotionOracle(
				timeDiscretization, initialTime, initialValue, riskFreeRate, volatility, numberOfPaths);

		// Then
		assertNotNull(oracle);
	}

	@Test
	void testGetValue() {
		// Given
		final LocalDateTime initialTime = LocalDateTime.of(2018, 8, 12, 12, 0);
		final LocalDateTime evaluationTime = LocalDateTime.of(2018, 8, 12, 12, 0);
		final LocalDateTime marketDataTime = LocalDateTime.of(2018, 8, 13, 12, 0);
		final BrownianMotionOracle oracle = new BrownianMotionOracle(initialTime);

		// When
		final RandomVariable value = oracle.getValue(evaluationTime, marketDataTime);

		// Then
		assertNotNull(value);
	}

	@Test
	void testGetValueWithSamplePath() {
		// Given
		final LocalDateTime initialTime = LocalDateTime.of(2018, 8, 12, 12, 0);
		final LocalDateTime finalTime = LocalDateTime.of(2018, 9, 12, 12, 0);
		final int path = 0;
		final StochasticValuationOracle stochasticOracle = new BrownianMotionOracle(initialTime);
		final var oracle = new ValuationOracleSamplePath(stochasticOracle, path);

		// When / Then
		for (LocalDateTime time = initialTime; time.isBefore(finalTime); time = time.plusDays(1)) {
			final double value = oracle.getValue(time, time).doubleValue();
			assertNotNull(value);
		}
	}

	@Test
	void testGetValueMultipleTimes() {
		// Given
		final LocalDateTime initialTime = LocalDateTime.of(2018, 8, 12, 12, 0);
		final BrownianMotionOracle oracle = new BrownianMotionOracle(initialTime, 100.0, 10.0, 0.05, 0.20, 500);

		// When
		final RandomVariable value1 = oracle.getValue(initialTime, initialTime);
		final RandomVariable value2 = oracle.getValue(initialTime, initialTime.plusDays(30));
		final RandomVariable value3 = oracle.getValue(initialTime, initialTime.plusDays(60));

		// Then
		assertNotNull(value1);
		assertNotNull(value2);
		assertNotNull(value3);
	}

	@Test
	void testGetValueInitializesSimulation() {
		// Given
		final LocalDateTime initialTime = LocalDateTime.of(2018, 8, 12, 12, 0);
		final LocalDateTime marketDataTime = LocalDateTime.of(2018, 8, 15, 12, 0);
		final BrownianMotionOracle oracle = new BrownianMotionOracle(initialTime);

		// When - First call should initialize simulation
		final RandomVariable firstValue = oracle.getValue(initialTime, marketDataTime);

		// Then
		assertNotNull(firstValue);

		// When - Second call should reuse initialized simulation
		final RandomVariable secondValue = oracle.getValue(initialTime, marketDataTime);

		// Then
		assertNotNull(secondValue);
		assertEquals(firstValue.size(), secondValue.size());
	}
}
