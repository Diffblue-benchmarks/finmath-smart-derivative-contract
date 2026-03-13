package net.finmath.smartcontract.valuation.service.utils;

import org.junit.jupiter.api.Test;

import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class SDCRoundingTest {

	@Test
	void roundDouble_shouldRoundTo2DecimalPlaces() {
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);
		assertEquals(1.24, rounding.roundDouble(1.235));
		assertEquals(1.23, rounding.roundDouble(1.234));
	}

	@Test
	void roundDouble_shouldHandleWholeNumbers() {
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);
		assertEquals(5.0, rounding.roundDouble(5.0));
	}

	@Test
	void roundDouble_shouldHandleNegativeNumbers() {
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);
		assertEquals(-1.24, rounding.roundDouble(-1.235));
	}

	@Test
	void getRoundedValueAsIntegerString_shouldConvertCorrectly() {
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);
		assertEquals("124", rounding.getRoundedValueAsIntegerString(1.235));
	}

	@Test
	void getDoubleFromIntegerString_shouldConvertBack() {
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);
		assertEquals(1.24, rounding.getDoubleFromIntegerString("124"));
	}

	@Test
	void getDoubleFromIntegerString_shouldHandleSingleDigit() {
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);
		assertEquals(0.05, rounding.getDoubleFromIntegerString("5"));
	}

	@Test
	void getDoubleFromIntegerString_shouldHandleTwoDigits() {
		SDCRounding rounding = new SDCRounding(2, RoundingMode.HALF_UP);
		assertEquals(0.12, rounding.getDoubleFromIntegerString("12"));
	}

	@Test
	void roundDouble_withScale4_shouldRound() {
		SDCRounding rounding = new SDCRounding(4, RoundingMode.HALF_UP);
		assertEquals(0.0235, rounding.roundDouble(0.02349999));
	}
}
