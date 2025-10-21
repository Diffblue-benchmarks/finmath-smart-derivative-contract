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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BrownianMotionOracle.class})
@ExtendWith(SpringExtension.class)
class BrownianMotionOracleDiffblueTest {
  @Autowired private BrownianMotionOracle brownianMotionOracle;

  /**
   * Test {@link BrownianMotionOracle#BrownianMotionOracle(LocalDateTime)}.
   *
   * <p>Method under test: {@link BrownianMotionOracle#BrownianMotionOracle(LocalDateTime)}
   */
  @Test
  @DisplayName("Test new BrownianMotionOracle(LocalDateTime)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BrownianMotionOracle.<init>(LocalDateTime)"})
  void testNewBrownianMotionOracle() {
    // Arrange and Act
    RandomVariable actualValue =
        new BrownianMotionOracle(LocalDate.of(1970, 1, 1).atStartOfDay())
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
    assertEquals(0.0d, actualValue.getAverage());
    assertEquals(0.0d, actualValue.getMax());
    assertEquals(0.0d, actualValue.getMin());
    assertEquals(0.0d, actualValue.getSampleVariance());
    assertEquals(0.0d, actualValue.getStandardDeviation());
    assertEquals(0.0d, actualValue.getStandardError());
    assertEquals(0.0d, actualValue.getVariance());
    assertEquals(1, actualValue.size());
    assertTrue(actualValue.isDeterministic());
    assertEquals(Double.NEGATIVE_INFINITY, actualValue.getFiltrationTime());
    RandomVariable actualExpectationResult = actualValue.expectation();
    assertSame(actualValue, actualExpectationResult);
  }

  /**
   * Test {@link BrownianMotionOracle#BrownianMotionOracle(LocalDateTime)}.
   *
   * <p>Method under test: {@link BrownianMotionOracle#BrownianMotionOracle(LocalDateTime)}
   */
  @Test
  @DisplayName("Test new BrownianMotionOracle(LocalDateTime)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BrownianMotionOracle.<init>(LocalDateTime)"})
  void testNewBrownianMotionOracle2() {
    // Arrange
    LocalDate ofYearDayResult = LocalDate.ofYearDay(1, 1);

    // Act
    RandomVariable actualValue =
        new BrownianMotionOracle(ofYearDayResult.atStartOfDay())
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
    assertEquals(-0.011756276978071615d, actualValue.getAverage());
    assertEquals(-1.9983747559122673d, actualValue.getMin());
    assertEquals(0.020718579877407727d, actualValue.getStandardError());
    assertEquals(0.4292595521365243d, actualValue.getVariance());
    assertEquals(0.4296892413779022d, actualValue.getSampleVariance());
    assertEquals(0.6551790229674057d, actualValue.getStandardDeviation());
    assertEquals(1, actualValue.getTypePriority());
    assertEquals(1000, actualValue.size());
    assertEquals(1000, actualValue.getRealizations().length);
    assertEquals(2.2168789839536216d, actualValue.getMax());
    assertEquals(20.0d, actualValue.getFiltrationTime());
    assertFalse(actualValue.isDeterministic());
  }

  /**
   * Test {@link BrownianMotionOracle#BrownianMotionOracle(LocalDateTime, double, double, double,
   * double, int)}.
   *
   * <p>Method under test: {@link BrownianMotionOracle#BrownianMotionOracle(LocalDateTime, double,
   * double, double, double, int)}
   */
  @Test
  @DisplayName("Test new BrownianMotionOracle(LocalDateTime, double, double, double, double, int)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BrownianMotionOracle.<init>(LocalDateTime, double, double, double, double, int)"
  })
  void testNewBrownianMotionOracle3() {
    // Arrange and Act
    BrownianMotionOracle actualBrownianMotionOracle =
        new BrownianMotionOracle(
            LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d, 10.0d, 10.0d, 10.0d, 10);
    RandomVariable actualValue =
        actualBrownianMotionOracle.getValue(
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
    RandomVariable actualExpectationResult = actualValue.expectation();
    assertSame(actualValue, actualExpectationResult);
  }

  /**
   * Test {@link BrownianMotionOracle#BrownianMotionOracle(LocalDateTime, double, double, double,
   * double, int)}.
   *
   * <p>Method under test: {@link BrownianMotionOracle#BrownianMotionOracle(LocalDateTime, double,
   * double, double, double, int)}
   */
  @Test
  @DisplayName("Test new BrownianMotionOracle(LocalDateTime, double, double, double, double, int)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BrownianMotionOracle.<init>(LocalDateTime, double, double, double, double, int)"
  })
  void testNewBrownianMotionOracle4() {
    // Arrange
    LocalDate ofYearDayResult = LocalDate.ofYearDay(1, 1);

    // Act
    BrownianMotionOracle actualBrownianMotionOracle =
        new BrownianMotionOracle(ofYearDayResult.atStartOfDay(), 10.0d, 10.0d, 10.0d, 10.0d, 10);
    RandomVariable actualValue =
        actualBrownianMotionOracle.getValue(
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
    assertEquals(-9.16963440572464E44d, actualValue.getMin());
    assertEquals(1, actualValue.getTypePriority());
    assertEquals(10, actualValue.size());
    assertEquals(10.0d, actualValue.getFiltrationTime());
    assertEquals(2.5492670822372017E45d, actualValue.getMax());
    assertEquals(2.8191085850081394E44d, actualValue.getStandardError());
    assertEquals(5.6205646843888904E44d, actualValue.getAverage());
    assertEquals(7.947373214066597E89d, actualValue.getVariance());
    assertEquals(8.830414682296218E89d, actualValue.getSampleVariance());
    assertEquals(8.914804099960131E44d, actualValue.getStandardDeviation());
    assertFalse(actualValue.isDeterministic());
    assertArrayEquals(
        new double[] {
          -2.3918824258214753E43d,
          -9.16963440572464E44d,
          6.603545582364161E43d,
          -1.104759105111274E44d,
          2.4610351679138213E44d,
          2.5492670822372017E45d,
          8.316510668176279E44d,
          8.486709796011517E44d,
          1.1299352573612321E45d,
          1.0002595010984591E45d
        },
        actualValue.getRealizations(),
        0.0);
  }

  /**
   * Test {@link BrownianMotionOracle#BrownianMotionOracle(LocalDateTime, double, double, double,
   * double, int)}.
   *
   * <ul>
   *   <li>When {@code 0.180625}.
   * </ul>
   *
   * <p>Method under test: {@link BrownianMotionOracle#BrownianMotionOracle(LocalDateTime, double,
   * double, double, double, int)}
   */
  @Test
  @DisplayName(
      "Test new BrownianMotionOracle(LocalDateTime, double, double, double, double, int); when '0.180625'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BrownianMotionOracle.<init>(LocalDateTime, double, double, double, double, int)"
  })
  void testNewBrownianMotionOracle_when0180625() {
    // Arrange and Act
    BrownianMotionOracle actualBrownianMotionOracle =
        new BrownianMotionOracle(
            LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d, 10.0d, 10.0d, 0.180625d, 10);
    RandomVariable actualValue =
        actualBrownianMotionOracle.getValue(
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
    RandomVariable actualExpectationResult = actualValue.expectation();
    assertSame(actualValue, actualExpectationResult);
  }

  /**
   * Test {@link BrownianMotionOracle#getValue(LocalDateTime, LocalDateTime)}.
   *
   * <ul>
   *   <li>Given {@link LocalDate} with {@code 1970} and one and one atStartOfDay.
   *   <li>Then return {@link Scalar}.
   * </ul>
   *
   * <p>Method under test: {@link BrownianMotionOracle#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName(
      "Test getValue(LocalDateTime, LocalDateTime); given LocalDate with '1970' and one and one atStartOfDay; then return Scalar")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"RandomVariable BrownianMotionOracle.getValue(LocalDateTime, LocalDateTime)"})
  void testGetValue_givenLocalDateWith1970AndOneAndOneAtStartOfDay_thenReturnScalar() {
    // Arrange and Act
    RandomVariable actualValue =
        new BrownianMotionOracle(LocalDate.of(1970, 1, 1).atStartOfDay())
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
    assertEquals(0.0d, actualValue.getAverage());
    assertEquals(0.0d, actualValue.getMax());
    assertEquals(0.0d, actualValue.getMin());
    assertEquals(0.0d, actualValue.getSampleVariance());
    assertEquals(0.0d, actualValue.getStandardDeviation());
    assertEquals(0.0d, actualValue.getStandardError());
    assertEquals(0.0d, actualValue.getVariance());
    assertEquals(1, actualValue.size());
    assertTrue(actualValue.isDeterministic());
    assertEquals(Double.NEGATIVE_INFINITY, actualValue.getFiltrationTime());
    RandomVariable actualExpectationResult = actualValue.expectation();
    assertSame(actualValue, actualExpectationResult);
  }
}
