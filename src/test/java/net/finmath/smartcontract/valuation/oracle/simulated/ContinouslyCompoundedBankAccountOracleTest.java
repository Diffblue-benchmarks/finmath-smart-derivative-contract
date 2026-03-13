package net.finmath.smartcontract.valuation.oracle.simulated;

import net.finmath.stochastic.RandomVariable;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ContinouslyCompoundedBankAccountOracleTest {

	private final LocalDateTime initialTime = LocalDateTime.of(2024, 1, 1, 0, 0);

	@Test
	void getValue_atInitialTime_shouldReturnInitialValue() {
		ContinouslyCompoundedBankAccountOracle oracle =
				new ContinouslyCompoundedBankAccountOracle(initialTime, 100.0, 10.0, 0.05);

		RandomVariable value = oracle.getValue(initialTime, initialTime);
		assertEquals(100.0, value.get(0), 1e-10);
	}

	@Test
	void getValue_afterOneYear_shouldGrow() {
		double initialValue = 100.0;
		double rate = 0.05;
		ContinouslyCompoundedBankAccountOracle oracle =
				new ContinouslyCompoundedBankAccountOracle(initialTime, initialValue, 10.0, rate);

		LocalDateTime oneYearLater = initialTime.plusYears(1);
		RandomVariable value = oracle.getValue(initialTime, oneYearLater);

		double expected = initialValue * Math.exp(rate * 1.0);
		assertEquals(expected, value.get(0), 0.5); // approximate due to time discretization
	}

	@Test
	void getValue_withZeroRate_shouldRemainConstant() {
		ContinouslyCompoundedBankAccountOracle oracle =
				new ContinouslyCompoundedBankAccountOracle(initialTime, 50.0, 5.0, 0.0);

		LocalDateTime later = initialTime.plusMonths(6);
		RandomVariable value = oracle.getValue(initialTime, later);
		assertEquals(50.0, value.get(0), 1e-6);
	}

	@Test
	void constructorWithInitialTime_shouldCreateOracle() {
		ContinouslyCompoundedBankAccountOracle oracle =
				new ContinouslyCompoundedBankAccountOracle(initialTime);

		RandomVariable value = oracle.getValue(initialTime, initialTime);
		assertNotNull(value);
		assertEquals(1.0, value.get(0), 1e-10);
	}
}
