package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import com.diffblue.cover.annotations.InterestingTestFactory;

/**
 * Factory class to provide test instances for CalibrationSpecProviderOis.
 */
public class CalibrationSpecProviderOisTestFactory {

    /**
     * Creates a CalibrationSpecProviderOis instance with 5Y maturity and annual frequency.
     */
    @InterestingTestFactory
    public static CalibrationSpecProviderOis createOisProvider5Y() {
        return new CalibrationSpecProviderOis("5Y", "annual", 0.025);
    }

    /**
     * Creates a CalibrationSpecProviderOis instance with 10Y maturity and annual frequency.
     */
    @InterestingTestFactory
    public static CalibrationSpecProviderOis createOisProvider10Y() {
        return new CalibrationSpecProviderOis("10Y", "annual", 0.030);
    }

    /**
     * Creates a CalibrationSpecProviderOis instance with 2Y maturity and annual frequency.
     */
    @InterestingTestFactory
    public static CalibrationSpecProviderOis createOisProvider2Y() {
        return new CalibrationSpecProviderOis("2Y", "annual", 0.020);
    }

    /**
     * Creates a CalibrationSpecProviderOis instance with 1Y maturity and annual frequency.
     */
    @InterestingTestFactory
    public static CalibrationSpecProviderOis createOisProvider1Y() {
        return new CalibrationSpecProviderOis("1Y", "annual", 0.015);
    }

    /**
     * Creates a CalibrationSpecProviderOis instance with zero swap rate.
     */
    @InterestingTestFactory
    public static CalibrationSpecProviderOis createOisProviderZeroRate() {
        return new CalibrationSpecProviderOis("3Y", "annual", 0.0);
    }

    /**
     * Creates a CalibrationSpecProviderOis instance with negative swap rate.
     */
    @InterestingTestFactory
    public static CalibrationSpecProviderOis createOisProviderNegativeRate() {
        return new CalibrationSpecProviderOis("7Y", "annual", -0.005);
    }
}
