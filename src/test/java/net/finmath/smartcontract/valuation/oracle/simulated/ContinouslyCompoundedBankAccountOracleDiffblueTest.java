package net.finmath.smartcontract.valuation.oracle.simulated;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ContinouslyCompoundedBankAccountOracle.class})
@ExtendWith(SpringExtension.class)
class ContinouslyCompoundedBankAccountOracleDiffblueTest {
  @Autowired private ContinouslyCompoundedBankAccountOracle continouslyCompoundedBankAccountOracle;

  /**
   * Test {@link ContinouslyCompoundedBankAccountOracle#ContinouslyCompoundedBankAccountOracle()}.
   *
   * <p>Method under test: {@link
   * ContinouslyCompoundedBankAccountOracle#ContinouslyCompoundedBankAccountOracle()}
   */
  @Test
  @DisplayName("Test new ContinouslyCompoundedBankAccountOracle()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ContinouslyCompoundedBankAccountOracle.<init>()"})
  void testNewContinouslyCompoundedBankAccountOracle() {
    // Arrange and Act
    ContinouslyCompoundedBankAccountOracle actualContinouslyCompoundedBankAccountOracle =
        new ContinouslyCompoundedBankAccountOracle();
    LocalDateTime evaluationTime = LocalDate.of(1970, 1, 1).atStartOfDay();
    RandomVariable actualValue =
        actualContinouslyCompoundedBankAccountOracle.getValue(
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
    assertTrue(actualValue.isDeterministic());
    assertEquals(Double.NEGATIVE_INFINITY, actualValue.getFiltrationTime());
    assertSame(actualValue, actualValue.expectation());
  }

  /**
   * Test {@link
   * ContinouslyCompoundedBankAccountOracle#ContinouslyCompoundedBankAccountOracle(LocalDateTime)}.
   *
   * <p>Method under test: {@link
   * ContinouslyCompoundedBankAccountOracle#ContinouslyCompoundedBankAccountOracle(LocalDateTime)}
   */
  @Test
  @DisplayName("Test new ContinouslyCompoundedBankAccountOracle(LocalDateTime)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ContinouslyCompoundedBankAccountOracle.<init>(LocalDateTime)"})
  void testNewContinouslyCompoundedBankAccountOracle2() {
    // Arrange and Act
    ContinouslyCompoundedBankAccountOracle actualContinouslyCompoundedBankAccountOracle =
        new ContinouslyCompoundedBankAccountOracle(LocalDate.of(1970, 1, 1).atStartOfDay());
    LocalDateTime evaluationTime = LocalDate.of(1970, 1, 1).atStartOfDay();
    RandomVariable actualValue =
        actualContinouslyCompoundedBankAccountOracle.getValue(
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
    assertEquals(1.0d, actualValue.getAverage());
    assertEquals(1.0d, actualValue.getMax());
    assertEquals(1.0d, actualValue.getMin());
    assertTrue(actualValue.isDeterministic());
    assertEquals(Double.NEGATIVE_INFINITY, actualValue.getFiltrationTime());
    assertSame(actualValue, actualValue.expectation());
  }

  /**
   * Test {@link
   * ContinouslyCompoundedBankAccountOracle#ContinouslyCompoundedBankAccountOracle(LocalDateTime,
   * double, double, double)}.
   *
   * <p>Method under test: {@link
   * ContinouslyCompoundedBankAccountOracle#ContinouslyCompoundedBankAccountOracle(LocalDateTime,
   * double, double, double)}
   */
  @Test
  @DisplayName(
      "Test new ContinouslyCompoundedBankAccountOracle(LocalDateTime, double, double, double)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ContinouslyCompoundedBankAccountOracle.<init>(LocalDateTime, double, double, double)"
  })
  void testNewContinouslyCompoundedBankAccountOracle3() {
    // Arrange and Act
    ContinouslyCompoundedBankAccountOracle actualContinouslyCompoundedBankAccountOracle =
        new ContinouslyCompoundedBankAccountOracle(
            LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d, 10.0d, 10.0d);
    LocalDateTime evaluationTime = LocalDate.of(1970, 1, 1).atStartOfDay();
    RandomVariable actualValue =
        actualContinouslyCompoundedBankAccountOracle.getValue(
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
    assertSame(actualValue, actualValue.expectation());
  }

  /**
   * Test {@link
   * ContinouslyCompoundedBankAccountOracle#ContinouslyCompoundedBankAccountOracle(TimeDiscretization,
   * LocalDateTime, double, double)}.
   *
   * <p>Method under test: {@link
   * ContinouslyCompoundedBankAccountOracle#ContinouslyCompoundedBankAccountOracle(TimeDiscretization,
   * LocalDateTime, double, double)}
   */
  @Test
  @DisplayName(
      "Test new ContinouslyCompoundedBankAccountOracle(TimeDiscretization, LocalDateTime, double, double)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ContinouslyCompoundedBankAccountOracle.<init>(TimeDiscretization, LocalDateTime, double, double)"
  })
  void testNewContinouslyCompoundedBankAccountOracle4() {
    // Arrange
    TenorFromArray timeDiscretization = new TenorFromArray(10.0d, 10, 0.5d);

    // Act
    ContinouslyCompoundedBankAccountOracle actualContinouslyCompoundedBankAccountOracle =
        new ContinouslyCompoundedBankAccountOracle(
            timeDiscretization, LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d, 10.0d);
    LocalDateTime evaluationTime = LocalDate.of(1970, 1, 1).atStartOfDay();
    RandomVariable actualValue =
        actualContinouslyCompoundedBankAccountOracle.getValue(
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
    assertEquals(10.0d, actualValue.getAverage());
    assertEquals(10.0d, actualValue.getMax());
    assertEquals(10.0d, actualValue.getMin());
    assertTrue(actualValue.isDeterministic());
    assertEquals(Double.NEGATIVE_INFINITY, actualValue.getFiltrationTime());
    assertSame(actualValue, actualValue.expectation());
  }

  /**
   * Test {@link
   * ContinouslyCompoundedBankAccountOracle#ContinouslyCompoundedBankAccountOracle(LocalDateTime,
   * double, double, double)}.
   *
   * <ul>
   *   <li>When {@code 3.1536E7}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ContinouslyCompoundedBankAccountOracle#ContinouslyCompoundedBankAccountOracle(LocalDateTime,
   * double, double, double)}
   */
  @Test
  @DisplayName(
      "Test new ContinouslyCompoundedBankAccountOracle(LocalDateTime, double, double, double); when '3.1536E7'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ContinouslyCompoundedBankAccountOracle.<init>(LocalDateTime, double, double, double)"
  })
  void testNewContinouslyCompoundedBankAccountOracle_when31536e7() {
    // Arrange and Act
    ContinouslyCompoundedBankAccountOracle actualContinouslyCompoundedBankAccountOracle =
        new ContinouslyCompoundedBankAccountOracle(
            LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d, 3.1536E7d, 10.0d);
    LocalDateTime evaluationTime = LocalDate.of(1970, 1, 1).atStartOfDay();
    RandomVariable actualValue =
        actualContinouslyCompoundedBankAccountOracle.getValue(
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
    assertSame(actualValue, actualValue.expectation());
  }
}
