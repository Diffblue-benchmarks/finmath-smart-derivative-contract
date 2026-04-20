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

class ValuationOraclePlainSwapTest {

	private static final LocalDateTime SCENARIO_TIME = LocalDateTime.of(2023, 6, 15, 10, 0);

	private static AnalyticProduct createMockProduct() {
		return new AnalyticProduct() {
			@Override
			public double getValue(double evaluationTime, AnalyticModel model) {
				return 100.0;
			}

			@Override
			public Object getValue(double evaluationTime, Model model) {
				return 100.0;
			}
		};
	}

	private static AnalyticProduct createThrowingProduct() {
		return new AnalyticProduct() {
			@Override
			public double getValue(double evaluationTime, AnalyticModel model) {
				throw new RuntimeException("Simulated valuation error");
			}

			@Override
			public Object getValue(double evaluationTime, Model model) {
				throw new RuntimeException("Simulated valuation error");
			}
		};
	}

	@Test
	void testGetValuesReturnsNullWhenNoScenarioMatches() {
		LocalDateTime nonMatchingTime = LocalDateTime.of(2099, 1, 1, 0, 0);
		CalibrationDataset dataset = createDataset(SCENARIO_TIME, Set.of());
		ValuationOraclePlainSwap oracle = new ValuationOraclePlainSwap(
				Map.of("value", createMockProduct()), List.of(dataset));

		Map<String, BigDecimal> result = oracle.getValues(nonMatchingTime, nonMatchingTime);

		assertNull(result);
	}

	@Test
	void testGetValuesThrowsSDCExceptionOnCalibrationError() {
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("EURESTSD", "ESTR", "Swap-Rate", "1D");
		CalibrationDataItem oisItem = new CalibrationDataItem(spec, 0.03, SCENARIO_TIME);
		CalibrationDataset dataset = createDataset(SCENARIO_TIME, Set.of(oisItem));
		ValuationOraclePlainSwap oracle = new ValuationOraclePlainSwap(
				Map.of("value", createThrowingProduct()), List.of(dataset));

		assertThrows(SDCException.class, () -> oracle.getValues(SCENARIO_TIME, SCENARIO_TIME));
	}

	@Test
	void testAddMissingOverNightRateEarlyReturnWhenOisPresent() {
		CalibrationDataItem.Spec oisSpec = new CalibrationDataItem.Spec("EURESTSD", "ESTR", "Swap-Rate", "1D");
		CalibrationDataItem oisItem = new CalibrationDataItem(oisSpec, 0.03, SCENARIO_TIME);
		CalibrationDataItem.Spec swapSpec = new CalibrationDataItem.Spec("EUR6M5Y", "Euribor6M", "Swap-Rate", "5Y");
		CalibrationDataItem swapItem = new CalibrationDataItem(swapSpec, 0.025, SCENARIO_TIME);
		CalibrationDataset dataset = createDataset(SCENARIO_TIME, Set.of(oisItem, swapItem));
		ValuationOraclePlainSwap oracle = new ValuationOraclePlainSwap(
				Map.of("value", createMockProduct()), List.of(dataset));

		Map<String, BigDecimal> result = oracle.getValues(SCENARIO_TIME, SCENARIO_TIME);

		assertNotNull(result);
		assertTrue(result.containsKey("value"));
	}

	@Test
	void testAddMissingOverNightRateFromEstrFixing() {
		CalibrationDataItem.Spec fixingSpec = new CalibrationDataItem.Spec("ESTRFIX", "ESTR", "Fixing", "1D");
		CalibrationDataItem fixingItem = new CalibrationDataItem(fixingSpec, 0.035, SCENARIO_TIME.minusDays(1));
		CalibrationDataItem.Spec swapSpec = new CalibrationDataItem.Spec("EUR6M5Y", "Euribor6M", "Swap-Rate", "5Y");
		CalibrationDataItem swapItem = new CalibrationDataItem(swapSpec, 0.025, SCENARIO_TIME);
		CalibrationDataset dataset = createDataset(SCENARIO_TIME, Set.of(fixingItem, swapItem));
		ValuationOraclePlainSwap oracle = new ValuationOraclePlainSwap(
				Map.of("value", createMockProduct()), List.of(dataset));

		Map<String, BigDecimal> result = oracle.getValues(SCENARIO_TIME, SCENARIO_TIME);

		assertNotNull(result);
		assertTrue(result.containsKey("value"));
	}

	@Test
	void testGetAmountWithMatchingScenario() {
		CalibrationDataItem.Spec oisSpec = new CalibrationDataItem.Spec("EURESTSD", "ESTR", "Swap-Rate", "1D");
		CalibrationDataItem oisItem = new CalibrationDataItem(oisSpec, 0.03, SCENARIO_TIME);
		CalibrationDataset dataset = createDataset(SCENARIO_TIME, Set.of(oisItem));
		ValuationOraclePlainSwap oracle = new ValuationOraclePlainSwap(
				Map.of("value", createMockProduct()), List.of(dataset));

		MonetaryAmount amount = oracle.getAmount(SCENARIO_TIME, SCENARIO_TIME);

		assertNotNull(amount);
		assertEquals("EUR", amount.getCurrency().getCurrencyCode());
	}

	@Test
	void testGetAmountThrowsWhenNoScenarioMatches() {
		CalibrationDataset dataset = createDataset(SCENARIO_TIME, Set.of());
		ValuationOraclePlainSwap oracle = new ValuationOraclePlainSwap(
				Map.of("value", createMockProduct()), List.of(dataset));

		assertThrows(NullPointerException.class,
				() -> oracle.getAmount(SCENARIO_TIME.plusDays(999), SCENARIO_TIME.plusDays(999)));
	}

	private CalibrationDataset createDataset(LocalDateTime time, Set<CalibrationDataItem> items) {
		return new CalibrationDataset(items, time);
	}
}
