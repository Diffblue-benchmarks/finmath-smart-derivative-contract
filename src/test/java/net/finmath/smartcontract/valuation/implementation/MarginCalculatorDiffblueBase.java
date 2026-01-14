package net.finmath.smartcontract.valuation.implementation;

import com.diffblue.cover.annotations.InterestingTestFactory;

/**
 * Custom base class for Diffblue Cover generated tests for MarginCalculator.
 * Provides factory methods to create test instances with valid SDC XML product data.
 */
public abstract class MarginCalculatorDiffblueBase {

    /**
     * Factory method to create a valid Smart Derivative Contract XML string for MarginCalculator methods.
     * This prevents SDC_JAXB_ERROR when testing getValue methods that parse productData parameter.
     * The XML includes all required elements to pass JAXB unmarshalling and parsing.
     *
     * @return A valid SDC XML string
     */
    @InterestingTestFactory
    public static String createValidSmartDerivativeContractXml() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
               "<smartderivativecontract xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
               "xmlns=\"uri:sdc\" xsi:schemaLocation=\"uri:sdc smartderivativecontract.xsd\">" +
               "<dltTradeId>TEST-TRADE-001</dltTradeId>" +
               "<dltAddress>0x000000001</dltAddress>" +
               "<uniqueTradeIdentifier>UTI-TEST-001</uniqueTradeIdentifier>" +
               "<settlementCurrency>EUR</settlementCurrency>" +
               "<tradeType>SDCPledgedBalance</tradeType>" +
               "<valuation>" +
               "  <artefact>" +
               "    <groupId>net.finmath</groupId>" +
               "    <artifactId>finmath-smart-derivative-contract</artifactId>" +
               "    <version>0.1.8</version>" +
               "  </artefact>" +
               "</valuation>" +
               "<parties>" +
               "  <party>" +
               "    <name>Party 1</name>" +
               "    <id>party1</id>" +
               "    <marginAccount><type>constant</type><value>10000.0</value></marginAccount>" +
               "    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>" +
               "    <address>0x627306090abab3a6e1400e9345bc60c78a8bef57</address>" +
               "  </party>" +
               "  <party>" +
               "    <name>Party 2</name>" +
               "    <id>party2</id>" +
               "    <marginAccount><type>constant</type><value>10000.0</value></marginAccount>" +
               "    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>" +
               "    <address>0xf17f52151ebef6c7334fad080c5704d77216b732</address>" +
               "  </party>" +
               "</parties>" +
               "<settlement>" +
               "  <settlementTime><type>daily</type><value>17:00</value></settlementTime>" +
               "  <marketdata>" +
               "    <provider>internal</provider>" +
               "    <marketdataitems>" +
               "      <item><symbol>ESTRFIX1D</symbol><curve>ESTR</curve><type>Fixing</type><tenor>1D</tenor></item>" +
               "      <item><symbol>EUB6FIX6M</symbol><curve>Euribor6M</curve><type>Fixing</type><tenor>6M</tenor></item>" +
               "      <item><symbol>EUB6DEP6M</symbol><curve>Euribor6M</curve><type>Deposit</type><tenor>6M</tenor></item>" +
               "      <item><symbol>EUB6SWP2Y</symbol><curve>Euribor6M</curve><type>Swap-Rate</type><tenor>2Y</tenor></item>" +
               "      <item><symbol>EUB6SWP3Y</symbol><curve>Euribor6M</curve><type>Swap-Rate</type><tenor>3Y</tenor></item>" +
               "      <item><symbol>EUB6SWP4Y</symbol><curve>Euribor6M</curve><type>Swap-Rate</type><tenor>4Y</tenor></item>" +
               "      <item><symbol>EUB6SWP5Y</symbol><curve>Euribor6M</curve><type>Swap-Rate</type><tenor>5Y</tenor></item>" +
               "      <item><symbol>EUB6SWP6Y</symbol><curve>Euribor6M</curve><type>Swap-Rate</type><tenor>6Y</tenor></item>" +
               "      <item><symbol>EUB6SWP7Y</symbol><curve>Euribor6M</curve><type>Swap-Rate</type><tenor>7Y</tenor></item>" +
               "      <item><symbol>EUB6SWP8Y</symbol><curve>Euribor6M</curve><type>Swap-Rate</type><tenor>8Y</tenor></item>" +
               "      <item><symbol>EUB6SWP9Y</symbol><curve>Euribor6M</curve><type>Swap-Rate</type><tenor>9Y</tenor></item>" +
               "      <item><symbol>EUB6SWP10Y</symbol><curve>Euribor6M</curve><type>Swap-Rate</type><tenor>10Y</tenor></item>" +
               "      <item><symbol>ESTRSWP1Y</symbol><curve>ESTR</curve><type>Swap-Rate</type><tenor>1Y</tenor></item>" +
               "      <item><symbol>ESTRSWP2Y</symbol><curve>ESTR</curve><type>Swap-Rate</type><tenor>2Y</tenor></item>" +
               "      <item><symbol>ESTRSWP3Y</symbol><curve>ESTR</curve><type>Swap-Rate</type><tenor>3Y</tenor></item>" +
               "      <item><symbol>ESTRSWP5Y</symbol><curve>ESTR</curve><type>Swap-Rate</type><tenor>5Y</tenor></item>" +
               "      <item><symbol>ESTRSWP10Y</symbol><curve>ESTR</curve><type>Swap-Rate</type><tenor>10Y</tenor></item>" +
               "    </marketdataitems>" +
               "  </marketdata>" +
               "</settlement>" +
               "<receiverPartyID>party1</receiverPartyID>" +
               "<underlyings>" +
               "  <underlying>" +
               "    <dataDocument xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
               "xmlns=\"http://www.fpml.org/FpML-5/confirmation\" fpmlVersion=\"5-9\" " +
               "xsi:schemaLocation=\"http://www.fpml.org/FpML-5/confirmation ../../fpml-main-5-9.xsd http://www.w3.org/2000/09/xmldsig# ../../xmldsig-core-schema.xsd\">" +
               "      <trade>" +
               "        <tradeHeader>" +
               "          <partyTradeIdentifier>" +
               "            <partyReference href=\"party1\"/>" +
               "            <tradeId tradeIdScheme=\"http://www.partyA.com/swaps/trade-id\">CP1</tradeId>" +
               "          </partyTradeIdentifier>" +
               "          <partyTradeIdentifier>" +
               "            <partyReference href=\"party2\"/>" +
               "            <tradeId tradeIdScheme=\"http://www.partyA.com/swaps/trade-id\">CP2</tradeId>" +
               "          </partyTradeIdentifier>" +
               "          <tradeDate>2022-09-05</tradeDate>" +
               "        </tradeHeader>" +
               "        <swap>" +
               "          <swapStream>" +
               "            <payerPartyReference href=\"party1\"/>" +
               "            <receiverPartyReference href=\"party2\"/>" +
               "            <calculationPeriodDates id=\"floatingCalcPeriodDates\">" +
               "              <effectiveDate>" +
               "                <unadjustedDate>2022-09-07</unadjustedDate>" +
               "                <dateAdjustments><businessDayConvention>NONE</businessDayConvention></dateAdjustments>" +
               "              </effectiveDate>" +
               "              <terminationDate>" +
               "                <unadjustedDate>2032-09-07</unadjustedDate>" +
               "                <dateAdjustments>" +
               "                  <businessDayConvention>MODFOLLOWING</businessDayConvention>" +
               "                  <businessCenters id=\"primaryBusinessCenters\"><businessCenter>DEFR</businessCenter></businessCenters>" +
               "                </dateAdjustments>" +
               "              </terminationDate>" +
               "              <calculationPeriodDatesAdjustments>" +
               "                <businessDayConvention>MODFOLLOWING</businessDayConvention>" +
               "                <businessCentersReference href=\"primaryBusinessCenters\"/>" +
               "              </calculationPeriodDatesAdjustments>" +
               "              <calculationPeriodFrequency>" +
               "                <periodMultiplier>6</periodMultiplier><period>M</period><rollConvention>14</rollConvention>" +
               "              </calculationPeriodFrequency>" +
               "            </calculationPeriodDates>" +
               "            <paymentDates>" +
               "              <calculationPeriodDatesReference href=\"floatingCalcPeriodDates\"/>" +
               "              <paymentFrequency><periodMultiplier>6</periodMultiplier><period>M</period></paymentFrequency>" +
               "              <payRelativeTo>CalculationPeriodEndDate</payRelativeTo>" +
               "              <paymentDatesAdjustments>" +
               "                <businessDayConvention>FOLLOWING</businessDayConvention>" +
               "                <businessCentersReference href=\"primaryBusinessCenters\"/>" +
               "              </paymentDatesAdjustments>" +
               "            </paymentDates>" +
               "            <resetDates id=\"resetDates\">" +
               "              <calculationPeriodDatesReference href=\"floatingCalcPeriodDates\"/>" +
               "              <resetRelativeTo>CalculationPeriodStartDate</resetRelativeTo>" +
               "              <fixingDates>" +
               "                <periodMultiplier>-2</periodMultiplier><period>D</period><dayType>Business</dayType>" +
               "                <businessDayConvention>NONE</businessDayConvention>" +
               "                <businessCenters><businessCenter>GBLO</businessCenter></businessCenters>" +
               "                <dateRelativeTo href=\"resetDates\"/>" +
               "              </fixingDates>" +
               "              <resetFrequency><periodMultiplier>6</periodMultiplier><period>M</period></resetFrequency>" +
               "              <resetDatesAdjustments>" +
               "                <businessDayConvention>FOLLOWING</businessDayConvention>" +
               "                <businessCentersReference href=\"primaryBusinessCenters\"/>" +
               "              </resetDatesAdjustments>" +
               "            </resetDates>" +
               "            <calculationPeriodAmount>" +
               "              <calculation>" +
               "                <notionalSchedule>" +
               "                  <notionalStepSchedule>" +
               "                    <initialValue>10000000.00</initialValue>" +
               "                    <currency currencyScheme=\"http://www.fpml.org/coding-scheme/external/iso4217\">EUR</currency>" +
               "                  </notionalStepSchedule>" +
               "                </notionalSchedule>" +
               "                <floatingRateCalculation>" +
               "                  <floatingRateIndex>EUR-LIBOR-BBA</floatingRateIndex>" +
               "                  <indexTenor><periodMultiplier>6</periodMultiplier><period>M</period></indexTenor>" +
               "                </floatingRateCalculation>" +
               "                <dayCountFraction>ACT/360</dayCountFraction>" +
               "              </calculation>" +
               "            </calculationPeriodAmount>" +
               "          </swapStream>" +
               "          <swapStream>" +
               "            <payerPartyReference href=\"party2\"/>" +
               "            <receiverPartyReference href=\"party1\"/>" +
               "            <calculationPeriodDates id=\"fixedCalcPeriodDates\">" +
               "              <effectiveDate>" +
               "                <unadjustedDate>2022-09-07</unadjustedDate>" +
               "                <dateAdjustments><businessDayConvention>NONE</businessDayConvention></dateAdjustments>" +
               "              </effectiveDate>" +
               "              <terminationDate>" +
               "                <unadjustedDate>2032-09-07</unadjustedDate>" +
               "                <dateAdjustments>" +
               "                  <businessDayConvention>FOLLOWING</businessDayConvention>" +
               "                  <businessCentersReference href=\"primaryBusinessCenters\"/>" +
               "                </dateAdjustments>" +
               "              </terminationDate>" +
               "              <calculationPeriodDatesAdjustments>" +
               "                <businessDayConvention>FOLLOWING</businessDayConvention>" +
               "                <businessCentersReference href=\"primaryBusinessCenters\"/>" +
               "              </calculationPeriodDatesAdjustments>" +
               "              <calculationPeriodFrequency>" +
               "                <periodMultiplier>1</periodMultiplier><period>Y</period><rollConvention>14</rollConvention>" +
               "              </calculationPeriodFrequency>" +
               "            </calculationPeriodDates>" +
               "            <paymentDates>" +
               "              <calculationPeriodDatesReference href=\"fixedCalcPeriodDates\"/>" +
               "              <paymentFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></paymentFrequency>" +
               "              <payRelativeTo>CalculationPeriodEndDate</payRelativeTo>" +
               "              <paymentDatesAdjustments>" +
               "                <businessDayConvention>MODFOLLOWING</businessDayConvention>" +
               "                <businessCentersReference href=\"primaryBusinessCenters\"/>" +
               "              </paymentDatesAdjustments>" +
               "            </paymentDates>" +
               "            <calculationPeriodAmount>" +
               "              <calculation>" +
               "                <notionalSchedule>" +
               "                  <notionalStepSchedule>" +
               "                    <initialValue>10000000.00</initialValue>" +
               "                    <currency currencyScheme=\"http://www.fpml.org/coding-scheme/external/iso4217\">EUR</currency>" +
               "                  </notionalStepSchedule>" +
               "                </notionalSchedule>" +
               "                <fixedRateSchedule><initialValue>0.0395</initialValue></fixedRateSchedule>" +
               "                <dayCountFraction>30E/360</dayCountFraction>" +
               "              </calculation>" +
               "            </calculationPeriodAmount>" +
               "          </swapStream>" +
               "        </swap>" +
               "      </trade>" +
               "      <party id=\"party1\"><partyId>PARTXXXX</partyId></party>" +
               "      <party id=\"party2\"><partyId>P2RTXXXX</partyId></party>" +
               "    </dataDocument>" +
               "  </underlying>" +
               "</underlyings>" +
               "</smartderivativecontract>";
    }

    /**
     * Factory method to create an alternative valid Smart Derivative Contract XML string.
     * This provides variety in test scenarios with different trade parameters.
     *
     * @return A valid SDC XML string with different values
     */
    @InterestingTestFactory
    public static String createAlternativeSmartDerivativeContractXml() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
               "<smartderivativecontract xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
               "xmlns=\"uri:sdc\" xsi:schemaLocation=\"uri:sdc smartderivativecontract.xsd\">" +
               "<dltTradeId>TEST-TRADE-002</dltTradeId>" +
               "<dltAddress>0x000000002</dltAddress>" +
               "<uniqueTradeIdentifier>UTI-TEST-002</uniqueTradeIdentifier>" +
               "<settlementCurrency>EUR</settlementCurrency>" +
               "<tradeType>SDCPledgedBalance</tradeType>" +
               "<valuation>" +
               "  <artefact>" +
               "    <groupId>net.finmath</groupId>" +
               "    <artifactId>finmath-smart-derivative-contract</artifactId>" +
               "    <version>0.1.8</version>" +
               "  </artefact>" +
               "</valuation>" +
               "<parties>" +
               "  <party>" +
               "    <name>Counterparty A</name>" +
               "    <id>partyA</id>" +
               "    <marginAccount><type>constant</type><value>20000.0</value></marginAccount>" +
               "    <penaltyFee><type>constant</type><value>200.0</value></penaltyFee>" +
               "    <address>0xAAAA</address>" +
               "  </party>" +
               "  <party>" +
               "    <name>Counterparty B</name>" +
               "    <id>partyB</id>" +
               "    <marginAccount><type>constant</type><value>20000.0</value></marginAccount>" +
               "    <penaltyFee><type>constant</type><value>200.0</value></penaltyFee>" +
               "    <address>0xBBBB</address>" +
               "  </party>" +
               "</parties>" +
               "<settlement>" +
               "  <settlementTime><type>daily</type><value>15:00</value></settlementTime>" +
               "  <marketdata>" +
               "    <provider>test</provider>" +
               "    <marketdataitems>" +
               "      <item><symbol>ESTRFIX1D</symbol><curve>ESTR</curve><type>Fixing</type><tenor>1D</tenor></item>" +
               "      <item><symbol>EUB6FIX6M</symbol><curve>Euribor6M</curve><type>Fixing</type><tenor>6M</tenor></item>" +
               "      <item><symbol>EUB6DEP6M</symbol><curve>Euribor6M</curve><type>Deposit</type><tenor>6M</tenor></item>" +
               "      <item><symbol>EUB6SWP5Y</symbol><curve>Euribor6M</curve><type>Swap-Rate</type><tenor>5Y</tenor></item>" +
               "      <item><symbol>ESTRSWP5Y</symbol><curve>ESTR</curve><type>Swap-Rate</type><tenor>5Y</tenor></item>" +
               "    </marketdataitems>" +
               "  </marketdata>" +
               "</settlement>" +
               "<receiverPartyID>partyA</receiverPartyID>" +
               "<underlyings>" +
               "  <underlying>" +
               "    <dataDocument xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
               "xmlns=\"http://www.fpml.org/FpML-5/confirmation\" fpmlVersion=\"5-9\" " +
               "xsi:schemaLocation=\"http://www.fpml.org/FpML-5/confirmation ../../fpml-main-5-9.xsd http://www.w3.org/2000/09/xmldsig# ../../xmldsig-core-schema.xsd\">" +
               "      <trade>" +
               "        <tradeHeader>" +
               "          <partyTradeIdentifier>" +
               "            <partyReference href=\"partyA\"/>" +
               "            <tradeId tradeIdScheme=\"http://www.test.com/swaps/trade-id\">TID-A</tradeId>" +
               "          </partyTradeIdentifier>" +
               "          <partyTradeIdentifier>" +
               "            <partyReference href=\"partyB\"/>" +
               "            <tradeId tradeIdScheme=\"http://www.test.com/swaps/trade-id\">TID-B</tradeId>" +
               "          </partyTradeIdentifier>" +
               "          <tradeDate>2023-01-15</tradeDate>" +
               "        </tradeHeader>" +
               "        <swap>" +
               "          <swapStream>" +
               "            <payerPartyReference href=\"partyA\"/>" +
               "            <receiverPartyReference href=\"partyB\"/>" +
               "            <calculationPeriodDates id=\"floatingCalcPeriodDates\">" +
               "              <effectiveDate>" +
               "                <unadjustedDate>2023-01-20</unadjustedDate>" +
               "                <dateAdjustments><businessDayConvention>NONE</businessDayConvention></dateAdjustments>" +
               "              </effectiveDate>" +
               "              <terminationDate>" +
               "                <unadjustedDate>2028-01-20</unadjustedDate>" +
               "                <dateAdjustments>" +
               "                  <businessDayConvention>MODFOLLOWING</businessDayConvention>" +
               "                  <businessCenters id=\"primaryBusinessCenters\"><businessCenter>DEFR</businessCenter></businessCenters>" +
               "                </dateAdjustments>" +
               "              </terminationDate>" +
               "              <calculationPeriodDatesAdjustments>" +
               "                <businessDayConvention>MODFOLLOWING</businessDayConvention>" +
               "                <businessCentersReference href=\"primaryBusinessCenters\"/>" +
               "              </calculationPeriodDatesAdjustments>" +
               "              <calculationPeriodFrequency>" +
               "                <periodMultiplier>6</periodMultiplier><period>M</period><rollConvention>20</rollConvention>" +
               "              </calculationPeriodFrequency>" +
               "            </calculationPeriodDates>" +
               "            <paymentDates>" +
               "              <calculationPeriodDatesReference href=\"floatingCalcPeriodDates\"/>" +
               "              <paymentFrequency><periodMultiplier>6</periodMultiplier><period>M</period></paymentFrequency>" +
               "              <payRelativeTo>CalculationPeriodEndDate</payRelativeTo>" +
               "              <paymentDatesAdjustments>" +
               "                <businessDayConvention>FOLLOWING</businessDayConvention>" +
               "                <businessCentersReference href=\"primaryBusinessCenters\"/>" +
               "              </paymentDatesAdjustments>" +
               "            </paymentDates>" +
               "            <resetDates id=\"resetDates\">" +
               "              <calculationPeriodDatesReference href=\"floatingCalcPeriodDates\"/>" +
               "              <resetRelativeTo>CalculationPeriodStartDate</resetRelativeTo>" +
               "              <fixingDates>" +
               "                <periodMultiplier>-2</periodMultiplier><period>D</period><dayType>Business</dayType>" +
               "                <businessDayConvention>NONE</businessDayConvention>" +
               "                <businessCenters><businessCenter>GBLO</businessCenter></businessCenters>" +
               "                <dateRelativeTo href=\"resetDates\"/>" +
               "              </fixingDates>" +
               "              <resetFrequency><periodMultiplier>6</periodMultiplier><period>M</period></resetFrequency>" +
               "              <resetDatesAdjustments>" +
               "                <businessDayConvention>FOLLOWING</businessDayConvention>" +
               "                <businessCentersReference href=\"primaryBusinessCenters\"/>" +
               "              </resetDatesAdjustments>" +
               "            </resetDates>" +
               "            <calculationPeriodAmount>" +
               "              <calculation>" +
               "                <notionalSchedule>" +
               "                  <notionalStepSchedule>" +
               "                    <initialValue>5000000.00</initialValue>" +
               "                    <currency currencyScheme=\"http://www.fpml.org/coding-scheme/external/iso4217\">EUR</currency>" +
               "                  </notionalStepSchedule>" +
               "                </notionalSchedule>" +
               "                <floatingRateCalculation>" +
               "                  <floatingRateIndex>EUR-EURIBOR-Reuters</floatingRateIndex>" +
               "                  <indexTenor><periodMultiplier>6</periodMultiplier><period>M</period></indexTenor>" +
               "                </floatingRateCalculation>" +
               "                <dayCountFraction>ACT/360</dayCountFraction>" +
               "              </calculation>" +
               "            </calculationPeriodAmount>" +
               "          </swapStream>" +
               "          <swapStream>" +
               "            <payerPartyReference href=\"partyB\"/>" +
               "            <receiverPartyReference href=\"partyA\"/>" +
               "            <calculationPeriodDates id=\"fixedCalcPeriodDates\">" +
               "              <effectiveDate>" +
               "                <unadjustedDate>2023-01-20</unadjustedDate>" +
               "                <dateAdjustments><businessDayConvention>NONE</businessDayConvention></dateAdjustments>" +
               "              </effectiveDate>" +
               "              <terminationDate>" +
               "                <unadjustedDate>2028-01-20</unadjustedDate>" +
               "                <dateAdjustments>" +
               "                  <businessDayConvention>FOLLOWING</businessDayConvention>" +
               "                  <businessCentersReference href=\"primaryBusinessCenters\"/>" +
               "                </dateAdjustments>" +
               "              </terminationDate>" +
               "              <calculationPeriodDatesAdjustments>" +
               "                <businessDayConvention>FOLLOWING</businessDayConvention>" +
               "                <businessCentersReference href=\"primaryBusinessCenters\"/>" +
               "              </calculationPeriodDatesAdjustments>" +
               "              <calculationPeriodFrequency>" +
               "                <periodMultiplier>1</periodMultiplier><period>Y</period><rollConvention>20</rollConvention>" +
               "              </calculationPeriodFrequency>" +
               "            </calculationPeriodDates>" +
               "            <paymentDates>" +
               "              <calculationPeriodDatesReference href=\"fixedCalcPeriodDates\"/>" +
               "              <paymentFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></paymentFrequency>" +
               "              <payRelativeTo>CalculationPeriodEndDate</payRelativeTo>" +
               "              <paymentDatesAdjustments>" +
               "                <businessDayConvention>MODFOLLOWING</businessDayConvention>" +
               "                <businessCentersReference href=\"primaryBusinessCenters\"/>" +
               "              </paymentDatesAdjustments>" +
               "            </paymentDates>" +
               "            <calculationPeriodAmount>" +
               "              <calculation>" +
               "                <notionalSchedule>" +
               "                  <notionalStepSchedule>" +
               "                    <initialValue>5000000.00</initialValue>" +
               "                    <currency currencyScheme=\"http://www.fpml.org/coding-scheme/external/iso4217\">EUR</currency>" +
               "                  </notionalStepSchedule>" +
               "                </notionalSchedule>" +
               "                <fixedRateSchedule><initialValue>0.025</initialValue></fixedRateSchedule>" +
               "                <dayCountFraction>30E/360</dayCountFraction>" +
               "              </calculation>" +
               "            </calculationPeriodAmount>" +
               "          </swapStream>" +
               "        </swap>" +
               "      </trade>" +
               "      <party id=\"partyA\"><partyId>AAAA1234</partyId></party>" +
               "      <party id=\"partyB\"><partyId>BBBB5678</partyId></party>" +
               "    </dataDocument>" +
               "  </underlying>" +
               "</underlyings>" +
               "</smartderivativecontract>";
    }
}
