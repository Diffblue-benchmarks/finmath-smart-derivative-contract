package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import net.finmath.marketdata.calibration.CalibratedCurves;
import net.finmath.smartcontract.product.SmartDerivativeContractDescriptor;
import net.finmath.smartcontract.product.xml.SDCXMLParser;
import net.finmath.smartcontract.valuation.client.ValuationClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CalibrationResultTest {

	private CalibrationResult calibrationResult;

	@BeforeAll
	void initializeTests() throws Exception {
		final String marketData = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net/finmath/smartcontract/valuation/client/md_testset_with_fixings.xml").readAllBytes(), StandardCharsets.UTF_8);
		final String productData = new String(ValuationClient.class.getClassLoader().getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract.xml").readAllBytes(), StandardCharsets.UTF_8);
		SmartDerivativeContractDescriptor productDescriptor = SDCXMLParser.parse(productData);

		final CalibrationDataset calibrationDataset = CalibrationParserDataItems.getCalibrationDataSetFromXML(marketData, productDescriptor.getMarketdataItemList());

		CalibrationContext calibrationContext = new CalibrationContextImpl(calibrationDataset.getDate().toLocalDate(), 1E-9);

		Stream<CalibrationSpecProvider> calibrationSpecsStream =
				calibrationDataset.getDataAsCalibrationDataPointStream(new CalibrationParserDataItems());
		CalibratedCurves.CalibrationSpec[] calibrationSpecs = calibrationSpecsStream
				.map(c -> c.getCalibrationSpec(calibrationContext))
				.toArray(CalibratedCurves.CalibrationSpec[]::new);

		List<CalibrationDataItem> fixings = calibrationDataset.getDataPoints().stream()
				.filter(cdi -> cdi.getSpec().getProductName().equals("Fixing") ||
						cdi.getSpec().getProductName().equals("Deposit"))
				.toList();
		Calibrator calibrator = new Calibrator(fixings, calibrationContext);

		Stream<CalibrationSpecProvider> calibrationSpecsStream2 =
				calibrationDataset.getDataAsCalibrationDataPointStream(new CalibrationParserDataItems());
		CalibrationResult result = calibrator.calibrateModel(calibrationSpecsStream2, calibrationContext)
				.orElseThrow();

		CalibratedCurves calibratedCurves = calibrator.getCalibratedCurves();
		calibrationResult = new CalibrationResult(calibratedCurves, calibrationSpecs);
	}

	@Test
	void getFreshness() {
		LocalTime freshness = calibrationResult.getFreshness();

		Assertions.assertNotNull(freshness);
	}

	@Test
	void getSumOfSquaredErrors() {
		double sumOfSquaredErrors = calibrationResult.getSumOfSquaredErrors();

		Assertions.assertTrue(sumOfSquaredErrors >= 0.0, "Sum of squared errors must be non-negative");
	}
}
