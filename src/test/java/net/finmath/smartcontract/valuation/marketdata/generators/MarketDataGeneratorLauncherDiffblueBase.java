package net.finmath.smartcontract.valuation.marketdata.generators;

import com.diffblue.cover.annotations.InterestingTestFactory;
import net.finmath.smartcontract.product.SmartDerivativeContractDescriptor;

/**
 * Custom base class for Diffblue Cover generated tests for MarketDataGeneratorLauncher.
 * Provides factory methods to create test instances with properly initialized parameters.
 */
public abstract class MarketDataGeneratorLauncherDiffblueBase {

    /**
     * Factory method to create a minimal SmartDerivativeContractDescriptor with non-null marketdataItemList.
     * This prevents NullPointerException when testing instantiateMarketDataGeneratorWebsocket() method.
     *
     * This factory creates a SmartDerivativeContractDescriptor by parsing a minimal SDC XML,
     * which ensures all required fields (including marketdataItemList) are properly initialized.
     *
     * @return A SmartDerivativeContractDescriptor instance with non-null marketdataItemList
     */
    @InterestingTestFactory
    public static SmartDerivativeContractDescriptor createSmartDerivativeContractDescriptorWithMarketData() {
        try {
            // Load a minimal SDC XML and parse it
            String sdcXml = createMinimalSmartDerivativeContractXml();
            return net.finmath.smartcontract.product.xml.SDCXMLParser.parse(sdcXml);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create SmartDerivativeContractDescriptor for testing", e);
        }
    }

    /**
     * Factory method to create a minimal SDC XML string for testing.
     * This XML includes market data items to ensure the marketdataItemList is not null.
     *
     * @return A minimal valid SDC XML string with market data items
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
