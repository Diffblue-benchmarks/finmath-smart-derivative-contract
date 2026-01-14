package net.finmath.smartcontract.valuation.marketdata.data;

import com.diffblue.cover.annotations.InterestingTestFactory;

/**
 * Custom base class for Diffblue Cover generated tests for LocalDateTimeAdapter.
 * Provides factory methods to create test instances with valid date-time strings.
 */
public abstract class LocalDateTimeAdapterDiffblueBase {

    /**
     * Factory method to create a valid date-time string for LocalDateTimeAdapter.unmarshal method.
     * This prevents DateTimeParseException when testing the unmarshal method.
     * The string follows the expected format "yyyyMMdd-HHmmss".
     *
     * @return A valid date-time string in the format "yyyyMMdd-HHmmss"
     */
    @InterestingTestFactory
    public static String createValidDateTimeString() {
        return "20220905-170000";
    }

    /**
     * Factory method to create an alternative valid date-time string.
     * This provides variety in test scenarios with a different date and time.
     *
     * @return A valid date-time string in the format "yyyyMMdd-HHmmss"
     */
    @InterestingTestFactory
    public static String createAlternativeDateTimeString() {
        return "20230115-150000";
    }

    /**
     * Factory method to create a valid date-time string for midnight.
     * This provides variety in test scenarios with edge case time value.
     *
     * @return A valid date-time string in the format "yyyyMMdd-HHmmss" for midnight
     */
    @InterestingTestFactory
    public static String createMidnightDateTimeString() {
        return "20220101-000000";
    }

    /**
     * Factory method to create a valid date-time string for end of day.
     * This provides variety in test scenarios with edge case time value.
     *
     * @return A valid date-time string in the format "yyyyMMdd-HHmmss" for end of day
     */
    @InterestingTestFactory
    public static String createEndOfDayDateTimeString() {
        return "20221231-235959";
    }
}
