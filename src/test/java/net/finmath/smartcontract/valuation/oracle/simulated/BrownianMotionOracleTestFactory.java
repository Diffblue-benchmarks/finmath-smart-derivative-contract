package net.finmath.smartcontract.valuation.oracle.simulated;

import com.diffblue.cover.annotations.InterestingTestFactory;
import net.finmath.time.TimeDiscretization;
import net.finmath.time.TimeDiscretizationFromArray;

import java.time.LocalDateTime;

/**
 * Factory class to provide test instances for BrownianMotionOracle.
 */
public class BrownianMotionOracleTestFactory {

    /**
     * Creates a BrownianMotionOracle instance using the no-arg constructor with a fixed time
     * to avoid ArrayIndexOutOfBoundsException due to negative index.
     *
     * The default no-arg constructor uses LocalDateTime.now() which can cause issues when
     * getValue is called with times before the initial time.
     */
    @InterestingTestFactory
    public static BrownianMotionOracle createBrownianMotionOracleWithFixedTime() {
        // Use a fixed initial time far enough in the past to avoid negative index issues
        LocalDateTime fixedInitialTime = LocalDateTime.of(2000, 1, 1, 0, 0);
        return new BrownianMotionOracle(fixedInitialTime);
    }

    /**
     * Creates a BrownianMotionOracle instance with valid TimeDiscretization parameters
     * to avoid ArrayIndexOutOfBoundsException due to index out of bounds.
     *
     * The key is to ensure that getValue calls use times within the discretization range.
     */
    @InterestingTestFactory
    public static BrownianMotionOracle createBrownianMotionOracleWithValidTimeDiscretization() {
        LocalDateTime initialTime = LocalDateTime.of(2020, 1, 1, 0, 0);

        // Create a time discretization with sufficient range
        // timeHorizon of 10.0 years with daily steps (1.0/365.0)
        // This creates approximately 3650 time points
        double timeHorizon = 10.0;
        double deltaT = 1.0 / 365.0;
        TimeDiscretization timeDiscretization = new TimeDiscretizationFromArray(
            0.0,
            timeHorizon,
            deltaT,
            TimeDiscretizationFromArray.ShortPeriodLocation.SHORT_PERIOD_AT_END
        );

        double initialValue = 100.0;
        double riskFreeRate = 0.02;
        double volatility = 0.20;
        int numberOfPaths = 1000;

        return new BrownianMotionOracle(
            timeDiscretization,
            initialTime,
            initialValue,
            riskFreeRate,
            volatility,
            numberOfPaths
        );
    }

    /**
     * Creates a BrownianMotionOracle instance with extended time horizon
     * to handle various test scenarios without index out of bounds errors.
     */
    @InterestingTestFactory
    public static BrownianMotionOracle createBrownianMotionOracleWithExtendedHorizon() {
        LocalDateTime initialTime = LocalDateTime.of(2020, 1, 1, 0, 0);

        // Use parameters that won't cause index out of bounds
        double initialValue = 100.0;
        double timeHorizon = 30.0; // 30 years
        double riskFreeRate = 0.02;
        double volatility = 0.15;
        int numberOfPaths = 500;

        return new BrownianMotionOracle(
            initialTime,
            initialValue,
            timeHorizon,
            riskFreeRate,
            volatility,
            numberOfPaths
        );
    }
}
