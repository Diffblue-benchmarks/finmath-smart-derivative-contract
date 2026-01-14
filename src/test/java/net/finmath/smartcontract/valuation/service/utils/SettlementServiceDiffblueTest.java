package net.finmath.smartcontract.valuation.service.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import net.finmath.smartcontract.model.ExceptionId;
import net.finmath.smartcontract.model.InitialSettlementRequest;
import net.finmath.smartcontract.model.RegularSettlementRequest;
import net.finmath.smartcontract.model.SDCException;
import net.finmath.smartcontract.settlement.SettlementGeneratorDiffblueBase;
import net.finmath.smartcontract.valuation.implementation.MarginCalculatorDiffblueBase;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationParserDataItemsDiffblueBase;
import net.finmath.smartcontract.valuation.marketdata.generators.MarketDataGeneratorLauncherDiffblueBase;
import net.finmath.smartcontract.valuation.service.config.RefinitivConfig;
import net.finmath.smartcontract.valuation.service.config.ValuationConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(
    classes = {SettlementService.class, RefinitivConfig.class, ValuationConfig.class})
@ExtendWith(SpringExtension.class)
class SettlementServiceDiffblueTest {
  @Autowired private RefinitivConfig refinitivConfig;

  @Autowired private SettlementService settlementService;

  @Autowired private ValuationConfig valuationConfig;

