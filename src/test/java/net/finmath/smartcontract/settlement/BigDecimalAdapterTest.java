package net.finmath.smartcontract.settlement;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class BigDecimalAdapterTest {

	@Test
	void testMarshalNonNullValue() throws Exception {
		BigDecimalAdapter adapter = new BigDecimalAdapter();
		BigDecimal value = new BigDecimal("123.456");

		String result = adapter.marshal(value);

		assertEquals("123.456", result);
	}

	@Test
	void testMarshalNullValue() throws Exception {
		BigDecimalAdapter adapter = new BigDecimalAdapter();

		String result = adapter.marshal(null);

		assertNull(result);
	}

	@Test
	void testUnmarshal() throws Exception {
		BigDecimalAdapter adapter = new BigDecimalAdapter();

		BigDecimal result = adapter.unmarshal("789.012");

		assertEquals(new BigDecimal("789.012"), result);
	}
}
