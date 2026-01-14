package net.finmath.smartcontract.valuation.service.config;

/**
 * Custom base class for Diffblue Cover generated tests for BasicAuthWebSecurityConfiguration.
 * This prevents Cover from trying to create a Spring test context for a @Configuration class
 * that has complex dependencies and requires application properties to be loaded.
 *
 * The BasicAuthWebSecurityConfiguration class is a Spring Security configuration that depends on
 * ApplicationProperties, HttpSecurity, and other Spring beans that are difficult to set up in isolation.
 * By providing this empty base class, we signal to Cover that it should not attempt to create
 * a Spring context for testing this configuration class.
 */
public abstract class BasicAuthWebSecurityConfigurationDiffblueBase {
    // Empty base class - no Spring annotations needed
    // Tests for configuration classes are typically integration tests rather than unit tests
}
