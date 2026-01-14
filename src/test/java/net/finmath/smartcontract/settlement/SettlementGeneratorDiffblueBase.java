package net.finmath.smartcontract.settlement;

import com.diffblue.cover.annotations.InterestingTestFactory;
import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.product.SmartDerivativeContractDescriptor;
import net.finmath.smartcontract.valuation.marketdata.data.MarketDataPoint;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom base class for Diffblue Cover generated tests for SettlementGenerator.
 * Provides factory methods to create test instances with properly initialized state.
 */
public abstract class SettlementGeneratorDiffblueBase {

    /**
     * Factory method to create a SettlementGenerator with a fully initialized Settlement object.
     * This prevents NullPointerException when testing build() and buildObject() methods.
     *
     * @return A SettlementGenerator instance with all Settlement fields populated
     */
    @InterestingTestFactory
    public static SettlementGenerator createSettlementGeneratorWithInitializedSettlement() {
        SettlementGenerator generator = new SettlementGenerator();

        // Create a minimal Settlement object with all required fields
        Settlement settlement = new Settlement();
        settlement.setTradeId("TEST-TRADE-001");
        settlement.setSettlementType(Settlement.SettlementType.INITIAL);
        settlement.setCurrency("EUR");
        settlement.setMarginValue(BigDecimal.ZERO);
        settlement.setMarginLimits(List.of(BigDecimal.valueOf(1000), BigDecimal.valueOf(2000)));
        settlement.setSettlementTime(ZonedDateTime.now());
        settlement.setSettlementNPV(BigDecimal.valueOf(100));
        settlement.setSettlementNPVPrevious(BigDecimal.ZERO);
        settlement.setSettlementTimeNext(ZonedDateTime.now().plusDays(1));
        settlement.setSettlementNPVNext(BigDecimal.valueOf(105));

        // Create minimal market data list
        MarketDataList marketDataList = new MarketDataList();
        marketDataList.setRequestTimeStamp(LocalDateTime.now());
        marketDataList.setPoints(new ArrayList<>());
        settlement.setMarketData(marketDataList);

        // Create minimal settlement infos
        List<SettlementInfo> settlementInfos = new ArrayList<>();
        settlement.setSettlementInfos(settlementInfos);

        // Use reflection to set the private settlement field
        try {
            java.lang.reflect.Field settlementField = SettlementGenerator.class.getDeclaredField("settlement");
            settlementField.setAccessible(true);
            settlementField.set(generator, settlement);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize SettlementGenerator for testing", e);
        }

        return generator;
    }

    /**
     * Factory method to create a SettlementGenerator with a minimally initialized Settlement object.
     * This prevents NullPointerException when testing setter methods like settlementInfo(), settlementNPV(), etc.,
     * but doesn't set all fields so the methods can be tested in isolation.
     *
     * @return A SettlementGenerator instance with a non-null but minimally initialized Settlement
     */
    @InterestingTestFactory
    public static SettlementGenerator createSettlementGeneratorWithMinimalSettlement() {
        SettlementGenerator generator = new SettlementGenerator();

        // Create a minimal Settlement object - just enough to avoid NPE
        Settlement settlement = new Settlement();

        // Use reflection to set the private settlement field
        try {
            java.lang.reflect.Field settlementField = SettlementGenerator.class.getDeclaredField("settlement");
            settlementField.setAccessible(true);
            settlementField.set(generator, settlement);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize SettlementGenerator for testing", e);
        }

        return generator;
    }

    /**
     * Factory method to create a valid market data XML string.
     * This can be used as input for generateInitialSettlementXml and generateRegularSettlementXml methods.
     *
     * @return A valid XML string representing MarketDataList
     */
    @InterestingTestFactory
    public static String createValidMarketDataXml() {
        MarketDataList marketDataList = new MarketDataList();
        marketDataList.setRequestTimeStamp(LocalDateTime.now());

        // Add a sample market data point
        MarketDataPoint point = new MarketDataPoint();
        point.setId("EUR-EONIA");
        point.setValue(0.01);
        point.setTimeStamp(LocalDateTime.now());
        marketDataList.add(point);

        // Marshal to XML using the same parser that will be used by the code under test
        try {
            return net.finmath.smartcontract.product.xml.SDCXMLParser.marshalClassToXMLString(marketDataList);
        } catch (Exception e) {
            // Fallback to a minimal valid XML string
            return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                   "<marketDataList>" +
                   "<requestTimeStamp>2024-01-01T12:00:00</requestTimeStamp>" +
                   "<item>" +
                   "<id>EUR-EONIA</id>" +
                   "<value>0.01</value>" +
                   "<timeStamp>2024-01-01T12:00:00</timeStamp>" +
                   "</item>" +
                   "</marketDataList>";
        }
    }

