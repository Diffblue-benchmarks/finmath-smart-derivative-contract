package net.finmath.smartcontract.valuation.oracle;

/**
 * Custom base class for Diffblue Cover generated tests for ValuationOracleSamplePath.
 * This prevents Cover from trying to create a Spring test context and mock primitive types.
 *
 * The issue is that ValuationOracleSamplePath constructor takes a primitive int parameter,
 * and Mockito cannot mock primitive types. By providing this base class, we signal to Cover
 * that it should not attempt to create a Spring context with @MockBean for the int parameter.
 */
public abstract class ValuationOracleSamplePathDiffblueBase {
    // Empty base class - no Spring annotations needed
    // Tests will be generated without Spring context setup
}
