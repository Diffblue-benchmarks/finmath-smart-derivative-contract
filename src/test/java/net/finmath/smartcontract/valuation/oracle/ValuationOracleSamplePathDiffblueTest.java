package net.finmath.smartcontract.valuation.oracle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import javax.money.CurrencyContext;
import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryAmountFactory;
import javax.money.MonetaryContext;
import javax.money.NumberValue;
import net.finmath.montecarlo.RandomVariableFromDoubleArray;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.spi.DefaultNumberValue;
import org.javamoney.moneta.spi.JDKCurrencyAdapter;
import org.javamoney.moneta.spi.MoneyAmountFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ValuationOracleSamplePathDiffblueTest {
  /**
   * Method under test:
   * {@link ValuationOracleSamplePath#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetValue() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test:
   * {@link ValuationOracleSamplePath#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetValues() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test:
   * {@link ValuationOracleSamplePath#getAmount(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetAmount() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
    NumberValue number = actualAmount.getNumber();
    assertTrue(number instanceof DefaultNumberValue);
    CurrencyUnit currency = actualAmount.getCurrency();
    assertTrue(currency instanceof JDKCurrencyAdapter);
    MonetaryAmountFactory<? extends MonetaryAmount> factory = actualAmount.getFactory();
    assertTrue(factory instanceof MoneyAmountFactory);
    assertEquals("EUR", currency.getCurrencyCode());
    CurrencyContext context = currency.getContext();
    assertEquals("java.util.Currency", context.getProviderName());
    MonetaryContext defaultMonetaryContext = factory.getDefaultMonetaryContext();
    assertNull(defaultMonetaryContext.getProviderName());
    MonetaryContext maximalMonetaryContext = factory.getMaximalMonetaryContext();
    assertNull(maximalMonetaryContext.getProviderName());
    assertNull(factory.getMaxNumber());
    assertNull(factory.getMinNumber());
    assertEquals(-1, maximalMonetaryContext.getMaxScale());
    assertEquals(-1, number.getScale());
    assertEquals(0, defaultMonetaryContext.getPrecision());
    assertEquals(0, maximalMonetaryContext.getPrecision());
    assertEquals(0L, number.getAmountFractionNumerator());
    assertEquals(1, actualAmount.signum());
    assertEquals(1, number.getPrecision());
    assertEquals(1L, number.getAmountFractionDenominator());
    assertEquals(2, currency.getDefaultFractionDigits());
    assertEquals(63, defaultMonetaryContext.getMaxScale());
    assertEquals(978, currency.getNumericCode());
    assertFalse(context.isEmpty());
    assertFalse(defaultMonetaryContext.isEmpty());
    assertFalse(maximalMonetaryContext.isEmpty());
    assertFalse(actualAmount.isNegative());
    assertFalse(actualAmount.isNegativeOrZero());
    assertFalse(actualAmount.isZero());
    assertTrue(actualAmount.isPositive());
    assertTrue(actualAmount.isPositiveOrZero());
    BigDecimal expectedNumberStripped = new BigDecimal("1E+1");
    assertEquals(expectedNumberStripped, ((Money) actualAmount).getNumberStripped());
    Class<BigDecimal> expectedNumberType = BigDecimal.class;
    assertEquals(expectedNumberType, number.getNumberType());
    MonetaryContext expectedContext = ((Money) actualAmount).DEFAULT_MONETARY_CONTEXT;
    assertSame(expectedContext, actualAmount.getContext());
  }
}
