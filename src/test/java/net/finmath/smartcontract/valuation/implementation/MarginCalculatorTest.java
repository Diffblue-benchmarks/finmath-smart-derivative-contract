package net.finmath.smartcontract.valuation.implementation;

import net.finmath.smartcontract.model.MarginResult;
import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.model.ValueResult;
import net.finmath.smartcontract.product.xml.SDCXMLParser;
import net.finmath.smartcontract.valuation.client.ValuationClient;
import net.finmath.smartcontract.valuation.marketdata.data.MarketDataPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;

class MarginCalculatorTest {

	@Test
	void testMargin() throws Exception {
		final String marketDataStart = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net/finmath/smartcontract/valuation/client/md_testset1.xml").readAllBytes(), StandardCharsets.UTF_8);
		final String marketDataEnd = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net/finmath/smartcontract/valuation/client/md_testset2.xml").readAllBytes(), StandardCharsets.UTF_8);
		final String product = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract.xml").readAllBytes(), StandardCharsets.UTF_8);

		MarginCalculator marginCalculator = new MarginCalculator();
		MarginResult valuationResult = marginCalculator.getValue(marketDataStart, marketDataEnd, product);

		double value = valuationResult.getValue().doubleValue();

		Assertions.assertEquals(9908.52, value, 0.005, "Margin");
		System.out.println(valuationResult);
	}

	@Test
	void testValue() throws Exception {
		final String marketData = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net/finmath/smartcontract/valuation/client/md_testset1.xml").readAllBytes(), StandardCharsets.UTF_8);
		final String product = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract.xml").readAllBytes(), StandardCharsets.UTF_8);

		MarginCalculator marginCalculator = new MarginCalculator();
		ValueResult valuationResult = marginCalculator.getValue(marketData, product);

		double value = valuationResult.getValue().doubleValue();

		Assertions.assertEquals(926403.97, value, 0.005, "Valuation");
		System.out.println(valuationResult);
	}

	@Test
	void testConstructorWithCustomRounding() throws Exception {
		DoubleUnaryOperator customRounding = x -> Math.round(x * 100) / 100.0;
		MarginCalculator marginCalculator = new MarginCalculator(customRounding);

		final String marketData = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net/finmath/smartcontract/valuation/client/md_testset1.xml").readAllBytes(), StandardCharsets.UTF_8);
		final String product = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract.xml").readAllBytes(), StandardCharsets.UTF_8);

		ValueResult result = marginCalculator.getValue(marketData, product);

		Assertions.assertNotNull(result);
		Assertions.assertNotNull(result.getValue());
	}

	@Test
	void testDefaultConstructor() throws Exception {
		MarginCalculator marginCalculator = new MarginCalculator();

		final String marketData = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net/finmath/smartcontract/valuation/client/md_testset1.xml").readAllBytes(), StandardCharsets.UTF_8);
		final String product = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract.xml").readAllBytes(), StandardCharsets.UTF_8);

		ValueResult result = marginCalculator.getValue(marketData, product);

		Assertions.assertNotNull(result);
		Assertions.assertNotNull(result.getValue());
	}

	@Test
	void testGetValuesWithStringMarketData() throws Exception {
		final String marketDataStart = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net/finmath/smartcontract/valuation/client/md_testset1.xml").readAllBytes(), StandardCharsets.UTF_8);
		final String marketDataEnd = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net/finmath/smartcontract/valuation/client/md_testset2.xml").readAllBytes(), StandardCharsets.UTF_8);
		final String product = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract.xml").readAllBytes(), StandardCharsets.UTF_8);

		MarginCalculator marginCalculator = new MarginCalculator();
		Map<String, BigDecimal> values = marginCalculator.getValues(marketDataStart, marketDataEnd, product);

		Assertions.assertNotNull(values);
		Assertions.assertTrue(values.containsKey("value"));
		Assertions.assertNotNull(values.get("value"));
	}

	@Test
	void testGetValueAtEvaluationTime() throws Exception {
		final String marketData = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net/finmath/smartcontract/valuation/client/md_testset1.xml").readAllBytes(), StandardCharsets.UTF_8);
		final String product = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract.xml").readAllBytes(), StandardCharsets.UTF_8);

		LocalDateTime evaluationTime = LocalDateTime.of(2023, 1, 31, 14, 35, 23);

		MarginCalculator marginCalculator = new MarginCalculator();
		ValueResult result = marginCalculator.getValueAtEvaluationTime(marketData, product, evaluationTime);

		Assertions.assertNotNull(result);
		Assertions.assertNotNull(result.getValue());
		Assertions.assertEquals("EUR", result.getCurrency());
		Assertions.assertNotNull(result.getValuationDate());
	}

