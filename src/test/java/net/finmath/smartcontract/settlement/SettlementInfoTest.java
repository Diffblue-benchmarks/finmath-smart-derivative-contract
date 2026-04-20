package net.finmath.smartcontract.settlement;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SettlementInfoTest {

	@Test
	void testDefaultConstructor() {
		SettlementInfo info = new SettlementInfo();
		assertNull(info.getKey());
		assertNull(info.getValue());
	}

	@Test
	void testParameterizedConstructor() {
		SettlementInfo info = new SettlementInfo("marginValue", new BigDecimal("123.45"));
		assertEquals("marginValue", info.getKey());
		assertEquals(new BigDecimal("123.45"), info.getValue());
	}

	@Test
	void testSetKey() {
		SettlementInfo info = new SettlementInfo();
		info.setKey("newKey");
		assertEquals("newKey", info.getKey());
	}

	@Test
	void testSetValue() {
		SettlementInfo info = new SettlementInfo();
		info.setValue(new BigDecimal("99.99"));
		assertEquals(new BigDecimal("99.99"), info.getValue());
	}
}
