package net.finmath.smartcontract.valuation.oracle;

import net.finmath.stochastic.RandomVariable;
import org.junit.jupiter.api.Test;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ValuationOracleSamplePathTest {

	private final LocalDateTime evalTime = LocalDateTime.of(2024, 6, 15, 17, 0);
	private final LocalDateTime marketDataTime = LocalDateTime.of(2024, 6, 14, 17, 0);

	@Test
	void getValue_shouldExtractPathFromStochasticOracle() {
		StochasticValuationOracle stochasticOracle = mock(StochasticValuationOracle.class);
		RandomVariable rv = mock(RandomVariable.class);
		when(stochasticOracle.getValue(evalTime, marketDataTime)).thenReturn(rv);
		when(rv.get(0)).thenReturn(42.5);

		ValuationOracleSamplePath oracle = new ValuationOracleSamplePath(stochasticOracle, 0);
		BigDecimal value = oracle.getValue(evalTime, marketDataTime);

		assertEquals(BigDecimal.valueOf(42.5), value);
	}

	@Test
	void getValues_shouldReturnMapWithValue() {
		StochasticValuationOracle stochasticOracle = mock(StochasticValuationOracle.class);
		RandomVariable rv = mock(RandomVariable.class);
		when(stochasticOracle.getValue(evalTime, marketDataTime)).thenReturn(rv);
		when(rv.get(2)).thenReturn(100.0);

		ValuationOracleSamplePath oracle = new ValuationOracleSamplePath(stochasticOracle, 2);
		Map<String, BigDecimal> values = oracle.getValues(evalTime, marketDataTime);

		assertTrue(values.containsKey("value"));
		assertEquals(BigDecimal.valueOf(100.0), values.get("value"));
	}

	@Test
	void getAmount_shouldReturnMonetaryAmountInEUR() {
		StochasticValuationOracle stochasticOracle = mock(StochasticValuationOracle.class);
		RandomVariable rv = mock(RandomVariable.class);
		when(stochasticOracle.getValue(evalTime, marketDataTime)).thenReturn(rv);
		when(rv.get(0)).thenReturn(250.0);

		ValuationOracleSamplePath oracle = new ValuationOracleSamplePath(stochasticOracle, 0);
		MonetaryAmount amount = oracle.getAmount(evalTime, marketDataTime);

		assertNotNull(amount);
		assertEquals("EUR", amount.getCurrency().getCurrencyCode());
	}
}
