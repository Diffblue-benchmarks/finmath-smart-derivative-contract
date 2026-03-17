package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import net.finmath.marketdata.calibration.CalibratedCurves;
import net.finmath.smartcontract.product.SmartDerivativeContractDescriptor;
import net.finmath.smartcontract.product.xml.SDCXMLParser;
import net.finmath.smartcontract.valuation.client.ValuationClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for CalibrationResult class.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CalibrationResultTest {
	private CalibrationResult calibrationResult;
	private CalibratedCurves calibratedCurves;
	private CalibratedCurves.CalibrationSpec[] calibrationSpecs;

	@BeforeAll
	void initializeTests() throws Exception {
		final String marketData = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net/finmath/smartcontract/valuation/client/md_testset_with_fixings.xml").readAllBytes(), StandardCharsets.UTF_8);
		final String productData = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract.xml").readAllBytes(), StandardCharsets.UTF_8);
		SmartDerivativeContractDescriptor productDescriptor = SDCXMLParser.parse(productData);

		final CalibrationDataset calibrationDataset = CalibrationParserDataItems.getCalibrationDataSetFromXML(marketData, productDescriptor.getMarketdataItemList());
		CalibrationContext calibrationContext = new CalibrationContextImpl(calibrationDataset.getDate().toLocalDate(), 1E-9);

		Stream<CalibrationSpecProvider> calibrationSpecsDataPointStream = calibrationDataset.getDataAsCalibrationDataPointStream(new CalibrationParserDataItems());
		calibrationSpecs = calibrationSpecsDataPointStream.map(c -> c.getCalibrationSpec(calibrationContext)).toArray(CalibratedCurves.CalibrationSpec[]::new);

		calibrationSpecsDataPointStream = calibrationDataset.getDataAsCalibrationDataPointStream(new CalibrationParserDataItems());
		List<CalibrationDataItem> fixings = calibrationDataset.getDataPoints().stream()
				.filter(cdi -> cdi.getSpec().getProductName().equals("Fixing") || cdi.getSpec().getProductName().equals("Deposit"))
				.toList();

		Calibrator calibrator = new Calibrator(fixings, calibrationContext);
		calibrator.calibrateModel(calibrationSpecsDataPointStream, calibrationContext).orElseThrow();

		calibratedCurves = calibrator.getCalibratedCurves();
		calibrationResult = new CalibrationResult(calibratedCurves, calibrationSpecs);
	}

	@Test
	void testGetSumOfSquaredErrors() {
		double sumOfSquaredErrors = calibrationResult.getSumOfSquaredErrors();

		assertNotNull(calibrationResult.getCalibratedModel());
		assertTrue(sumOfSquaredErrors >= 0.0, "Sum of squared errors should be non-negative");
		assertTrue(Double.isFinite(sumOfSquaredErrors), "Sum of squared errors should be finite");
	}

	@Test
	void testGetFreshness() {
		LocalTime freshness = calibrationResult.getFreshness();

		assertNotNull(freshness, "Freshness should not be null");
		assertTrue(freshness.isBefore(LocalTime.now().plusSeconds(1)), "Freshness should be before or very close to current time");
	}

	@Test
	void testGetCalibratedModel() {
		assertNotNull(calibrationResult.getCalibratedModel(), "Calibrated model should not be null");
	}
}
