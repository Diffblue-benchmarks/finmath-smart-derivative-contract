package net.finmath.smartcontract.valuation.implementation.reactive;

import net.finmath.smartcontract.model.ValueResult;
import net.finmath.smartcontract.valuation.implementation.MarginCalculator;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataset;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConditionalSettlementCalculatorTest {

	@Test
	void testConstructor() {
		String sdcXML = "<xml>test</xml>";
		BigDecimal triggerValue = BigDecimal.valueOf(100.0);

		try (MockedConstruction<MarginCalculator> ignored = mockConstruction(MarginCalculator.class)) {
			ConditionalSettlementCalculator calculator = new ConditionalSettlementCalculator(sdcXML, triggerValue);
			assertNotNull(calculator);
		}
	}

	@Test
	void testApplyFirstCallReturnDefaultResult() {
		String sdcXML = "<xml>test</xml>";
		BigDecimal triggerValue = BigDecimal.valueOf(100.0);

		CalibrationDataset mockDataset = mock(CalibrationDataset.class);
		when(mockDataset.serializeToJson()).thenReturn("{\"data\":\"test\"}");

		try (MockedConstruction<MarginCalculator> ignored = mockConstruction(MarginCalculator.class)) {
			ConditionalSettlementCalculator calculator = new ConditionalSettlementCalculator(sdcXML, triggerValue);

			ValueResult result = calculator.apply(mockDataset);

			assertNotNull(result);
			assertNull(result.getValue());
		}
	}

	@Test
	void testApplySecondCallReturnsMarginResult() throws Exception {
		String sdcXML = "<xml>test</xml>";
		BigDecimal triggerValue = BigDecimal.valueOf(100.0);
		String marketDataJson = "{\"data\":\"test\"}";

		CalibrationDataset mockDataset = mock(CalibrationDataset.class);
		when(mockDataset.serializeToJson()).thenReturn(marketDataJson);

		ValueResult expectedResult = new ValueResult();
		expectedResult.setValue(BigDecimal.valueOf(42.0));

		try (MockedConstruction<MarginCalculator> construction = mockConstruction(MarginCalculator.class,
				(mock, context) -> when(mock.getValue(anyString(), anyString())).thenReturn(expectedResult))) {
			ConditionalSettlementCalculator calculator = new ConditionalSettlementCalculator(sdcXML, triggerValue);

			// First call sets previousmarketdata
			calculator.apply(mockDataset);

			// Second call should use MarginCalculator
			ValueResult result = calculator.apply(mockDataset);

			assertNotNull(result);
			assertEquals(BigDecimal.valueOf(42.0), result.getValue());
		}
	}

	@Test
	void testApplySecondCallWhenCalculatorThrowsException() throws Exception {
		String sdcXML = "<xml>test</xml>";
		BigDecimal triggerValue = BigDecimal.valueOf(100.0);
		String marketDataJson = "{\"data\":\"test\"}";

		CalibrationDataset mockDataset = mock(CalibrationDataset.class);
		when(mockDataset.serializeToJson()).thenReturn(marketDataJson);

		try (MockedConstruction<MarginCalculator> construction = mockConstruction(MarginCalculator.class,
				(mock, context) -> when(mock.getValue(anyString(), anyString())).thenThrow(new RuntimeException("Calculation failed")))) {
			ConditionalSettlementCalculator calculator = new ConditionalSettlementCalculator(sdcXML, triggerValue);

			// First call sets previousmarketdata
			calculator.apply(mockDataset);

			// Second call triggers exception path
			ValueResult result = calculator.apply(mockDataset);

			assertNotNull(result);
			assertNull(result.getValue());
		}
	}
}
