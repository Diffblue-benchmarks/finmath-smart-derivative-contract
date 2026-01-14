package net.finmath.smartcontract.valuation.marketdata.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import net.finmath.smartcontract.valuation.marketdata.generators.MarketDataGeneratorLauncherDiffblueBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class MarketDataErrorsDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link MarketDataErrors#MarketDataErrors(boolean)}
   *   <li>{@link MarketDataErrors#setErrorMessage(String)}
   *   <li>{@link MarketDataErrors#setMissingDataPoints(List)}
   *   <li>{@link MarketDataErrors#toString()}
   *   <li>{@link MarketDataErrors#getErrorMessage()}
   *   <li>{@link MarketDataErrors#getMissingDataPoints()}
   *   <li>{@link MarketDataErrors#hasErrors()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MarketDataErrors.<init>(boolean)",
    "String MarketDataErrors.getErrorMessage()",
    "List MarketDataErrors.getMissingDataPoints()",
    "boolean MarketDataErrors.hasErrors()",
    "void MarketDataErrors.setErrorMessage(String)",
    "void MarketDataErrors.setMissingDataPoints(List)",
    "String MarketDataErrors.toString()"
  })
  void testGettersAndSetters() {
    // Arrange and Act
    MarketDataErrors actualMarketDataErrors = new MarketDataErrors(true);
    actualMarketDataErrors.setErrorMessage(
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());
    ArrayList<String> missingDataPoints = new ArrayList<>();
    actualMarketDataErrors.setMissingDataPoints(missingDataPoints);
    String actualToStringResult = actualMarketDataErrors.toString();
    String actualErrorMessage = actualMarketDataErrors.getErrorMessage();
    List<String> actualMissingDataPoints = actualMarketDataErrors.getMissingDataPoints();
    boolean actualHasErrorsResult = actualMarketDataErrors.hasErrors();

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
        actualErrorMessage);
    assertEquals(
        "MarketDataErrors{hasErrors=true, missingDataPoints=[], errorMessage='<?xml version=\"1.0\" encoding=\"UTF-8\""
            + " standalone=\"yes\"?><smartderivativecontract xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
            + " xmlns=\"uri:sdc\" xsi:schemaLocation=\"uri:sdc smartderivativecontract.xsd\"><dltTradeId>TEST-TRADE-001"
            + "</dltTradeId><dltAddress>0xTEST</dltAddress><uniqueTradeIdentifier>UTI-TEST-001</uniqueTradeIdentifier"
            + "><settlementCurrency>EUR</settlementCurrency><tradeType>SDCPledgedBalance</tradeType><valuation> "
            + " <artefact>    <groupId>net.finmath</groupId>    <artifactId>finmath-smart-derivative-contract</artifactId>"
            + "    <version>1.0.0</version>  </artefact></valuation><parties>  <party>    <name>Party 1</name>   "
            + " <id>party1</id>    <marginAccount><type>constant</type><value>10000.0</value></marginAccount>   "
            + " <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>    <address>0x1234</address> "
            + " </party>  <party>    <name>Party 2</name>    <id>party2</id>    <marginAccount><type>constant</type"
            + "><value>10000.0</value></marginAccount>    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>"
            + "    <address>0x5678</address>  </party></parties><settlement>  <settlementTime><type>daily</type><value"
            + ">17:00</value></settlementTime>  <marketdata>    <provider>test</provider>    <marketdataitems>     "
            + " <item>        <symbol>EUR-EONIA</symbol>        <curve>ESTR</curve>        <type>Fixing</type>     "
            + "   <tenor>1D</tenor>      </item>    </marketdataitems>  </marketdata></settlement><trade> "
            + " <product>InterestRateDerivative</product>  <productType>Swap</productType>  <startDate>2024-01-01<"
            + "/startDate>  <endDate>2029-01-01</endDate>  <receiverPartyReference><partyReference>party1</partyReference"
            + "></receiverPartyReference>  <notional><currency>EUR</currency><amount>1000000.0</amount></notional> "
            + " <swap>    <swapStream id=\"floatLeg\">      <receiverPartyReference>party1</receiverPartyReference>  "
            + "    <calculationPeriodDates>        <effectiveDate>2024-01-01</effectiveDate>        <terminationDate"
            + ">2029-01-01</terminationDate>        <calculationPeriodFrequency><periodMultiplier>6</periodMultiplier"
            + "><period>M</period></calculationPeriodFrequency>      </calculationPeriodDates>      <paymentDates> "
            + "       <paymentFrequency><periodMultiplier>6</periodMultiplier><period>M</period></paymentFrequency>"
            + "      </paymentDates>      <calculationPeriodAmount>        <calculation><notional><currency>EUR<"
            + "/currency><amount>1000000.0</amount></notional>        <floatingRateCalculation><floatingRateIndex>EUR"
            + "-EURIBOR-Reuters</floatingRateIndex><indexTenor><periodMultiplier>6</periodMultiplier><period>M</period"
            + "></indexTenor></floatingRateCalculation>        </calculation>      </calculationPeriodAmount>   "
            + " </swapStream>    <swapStream id=\"fixedLeg\">      <receiverPartyReference>party2</receiverPartyReference>"
            + "      <calculationPeriodDates>        <effectiveDate>2024-01-01</effectiveDate>        <terminationDate"
            + ">2029-01-01</terminationDate>        <calculationPeriodFrequency><periodMultiplier>1</periodMultiplier"
            + "><period>Y</period></calculationPeriodFrequency>      </calculationPeriodDates>      <paymentDates> "
            + "       <paymentFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></paymentFrequency>"
            + "      </paymentDates>      <calculationPeriodAmount>        <calculation><notional><currency>EUR<"
            + "/currency><amount>1000000.0</amount></notional>        <fixedRateSchedule><initialValue>0.02</initialValue"
            + "></fixedRateSchedule>        </calculation>      </calculationPeriodAmount>    </swapStream> "
            + " </swap></trade></smartderivativecontract>'}",
        actualToStringResult);
    assertTrue(actualMissingDataPoints.isEmpty());
    assertTrue(actualHasErrorsResult);
    assertSame(missingDataPoints, actualMissingDataPoints);
  }

  /**
   * Test {@link MarketDataErrors#addMissingData(String)}.
   *
   * <p>Method under test: {@link MarketDataErrors#addMissingData(String)}
   */
  @Test
  @DisplayName("Test addMissingData(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MarketDataErrors.addMissingData(String)"})
  void testAddMissingData() {
    // Arrange
    MarketDataErrors marketDataErrors = new MarketDataErrors(true);

    // Act
    marketDataErrors.addMissingData(
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    // Assert
    List<String> missingDataPoints = marketDataErrors.getMissingDataPoints();
    assertEquals(1, missingDataPoints.size());
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
        missingDataPoints.get(0));
  }
}
