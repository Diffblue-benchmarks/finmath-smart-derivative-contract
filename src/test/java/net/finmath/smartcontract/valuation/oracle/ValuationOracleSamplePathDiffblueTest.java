package net.finmath.smartcontract.valuation.oracle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import javax.money.MonetaryAmount;
import javax.money.MonetaryContext;
import net.finmath.montecarlo.RandomVariableFromDoubleArray;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.spi.DefaultNumberValue;
import org.javamoney.moneta.spi.JDKCurrencyAdapter;
import org.javamoney.moneta.spi.MoneyAmountFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ValuationOracleSamplePathDiffblueTest {
  /**
   * Test {@link ValuationOracleSamplePath#getValue(LocalDateTime, LocalDateTime)}.
   * <ul>
   *   <li>Then return {@link BigDecimal#BigDecimal(String)} with {@code 10.0}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValuationOracleSamplePath#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValue(LocalDateTime, LocalDateTime); then return BigDecimal(String) with '10.0'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BigDecimal ValuationOracleSamplePath.getValue(LocalDateTime, LocalDateTime)"})
  void testGetValue_thenReturnBigDecimalWith100() {
    // Arrange
    StochasticValuationOracle stochasticValuationOracle = mock(StochasticValuationOracle.class);
    when(stochasticValuationOracle.getValue(Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any()))
        .thenReturn(new RandomVariableFromDoubleArray(10.0d));
    ValuationOracleSamplePath valuationOracleSamplePath = new ValuationOracleSamplePath(stochasticValuationOracle, 1);
    LocalDateTime evaluationTime = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    BigDecimal actualValue = valuationOracleSamplePath.getValue(evaluationTime,
        LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    verify(stochasticValuationOracle).getValue(isA(LocalDateTime.class), isA(LocalDateTime.class));
    assertEquals(new BigDecimal("10.0"), actualValue);
  }

  /**
   * Test {@link ValuationOracleSamplePath#getValues(LocalDateTime, LocalDateTime)}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValuationOracleSamplePath#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValues(LocalDateTime, LocalDateTime); then return size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map ValuationOracleSamplePath.getValues(LocalDateTime, LocalDateTime)"})
  void testGetValues_thenReturnSizeIsOne() {
    // Arrange
    StochasticValuationOracle stochasticValuationOracle = mock(StochasticValuationOracle.class);
    when(stochasticValuationOracle.getValue(Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any()))
        .thenReturn(new RandomVariableFromDoubleArray(10.0d));
    ValuationOracleSamplePath valuationOracleSamplePath = new ValuationOracleSamplePath(stochasticValuationOracle, 1);
    LocalDateTime evaluationTime = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    Map<String, BigDecimal> actualValues = valuationOracleSamplePath.getValues(evaluationTime,
        LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    verify(stochasticValuationOracle).getValue(isA(LocalDateTime.class), isA(LocalDateTime.class));
    assertEquals(1, actualValues.size());
    BigDecimal expectedGetResult = new BigDecimal("10.0");
    assertEquals(expectedGetResult, actualValues.get("value"));
  }

  /**
   * Test {@link ValuationOracleSamplePath#getAmount(LocalDateTime, LocalDateTime)}.
   * <ul>
   *   <li>Then return {@link Money}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValuationOracleSamplePath#getAmount(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getAmount(LocalDateTime, LocalDateTime); then return Money")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"MonetaryAmount ValuationOracleSamplePath.getAmount(LocalDateTime, LocalDateTime)"})
  void testGetAmount_thenReturnMoney() {
    // Arrange
    StochasticValuationOracle stochasticValuationOracle = mock(StochasticValuationOracle.class);
    when(stochasticValuationOracle.getValue(Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any()))
        .thenReturn(new RandomVariableFromDoubleArray(10.0d));
    ValuationOracleSamplePath valuationOracleSamplePath = new ValuationOracleSamplePath(stochasticValuationOracle, 1);
    LocalDateTime evaluationTime = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    MonetaryAmount actualAmount = valuationOracleSamplePath.getAmount(evaluationTime,
        LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    verify(stochasticValuationOracle).getValue(isA(LocalDateTime.class), isA(LocalDateTime.class));
    assertTrue(actualAmount instanceof Money);
    assertTrue(actualAmount.getNumber() instanceof DefaultNumberValue);
    assertTrue(actualAmount.getCurrency() instanceof JDKCurrencyAdapter);
    assertTrue(actualAmount.getFactory() instanceof MoneyAmountFactory);
    assertEquals(1, actualAmount.signum());
    assertFalse(actualAmount.isNegative());
    assertFalse(actualAmount.isNegativeOrZero());
    assertFalse(actualAmount.isZero());
    assertTrue(actualAmount.isPositive());
    assertTrue(actualAmount.isPositiveOrZero());
    BigDecimal expectedNumberStripped = new BigDecimal("1E+1");
    assertEquals(expectedNumberStripped, ((Money) actualAmount).getNumberStripped());
    MonetaryContext expectedContext = ((Money) actualAmount).DEFAULT_MONETARY_CONTEXT;
    assertSame(expectedContext, actualAmount.getContext());
  }
}
