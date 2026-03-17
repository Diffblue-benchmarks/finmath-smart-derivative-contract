/*
 * (c) Copyright Christian P. Fries, Germany. All rights reserved. Contact: email@christianfries.com.
 *
 * Created on 6 Oct 2018
 */

package net.finmath.smartcontract.valuation.oracle;

import net.finmath.smartcontract.valuation.oracle.simulated.GeometricBrownianMotionOracle;
import net.finmath.stochastic.RandomVariable;
import net.finmath.time.TimeDiscretizationFromArray;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Christian Fries
 */
class GeometricBrownianMotionOracleTest {

	@Test
	void test() {
		final LocalDateTime initialTime = LocalDateTime.of(2018, 8, 12, 12, 00);
		final LocalDateTime finalTime = LocalDateTime.of(2028, 8, 12, 12, 00);
		final int path = 0;

		final StochasticValuationOracle stoachasticOracle = new GeometricBrownianMotionOracle(initialTime);

		final ValuationOracle oracle = new ValuationOracleSamplePath(stoachasticOracle, path);

		for (LocalDateTime time = initialTime; time.isBefore(finalTime); time = time.plusDays(1)) {

			final double value = oracle.getValue(time, time).doubleValue();
			System.out.println(time.toLocalDate() + "\t" + value);

		}
	}

	@Test
	void testDefaultConstructor() {
		final GeometricBrownianMotionOracle oracle = new GeometricBrownianMotionOracle();
		assertNotNull(oracle);
	}

	@Test
	void testConstructorWithInitialTime() {
		final LocalDateTime initialTime = LocalDateTime.of(2020, 1, 1, 0, 0);
		final GeometricBrownianMotionOracle oracle = new GeometricBrownianMotionOracle(initialTime);
		assertNotNull(oracle);
	}

	@Test
	void testConstructorWithParameters() {
		final LocalDateTime initialTime = LocalDateTime.of(2020, 1, 1, 0, 0);
		final double initialValue = 100.0;
		final double timeHorizon = 10.0;
		final double riskFreeRate = 0.05;
		final double volatility = 0.20;
		final int numberOfPaths = 500;

		final GeometricBrownianMotionOracle oracle = new GeometricBrownianMotionOracle(
				initialTime, initialValue, timeHorizon, riskFreeRate, volatility, numberOfPaths);
		assertNotNull(oracle);
	}

	@Test
	void testConstructorWithTimeDiscretization() {
		final LocalDateTime initialTime = LocalDateTime.of(2020, 1, 1, 0, 0);
		final double initialValue = 100.0;
		final double riskFreeRate = 0.05;
		final double volatility = 0.20;
		final int numberOfPaths = 500;
		final TimeDiscretizationFromArray timeDiscretization = new TimeDiscretizationFromArray(0.0, 5.0, 0.1);

		final GeometricBrownianMotionOracle oracle = new GeometricBrownianMotionOracle(
				timeDiscretization, initialTime, initialValue, riskFreeRate, volatility, numberOfPaths);
		assertNotNull(oracle);
	}

	@Test
	void testGetValue() {
		final LocalDateTime initialTime = LocalDateTime.of(2020, 1, 1, 0, 0);
		final LocalDateTime evaluationTime = LocalDateTime.of(2020, 1, 1, 0, 0);
		final LocalDateTime marketDataTime = LocalDateTime.of(2020, 6, 1, 0, 0);

		final GeometricBrownianMotionOracle oracle = new GeometricBrownianMotionOracle(initialTime);
		final RandomVariable value = oracle.getValue(evaluationTime, marketDataTime);

		assertNotNull(value);
		assertTrue(value.getAverage() > 0);
	}

	@Test
	void testGetValueMultipleTimes() {
		final LocalDateTime initialTime = LocalDateTime.of(2020, 1, 1, 0, 0);
		final LocalDateTime evaluationTime = LocalDateTime.of(2020, 1, 1, 0, 0);

		final GeometricBrownianMotionOracle oracle = new GeometricBrownianMotionOracle(initialTime);

		final RandomVariable value1 = oracle.getValue(evaluationTime, LocalDateTime.of(2020, 6, 1, 0, 0));
		final RandomVariable value2 = oracle.getValue(evaluationTime, LocalDateTime.of(2021, 1, 1, 0, 0));

		assertNotNull(value1);
		assertNotNull(value2);
		assertTrue(value1.getAverage() > 0);
		assertTrue(value2.getAverage() > 0);
	}

	@Test
	void testGetValueWithDifferentParameters() {
		final LocalDateTime initialTime = LocalDateTime.of(2020, 1, 1, 0, 0);
		final double initialValue = 50.0;
		final double timeHorizon = 20.0;
		final double riskFreeRate = 0.03;
		final double volatility = 0.15;
		final int numberOfPaths = 1000;

		final GeometricBrownianMotionOracle oracle = new GeometricBrownianMotionOracle(
				initialTime, initialValue, timeHorizon, riskFreeRate, volatility, numberOfPaths);

		final LocalDateTime evaluationTime = LocalDateTime.of(2020, 1, 1, 0, 0);
		final LocalDateTime marketDataTime = LocalDateTime.of(2021, 1, 1, 0, 0);
		final RandomVariable value = oracle.getValue(evaluationTime, marketDataTime);

		assertNotNull(value);
		assertTrue(value.getAverage() > 0);
	}

}
