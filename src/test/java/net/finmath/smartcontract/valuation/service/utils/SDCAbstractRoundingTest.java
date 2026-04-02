package net.finmath.smartcontract.valuation.service.utils;

import java.math.RoundingMode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SDCAbstractRoundingTest {

    private SDCAbstractRounding rounding;

    @BeforeEach
    void setUp() {
        rounding = new SDCAbstractRounding() {
            {
                scale = 2;
                roundingMode = RoundingMode.HALF_UP;
            }
        };
    }

    @Test
    void testRoundDouble() {
        double result = rounding.roundDouble(3.14159);
        Assertions.assertEquals(3.14, result, 0.0001, "roundDouble should round to 2 decimal places");
    }

    @Test
    void testRoundDoubleHalfUp() {
        double result = rounding.roundDouble(2.555);
        Assertions.assertEquals(2.56, result, 0.0001, "roundDouble should round half up");
    }

    @Test
    void testGetRoundedValueAsIntegerString() {
        String result = rounding.getRoundedValueAsIntegerString(12.34);
        Assertions.assertEquals("1234", result, "getRoundedValueAsIntegerString should remove decimal point");
    }

    @Test
    void testGetRoundedValueAsIntegerStringWithRounding() {
        String result = rounding.getRoundedValueAsIntegerString(1.005);
        Assertions.assertFalse(result.contains("."), "result should not contain decimal point");
    }

    @Test
    void testGetDoubleFromIntegerStringLengthOne() {
        double result = rounding.getDoubleFromIntegerString("5");
        Assertions.assertEquals(0.05, result, 0.0001, "single char string should be interpreted as 0.0X");
    }

    @Test
    void testGetDoubleFromIntegerStringLengthTwo() {
        double result = rounding.getDoubleFromIntegerString("75");
        Assertions.assertEquals(0.75, result, 0.0001, "two char string should be interpreted as 0.XX");
    }

    @Test
    void testGetDoubleFromIntegerStringLengthMoreThanTwo() {
        double result = rounding.getDoubleFromIntegerString("1234");
        Assertions.assertEquals(12.34, result, 0.0001, "longer string should insert decimal based on scale");
    }

    @Test
    void testGetDoubleFromIntegerStringRoundTrip() {
        double original = 99.99;
        String intString = rounding.getRoundedValueAsIntegerString(original);
        double result = rounding.getDoubleFromIntegerString(intString);
        Assertions.assertEquals(original, result, 0.0001, "round-trip conversion should preserve value");
    }
}
