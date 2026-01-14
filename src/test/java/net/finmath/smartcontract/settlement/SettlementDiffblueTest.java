package net.finmath.smartcontract.settlement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.settlement.Settlement.SettlementType;
import net.finmath.smartcontract.valuation.marketdata.generators.MarketDataGeneratorLauncherDiffblueBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SettlementDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link Settlement}
   *   <li>{@link Settlement#setCurrency(String)}
   *   <li>{@link Settlement#setMarginLimits(List)}
   *   <li>{@link Settlement#setMarketData(MarketDataList)}
   *   <li>{@link Settlement#setSettlementInfos(List)}
   *   <li>{@link Settlement#setSettlementTime(ZonedDateTime)}
   *   <li>{@link Settlement#setSettlementTimeNext(ZonedDateTime)}
   *   <li>{@link Settlement#setSettlementType(SettlementType)}
   *   <li>{@link Settlement#setTradeId(String)}
   *   <li>{@link Settlement#getCurrency()}
   *   <li>{@link Settlement#getMarginLimits()}
   *   <li>{@link Settlement#getMarginValue()}
   *   <li>{@link Settlement#getMarketData()}
   *   <li>{@link Settlement#getSettlementInfos()}
   *   <li>{@link Settlement#getSettlementNPV()}
   *   <li>{@link Settlement#getSettlementNPVNext()}
   *   <li>{@link Settlement#getSettlementNPVPrevious()}
   *   <li>{@link Settlement#getSettlementTime()}
   *   <li>{@link Settlement#getSettlementTimeNext()}
   *   <li>{@link Settlement#getSettlementType()}
   *   <li>{@link Settlement#getTradeId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void Settlement.<init>()",
    "String Settlement.getCurrency()",
    "List Settlement.getMarginLimits()",
    "BigDecimal Settlement.getMarginValue()",
    "MarketDataList Settlement.getMarketData()",
    "List Settlement.getSettlementInfos()",
    "BigDecimal Settlement.getSettlementNPV()",
    "BigDecimal Settlement.getSettlementNPVNext()",
    "BigDecimal Settlement.getSettlementNPVPrevious()",
    "ZonedDateTime Settlement.getSettlementTime()",
    "ZonedDateTime Settlement.getSettlementTimeNext()",
    "SettlementType Settlement.getSettlementType()",
    "String Settlement.getTradeId()",
    "void Settlement.setCurrency(String)",
    "void Settlement.setMarginLimits(List)",
    "void Settlement.setMarginValue(BigDecimal)",
    "void Settlement.setMarketData(MarketDataList)",
    "void Settlement.setSettlementInfos(List)",
    "void Settlement.setSettlementNPV(BigDecimal)",
    "void Settlement.setSettlementNPVNext(BigDecimal)",
    "void Settlement.setSettlementNPVPrevious(BigDecimal)",
    "void Settlement.setSettlementTime(ZonedDateTime)",
    "void Settlement.setSettlementTimeNext(ZonedDateTime)",
    "void Settlement.setSettlementType(SettlementType)",
    "void Settlement.setTradeId(String)"
  })
  void testGettersAndSetters() {
    // Arrange and Act
    Settlement actualSettlement = new Settlement();
    actualSettlement.setCurrency(
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());
    ArrayList<BigDecimal> marginLimits = new ArrayList<>();
    actualSettlement.setMarginLimits(marginLimits);
    MarketDataList marketData = new MarketDataList();
    actualSettlement.setMarketData(marketData);
    ArrayList<SettlementInfo> settlementInfos = new ArrayList<>();
    actualSettlement.setSettlementInfos(settlementInfos);
    ZonedDateTime settlementTime = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC);
    actualSettlement.setSettlementTime(settlementTime);
    ZonedDateTime settlementTimeNext =
        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC);
    actualSettlement.setSettlementTimeNext(settlementTimeNext);
    actualSettlement.setSettlementType(SettlementType.INITIAL);
    actualSettlement.setTradeId(
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());
    String actualCurrency = actualSettlement.getCurrency();
    List<BigDecimal> actualMarginLimits = actualSettlement.getMarginLimits();
    BigDecimal actualMarginValue = actualSettlement.getMarginValue();
    MarketDataList actualMarketData = actualSettlement.getMarketData();
    List<SettlementInfo> actualSettlementInfos = actualSettlement.getSettlementInfos();
    BigDecimal actualSettlementNPV = actualSettlement.getSettlementNPV();
    BigDecimal actualSettlementNPVNext = actualSettlement.getSettlementNPVNext();
    BigDecimal actualSettlementNPVPrevious = actualSettlement.getSettlementNPVPrevious();
    ZonedDateTime actualSettlementTime = actualSettlement.getSettlementTime();
    ZonedDateTime actualSettlementTimeNext = actualSettlement.getSettlementTimeNext();
    SettlementType actualSettlementType = actualSettlement.getSettlementType();

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
        actualCurrency);
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
        actualSettlement.getTradeId());
    assertNull(actualMarginValue);
    assertNull(actualSettlementNPV);
    assertNull(actualSettlementNPVNext);
    assertNull(actualSettlementNPVPrevious);
    assertEquals(SettlementType.INITIAL, actualSettlementType);
    assertTrue(actualMarginLimits.isEmpty());
    assertTrue(actualSettlementInfos.isEmpty());
    assertSame(marginLimits, actualMarginLimits);
    assertSame(settlementInfos, actualSettlementInfos);
    assertSame(marketData, actualMarketData);
    assertSame(settlementTime, actualSettlementTime);
    assertSame(settlementTimeNext, actualSettlementTimeNext);
  }
}