  /**
   * Test {@link SettlementService#generateRegularSettlementResult(RegularSettlementRequest)}.
   *
   * <p>Method under test: {@link
   * SettlementService#generateRegularSettlementResult(RegularSettlementRequest)}
   */
  @Test
  @DisplayName("Test generateRegularSettlementResult(RegularSettlementRequest)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "net.finmath.smartcontract.model.RegularSettlementResult SettlementService.generateRegularSettlementResult(RegularSettlementRequest)"
  })
  void testGenerateRegularSettlementResult() {
    // Arrange
    RegularSettlementRequest regularSettlementRequest = mock(RegularSettlementRequest.class);
    when(regularSettlementRequest.getNewProvidedMarketData())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());
    when(regularSettlementRequest.getTradeData())
        .thenReturn(MarginCalculatorDiffblueBase.createValidSmartDerivativeContractXml());

    // Act and Assert
    assertThrows(
        SDCException.class,
        () -> settlementService.generateRegularSettlementResult(regularSettlementRequest));
    verify(regularSettlementRequest, atLeast(1)).getNewProvidedMarketData();
    verify(regularSettlementRequest).getTradeData();
  }

  /**
   * Test {@link SettlementService#generateRegularSettlementResult(RegularSettlementRequest)}.
   *
   * <p>Method under test: {@link
   * SettlementService#generateRegularSettlementResult(RegularSettlementRequest)}
   */
  @Test
  @DisplayName("Test generateRegularSettlementResult(RegularSettlementRequest)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "net.finmath.smartcontract.model.RegularSettlementResult SettlementService.generateRegularSettlementResult(RegularSettlementRequest)"
  })
  void testGenerateRegularSettlementResult2() {
    // Arrange
    RegularSettlementRequest regularSettlementRequest = mock(RegularSettlementRequest.class);
    when(regularSettlementRequest.getNewProvidedMarketData())
        .thenThrow(
            new SDCException(
                ExceptionId.SDC_AUTH_ERROR,
                MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml()));
    when(regularSettlementRequest.getTradeData())
        .thenReturn(MarginCalculatorDiffblueBase.createValidSmartDerivativeContractXml());

    // Act and Assert
    assertThrows(
        SDCException.class,
        () -> settlementService.generateRegularSettlementResult(regularSettlementRequest));
    verify(regularSettlementRequest).getNewProvidedMarketData();
    verify(regularSettlementRequest).getTradeData();
  }

  /**
   * Test {@link SettlementService#generateRegularSettlementResult(RegularSettlementRequest)}.
   *
   * <ul>
   *   <li>Then calls {@link RegularSettlementRequest#getSettlementLast()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SettlementService#generateRegularSettlementResult(RegularSettlementRequest)}
   */
  @Test
  @DisplayName(
      "Test generateRegularSettlementResult(RegularSettlementRequest); then calls getSettlementLast()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "net.finmath.smartcontract.model.RegularSettlementResult SettlementService.generateRegularSettlementResult(RegularSettlementRequest)"
  })
  void testGenerateRegularSettlementResult_thenCallsGetSettlementLast() {
    // Arrange
    RegularSettlementRequest regularSettlementRequest = mock(RegularSettlementRequest.class);
    when(regularSettlementRequest.getSettlementLast())
        .thenThrow(
            new SDCException(
                ExceptionId.SDC_AUTH_ERROR,
                MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml()));
    when(regularSettlementRequest.getNewProvidedMarketData())
        .thenReturn(CalibrationParserDataItemsDiffblueBase.createValidMarketDataXml());
    when(regularSettlementRequest.getTradeData())
        .thenReturn(MarginCalculatorDiffblueBase.createValidSmartDerivativeContractXml());

    // Act and Assert
    assertThrows(
        SDCException.class,
        () -> settlementService.generateRegularSettlementResult(regularSettlementRequest));
    verify(regularSettlementRequest, atLeast(1)).getNewProvidedMarketData();
    verify(regularSettlementRequest).getSettlementLast();
    verify(regularSettlementRequest, atLeast(1)).getTradeData();
  }

  /**
   * Test {@link SettlementService#generateInitialSettlementResult(InitialSettlementRequest)}.
   *
   * <p>Method under test: {@link
   * SettlementService#generateInitialSettlementResult(InitialSettlementRequest)}
   */
  @Test
  @DisplayName("Test generateInitialSettlementResult(InitialSettlementRequest)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "net.finmath.smartcontract.model.InitialSettlementResult SettlementService.generateInitialSettlementResult(InitialSettlementRequest)"
  })
  void testGenerateInitialSettlementResult() {
    // Arrange
    InitialSettlementRequest initialSettlementRequest = mock(InitialSettlementRequest.class);
    when(initialSettlementRequest.getNewProvidedMarketData())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());
    when(initialSettlementRequest.getTradeData())
        .thenReturn(MarginCalculatorDiffblueBase.createValidSmartDerivativeContractXml());

    // Act and Assert
    assertThrows(
        SDCException.class,
        () -> settlementService.generateInitialSettlementResult(initialSettlementRequest));
    verify(initialSettlementRequest, atLeast(1)).getNewProvidedMarketData();
    verify(initialSettlementRequest).getTradeData();
  }

  /**
   * Test {@link SettlementService#generateInitialSettlementResult(InitialSettlementRequest)}.
   *
   * <p>Method under test: {@link
   * SettlementService#generateInitialSettlementResult(InitialSettlementRequest)}
   */
  @Test
  @DisplayName("Test generateInitialSettlementResult(InitialSettlementRequest)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "net.finmath.smartcontract.model.InitialSettlementResult SettlementService.generateInitialSettlementResult(InitialSettlementRequest)"
  })
  void testGenerateInitialSettlementResult2() {
    // Arrange
    InitialSettlementRequest initialSettlementRequest = mock(InitialSettlementRequest.class);
    when(initialSettlementRequest.getNewProvidedMarketData())
        .thenThrow(
            new SDCException(
                ExceptionId.SDC_AUTH_ERROR,
                MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml()));
    when(initialSettlementRequest.getTradeData())
        .thenReturn(MarginCalculatorDiffblueBase.createValidSmartDerivativeContractXml());

    // Act and Assert
    assertThrows(
        SDCException.class,
        () -> settlementService.generateInitialSettlementResult(initialSettlementRequest));
    verify(initialSettlementRequest).getNewProvidedMarketData();
    verify(initialSettlementRequest).getTradeData();
  }

  /**
   * Test {@link SettlementService#generateInitialSettlementResult(InitialSettlementRequest)}.
   *
   * <ul>
   *   <li>Given createValidMarketDataXml.
   * </ul>
   *
   * <p>Method under test: {@link
   * SettlementService#generateInitialSettlementResult(InitialSettlementRequest)}
   */
  @Test
  @DisplayName(
      "Test generateInitialSettlementResult(InitialSettlementRequest); given createValidMarketDataXml")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "net.finmath.smartcontract.model.InitialSettlementResult SettlementService.generateInitialSettlementResult(InitialSettlementRequest)"
  })
  void testGenerateInitialSettlementResult_givenCreateValidMarketDataXml() {
    // Arrange
    InitialSettlementRequest initialSettlementRequest = mock(InitialSettlementRequest.class);
    when(initialSettlementRequest.getNewProvidedMarketData())
        .thenReturn(CalibrationParserDataItemsDiffblueBase.createValidMarketDataXml());
    when(initialSettlementRequest.getTradeData())
        .thenReturn(MarginCalculatorDiffblueBase.createValidSmartDerivativeContractXml());

    // Act and Assert
    assertThrows(
        SDCException.class,
        () -> settlementService.generateInitialSettlementResult(initialSettlementRequest));
    verify(initialSettlementRequest, atLeast(1)).getNewProvidedMarketData();
    verify(initialSettlementRequest, atLeast(1)).getTradeData();
  }

  /**
   * Test {@link SettlementService#generateInitialSettlementResult(InitialSettlementRequest)}.
   *
   * <ul>
   *   <li>Given createValidMarketDataXml.
   * </ul>
   *
   * <p>Method under test: {@link
   * SettlementService#generateInitialSettlementResult(InitialSettlementRequest)}
   */
  @Test
  @DisplayName(
      "Test generateInitialSettlementResult(InitialSettlementRequest); given createValidMarketDataXml")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "net.finmath.smartcontract.model.InitialSettlementResult SettlementService.generateInitialSettlementResult(InitialSettlementRequest)"
  })
  void testGenerateInitialSettlementResult_givenCreateValidMarketDataXml2() {
    // Arrange
    InitialSettlementRequest initialSettlementRequest = mock(InitialSettlementRequest.class);
    when(initialSettlementRequest.getNewProvidedMarketData())
        .thenReturn(SettlementGeneratorDiffblueBase.createValidMarketDataXml());
    when(initialSettlementRequest.getTradeData())
        .thenReturn(MarginCalculatorDiffblueBase.createValidSmartDerivativeContractXml());

    // Act and Assert
    assertThrows(
        SDCException.class,
        () -> settlementService.generateInitialSettlementResult(initialSettlementRequest));
    verify(initialSettlementRequest, atLeast(1)).getNewProvidedMarketData();
    verify(initialSettlementRequest, atLeast(1)).getTradeData();
  }
}
