package net.finmath.smartcontract.settlement;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BigDecimalAdapterTest {

	private final BigDecimalAdapter adapter = new BigDecimalAdapter();

	@Test
	void marshal_shouldConvertToString() throws Exception {
		String result = adapter.marshal(BigDecimal.valueOf(123.45));
		assertEquals("123.45", result);
	}

	@Test
	void marshal_shouldReturnNull_forNullInput() throws Exception {
		assertNull(adapter.marshal(null));
	}

	@Test
	void unmarshal_shouldConvertToBigDecimal() throws Exception {
		BigDecimal result = adapter.unmarshal("99.99");
		assertEquals(new BigDecimal("99.99"), result);
	}

	@Test
	void roundTrip_shouldPreserveValue() throws Exception {
		BigDecimal original = new BigDecimal("1234567890.123456789");
		String marshalled = adapter.marshal(original);
		BigDecimal unmarshalled = adapter.unmarshal(marshalled);
		assertEquals(original, unmarshalled);
	}
}
