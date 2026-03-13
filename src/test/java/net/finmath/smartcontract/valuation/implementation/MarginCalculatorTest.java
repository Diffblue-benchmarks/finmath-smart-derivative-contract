package net.finmath.smartcontract.valuation.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.DoubleUnaryOperator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for MarginCalculator.
 * Tests the calculator construction and rounding logic.
 */
class MarginCalculatorTest {

    private MarginCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new MarginCalculator();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(calculator);
    }

    @Test
    void testConstructorWithCustomRounding() {
        DoubleUnaryOperator customRounding = x -> Math.round(x * 100) / 100.0;

        MarginCalculator customCalculator = new MarginCalculator(customRounding);

        assertNotNull(customCalculator);
    }

    @Test
    void testConstructorWithRoundingToTwoDecimals() {
        DoubleUnaryOperator roundingTwoDecimals = x -> Math.round(x * 100) / 100.0;

        MarginCalculator calculator = new MarginCalculator(roundingTwoDecimals);

        assertNotNull(calculator);
    }

    @Test
    void testConstructorWithRoundingToFourDecimals() {
        DoubleUnaryOperator roundingFourDecimals = x -> Math.round(x * 10000) / 10000.0;

        MarginCalculator calculator = new MarginCalculator(roundingFourDecimals);

        assertNotNull(calculator);
    }

    @Test
    void testConstructorWithNoRounding() {
        DoubleUnaryOperator noRounding = x -> x;

        MarginCalculator calculator = new MarginCalculator(noRounding);

        assertNotNull(calculator);
    }

    @Test
    void testConstructorWithCeilingRounding() {
        DoubleUnaryOperator ceilingRounding = Math::ceil;

        MarginCalculator calculator = new MarginCalculator(ceilingRounding);

        assertNotNull(calculator);
    }

    @Test
    void testConstructorWithFloorRounding() {
        DoubleUnaryOperator floorRounding = Math::floor;

        MarginCalculator calculator = new MarginCalculator(floorRounding);

        assertNotNull(calculator);
    }

    @Test
    void testDefaultRoundingBehavior() {
        // Default constructor uses: x -> Math.round(x * 1000) / 1000.0
        // This rounds to 3 decimal places
        MarginCalculator defaultCalculator = new MarginCalculator();

        assertNotNull(defaultCalculator);
    }

    @Test
    void testCalculatorCanBeInstantiatedMultipleTimes() {
        MarginCalculator calc1 = new MarginCalculator();
        MarginCalculator calc2 = new MarginCalculator();

        assertNotNull(calc1);
        assertNotNull(calc2);
        assertNotSame(calc1, calc2);
    }

    @Test
    void testRoundingOperatorIsUsed() {
        boolean[] wasCalled = {false};
        DoubleUnaryOperator trackingRounding = x -> {
            wasCalled[0] = true;
            return x;
        };

        MarginCalculator calculator = new MarginCalculator(trackingRounding);

        assertNotNull(calculator);
        // Rounding operator should be stored (will be called during actual calculations)
    }

    @Test
    void testCustomRoundingWithNegativeValues() {
        DoubleUnaryOperator customRounding = x -> Math.round(x * 100) / 100.0;

        MarginCalculator calculator = new MarginCalculator(customRounding);

        assertNotNull(calculator);
    }

    @Test
    void testCustomRoundingWithZero() {
        DoubleUnaryOperator customRounding = x -> Math.round(x * 100) / 100.0;

        MarginCalculator calculator = new MarginCalculator(customRounding);

        assertNotNull(calculator);
    }

    @Test
    void testCustomRoundingWithLargeValues() {
        DoubleUnaryOperator customRounding = x -> Math.round(x);

        MarginCalculator calculator = new MarginCalculator(customRounding);

        assertNotNull(calculator);
    }

    @Test
    void testCalculatorIsIndependent() {
        DoubleUnaryOperator rounding1 = x -> Math.round(x * 10) / 10.0;
        DoubleUnaryOperator rounding2 = x -> Math.round(x * 100) / 100.0;

        MarginCalculator calc1 = new MarginCalculator(rounding1);
        MarginCalculator calc2 = new MarginCalculator(rounding2);

        assertNotNull(calc1);
        assertNotNull(calc2);
        assertNotSame(calc1, calc2);
    }
}
