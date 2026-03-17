package net.finmath.smartcontract.settlement;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class SettlementInfoTest {

	@Test
	void testDefaultConstructor() {
		final SettlementInfo info = new SettlementInfo();

		assertNotNull(info);
		assertNull(info.getKey());
		assertNull(info.getValue());
	}

	@Test
	void testParameterizedConstructor() {
		final String key = "testKey";
		final BigDecimal value = new BigDecimal("123.45");

		final SettlementInfo info = new SettlementInfo(key, value);

		assertEquals(key, info.getKey());
		assertEquals(value, info.getValue());
	}

	@Test
	void testSetAndGetKey() {
		final SettlementInfo info = new SettlementInfo();
		final String key = "myKey";

		info.setKey(key);

		assertEquals(key, info.getKey());
	}

	@Test
	void testSetAndGetValue() {
		final SettlementInfo info = new SettlementInfo();
		final BigDecimal value = new BigDecimal("999.99");

		info.setValue(value);

		assertEquals(value, info.getValue());
	}

	@Test
	void testSetKeyOverwritesExistingValue() {
		final SettlementInfo info = new SettlementInfo("initialKey", BigDecimal.ONE);
		final String newKey = "updatedKey";

		info.setKey(newKey);

		assertEquals(newKey, info.getKey());
	}

	@Test
	void testSetValueOverwritesExistingValue() {
		final SettlementInfo info = new SettlementInfo("key", BigDecimal.ONE);
		final BigDecimal newValue = new BigDecimal("42.0");

		info.setValue(newValue);

		assertEquals(newValue, info.getValue());
	}
}
