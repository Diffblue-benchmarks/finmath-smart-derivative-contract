package net.finmath.smartcontract.valuation.implementation;

import com.diffblue.cover.annotations.InterestingTestFactory;

import java.util.function.DoubleUnaryOperator;

/**
 * Factory class to provide test instances for MarginCalculator.
 */
public class MarginCalculatorTestFactory {

    /**
     * Creates a MarginCalculator instance with a standard rounding operator.
     * Rounds to 3 decimal places.
     */
    @InterestingTestFactory
    public static MarginCalculator createMarginCalculatorWithStandardRounding() {
        DoubleUnaryOperator rounding = x -> Math.round(x * 1000) / 1000.0;
        return new MarginCalculator(rounding);
    }

    /**
     * Creates a MarginCalculator instance with identity rounding (no rounding).
     */
    @InterestingTestFactory
    public static MarginCalculator createMarginCalculatorWithIdentityRounding() {
        DoubleUnaryOperator identityRounding = x -> x;
        return new MarginCalculator(identityRounding);
    }

    /**
     * Creates a MarginCalculator instance with rounding to 2 decimal places.
     * This is common for currency operations.
     */
    @InterestingTestFactory
    public static MarginCalculator createMarginCalculatorWithCurrencyRounding() {
        DoubleUnaryOperator currencyRounding = x -> Math.round(x * 100) / 100.0;
        return new MarginCalculator(currencyRounding);
    }

    /**
     * Creates a MarginCalculator instance using the default no-arg constructor.
     * Uses the default rounding to 3 decimal places.
     */
    @InterestingTestFactory
    public static MarginCalculator createMarginCalculatorWithDefaultRounding() {
        return new MarginCalculator();
    }
}
