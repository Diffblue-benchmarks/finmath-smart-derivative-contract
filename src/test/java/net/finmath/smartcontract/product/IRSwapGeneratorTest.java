package net.finmath.smartcontract.product;

import net.finmath.marketdata.products.Swap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class IRSwapGeneratorTest {

	@Test
	void testGenerateAnalyticSwapObjectReceiveFix() {
		LocalDate startDate = LocalDate.of(2023, 1, 3);
		String maturityLabel = "5Y";
		double notional = 1000000.0;
		double fixRate = 0.02;
		String forwardCurveName = "forward-EUR-6M";
		String discountCurveName = "discount-EUR-OIS";

		Swap swap = IRSwapGenerator.generateAnalyticSwapObject(startDate, maturityLabel, notional, fixRate, true, forwardCurveName, discountCurveName);

		Assertions.assertNotNull(swap, "Swap object should not be null");
	}

	@Test
	void testGenerateAnalyticSwapObjectPayFix() {
		LocalDate startDate = LocalDate.of(2023, 1, 3);
		String maturityLabel = "5Y";
		double notional = 1000000.0;
		double fixRate = 0.02;
		String forwardCurveName = "forward-EUR-6M";
		String discountCurveName = "discount-EUR-OIS";

		Swap swap = IRSwapGenerator.generateAnalyticSwapObject(startDate, maturityLabel, notional, fixRate, false, forwardCurveName, discountCurveName);

		Assertions.assertNotNull(swap, "Swap object should not be null");
	}

	@Test
	void testGenerateAnalyticSwapObjectWith3MForwardCurve() {
		LocalDate startDate = LocalDate.of(2023, 1, 3);
		String maturityLabel = "3Y";
		double notional = 500000.0;
		double fixRate = 0.015;
		String forwardCurveName = "forward-EUR-3M";
		String discountCurveName = "discount-EUR-OIS";

		Swap swap = IRSwapGenerator.generateAnalyticSwapObject(startDate, maturityLabel, notional, fixRate, true, forwardCurveName, discountCurveName);

		Assertions.assertNotNull(swap, "Swap with 3M forward curve should not be null");
	}

	@Test
	void testGenerateAnalyticSwapObjectWith1MForwardCurve() {
		LocalDate startDate = LocalDate.of(2023, 1, 3);
		String maturityLabel = "2Y";
		double notional = 200000.0;
		double fixRate = 0.01;
		String forwardCurveName = "forward-EUR-1M";
		String discountCurveName = "discount-EUR-OIS";

		Swap swap = IRSwapGenerator.generateAnalyticSwapObject(startDate, maturityLabel, notional, fixRate, false, forwardCurveName, discountCurveName);

		Assertions.assertNotNull(swap, "Swap with 1M forward curve should not be null");
	}

	@Test
	void testGenerateAnalyticSwapObjectWithAnnualFrequency() {
		LocalDate startDate = LocalDate.of(2023, 1, 3);
		String maturityLabel = "10Y";
		double notional = 2000000.0;
		double fixRate = 0.025;
		String forwardCurveName = "forward-EUR-annual";
		String discountCurveName = "discount-EUR-OIS";

		Swap swap = IRSwapGenerator.generateAnalyticSwapObject(startDate, maturityLabel, notional, fixRate, true, forwardCurveName, discountCurveName);

		Assertions.assertNotNull(swap, "Swap with annual frequency should not be null");
	}
}
