package net.finmath.smartcontract.valuation.oracle.simulated;

import net.finmath.stochastic.RandomVariable;
import net.finmath.time.TimeDiscretizationFromArray;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GeometricBrownianMotionOracleTest {

	@Test
	void testDefaultConstructor() {
		GeometricBrownianMotionOracle oracle = new GeometricBrownianMotionOracle();
		assertNotNull(oracle);

		LocalDateTime now = LocalDateTime.now();
		RandomVariable value = oracle.getValue(now, now);
		assertNotNull(value);
	}

	@Test
	void testConstructorWithInitialTime() {
		LocalDateTime initialTime = LocalDateTime.of(2020, 1, 1, 0, 0);
		GeometricBrownianMotionOracle oracle = new GeometricBrownianMotionOracle(initialTime);
		assertNotNull(oracle);

		RandomVariable value = oracle.getValue(initialTime, initialTime);
		assertNotNull(value);
		assertTrue(Double.isFinite(value.getAverage()));
	}

	@Test
	void testConstructorWithAllParameters() {
		LocalDateTime initialTime = LocalDateTime.of(2020, 1, 1, 0, 0);
		GeometricBrownianMotionOracle oracle = new GeometricBrownianMotionOracle(
				initialTime, 100.0, 10.0, 0.05, 0.20, 500);
		assertNotNull(oracle);

		LocalDateTime evalTime = initialTime.plusDays(30);
		RandomVariable value = oracle.getValue(evalTime, evalTime);
		assertNotNull(value);
		assertTrue(Double.isFinite(value.getAverage()));
	}

	@Test
	void testConstructorWithTimeDiscretization() {
		LocalDateTime initialTime = LocalDateTime.of(2020, 1, 1, 0, 0);
		TimeDiscretizationFromArray timeDiscretization = new TimeDiscretizationFromArray(
				0.0, 5.0, 1.0 / 365.0,
				TimeDiscretizationFromArray.ShortPeriodLocation.SHORT_PERIOD_AT_END);

		GeometricBrownianMotionOracle oracle = new GeometricBrownianMotionOracle(
				timeDiscretization, initialTime, 1.0, 0.02, 0.10, 100);
		assertNotNull(oracle);

		LocalDateTime evalTime = initialTime.plusDays(10);
		RandomVariable value = oracle.getValue(evalTime, evalTime);
		assertNotNull(value);
		assertTrue(Double.isFinite(value.getAverage()));
	}

	@Test
	void testGetValueAtInitialTime() {
		LocalDateTime initialTime = LocalDateTime.of(2020, 6, 15, 12, 0);
		double initialValue = 50.0;
		GeometricBrownianMotionOracle oracle = new GeometricBrownianMotionOracle(
				initialTime, initialValue, 5.0, 0.03, 0.15, 200);

		RandomVariable value = oracle.getValue(initialTime, initialTime);
		assertNotNull(value);
		// At time 0, the value should equal the initial value
		assertTrue(Math.abs(value.getAverage() - initialValue) < 1e-10,
				"Value at initial time should equal initial value");
	}

	@Test
	void testGetValueAtFutureTime() {
		LocalDateTime initialTime = LocalDateTime.of(2020, 1, 1, 0, 0);
		GeometricBrownianMotionOracle oracle = new GeometricBrownianMotionOracle(
				initialTime, 1.0, 10.0, 0.02, 0.10, 1000);

		LocalDateTime futureTime = initialTime.plusYears(1);
		RandomVariable value = oracle.getValue(futureTime, futureTime);
		assertNotNull(value);
		assertTrue(value.getAverage() > 0, "GBM value should be positive");
	}
}
