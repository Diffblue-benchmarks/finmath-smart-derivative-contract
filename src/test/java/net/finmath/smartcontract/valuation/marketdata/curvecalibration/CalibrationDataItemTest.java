package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CalibrationDataItem class.
 */
class CalibrationDataItemTest {

    private CalibrationDataItem.Spec spec;
    private LocalDateTime dateTime;

    @BeforeEach
    void setUp() {
        spec = new CalibrationDataItem.Spec("KEY-001", "EUR-OIS", "SWAP", "5Y");
        dateTime = LocalDateTime.of(2024, 3, 15, 10, 30, 0);
    }

    @Test
    void testSpecConstructor() {
        CalibrationDataItem.Spec testSpec = new CalibrationDataItem.Spec("KEY", "CURVE", "PRODUCT", "2Y");

        assertEquals("KEY", testSpec.getKey());
        assertEquals("CURVE", testSpec.getCurveName());
        assertEquals("PRODUCT", testSpec.getProductName());
        assertEquals("2Y", testSpec.getMaturity());
    }

    @Test
    void testSpecEquals_SameObject() {
        assertTrue(spec.equals(spec));
    }

    @Test
    void testSpecEquals_EqualObjects() {
        CalibrationDataItem.Spec spec2 = new CalibrationDataItem.Spec("KEY-001", "EUR-OIS", "SWAP", "5Y");

        assertTrue(spec.equals(spec2));
    }

    @Test
    void testSpecEquals_DifferentObjects() {
        CalibrationDataItem.Spec spec2 = new CalibrationDataItem.Spec("KEY-002", "EUR-OIS", "SWAP", "5Y");

        assertFalse(spec.equals(spec2));
    }

    @Test
    void testSpecEquals_Null() {
        assertFalse(spec.equals(null));
    }

    @Test
    void testSpecHashCode_EqualObjects() {
        CalibrationDataItem.Spec spec2 = new CalibrationDataItem.Spec("KEY-001", "EUR-OIS", "SWAP", "5Y");

        assertEquals(spec.hashCode(), spec2.hashCode());
    }

    @Test
    void testCalibrationDataItemConstructor() {
        Double quote = 0.0235;
        CalibrationDataItem item = new CalibrationDataItem(spec, quote, dateTime);

        assertEquals(spec, item.getSpec());
        assertEquals(quote, item.getQuote());
        assertEquals(dateTime, item.getDateTime());
    }

    @Test
    void testGetCurveName() {
        CalibrationDataItem item = new CalibrationDataItem(spec, 0.02, dateTime);

        assertEquals("EUR-OIS", item.getCurveName());
    }

    @Test
    void testGetProductName() {
        CalibrationDataItem item = new CalibrationDataItem(spec, 0.02, dateTime);

        assertEquals("SWAP", item.getProductName());
    }

    @Test
    void testGetMaturity() {
        CalibrationDataItem item = new CalibrationDataItem(spec, 0.02, dateTime);

        assertEquals("5Y", item.getMaturity());
    }

    @Test
    void testGetClonedScaled() {
        CalibrationDataItem item = new CalibrationDataItem(spec, 100.0, dateTime);
        double factor = 2.0;

        CalibrationDataItem scaled = item.getClonedScaled(factor);

        assertEquals(50.0, scaled.getQuote());
        assertEquals(item.getSpec(), scaled.getSpec());
        assertEquals(item.getDateTime(), scaled.getDateTime());
    }

    @Test
    void testGetClonedShifted() {
        CalibrationDataItem item = new CalibrationDataItem(spec, 0.02, dateTime);
        double amount = 0.005;

        CalibrationDataItem shifted = item.getClonedShifted(amount);

        assertEquals(0.025, shifted.getQuote());
        assertEquals(item.getSpec(), shifted.getSpec());
        assertEquals(item.getDateTime(), shifted.getDateTime());
    }

    @Test
    void testGetDaysToMaturity_Years() {
        CalibrationDataItem.Spec yearSpec = new CalibrationDataItem.Spec("KEY", "CURVE", "PRODUCT", "5Y");
        CalibrationDataItem item = new CalibrationDataItem(yearSpec, 0.02, dateTime);

        assertEquals(1800, item.getDaysToMaturity()); // 5 * 360
    }

