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
import net.finmath.smartcontract.valuation.marketdata.generators.MarketDataGeneratorLauncherDiffblueBase;
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

  /**
   * Test {@link SmartDerivativeContractSettlementOracle#getMargin(LocalDateTime, LocalDateTime)}.
   *
   * <ul>
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link SmartDerivativeContractSettlementOracle#getMargin(LocalDateTime,
   * LocalDateTime)}
   */
  @Test
  @DisplayName("Test getMargin(LocalDateTime, LocalDateTime); then return size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map SmartDerivativeContractSettlementOracle.getMargin(LocalDateTime, LocalDateTime)"
  })
  void testGetMargin_thenReturnSizeIsOne() {
    // Arrange
    HashMap<String, BigDecimal> stringBigDecimalMap = new HashMap<>();
    String createMinimalSmartDerivativeContractXmlResult =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    stringBigDecimalMap.put(createMinimalSmartDerivativeContractXmlResult, new BigDecimal("2.3"));
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
    assertEquals(
        new BigDecimal("0.0"),
        actualMargin.get(
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><smartderivativecontract xmlns:xsi=\"http://www"
                + ".w3.org/2001/XMLSchema-instance\" xmlns=\"uri:sdc\" xsi:schemaLocation=\"uri:sdc smartderivativecontract"
                + ".xsd\"><dltTradeId>TEST-TRADE-001</dltTradeId><dltAddress>0xTEST</dltAddress><uniqueTradeIdentifier>UTI"
                + "-TEST-001</uniqueTradeIdentifier><settlementCurrency>EUR</settlementCurrency><tradeType>SDCPledgedBalance"
                + "</tradeType><valuation>  <artefact>    <groupId>net.finmath</groupId>    <artifactId>finmath-smart"
                + "-derivative-contract</artifactId>    <version>1.0.0</version>  </artefact></valuation><parties>  <party>"
                + "    <name>Party 1</name>    <id>party1</id>    <marginAccount><type>constant</type><value>10000.0<"
                + "/value></marginAccount>    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>   "
                + " <address>0x1234</address>  </party>  <party>    <name>Party 2</name>    <id>party2</id>   "
                + " <marginAccount><type>constant</type><value>10000.0</value></marginAccount>    <penaltyFee><type"
                + ">constant</type><value>100.0</value></penaltyFee>    <address>0x5678</address>  </party></parties>"
                + "<settlement>  <settlementTime><type>daily</type><value>17:00</value></settlementTime>  <marketdata> "
                + "   <provider>test</provider>    <marketdataitems>      <item>        <symbol>EUR-EONIA</symbol>     "
                + "   <curve>ESTR</curve>        <type>Fixing</type>        <tenor>1D</tenor>      </item>    </marketdataitems>"
                + "  </marketdata></settlement><trade>  <product>InterestRateDerivative</product>  <productType>Swap<"
                + "/productType>  <startDate>2024-01-01</startDate>  <endDate>2029-01-01</endDate>  <receiverPartyReference"
                + "><partyReference>party1</partyReference></receiverPartyReference>  <notional><currency>EUR</currency"
                + "><amount>1000000.0</amount></notional>  <swap>    <swapStream id=\"floatLeg\">      <receiverPartyReference"
                + ">party1</receiverPartyReference>      <calculationPeriodDates>        <effectiveDate>2024-01-01<"
                + "/effectiveDate>        <terminationDate>2029-01-01</terminationDate>        <calculationPeriodFrequency"
                + "><periodMultiplier>6</periodMultiplier><period>M</period></calculationPeriodFrequency>     "
                + " </calculationPeriodDates>      <paymentDates>        <paymentFrequency><periodMultiplier>6<"
                + "/periodMultiplier><period>M</period></paymentFrequency>      </paymentDates>      <calculationPeriodAmount>"
                + "        <calculation><notional><currency>EUR</currency><amount>1000000.0</amount></notional>       "
                + " <floatingRateCalculation><floatingRateIndex>EUR-EURIBOR-Reuters</floatingRateIndex><indexTenor>"
                + "<periodMultiplier>6</periodMultiplier><period>M</period></indexTenor></floatingRateCalculation>     "
                + "   </calculation>      </calculationPeriodAmount>    </swapStream>    <swapStream id=\"fixedLeg\">    "
                + "  <receiverPartyReference>party2</receiverPartyReference>      <calculationPeriodDates>       "
                + " <effectiveDate>2024-01-01</effectiveDate>        <terminationDate>2029-01-01</terminationDate>     "
                + "   <calculationPeriodFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></calculation"
                + "PeriodFrequency>      </calculationPeriodDates>      <paymentDates>        <paymentFrequency>"
                + "<periodMultiplier>1</periodMultiplier><period>Y</period></paymentFrequency>      </paymentDates>    "
                + "  <calculationPeriodAmount>        <calculation><notional><currency>EUR</currency><amount>1000000.0<"
                + "/amount></notional>        <fixedRateSchedule><initialValue>0.02</initialValue></fixedRateSchedule> "
                + "       </calculation>      </calculationPeriodAmount>    </swapStream>  </swap></trade></smartderiva"
                + "tivecontract>"));
  }
}
