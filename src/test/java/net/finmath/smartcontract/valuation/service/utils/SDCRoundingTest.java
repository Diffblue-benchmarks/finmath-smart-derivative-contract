package net.finmath.smartcontract.valuation.service.utils;

import java.math.RoundingMode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SDCRoundingTest {

	@Test
	void testConstructorSetsScaleAndRoundingMode() {
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);

		assertEquals(1.24, rounding.roundDouble(1.235), 0.0001);
	}

	@Test
	void testConstructorWithDifferentScale() {
		SDCRounding rounding = new SDCRounding(0, RoundingMode.FLOOR);

		assertEquals(1.0, rounding.roundDouble(1.9), 0.0001);
	}

}
