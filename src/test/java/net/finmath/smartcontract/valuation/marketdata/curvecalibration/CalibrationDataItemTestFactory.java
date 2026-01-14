package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import com.diffblue.cover.annotations.InterestingTestFactory;

import java.time.LocalDateTime;

/**
 * Factory class to provide test instances for CalibrationDataItem and CalibrationDataItem.Spec.
 */
public class CalibrationDataItemTestFactory {

    /**
     * Creates a CalibrationDataItem.Spec instance with typical swap rate parameters.
     */
    @InterestingTestFactory
    public static CalibrationDataItem.Spec createSpecForSwapRate() {
        return new CalibrationDataItem.Spec(
                "ESTR_Swap-Rate_5Y",
                "ESTR",
                "Swap-Rate",
                "5Y"
        );
    }

    /**
     * Creates a CalibrationDataItem.Spec instance with Euribor6M parameters.
     */
    @InterestingTestFactory
    public static CalibrationDataItem.Spec createSpecForEuribor6M() {
        return new CalibrationDataItem.Spec(
                "Euribor6M_Swap-Rate_10Y",
                "Euribor6M",
                "Swap-Rate",
                "10Y"
        );
    }

    /**
     * Creates a CalibrationDataItem.Spec instance with short maturity.
     */
    @InterestingTestFactory
    public static CalibrationDataItem.Spec createSpecForShortMaturity() {
        return new CalibrationDataItem.Spec(
                "EONIA_Deposit-Rate_6M",
                "EONIA",
                "Deposit-Rate",
                "6M"
        );
    }

    /**
     * Creates a CalibrationDataItem instance with typical market data.
     */
    @InterestingTestFactory
    public static CalibrationDataItem createCalibrationDataItem() {
        CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec(
                "ESTR_Swap-Rate_5Y",
                "ESTR",
                "Swap-Rate",
                "5Y"
        );
        return new CalibrationDataItem(spec, 0.025, LocalDateTime.of(2024, 1, 15, 17, 0));
    }

    /**
     * Creates a CalibrationDataItem instance with Euribor6M data.
     */
    @InterestingTestFactory
    public static CalibrationDataItem createCalibrationDataItemEuribor6M() {
        CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec(
                "Euribor6M_Swap-Rate_10Y",
                "Euribor6M",
                "Swap-Rate",
                "10Y"
        );
        return new CalibrationDataItem(spec, 0.035, LocalDateTime.of(2024, 6, 30, 17, 0));
    }

    /**
     * Creates a CalibrationDataItem instance with null quote (allowed by the class).
     */
    @InterestingTestFactory
    public static CalibrationDataItem createCalibrationDataItemWithNullQuote() {
        CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec(
                "ESTR_Swap-Rate_2Y",
                "ESTR",
                "Swap-Rate",
                "2Y"
        );
        return new CalibrationDataItem(spec, null, LocalDateTime.of(2024, 3, 15, 17, 0));
    }

    /**
     * Creates a CalibrationDataItem instance with monthly maturity.
     */
    @InterestingTestFactory
    public static CalibrationDataItem createCalibrationDataItemMonthlyMaturity() {
        CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec(
                "Euribor3M_Deposit_6M",
                "Euribor3M",
                "Deposit",
                "6M"
        );
        return new CalibrationDataItem(spec, 0.015, LocalDateTime.of(2024, 12, 1, 17, 0));
    }

    /**
     * Creates a CalibrationDataItem instance with daily maturity.
     */
    @InterestingTestFactory
    public static CalibrationDataItem createCalibrationDataItemDailyMaturity() {
        CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec(
                "ESTR_Fixing_1D",
                "ESTR",
                "Fixing",
                "1D"
        );
        return new CalibrationDataItem(spec, 0.001, LocalDateTime.of(2024, 1, 1, 0, 0));
    }
}
