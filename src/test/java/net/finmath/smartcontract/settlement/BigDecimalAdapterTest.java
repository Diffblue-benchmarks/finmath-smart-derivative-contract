package net.finmath.smartcontract.settlement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for BigDecimalAdapter class.
 */
class BigDecimalAdapterTest {

    private BigDecimalAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new BigDecimalAdapter();
    }

    @Test
    void testMarshal_ValidBigDecimal() throws Exception {
        BigDecimal value = new BigDecimal("123.45");

        String result = adapter.marshal(value);

        assertNotNull(result);
        assertEquals("123.45", result);
    }

    @Test
    void testMarshal_NullValue() throws Exception {
        String result = adapter.marshal(null);

        assertNull(result);
    }

    @Test
    void testMarshal_Zero() throws Exception {
        BigDecimal value = BigDecimal.ZERO;

        String result = adapter.marshal(value);

        assertEquals("0", result);
    }

    @Test
    void testMarshal_NegativeValue() throws Exception {
        BigDecimal value = new BigDecimal("-999.99");

        String result = adapter.marshal(value);

        assertEquals("-999.99", result);
    }

    @Test
    void testMarshal_LargeValue() throws Exception {
        BigDecimal value = new BigDecimal("999999999.999999");

        String result = adapter.marshal(value);

        assertEquals("999999999.999999", result);
    }

    @Test
    void testUnmarshal_ValidString() throws Exception {
        String input = "123.45";

        BigDecimal result = adapter.unmarshal(input);

        assertNotNull(result);
        assertEquals(new BigDecimal("123.45"), result);
    }

    @Test
    void testUnmarshal_IntegerString() throws Exception {
        String input = "100";

        BigDecimal result = adapter.unmarshal(input);

        assertEquals(new BigDecimal("100"), result);
    }

    @Test
    void testUnmarshal_NegativeString() throws Exception {
        String input = "-50.25";

        BigDecimal result = adapter.unmarshal(input);

        assertEquals(new BigDecimal("-50.25"), result);
    }

    @Test
    void testUnmarshal_ZeroString() throws Exception {
        String input = "0";

        BigDecimal result = adapter.unmarshal(input);

        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    void testUnmarshal_ScientificNotation() throws Exception {
        String input = "1.23E+3";

        BigDecimal result = adapter.unmarshal(input);

        assertEquals(new BigDecimal("1.23E+3"), result);
    }

    @Test
    void testRoundTrip_PositiveValue() throws Exception {
        BigDecimal original = new BigDecimal("456.78");

        String marshalled = adapter.marshal(original);
        BigDecimal unmarshalled = adapter.unmarshal(marshalled);

        assertEquals(original, unmarshalled);
    }

    @Test
    void testRoundTrip_NegativeValue() throws Exception {
        BigDecimal original = new BigDecimal("-123.456");

        String marshalled = adapter.marshal(original);
        BigDecimal unmarshalled = adapter.unmarshal(marshalled);

        assertEquals(original, unmarshalled);
    }
}
