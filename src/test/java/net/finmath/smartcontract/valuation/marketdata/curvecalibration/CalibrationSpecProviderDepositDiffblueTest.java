package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import net.finmath.marketdata.calibration.CalibratedCurves;
import net.finmath.marketdata.calibration.CalibratedCurves.CalibrationSpec;
import net.finmath.smartcontract.valuation.marketdata.data.LocalDateTimeAdapterDiffblueBase;
import net.finmath.smartcontract.valuation.marketdata.generators.MarketDataGeneratorLauncherDiffblueBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CalibrationSpecProviderDepositDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link CalibrationSpecProviderDeposit#CalibrationSpecProviderDeposit(String, String,
   *       double)}
   *   <li>{@link CalibrationSpecProviderDeposit#getDepositRate()}
   *   <li>{@link CalibrationSpecProviderDeposit#getMaturityLabel()}
   *   <li>{@link CalibrationSpecProviderDeposit#getTenorLabel()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CalibrationSpecProviderDeposit.<init>(String, String, double)",
    "double CalibrationSpecProviderDeposit.getDepositRate()",
    "String CalibrationSpecProviderDeposit.getMaturityLabel()",
    "String CalibrationSpecProviderDeposit.getTenorLabel()"
  })
  void testGettersAndSetters() {
    // Arrange
    String tenorLabel =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    // Act
    CalibrationSpecProviderDeposit actualCalibrationSpecProviderDeposit =
        new CalibrationSpecProviderDeposit(
            tenorLabel,
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml(),
            10.0d);
    double actualDepositRate = actualCalibrationSpecProviderDeposit.getDepositRate();
    String actualMaturityLabel = actualCalibrationSpecProviderDeposit.getMaturityLabel();

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
        actualMaturityLabel);
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
        actualCalibrationSpecProviderDeposit.getTenorLabel());
    assertEquals(10.0d, actualDepositRate);
  }

  /**
   * Test {@link CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}.
   *
   * <p>Method under test: {@link
   * CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}
   */
  @Test
  @DisplayName("Test getCalibrationSpec(CalibrationContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CalibratedCurves.CalibrationSpec CalibrationSpecProviderDeposit.getCalibrationSpec(CalibrationContext)"
  })
  void testGetCalibrationSpec() {
    // Arrange
    CalibrationSpecProviderDeposit calibrationSpecProviderDeposit =
        new CalibrationSpecProviderDeposit(
            LocalDateTimeAdapterDiffblueBase.createValidDateTimeString(), "42", 10.0d);

    LocalDate ofYearDayResult = LocalDate.ofYearDay(2, 2);

    // Act
    CalibrationSpec actualCalibrationSpec =
        calibrationSpecProviderDeposit.getCalibrationSpec(
            new CalibrationContextImpl(ofYearDayResult.atStartOfDay(), 10.0d));

    // Assert
    assertEquals("EUR-20220905-17000042", actualCalibrationSpec.getSymbol());
  }

  /**
   * Test {@link CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}.
   *
   * <p>Method under test: {@link
   * CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}
   */
  @Test
  @DisplayName("Test getCalibrationSpec(CalibrationContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CalibratedCurves.CalibrationSpec CalibrationSpecProviderDeposit.getCalibrationSpec(CalibrationContext)"
  })
  void testGetCalibrationSpec2() {
    // Arrange
    CalibrationSpecProviderDeposit calibrationSpecProviderDeposit =
        new CalibrationSpecProviderDeposit(
            LocalDateTimeAdapterDiffblueBase.createValidDateTimeString(), "42", 10.0d);

    LocalDate ofYearDayResult = LocalDate.ofYearDay(2, 100);

    // Act
    CalibrationSpec actualCalibrationSpec =
        calibrationSpecProviderDeposit.getCalibrationSpec(
            new CalibrationContextImpl(ofYearDayResult.atStartOfDay(), 10.0d));

    // Assert
    assertEquals("EUR-20220905-17000042", actualCalibrationSpec.getSymbol());
  }

  /**
   * Test {@link CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}.
   *
   * <p>Method under test: {@link
   * CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}
   */
  @Test
  @DisplayName("Test getCalibrationSpec(CalibrationContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CalibratedCurves.CalibrationSpec CalibrationSpecProviderDeposit.getCalibrationSpec(CalibrationContext)"
  })
  void testGetCalibrationSpec3() {
    // Arrange
    CalibrationSpecProviderDeposit calibrationSpecProviderDeposit =
        new CalibrationSpecProviderDeposit(
            LocalDateTimeAdapterDiffblueBase.createValidDateTimeString(), "42", 10.0d);

    LocalDate ofYearDayResult = LocalDate.ofYearDay(19, 100);

    // Act
    CalibrationSpec actualCalibrationSpec =
        calibrationSpecProviderDeposit.getCalibrationSpec(
            new CalibrationContextImpl(ofYearDayResult.atStartOfDay(), 10.0d));

    // Assert
    assertEquals("EUR-20220905-17000042", actualCalibrationSpec.getSymbol());
  }

  /**
   * Test {@link CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}.
   *
   * <p>Method under test: {@link
   * CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}
   */
  @Test
  @DisplayName("Test getCalibrationSpec(CalibrationContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CalibratedCurves.CalibrationSpec CalibrationSpecProviderDeposit.getCalibrationSpec(CalibrationContext)"
  })
  void testGetCalibrationSpec4() {
    // Arrange
    CalibrationSpecProviderDeposit calibrationSpecProviderDeposit =
        new CalibrationSpecProviderDeposit(
            LocalDateTimeAdapterDiffblueBase.createValidDateTimeString(), "42", 10.0d);

    LocalDate ofYearDayResult = LocalDate.ofYearDay(2, Integer.SIZE);

    // Act
    CalibrationSpec actualCalibrationSpec =
        calibrationSpecProviderDeposit.getCalibrationSpec(
            new CalibrationContextImpl(ofYearDayResult.atStartOfDay(), 10.0d));

    // Assert
    assertEquals("EUR-20220905-17000042", actualCalibrationSpec.getSymbol());
  }

  /**
   * Test {@link CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}.
   *
   * <p>Method under test: {@link
   * CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}
   */
  @Test
  @DisplayName("Test getCalibrationSpec(CalibrationContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CalibratedCurves.CalibrationSpec CalibrationSpecProviderDeposit.getCalibrationSpec(CalibrationContext)"
  })
  void testGetCalibrationSpec5() {
    // Arrange
    CalibrationSpecProviderDeposit calibrationSpecProviderDeposit =
        new CalibrationSpecProviderDeposit(
            LocalDateTimeAdapterDiffblueBase.createValidDateTimeString(), "42", 10.0d);

    LocalDate ofYearDayResult = LocalDate.ofYearDay(19, Integer.SIZE);

    // Act
    CalibrationSpec actualCalibrationSpec =
        calibrationSpecProviderDeposit.getCalibrationSpec(
            new CalibrationContextImpl(ofYearDayResult.atStartOfDay(), 10.0d));

    // Assert
    assertEquals("EUR-20220905-17000042", actualCalibrationSpec.getSymbol());
  }

  /**
   * Test {@link CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}.
   *
   * <ul>
   *   <li>When {@link LocalDate} with {@code 1970} and one and one atStartOfDay.
   * </ul>
   *
   * <p>Method under test: {@link
   * CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}
   */
  @Test
  @DisplayName(
      "Test getCalibrationSpec(CalibrationContext); when LocalDate with '1970' and one and one atStartOfDay")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CalibratedCurves.CalibrationSpec CalibrationSpecProviderDeposit.getCalibrationSpec(CalibrationContext)"
  })
  void testGetCalibrationSpec_whenLocalDateWith1970AndOneAndOneAtStartOfDay() {
    // Arrange
    CalibrationSpecProviderDeposit calibrationSpecProviderDeposit =
        new CalibrationSpecProviderDeposit(
            LocalDateTimeAdapterDiffblueBase.createValidDateTimeString(), "42", 10.0d);

    // Act
    CalibrationSpec actualCalibrationSpec =
        calibrationSpecProviderDeposit.getCalibrationSpec(
            new CalibrationContextImpl(LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d));

    // Assert
    assertEquals("EUR-20220905-17000042", actualCalibrationSpec.getSymbol());
  }
}
