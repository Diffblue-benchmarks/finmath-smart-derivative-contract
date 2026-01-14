package net.finmath.smartcontract.valuation.marketdata.data;

import com.diffblue.cover.annotations.InterestingTestFactory;

/**
 * Factory class to provide test instances for LocalDateTimeAdapter.
 */
public class LocalDateTimeAdapterTestFactory {

    /**
     * Creates a LocalDateTimeAdapter instance.
     * The adapter is used for XML binding and has a simple no-arg constructor.
     */
    @InterestingTestFactory
    public static LocalDateTimeAdapter createLocalDateTimeAdapter() {
        return new LocalDateTimeAdapter();
    }
}
