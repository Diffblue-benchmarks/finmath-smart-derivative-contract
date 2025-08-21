package net.finmath.smartcontract.valuation.oracle.simulated;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
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
import net.finmath.time.TenorFromArray;
import net.finmath.time.TimeDiscretization;
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
   * Test {@link GeometricBrownianMotionOracle#GeometricBrownianMotionOracle(LocalDateTime, double,
   * double, double, double, int)}.
   *
   * <p>Method under test: {@link
   * GeometricBrownianMotionOracle#GeometricBrownianMotionOracle(LocalDateTime, double, double,
   * double, double, int)}
   */
  @Test
  @DisplayName(
      "Test new GeometricBrownianMotionOracle(LocalDateTime, double, double, double, double, int)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void GeometricBrownianMotionOracle.<init>(LocalDateTime, double, double, double, double, int)"
  })
  void testNewGeometricBrownianMotionOracle() {
    // Arrange and Act
    GeometricBrownianMotionOracle actualGeometricBrownianMotionOracle =
        new GeometricBrownianMotionOracle(
            LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d, 10.0d, 10.0d, 10.0d, 10);
    LocalDateTime evaluationTime = LocalDate.of(1970, 1, 1).atStartOfDay();
    RandomVariable actualValue =
        actualGeometricBrownianMotionOracle.getValue(
            evaluationTime, LocalDate.of(1970, 1, 1).atStartOfDay());

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
   * Test {@link GeometricBrownianMotionOracle#GeometricBrownianMotionOracle(LocalDateTime, double,
   * double, double, double, int)}.
   *
   * <p>Method under test: {@link
   * GeometricBrownianMotionOracle#GeometricBrownianMotionOracle(LocalDateTime, double, double,
   * double, double, int)}
   */
  @Test
  @DisplayName(
      "Test new GeometricBrownianMotionOracle(LocalDateTime, double, double, double, double, int)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void GeometricBrownianMotionOracle.<init>(LocalDateTime, double, double, double, double, int)"
  })
  void testNewGeometricBrownianMotionOracle2() {
    // Arrange and Act
    GeometricBrownianMotionOracle actualGeometricBrownianMotionOracle =
        new GeometricBrownianMotionOracle(
            LocalDate.ofYearDay(1, 1).atStartOfDay(), 10.0d, 10.0d, 10.0d, 10.0d, 10);
    LocalDateTime evaluationTime = LocalDate.of(1970, 1, 1).atStartOfDay();
    RandomVariable actualValue =
        actualGeometricBrownianMotionOracle.getValue(
            evaluationTime, LocalDate.of(1970, 1, 1).atStartOfDay());

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
    assertEquals(1, actualValue.getTypePriority());
    assertEquals(1.266363963436299E-137d, actualValue.getStandardError());
    assertEquals(1.3326724488745027E-192d, actualValue.getMin());
    assertEquals(1.3348648237389648E-136d, actualValue.getMax());
    assertEquals(1.3348648237389648E-137d, actualValue.getAverage());
    assertEquals(1.6036776878900916E-273d, actualValue.getVariance());
    assertEquals(1.7818640976556574E-273d, actualValue.getSampleVariance());
    assertEquals(10, actualValue.size());
    assertEquals(10.0d, actualValue.getFiltrationTime());
    assertEquals(4.0045944712168943E-137d, actualValue.getStandardDeviation());
    assertFalse(actualValue.isDeterministic());
    assertArrayEquals(
        new double[] {
          3.5713126274155354E-178d,
          1.3326724488745027E-192d,
          1.014230896373704E-176d,
          1.4269352074306705E-179d,
          8.228708514539412E-174d,
          1.3348648237389648E-136d,
          2.3740370081906858E-164d,
          4.47159680374729E-164d,
          1.5652803302888778E-159d,
          1.2575915999867182E-161d
        },
        actualValue.getRealizations(),
        0.0);
  }

  /**
   * Test {@link GeometricBrownianMotionOracle#GeometricBrownianMotionOracle(TimeDiscretization,
   * LocalDateTime, double, double, double, int)}.
   *
   * <p>Method under test: {@link
   * GeometricBrownianMotionOracle#GeometricBrownianMotionOracle(TimeDiscretization, LocalDateTime,
   * double, double, double, int)}
   */
  @Test
  @DisplayName(
      "Test new GeometricBrownianMotionOracle(TimeDiscretization, LocalDateTime, double, double, double, int)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void GeometricBrownianMotionOracle.<init>(TimeDiscretization, LocalDateTime, double, double, double, int)"
  })
  void testNewGeometricBrownianMotionOracle3() {
    // Arrange
    TenorFromArray timeDiscretization = new TenorFromArray(10.0d, 10, 0.5d);

    LocalDate ofResult = LocalDate.of(1970, 1, 1);
    LocalDateTime initialTime = ofResult.atStartOfDay();

    // Act
    new GeometricBrownianMotionOracle(timeDiscretization, initialTime, 10.0d, 10.0d, 10.0d, 10);

    // Assert that nothing has changed
    assertEquals(1.1415525114155251E-4d, timeDiscretization.getTickSize());
    assertEquals(10, timeDiscretization.getNumberOfTimeSteps());
    assertEquals(10.0d, timeDiscretization.getFirstTime());
    assertEquals(11, timeDiscretization.getAsArrayList().size());
    assertEquals(11, timeDiscretization.getNumberOfTimes());
    assertEquals(15.0d, timeDiscretization.getLastTime());
    assertTrue(timeDiscretization.iterator().hasNext());
    assertSame(ofResult, initialTime.toLocalDate());
    assertArrayEquals(
        new double[] {10.0d, 10.5d, 11.0d, 11.5d, 12.0d, 12.5d, 13.0d, 13.5d, 14.0d, 14.5d, 15.0d},
        timeDiscretization.getAsDoubleArray(),
        0.0);
  }
}
