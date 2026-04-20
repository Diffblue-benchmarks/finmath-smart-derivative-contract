package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalibrationDataItemSpecTest {

	@Test
	void testEqualsSameInstance() {
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("key1", "curve1", "swap", "1Y");
		assertEquals(spec, spec);
	}

	@Test
	void testEqualsNull() {
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("key1", "curve1", "swap", "1Y");
		assertNotEquals(null, spec);
	}

	@Test
	void testEqualsDifferentClass() {
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("key1", "curve1", "swap", "1Y");
		assertNotEquals("not a spec", spec);
	}

	@Test
	void testEqualsIdenticalSpecs() {
		CalibrationDataItem.Spec spec1 = new CalibrationDataItem.Spec("key1", "curve1", "swap", "1Y");
		CalibrationDataItem.Spec spec2 = new CalibrationDataItem.Spec("key1", "curve1", "swap", "1Y");
		assertEquals(spec1, spec2);
	}

	@Test
	void testEqualsDifferentKey() {
		CalibrationDataItem.Spec spec1 = new CalibrationDataItem.Spec("key1", "curve1", "swap", "1Y");
		CalibrationDataItem.Spec spec2 = new CalibrationDataItem.Spec("key2", "curve1", "swap", "1Y");
		assertNotEquals(spec1, spec2);
	}

	@Test
	void testEqualsDifferentCurveName() {
		CalibrationDataItem.Spec spec1 = new CalibrationDataItem.Spec("key1", "curve1", "swap", "1Y");
		CalibrationDataItem.Spec spec2 = new CalibrationDataItem.Spec("key1", "curve2", "swap", "1Y");
		assertNotEquals(spec1, spec2);
	}

	@Test
	void testEqualsDifferentProductName() {
		CalibrationDataItem.Spec spec1 = new CalibrationDataItem.Spec("key1", "curve1", "swap", "1Y");
		CalibrationDataItem.Spec spec2 = new CalibrationDataItem.Spec("key1", "curve1", "fra", "1Y");
		assertNotEquals(spec1, spec2);
	}

	@Test
	void testEqualsDifferentMaturity() {
		CalibrationDataItem.Spec spec1 = new CalibrationDataItem.Spec("key1", "curve1", "swap", "1Y");
		CalibrationDataItem.Spec spec2 = new CalibrationDataItem.Spec("key1", "curve1", "swap", "2Y");
		assertNotEquals(spec1, spec2);
	}
}
