package net.finmath.smartcontract.valuation.service.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import net.finmath.smartcontract.model.ExceptionId;
import net.finmath.smartcontract.model.InitialSettlementRequest;
import net.finmath.smartcontract.model.RegularSettlementRequest;
import net.finmath.smartcontract.model.SDCException;
import net.finmath.smartcontract.valuation.service.config.ValuationConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SettlementServiceDiffblueTest {
  @InjectMocks private SettlementService settlementService;

  @Mock private ValuationConfig valuationConfig;

  /**
   * Test {@link SettlementService#generateRegularSettlementResult(RegularSettlementRequest)}.
   *
   * <ul>
   *   <li>Then throw {@link SDCException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SettlementService#generateRegularSettlementResult(RegularSettlementRequest)}
   */
  @Test
  @DisplayName(
      "Test generateRegularSettlementResult(RegularSettlementRequest); then throw SDCException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "net.finmath.smartcontract.model.RegularSettlementResult SettlementService.generateRegularSettlementResult(RegularSettlementRequest)"
  })
  void testGenerateRegularSettlementResult_thenThrowSDCException() {
    // Arrange
    when(valuationConfig.isLiveMarketData())
        .thenThrow(
            new SDCException(
                ExceptionId.SDC_AUTH_ERROR,
                "\"Invalid smart contract execution: The contract failed to execute due to insufficient funds in the"
                    + " account.\""));

    // Act and Assert
    assertThrows(
        SDCException.class,
        () -> settlementService.generateRegularSettlementResult(new RegularSettlementRequest()));
    verify(valuationConfig).isLiveMarketData();
  }

  /**
   * Test {@link SettlementService#generateInitialSettlementResult(InitialSettlementRequest)}.
   *
   * <ul>
   *   <li>Then throw {@link SDCException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SettlementService#generateInitialSettlementResult(InitialSettlementRequest)}
   */
  @Test
  @DisplayName(
      "Test generateInitialSettlementResult(InitialSettlementRequest); then throw SDCException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "net.finmath.smartcontract.model.InitialSettlementResult SettlementService.generateInitialSettlementResult(InitialSettlementRequest)"
  })
  void testGenerateInitialSettlementResult_thenThrowSDCException() {
    // Arrange
    when(valuationConfig.isLiveMarketData())
        .thenThrow(
            new SDCException(
                ExceptionId.SDC_AUTH_ERROR,
                "\"Invalid smart contract execution: The contract failed to execute due to insufficient funds in the"
                    + " account.\""));

    // Act and Assert
    assertThrows(
        SDCException.class,
        () -> settlementService.generateInitialSettlementResult(new InitialSettlementRequest()));
    verify(valuationConfig).isLiveMarketData();
  }
}
