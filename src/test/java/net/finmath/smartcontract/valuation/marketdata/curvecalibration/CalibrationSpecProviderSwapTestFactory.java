package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import com.diffblue.cover.annotations.InterestingTestFactory;

/**
 * Factory class to provide test instances for CalibrationSpecProviderSwap.
 */
public class CalibrationSpecProviderSwapTestFactory {

    /**
     * Creates a CalibrationSpecProviderSwap instance with 6M tenor, semiannual frequency, and 5Y maturity.
     */
    @InterestingTestFactory
    public static CalibrationSpecProviderSwap createSwapProvider6M5Y() {
        return new CalibrationSpecProviderSwap("6M", "semiannual", "5Y", 0.028);
    }

    /**
     * Creates a CalibrationSpecProviderSwap instance with 6M tenor, semiannual frequency, and 10Y maturity.
     */
    @InterestingTestFactory
    public static CalibrationSpecProviderSwap createSwapProvider6M10Y() {
        return new CalibrationSpecProviderSwap("6M", "semiannual", "10Y", 0.032);
    }

    /**
     * Creates a CalibrationSpecProviderSwap instance with 3M tenor and quarterly frequency.
     */
    @InterestingTestFactory
    public static CalibrationSpecProviderSwap createSwapProvider3M() {
        return new CalibrationSpecProviderSwap("3M", "quarterly", "7Y", 0.027);
    }

    /**
     * Creates a CalibrationSpecProviderSwap instance with 1M tenor and monthly frequency.
     */
    @InterestingTestFactory
    public static CalibrationSpecProviderSwap createSwapProvider1M() {
        return new CalibrationSpecProviderSwap("1M", "monthly", "3Y", 0.022);
    }

    /**
     * Creates a CalibrationSpecProviderSwap instance with 6M tenor and 2Y maturity.
     */
    @InterestingTestFactory
    public static CalibrationSpecProviderSwap createSwapProvider6M2Y() {
        return new CalibrationSpecProviderSwap("6M", "semiannual", "2Y", 0.018);
    }

    /**
     * Creates a CalibrationSpecProviderSwap instance with zero swap rate.
     */
    @InterestingTestFactory
    public static CalibrationSpecProviderSwap createSwapProviderZeroRate() {
        return new CalibrationSpecProviderSwap("6M", "semiannual", "1Y", 0.0);
    }

    /**
     * Creates a CalibrationSpecProviderSwap instance with negative swap rate.
     */
    @InterestingTestFactory
    public static CalibrationSpecProviderSwap createSwapProviderNegativeRate() {
        return new CalibrationSpecProviderSwap("6M", "semiannual", "4Y", -0.003);
    }
}
