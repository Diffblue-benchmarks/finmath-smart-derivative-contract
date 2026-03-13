package net.finmath.smartcontract.product;

import net.finmath.marketdata.products.Swap;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link IRSwapGenerator}.
 * Tests swap generation with various forward curve names and receiver/payer configurations.
 */
class IRSwapGeneratorTest {

	private static final LocalDate START_DATE = LocalDate.of(2024, 1, 15);
	private static final String MATURITY_LABEL = "5Y";
	private static final double NOTIONAL = 1_000_000.0;
	private static final double FIX_RATE = 0.025;
	private static final String DISCOUNT_CURVE = "discountCurve-EUR";

	@Test
	void testGenerateSwapWith6MForwardCurve() {
		Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				START_DATE, MATURITY_LABEL, NOTIONAL, FIX_RATE,
				true, "forward-EUR-6M", DISCOUNT_CURVE);

		assertNotNull(swap, "Generated swap with 6M forward curve should not be null");
	}

	@Test
	void testGenerateSwapWith3MForwardCurve() {
		Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				START_DATE, MATURITY_LABEL, NOTIONAL, FIX_RATE,
				true, "forward-EUR-3M", DISCOUNT_CURVE);

		assertNotNull(swap, "Generated swap with 3M forward curve should not be null");
	}

	@Test
	void testGenerateSwapReceiveFixTrue() {
		Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				START_DATE, MATURITY_LABEL, NOTIONAL, FIX_RATE,
				true, "forward-EUR-6M", DISCOUNT_CURVE);

		assertNotNull(swap, "Generated swap with isReceiveFix=true should not be null");
	}

	@Test
	void testGenerateSwapReceiveFixFalse() {
		Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				START_DATE, MATURITY_LABEL, NOTIONAL, FIX_RATE,
				false, "forward-EUR-6M", DISCOUNT_CURVE);

		assertNotNull(swap, "Generated swap with isReceiveFix=false should not be null");
	}

	@Test
	void testGenerateSwapBothDirectionsReturnDistinctObjects() {
		Swap receiverSwap = IRSwapGenerator.generateAnalyticSwapObject(
				START_DATE, MATURITY_LABEL, NOTIONAL, FIX_RATE,
				true, "forward-EUR-6M", DISCOUNT_CURVE);

		Swap payerSwap = IRSwapGenerator.generateAnalyticSwapObject(
				START_DATE, MATURITY_LABEL, NOTIONAL, FIX_RATE,
				false, "forward-EUR-6M", DISCOUNT_CURVE);

		assertNotNull(receiverSwap);
		assertNotNull(payerSwap);
		assertNotSame(receiverSwap, payerSwap, "Receiver and payer swaps should be distinct objects");
	}

	@Test
	void testGenerateSwapWith1MForwardCurve() {
		Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				START_DATE, MATURITY_LABEL, NOTIONAL, FIX_RATE,
				true, "forward-EUR-1M", DISCOUNT_CURVE);

		assertNotNull(swap, "Generated swap with 1M forward curve should not be null");
	}

	@Test
	void testGenerateSwapWithAnnualFallbackForwardCurve() {
		Swap swap = IRSwapGenerator.generateAnalyticSwapObject(
				START_DATE, MATURITY_LABEL, NOTIONAL, FIX_RATE,
				true, "forward-EUR-12M", DISCOUNT_CURVE);

		assertNotNull(swap, "Generated swap with annual fallback forward curve should not be null");
	}
}