	@Test
	void testGetValueWithMarketDataList() throws Exception {
		final String marketDataXml = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net/finmath/smartcontract/valuation/client/md_testset1.xml").readAllBytes(), StandardCharsets.UTF_8);
		final String product = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract.xml").readAllBytes(), StandardCharsets.UTF_8);

		MarketDataList marketDataList = SDCXMLParser.unmarshalXml(marketDataXml, MarketDataList.class);

		MarginCalculator marginCalculator = new MarginCalculator();
		ValueResult result = marginCalculator.getValue(marketDataList, product);

		Assertions.assertNotNull(result);
		Assertions.assertNotNull(result.getValue());
		Assertions.assertEquals("EUR", result.getCurrency());
		Assertions.assertNotNull(result.getValuationDate());
		Assertions.assertEquals(marketDataList.getRequestTimeStamp().toString(), result.getValuationDate());
	}

	@Test
	void testGetValuesWithMarketDataList() throws Exception {
		final String marketDataXmlStart = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net/finmath/smartcontract/valuation/client/md_testset1.xml").readAllBytes(), StandardCharsets.UTF_8);
		final String marketDataXmlEnd = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net/finmath/smartcontract/valuation/client/md_testset2.xml").readAllBytes(), StandardCharsets.UTF_8);
		final String product = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract.xml").readAllBytes(), StandardCharsets.UTF_8);

		MarketDataList marketDataListStart = SDCXMLParser.unmarshalXml(marketDataXmlStart, MarketDataList.class);
		MarketDataList marketDataListEnd = SDCXMLParser.unmarshalXml(marketDataXmlEnd, MarketDataList.class);

		MarginCalculator marginCalculator = new MarginCalculator();
		Map<String, BigDecimal> values = marginCalculator.getValues(marketDataListStart, marketDataListEnd, product);

		Assertions.assertNotNull(values);
		Assertions.assertTrue(values.containsKey("value"));
		Assertions.assertNotNull(values.get("value"));
		Assertions.assertTrue(values.containsKey("value.receiverLeg"));
		Assertions.assertTrue(values.containsKey("value.payerLeg"));
	}


	private MarketDataList createTestMarketDataList(LocalDateTime timestamp) {
		MarketDataList marketDataList = new MarketDataList();
		marketDataList.setRequestTimeStamp(timestamp);

		marketDataList.add(new MarketDataPoint("ESTRSWP7D", 0.019761, timestamp));
		marketDataList.add(new MarketDataPoint("ESTRSWP14D", 0.021920, timestamp));
		marketDataList.add(new MarketDataPoint("ESTRSWP21D", 0.022648, timestamp));
		marketDataList.add(new MarketDataPoint("ESTRSWP1M", 0.023009, timestamp));
		marketDataList.add(new MarketDataPoint("ESTRSWP2M", 0.024499, timestamp));
		marketDataList.add(new MarketDataPoint("ESTRSWP3M", 0.025866, timestamp));
		marketDataList.add(new MarketDataPoint("ESTRSWP4M", 0.027169, timestamp));
		marketDataList.add(new MarketDataPoint("ESTRSWP5M", 0.028264, timestamp));
		marketDataList.add(new MarketDataPoint("ESTRSWP6M", 0.029157, timestamp));
		marketDataList.add(new MarketDataPoint("EUB6DEP6M", 0.033635, timestamp));
		marketDataList.add(new MarketDataPoint("ESTRSWP9M", 0.030614, timestamp));
		marketDataList.add(new MarketDataPoint("ESTRSWP1Y", 0.031531, timestamp));
		marketDataList.add(new MarketDataPoint("ESTRSWP15M", 0.032675, timestamp));
		marketDataList.add(new MarketDataPoint("ESTRSWP18M", 0.033354, timestamp));
		marketDataList.add(new MarketDataPoint("ESTRSWP21M", 0.033710, timestamp));
		marketDataList.add(new MarketDataPoint("ESTRSWP2Y", 0.033930, timestamp));
		marketDataList.add(new MarketDataPoint("ESTRSWP3Y", 0.033244, timestamp));
		marketDataList.add(new MarketDataPoint("ESTRSWP4Y", 0.032048, timestamp));
		marketDataList.add(new MarketDataPoint("ESTRSWP5Y", 0.030662, timestamp));
		marketDataList.add(new MarketDataPoint("ESTRSWP6Y", 0.029362, timestamp));
		marketDataList.add(new MarketDataPoint("ESTRSWP7Y", 0.028253, timestamp));
		marketDataList.add(new MarketDataPoint("ESTRSWP8Y", 0.027313, timestamp));
		marketDataList.add(new MarketDataPoint("ESTRSWP9Y", 0.026512, timestamp));
		marketDataList.add(new MarketDataPoint("ESTRSWP10Y", 0.025825, timestamp));
		marketDataList.add(new MarketDataPoint("ESTRSWP12Y", 0.024699, timestamp));
		marketDataList.add(new MarketDataPoint("ESTRSWP15Y", 0.023447, timestamp));
		marketDataList.add(new MarketDataPoint("ESTRSWP20Y", 0.021857, timestamp));
		marketDataList.add(new MarketDataPoint("ESTRSWP25Y", 0.020420, timestamp));
		marketDataList.add(new MarketDataPoint("ESTRSWP30Y", 0.019260, timestamp));

		return marketDataList;
	}

