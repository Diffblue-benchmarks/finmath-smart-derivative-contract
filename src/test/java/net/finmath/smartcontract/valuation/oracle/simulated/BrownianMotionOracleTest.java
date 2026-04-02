/*
 * (c) Copyright Christian P. Fries, Germany. All rights reserved. Contact: email@christianfries.com.
 */

package net.finmath.smartcontract.valuation.oracle.simulated;

import net.finmath.stochastic.RandomVariable;
import net.finmath.time.TimeDiscretizationFromArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 * Tests for {@link BrownianMotionOracle}.
 */
class BrownianMotionOracleTest {

	@Test
	void testDefaultConstructorAndGetValue() {
		final LocalDateTime now = LocalDateTime.now();

		final BrownianMotionOracle oracle = new BrownianMotionOracle();

		final RandomVariable value = oracle.getValue(now, now.plusDays(30));

		Assertions.assertNotNull(value);
	}

	@Test
	void testConstructorWithInitialTime() {
		final LocalDateTime initialTime = LocalDateTime.of(2020, 1, 1, 0, 0);
		final LocalDateTime marketDataTime = LocalDateTime.of(2020, 6, 1, 0, 0);

		final BrownianMotionOracle oracle = new BrownianMotionOracle(initialTime);

		final RandomVariable value = oracle.getValue(initialTime, marketDataTime);

		Assertions.assertNotNull(value);
	}

	@Test
	void testConstructorWithAllParams() {
		final LocalDateTime initialTime = LocalDateTime.of(2020, 1, 1, 0, 0);
		final LocalDateTime marketDataTime = LocalDateTime.of(2020, 6, 1, 0, 0);

		final BrownianMotionOracle oracle = new BrownianMotionOracle(initialTime, 100.0, 5.0, 0.02, 0.20, 500);

		final RandomVariable value = oracle.getValue(initialTime, marketDataTime);

		Assertions.assertNotNull(value);
	}

	@Test
	void testConstructorWithTimeDiscretization() {
		final LocalDateTime initialTime = LocalDateTime.of(2020, 1, 1, 0, 0);
		final LocalDateTime marketDataTime = LocalDateTime.of(2020, 6, 1, 0, 0);

		final TimeDiscretizationFromArray td = new TimeDiscretizationFromArray(0.0, 5.0, 1.0 / 365.0,
				TimeDiscretizationFromArray.ShortPeriodLocation.SHORT_PERIOD_AT_END);

		final BrownianMotionOracle oracle = new BrownianMotionOracle(td, initialTime, 50.0, 0.01, 0.15, 200);

		final RandomVariable value = oracle.getValue(initialTime, marketDataTime);

		Assertions.assertNotNull(value);
	}

	@Test
	void testGetValueReturnsSameSimulationOnSecondCall() {
		final LocalDateTime initialTime = LocalDateTime.of(2021, 1, 1, 0, 0);
		final LocalDateTime marketDataTime = LocalDateTime.of(2021, 3, 1, 0, 0);

		final BrownianMotionOracle oracle = new BrownianMotionOracle(initialTime, 0.0, 5.0, 0.02, 0.10, 100);

		final RandomVariable value1 = oracle.getValue(initialTime, marketDataTime);
		final RandomVariable value2 = oracle.getValue(initialTime, marketDataTime);

		Assertions.assertNotNull(value1);
		Assertions.assertNotNull(value2);
		Assertions.assertEquals(value1.getAverage(), value2.getAverage(), 1e-12);
	}
}
