package net.finmath.smartcontract.valuation.service.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import net.finmath.smartcontract.valuation.marketdata.generators.MarketDataGeneratorLauncherDiffblueBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ValuationConfigDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ValuationConfig#setFpmlSchemaPath(String)}
   *   <li>{@link ValuationConfig#setInternalMarketDataProvider(String)}
   *   <li>{@link ValuationConfig#setLiveMarketData(boolean)}
   *   <li>{@link ValuationConfig#setLiveMarketDataProvider(String)}
   *   <li>{@link ValuationConfig#setMarketDataProviderToTemplate(Map)}
   *   <li>{@link ValuationConfig#setProductFixingType(String)}
   *   <li>{@link ValuationConfig#setSettlementCurrency(String)}
   *   <li>{@link ValuationConfig#getFpmlSchemaPath()}
   *   <li>{@link ValuationConfig#getInternalMarketDataProvider()}
   *   <li>{@link ValuationConfig#getLiveMarketDataProvider()}
   *   <li>{@link ValuationConfig#getMarketDataProviderToTemplate()}
   *   <li>{@link ValuationConfig#getProductFixingType()}
   *   <li>{@link ValuationConfig#getSettlementCurrency()}
   *   <li>{@link ValuationConfig#isLiveMarketData()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String ValuationConfig.getFpmlSchemaPath()",
    "String ValuationConfig.getInternalMarketDataProvider()",
    "String ValuationConfig.getLiveMarketDataProvider()",
    "Map ValuationConfig.getMarketDataProviderToTemplate()",
    "String ValuationConfig.getProductFixingType()",
    "String ValuationConfig.getSettlementCurrency()",
    "boolean ValuationConfig.isLiveMarketData()",
    "void ValuationConfig.setFpmlSchemaPath(String)",
    "void ValuationConfig.setInternalMarketDataProvider(String)",
    "void ValuationConfig.setLiveMarketData(boolean)",
    "void ValuationConfig.setLiveMarketDataProvider(String)",
    "void ValuationConfig.setMarketDataProviderToTemplate(Map)",
    "void ValuationConfig.setProductFixingType(String)",
    "void ValuationConfig.setSettlementCurrency(String)"
  })
  void testGettersAndSetters() {
    // Arrange
    ValuationConfig valuationConfig = new ValuationConfig();

    // Act
    valuationConfig.setFpmlSchemaPath(
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());
    valuationConfig.setInternalMarketDataProvider(
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());
    valuationConfig.setLiveMarketData(true);
    valuationConfig.setLiveMarketDataProvider(
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());
    HashMap<String, String> marketDataProviderToTemplate = new HashMap<>();
    valuationConfig.setMarketDataProviderToTemplate(marketDataProviderToTemplate);
    valuationConfig.setProductFixingType(
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());
    valuationConfig.setSettlementCurrency(
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());
    String actualFpmlSchemaPath = valuationConfig.getFpmlSchemaPath();
    String actualInternalMarketDataProvider = valuationConfig.getInternalMarketDataProvider();
    String actualLiveMarketDataProvider = valuationConfig.getLiveMarketDataProvider();
    Map<String, String> actualMarketDataProviderToTemplate =
        valuationConfig.getMarketDataProviderToTemplate();
    String actualProductFixingType = valuationConfig.getProductFixingType();
    String actualSettlementCurrency = valuationConfig.getSettlementCurrency();
    boolean actualIsLiveMarketDataResult = valuationConfig.isLiveMarketData();

    // Assert
    assertEquals(
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
            + "tivecontract>",
        actualFpmlSchemaPath);
    assertEquals(
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
            + "tivecontract>",
        actualInternalMarketDataProvider);
    assertEquals(
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
            + "tivecontract>",
        actualLiveMarketDataProvider);
    assertEquals(
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
            + "tivecontract>",
        actualProductFixingType);
    assertEquals(
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
            + "tivecontract>",
        actualSettlementCurrency);
    assertTrue(actualMarketDataProviderToTemplate.isEmpty());
    assertTrue(actualIsLiveMarketDataResult);
    assertSame(marketDataProviderToTemplate, actualMarketDataProviderToTemplate);
  }
}
