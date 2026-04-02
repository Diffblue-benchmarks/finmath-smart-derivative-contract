/*
 * (c) Copyright Christian P. Fries, Germany. All rights reserved. Contact: email@christianfries.com.
 *
 * Created on 6 Oct 2018
 */

package net.finmath.smartcontract.valuation.oracle;

import net.finmath.smartcontract.valuation.oracle.simulated.GeometricBrownianMotionOracle;
import net.finmath.stochastic.RandomVariable;
import net.finmath.time.TimeDiscretizationFromArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

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

		Assertions.assertNotNull(oracle);
	}

	@Test
	void testConstructorWithInitialTime() {
		final LocalDateTime initialTime = LocalDateTime.of(2020, 1, 1, 0, 0);

		final GeometricBrownianMotionOracle oracle = new GeometricBrownianMotionOracle(initialTime);

		Assertions.assertNotNull(oracle);
	}

	@Test
	void testConstructorWithAllParameters() {
		final LocalDateTime initialTime = LocalDateTime.of(2020, 1, 1, 0, 0);

		final GeometricBrownianMotionOracle oracle = new GeometricBrownianMotionOracle(initialTime, 1.0, 5.0, 0.02, 0.10, 100);

		Assertions.assertNotNull(oracle);
	}

	@Test
	void testConstructorWithTimeDiscretization() {
		final LocalDateTime initialTime = LocalDateTime.of(2020, 1, 1, 0, 0);
		final TimeDiscretizationFromArray timeDiscretization = new TimeDiscretizationFromArray(0.0, 5.0, 1.0 / 365.0,
				TimeDiscretizationFromArray.ShortPeriodLocation.SHORT_PERIOD_AT_END);

		final GeometricBrownianMotionOracle oracle = new GeometricBrownianMotionOracle(timeDiscretization, initialTime, 1.0, 0.02, 0.10, 100);

		Assertions.assertNotNull(oracle);
	}

	@Test
	void testGetValue() {
		final LocalDateTime initialTime = LocalDateTime.of(2020, 1, 1, 0, 0);
		final LocalDateTime marketDataTime = LocalDateTime.of(2021, 1, 1, 0, 0);

		final GeometricBrownianMotionOracle oracle = new GeometricBrownianMotionOracle(initialTime, 1.0, 5.0, 0.02, 0.10, 100);

		final RandomVariable value = oracle.getValue(initialTime, marketDataTime);

		Assertions.assertNotNull(value);
		Assertions.assertTrue(value.getMin() > 0.0, "Asset value should be positive");
	}

	@Test
	void testGetValueLazyInitCalledTwice() {
		final LocalDateTime initialTime = LocalDateTime.of(2020, 1, 1, 0, 0);
		final LocalDateTime marketDataTime = LocalDateTime.of(2021, 6, 1, 0, 0);

		final GeometricBrownianMotionOracle oracle = new GeometricBrownianMotionOracle(initialTime, 1.0, 3.0, 0.02, 0.10, 50);

		final RandomVariable value1 = oracle.getValue(initialTime, marketDataTime);
		final RandomVariable value2 = oracle.getValue(initialTime, marketDataTime);

		Assertions.assertNotNull(value1);
		Assertions.assertNotNull(value2);
		Assertions.assertEquals(value1.getAverage(), value2.getAverage(), 1e-10, "Repeated getValue calls should return same simulation result");
	}

}
