package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import com.diffblue.cover.annotations.InterestingTestFactory;

/**
 * Custom base class for Diffblue Cover generated tests for CalibrationParserDataItems.
 * Provides factory methods to create test instances with valid market data XML.
 */
public abstract class CalibrationParserDataItemsDiffblueBase {

    /**
     * Factory method to create a valid market data XML string for CalibrationParserDataItems methods.
     * This prevents SDC_JAXB_ERROR when testing getCalibrationDataSetFromXML method.
     * The XML includes all required elements to pass JAXB unmarshalling and parsing.
     *
     * @return A valid market data XML string
     */
    @InterestingTestFactory
    public static String createValidMarketDataXml() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
               "<marketDataList>\n" +
               "    <requestTimeStamp>20220905-170000</requestTimeStamp>\n" +
               "    <item>\n" +
               "        <id>ESTRFIX1D</id>\n" +
               "        <value>0.001</value>\n" +
               "        <timeStamp>20220905-170000</timeStamp>\n" +
               "    </item>\n" +
               "    <item>\n" +
               "        <id>EUB6FIX6M</id>\n" +
               "        <value>0.015</value>\n" +
               "        <timeStamp>20220905-170000</timeStamp>\n" +
               "    </item>\n" +
               "    <item>\n" +
               "        <id>EUB6DEP6M</id>\n" +
               "        <value>0.018</value>\n" +
               "        <timeStamp>20220905-170000</timeStamp>\n" +
               "    </item>\n" +
               "    <item>\n" +
               "        <id>EUB6SWP2Y</id>\n" +
               "        <value>0.025</value>\n" +
               "        <timeStamp>20220905-170000</timeStamp>\n" +
               "    </item>\n" +
               "    <item>\n" +
               "        <id>EUB6SWP3Y</id>\n" +
               "        <value>0.028</value>\n" +
               "        <timeStamp>20220905-170000</timeStamp>\n" +
               "    </item>\n" +
               "    <item>\n" +
               "        <id>EUB6SWP5Y</id>\n" +
               "        <value>0.032</value>\n" +
               "        <timeStamp>20220905-170000</timeStamp>\n" +
               "    </item>\n" +
               "    <item>\n" +
               "        <id>EUB6SWP10Y</id>\n" +
               "        <value>0.035</value>\n" +
               "        <timeStamp>20220905-170000</timeStamp>\n" +
               "    </item>\n" +
               "    <item>\n" +
               "        <id>ESTRSWP1Y</id>\n" +
               "        <value>0.020</value>\n" +
               "        <timeStamp>20220905-170000</timeStamp>\n" +
               "    </item>\n" +
               "    <item>\n" +
               "        <id>ESTRSWP2Y</id>\n" +
               "        <value>0.022</value>\n" +
               "        <timeStamp>20220905-170000</timeStamp>\n" +
               "    </item>\n" +
               "    <item>\n" +
               "        <id>ESTRSWP3Y</id>\n" +
               "        <value>0.024</value>\n" +
               "        <timeStamp>20220905-170000</timeStamp>\n" +
               "    </item>\n" +
               "    <item>\n" +
               "        <id>ESTRSWP5Y</id>\n" +
               "        <value>0.027</value>\n" +
               "        <timeStamp>20220905-170000</timeStamp>\n" +
               "    </item>\n" +
               "    <item>\n" +
               "        <id>ESTRSWP10Y</id>\n" +
               "        <value>0.030</value>\n" +
               "        <timeStamp>20220905-170000</timeStamp>\n" +
               "    </item>\n" +
               "</marketDataList>";
    }

    /**
     * Factory method to create an alternative valid market data XML string.
     * This provides variety in test scenarios with different market data values.
     *
     * @return A valid market data XML string with different values
     */
    @InterestingTestFactory
    public static String createAlternativeMarketDataXml() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
               "<marketDataList>\n" +
               "    <requestTimeStamp>20230115-150000</requestTimeStamp>\n" +
               "    <item>\n" +
               "        <id>ESTRFIX1D</id>\n" +
               "        <value>0.002</value>\n" +
               "        <timeStamp>20230115-150000</timeStamp>\n" +
               "    </item>\n" +
               "    <item>\n" +
               "        <id>EUB6FIX6M</id>\n" +
               "        <value>0.020</value>\n" +
               "        <timeStamp>20230115-150000</timeStamp>\n" +
               "    </item>\n" +
               "    <item>\n" +
               "        <id>EUB6DEP6M</id>\n" +
               "        <value>0.022</value>\n" +
               "        <timeStamp>20230115-150000</timeStamp>\n" +
               "    </item>\n" +
               "    <item>\n" +
               "        <id>EUB6SWP5Y</id>\n" +
               "        <value>0.035</value>\n" +
               "        <timeStamp>20230115-150000</timeStamp>\n" +
               "    </item>\n" +
               "    <item>\n" +
               "        <id>ESTRSWP5Y</id>\n" +
               "        <value>0.030</value>\n" +
               "        <timeStamp>20230115-150000</timeStamp>\n" +
               "    </item>\n" +
               "</marketDataList>";
    }
}
