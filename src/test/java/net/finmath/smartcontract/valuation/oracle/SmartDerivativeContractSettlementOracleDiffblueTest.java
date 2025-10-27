package net.finmath.smartcontract.valuation.oracle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SmartDerivativeContractSettlementOracle.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class SmartDerivativeContractSettlementOracleDiffblueTest {
  @Autowired
  private SmartDerivativeContractSettlementOracle smartDerivativeContractSettlementOracle;

  @MockBean
  private ValuationOracle valuationOracle;

  /**
   * Method under test:
   * {@link SmartDerivativeContractSettlementOracle#getMargin(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetMargin() {
    // Arrange
    when(valuationOracle.getValues(Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any()))
        .thenReturn(new HashMap<>());
    LocalDateTime marginPeriodStart = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    Map<String, BigDecimal> actualMargin = smartDerivativeContractSettlementOracle.getMargin(marginPeriodStart,
        LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    verify(valuationOracle, atLeast(1)).getValues(isA(LocalDateTime.class), isA(LocalDateTime.class));
    assertTrue(actualMargin.isEmpty());
  }

  /**
   * Method under test:
   * {@link SmartDerivativeContractSettlementOracle#getMargin(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetMargin2() {
    // Arrange
    HashMap<String, BigDecimal> stringBigDecimalMap = new HashMap<>();
    stringBigDecimalMap.put("foo", new BigDecimal("2.3"));
    when(valuationOracle.getValues(Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any()))
        .thenReturn(stringBigDecimalMap);
    LocalDateTime marginPeriodStart = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    Map<String, BigDecimal> actualMargin = smartDerivativeContractSettlementOracle.getMargin(marginPeriodStart,
        LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    verify(valuationOracle, atLeast(1)).getValues(isA(LocalDateTime.class), isA(LocalDateTime.class));
    assertEquals(1, actualMargin.size());
    BigDecimal expectedGetResult = new BigDecimal("0.0");
    assertEquals(expectedGetResult, actualMargin.get("foo"));
  }

  /**
   * Method under test:
   * {@link SmartDerivativeContractSettlementOracle#getMargin(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetMargin3() {
    // Arrange
    HashMap<String, BigDecimal> stringBigDecimalMap = new HashMap<>();
    stringBigDecimalMap.put("42", new BigDecimal("2.3"));
    stringBigDecimalMap.put("foo", new BigDecimal("2.3"));
    when(valuationOracle.getValues(Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any()))
        .thenReturn(stringBigDecimalMap);
    LocalDateTime marginPeriodStart = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    Map<String, BigDecimal> actualMargin = smartDerivativeContractSettlementOracle.getMargin(marginPeriodStart,
        LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    verify(valuationOracle, atLeast(1)).getValues(isA(LocalDateTime.class), isA(LocalDateTime.class));
    assertEquals(2, actualMargin.size());
    BigDecimal expectedGetResult = new BigDecimal("0.0");
    BigDecimal getResult = actualMargin.get("foo");
    assertEquals(expectedGetResult, getResult);
    assertSame(getResult, actualMargin.get("42"));
  }
}
