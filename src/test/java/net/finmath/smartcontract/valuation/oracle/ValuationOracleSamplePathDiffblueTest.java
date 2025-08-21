package net.finmath.smartcontract.valuation.oracle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import javax.money.MonetaryAmount;
import javax.money.NumberValue;
import net.finmath.montecarlo.RandomVariableFromDoubleArray;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.spi.DefaultNumberValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ValuationOracleSamplePathDiffblueTest {
  /**
   * Test {@link ValuationOracleSamplePath#getValue(LocalDateTime, LocalDateTime)}.
   *
   * <ul>
   *   <li>Then return {@link BigDecimal#BigDecimal(String)} with {@code 10.0}.
   * </ul>
   *
   * <p>Method under test: {@link ValuationOracleSamplePath#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName(
      "Test getValue(LocalDateTime, LocalDateTime); then return BigDecimal(String) with '10.0'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BigDecimal ValuationOracleSamplePath.getValue(LocalDateTime, LocalDateTime)"})
  void testGetValue_thenReturnBigDecimalWith100() {
    // Arrange
    StochasticValuationOracle stochasticValuationOracle = mock(StochasticValuationOracle.class);
    when(stochasticValuationOracle.getValue(
            Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any()))
        .thenReturn(new RandomVariableFromDoubleArray(10.0d));
    ValuationOracleSamplePath valuationOracleSamplePath =
        new ValuationOracleSamplePath(stochasticValuationOracle, 1);
    LocalDateTime evaluationTime = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    BigDecimal actualValue =
        valuationOracleSamplePath.getValue(evaluationTime, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    verify(stochasticValuationOracle).getValue(isA(LocalDateTime.class), isA(LocalDateTime.class));
    assertEquals(new BigDecimal("10.0"), actualValue);
  }

  /**
   * Test {@link ValuationOracleSamplePath#getValues(LocalDateTime, LocalDateTime)}.
   *
   * <ul>
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link ValuationOracleSamplePath#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValues(LocalDateTime, LocalDateTime); then return size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ValuationOracleSamplePath.getValues(LocalDateTime, LocalDateTime)"})
  void testGetValues_thenReturnSizeIsOne() {
    // Arrange
    StochasticValuationOracle stochasticValuationOracle = mock(StochasticValuationOracle.class);
    when(stochasticValuationOracle.getValue(
            Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any()))
        .thenReturn(new RandomVariableFromDoubleArray(10.0d));
    ValuationOracleSamplePath valuationOracleSamplePath =
        new ValuationOracleSamplePath(stochasticValuationOracle, 1);
    LocalDateTime evaluationTime = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    Map<String, BigDecimal> actualValues =
        valuationOracleSamplePath.getValues(
            evaluationTime, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    verify(stochasticValuationOracle).getValue(isA(LocalDateTime.class), isA(LocalDateTime.class));
    assertEquals(1, actualValues.size());
    BigDecimal expectedGetResult = new BigDecimal("10.0");
    assertEquals(expectedGetResult, actualValues.get("value"));
  }

  /**
   * Test {@link ValuationOracleSamplePath#getAmount(LocalDateTime, LocalDateTime)}.
   *
   * <ul>
   *   <li>Then return Number Scale is minus one.
   * </ul>
   *
   * <p>Method under test: {@link ValuationOracleSamplePath#getAmount(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName(
      "Test getAmount(LocalDateTime, LocalDateTime); then return Number Scale is minus one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MonetaryAmount ValuationOracleSamplePath.getAmount(LocalDateTime, LocalDateTime)"
  })
  void testGetAmount_thenReturnNumberScaleIsMinusOne() {
    // Arrange
    StochasticValuationOracle stochasticValuationOracle = mock(StochasticValuationOracle.class);
    when(stochasticValuationOracle.getValue(
            Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any()))
        .thenReturn(new RandomVariableFromDoubleArray(10.0d));
    ValuationOracleSamplePath valuationOracleSamplePath =
        new ValuationOracleSamplePath(stochasticValuationOracle, 1);
    LocalDateTime evaluationTime = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    MonetaryAmount actualAmount =
        valuationOracleSamplePath.getAmount(
            evaluationTime, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    verify(stochasticValuationOracle).getValue(isA(LocalDateTime.class), isA(LocalDateTime.class));
    assertTrue(actualAmount instanceof Money);
    NumberValue number = actualAmount.getNumber();
    assertTrue(number instanceof DefaultNumberValue);
    assertEquals(-1, number.getScale());
    assertEquals(0L, number.getAmountFractionNumerator());
    assertEquals(1L, number.getAmountFractionDenominator());
    BigDecimal expectedNumberStripped = new BigDecimal("1E+1");
    assertEquals(expectedNumberStripped, ((Money) actualAmount).getNumberStripped());
  }

  /**
   * Test {@link ValuationOracleSamplePath#getAmount(LocalDateTime, LocalDateTime)}.
   *
   * <ul>
   *   <li>Then return Number Scale is one.
   * </ul>
   *
   * <p>Method under test: {@link ValuationOracleSamplePath#getAmount(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getAmount(LocalDateTime, LocalDateTime); then return Number Scale is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MonetaryAmount ValuationOracleSamplePath.getAmount(LocalDateTime, LocalDateTime)"
  })
  void testGetAmount_thenReturnNumberScaleIsOne() {
    // Arrange
    StochasticValuationOracle stochasticValuationOracle = mock(StochasticValuationOracle.class);
    when(stochasticValuationOracle.getValue(
            Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any()))
        .thenReturn(new RandomVariableFromDoubleArray(0.5d));
    ValuationOracleSamplePath valuationOracleSamplePath =
        new ValuationOracleSamplePath(stochasticValuationOracle, 1);
    LocalDateTime evaluationTime = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    MonetaryAmount actualAmount =
        valuationOracleSamplePath.getAmount(
            evaluationTime, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    verify(stochasticValuationOracle).getValue(isA(LocalDateTime.class), isA(LocalDateTime.class));
    assertTrue(actualAmount instanceof Money);
    NumberValue number = actualAmount.getNumber();
    assertTrue(number instanceof DefaultNumberValue);
    assertEquals(1, number.getScale());
    assertEquals(10L, number.getAmountFractionDenominator());
    assertEquals(5L, number.getAmountFractionNumerator());
    BigDecimal expectedNumberStripped = new BigDecimal("0.5");
    assertEquals(expectedNumberStripped, ((Money) actualAmount).getNumberStripped());
  }
}
