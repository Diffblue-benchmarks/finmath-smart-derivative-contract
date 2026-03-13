package net.finmath.smartcontract.settlement;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for SettlementInfo class.
 */
class SettlementInfoTest {

    @Test
    void testDefaultConstructor() {
        SettlementInfo info = new SettlementInfo();
        assertNotNull(info);
        assertNull(info.getKey());
        assertNull(info.getValue());
    }

    @Test
    void testConstructorWithKeyAndValue() {
        String key = "risk_factor";
        BigDecimal value = new BigDecimal("123.45");

        SettlementInfo info = new SettlementInfo(key, value);

        assertNotNull(info);
        assertEquals(key, info.getKey());
        assertEquals(value, info.getValue());
    }

    @Test
    void testSetAndGetKey() {
        SettlementInfo info = new SettlementInfo();
        String key = "npv_scenario";

        info.setKey(key);

        assertEquals(key, info.getKey());
    }

    @Test
    void testSetAndGetValue() {
        SettlementInfo info = new SettlementInfo();
        BigDecimal value = new BigDecimal("9876.54");

        info.setValue(value);

        assertEquals(value, info.getValue());
    }

    @Test
    void testWithNullKey() {
        SettlementInfo info = new SettlementInfo(null, new BigDecimal("100"));
        assertNull(info.getKey());
        assertNotNull(info.getValue());
    }

    @Test
    void testWithNullValue() {
        SettlementInfo info = new SettlementInfo("test_key", null);
        assertNotNull(info.getKey());
        assertNull(info.getValue());
    }

    @Test
    void testWithZeroValue() {
        BigDecimal zero = BigDecimal.ZERO;
        SettlementInfo info = new SettlementInfo("zero_value", zero);

        assertEquals("zero_value", info.getKey());
        assertEquals(zero, info.getValue());
    }

    @Test
    void testWithNegativeValue() {
        BigDecimal negative = new BigDecimal("-500.25");
        SettlementInfo info = new SettlementInfo("loss", negative);

        assertEquals("loss", info.getKey());
        assertEquals(negative, info.getValue());
    }

    @Test
    void testWithLargeValue() {
        BigDecimal large = new BigDecimal("999999999.999999");
        SettlementInfo info = new SettlementInfo("large_amount", large);

        assertEquals(large, info.getValue());
    }
}
