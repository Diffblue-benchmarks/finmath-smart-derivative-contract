package net.finmath.smartcontract.valuation.marketdata.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import net.finmath.smartcontract.valuation.marketdata.generators.MarketDataGeneratorLauncherDiffblueBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class MarketDataPointDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link MarketDataPoint#MarketDataPoint()}
   *   <li>{@link MarketDataPoint#setId(String)}
   *   <li>{@link MarketDataPoint#setTimeStamp(LocalDateTime)}
   *   <li>{@link MarketDataPoint#setValue(Double)}
   *   <li>{@link MarketDataPoint#toString()}
   *   <li>{@link MarketDataPoint#getId()}
   *   <li>{@link MarketDataPoint#getTimeStamp()}
   *   <li>{@link MarketDataPoint#getValue()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MarketDataPoint.<init>()",
    "void MarketDataPoint.<init>(String, Double, LocalDateTime)",
    "String MarketDataPoint.getId()",
    "LocalDateTime MarketDataPoint.getTimeStamp()",
    "Double MarketDataPoint.getValue()",
    "void MarketDataPoint.setId(String)",
    "void MarketDataPoint.setTimeStamp(LocalDateTime)",
    "void MarketDataPoint.setValue(Double)",
    "String MarketDataPoint.toString()"
  })
  void testGettersAndSetters() {
    // Arrange and Act
    MarketDataPoint actualMarketDataPoint = new MarketDataPoint();
    actualMarketDataPoint.setId(
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());
    LocalDateTime timeStamp = LocalDate.of(1970, 1, 1).atStartOfDay();
    actualMarketDataPoint.setTimeStamp(timeStamp);
    actualMarketDataPoint.setValue(10.0d);
    String actualToStringResult = actualMarketDataPoint.toString();
    String actualId = actualMarketDataPoint.getId();
    LocalDateTime actualTimeStamp = actualMarketDataPoint.getTimeStamp();

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
        actualId);
    assertEquals(
        "MarketDataPoint{timeStamp=1970-01-01T00:00, id='<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes"
            + "\"?><smartderivativecontract xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"uri:sdc\""
            + " xsi:schemaLocation=\"uri:sdc smartderivativecontract.xsd\"><dltTradeId>TEST-TRADE-001</dltTradeId>"
            + "<dltAddress>0xTEST</dltAddress><uniqueTradeIdentifier>UTI-TEST-001</uniqueTradeIdentifier><settlementCurrency"
            + ">EUR</settlementCurrency><tradeType>SDCPledgedBalance</tradeType><valuation>  <artefact>   "
            + " <groupId>net.finmath</groupId>    <artifactId>finmath-smart-derivative-contract</artifactId>   "
            + " <version>1.0.0</version>  </artefact></valuation><parties>  <party>    <name>Party 1</name>   "
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
            + " </swap></trade></smartderivativecontract>', value=10.0}",
        actualToStringResult);
    assertEquals(10.0d, actualMarketDataPoint.getValue().doubleValue());
    assertSame(timeStamp, actualTimeStamp);
  }

  /**
   * Test getters and setters.
   *
   * <ul>
   *   <li>When createMinimalSmartDerivativeContractXml.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link MarketDataPoint#MarketDataPoint(String, Double, LocalDateTime)}
   *   <li>{@link MarketDataPoint#setId(String)}
   *   <li>{@link MarketDataPoint#setTimeStamp(LocalDateTime)}
   *   <li>{@link MarketDataPoint#setValue(Double)}
   *   <li>{@link MarketDataPoint#toString()}
   *   <li>{@link MarketDataPoint#getId()}
   *   <li>{@link MarketDataPoint#getTimeStamp()}
   *   <li>{@link MarketDataPoint#getValue()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; when createMinimalSmartDerivativeContractXml")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MarketDataPoint.<init>()",
    "void MarketDataPoint.<init>(String, Double, LocalDateTime)",
    "String MarketDataPoint.getId()",
    "LocalDateTime MarketDataPoint.getTimeStamp()",
    "Double MarketDataPoint.getValue()",
    "void MarketDataPoint.setId(String)",
    "void MarketDataPoint.setTimeStamp(LocalDateTime)",
    "void MarketDataPoint.setValue(Double)",
    "String MarketDataPoint.toString()"
  })
  void testGettersAndSetters_whenCreateMinimalSmartDerivativeContractXml() {
    // Arrange and Act
    MarketDataPoint actualMarketDataPoint =
        new MarketDataPoint(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml(),
            10.0d,
            LocalDate.of(1970, 1, 1).atStartOfDay());
    actualMarketDataPoint.setId(
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());
    LocalDateTime timeStamp = LocalDate.of(1970, 1, 1).atStartOfDay();
    actualMarketDataPoint.setTimeStamp(timeStamp);
    actualMarketDataPoint.setValue(10.0d);
    String actualToStringResult = actualMarketDataPoint.toString();
    String actualId = actualMarketDataPoint.getId();
    LocalDateTime actualTimeStamp = actualMarketDataPoint.getTimeStamp();

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
        actualId);
    assertEquals(
        "MarketDataPoint{timeStamp=1970-01-01T00:00, id='<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes"
            + "\"?><smartderivativecontract xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"uri:sdc\""
            + " xsi:schemaLocation=\"uri:sdc smartderivativecontract.xsd\"><dltTradeId>TEST-TRADE-001</dltTradeId>"
            + "<dltAddress>0xTEST</dltAddress><uniqueTradeIdentifier>UTI-TEST-001</uniqueTradeIdentifier><settlementCurrency"
            + ">EUR</settlementCurrency><tradeType>SDCPledgedBalance</tradeType><valuation>  <artefact>   "
            + " <groupId>net.finmath</groupId>    <artifactId>finmath-smart-derivative-contract</artifactId>   "
            + " <version>1.0.0</version>  </artefact></valuation><parties>  <party>    <name>Party 1</name>   "
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
            + " </swap></trade></smartderivativecontract>', value=10.0}",
        actualToStringResult);
    assertEquals(10.0d, actualMarketDataPoint.getValue().doubleValue());
    assertSame(timeStamp, actualTimeStamp);
  }

  /**
   * Test {@link MarketDataPoint#equals(Object)}, and {@link MarketDataPoint#hashCode()}.
   *
   * <ul>
   *   <li>When other is equal.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataPoint#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MarketDataPoint.equals(Object)"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    MarketDataPoint marketDataPoint = new MarketDataPoint();
    MarketDataPoint marketDataPoint2 = new MarketDataPoint();

    // Act and Assert
    assertEquals(marketDataPoint, marketDataPoint2);
    assertNotEquals(marketDataPoint.hashCode(), marketDataPoint2.hashCode());
  }

  /**
   * Test {@link MarketDataPoint#equals(Object)}, and {@link MarketDataPoint#hashCode()}.
   *
   * <ul>
   *   <li>When other is same.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataPoint#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MarketDataPoint.equals(Object)"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    MarketDataPoint marketDataPoint = new MarketDataPoint();

    // Act and Assert
    assertEquals(marketDataPoint, marketDataPoint);
    int expectedHashCodeResult = marketDataPoint.hashCode();
    assertEquals(expectedHashCodeResult, marketDataPoint.hashCode());
  }

  /**
   * Test {@link MarketDataPoint#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataPoint#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MarketDataPoint.equals(Object)"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    MarketDataPoint marketDataPoint =
        new MarketDataPoint(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml(),
            10.0d,
            LocalDate.of(1970, 1, 1).atStartOfDay());

    // Act and Assert
    assertNotEquals(marketDataPoint, new MarketDataPoint());
  }

  /**
   * Test {@link MarketDataPoint#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataPoint#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MarketDataPoint.equals(Object)"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    MarketDataPoint marketDataPoint = new MarketDataPoint();
    marketDataPoint.setId(
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    // Act and Assert
    assertNotEquals(marketDataPoint, new MarketDataPoint());
  }

  /**
   * Test {@link MarketDataPoint#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataPoint#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MarketDataPoint.equals(Object)"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    MarketDataPoint marketDataPoint = new MarketDataPoint();
    marketDataPoint.setValue(10.0d);

    // Act and Assert
    assertNotEquals(marketDataPoint, new MarketDataPoint());
  }

  /**
   * Test {@link MarketDataPoint#equals(Object)}.
   *
   * <ul>
   *   <li>When other is {@code null}.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataPoint#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MarketDataPoint.equals(Object)"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new MarketDataPoint(), null);
  }

  /**
   * Test {@link MarketDataPoint#equals(Object)}.
   *
   * <ul>
   *   <li>When other is wrong type.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataPoint#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MarketDataPoint.equals(Object)"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new MarketDataPoint(), "Different type to MarketDataPoint");
  }
}
