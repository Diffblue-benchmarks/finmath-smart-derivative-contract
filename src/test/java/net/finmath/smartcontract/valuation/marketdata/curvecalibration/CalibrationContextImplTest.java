package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CalibrationContextImplTest {

	@Test
	void getReferenceDate_shouldReturnDatePart() {
		LocalDateTime refDateTime = LocalDateTime.of(2024, 6, 15, 14, 30);
		CalibrationContextImpl ctx = new CalibrationContextImpl(refDateTime, 1e-12);
		assertEquals(LocalDate.of(2024, 6, 15), ctx.getReferenceDate());
	}

	@Test
	void getReferenceDateTime_shouldReturnFullDateTime() {
		LocalDateTime refDateTime = LocalDateTime.of(2024, 6, 15, 14, 30);
		CalibrationContextImpl ctx = new CalibrationContextImpl(refDateTime, 1e-12);
		assertEquals(refDateTime, ctx.getReferenceDateTime());
	}

	@Test
	void getAccuracy_shouldReturnConfiguredValue() {
		CalibrationContextImpl ctx = new CalibrationContextImpl(LocalDateTime.now(), 1e-8);
		assertEquals(1e-8, ctx.getAccuracy());
	}
}
