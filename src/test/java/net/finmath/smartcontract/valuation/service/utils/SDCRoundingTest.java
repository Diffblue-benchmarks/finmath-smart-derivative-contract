package net.finmath.smartcontract.valuation.service.utils;

import org.junit.jupiter.api.Test;

import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for SDCRounding class.
 */
class SDCRoundingTest {

    @Test
    void testConstructorWithScaleAndRoundingMode() {
        SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);
        assertNotNull(rounding);
    }

    @Test
    void testRoundDouble_TwoDecimalPlaces() {
        SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

        assertEquals(1.23, rounding.roundDouble(1.234));
        assertEquals(1.24, rounding.roundDouble(1.235));
        assertEquals(1.24, rounding.roundDouble(1.236));
    }

    @Test
    void testRoundDouble_ZeroDecimalPlaces() {
        SDCRounding rounding = new SDCRounding(0, RoundingMode.HALF_UP);

        assertEquals(1.0, rounding.roundDouble(1.4));
        assertEquals(2.0, rounding.roundDouble(1.5));
        assertEquals(2.0, rounding.roundDouble(1.6));
    }

    @Test
    void testRoundDouble_FourDecimalPlaces() {
        SDCRounding rounding = new SDCRounding(4, RoundingMode.HALF_UP);

        assertEquals(1.2346, rounding.roundDouble(1.23456));
        assertEquals(1.2345, rounding.roundDouble(1.23454));
    }

    @Test
    void testRoundDouble_RoundingModeDown() {
        SDCRounding rounding = new SDCRounding(2, RoundingMode.DOWN);

        assertEquals(1.23, rounding.roundDouble(1.239));
        assertEquals(1.23, rounding.roundDouble(1.234));
    }

    @Test
    void testRoundDouble_RoundingModeUp() {
        SDCRounding rounding = new SDCRounding(2, RoundingMode.UP);

        assertEquals(1.24, rounding.roundDouble(1.231));
        assertEquals(1.24, rounding.roundDouble(1.239));
    }

    @Test
    void testRoundDouble_NegativeNumbers() {
        SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

        assertEquals(-1.23, rounding.roundDouble(-1.234));
        assertEquals(-1.24, rounding.roundDouble(-1.235));
    }

    @Test
    void testRoundDouble_Zero() {
        SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);
        assertEquals(0.0, rounding.roundDouble(0.0));
    }

    @Test
    void testGetRoundedValueAsIntegerString_TwoDecimalPlaces() {
        SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

        assertEquals("123", rounding.getRoundedValueAsIntegerString(1.23));
        assertEquals("125", rounding.getRoundedValueAsIntegerString(1.25));
        assertEquals("10050", rounding.getRoundedValueAsIntegerString(100.50));
    }

    @Test
    void testGetRoundedValueAsIntegerString_RemovesDecimalPoint() {
        SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

        String result = rounding.getRoundedValueAsIntegerString(99.99);
        assertFalse(result.contains("."));
        assertEquals("9999", result);
    }

    @Test
    void testGetDoubleFromIntegerString_TwoDecimalScale() {
        SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

        assertEquals(1.23, rounding.getDoubleFromIntegerString("123"));
        assertEquals(100.50, rounding.getDoubleFromIntegerString("10050"));
    }

    @Test
    void testGetDoubleFromIntegerString_SingleDigit() {
        SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

        assertEquals(0.05, rounding.getDoubleFromIntegerString("5"));
    }

    @Test
    void testGetDoubleFromIntegerString_TwoDigits() {
        SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

        assertEquals(0.55, rounding.getDoubleFromIntegerString("55"));
    }

    @Test
    void testRoundTrip_ConversionConsistency() {
        SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

        double original = 123.45;
        String intString = rounding.getRoundedValueAsIntegerString(original);
        double recovered = rounding.getDoubleFromIntegerString(intString);

        assertEquals(original, recovered, 0.001);
    }

    @Test
    void testRoundDouble_VerySmallNumbers() {
        SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

        assertEquals(0.01, rounding.roundDouble(0.012));
        assertEquals(0.01, rounding.roundDouble(0.005));
    }

    @Test
    void testRoundDouble_LargeNumbers() {
        SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

        assertEquals(999999.99, rounding.roundDouble(999999.994));
        assertEquals(1000000.00, rounding.roundDouble(999999.995));
    }
}
