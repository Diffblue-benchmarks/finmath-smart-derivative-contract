package net.finmath.smartcontract.valuation.service.websocket.client;

/**
 * Custom base class for Diffblue Cover generated tests for WebSocketClientEndpoint.
 * This prevents Cover from trying to create a Spring test context for a Jakarta WebSocket client endpoint.
 *
 * The WebSocketClientEndpoint class is a Jakarta WebSocket @ClientEndpoint that:
 * - Extends jakarta.websocket.Endpoint
 * - Requires URI, username, and password as constructor parameters
 * - Manages its own WebSocket session through ContainerProvider
 * - Is not designed to be a Spring bean
 *
 * By providing this empty base class, we signal to Cover that it should not attempt to create
 * a Spring ApplicationContext for testing this class. Instead, Cover will generate standard
 * unit tests that instantiate the class directly with mocked dependencies.
 */
public abstract class WebSocketClientEndpointDiffblueBase {
    // Empty base class - no Spring annotations needed
    // WebSocket client endpoints should be tested with mocked Sessions and URIs
}
