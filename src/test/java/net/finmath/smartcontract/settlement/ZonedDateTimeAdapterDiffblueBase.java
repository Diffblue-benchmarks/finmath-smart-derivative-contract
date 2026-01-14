package net.finmath.smartcontract.settlement;

import com.diffblue.cover.annotations.InterestingTestFactory;

/**
 * Custom base class for Diffblue Cover generated tests for ZonedDateTimeAdapter.
 * Provides factory methods to create test instances with properly formatted input strings.
 */
public abstract class ZonedDateTimeAdapterDiffblueBase {

    /**
     * Factory method to create a valid date-time string for ZonedDateTimeAdapter.unmarshal().
     * The unmarshal method expects a string in the format "yyyyMMdd-HHmmss".
     * This prevents DateTimeParseException when testing the unmarshal method.
     *
     * @return A valid date-time string in format "yyyyMMdd-HHmmss"
     */
    @InterestingTestFactory
    public static String createValidDateTimeString() {
        return "20240101-120000";
    }

    /**
     * Factory method to create another valid date-time string with different values.
     * This provides variety in test scenarios.
     *
     * @return A valid date-time string in format "yyyyMMdd-HHmmss"
     */
    @InterestingTestFactory
    public static String createAlternativeDateTimeString() {
        return "20231215-153045";
    }
}