	@Test
	void testGetValuesWithJsonMarketData() throws Exception {
		final String product = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract.xml").readAllBytes(), StandardCharsets.UTF_8);

		final String marketDataJsonStart = "{\n" +
				"  \"20230501-170000\": {\n" +
				"    \"Quotes\": {\n" +
				"      \"ESTR\": {\n" +
				"        \"Swap-Rate\": {\n" +
				"          \"7D\": 0.019761,\n" +
				"          \"14D\": 0.021920,\n" +
				"          \"21D\": 0.022648,\n" +
				"          \"1M\": 0.023009,\n" +
				"          \"2M\": 0.024499,\n" +
				"          \"3M\": 0.025866,\n" +
				"          \"4M\": 0.027169,\n" +
				"          \"5M\": 0.028264,\n" +
				"          \"6M\": 0.029157,\n" +
				"          \"9M\": 0.030614,\n" +
				"          \"1Y\": 0.031531,\n" +
				"          \"15M\": 0.032675,\n" +
				"          \"18M\": 0.033354,\n" +
				"          \"21M\": 0.033710,\n" +
				"          \"2Y\": 0.033930,\n" +
				"          \"3Y\": 0.033244,\n" +
				"          \"4Y\": 0.032048,\n" +
				"          \"5Y\": 0.030662,\n" +
				"          \"6Y\": 0.029362,\n" +
				"          \"7Y\": 0.028253,\n" +
				"          \"8Y\": 0.027313,\n" +
				"          \"9Y\": 0.026512,\n" +
				"          \"10Y\": 0.025825,\n" +
				"          \"12Y\": 0.024699,\n" +
				"          \"15Y\": 0.023447,\n" +
				"          \"20Y\": 0.021857,\n" +
				"          \"25Y\": 0.020420,\n" +
				"          \"30Y\": 0.019260\n" +
				"        }\n" +
				"      },\n" +
				"      \"Euribor6M\": {\n" +
				"        \"Deposit-Rate\": {\n" +
				"          \"6M\": 0.033635\n" +
				"        }\n" +
				"      }\n" +
				"    }\n" +
				"  }\n" +
				"}";

		final String marketDataJsonEnd = "{\n" +
				"  \"20230601-170000\": {\n" +
				"    \"Quotes\": {\n" +
				"      \"ESTR\": {\n" +
				"        \"Swap-Rate\": {\n" +
				"          \"7D\": 0.020761,\n" +
				"          \"14D\": 0.022920,\n" +
				"          \"21D\": 0.023648,\n" +
				"          \"1M\": 0.024009,\n" +
				"          \"2M\": 0.025499,\n" +
				"          \"3M\": 0.026866,\n" +
				"          \"4M\": 0.028169,\n" +
				"          \"5M\": 0.029264,\n" +
				"          \"6M\": 0.030157,\n" +
				"          \"9M\": 0.031614,\n" +
				"          \"1Y\": 0.032531,\n" +
				"          \"15M\": 0.033675,\n" +
				"          \"18M\": 0.034354,\n" +
				"          \"21M\": 0.034710,\n" +
				"          \"2Y\": 0.034930,\n" +
				"          \"3Y\": 0.034244,\n" +
				"          \"4Y\": 0.033048,\n" +
				"          \"5Y\": 0.031662,\n" +
				"          \"6Y\": 0.030362,\n" +
				"          \"7Y\": 0.029253,\n" +
				"          \"8Y\": 0.028313,\n" +
				"          \"9Y\": 0.027512,\n" +
				"          \"10Y\": 0.026825,\n" +
				"          \"12Y\": 0.025699,\n" +
				"          \"15Y\": 0.024447,\n" +
				"          \"20Y\": 0.022857,\n" +
				"          \"25Y\": 0.021420,\n" +
				"          \"30Y\": 0.020260\n" +
				"        }\n" +
				"      },\n" +
				"      \"Euribor6M\": {\n" +
				"        \"Deposit-Rate\": {\n" +
				"          \"6M\": 0.034635\n" +
				"        }\n" +
				"      }\n" +
				"    }\n" +
				"  }\n" +
				"}";

		MarginCalculator marginCalculator = new MarginCalculator();

		Assertions.assertThrows(NullPointerException.class, () -> {
			marginCalculator.getValues(marketDataJsonStart, marketDataJsonEnd, product);
		});
	}

}
