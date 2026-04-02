package net.finmath.smartcontract.valuation.implementation;

import net.finmath.smartcontract.model.MarginResult;
import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.model.ValueResult;
import net.finmath.smartcontract.product.xml.SDCXMLParser;
import net.finmath.smartcontract.valuation.client.ValuationClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Map;

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
	void testGetValueReturnsMarginResult() throws Exception {
		final String marketDataStart = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net/finmath/smartcontract/valuation/client/md_testset1.xml").readAllBytes(), StandardCharsets.UTF_8);
		final String marketDataEnd = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net/finmath/smartcontract/valuation/client/md_testset2.xml").readAllBytes(), StandardCharsets.UTF_8);
		final String product = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract.xml").readAllBytes(), StandardCharsets.UTF_8);

		MarginCalculator marginCalculator = new MarginCalculator();
		MarginResult result = marginCalculator.getValue(marketDataStart, marketDataEnd, product);

		Assertions.assertNotNull(result);
		Assertions.assertEquals("EUR", result.getCurrency());
		Assertions.assertNotNull(result.getValuationDate());
		System.out.println(result);
	}

	@Test
	void testGetValuesWithMarketDataList() throws Exception {
		final String marketDataStartXml = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net/finmath/smartcontract/valuation/client/md_testset1.xml").readAllBytes(), StandardCharsets.UTF_8);
		final String marketDataEndXml = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net/finmath/smartcontract/valuation/client/md_testset2.xml").readAllBytes(), StandardCharsets.UTF_8);
		final String product = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract.xml").readAllBytes(), StandardCharsets.UTF_8);

		MarketDataList marketDataStart = SDCXMLParser.unmarshalXml(marketDataStartXml, MarketDataList.class);
		MarketDataList marketDataEnd = SDCXMLParser.unmarshalXml(marketDataEndXml, MarketDataList.class);

		MarginCalculator marginCalculator = new MarginCalculator();
		Map<String, BigDecimal> values = marginCalculator.getValues(marketDataStart, marketDataEnd, product);

		Assertions.assertNotNull(values);
		Assertions.assertTrue(values.containsKey("value"));
		System.out.println(values);
	}

	@Test
	void testGetValueWithMarketDataLists() throws Exception {
		final String marketDataStartXml = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net/finmath/smartcontract/valuation/client/md_testset1.xml").readAllBytes(), StandardCharsets.UTF_8);
		final String marketDataEndXml = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net/finmath/smartcontract/valuation/client/md_testset2.xml").readAllBytes(), StandardCharsets.UTF_8);
		final String product = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract.xml").readAllBytes(), StandardCharsets.UTF_8);

		MarketDataList marketDataStart = SDCXMLParser.unmarshalXml(marketDataStartXml, MarketDataList.class);
		MarketDataList marketDataEnd = SDCXMLParser.unmarshalXml(marketDataEndXml, MarketDataList.class);

		MarginCalculator marginCalculator = new MarginCalculator();
		MarginResult result = marginCalculator.getValue(marketDataStart, marketDataEnd, product);

		Assertions.assertNotNull(result);
		Assertions.assertEquals("EUR", result.getCurrency());
		Assertions.assertNotNull(result.getValuationDate());
		System.out.println(result);
	}

	@Test
	void testGetValueWithSingleMarketDataList() throws Exception {
		final String marketDataXml = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net/finmath/smartcontract/valuation/client/md_testset1.xml").readAllBytes(), StandardCharsets.UTF_8);
		final String product = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract.xml").readAllBytes(), StandardCharsets.UTF_8);

		MarketDataList marketData = SDCXMLParser.unmarshalXml(marketDataXml, MarketDataList.class);

		MarginCalculator marginCalculator = new MarginCalculator();
		ValueResult result = marginCalculator.getValue(marketData, product);

		Assertions.assertNotNull(result);
		Assertions.assertEquals("EUR", result.getCurrency());
		Assertions.assertNotNull(result.getValuationDate());
		System.out.println(result);
	}

}
