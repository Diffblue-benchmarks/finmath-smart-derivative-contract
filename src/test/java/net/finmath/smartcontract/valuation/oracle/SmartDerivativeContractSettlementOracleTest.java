package net.finmath.smartcontract.valuation.oracle;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SmartDerivativeContractSettlementOracleTest {

	private final LocalDateTime periodStart = LocalDateTime.of(2024, 6, 14, 17, 0);
	private final LocalDateTime periodEnd = LocalDateTime.of(2024, 6, 15, 17, 0);

	@Test
	void getMargin_shouldComputeDifferenceBetweenCurrentAndPreviousValues() {
		ValuationOracle derivativeOracle = mock(ValuationOracle.class);

		when(derivativeOracle.getValues(periodEnd, periodEnd))
				.thenReturn(Map.of("value", BigDecimal.valueOf(105)));
		when(derivativeOracle.getValues(periodEnd, periodStart))
				.thenReturn(Map.of("value", BigDecimal.valueOf(100)));

		SmartDerivativeContractSettlementOracle oracle =
				new SmartDerivativeContractSettlementOracle(derivativeOracle);

		Map<String, BigDecimal> margin = oracle.getMargin(periodStart, periodEnd);

		assertEquals(BigDecimal.valueOf(5), margin.get("value"));
	}

	@Test
	void getMargin_shouldHandleNegativeMargin() {
		ValuationOracle derivativeOracle = mock(ValuationOracle.class);

		when(derivativeOracle.getValues(periodEnd, periodEnd))
				.thenReturn(Map.of("value", BigDecimal.valueOf(95)));
		when(derivativeOracle.getValues(periodEnd, periodStart))
				.thenReturn(Map.of("value", BigDecimal.valueOf(100)));

		SmartDerivativeContractSettlementOracle oracle =
				new SmartDerivativeContractSettlementOracle(derivativeOracle);

		Map<String, BigDecimal> margin = oracle.getMargin(periodStart, periodEnd);

		assertEquals(BigDecimal.valueOf(-5), margin.get("value"));
	}

	@Test
	void getMargin_shouldHandleMultipleKeys() {
		ValuationOracle derivativeOracle = mock(ValuationOracle.class);

		when(derivativeOracle.getValues(periodEnd, periodEnd))
				.thenReturn(Map.of("value", BigDecimal.valueOf(100), "delta", BigDecimal.valueOf(50)));
		when(derivativeOracle.getValues(periodEnd, periodStart))
				.thenReturn(Map.of("value", BigDecimal.valueOf(90), "delta", BigDecimal.valueOf(45)));

		SmartDerivativeContractSettlementOracle oracle =
				new SmartDerivativeContractSettlementOracle(derivativeOracle);

		Map<String, BigDecimal> margin = oracle.getMargin(periodStart, periodEnd);

		assertEquals(BigDecimal.valueOf(10), margin.get("value"));
		assertEquals(BigDecimal.valueOf(5), margin.get("delta"));
	}
}
