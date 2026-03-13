package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CalibrationDataItemTest {

	private final LocalDateTime dateTime = LocalDateTime.of(2024, 6, 15, 17, 0);
	private final CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec(
			"Euribor6M_Swap-Rate_5Y", "Euribor6M", "Swap-Rate", "5Y");

	@Test
	void getDaysToMaturity_forYears() {
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.03, dateTime);
		assertEquals(5 * 360, item.getDaysToMaturity());
	}

	@Test
	void getDaysToMaturity_forMonths() {
		CalibrationDataItem.Spec monthSpec = new CalibrationDataItem.Spec("key", "curve", "product", "6M");
		CalibrationDataItem item = new CalibrationDataItem(monthSpec, 0.02, dateTime);
		assertEquals(6 * 30, item.getDaysToMaturity());
	}

	@Test
	void getDaysToMaturity_forDays() {
		CalibrationDataItem.Spec daySpec = new CalibrationDataItem.Spec("key", "curve", "product", "30D");
		CalibrationDataItem item = new CalibrationDataItem(daySpec, 0.01, dateTime);
		assertEquals(30, item.getDaysToMaturity());
	}

	@Test
	void getClonedScaled_shouldDivideByFactor() {
		CalibrationDataItem item = new CalibrationDataItem(spec, 100.0, dateTime);
		CalibrationDataItem scaled = item.getClonedScaled(2.0);
		assertEquals(50.0, scaled.getQuote());
		assertEquals(spec, scaled.getSpec());
	}

	@Test
	void getClonedShifted_shouldAddAmount() {
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.03, dateTime);
		CalibrationDataItem shifted = item.getClonedShifted(0.01);
		assertEquals(0.04, shifted.getQuote(), 1e-10);
	}

	@Test
	void getDateString_shouldFormatCorrectly() {
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.03, dateTime);
		assertEquals("2024-06-15", item.getDateString());
	}

	@Test
	void getDate_shouldReturnLocalDate() {
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.03, dateTime);
		assertEquals(LocalDate.of(2024, 6, 15), item.getDate());
	}

	@Test
	void equals_shouldBeTrue_forSameData() {
		CalibrationDataItem item1 = new CalibrationDataItem(spec, 0.03, dateTime);
		CalibrationDataItem item2 = new CalibrationDataItem(spec, 0.03, dateTime);
		assertEquals(item1, item2);
		assertEquals(item1.hashCode(), item2.hashCode());
	}

	@Test
	void equals_shouldBeFalse_forDifferentQuote() {
		CalibrationDataItem item1 = new CalibrationDataItem(spec, 0.03, dateTime);
		CalibrationDataItem item2 = new CalibrationDataItem(spec, 0.04, dateTime);
		assertNotEquals(item1, item2);
	}

	@Test
	void specEquals_shouldWorkCorrectly() {
		CalibrationDataItem.Spec spec2 = new CalibrationDataItem.Spec(
				"Euribor6M_Swap-Rate_5Y", "Euribor6M", "Swap-Rate", "5Y");
		assertEquals(spec, spec2);
		assertEquals(spec.hashCode(), spec2.hashCode());
	}

	@Test
	void specGetters_shouldReturnCorrectValues() {
		assertEquals("Euribor6M_Swap-Rate_5Y", spec.getKey());
		assertEquals("Euribor6M", spec.getCurveName());
		assertEquals("Swap-Rate", spec.getProductName());
		assertEquals("5Y", spec.getMaturity());
	}

	@Test
	void getters_shouldDelegateThroughSpec() {
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.03, dateTime);
		assertEquals("Euribor6M", item.getCurveName());
		assertEquals("Swap-Rate", item.getProductName());
		assertEquals("5Y", item.getMaturity());
	}
}
