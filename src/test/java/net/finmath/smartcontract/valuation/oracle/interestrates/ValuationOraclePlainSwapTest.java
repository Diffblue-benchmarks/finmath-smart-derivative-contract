package net.finmath.smartcontract.valuation.oracle.interestrates;

import net.finmath.marketdata.model.AnalyticModel;
import net.finmath.marketdata.products.AnalyticProduct;
import net.finmath.modelling.Model;
import net.finmath.smartcontract.model.SDCException;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataset;
import org.junit.jupiter.api.Test;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ValuationOraclePlainSwap
 */
class ValuationOraclePlainSwapTest {

	@Test
	void testGetAmountWithValidScenario() {
		// Given
		final LocalDateTime evaluationTime = LocalDateTime.of(2020, 1, 1, 0, 0);
		final LocalDateTime marketDataTime = LocalDateTime.of(2020, 1, 1, 0, 0);

		final AnalyticProduct mockProduct = new AnalyticProduct() {
			@Override
			public double getValue(double evaluationTime, AnalyticModel model) {
				return 100.0;
			}

			@Override
			public Object getValue(double evaluationTime, Model model) {
				return getValue(evaluationTime, (AnalyticModel) model);
			}
		};

		final Map<String, AnalyticProduct> products = Map.of("value", mockProduct);
		final Set<CalibrationDataItem> dataItems = new HashSet<>();
		final CalibrationDataset scenario = new CalibrationDataset(dataItems, marketDataTime);
		final List<CalibrationDataset> scenarioList = List.of(scenario);

		final ValuationOraclePlainSwap oracle = new ValuationOraclePlainSwap(products, scenarioList);

		// When
		final MonetaryAmount amount = oracle.getAmount(evaluationTime, marketDataTime);

		// Then
		assertNotNull(amount);
		assertEquals("EUR", amount.getCurrency().getCurrencyCode());
	}

	@Test
	void testGetValuesWithNonMatchingScenario() {
		// Given
		final LocalDateTime evaluationTime = LocalDateTime.of(2020, 1, 1, 0, 0);
		final LocalDateTime marketDataTime = LocalDateTime.of(2020, 1, 1, 0, 0);
		final LocalDateTime nonMatchingMarketDataTime = LocalDateTime.of(2021, 1, 1, 0, 0);

		final AnalyticProduct mockProduct = new AnalyticProduct() {
			@Override
			public double getValue(double evaluationTime, AnalyticModel model) {
				return 100.0;
			}

			@Override
			public Object getValue(double evaluationTime, Model model) {
				return getValue(evaluationTime, (AnalyticModel) model);
			}
		};

		final Map<String, AnalyticProduct> products = Map.of("value", mockProduct);
		final Set<CalibrationDataItem> dataItems = new HashSet<>();
		final CalibrationDataset scenario = new CalibrationDataset(dataItems, marketDataTime);
		final List<CalibrationDataset> scenarioList = List.of(scenario);

		final ValuationOraclePlainSwap oracle = new ValuationOraclePlainSwap(products, scenarioList);

		// When
		final Map<String, BigDecimal> values = oracle.getValues(evaluationTime, nonMatchingMarketDataTime);

		// Then
		assertNull(values);
	}

	@Test
	void testGetValuesWithCalibrationError() {
		// Given
		final LocalDateTime evaluationTime = LocalDateTime.of(2020, 1, 1, 0, 0);
		final LocalDateTime marketDataTime = LocalDateTime.of(2020, 1, 1, 0, 0);

		final AnalyticProduct throwingProduct = new AnalyticProduct() {
			@Override
			public double getValue(double evaluationTime, AnalyticModel model) {
				throw new RuntimeException("Calibration failed");
			}

			@Override
			public Object getValue(double evaluationTime, Model model) {
				return getValue(evaluationTime, (AnalyticModel) model);
			}
		};

		final Map<String, AnalyticProduct> products = Map.of("value", throwingProduct);
		final Set<CalibrationDataItem> dataItems = new HashSet<>();
		final CalibrationDataset scenario = new CalibrationDataset(dataItems, marketDataTime);
		final List<CalibrationDataset> scenarioList = List.of(scenario);

		final ValuationOraclePlainSwap oracle = new ValuationOraclePlainSwap(products, scenarioList);

		// When & Then
		assertThrows(SDCException.class, () -> oracle.getValues(evaluationTime, marketDataTime));
	}
}
