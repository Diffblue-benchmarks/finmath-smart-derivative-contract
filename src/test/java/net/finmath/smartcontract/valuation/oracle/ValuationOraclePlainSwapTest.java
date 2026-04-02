package net.finmath.smartcontract.valuation.oracle;

import net.finmath.marketdata.products.Swap;
import net.finmath.smartcontract.model.SDCException;
import net.finmath.smartcontract.product.IRSwapGenerator;
import net.finmath.smartcontract.product.SmartDerivativeContractDescriptor;
import net.finmath.smartcontract.product.xml.SDCXMLParser;
import net.finmath.smartcontract.valuation.client.ValuationClient;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataset;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationParserDataItems;
import net.finmath.smartcontract.valuation.oracle.interestrates.ValuationOraclePlainSwap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ValuationOraclePlainSwapTest {

	private List<CalibrationDataset> scenarioList;
	private Map<String, net.finmath.marketdata.products.AnalyticProduct> products;
	private LocalDateTime scenarioDate;

	@BeforeAll
	void setup() throws Exception {
		final String marketData = new String(ValuationClient.class.getClassLoader()
				.getResourceAsStream("net/finmath/smartcontract/valuation/client/md_testset1.xml")
				.readAllBytes(), StandardCharsets.UTF_8);
		final String productData = new String(ValuationClient.class.getClassLoader()
				.getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract.xml")
				.readAllBytes(), StandardCharsets.UTF_8);

		SmartDerivativeContractDescriptor productDescriptor = SDCXMLParser.parse(productData);
		CalibrationDataset dataset = CalibrationParserDataItems.getCalibrationDataSetFromXML(
				marketData, productDescriptor.getMarketdataItemList());

		scenarioDate = dataset.getDate();
		scenarioList = List.of(dataset);

		LocalDate referenceDate = productDescriptor.getTradeDate().toLocalDate();
		Swap swap = IRSwapGenerator.generateAnalyticSwapObject(referenceDate, "5Y", 1.0E7, 0.02, true,
				"forward-EUR-6M", "discount-EUR-OIS");

		products = Map.of("value", swap);
	}

	@Test
	void getAmount() {
		ValuationOraclePlainSwap oracle = new ValuationOraclePlainSwap(products, scenarioList);

		MonetaryAmount amount = oracle.getAmount(scenarioDate, scenarioDate);

		Assertions.assertNotNull(amount);
		Assertions.assertEquals("EUR", amount.getCurrency().getCurrencyCode());
	}

	@Test
	void getValuesReturnsNullWhenScenarioNotFound() {
		ValuationOraclePlainSwap oracle = new ValuationOraclePlainSwap(products, scenarioList);

		LocalDateTime nonExistentDate = LocalDateTime.of(1990, 1, 1, 0, 0, 0);
		Map<String, BigDecimal> result = oracle.getValues(nonExistentDate, nonExistentDate);

		Assertions.assertNull(result);
	}

	@Test
	void getValuesThrowsSDCExceptionOnCalibrationFailure() {
		CalibrationDataset emptyDataset = new CalibrationDataset(Collections.emptySet(), scenarioDate);
		ValuationOraclePlainSwap oracle = new ValuationOraclePlainSwap(products, List.of(emptyDataset));

		Assertions.assertThrows(SDCException.class, () -> oracle.getValues(scenarioDate, scenarioDate));
	}
}
