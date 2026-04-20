package net.finmath.smartcontract.valuation.implementation;

import net.finmath.smartcontract.model.MarginResult;
import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.model.ValueResult;
import net.finmath.smartcontract.product.xml.SDCXMLParser;
import net.finmath.smartcontract.valuation.client.ValuationClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MarginCalculatorGeneratedTest {

	private String marketDataStartXml;
	private String marketDataEndXml;
	private String productXml;

	@BeforeAll
	void setup() throws Exception {
		marketDataStartXml = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net/finmath/smartcontract/valuation/client/md_testset1.xml").readAllBytes(), StandardCharsets.UTF_8);
		marketDataEndXml = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net/finmath/smartcontract/valuation/client/md_testset2.xml").readAllBytes(), StandardCharsets.UTF_8);
		productXml = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract.xml").readAllBytes(), StandardCharsets.UTF_8);
	}

	@Test
	void testConstructorWithCustomRounding() throws Exception {
		DoubleUnaryOperator customRounding = x -> Math.round(x * 100) / 100.0;
		MarginCalculator calculator = new MarginCalculator(customRounding);
		assertNotNull(calculator);

		MarginResult result = calculator.getValue(marketDataStartXml, marketDataEndXml, productXml);
		assertNotNull(result);
		assertNotNull(result.getValue());
	}

	@Test
	void testDefaultConstructor() throws Exception {
		MarginCalculator calculator = new MarginCalculator();
		assertNotNull(calculator);

		MarginResult result = calculator.getValue(marketDataStartXml, marketDataEndXml, productXml);
		assertNotNull(result);
	}

	@Test
	void testGetValuesReturnsMap() throws Exception {
		MarginCalculator calculator = new MarginCalculator();
		Map<String, BigDecimal> values = calculator.getValues(marketDataStartXml, marketDataEndXml, productXml);

		assertNotNull(values);
		assertTrue(values.containsKey("value"), "Result should contain 'value' key");
		assertTrue(values.containsKey("value.receiverLeg"), "Result should contain 'value.receiverLeg' key");
		assertTrue(values.containsKey("value.payerLeg"), "Result should contain 'value.payerLeg' key");
		assertEquals(9932.71, values.get("value").doubleValue(), 0.005, "Margin value");
	}

	@Test
	void testGetValueStringStringString() throws Exception {
		MarginCalculator calculator = new MarginCalculator();
		MarginResult result = calculator.getValue(marketDataStartXml, marketDataEndXml, productXml);

		assertNotNull(result);
		assertNotNull(result.getValue());
		assertNotNull(result.getCurrency());
		assertEquals("EUR", result.getCurrency());
		assertNotNull(result.getValuationDate());
		assertEquals(9932.71, result.getValue().doubleValue(), 0.005, "Margin");
	}

	@Test
	void testGetValuesWithMarketDataList() throws Exception {
		MarketDataList marketDataStart = SDCXMLParser.unmarshalXml(marketDataStartXml, MarketDataList.class);
		MarketDataList marketDataEnd = SDCXMLParser.unmarshalXml(marketDataEndXml, MarketDataList.class);

		MarginCalculator calculator = new MarginCalculator();
		Map<String, BigDecimal> values = calculator.getValues(marketDataStart, marketDataEnd, productXml);

		assertNotNull(values);
		assertTrue(values.containsKey("value"), "Result should contain 'value' key");
		assertTrue(values.containsKey("value.receiverLeg"), "Result should contain 'value.receiverLeg' key");
		assertTrue(values.containsKey("value.payerLeg"), "Result should contain 'value.payerLeg' key");
	}

	@Test
	void testGetValueWithMarketDataLists() throws Exception {
		MarketDataList marketDataStart = SDCXMLParser.unmarshalXml(marketDataStartXml, MarketDataList.class);
		MarketDataList marketDataEnd = SDCXMLParser.unmarshalXml(marketDataEndXml, MarketDataList.class);

		MarginCalculator calculator = new MarginCalculator();
		MarginResult result = calculator.getValue(marketDataStart, marketDataEnd, productXml);

		assertNotNull(result);
		assertNotNull(result.getCurrency());
		assertEquals("EUR", result.getCurrency());
		assertNotNull(result.getValuationDate());
	}

	@Test
	void testGetValueWithSingleMarketData() throws Exception {
		MarginCalculator calculator = new MarginCalculator();
		ValueResult result = calculator.getValue(marketDataStartXml, productXml);

		assertNotNull(result);
		assertNotNull(result.getValue());
		assertNotNull(result.getCurrency());
		assertEquals("EUR", result.getCurrency());
		assertNotNull(result.getValuationDate());
	}

	@Test
	void testGetValueWithSingleMarketDataList() throws Exception {
		MarketDataList marketData = SDCXMLParser.unmarshalXml(marketDataStartXml, MarketDataList.class);

		MarginCalculator calculator = new MarginCalculator();
		ValueResult result = calculator.getValue(marketData, productXml);

		assertNotNull(result);
		assertNotNull(result.getValue());
		assertNotNull(result.getCurrency());
		assertEquals("EUR", result.getCurrency());
		assertNotNull(result.getValuationDate());
	}

	@Test
	void testGetValueAtEvaluationTime() throws Exception {
		String marketDataWithFixings = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net/finmath/smartcontract/valuation/client/md_testset_with_fixings.xml").readAllBytes(), StandardCharsets.UTF_8);

		MarginCalculator calculator = new MarginCalculator();
		LocalDateTime evaluationTime = LocalDateTime.of(2023, 1, 31, 14, 35, 23);
		ValueResult result = calculator.getValueAtEvaluationTime(marketDataWithFixings, productXml, evaluationTime);

		assertNotNull(result);
		assertNotNull(result.getValue());
		assertNotNull(result.getCurrency());
		assertNotNull(result.getValuationDate());
		assertEquals(evaluationTime.toString(), result.getValuationDate());
	}
}
