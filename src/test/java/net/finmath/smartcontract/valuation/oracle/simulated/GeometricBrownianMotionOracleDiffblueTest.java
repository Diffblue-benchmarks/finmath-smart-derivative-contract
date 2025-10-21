package net.finmath.smartcontract.valuation.oracle.simulated;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import net.finmath.montecarlo.RandomVariableFromDoubleArray;
import net.finmath.stochastic.RandomVariable;
import net.finmath.stochastic.Scalar;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {GeometricBrownianMotionOracle.class})
@ExtendWith(SpringExtension.class)
class GeometricBrownianMotionOracleDiffblueTest {
  @Autowired private GeometricBrownianMotionOracle geometricBrownianMotionOracle;

  /**
   * Test {@link GeometricBrownianMotionOracle#GeometricBrownianMotionOracle(LocalDateTime)}.
   *
   * <p>Method under test: {@link
   * GeometricBrownianMotionOracle#GeometricBrownianMotionOracle(LocalDateTime)}
   */
  @Test
  @DisplayName("Test new GeometricBrownianMotionOracle(LocalDateTime)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void GeometricBrownianMotionOracle.<init>(LocalDateTime)"})
  void testNewGeometricBrownianMotionOracle() {
    // Arrange and Act
    RandomVariable actualValue =
        new GeometricBrownianMotionOracle(LocalDate.of(1970, 1, 1).atStartOfDay())
            .getValue(
                LocalDate.of(1970, 1, 1).atStartOfDay(), LocalDate.of(1970, 1, 1).atStartOfDay());

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
    assertEquals(1.0d, actualValue.getAverage());
    assertEquals(1.0d, actualValue.getMax());
    assertEquals(1.0d, actualValue.getMin());
    assertTrue(actualValue.isDeterministic());
    assertEquals(Double.NEGATIVE_INFINITY, actualValue.getFiltrationTime());
    RandomVariable actualExpectationResult = actualValue.expectation();
    assertSame(actualValue, actualExpectationResult);
  }

  /**
   * Test {@link GeometricBrownianMotionOracle#GeometricBrownianMotionOracle(LocalDateTime)}.
   *
   * <p>Method under test: {@link
   * GeometricBrownianMotionOracle#GeometricBrownianMotionOracle(LocalDateTime)}
   */
  @Test
  @DisplayName("Test new GeometricBrownianMotionOracle(LocalDateTime)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void GeometricBrownianMotionOracle.<init>(LocalDateTime)"})
  void testNewGeometricBrownianMotionOracle2() {
    // Arrange
    LocalDate ofYearDayResult = LocalDate.ofYearDay(1, 1);

    // Act
    RandomVariable actualValue =
        new GeometricBrownianMotionOracle(ofYearDayResult.atStartOfDay())
            .getValue(
                LocalDate.of(1970, 1, 1).atStartOfDay(), LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    assertTrue(actualValue instanceof RandomVariableFromDoubleArray);
    assertTrue(actualValue.abs() instanceof RandomVariableFromDoubleArray);
    assertTrue(actualValue.average() instanceof RandomVariableFromDoubleArray);
    assertTrue(actualValue.cos() instanceof RandomVariableFromDoubleArray);
    assertTrue(actualValue.expectation() instanceof RandomVariableFromDoubleArray);
    assertTrue(actualValue.invert() instanceof RandomVariableFromDoubleArray);
    assertTrue(actualValue.isNaN() instanceof RandomVariableFromDoubleArray);
    assertTrue(actualValue.sin() instanceof RandomVariableFromDoubleArray);
    assertTrue(actualValue.sqrt() instanceof RandomVariableFromDoubleArray);
    assertTrue(actualValue.squared() instanceof RandomVariableFromDoubleArray);
    assertTrue(actualValue.variance() instanceof RandomVariableFromDoubleArray);
    assertEquals(0.020958587562626273d, actualValue.getStandardError());
    assertEquals(0.35361353954627667d, actualValue.getMin());
    assertEquals(0.43926239262027267d, actualValue.getVariance());
    assertEquals(0.43970209471498767d, actualValue.getSampleVariance());
    assertEquals(0.6627687323797591d, actualValue.getStandardDeviation());
    assertEquals(1, actualValue.getTypePriority());
    assertEquals(1.4728960327616165d, actualValue.getAverage());
    assertEquals(1000, actualValue.size());
    assertEquals(1000, actualValue.getRealizations().length);
    assertEquals(20.0d, actualValue.getFiltrationTime());
    assertEquals(5.965652410004549d, actualValue.getMax());
    assertFalse(actualValue.isDeterministic());
  }
}
