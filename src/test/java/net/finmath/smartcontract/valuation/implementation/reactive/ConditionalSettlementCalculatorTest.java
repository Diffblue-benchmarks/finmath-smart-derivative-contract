package net.finmath.smartcontract.valuation.implementation.reactive;

import net.finmath.smartcontract.model.ValueResult;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataset;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ConditionalSettlementCalculator}.
 * Verifies that the first invocation of {@code apply} returns a {@link ValueResult}
 * with a null value (since no previous market data exists yet).
 */
class ConditionalSettlementCalculatorTest {

	/**
	 * Creates a minimal {@link CalibrationDataset} with a single synthetic data item.
	 *
	 * @return a CalibrationDataset suitable for testing
	 */
	private CalibrationDataset createTestCalibrationDataset() {
		LocalDateTime now = LocalDateTime.of(2026, 3, 13, 10, 0, 0);
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec(
				"EUR-EURIBOR-6M-Swap-10Y", "EUR-EURIBOR-6M", "Swap", "10Y"
		);
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.025, now);

		Set<CalibrationDataItem> items = new LinkedHashSet<>();
		items.add(item);
		return new CalibrationDataset(items, now);
	}

	@Test
	@DisplayName("First call to apply returns ValueResult with null value")
	void apply_firstCall_returnsValueResultWithNullValue() {
		String dummySdcXml = "<sdc>test</sdc>";
		BigDecimal triggerValue = BigDecimal.valueOf(100.0);
		ConditionalSettlementCalculator calculator =
				new ConditionalSettlementCalculator(dummySdcXml, triggerValue);

		CalibrationDataset dataset = createTestCalibrationDataset();
		ValueResult result = calculator.apply(dataset);

		assertNotNull(result, "Result should not be null on first call");
		assertNull(result.getValue(), "Value should be null on first call since no previous data exists");
	}

	@Test
	@DisplayName("First call to apply returns a non-null ValueResult object")
	void apply_firstCall_returnsNonNullResult() {
		String dummySdcXml = "<sdc>dummy</sdc>";
		ConditionalSettlementCalculator calculator =
				new ConditionalSettlementCalculator(dummySdcXml, BigDecimal.ZERO);

		CalibrationDataset dataset = createTestCalibrationDataset();
		ValueResult result = calculator.apply(dataset);

		assertNotNull(result, "First call should always return a non-null ValueResult");
	}
}
