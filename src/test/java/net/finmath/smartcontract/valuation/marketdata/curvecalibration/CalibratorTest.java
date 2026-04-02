package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import net.finmath.smartcontract.product.SmartDerivativeContractDescriptor;
import net.finmath.smartcontract.product.xml.SDCXMLParser;
import net.finmath.smartcontract.valuation.client.ValuationClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

class CalibratorTest {

	private static final LocalDate REFERENCE_DATE = LocalDate.of(2023, 1, 31);

	@Test
	void testGetCalibratedCurvesBeforeCalibration() {
		CalibrationContext ctx = new CalibrationContextImpl(REFERENCE_DATE, 1E-9);
		Calibrator calibrator = new Calibrator(List.of(), ctx);

		Assertions.assertNull(calibrator.getCalibratedCurves());
	}

	@Test
	void testCalibrateModelWithEstrHistoricalFixings() throws Exception {
		CalibrationContext ctx = new CalibrationContextImpl(REFERENCE_DATE, 1E-9);
		CalibrationDataset dataset = loadCalibrationDataset();
		Set<CalibrationDataItem> allDataPoints = dataset.getDataPoints();

		List<CalibrationDataItem> fixings = new ArrayList<>(
				allDataPoints.stream()
						.filter(cdi -> cdi.getSpec().getProductName().equals("Fixing") ||
								cdi.getSpec().getProductName().equals("Deposit"))
						.toList());

		// Add ESTR fixings with past dates to exercise the time < 0 branch in getOisDiscountCurve
		fixings.add(createFixingItem("ESTR", "ESTRFIX1D", LocalDate.of(2023, 1, 24), 0.0235));
		fixings.add(createFixingItem("ESTR", "ESTRFIX1D", LocalDate.of(2023, 1, 25), 0.0235));
		fixings.add(createFixingItem("ESTR", "ESTRFIX1D", LocalDate.of(2023, 1, 26), 0.0236));

		Stream<CalibrationSpecProvider> providers = dataset.getDataAsCalibrationDataPointStream(new CalibrationParserDataItems());
		Calibrator calibrator = new Calibrator(fixings, ctx);
		Optional<CalibrationResult> result = calibrator.calibrateModel(providers, ctx);

		Assertions.assertTrue(result.isPresent());
		Assertions.assertNotNull(calibrator.getCalibratedCurves());
	}

	@Test
	void testCalibrateModelWithEuribor3MFixings() throws Exception {
		CalibrationContext ctx = new CalibrationContextImpl(REFERENCE_DATE, 1E-9);
		CalibrationDataset dataset = loadCalibrationDataset();
		Set<CalibrationDataItem> allDataPoints = dataset.getDataPoints();

		List<CalibrationDataItem> fixings = new ArrayList<>(
				allDataPoints.stream()
						.filter(cdi -> cdi.getSpec().getProductName().equals("Fixing") ||
								cdi.getSpec().getProductName().equals("Deposit"))
						.toList());

		// Add Euribor3M fixings to exercise the non-empty branch in get3MForwardCurve
		fixings.add(createFixingItem("Euribor3M", "EUB3FIX3M", LocalDate.of(2023, 1, 20), 0.0250));
		fixings.add(createFixingItem("Euribor3M", "EUB3FIX3M", LocalDate.of(2023, 1, 25), 0.0252));

		Stream<CalibrationSpecProvider> providers = dataset.getDataAsCalibrationDataPointStream(new CalibrationParserDataItems());
		Calibrator calibrator = new Calibrator(fixings, ctx);
		Optional<CalibrationResult> result = calibrator.calibrateModel(providers, ctx);

		Assertions.assertTrue(result.isPresent());
	}

	@Test
	void testCalibrateModelWithEuribor1MFixings() throws Exception {
		CalibrationContext ctx = new CalibrationContextImpl(REFERENCE_DATE, 1E-9);
		CalibrationDataset dataset = loadCalibrationDataset();
		Set<CalibrationDataItem> allDataPoints = dataset.getDataPoints();

		List<CalibrationDataItem> fixings = new ArrayList<>(
				allDataPoints.stream()
						.filter(cdi -> cdi.getSpec().getProductName().equals("Fixing") ||
								cdi.getSpec().getProductName().equals("Deposit"))
						.toList());

		// Add Euribor1M fixings to exercise the non-empty branch in get1MForwardCurve
		fixings.add(createFixingItem("Euribor1M", "EUB1FIX1M", LocalDate.of(2023, 1, 20), 0.0210));
		fixings.add(createFixingItem("Euribor1M", "EUB1FIX1M", LocalDate.of(2023, 1, 25), 0.0212));

		Stream<CalibrationSpecProvider> providers = dataset.getDataAsCalibrationDataPointStream(new CalibrationParserDataItems());
		Calibrator calibrator = new Calibrator(fixings, ctx);
		Optional<CalibrationResult> result = calibrator.calibrateModel(providers, ctx);

		Assertions.assertTrue(result.isPresent());
	}

	private CalibrationDataset loadCalibrationDataset() throws Exception {
		final String marketData = new String(
				ValuationClient.class.getClassLoader()
						.getResourceAsStream("net/finmath/smartcontract/valuation/client/md_testset_with_fixings.xml")
						.readAllBytes(), StandardCharsets.UTF_8);
		final String productData = new String(
				ValuationClient.class.getClassLoader()
						.getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract.xml")
						.readAllBytes(), StandardCharsets.UTF_8);

		SmartDerivativeContractDescriptor descriptor = SDCXMLParser.parse(productData);
		return CalibrationParserDataItems.getCalibrationDataSetFromXML(marketData, descriptor.getMarketdataItemList());
	}

	private CalibrationDataItem createFixingItem(String curveName, String key, LocalDate date, double quote) {
		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec(key, curveName, "Fixing", "1D");
		return new CalibrationDataItem(spec, quote, date.atStartOfDay());
	}
}
