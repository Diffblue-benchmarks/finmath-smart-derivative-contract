package net.finmath.smartcontract.valuation.oracle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
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
   * Test {@link SmartDerivativeContractSettlementOracle#getMargin(LocalDateTime, LocalDateTime)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} All is {@link HashMap#HashMap()}.
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link SmartDerivativeContractSettlementOracle#getMargin(LocalDateTime,
   * LocalDateTime)}
   */
  @Test
  @DisplayName(
      "Test getMargin(LocalDateTime, LocalDateTime); given HashMap() All is HashMap(); then return size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map SmartDerivativeContractSettlementOracle.getMargin(LocalDateTime, LocalDateTime)"
  })
  void testGetMargin_givenHashMapAllIsHashMap_thenReturnSizeIsOne() {
    // Arrange
    HashMap<String, BigDecimal> stringBigDecimalMap = new HashMap<>();
    stringBigDecimalMap.putAll(new HashMap<>());
    stringBigDecimalMap.put("\"TestKeyForHashMapPutMethod\"", BigDecimal.valueOf(42L));
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
    assertEquals(new BigDecimal("0"), actualMargin.get("\"TestKeyForHashMapPutMethod\""));
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
