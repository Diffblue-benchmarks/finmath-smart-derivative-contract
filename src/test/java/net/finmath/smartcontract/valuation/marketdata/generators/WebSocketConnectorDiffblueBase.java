package net.finmath.smartcontract.valuation.marketdata.generators;

/**
 * Custom base class for Diffblue Cover generated tests for WebSocketConnector.
 * This prevents Cover from trying to create a Spring test context for a non-Spring POJO class.
 * The WebSocketConnector class requires a Properties object as a constructor parameter,
 * but it doesn't need to be a Spring bean.
 */
public abstract class WebSocketConnectorDiffblueBase {
    // Empty base class - no Spring annotations needed
}
