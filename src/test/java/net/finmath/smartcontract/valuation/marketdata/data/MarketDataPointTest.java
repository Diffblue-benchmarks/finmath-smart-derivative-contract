package net.finmath.smartcontract.valuation.marketdata.data;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for MarketDataPoint class.
 */
class MarketDataPointTest {

    @Test
    void testDefaultConstructor() {
        MarketDataPoint point = new MarketDataPoint();

        assertNotNull(point);
        assertNull(point.getId());
        assertNull(point.getValue());
        assertNull(point.getTimeStamp());
    }

    @Test
    void testParameterizedConstructor() {
        String id = "EUR-SWAP-5Y";
        Double value = 0.0235;
        LocalDateTime timestamp = LocalDateTime.of(2024, 3, 15, 10, 30, 0);

        MarketDataPoint point = new MarketDataPoint(id, value, timestamp);

        assertEquals(id, point.getId());
        assertEquals(value, point.getValue());
        assertEquals(timestamp, point.getTimeStamp());
    }

    @Test
    void testSetAndGetId() {
        MarketDataPoint point = new MarketDataPoint();
        String id = "USD-LIBOR-3M";

        point.setId(id);

        assertEquals(id, point.getId());
    }

    @Test
    void testSetAndGetValue() {
        MarketDataPoint point = new MarketDataPoint();
        Double value = 1.234;

        point.setValue(value);

        assertEquals(value, point.getValue());
    }

    @Test
    void testSetAndGetTimeStamp() {
        MarketDataPoint point = new MarketDataPoint();
        LocalDateTime timestamp = LocalDateTime.now();

        point.setTimeStamp(timestamp);

        assertEquals(timestamp, point.getTimeStamp());
    }

    @Test
    void testToString() {
        String id = "TEST-ID";
        Double value = 0.05;
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 1, 12, 0, 0);

        MarketDataPoint point = new MarketDataPoint(id, value, timestamp);
        String result = point.toString();

        assertNotNull(result);
        assertTrue(result.contains("MarketDataPoint"));
        assertTrue(result.contains(id));
        assertTrue(result.contains(value.toString()));
    }

    @Test
    void testEquals_SameObject() {
        MarketDataPoint point = new MarketDataPoint("ID-1", 0.01, LocalDateTime.now());

        assertTrue(point.equals(point));
    }

    @Test
    void testEquals_NullObject() {
        MarketDataPoint point = new MarketDataPoint("ID-1", 0.01, LocalDateTime.now());

        assertFalse(point.equals(null));
    }

    @Test
    void testEquals_DifferentClass() {
        MarketDataPoint point = new MarketDataPoint("ID-1", 0.01, LocalDateTime.now());

        assertFalse(point.equals("not a MarketDataPoint"));
    }

    @Test
    void testEquals_EqualPoints() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 3, 15, 10, 30, 0);
        MarketDataPoint point1 = new MarketDataPoint("ID-1", 0.01, timestamp);
        MarketDataPoint point2 = new MarketDataPoint("ID-1", 0.01, timestamp);

        assertTrue(point1.equals(point2));
    }

    @Test
    void testEquals_DifferentId() {
        LocalDateTime timestamp = LocalDateTime.now();
        MarketDataPoint point1 = new MarketDataPoint("ID-1", 0.01, timestamp);
        MarketDataPoint point2 = new MarketDataPoint("ID-2", 0.01, timestamp);

        assertFalse(point1.equals(point2));
    }

    @Test
    void testEquals_DifferentValue() {
        LocalDateTime timestamp = LocalDateTime.now();
        MarketDataPoint point1 = new MarketDataPoint("ID-1", 0.01, timestamp);
        MarketDataPoint point2 = new MarketDataPoint("ID-1", 0.02, timestamp);

        assertFalse(point1.equals(point2));
    }

    @Test
    void testEquals_DifferentTimestamp() {
        MarketDataPoint point1 = new MarketDataPoint("ID-1", 0.01, LocalDateTime.of(2024, 1, 1, 10, 0, 0));
        MarketDataPoint point2 = new MarketDataPoint("ID-1", 0.01, LocalDateTime.of(2024, 1, 2, 10, 0, 0));

        assertFalse(point1.equals(point2));
    }

    @Test
    void testEquals_BothNullValues() {
        MarketDataPoint point1 = new MarketDataPoint();
        MarketDataPoint point2 = new MarketDataPoint();

        assertTrue(point1.equals(point2));
    }

    @Test
    void testWithNullId() {
        MarketDataPoint point = new MarketDataPoint(null, 0.01, LocalDateTime.now());

        assertNull(point.getId());
        assertNotNull(point.getValue());
        assertNotNull(point.getTimeStamp());
    }

    @Test
    void testWithNullValue() {
        MarketDataPoint point = new MarketDataPoint("ID-1", null, LocalDateTime.now());

        assertNotNull(point.getId());
        assertNull(point.getValue());
        assertNotNull(point.getTimeStamp());
    }

    @Test
    void testWithNullTimestamp() {
        MarketDataPoint point = new MarketDataPoint("ID-1", 0.01, null);

        assertNotNull(point.getId());
        assertNotNull(point.getValue());
        assertNull(point.getTimeStamp());
    }

    @Test
    void testWithZeroValue() {
        MarketDataPoint point = new MarketDataPoint("ID-1", 0.0, LocalDateTime.now());

        assertEquals(0.0, point.getValue());
    }

    @Test
    void testWithNegativeValue() {
        MarketDataPoint point = new MarketDataPoint("ID-1", -0.01, LocalDateTime.now());

        assertEquals(-0.01, point.getValue());
    }

    @Test
    void testWithLargeValue() {
        Double largeValue = 999999.999999;
        MarketDataPoint point = new MarketDataPoint("ID-1", largeValue, LocalDateTime.now());

        assertEquals(largeValue, point.getValue());
    }
}
