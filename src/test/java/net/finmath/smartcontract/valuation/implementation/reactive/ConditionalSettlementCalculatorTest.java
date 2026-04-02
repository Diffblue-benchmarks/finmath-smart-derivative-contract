package net.finmath.smartcontract.valuation.implementation.reactive;

import net.finmath.smartcontract.model.ValueResult;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataset;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;

class ConditionalSettlementCalculatorTest {

	@Test
	void testConstructor() {
		ConditionalSettlementCalculator calculator = new ConditionalSettlementCalculator("someXML", BigDecimal.ONE);

		Assertions.assertNotNull(calculator);
	}

	@Test
	void testApplyFirstCallReturnsNullValue() {
		ConditionalSettlementCalculator calculator = new ConditionalSettlementCalculator("someXML", BigDecimal.ONE);
		CalibrationDataset dataset = new CalibrationDataset(new LinkedHashSet<>(), LocalDateTime.now());

		ValueResult result = calculator.apply(dataset);

		Assertions.assertNull(result.getValue());
	}

	@Test
	void testApplySecondCallWithExceptionReturnsNullValue() {
		ConditionalSettlementCalculator calculator = new ConditionalSettlementCalculator("invalidXML", BigDecimal.ONE);
		CalibrationDataset dataset = new CalibrationDataset(new LinkedHashSet<>(), LocalDateTime.now());

		calculator.apply(dataset);

		ValueResult result = calculator.apply(dataset);

		Assertions.assertNull(result.getValue());
	}

}
