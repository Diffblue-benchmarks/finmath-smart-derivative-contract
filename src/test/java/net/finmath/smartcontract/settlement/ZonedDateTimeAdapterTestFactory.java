package net.finmath.smartcontract.settlement;

import com.diffblue.cover.annotations.InterestingTestFactory;

/**
 * Factory class to provide test instances for ZonedDateTimeAdapter.
 */
public class ZonedDateTimeAdapterTestFactory {

    /**
     * Creates a ZonedDateTimeAdapter instance.
     * The adapter is used for XML binding and has a simple no-arg constructor.
     */
    @InterestingTestFactory
    public static ZonedDateTimeAdapter createZonedDateTimeAdapter() {
        return new ZonedDateTimeAdapter();
    }
}
