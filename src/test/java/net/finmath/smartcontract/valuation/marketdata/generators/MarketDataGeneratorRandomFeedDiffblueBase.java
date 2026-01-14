package net.finmath.smartcontract.valuation.marketdata.generators;

/**
 * Custom base class for Diffblue Cover generated tests for MarketDataGeneratorRandomFeed.
 * This prevents Cover from trying to create a Spring test context for a non-Spring POJO class.
 */
public abstract class MarketDataGeneratorRandomFeedDiffblueBase {
    // Empty base class - no Spring annotations needed
}
