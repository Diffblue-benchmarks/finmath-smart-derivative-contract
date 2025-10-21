package net.finmath.smartcontract.valuation.oracle.simulated;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import net.finmath.stochastic.RandomVariable;
import net.finmath.stochastic.Scalar;
import net.finmath.time.TenorFromArray;
import net.finmath.time.TimeDiscretization;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class GeometricBrownianMotionOracleDiffblueTest {
  /**
   * Test {@link GeometricBrownianMotionOracle#GeometricBrownianMotionOracle(LocalDateTime, double, double, double, double, int)}.
   * <p>
   * Method under test: {@link GeometricBrownianMotionOracle#GeometricBrownianMotionOracle(LocalDateTime, double, double, double, double, int)}
   */
  @Test
  @DisplayName("Test new GeometricBrownianMotionOracle(LocalDateTime, double, double, double, double, int)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void GeometricBrownianMotionOracle.<init>(LocalDateTime, double, double, double, double, int)"})
  void testNewGeometricBrownianMotionOracle() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    GeometricBrownianMotionOracle actualGeometricBrownianMotionOracle = new GeometricBrownianMotionOracle(
        LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d, 10.0d, 10.0d, 10.0d, 10);
    LocalDateTime evaluationTime = LocalDate.of(1970, 1, 1).atStartOfDay();
    RandomVariable actualValue = actualGeometricBrownianMotionOracle.getValue(evaluationTime,
        LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    assertTrue(actualValue instanceof Scalar);
    assertTrue(actualValue.abs() instanceof Scalar);
    assertTrue(actualValue.cos() instanceof Scalar);
    assertTrue(actualValue.exp() instanceof Scalar);
    assertTrue(actualValue.expm1() instanceof Scalar);
    assertTrue(actualValue.invert() instanceof Scalar);
    assertTrue(actualValue.isNaN() instanceof Scalar);
    assertTrue(actualValue.sin() instanceof Scalar);
    assertTrue(actualValue.sqrt() instanceof Scalar);
    assertTrue(actualValue.squared() instanceof Scalar);
    assertTrue(actualValue.variance() instanceof Scalar);
    assertNull(actualValue.getRealizations());
    assertNull(actualValue.getOperator());
    assertNull(actualValue.getRealizationsStream());
    assertEquals(0, actualValue.getTypePriority());
    assertEquals(0.0d, actualValue.getSampleVariance());
    assertEquals(0.0d, actualValue.getStandardDeviation());
    assertEquals(0.0d, actualValue.getStandardError());
    assertEquals(0.0d, actualValue.getVariance());
    assertEquals(1, actualValue.size());
    assertEquals(10.000000000000002d, actualValue.getAverage());
    assertEquals(10.000000000000002d, actualValue.getMax());
    assertEquals(10.000000000000002d, actualValue.getMin());
    assertTrue(actualValue.isDeterministic());
    assertEquals(Double.NEGATIVE_INFINITY, actualValue.getFiltrationTime());
    assertSame(actualValue, actualValue.expectation());
  }

  /**
   * Test {@link GeometricBrownianMotionOracle#GeometricBrownianMotionOracle(TimeDiscretization, LocalDateTime, double, double, double, int)}.
   * <p>
   * Method under test: {@link GeometricBrownianMotionOracle#GeometricBrownianMotionOracle(TimeDiscretization, LocalDateTime, double, double, double, int)}
   */
  @Test
  @DisplayName("Test new GeometricBrownianMotionOracle(TimeDiscretization, LocalDateTime, double, double, double, int)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void GeometricBrownianMotionOracle.<init>(TimeDiscretization, LocalDateTime, double, double, double, int)"})
  void testNewGeometricBrownianMotionOracle2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    TenorFromArray timeDiscretization = new TenorFromArray(new double[]{2.0d, -1.0d, 2.0d, -1.0d});

    // Act
    GeometricBrownianMotionOracle actualGeometricBrownianMotionOracle = new GeometricBrownianMotionOracle(
        timeDiscretization, LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d, 10.0d, 10.0d, 10);
    LocalDateTime evaluationTime = LocalDate.of(1970, 1, 1).atStartOfDay();
    RandomVariable actualValue = actualGeometricBrownianMotionOracle.getValue(evaluationTime,
        LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    assertTrue(actualValue instanceof Scalar);
    assertTrue(actualValue.abs() instanceof Scalar);
    assertTrue(actualValue.cos() instanceof Scalar);
    assertTrue(actualValue.exp() instanceof Scalar);
    assertTrue(actualValue.expm1() instanceof Scalar);
    assertTrue(actualValue.invert() instanceof Scalar);
    assertTrue(actualValue.isNaN() instanceof Scalar);
    assertTrue(actualValue.sin() instanceof Scalar);
    assertTrue(actualValue.sqrt() instanceof Scalar);
    assertTrue(actualValue.squared() instanceof Scalar);
    assertTrue(actualValue.variance() instanceof Scalar);
    assertNull(actualValue.getRealizations());
    assertNull(actualValue.getOperator());
    assertNull(actualValue.getRealizationsStream());
    assertEquals(0, actualValue.getTypePriority());
    assertEquals(0.0d, actualValue.getSampleVariance());
    assertEquals(0.0d, actualValue.getStandardDeviation());
    assertEquals(0.0d, actualValue.getStandardError());
    assertEquals(0.0d, actualValue.getVariance());
    assertEquals(1, actualValue.size());
    assertEquals(10.000000000000002d, actualValue.getAverage());
    assertEquals(10.000000000000002d, actualValue.getMax());
    assertEquals(10.000000000000002d, actualValue.getMin());
    assertTrue(actualValue.isDeterministic());
    assertEquals(Double.NEGATIVE_INFINITY, actualValue.getFiltrationTime());
    assertSame(actualValue, actualValue.expectation());
  }
}