    @Test
    void testGetDaysToMaturity_Months() {
        CalibrationDataItem.Spec monthSpec = new CalibrationDataItem.Spec("KEY", "CURVE", "PRODUCT", "6M");
        CalibrationDataItem item = new CalibrationDataItem(monthSpec, 0.02, dateTime);

        assertEquals(180, item.getDaysToMaturity()); // 6 * 30
    }

    @Test
    void testGetDaysToMaturity_Days() {
        CalibrationDataItem.Spec daySpec = new CalibrationDataItem.Spec("KEY", "CURVE", "PRODUCT", "90D");
        CalibrationDataItem item = new CalibrationDataItem(daySpec, 0.02, dateTime);

        assertEquals(90, item.getDaysToMaturity());
    }

    @Test
    void testGetDateString() {
        CalibrationDataItem item = new CalibrationDataItem(spec, 0.02, dateTime);

        String dateString = item.getDateString();

        assertEquals("2024-03-15", dateString);
    }

    @Test
    void testGetDate() {
        CalibrationDataItem item = new CalibrationDataItem(spec, 0.02, dateTime);

        LocalDate date = item.getDate();

        assertEquals(LocalDate.of(2024, 3, 15), date);
    }

    @Test
    void testEquals_SameObject() {
        CalibrationDataItem item = new CalibrationDataItem(spec, 0.02, dateTime);

        assertTrue(item.equals(item));
    }

    @Test
    void testEquals_EqualObjects() {
        CalibrationDataItem item1 = new CalibrationDataItem(spec, 0.02, dateTime);
        CalibrationDataItem item2 = new CalibrationDataItem(spec, 0.02, dateTime);

        assertTrue(item1.equals(item2));
    }

    @Test
    void testEquals_DifferentQuote() {
        CalibrationDataItem item1 = new CalibrationDataItem(spec, 0.02, dateTime);
        CalibrationDataItem item2 = new CalibrationDataItem(spec, 0.03, dateTime);

        assertFalse(item1.equals(item2));
    }

    @Test
    void testEquals_DifferentDateTime() {
        CalibrationDataItem item1 = new CalibrationDataItem(spec, 0.02, dateTime);
        CalibrationDataItem item2 = new CalibrationDataItem(spec, 0.02, dateTime.plusDays(1));

        assertFalse(item1.equals(item2));
    }

    @Test
    void testEquals_Null() {
        CalibrationDataItem item = new CalibrationDataItem(spec, 0.02, dateTime);

        assertFalse(item.equals(null));
    }

    @Test
    void testHashCode_EqualObjects() {
        CalibrationDataItem item1 = new CalibrationDataItem(spec, 0.02, dateTime);
        CalibrationDataItem item2 = new CalibrationDataItem(spec, 0.02, dateTime);

        assertEquals(item1.hashCode(), item2.hashCode());
    }

    @Test
    void testGetDaysToMaturity_OneYear() {
        CalibrationDataItem.Spec yearSpec = new CalibrationDataItem.Spec("KEY", "CURVE", "PRODUCT", "1Y");
        CalibrationDataItem item = new CalibrationDataItem(yearSpec, 0.02, dateTime);

        assertEquals(360, item.getDaysToMaturity());
    }

    @Test
    void testGetDaysToMaturity_TenYears() {
        CalibrationDataItem.Spec yearSpec = new CalibrationDataItem.Spec("KEY", "CURVE", "PRODUCT", "10Y");
        CalibrationDataItem item = new CalibrationDataItem(yearSpec, 0.02, dateTime);

        assertEquals(3600, item.getDaysToMaturity()); // 10 * 360
    }

    @Test
    void testGetClonedScaled_WithNegativeQuote() {
        CalibrationDataItem item = new CalibrationDataItem(spec, -100.0, dateTime);

        CalibrationDataItem scaled = item.getClonedScaled(2.0);

        assertEquals(-50.0, scaled.getQuote());
    }

    @Test
    void testGetClonedShifted_WithNegativeShift() {
        CalibrationDataItem item = new CalibrationDataItem(spec, 0.05, dateTime);

        CalibrationDataItem shifted = item.getClonedShifted(-0.02);

        assertEquals(0.03, shifted.getQuote(), 0.0001); // Use delta for floating point comparison
    }
}
