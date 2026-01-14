/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.product;

import net.finmath.marketdata.products.Swap;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for IRSwapGenerator.
 * Tests all methods in the swap generator with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class IRSwapGeneratorClaudeTest {

	/**
	 * Test generating a swap with 3M forward curve (quarterly frequency).
	 * Tests the branch: forwardCurveName.contains("3M") -> "quarterly"
	 */
	@Test
	void testGenerateAnalyticSwapObject_3MForwardCurve_Quarterly() {
		// Arrange
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final String maturityLabel = "1Y";
		final double notional = 1000000.0;
		final double fixRate = 0.03;
		final boolean isReceiveFix = true;
		final String forwardCurveName = "Forward-EUR-3M";
		final String discountCurveName = "Discount-EUR";

		// Act
		final Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				startDate, maturityLabel, notional, fixRate, isReceiveFix, forwardCurveName, discountCurveName);

		// Assert
		assertNotNull(swap, "Swap should be created with 3M forward curve");
	}

	/**
	 * Test generating a swap with 6M forward curve (semiannual frequency).
	 * Tests the branch: forwardCurveName.contains("6M") -> "semiannual"
	 */
	@Test
	void testGenerateAnalyticSwapObject_6MForwardCurve_Semiannual() {
		// Arrange
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final String maturityLabel = "2Y";
		final double notional = 500000.0;
		final double fixRate = 0.025;
		final boolean isReceiveFix = false;
		final String forwardCurveName = "Forward-EUR-6M";
		final String discountCurveName = "Discount-EUR";

		// Act
		final Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				startDate, maturityLabel, notional, fixRate, isReceiveFix, forwardCurveName, discountCurveName);

		// Assert
		assertNotNull(swap, "Swap should be created with 6M forward curve");
	}

	/**
	 * Test generating a swap with 1M forward curve (monthly frequency).
	 * Tests the branch: forwardCurveName.contains("1M") -> "monthly"
	 */
	@Test
	void testGenerateAnalyticSwapObject_1MForwardCurve_Monthly() {
		// Arrange
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final String maturityLabel = "1Y";
		final double notional = 2000000.0;
		final double fixRate = 0.04;
		final boolean isReceiveFix = true;
		final String forwardCurveName = "Forward-USD-1M";
		final String discountCurveName = "Discount-USD";

		// Act
		final Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				startDate, maturityLabel, notional, fixRate, isReceiveFix, forwardCurveName, discountCurveName);

		// Assert
		assertNotNull(swap, "Swap should be created with 1M forward curve");
	}

	/**
	 * Test generating a swap with no specific frequency marker (defaults to annual).
	 * Tests the default branch: -> "annual"
	 */
	@Test
	void testGenerateAnalyticSwapObject_NoFrequencyMarker_Annual() {
		// Arrange
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final String maturityLabel = "3Y";
		final double notional = 1500000.0;
		final double fixRate = 0.035;
		final boolean isReceiveFix = false;
		final String forwardCurveName = "Forward-EUR";
		final String discountCurveName = "Discount-EUR";

		// Act
		final Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				startDate, maturityLabel, notional, fixRate, isReceiveFix, forwardCurveName, discountCurveName);

		// Assert
		assertNotNull(swap, "Swap should be created with default annual frequency");
	}

	/**
	 * Test generating a swap with isReceiveFix = true.
	 * Tests the condition: isReceiveFix ? new Swap(fixLeg, floatLeg) : ...
	 */
	@Test
	void testGenerateAnalyticSwapObject_ReceiveFix_True() {
		// Arrange
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final String maturityLabel = "1Y";
		final double notional = 1000000.0;
		final double fixRate = 0.03;
		final boolean isReceiveFix = true;
		final String forwardCurveName = "Forward-EUR-3M";
		final String discountCurveName = "Discount-EUR";

		// Act
		final Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				startDate, maturityLabel, notional, fixRate, isReceiveFix, forwardCurveName, discountCurveName);

		// Assert
		assertNotNull(swap, "Swap should be created with isReceiveFix=true");
	}

	/**
	 * Test generating a swap with isReceiveFix = false.
	 * Tests the condition: isReceiveFix ? ... : new Swap(floatLeg, fixLeg)
	 */
	@Test
	void testGenerateAnalyticSwapObject_ReceiveFix_False() {
		// Arrange
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final String maturityLabel = "1Y";
		final double notional = 1000000.0;
		final double fixRate = 0.03;
		final boolean isReceiveFix = false;
		final String forwardCurveName = "Forward-EUR-3M";
		final String discountCurveName = "Discount-EUR";

		// Act
		final Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				startDate, maturityLabel, notional, fixRate, isReceiveFix, forwardCurveName, discountCurveName);

		// Assert
		assertNotNull(swap, "Swap should be created with isReceiveFix=false");
	}

	/**
	 * Test generating a swap with zero notional.
	 */
	@Test
	void testGenerateAnalyticSwapObject_ZeroNotional() {
		// Arrange
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final String maturityLabel = "1Y";
		final double notional = 0.0;
		final double fixRate = 0.03;
		final boolean isReceiveFix = true;
		final String forwardCurveName = "Forward-EUR-3M";
		final String discountCurveName = "Discount-EUR";

		// Act
		final Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				startDate, maturityLabel, notional, fixRate, isReceiveFix, forwardCurveName, discountCurveName);

		// Assert
		assertNotNull(swap, "Swap should be created with zero notional");
	}

	/**
	 * Test generating a swap with negative notional (reverse swap).
	 */
	@Test
	void testGenerateAnalyticSwapObject_NegativeNotional() {
		// Arrange
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final String maturityLabel = "1Y";
		final double notional = -1000000.0;
		final double fixRate = 0.03;
		final boolean isReceiveFix = true;
		final String forwardCurveName = "Forward-EUR-3M";
		final String discountCurveName = "Discount-EUR";

		// Act
		final Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				startDate, maturityLabel, notional, fixRate, isReceiveFix, forwardCurveName, discountCurveName);

		// Assert
		assertNotNull(swap, "Swap should be created with negative notional");
	}

	/**
	 * Test generating a swap with zero fix rate.
	 */
	@Test
	void testGenerateAnalyticSwapObject_ZeroFixRate() {
		// Arrange
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final String maturityLabel = "1Y";
		final double notional = 1000000.0;
		final double fixRate = 0.0;
		final boolean isReceiveFix = true;
		final String forwardCurveName = "Forward-EUR-3M";
		final String discountCurveName = "Discount-EUR";

		// Act
		final Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				startDate, maturityLabel, notional, fixRate, isReceiveFix, forwardCurveName, discountCurveName);

		// Assert
		assertNotNull(swap, "Swap should be created with zero fix rate");
	}

	/**
	 * Test generating a swap with negative fix rate.
	 */
	@Test
	void testGenerateAnalyticSwapObject_NegativeFixRate() {
		// Arrange
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final String maturityLabel = "1Y";
		final double notional = 1000000.0;
		final double fixRate = -0.01;
		final boolean isReceiveFix = false;
		final String forwardCurveName = "Forward-EUR-3M";
		final String discountCurveName = "Discount-EUR";

		// Act
		final Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				startDate, maturityLabel, notional, fixRate, isReceiveFix, forwardCurveName, discountCurveName);

		// Assert
		assertNotNull(swap, "Swap should be created with negative fix rate");
	}

	/**
	 * Test generating a swap with high fix rate.
	 */
	@Test
	void testGenerateAnalyticSwapObject_HighFixRate() {
		// Arrange
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final String maturityLabel = "1Y";
		final double notional = 1000000.0;
		final double fixRate = 0.15;
		final boolean isReceiveFix = true;
		final String forwardCurveName = "Forward-EUR-3M";
		final String discountCurveName = "Discount-EUR";

		// Act
		final Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				startDate, maturityLabel, notional, fixRate, isReceiveFix, forwardCurveName, discountCurveName);

		// Assert
		assertNotNull(swap, "Swap should be created with high fix rate");
	}

	/**
	 * Test generating a swap with short maturity (6 months).
	 */
	@Test
	void testGenerateAnalyticSwapObject_ShortMaturity_6M() {
		// Arrange
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final String maturityLabel = "6M";
		final double notional = 1000000.0;
		final double fixRate = 0.03;
		final boolean isReceiveFix = true;
		final String forwardCurveName = "Forward-EUR-3M";
		final String discountCurveName = "Discount-EUR";

		// Act
		final Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				startDate, maturityLabel, notional, fixRate, isReceiveFix, forwardCurveName, discountCurveName);

		// Assert
		assertNotNull(swap, "Swap should be created with 6M maturity");
	}

	/**
	 * Test generating a swap with long maturity (10 years).
	 */
	@Test
	void testGenerateAnalyticSwapObject_LongMaturity_10Y() {
		// Arrange
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final String maturityLabel = "10Y";
		final double notional = 5000000.0;
		final double fixRate = 0.035;
		final boolean isReceiveFix = false;
		final String forwardCurveName = "Forward-EUR-6M";
		final String discountCurveName = "Discount-EUR";

		// Act
		final Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				startDate, maturityLabel, notional, fixRate, isReceiveFix, forwardCurveName, discountCurveName);

		// Assert
		assertNotNull(swap, "Swap should be created with 10Y maturity");
	}

	/**
	 * Test generating a swap with maturity in days.
	 */
	@Test
	void testGenerateAnalyticSwapObject_MaturityInDays() {
		// Arrange
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final String maturityLabel = "90D";
		final double notional = 500000.0;
		final double fixRate = 0.025;
		final boolean isReceiveFix = true;
		final String forwardCurveName = "Forward-EUR-1M";
		final String discountCurveName = "Discount-EUR";

		// Act
		final Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				startDate, maturityLabel, notional, fixRate, isReceiveFix, forwardCurveName, discountCurveName);

		// Assert
		assertNotNull(swap, "Swap should be created with 90D maturity");
	}

	/**
	 * Test generating a swap with different start date (mid-year).
	 */
	@Test
	void testGenerateAnalyticSwapObject_MidYearStartDate() {
		// Arrange
		final LocalDate startDate = LocalDate.of(2024, 7, 15);
		final String maturityLabel = "2Y";
		final double notional = 1000000.0;
		final double fixRate = 0.03;
		final boolean isReceiveFix = true;
		final String forwardCurveName = "Forward-EUR-3M";
		final String discountCurveName = "Discount-EUR";

		// Act
		final Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				startDate, maturityLabel, notional, fixRate, isReceiveFix, forwardCurveName, discountCurveName);

		// Assert
		assertNotNull(swap, "Swap should be created with mid-year start date");
	}

	/**
	 * Test generating a swap with weekend start date.
	 */
	@Test
	void testGenerateAnalyticSwapObject_WeekendStartDate() {
		// Arrange
		final LocalDate startDate = LocalDate.of(2024, 1, 6); // Saturday
		final String maturityLabel = "1Y";
		final double notional = 1000000.0;
		final double fixRate = 0.03;
		final boolean isReceiveFix = false;
		final String forwardCurveName = "Forward-EUR-3M";
		final String discountCurveName = "Discount-EUR";

		// Act
		final Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				startDate, maturityLabel, notional, fixRate, isReceiveFix, forwardCurveName, discountCurveName);

		// Assert
		assertNotNull(swap, "Swap should be created with weekend start date");
	}

	/**
	 * Test generating a swap with large notional.
	 */
	@Test
	void testGenerateAnalyticSwapObject_LargeNotional() {
		// Arrange
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final String maturityLabel = "5Y";
		final double notional = 1_000_000_000.0; // 1 billion
		final double fixRate = 0.03;
		final boolean isReceiveFix = true;
		final String forwardCurveName = "Forward-EUR-6M";
		final String discountCurveName = "Discount-EUR";

		// Act
		final Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				startDate, maturityLabel, notional, fixRate, isReceiveFix, forwardCurveName, discountCurveName);

		// Assert
		assertNotNull(swap, "Swap should be created with large notional");
	}

	/**
	 * Test generating a swap with different currency curve names.
	 */
	@Test
	void testGenerateAnalyticSwapObject_DifferentCurrencyCurves() {
		// Arrange
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final String maturityLabel = "3Y";
		final double notional = 1000000.0;
		final double fixRate = 0.04;
		final boolean isReceiveFix = true;
		final String forwardCurveName = "Forward-USD-3M";
		final String discountCurveName = "Discount-USD";

		// Act
		final Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				startDate, maturityLabel, notional, fixRate, isReceiveFix, forwardCurveName, discountCurveName);

		// Assert
		assertNotNull(swap, "Swap should be created with USD curves");
	}

	/**
	 * Test generating a swap with curve names containing multiple frequency markers.
	 * Should match the first one in the ternary chain (3M).
	 */
	@Test
	void testGenerateAnalyticSwapObject_MultipleFrequencyMarkers() {
		// Arrange
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final String maturityLabel = "1Y";
		final double notional = 1000000.0;
		final double fixRate = 0.03;
		final boolean isReceiveFix = true;
		final String forwardCurveName = "Forward-3M-6M"; // Contains both 3M and 6M
		final String discountCurveName = "Discount-EUR";

		// Act
		final Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				startDate, maturityLabel, notional, fixRate, isReceiveFix, forwardCurveName, discountCurveName);

		// Assert
		assertNotNull(swap, "Swap should be created with multiple frequency markers");
	}

	/**
	 * Test generating a swap with empty curve names.
	 */
	@Test
	void testGenerateAnalyticSwapObject_EmptyCurveNames() {
		// Arrange
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final String maturityLabel = "1Y";
		final double notional = 1000000.0;
		final double fixRate = 0.03;
		final boolean isReceiveFix = true;
		final String forwardCurveName = "";
		final String discountCurveName = "";

		// Act
		final Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				startDate, maturityLabel, notional, fixRate, isReceiveFix, forwardCurveName, discountCurveName);

		// Assert
		assertNotNull(swap, "Swap should be created with empty curve names");
	}

	/**
	 * Test generating a swap with very small notional.
	 */
	@Test
	void testGenerateAnalyticSwapObject_VerySmallNotional() {
		// Arrange
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final String maturityLabel = "1Y";
		final double notional = 0.01;
		final double fixRate = 0.03;
		final boolean isReceiveFix = true;
		final String forwardCurveName = "Forward-EUR-3M";
		final String discountCurveName = "Discount-EUR";

		// Act
		final Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				startDate, maturityLabel, notional, fixRate, isReceiveFix, forwardCurveName, discountCurveName);

		// Assert
		assertNotNull(swap, "Swap should be created with very small notional");
	}

	/**
	 * Test generating a swap with very small fix rate.
	 */
	@Test
	void testGenerateAnalyticSwapObject_VerySmallFixRate() {
		// Arrange
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final String maturityLabel = "1Y";
		final double notional = 1000000.0;
		final double fixRate = 0.0001;
		final boolean isReceiveFix = true;
		final String forwardCurveName = "Forward-EUR-3M";
		final String discountCurveName = "Discount-EUR";

		// Act
		final Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				startDate, maturityLabel, notional, fixRate, isReceiveFix, forwardCurveName, discountCurveName);

		// Assert
		assertNotNull(swap, "Swap should be created with very small fix rate");
	}

	/**
	 * Test with maturity in weeks.
	 */
	@Test
	void testGenerateAnalyticSwapObject_MaturityInWeeks() {
		// Arrange
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final String maturityLabel = "4W";
		final double notional = 1000000.0;
		final double fixRate = 0.03;
		final boolean isReceiveFix = true;
		final String forwardCurveName = "Forward-EUR-1M";
		final String discountCurveName = "Discount-EUR";

		// Act
		final Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				startDate, maturityLabel, notional, fixRate, isReceiveFix, forwardCurveName, discountCurveName);

		// Assert
		assertNotNull(swap, "Swap should be created with maturity in weeks");
	}

	/**
	 * Test with 30 year maturity (very long).
	 */
	@Test
	void testGenerateAnalyticSwapObject_VeryLongMaturity_30Y() {
		// Arrange
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final String maturityLabel = "30Y";
		final double notional = 10000000.0;
		final double fixRate = 0.04;
		final boolean isReceiveFix = false;
		final String forwardCurveName = "Forward-EUR-6M";
		final String discountCurveName = "Discount-EUR";

		// Act
		final Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				startDate, maturityLabel, notional, fixRate, isReceiveFix, forwardCurveName, discountCurveName);

		// Assert
		assertNotNull(swap, "Swap should be created with 30Y maturity");
	}

	/**
	 * Test with year-end start date.
	 */
	@Test
	void testGenerateAnalyticSwapObject_YearEndStartDate() {
		// Arrange
		final LocalDate startDate = LocalDate.of(2023, 12, 29);
		final String maturityLabel = "1Y";
		final double notional = 1000000.0;
		final double fixRate = 0.03;
		final boolean isReceiveFix = true;
		final String forwardCurveName = "Forward-EUR-3M";
		final String discountCurveName = "Discount-EUR";

		// Act
		final Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				startDate, maturityLabel, notional, fixRate, isReceiveFix, forwardCurveName, discountCurveName);

		// Assert
		assertNotNull(swap, "Swap should be created with year-end start date");
	}

	/**
	 * Test with leap year date.
	 */
	@Test
	void testGenerateAnalyticSwapObject_LeapYearDate() {
		// Arrange
		final LocalDate startDate = LocalDate.of(2024, 2, 29); // Leap year
		final String maturityLabel = "1Y";
		final double notional = 1000000.0;
		final double fixRate = 0.03;
		final boolean isReceiveFix = true;
		final String forwardCurveName = "Forward-EUR-3M";
		final String discountCurveName = "Discount-EUR";

		// Act
		final Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				startDate, maturityLabel, notional, fixRate, isReceiveFix, forwardCurveName, discountCurveName);

		// Assert
		assertNotNull(swap, "Swap should be created with leap year date");
	}

	/**
	 * Test with GBP currency curves.
	 */
	@Test
	void testGenerateAnalyticSwapObject_GBPCurves() {
		// Arrange
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final String maturityLabel = "2Y";
		final double notional = 1000000.0;
		final double fixRate = 0.035;
		final boolean isReceiveFix = false;
		final String forwardCurveName = "Forward-GBP-6M";
		final String discountCurveName = "Discount-GBP";

		// Act
		final Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				startDate, maturityLabel, notional, fixRate, isReceiveFix, forwardCurveName, discountCurveName);

		// Assert
		assertNotNull(swap, "Swap should be created with GBP curves");
	}

	/**
	 * Test with 5Y maturity.
	 */
	@Test
	void testGenerateAnalyticSwapObject_5YMaturity() {
		// Arrange
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final String maturityLabel = "5Y";
		final double notional = 3000000.0;
		final double fixRate = 0.032;
		final boolean isReceiveFix = true;
		final String forwardCurveName = "Forward-EUR-3M";
		final String discountCurveName = "Discount-EUR";

		// Act
		final Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				startDate, maturityLabel, notional, fixRate, isReceiveFix, forwardCurveName, discountCurveName);

		// Assert
		assertNotNull(swap, "Swap should be created with 5Y maturity");
	}

	/**
	 * Test with curve name containing only 6M (no 3M or 1M).
	 */
	@Test
	void testGenerateAnalyticSwapObject_Only6MInCurveName() {
		// Arrange
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final String maturityLabel = "1Y";
		final double notional = 1000000.0;
		final double fixRate = 0.03;
		final boolean isReceiveFix = true;
		final String forwardCurveName = "EURIBOR-6M";
		final String discountCurveName = "Discount-EUR";

		// Act
		final Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				startDate, maturityLabel, notional, fixRate, isReceiveFix, forwardCurveName, discountCurveName);

		// Assert
		assertNotNull(swap, "Swap should be created with 6M curve");
	}

	/**
	 * Test with curve name containing only 1M (no 3M or 6M before it).
	 */
	@Test
	void testGenerateAnalyticSwapObject_Only1MInCurveName() {
		// Arrange
		final LocalDate startDate = LocalDate.of(2024, 1, 2);
		final String maturityLabel = "1Y";
		final double notional = 1000000.0;
		final double fixRate = 0.03;
		final boolean isReceiveFix = true;
		final String forwardCurveName = "LIBOR-1M";
		final String discountCurveName = "Discount-USD";

		// Act
		final Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				startDate, maturityLabel, notional, fixRate, isReceiveFix, forwardCurveName, discountCurveName);

		// Assert
		assertNotNull(swap, "Swap should be created with 1M curve");
	}
}
