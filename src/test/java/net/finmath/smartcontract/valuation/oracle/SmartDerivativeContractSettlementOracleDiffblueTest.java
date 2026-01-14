package net.finmath.smartcontract.valuation.oracle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SmartDerivativeContractSettlementOracle.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class SmartDerivativeContractSettlementOracleDiffblueTest {
  @Autowired
  private SmartDerivativeContractSettlementOracle smartDerivativeContractSettlementOracle;

  @MockBean private ValuationOracle valuationOracle;

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link
   *       SmartDerivativeContractSettlementOracle#SmartDerivativeContractSettlementOracle(ValuationOracle)}
   *   <li>{@link SmartDerivativeContractSettlementOracle#getDerivativeValuationOracle()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SmartDerivativeContractSettlementOracle.<init>(ValuationOracle)",
    "ValuationOracle SmartDerivativeContractSettlementOracle.getDerivativeValuationOracle()"
  })
  void testGettersAndSetters() {
    // Arrange
    ValuationOracleSamplePath derivativeValuationOracle =
        new ValuationOracleSamplePath(mock(StochasticValuationOracle.class), 1);

    // Act
    ValuationOracle actualDerivativeValuationOracle =
        new SmartDerivativeContractSettlementOracle(derivativeValuationOracle)
            .getDerivativeValuationOracle();

    // Assert
    assertTrue(actualDerivativeValuationOracle instanceof ValuationOracleSamplePath);
    assertSame(derivativeValuationOracle, actualDerivativeValuationOracle);
  }

  /**
   * Test {@link SmartDerivativeContractSettlementOracle#getMargin(LocalDateTime, LocalDateTime)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 42} is {@link BigDecimal#BigDecimal(String)} with
   *       {@code 2.3}.
   *   <li>Then return size is two.
   * </ul>
   *
   * <p>Method under test: {@link SmartDerivativeContractSettlementOracle#getMargin(LocalDateTime,
   * LocalDateTime)}
   */
  @Test
  @DisplayName(
      "Test getMargin(LocalDateTime, LocalDateTime); given HashMap() '42' is BigDecimal(String) with '2.3'; then return size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map SmartDerivativeContractSettlementOracle.getMargin(LocalDateTime, LocalDateTime)"
  })
  void testGetMargin_givenHashMap42IsBigDecimalWith23_thenReturnSizeIsTwo() {
    // Arrange
    HashMap<String, BigDecimal> stringBigDecimalMap = new HashMap<>();
    stringBigDecimalMap.put("42", new BigDecimal("2.3"));
    stringBigDecimalMap.put("Key", new BigDecimal("2.3"));
    when(valuationOracle.getValues(Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any()))
        .thenReturn(stringBigDecimalMap);

    // Act
    Map<String, BigDecimal> actualMargin =
        smartDerivativeContractSettlementOracle.getMargin(
            LocalDate.of(1970, 1, 1).atStartOfDay(), LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    verify(valuationOracle, atLeast(1))
        .getValues(isA(LocalDateTime.class), isA(LocalDateTime.class));
    assertEquals(2, actualMargin.size());
    BigDecimal getResult = actualMargin.get("42");
    assertEquals(new BigDecimal("0.0"), getResult);
    assertSame(getResult, actualMargin.get("Key"));
  }

  /**
   * Test {@link SmartDerivativeContractSettlementOracle#getMargin(LocalDateTime, LocalDateTime)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code Key} is {@link BigDecimal#BigDecimal(String)} with
   *       {@code 2.3}.
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link SmartDerivativeContractSettlementOracle#getMargin(LocalDateTime,
   * LocalDateTime)}
   */
  @Test
  @DisplayName(
      "Test getMargin(LocalDateTime, LocalDateTime); given HashMap() 'Key' is BigDecimal(String) with '2.3'; then return size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map SmartDerivativeContractSettlementOracle.getMargin(LocalDateTime, LocalDateTime)"
  })
  void testGetMargin_givenHashMapKeyIsBigDecimalWith23_thenReturnSizeIsOne() {
    // Arrange
    HashMap<String, BigDecimal> stringBigDecimalMap = new HashMap<>();
    stringBigDecimalMap.put("Key", new BigDecimal("2.3"));
    when(valuationOracle.getValues(Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any()))
        .thenReturn(stringBigDecimalMap);

    // Act
    Map<String, BigDecimal> actualMargin =
        smartDerivativeContractSettlementOracle.getMargin(
            LocalDate.of(1970, 1, 1).atStartOfDay(), LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    verify(valuationOracle, atLeast(1))
        .getValues(isA(LocalDateTime.class), isA(LocalDateTime.class));
    assertEquals(1, actualMargin.size());
    assertEquals(new BigDecimal("0.0"), actualMargin.get("Key"));
  }

  /**
   * Test {@link SmartDerivativeContractSettlementOracle#getMargin(LocalDateTime, LocalDateTime)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link SmartDerivativeContractSettlementOracle#getMargin(LocalDateTime,
   * LocalDateTime)}
   */
  @Test
  @DisplayName("Test getMargin(LocalDateTime, LocalDateTime); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map SmartDerivativeContractSettlementOracle.getMargin(LocalDateTime, LocalDateTime)"
  })
  void testGetMargin_thenReturnEmpty() {
    // Arrange
    when(valuationOracle.getValues(Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any()))
        .thenReturn(new HashMap<>());

    // Act
    Map<String, BigDecimal> actualMargin =
        smartDerivativeContractSettlementOracle.getMargin(
            LocalDate.of(1970, 1, 1).atStartOfDay(), LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    verify(valuationOracle, atLeast(1))
        .getValues(isA(LocalDateTime.class), isA(LocalDateTime.class));
    assertTrue(actualMargin.isEmpty());
  }
}
