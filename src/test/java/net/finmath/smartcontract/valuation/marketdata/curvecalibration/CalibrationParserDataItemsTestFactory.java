package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import com.diffblue.cover.annotations.InterestingTestFactory;

/**
 * Factory class to provide test instances for CalibrationParserDataItems.
 */
public class CalibrationParserDataItemsTestFactory {

    /**
     * Creates a CalibrationParserDataItems instance.
     * The no-arg constructor is straightforward and doesn't require complex setup.
     */
    @InterestingTestFactory
    public static CalibrationParserDataItems createCalibrationParserDataItems() {
        return new CalibrationParserDataItems();
    }
}
