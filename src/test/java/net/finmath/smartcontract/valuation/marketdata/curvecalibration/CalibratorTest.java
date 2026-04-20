package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import net.finmath.smartcontract.product.SmartDerivativeContractDescriptor;
import net.finmath.smartcontract.product.xml.SDCXMLParser;
import net.finmath.smartcontract.valuation.client.ValuationClient;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CalibratorTest {

	@Test
	void testGetCalibratedCurvesBeforeCalibration() {
		LocalDateTime refDateTime = LocalDateTime.of(2023, 5, 5, 14, 35, 23);
		CalibrationContext ctx = new CalibrationContextImpl(refDateTime, 1E-9);
		List<CalibrationDataItem> fixings = new ArrayList<>();
		Calibrator calibrator = new Calibrator(fixings, ctx);

		assertNull(calibrator.getCalibratedCurves());
	}

	@Test
	void testCalibrateModelWithEmptySpecs() throws Exception {
		LocalDateTime refDateTime = LocalDateTime.of(2023, 5, 5, 14, 35, 23);
		CalibrationContext ctx = new CalibrationContextImpl(refDateTime, 1E-9);

		List<CalibrationDataItem> fixings = new ArrayList<>();
		CalibrationDataItem.Spec estrSpec = new CalibrationDataItem.Spec("ESTR1D", "ESTR", "Fixing", "1D");
		fixings.add(new CalibrationDataItem(estrSpec, 0.032, LocalDateTime.of(2023, 5, 4, 9, 0, 0)));
		fixings.add(new CalibrationDataItem(estrSpec, 0.031, LocalDateTime.of(2023, 5, 3, 9, 0, 0)));

		Calibrator calibrator = new Calibrator(fixings, ctx);

		// Empty stream means no calibration specs - should still succeed with empty specs
		Stream<CalibrationSpecProvider> providers = Stream.empty();
		Optional<CalibrationResult> result = calibrator.calibrateModel(providers, ctx);

		assertTrue(result.isPresent());
		assertNotNull(calibrator.getCalibratedCurves());
	}

	@Test
	void testCalibrateModelWithFixings() throws Exception {
		final String marketData = new String(
				ValuationClient.class.getClassLoader()
						.getResourceAsStream("net/finmath/smartcontract/valuation/client/md_testset_with_fixings.xml")
						.readAllBytes(), StandardCharsets.UTF_8);
		final String productData = new String(
				ValuationClient.class.getClassLoader()
						.getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract.xml")
						.readAllBytes(), StandardCharsets.UTF_8);
		SmartDerivativeContractDescriptor productDescriptor = SDCXMLParser.parse(productData);

		final CalibrationDataset calibrationDataset = CalibrationParserDataItems.getCalibrationDataSetFromXML(
				marketData, productDescriptor.getMarketdataItemList());

		CalibrationContext calibrationContext = new CalibrationContextImpl(calibrationDataset.getDate(), 1E-9);

		List<CalibrationDataItem> fixings = calibrationDataset.getDataPoints().stream()
				.filter(cdi -> cdi.getSpec().getProductName().equals("Fixing") ||
						cdi.getSpec().getProductName().equals("Deposit"))
				.toList();

		Calibrator calibrator = new Calibrator(fixings, calibrationContext);

		Stream<CalibrationSpecProvider> specStream =
				calibrationDataset.getDataAsCalibrationDataPointStream(new CalibrationParserDataItems());

		Optional<CalibrationResult> result = calibrator.calibrateModel(specStream, calibrationContext);

		assertTrue(result.isPresent());
		assertNotNull(result.get().getCalibratedModel());
		assertNotNull(calibrator.getCalibratedCurves());
	}

	@Test
	void testCalibrateModelWithEuriborFixings() throws Exception {
		LocalDateTime refDateTime = LocalDateTime.of(2023, 5, 5, 14, 35, 23);
		CalibrationContext ctx = new CalibrationContextImpl(refDateTime, 1E-5);

		// Create ESTR fixings for the past
		List<CalibrationDataItem> fixings = new ArrayList<>();
		CalibrationDataItem.Spec estrSpec = new CalibrationDataItem.Spec("ESTR1D", "ESTR", "Fixing", "1D");
		fixings.add(new CalibrationDataItem(estrSpec, 0.032, LocalDateTime.of(2023, 5, 4, 9, 0, 0)));
		fixings.add(new CalibrationDataItem(estrSpec, 0.031, LocalDateTime.of(2023, 5, 3, 9, 0, 0)));
		fixings.add(new CalibrationDataItem(estrSpec, 0.030, LocalDateTime.of(2023, 5, 2, 9, 0, 0)));

		// Create Euribor3M fixings
		CalibrationDataItem.Spec euribor3MSpec = new CalibrationDataItem.Spec("EUB3M", "Euribor3M", "Fixing", "3M");
		fixings.add(new CalibrationDataItem(euribor3MSpec, 0.035, LocalDateTime.of(2023, 5, 4, 11, 0, 0)));
		fixings.add(new CalibrationDataItem(euribor3MSpec, 0.034, LocalDateTime.of(2023, 5, 3, 11, 0, 0)));

		// Create Euribor6M fixings
		CalibrationDataItem.Spec euribor6MSpec = new CalibrationDataItem.Spec("EUB6M", "Euribor6M", "Fixing", "6M");
		fixings.add(new CalibrationDataItem(euribor6MSpec, 0.038, LocalDateTime.of(2023, 5, 4, 11, 0, 0)));
		fixings.add(new CalibrationDataItem(euribor6MSpec, 0.037, LocalDateTime.of(2023, 5, 3, 11, 0, 0)));

		// Create Euribor1M fixings
		CalibrationDataItem.Spec euribor1MSpec = new CalibrationDataItem.Spec("EUB1M", "Euribor1M", "Fixing", "1M");
		fixings.add(new CalibrationDataItem(euribor1MSpec, 0.033, LocalDateTime.of(2023, 5, 4, 11, 0, 0)));
		fixings.add(new CalibrationDataItem(euribor1MSpec, 0.032, LocalDateTime.of(2023, 5, 3, 11, 0, 0)));

		Calibrator calibrator = new Calibrator(fixings, ctx);

		// Use an OIS swap spec for calibration
		CalibrationSpecProvider oisProvider = new CalibrationSpecProviderOis("1Y", "annual", 0.032);
		Stream<CalibrationSpecProvider> providers = Stream.of(oisProvider);

		Optional<CalibrationResult> result = calibrator.calibrateModel(providers, ctx);

		assertTrue(result.isPresent());
		assertNotNull(calibrator.getCalibratedCurves());
	}
}
