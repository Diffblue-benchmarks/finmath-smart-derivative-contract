package net.finmath.smartcontract.product;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import net.finmath.marketdata.products.AnalyticProduct;
import net.finmath.marketdata.products.Swap;
import net.finmath.marketdata.products.SwapLeg;
import net.finmath.modelling.descriptor.InterestRateSwapLegProductDescriptor;
import net.finmath.modelling.descriptor.InterestRateSwapProductDescriptor;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationParserDataItemsDiffblueBase;
import net.finmath.smartcontract.valuation.marketdata.generators.MarketDataGeneratorLauncherDiffblueBase;
import net.finmath.time.ScheduleFromPeriods;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class IRSwapGeneratorDiffblueTest {
  /**
   * Test {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String, double, double,
   * boolean, String, String)}.
   *
   * <ul>
   *   <li>Then return LegPayer ForwardCurveName is a string.
   * </ul>
   *
   * <p>Method under test: {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String,
   * double, double, boolean, String, String)}
   */
  @Test
  @DisplayName(
      "Test generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String); then return LegPayer ForwardCurveName is a string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Swap IRSwapGenerator.generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String)"
  })
  void testGenerateAnalyticSwapObject_thenReturnLegPayerForwardCurveNameIsAString() {
    // Arrange
    LocalDate startDate = LocalDate.of(1970, 1, 1);
    String forwardCurveName = CalibrationParserDataItemsDiffblueBase.createValidMarketDataXml();

    // Act
    Swap actualGenerateAnalyticSwapObjectResult =
        IRSwapGenerator.generateAnalyticSwapObject(
            startDate,
            "3M",
            10.0d,
            10.0d,
            true,
            forwardCurveName,
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    // Assert
    AnalyticProduct legPayer = actualGenerateAnalyticSwapObjectResult.getLegPayer();
    assertTrue(legPayer instanceof SwapLeg);
    AnalyticProduct legReceiver = actualGenerateAnalyticSwapObjectResult.getLegReceiver();
    assertTrue(legReceiver instanceof SwapLeg);
    InterestRateSwapProductDescriptor descriptor =
        actualGenerateAnalyticSwapObjectResult.getDescriptor();
    assertTrue(descriptor.getLegPayer() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(descriptor.getLegReceiver() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(((SwapLeg) legPayer).getSchedule() instanceof ScheduleFromPeriods);
    assertTrue(((SwapLeg) legReceiver).getSchedule() instanceof ScheduleFromPeriods);
    assertEquals(
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
            + "<marketDataList>\n"
            + "    <requestTimeStamp>20220905-170000</requestTimeStamp>\n"
            + "    <item>\n"
            + "        <id>ESTRFIX1D</id>\n"
            + "        <value>0.001</value>\n"
            + "        <timeStamp>20220905-170000</timeStamp>\n"
            + "    </item>\n"
            + "    <item>\n"
            + "        <id>EUB6FIX6M</id>\n"
            + "        <value>0.015</value>\n"
            + "        <timeStamp>20220905-170000</timeStamp>\n"
            + "    </item>\n"
            + "    <item>\n"
            + "        <id>EUB6DEP6M</id>\n"
            + "        <value>0.018</value>\n"
            + "        <timeStamp>20220905-170000</timeStamp>\n"
            + "    </item>\n"
            + "    <item>\n"
            + "        <id>EUB6SWP2Y</id>\n"
            + "        <value>0.025</value>\n"
            + "        <timeStamp>20220905-170000</timeStamp>\n"
            + "    </item>\n"
            + "    <item>\n"
            + "        <id>EUB6SWP3Y</id>\n"
            + "        <value>0.028</value>\n"
            + "        <timeStamp>20220905-170000</timeStamp>\n"
            + "    </item>\n"
            + "    <item>\n"
            + "        <id>EUB6SWP5Y</id>\n"
            + "        <value>0.032</value>\n"
            + "        <timeStamp>20220905-170000</timeStamp>\n"
            + "    </item>\n"
            + "    <item>\n"
            + "        <id>EUB6SWP10Y</id>\n"
            + "        <value>0.035</value>\n"
            + "        <timeStamp>20220905-170000</timeStamp>\n"
            + "    </item>\n"
            + "    <item>\n"
            + "        <id>ESTRSWP1Y</id>\n"
            + "        <value>0.020</value>\n"
            + "        <timeStamp>20220905-170000</timeStamp>\n"
            + "    </item>\n"
            + "    <item>\n"
            + "        <id>ESTRSWP2Y</id>\n"
            + "        <value>0.022</value>\n"
            + "        <timeStamp>20220905-170000</timeStamp>\n"
            + "    </item>\n"
            + "    <item>\n"
            + "        <id>ESTRSWP3Y</id>\n"
            + "        <value>0.024</value>\n"
            + "        <timeStamp>20220905-170000</timeStamp>\n"
            + "    </item>\n"
            + "    <item>\n"
            + "        <id>ESTRSWP5Y</id>\n"
            + "        <value>0.027</value>\n"
            + "        <timeStamp>20220905-170000</timeStamp>\n"
            + "    </item>\n"
            + "    <item>\n"
            + "        <id>ESTRSWP10Y</id>\n"
            + "        <value>0.030</value>\n"
            + "        <timeStamp>20220905-170000</timeStamp>\n"
            + "    </item>\n"
            + "</marketDataList>",
        ((SwapLeg) legPayer).getForwardCurveName());
    assertArrayEquals(new double[] {0.0d}, ((SwapLeg) legPayer).getSpreads(), 0.0);
    assertArrayEquals(new double[] {10.0d}, ((SwapLeg) legReceiver).getSpreads(), 0.0);
  }

  /**
   * Test {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String, double, double,
   * boolean, String, String)}.
   *
   * <ul>
   *   <li>Then return LegPayer ForwardCurveName is empty string.
   * </ul>
   *
   * <p>Method under test: {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String,
   * double, double, boolean, String, String)}
   */
  @Test
  @DisplayName(
      "Test generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String); then return LegPayer ForwardCurveName is empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Swap IRSwapGenerator.generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String)"
  })
  void testGenerateAnalyticSwapObject_thenReturnLegPayerForwardCurveNameIsEmptyString() {
    // Arrange
    LocalDate startDate = LocalDate.of(1970, 1, 1);
    String forwardCurveName =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    // Act
    Swap actualGenerateAnalyticSwapObjectResult =
        IRSwapGenerator.generateAnalyticSwapObject(
            startDate,
            "3M",
            10.0d,
            10.0d,
            false,
            forwardCurveName,
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    // Assert
    AnalyticProduct legPayer = actualGenerateAnalyticSwapObjectResult.getLegPayer();
    assertTrue(legPayer instanceof SwapLeg);
    AnalyticProduct legReceiver = actualGenerateAnalyticSwapObjectResult.getLegReceiver();
    assertTrue(legReceiver instanceof SwapLeg);
    assertEquals("", ((SwapLeg) legPayer).getForwardCurveName());
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
        ((SwapLeg) legReceiver).getForwardCurveName());
    assertEquals(0.0d, ((SwapLeg) legReceiver).getSpread());
    assertEquals(10.0d, ((SwapLeg) legPayer).getSpread());
    assertArrayEquals(new double[] {0.0d}, ((SwapLeg) legReceiver).getSpreads(), 0.0);
    assertArrayEquals(new double[] {10.0d}, ((SwapLeg) legPayer).getSpreads(), 0.0);
  }

  /**
   * Test {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String, double, double,
   * boolean, String, String)}.
   *
   * <ul>
   *   <li>Then return LegPayer Spreads is array of {@code double} with zero.
   * </ul>
   *
   * <p>Method under test: {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String,
   * double, double, boolean, String, String)}
   */
  @Test
  @DisplayName(
      "Test generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String); then return LegPayer Spreads is array of double with zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Swap IRSwapGenerator.generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String)"
  })
  void testGenerateAnalyticSwapObject_thenReturnLegPayerSpreadsIsArrayOfDoubleWithZero() {
    // Arrange
    LocalDate startDate = LocalDate.of(1970, 1, 1);
    String forwardCurveName =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    // Act
    Swap actualGenerateAnalyticSwapObjectResult =
        IRSwapGenerator.generateAnalyticSwapObject(
            startDate,
            "3M",
            10.0d,
            10.0d,
            true,
            forwardCurveName,
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    // Assert
    AnalyticProduct legPayer = actualGenerateAnalyticSwapObjectResult.getLegPayer();
    assertTrue(legPayer instanceof SwapLeg);
    AnalyticProduct legReceiver = actualGenerateAnalyticSwapObjectResult.getLegReceiver();
    assertTrue(legReceiver instanceof SwapLeg);
    InterestRateSwapProductDescriptor descriptor =
        actualGenerateAnalyticSwapObjectResult.getDescriptor();
    assertTrue(descriptor.getLegPayer() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(descriptor.getLegReceiver() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(((SwapLeg) legPayer).getSchedule() instanceof ScheduleFromPeriods);
    assertTrue(((SwapLeg) legReceiver).getSchedule() instanceof ScheduleFromPeriods);
    assertArrayEquals(new double[] {0.0d}, ((SwapLeg) legPayer).getSpreads(), 0.0);
    assertArrayEquals(new double[] {10.0d}, ((SwapLeg) legReceiver).getSpreads(), 0.0);
  }

  /**
   * Test {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String, double, double,
   * boolean, String, String)}.
   *
   * <ul>
   *   <li>When {@code 1M}.
   *   <li>Then return LegPayer ForwardCurveName is {@code 1M}.
   * </ul>
   *
   * <p>Method under test: {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String,
   * double, double, boolean, String, String)}
   */
  @Test
  @DisplayName(
      "Test generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String); when '1M'; then return LegPayer ForwardCurveName is '1M'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Swap IRSwapGenerator.generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String)"
  })
  void testGenerateAnalyticSwapObject_when1m_thenReturnLegPayerForwardCurveNameIs1m() {
    // Arrange
    LocalDate startDate = LocalDate.of(1970, 1, 1);

    // Act
    Swap actualGenerateAnalyticSwapObjectResult =
        IRSwapGenerator.generateAnalyticSwapObject(
            startDate,
            "3M",
            10.0d,
            10.0d,
            true,
            "1M",
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    // Assert
    AnalyticProduct legPayer = actualGenerateAnalyticSwapObjectResult.getLegPayer();
    assertTrue(legPayer instanceof SwapLeg);
    AnalyticProduct legReceiver = actualGenerateAnalyticSwapObjectResult.getLegReceiver();
    assertTrue(legReceiver instanceof SwapLeg);
    InterestRateSwapProductDescriptor descriptor =
        actualGenerateAnalyticSwapObjectResult.getDescriptor();
    assertTrue(descriptor.getLegPayer() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(descriptor.getLegReceiver() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(((SwapLeg) legPayer).getSchedule() instanceof ScheduleFromPeriods);
    assertTrue(((SwapLeg) legReceiver).getSchedule() instanceof ScheduleFromPeriods);
    assertEquals("1M", ((SwapLeg) legPayer).getForwardCurveName());
    assertArrayEquals(new double[] {10.0d}, ((SwapLeg) legReceiver).getSpreads(), 0.0);
    assertArrayEquals(new double[] {0.0d, 0.0d, 0.0d}, ((SwapLeg) legPayer).getSpreads(), 0.0);
  }

  /**
   * Test {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String, double, double,
   * boolean, String, String)}.
   *
   * <ul>
   *   <li>When {@code 3M}.
   *   <li>Then return LegPayer ForwardCurveName is {@code 3M}.
   * </ul>
   *
   * <p>Method under test: {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String,
   * double, double, boolean, String, String)}
   */
  @Test
  @DisplayName(
      "Test generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String); when '3M'; then return LegPayer ForwardCurveName is '3M'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Swap IRSwapGenerator.generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String)"
  })
  void testGenerateAnalyticSwapObject_when3m_thenReturnLegPayerForwardCurveNameIs3m() {
    // Arrange
    LocalDate startDate = LocalDate.of(1970, 1, 1);

    // Act
    Swap actualGenerateAnalyticSwapObjectResult =
        IRSwapGenerator.generateAnalyticSwapObject(
            startDate,
            "3M",
            10.0d,
            10.0d,
            true,
            "3M",
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    // Assert
    AnalyticProduct legPayer = actualGenerateAnalyticSwapObjectResult.getLegPayer();
    assertTrue(legPayer instanceof SwapLeg);
    AnalyticProduct legReceiver = actualGenerateAnalyticSwapObjectResult.getLegReceiver();
    assertTrue(legReceiver instanceof SwapLeg);
    InterestRateSwapProductDescriptor descriptor =
        actualGenerateAnalyticSwapObjectResult.getDescriptor();
    assertTrue(descriptor.getLegPayer() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(descriptor.getLegReceiver() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(((SwapLeg) legPayer).getSchedule() instanceof ScheduleFromPeriods);
    assertTrue(((SwapLeg) legReceiver).getSchedule() instanceof ScheduleFromPeriods);
    assertEquals("3M", ((SwapLeg) legPayer).getForwardCurveName());
    assertArrayEquals(new double[] {0.0d}, ((SwapLeg) legPayer).getSpreads(), 0.0);
    assertArrayEquals(new double[] {10.0d}, ((SwapLeg) legReceiver).getSpreads(), 0.0);
  }

  /**
   * Test {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String, double, double,
   * boolean, String, String)}.
   *
   * <ul>
   *   <li>When now.
   * </ul>
   *
   * <p>Method under test: {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String,
   * double, double, boolean, String, String)}
   */
  @Test
  @DisplayName(
      "Test generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String); when now")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Swap IRSwapGenerator.generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String)"
  })
  void testGenerateAnalyticSwapObject_whenNow() {
    // Arrange
    LocalDate startDate = LocalDate.now();
    String forwardCurveName =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    // Act
    Swap actualGenerateAnalyticSwapObjectResult =
        IRSwapGenerator.generateAnalyticSwapObject(
            startDate,
            "3M",
            10.0d,
            10.0d,
            true,
            forwardCurveName,
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    // Assert
    AnalyticProduct legPayer = actualGenerateAnalyticSwapObjectResult.getLegPayer();
    assertTrue(legPayer instanceof SwapLeg);
    AnalyticProduct legReceiver = actualGenerateAnalyticSwapObjectResult.getLegReceiver();
    assertTrue(legReceiver instanceof SwapLeg);
    InterestRateSwapProductDescriptor descriptor =
        actualGenerateAnalyticSwapObjectResult.getDescriptor();
    assertTrue(descriptor.getLegPayer() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(descriptor.getLegReceiver() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(((SwapLeg) legPayer).getSchedule() instanceof ScheduleFromPeriods);
    assertTrue(((SwapLeg) legReceiver).getSchedule() instanceof ScheduleFromPeriods);
    assertArrayEquals(new double[] {0.0d}, ((SwapLeg) legPayer).getSpreads(), 0.0);
    assertArrayEquals(new double[] {10.0d}, ((SwapLeg) legReceiver).getSpreads(), 0.0);
  }

  /**
   * Test {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String, double, double,
   * boolean, String, String)}.
   *
   * <ul>
   *   <li>When ofEpochDay minus one.
   * </ul>
   *
   * <p>Method under test: {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String,
   * double, double, boolean, String, String)}
   */
  @Test
  @DisplayName(
      "Test generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String); when ofEpochDay minus one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Swap IRSwapGenerator.generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String)"
  })
  void testGenerateAnalyticSwapObject_whenOfEpochDayMinusOne() {
    // Arrange
    LocalDate startDate = LocalDate.ofEpochDay(-1L);
    String forwardCurveName =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    // Act
    Swap actualGenerateAnalyticSwapObjectResult =
        IRSwapGenerator.generateAnalyticSwapObject(
            startDate,
            "3M",
            10.0d,
            10.0d,
            true,
            forwardCurveName,
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    // Assert
    AnalyticProduct legPayer = actualGenerateAnalyticSwapObjectResult.getLegPayer();
    assertTrue(legPayer instanceof SwapLeg);
    AnalyticProduct legReceiver = actualGenerateAnalyticSwapObjectResult.getLegReceiver();
    assertTrue(legReceiver instanceof SwapLeg);
    InterestRateSwapProductDescriptor descriptor =
        actualGenerateAnalyticSwapObjectResult.getDescriptor();
    assertTrue(descriptor.getLegPayer() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(descriptor.getLegReceiver() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(((SwapLeg) legPayer).getSchedule() instanceof ScheduleFromPeriods);
    assertTrue(((SwapLeg) legReceiver).getSchedule() instanceof ScheduleFromPeriods);
    assertArrayEquals(new double[] {0.0d}, ((SwapLeg) legPayer).getSpreads(), 0.0);
    assertArrayEquals(new double[] {10.0d}, ((SwapLeg) legReceiver).getSpreads(), 0.0);
  }
}
