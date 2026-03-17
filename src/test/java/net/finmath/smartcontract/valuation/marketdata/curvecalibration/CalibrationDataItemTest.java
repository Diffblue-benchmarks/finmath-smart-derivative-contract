package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalibrationDataItemTest {

	@Test
	void testSpecGetKey() {
		final String key = "TEST_KEY";
		final CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec(key, "CurveName", "ProductName", "1Y");

		assertEquals(key, spec.getKey());
	}

	@Test
	void testSpecEqualsSameObject() {
		final CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("KEY1", "Curve1", "Product1", "1Y");

		assertTrue(spec.equals(spec));
	}

	@Test
	void testSpecEqualsNull() {
		final CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("KEY1", "Curve1", "Product1", "1Y");

		assertFalse(spec.equals(null));
	}

	@Test
	void testSpecEqualsDifferentClass() {
		final CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("KEY1", "Curve1", "Product1", "1Y");
		final String differentClass = "NotASpec";

		assertFalse(spec.equals(differentClass));
	}

	@Test
	void testSpecEqualsEqualSpecs() {
		final CalibrationDataItem.Spec spec1 = new CalibrationDataItem.Spec("KEY1", "Curve1", "Product1", "1Y");
		final CalibrationDataItem.Spec spec2 = new CalibrationDataItem.Spec("KEY1", "Curve1", "Product1", "1Y");

		assertTrue(spec1.equals(spec2));
		assertTrue(spec2.equals(spec1));
	}

	@Test
	void testSpecEqualsDifferentKey() {
		final CalibrationDataItem.Spec spec1 = new CalibrationDataItem.Spec("KEY1", "Curve1", "Product1", "1Y");
		final CalibrationDataItem.Spec spec2 = new CalibrationDataItem.Spec("KEY2", "Curve1", "Product1", "1Y");

		assertFalse(spec1.equals(spec2));
	}

	@Test
	void testSpecEqualsDifferentCurveName() {
		final CalibrationDataItem.Spec spec1 = new CalibrationDataItem.Spec("KEY1", "Curve1", "Product1", "1Y");
		final CalibrationDataItem.Spec spec2 = new CalibrationDataItem.Spec("KEY1", "Curve2", "Product1", "1Y");

		assertFalse(spec1.equals(spec2));
	}

	@Test
	void testSpecEqualsDifferentProductName() {
		final CalibrationDataItem.Spec spec1 = new CalibrationDataItem.Spec("KEY1", "Curve1", "Product1", "1Y");
		final CalibrationDataItem.Spec spec2 = new CalibrationDataItem.Spec("KEY1", "Curve1", "Product2", "1Y");

		assertFalse(spec1.equals(spec2));
	}

	@Test
	void testSpecEqualsDifferentMaturity() {
		final CalibrationDataItem.Spec spec1 = new CalibrationDataItem.Spec("KEY1", "Curve1", "Product1", "1Y");
		final CalibrationDataItem.Spec spec2 = new CalibrationDataItem.Spec("KEY1", "Curve1", "Product1", "2Y");

		assertFalse(spec1.equals(spec2));
	}
}
