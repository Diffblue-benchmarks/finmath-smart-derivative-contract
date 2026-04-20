package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CalibrationDataItemTest {

	private CalibrationDataItem createItem(double quote) {
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("key1", "curve1", "swap", "5Y");
		LocalDateTime dateTime = LocalDateTime.of(2024, 3, 15, 10, 30, 0);
		return new CalibrationDataItem(spec, quote, dateTime);
	}

	@Test
	void testGetClonedShifted() {
		CalibrationDataItem item = createItem(1.5);

		CalibrationDataItem shifted = item.getClonedShifted(0.5);

		assertEquals(2.0, shifted.getQuote(), 1e-15);
		assertEquals(item.getSpec(), shifted.getSpec());
		assertEquals(item.getDateTime(), shifted.getDateTime());
	}

	@Test
	void testGetDateString() {
		CalibrationDataItem item = createItem(1.0);

		assertEquals("2024-03-15", item.getDateString());
	}

	@Test
	void testGetDate() {
		CalibrationDataItem item = createItem(1.0);

		assertEquals(LocalDate.of(2024, 3, 15), item.getDate());
	}

	@Test
	void testGetDateTime() {
		CalibrationDataItem item = createItem(1.0);

		assertEquals(LocalDateTime.of(2024, 3, 15, 10, 30, 0), item.getDateTime());
	}

	@Test
	void testEqualsSameObject() {
		CalibrationDataItem item = createItem(1.0);

		assertEquals(item, item);
	}

	@Test
	void testEqualsNull() {
		CalibrationDataItem item = createItem(1.0);

		assertNotEquals(null, item);
	}

	@Test
	void testEqualsDifferentClass() {
		CalibrationDataItem item = createItem(1.0);

		assertNotEquals("not a CalibrationDataItem", item);
	}

	@Test
	void testEqualsEqualObjects() {
		CalibrationDataItem item1 = createItem(1.0);
		CalibrationDataItem item2 = createItem(1.0);

		assertEquals(item1, item2);
	}

	@Test
	void testEqualsDifferentQuote() {
		CalibrationDataItem item1 = createItem(1.0);
		CalibrationDataItem item2 = createItem(2.0);

		assertNotEquals(item1, item2);
	}
}
