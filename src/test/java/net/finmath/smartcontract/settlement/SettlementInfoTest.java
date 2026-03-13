package net.finmath.smartcontract.settlement;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class SettlementInfoTest {

	@Test
	void defaultConstructor_shouldCreateEmptyObject() {
		SettlementInfo info = new SettlementInfo();
		assertNull(info.getKey());
		assertNull(info.getValue());
	}

	@Test
	void parameterizedConstructor_shouldSetFields() {
		SettlementInfo info = new SettlementInfo("npv", BigDecimal.valueOf(1234.56));
		assertEquals("npv", info.getKey());
		assertEquals(BigDecimal.valueOf(1234.56), info.getValue());
	}

	@Test
	void setters_shouldUpdateFields() {
		SettlementInfo info = new SettlementInfo();
		info.setKey("margin");
		info.setValue(BigDecimal.TEN);
		assertEquals("margin", info.getKey());
		assertEquals(BigDecimal.TEN, info.getValue());
	}
}
