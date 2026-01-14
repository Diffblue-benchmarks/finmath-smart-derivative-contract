/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for CalibrationDataItem.
 * Tests all methods with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class CalibrationDataItemClaudeTest {

	/**
	 * Helper method to create a valid Spec instance.
	 */
	private CalibrationDataItem.Spec createSpec(String key, String curveName, String productName, String maturity) {
		return new CalibrationDataItem.Spec(key, curveName, productName, maturity);
	}

	/**
	 * Helper method to create a valid CalibrationDataItem instance.
	 */
	private CalibrationDataItem createValidItem() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		Double quote = 0.025;
		LocalDateTime dateTime = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		return new CalibrationDataItem(spec, quote, dateTime);
	}

	// Constructor tests

	@Test
	void testConstructor_WithAllParameters() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		Double quote = 0.025;
		LocalDateTime dateTime = LocalDateTime.of(2024, 1, 15, 10, 0, 0);

		CalibrationDataItem item = new CalibrationDataItem(spec, quote, dateTime);

		assertNotNull(item);
		assertEquals(spec, item.getSpec());
		assertEquals(quote, item.getQuote());
		assertEquals(dateTime, item.getDateTime());
	}

	@Test
	void testConstructor_WithNullQuote() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		LocalDateTime dateTime = LocalDateTime.of(2024, 1, 15, 10, 0, 0);

		CalibrationDataItem item = new CalibrationDataItem(spec, null, dateTime);

		assertNotNull(item);
		assertNull(item.getQuote());
	}

	@Test
	void testConstructor_WithNullDateTime() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		Double quote = 0.025;

		CalibrationDataItem item = new CalibrationDataItem(spec, quote, null);

		assertNotNull(item);
		assertNull(item.getDateTime());
	}

	// getClonedScaled tests

	@Test
	void testGetClonedScaled_WithPositiveFactor() {
		CalibrationDataItem item = createValidItem();
		double factor = 2.0;

		CalibrationDataItem scaled = item.getClonedScaled(factor);

		assertNotNull(scaled);
		assertEquals(item.getQuote() / factor, scaled.getQuote(), 1e-10);
		assertEquals(item.getSpec(), scaled.getSpec());
		assertEquals(item.getDateTime(), scaled.getDateTime());
		assertNotSame(item, scaled);
	}

	@Test
	void testGetClonedScaled_WithFractionFactor() {
		CalibrationDataItem item = createValidItem();
		double factor = 0.5;

		CalibrationDataItem scaled = item.getClonedScaled(factor);

		assertNotNull(scaled);
		assertEquals(item.getQuote() / factor, scaled.getQuote(), 1e-10);
	}

	@Test
	void testGetClonedScaled_WithNegativeFactor() {
		CalibrationDataItem item = createValidItem();
		double factor = -2.0;

		CalibrationDataItem scaled = item.getClonedScaled(factor);

		assertNotNull(scaled);
		assertEquals(item.getQuote() / factor, scaled.getQuote(), 1e-10);
		assertTrue(scaled.getQuote() < 0);
	}

	@Test
	void testGetClonedScaled_WithFactorOne() {
		CalibrationDataItem item = createValidItem();
		double factor = 1.0;

		CalibrationDataItem scaled = item.getClonedScaled(factor);

		assertNotNull(scaled);
		assertEquals(item.getQuote(), scaled.getQuote(), 1e-10);
	}

	// getClonedShifted tests

	@Test
	void testGetClonedShifted_WithPositiveAmount() {
		CalibrationDataItem item = createValidItem();
		double amount = 0.01;

		CalibrationDataItem shifted = item.getClonedShifted(amount);

		assertNotNull(shifted);
		assertEquals(item.getQuote() + amount, shifted.getQuote(), 1e-10);
		assertEquals(item.getSpec(), shifted.getSpec());
		assertEquals(item.getDateTime(), shifted.getDateTime());
		assertNotSame(item, shifted);
	}

	@Test
	void testGetClonedShifted_WithNegativeAmount() {
		CalibrationDataItem item = createValidItem();
		double amount = -0.01;

		CalibrationDataItem shifted = item.getClonedShifted(amount);

		assertNotNull(shifted);
		assertEquals(item.getQuote() + amount, shifted.getQuote(), 1e-10);
	}

	@Test
	void testGetClonedShifted_WithZeroAmount() {
		CalibrationDataItem item = createValidItem();
		double amount = 0.0;

		CalibrationDataItem shifted = item.getClonedShifted(amount);

		assertNotNull(shifted);
		assertEquals(item.getQuote(), shifted.getQuote(), 1e-10);
	}

	// getSpec tests

	@Test
	void testGetSpec() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.025, LocalDateTime.now());

		CalibrationDataItem.Spec retrievedSpec = item.getSpec();

		assertNotNull(retrievedSpec);
		assertSame(spec, retrievedSpec);
	}

	// getCurveName tests

	@Test
	void testGetCurveName() {
		CalibrationDataItem item = createValidItem();

		String curveName = item.getCurveName();

		assertEquals("EUR-EONIA", curveName);
	}

	@Test
	void testGetCurveName_WithDifferentCurve() {
		CalibrationDataItem.Spec spec = createSpec("key1", "USD-SOFR", "SWAP", "5Y");
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.025, LocalDateTime.now());

		String curveName = item.getCurveName();

		assertEquals("USD-SOFR", curveName);
	}

	// getProductName tests

	@Test
	void testGetProductName() {
		CalibrationDataItem item = createValidItem();

		String productName = item.getProductName();

		assertEquals("SWAP", productName);
	}

	@Test
	void testGetProductName_WithDifferentProduct() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "FRA", "3M");
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.015, LocalDateTime.now());

		String productName = item.getProductName();

		assertEquals("FRA", productName);
	}

	// getMaturity tests

	@Test
	void testGetMaturity() {
		CalibrationDataItem item = createValidItem();

		String maturity = item.getMaturity();

		assertEquals("5Y", maturity);
	}

	@Test
	void testGetMaturity_WithDifferentMaturity() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "10Y");
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.03, LocalDateTime.now());

		String maturity = item.getMaturity();

		assertEquals("10Y", maturity);
	}

	// getQuote tests

	@Test
	void testGetQuote() {
		Double quote = 0.025;
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		CalibrationDataItem item = new CalibrationDataItem(spec, quote, LocalDateTime.now());

		Double retrievedQuote = item.getQuote();

		assertEquals(quote, retrievedQuote);
	}

	@Test
	void testGetQuote_WithNullQuote() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		CalibrationDataItem item = new CalibrationDataItem(spec, null, LocalDateTime.now());

		Double retrievedQuote = item.getQuote();

		assertNull(retrievedQuote);
	}

	// getDaysToMaturity tests

	@Test
	void testGetDaysToMaturity_WithDays() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "30D");
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.025, LocalDateTime.now());

		Integer days = item.getDaysToMaturity();

		assertEquals(30, days);
	}

	@Test
	void testGetDaysToMaturity_WithMonths() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "6M");
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.025, LocalDateTime.now());

		Integer days = item.getDaysToMaturity();

		assertEquals(180, days); // 6 * 30
	}

	@Test
	void testGetDaysToMaturity_WithYears() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.025, LocalDateTime.now());

		Integer days = item.getDaysToMaturity();

		assertEquals(1800, days); // 5 * 360
	}

	@Test
	void testGetDaysToMaturity_WithOneDay() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "1D");
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.025, LocalDateTime.now());

		Integer days = item.getDaysToMaturity();

		assertEquals(1, days);
	}

	@Test
	void testGetDaysToMaturity_WithOneMonth() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "1M");
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.025, LocalDateTime.now());

		Integer days = item.getDaysToMaturity();

		assertEquals(30, days);
	}

	@Test
	void testGetDaysToMaturity_WithOneYear() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "1Y");
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.025, LocalDateTime.now());

		Integer days = item.getDaysToMaturity();

		assertEquals(360, days);
	}

	@Test
	void testGetDaysToMaturity_WithTenYears() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "10Y");
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.03, LocalDateTime.now());

		Integer days = item.getDaysToMaturity();

		assertEquals(3600, days); // 10 * 360
	}

	@Test
	void testGetDaysToMaturity_WithUnknownUnit() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5W");
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.025, LocalDateTime.now());

		Integer days = item.getDaysToMaturity();

		assertEquals(0, days); // Unknown unit returns 0
	}

	// getDateString tests

	@Test
	void testGetDateString() {
		LocalDateTime dateTime = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.025, dateTime);

		String dateString = item.getDateString();

		assertEquals("2024-01-15", dateString);
	}

	@Test
	void testGetDateString_WithDifferentDate() {
		LocalDateTime dateTime = LocalDateTime.of(2023, 12, 31, 23, 59, 59);
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.025, dateTime);

		String dateString = item.getDateString();

		assertEquals("2023-12-31", dateString);
	}

	@Test
	void testGetDateString_WithLeapYearDate() {
		LocalDateTime dateTime = LocalDateTime.of(2024, 2, 29, 12, 0, 0);
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.025, dateTime);

		String dateString = item.getDateString();

		assertEquals("2024-02-29", dateString);
	}

	// getDate tests

	@Test
	void testGetDate() {
		LocalDateTime dateTime = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.025, dateTime);

		LocalDate date = item.getDate();

		assertNotNull(date);
		assertEquals(LocalDate.of(2024, 1, 15), date);
	}

	@Test
	void testGetDate_WithDifferentDateTime() {
		LocalDateTime dateTime = LocalDateTime.of(2023, 6, 30, 23, 59, 59);
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.025, dateTime);

		LocalDate date = item.getDate();

		assertEquals(LocalDate.of(2023, 6, 30), date);
	}

	// getDateTime tests

	@Test
	void testGetDateTime() {
		LocalDateTime dateTime = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.025, dateTime);

		LocalDateTime retrievedDateTime = item.getDateTime();

		assertNotNull(retrievedDateTime);
		assertEquals(dateTime, retrievedDateTime);
	}

	@Test
	void testGetDateTime_WithNullDateTime() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		CalibrationDataItem item = new CalibrationDataItem(spec, 0.025, null);

		LocalDateTime retrievedDateTime = item.getDateTime();

		assertNull(retrievedDateTime);
	}

	// equals tests

	@Test
	void testEquals_SameInstance() {
		CalibrationDataItem item = createValidItem();

		boolean result = item.equals(item);

		assertTrue(result);
	}

	@Test
	void testEquals_EqualObjects() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		Double quote = 0.025;
		LocalDateTime dateTime = LocalDateTime.of(2024, 1, 15, 10, 0, 0);

		CalibrationDataItem item1 = new CalibrationDataItem(spec, quote, dateTime);
		CalibrationDataItem item2 = new CalibrationDataItem(spec, quote, dateTime);

		boolean result = item1.equals(item2);

		assertTrue(result);
	}

	@Test
	void testEquals_DifferentSpec() {
		CalibrationDataItem.Spec spec1 = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		CalibrationDataItem.Spec spec2 = createSpec("key2", "USD-SOFR", "SWAP", "5Y");
		Double quote = 0.025;
		LocalDateTime dateTime = LocalDateTime.of(2024, 1, 15, 10, 0, 0);

		CalibrationDataItem item1 = new CalibrationDataItem(spec1, quote, dateTime);
		CalibrationDataItem item2 = new CalibrationDataItem(spec2, quote, dateTime);

		boolean result = item1.equals(item2);

		assertFalse(result);
	}

	@Test
	void testEquals_DifferentQuote() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		LocalDateTime dateTime = LocalDateTime.of(2024, 1, 15, 10, 0, 0);

		CalibrationDataItem item1 = new CalibrationDataItem(spec, 0.025, dateTime);
		CalibrationDataItem item2 = new CalibrationDataItem(spec, 0.030, dateTime);

		boolean result = item1.equals(item2);

		assertFalse(result);
	}

	@Test
	void testEquals_DifferentDateTime() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		Double quote = 0.025;

		CalibrationDataItem item1 = new CalibrationDataItem(spec, quote, LocalDateTime.of(2024, 1, 15, 10, 0, 0));
		CalibrationDataItem item2 = new CalibrationDataItem(spec, quote, LocalDateTime.of(2024, 1, 16, 10, 0, 0));

		boolean result = item1.equals(item2);

		assertFalse(result);
	}

	@Test
	void testEquals_WithNull() {
		CalibrationDataItem item = createValidItem();

		boolean result = item.equals(null);

		assertFalse(result);
	}

	@Test
	void testEquals_WithDifferentClass() {
		CalibrationDataItem item = createValidItem();
		String otherObject = "not a CalibrationDataItem";

		boolean result = item.equals(otherObject);

		assertFalse(result);
	}

	@Test
	void testEquals_WithNullQuotes() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		LocalDateTime dateTime = LocalDateTime.of(2024, 1, 15, 10, 0, 0);

		CalibrationDataItem item1 = new CalibrationDataItem(spec, null, dateTime);
		CalibrationDataItem item2 = new CalibrationDataItem(spec, null, dateTime);

		boolean result = item1.equals(item2);

		assertTrue(result);
	}

	@Test
	void testEquals_OneNullQuote() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		LocalDateTime dateTime = LocalDateTime.of(2024, 1, 15, 10, 0, 0);

		CalibrationDataItem item1 = new CalibrationDataItem(spec, 0.025, dateTime);
		CalibrationDataItem item2 = new CalibrationDataItem(spec, null, dateTime);

		boolean result = item1.equals(item2);

		assertFalse(result);
	}

	@Test
	void testEquals_WithNullDateTimes() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		Double quote = 0.025;

		CalibrationDataItem item1 = new CalibrationDataItem(spec, quote, null);
		CalibrationDataItem item2 = new CalibrationDataItem(spec, quote, null);

		boolean result = item1.equals(item2);

		assertTrue(result);
	}

	@Test
	void testEquals_OneNullDateTime() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		Double quote = 0.025;

		CalibrationDataItem item1 = new CalibrationDataItem(spec, quote, LocalDateTime.of(2024, 1, 15, 10, 0, 0));
		CalibrationDataItem item2 = new CalibrationDataItem(spec, quote, null);

		boolean result = item1.equals(item2);

		assertFalse(result);
	}

	// hashCode tests

	@Test
	void testHashCode_ConsistentWithEquals() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		Double quote = 0.025;
		LocalDateTime dateTime = LocalDateTime.of(2024, 1, 15, 10, 0, 0);

		CalibrationDataItem item1 = new CalibrationDataItem(spec, quote, dateTime);
		CalibrationDataItem item2 = new CalibrationDataItem(spec, quote, dateTime);

		assertEquals(item1.hashCode(), item2.hashCode());
	}

	@Test
	void testHashCode_SameInstanceReturnsSameHash() {
		CalibrationDataItem item = createValidItem();

		int hash1 = item.hashCode();
		int hash2 = item.hashCode();

		assertEquals(hash1, hash2);
	}

	@Test
	void testHashCode_DifferentQuotesProduceDifferentHashes() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		LocalDateTime dateTime = LocalDateTime.of(2024, 1, 15, 10, 0, 0);

		CalibrationDataItem item1 = new CalibrationDataItem(spec, 0.025, dateTime);
		CalibrationDataItem item2 = new CalibrationDataItem(spec, 0.030, dateTime);

		assertNotEquals(item1.hashCode(), item2.hashCode());
	}

	@Test
	void testHashCode_WithNullValues() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		CalibrationDataItem item = new CalibrationDataItem(spec, null, null);

		int hashCode = item.hashCode();

		// Should not throw exception
		assertNotEquals(0, hashCode);
	}

	// Spec class tests

	@Test
	void testSpec_GetKey() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");

		String key = spec.getKey();

		assertEquals("key1", key);
	}

	@Test
	void testSpec_GetCurveName() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");

		String curveName = spec.getCurveName();

		assertEquals("EUR-EONIA", curveName);
	}

	@Test
	void testSpec_GetProductName() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");

		String productName = spec.getProductName();

		assertEquals("SWAP", productName);
	}

	@Test
	void testSpec_GetMaturity() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");

		String maturity = spec.getMaturity();

		assertEquals("5Y", maturity);
	}

	@Test
	void testSpec_Equals_SameInstance() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");

		boolean result = spec.equals(spec);

		assertTrue(result);
	}

	@Test
	void testSpec_Equals_EqualSpecs() {
		CalibrationDataItem.Spec spec1 = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		CalibrationDataItem.Spec spec2 = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");

		boolean result = spec1.equals(spec2);

		assertTrue(result);
	}

	@Test
	void testSpec_Equals_DifferentKey() {
		CalibrationDataItem.Spec spec1 = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		CalibrationDataItem.Spec spec2 = createSpec("key2", "EUR-EONIA", "SWAP", "5Y");

		boolean result = spec1.equals(spec2);

		assertFalse(result);
	}

	@Test
	void testSpec_Equals_DifferentCurveName() {
		CalibrationDataItem.Spec spec1 = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		CalibrationDataItem.Spec spec2 = createSpec("key1", "USD-SOFR", "SWAP", "5Y");

		boolean result = spec1.equals(spec2);

		assertFalse(result);
	}

	@Test
	void testSpec_Equals_DifferentProductName() {
		CalibrationDataItem.Spec spec1 = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		CalibrationDataItem.Spec spec2 = createSpec("key1", "EUR-EONIA", "FRA", "5Y");

		boolean result = spec1.equals(spec2);

		assertFalse(result);
	}

	@Test
	void testSpec_Equals_DifferentMaturity() {
		CalibrationDataItem.Spec spec1 = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		CalibrationDataItem.Spec spec2 = createSpec("key1", "EUR-EONIA", "SWAP", "10Y");

		boolean result = spec1.equals(spec2);

		assertFalse(result);
	}

	@Test
	void testSpec_Equals_WithNull() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");

		boolean result = spec.equals(null);

		assertFalse(result);
	}

	@Test
	void testSpec_Equals_WithDifferentClass() {
		CalibrationDataItem.Spec spec = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		String otherObject = "not a Spec";

		boolean result = spec.equals(otherObject);

		assertFalse(result);
	}

	@Test
	void testSpec_HashCode_ConsistentWithEquals() {
		CalibrationDataItem.Spec spec1 = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		CalibrationDataItem.Spec spec2 = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");

		assertEquals(spec1.hashCode(), spec2.hashCode());
	}

	@Test
	void testSpec_HashCode_DifferentSpecsProduceDifferentHashes() {
		CalibrationDataItem.Spec spec1 = createSpec("key1", "EUR-EONIA", "SWAP", "5Y");
		CalibrationDataItem.Spec spec2 = createSpec("key2", "USD-SOFR", "FRA", "10Y");

		assertNotEquals(spec1.hashCode(), spec2.hashCode());
	}
}
