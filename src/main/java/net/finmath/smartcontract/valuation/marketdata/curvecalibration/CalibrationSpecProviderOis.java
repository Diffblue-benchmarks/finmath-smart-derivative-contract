package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import net.finmath.marketdata.calibration.CalibratedCurves;
import net.finmath.time.Schedule;
import net.finmath.time.ScheduleGenerator;
import net.finmath.time.businessdaycalendar.BusinessdayCalendarExcludingTARGETHolidays;

import static net.finmath.smartcontract.valuation.marketdata.curvecalibration.Calibrator.DISCOUNT_EUR_OIS;

/**
 * A calibration spec provider for OIS swaps.
 *
 * @author Luca Del Re
 * @author Peter Kohl-Landgraf
 * @author Christian Fries
 */
public record CalibrationSpecProviderOis(String maturityLabel, String frequency, double swapRate) implements CalibrationSpecProvider {

	public String getMaturityLabel() { return maturityLabel; }
	public String getFrequency() { return frequency; }
	public double getSwapRate() { return swapRate; }

	@Override
	public CalibratedCurves.CalibrationSpec getCalibrationSpec(final CalibrationContext ctx) {
		final Schedule scheduleInterfaceRec = ScheduleGenerator.createScheduleFromConventions(ctx.getReferenceDate(), 2, "0D", maturityLabel, frequency, "act/360", "first", "modified_following", new BusinessdayCalendarExcludingTARGETHolidays(), 0, 1);
		final Schedule scheduleInterfacePay = ScheduleGenerator.createScheduleFromConventions(ctx.getReferenceDate(), 2, "0D", maturityLabel, frequency, "act/360", "first", "modified_following", new BusinessdayCalendarExcludingTARGETHolidays(), 0, 1);
		final double calibrationTime = scheduleInterfaceRec.getPayment(scheduleInterfaceRec.getNumberOfPeriods() - 1);

		return new CalibratedCurves.CalibrationSpec(String.format("EUR-OIS-%1$s", maturityLabel), "Swap", scheduleInterfaceRec, "forward-EUR-OIS", 0.0, DISCOUNT_EUR_OIS, scheduleInterfacePay, "", swapRate, DISCOUNT_EUR_OIS, DISCOUNT_EUR_OIS, calibrationTime);
	}
}
