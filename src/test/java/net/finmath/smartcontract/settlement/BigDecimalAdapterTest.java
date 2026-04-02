package net.finmath.smartcontract.settlement;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNull;

class BigDecimalAdapterTest {

	@Test
	void marshalNullReturnsNull() throws Exception {
		BigDecimalAdapter adapter = new BigDecimalAdapter();

		String result = adapter.marshal(null);

		assertNull(result);
	}
}
