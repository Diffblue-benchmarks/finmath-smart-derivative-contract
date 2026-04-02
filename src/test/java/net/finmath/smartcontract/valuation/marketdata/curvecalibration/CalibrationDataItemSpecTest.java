package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalibrationDataItemSpecTest {

	@Test
	void testEqualsReturnsTrueForSameInstance() {
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("key1", "curve1", "product1", "1Y");

		Assertions.assertTrue(spec.equals(spec));
	}

	@Test
	void testEqualsReturnsFalseForNull() {
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("key1", "curve1", "product1", "1Y");

		Assertions.assertFalse(spec.equals(null));
	}

	@Test
	void testEqualsReturnsFalseForDifferentClass() {
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("key1", "curve1", "product1", "1Y");

		Assertions.assertFalse(spec.equals("not a spec"));
	}

	@Test
	void testEqualsReturnsTrueForEqualSpecs() {
		CalibrationDataItem.Spec spec1 = new CalibrationDataItem.Spec("key1", "curve1", "product1", "1Y");
		CalibrationDataItem.Spec spec2 = new CalibrationDataItem.Spec("key1", "curve1", "product1", "1Y");

		Assertions.assertTrue(spec1.equals(spec2));
	}

	@Test
	void testEqualsReturnsFalseForDifferentKey() {
		CalibrationDataItem.Spec spec1 = new CalibrationDataItem.Spec("key1", "curve1", "product1", "1Y");
		CalibrationDataItem.Spec spec2 = new CalibrationDataItem.Spec("key2", "curve1", "product1", "1Y");

		Assertions.assertFalse(spec1.equals(spec2));
	}

	@Test
	void testEqualsReturnsFalseForDifferentCurveName() {
		CalibrationDataItem.Spec spec1 = new CalibrationDataItem.Spec("key1", "curve1", "product1", "1Y");
		CalibrationDataItem.Spec spec2 = new CalibrationDataItem.Spec("key1", "curve2", "product1", "1Y");

		Assertions.assertFalse(spec1.equals(spec2));
	}

	@Test
	void testEqualsReturnsFalseForDifferentProductName() {
		CalibrationDataItem.Spec spec1 = new CalibrationDataItem.Spec("key1", "curve1", "product1", "1Y");
		CalibrationDataItem.Spec spec2 = new CalibrationDataItem.Spec("key1", "curve1", "product2", "1Y");

		Assertions.assertFalse(spec1.equals(spec2));
	}

	@Test
	void testEqualsReturnsFalseForDifferentMaturity() {
		CalibrationDataItem.Spec spec1 = new CalibrationDataItem.Spec("key1", "curve1", "product1", "1Y");
		CalibrationDataItem.Spec spec2 = new CalibrationDataItem.Spec("key1", "curve1", "product1", "2Y");

		Assertions.assertFalse(spec1.equals(spec2));
	}
}