    /**
     * Factory method to create a minimal SmartDerivativeContractDescriptor for testing.
     * This can be used as input for generateInitialSettlementXml and generateRegularSettlementXml methods.
     *
     * This factory loads a test SDC XML from resources and parses it.
     *
     * @return A SmartDerivativeContractDescriptor instance with valid data
     */
    @InterestingTestFactory
    public static SmartDerivativeContractDescriptor createMinimalSmartDerivativeContractDescriptor() {
        try {
            // Load a minimal SDC XML from resources
            String sdcXml = createMinimalSmartDerivativeContractXml();
            return net.finmath.smartcontract.product.xml.SDCXMLParser.parse(sdcXml);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create SmartDerivativeContractDescriptor for testing", e);
        }
    }

    /**
     * Factory method to create a minimal SDC XML string for testing.
     *
     * @return A minimal valid SDC XML string
     */
    @InterestingTestFactory
    public static String createMinimalSmartDerivativeContractXml() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
               "<smartderivativecontract xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
               "xmlns=\"uri:sdc\" xsi:schemaLocation=\"uri:sdc smartderivativecontract.xsd\">" +
               "<dltTradeId>TEST-TRADE-001</dltTradeId>" +
               "<dltAddress>0xTEST</dltAddress>" +
               "<uniqueTradeIdentifier>UTI-TEST-001</uniqueTradeIdentifier>" +
               "<settlementCurrency>EUR</settlementCurrency>" +
               "<tradeType>SDCPledgedBalance</tradeType>" +
               "<valuation>" +
               "  <artefact>" +
               "    <groupId>net.finmath</groupId>" +
               "    <artifactId>finmath-smart-derivative-contract</artifactId>" +
               "    <version>1.0.0</version>" +
               "  </artefact>" +
               "</valuation>" +
               "<parties>" +
               "  <party>" +
               "    <name>Party 1</name>" +
               "    <id>party1</id>" +
               "    <marginAccount><type>constant</type><value>10000.0</value></marginAccount>" +
               "    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>" +
               "    <address>0x1234</address>" +
               "  </party>" +
               "  <party>" +
               "    <name>Party 2</name>" +
               "    <id>party2</id>" +
               "    <marginAccount><type>constant</type><value>10000.0</value></marginAccount>" +
               "    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>" +
               "    <address>0x5678</address>" +
               "  </party>" +
               "</parties>" +
               "<settlement>" +
               "  <settlementTime><type>daily</type><value>17:00</value></settlementTime>" +
               "  <marketdata>" +
               "    <provider>test</provider>" +
               "    <marketdataitems>" +
               "      <item>" +
               "        <symbol>EUR-EONIA</symbol>" +
               "        <curve>ESTR</curve>" +
               "        <type>Fixing</type>" +
               "        <tenor>1D</tenor>" +
               "      </item>" +
               "    </marketdataitems>" +
               "  </marketdata>" +
               "</settlement>" +
               "<trade>" +
               "  <product>InterestRateDerivative</product>" +
               "  <productType>Swap</productType>" +
               "  <startDate>2024-01-01</startDate>" +
               "  <endDate>2029-01-01</endDate>" +
               "  <receiverPartyReference><partyReference>party1</partyReference></receiverPartyReference>" +
               "  <notional><currency>EUR</currency><amount>1000000.0</amount></notional>" +
               "  <swap>" +
               "    <swapStream id=\"floatLeg\">" +
               "      <receiverPartyReference>party1</receiverPartyReference>" +
               "      <calculationPeriodDates>" +
               "        <effectiveDate>2024-01-01</effectiveDate>" +
               "        <terminationDate>2029-01-01</terminationDate>" +
               "        <calculationPeriodFrequency><periodMultiplier>6</periodMultiplier><period>M</period></calculationPeriodFrequency>" +
               "      </calculationPeriodDates>" +
               "      <paymentDates>" +
               "        <paymentFrequency><periodMultiplier>6</periodMultiplier><period>M</period></paymentFrequency>" +
               "      </paymentDates>" +
               "      <calculationPeriodAmount>" +
               "        <calculation><notional><currency>EUR</currency><amount>1000000.0</amount></notional>" +
               "        <floatingRateCalculation><floatingRateIndex>EUR-EURIBOR-Reuters</floatingRateIndex><indexTenor><periodMultiplier>6</periodMultiplier><period>M</period></indexTenor></floatingRateCalculation>" +
               "        </calculation>" +
               "      </calculationPeriodAmount>" +
               "    </swapStream>" +
               "    <swapStream id=\"fixedLeg\">" +
               "      <receiverPartyReference>party2</receiverPartyReference>" +
               "      <calculationPeriodDates>" +
               "        <effectiveDate>2024-01-01</effectiveDate>" +
               "        <terminationDate>2029-01-01</terminationDate>" +
               "        <calculationPeriodFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></calculationPeriodFrequency>" +
               "      </calculationPeriodDates>" +
               "      <paymentDates>" +
               "        <paymentFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></paymentFrequency>" +
               "      </paymentDates>" +
               "      <calculationPeriodAmount>" +
               "        <calculation><notional><currency>EUR</currency><amount>1000000.0</amount></notional>" +
               "        <fixedRateSchedule><initialValue>0.02</initialValue></fixedRateSchedule>" +
               "        </calculation>" +
               "      </calculationPeriodAmount>" +
               "    </swapStream>" +
               "  </swap>" +
               "</trade>" +
               "</smartderivativecontract>";
    }
}
