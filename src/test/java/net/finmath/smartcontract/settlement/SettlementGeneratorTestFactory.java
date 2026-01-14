package net.finmath.smartcontract.settlement;

import com.diffblue.cover.annotations.InterestingTestFactory;

/**
 * Factory class to provide test instances for SettlementGenerator.
 */
public class SettlementGeneratorTestFactory {

    /**
     * Creates a SettlementGenerator instance.
     * The no-arg constructor is straightforward and doesn't require complex setup.
     */
    @InterestingTestFactory
    public static SettlementGenerator createSettlementGenerator() {
        return new SettlementGenerator();
    }
}
