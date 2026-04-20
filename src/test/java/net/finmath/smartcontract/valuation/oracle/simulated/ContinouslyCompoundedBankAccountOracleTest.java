package net.finmath.smartcontract.valuation.oracle.simulated;

import net.finmath.stochastic.RandomVariable;
import net.finmath.time.TimeDiscretizationFromArray;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ContinouslyCompoundedBankAccountOracleTest {

	@Test
	void testDefaultConstructor() {
		ContinouslyCompoundedBankAccountOracle oracle = new ContinouslyCompoundedBankAccountOracle();
		assertNotNull(oracle);

		LocalDateTime now = LocalDateTime.now();
		RandomVariable value = oracle.getValue(now, now);
		assertNotNull(value);
		assertEquals(1.0, value.doubleValue(), 0.01, "Value at initial time should be close to initial value");
	}

	@Test
	void testConstructorWithInitialTime() {
		LocalDateTime initialTime = LocalDateTime.of(2024, 1, 1, 0, 0);
		ContinouslyCompoundedBankAccountOracle oracle = new ContinouslyCompoundedBankAccountOracle(initialTime);

		RandomVariable value = oracle.getValue(initialTime, initialTime);
		assertEquals(1.0, value.doubleValue(), 1e-10, "Value at initial time should equal initial value");
	}

	@Test
	void testConstructorWithParameters() {
		LocalDateTime initialTime = LocalDateTime.of(2024, 1, 1, 0, 0);
		double initialValue = 100.0;
		double timeHorizon = 10.0;
		double riskFreeRate = 0.05;

		ContinouslyCompoundedBankAccountOracle oracle = new ContinouslyCompoundedBankAccountOracle(initialTime, initialValue, timeHorizon, riskFreeRate);

		RandomVariable value = oracle.getValue(initialTime, initialTime);
		assertEquals(100.0, value.doubleValue(), 1e-10, "Value at initial time should equal initial value");
	}

	@Test
	void testFullConstructor() {
		LocalDateTime initialTime = LocalDateTime.of(2024, 1, 1, 0, 0);
		double initialValue = 50.0;
		double riskFreeRate = 0.03;
		var timeDiscretization = new TimeDiscretizationFromArray(0.0, 10.0, 1.0 / 365.0,
				TimeDiscretizationFromArray.ShortPeriodLocation.SHORT_PERIOD_AT_END);

		ContinouslyCompoundedBankAccountOracle oracle = new ContinouslyCompoundedBankAccountOracle(
				timeDiscretization, initialTime, initialValue, riskFreeRate);

		RandomVariable value = oracle.getValue(initialTime, initialTime);
		assertEquals(50.0, value.doubleValue(), 1e-10, "Value at initial time should equal initial value");
	}

	@Test
	void testGetValueWithFutureDate() {
		LocalDateTime initialTime = LocalDateTime.of(2024, 1, 1, 0, 0);
		LocalDateTime futureTime = LocalDateTime.of(2025, 1, 1, 0, 0);
		double initialValue = 100.0;
		double riskFreeRate = 0.05;
		double timeHorizon = 5.0;

		ContinouslyCompoundedBankAccountOracle oracle = new ContinouslyCompoundedBankAccountOracle(
				initialTime, initialValue, timeHorizon, riskFreeRate);

		RandomVariable value = oracle.getValue(initialTime, futureTime);
		// Expected: 100 * exp(0.05 * ~1.0) ≈ 105.127
		double expectedApprox = initialValue * Math.exp(riskFreeRate * 1.0);
		assertEquals(expectedApprox, value.doubleValue(), 0.5, "Value should grow exponentially");
	}
}
