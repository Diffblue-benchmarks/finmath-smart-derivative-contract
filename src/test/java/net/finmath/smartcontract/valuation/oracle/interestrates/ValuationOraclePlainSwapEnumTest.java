package net.finmath.smartcontract.valuation.oracle.interestrates;

import net.finmath.smartcontract.valuation.oracle.ValuationType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValuationOraclePlainSwapEnumTest {

	@Test
	void valuationTypeSwap_shouldHaveThreeValues() {
		ValuationOraclePlainSwap.ValuationTypeSwap[] values =
				ValuationOraclePlainSwap.ValuationTypeSwap.values();
		assertEquals(3, values.length);
	}

	@Test
	void valuationTypeSwap_shouldImplementValuationType() {
		ValuationType vt = ValuationOraclePlainSwap.ValuationTypeSwap.VALUE;
		assertNotNull(vt);
	}

	@Test
	void valuationTypeSwap_shouldContainExpectedValues() {
		assertNotNull(ValuationOraclePlainSwap.ValuationTypeSwap.valueOf("VALUE"));
		assertNotNull(ValuationOraclePlainSwap.ValuationTypeSwap.valueOf("VALUE_RECEIVER_LEG"));
		assertNotNull(ValuationOraclePlainSwap.ValuationTypeSwap.valueOf("VALUE_PAYER_LEG"));
	}
}
