package net.finmath.smartcontract.model;

import net.finmath.smartcontract.valuation.marketdata.data.MarketDataPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for MarketDataList class.
 */
class MarketDataListTest {

    private MarketDataList marketDataList;

    @BeforeEach
    void setUp() {
        marketDataList = new MarketDataList();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(marketDataList);
        assertNotNull(marketDataList.getPoints());
        assertNotNull(marketDataList.getRequestTimeStamp());
        assertEquals(0, marketDataList.getSize());
    }

    @Test
    void testAddMarketDataPoint() {
        MarketDataPoint point = new MarketDataPoint();
        marketDataList.add(point);

        assertEquals(1, marketDataList.getSize());
        assertTrue(marketDataList.getPoints().contains(point));
    }

    @Test
    void testAddMultipleMarketDataPoints() {
        MarketDataPoint point1 = new MarketDataPoint();
        MarketDataPoint point2 = new MarketDataPoint();
        MarketDataPoint point3 = new MarketDataPoint();

        marketDataList.add(point1);
        marketDataList.add(point2);
        marketDataList.add(point3);

        assertEquals(3, marketDataList.getSize());
    }

    @Test
    void testSetPoints() {
        List<MarketDataPoint> points = new ArrayList<>();
        points.add(new MarketDataPoint());
        points.add(new MarketDataPoint());

        marketDataList.setPoints(points);

        assertEquals(2, marketDataList.getSize());
        assertEquals(points, marketDataList.getPoints());
    }

    @Test
    void testSetAndGetRequestTimeStamp() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 1, 12, 0, 0);
        marketDataList.setRequestTimeStamp(timestamp);

        assertEquals(timestamp, marketDataList.getRequestTimeStamp());
    }

    @Test
    void testGetSize() {
        assertEquals(0, marketDataList.getSize());

        marketDataList.add(new MarketDataPoint());
        assertEquals(1, marketDataList.getSize());

        marketDataList.add(new MarketDataPoint());
        assertEquals(2, marketDataList.getSize());
    }

    @Test
    void testSerializeToJson() {
        MarketDataPoint point = new MarketDataPoint();
        marketDataList.add(point);

        String json = marketDataList.serializeToJson();

        assertNotNull(json);
        assertFalse(json.isEmpty());
    }

    @Test
    void testSerializeToJson_EmptyList() {
        String json = marketDataList.serializeToJson();

        assertNotNull(json);
        assertFalse(json.isEmpty());
    }

    @Test
    void testMapToCalibrationDataSet() {
        // This method returns null in current implementation
        assertNull(marketDataList.mapToCalibrationDataSet());
    }

    @Test
    void testToString() {
        String result = marketDataList.toString();

        assertNotNull(result);
        assertTrue(result.contains("MarketDataList"));
        assertTrue(result.contains("points"));
        assertTrue(result.contains("requestTimeStamp"));
    }

    @Test
    void testEquals_SameObject() {
        assertTrue(marketDataList.equals(marketDataList));
    }

    @Test
    void testEquals_NullObject() {
        assertFalse(marketDataList.equals(null));
    }

    @Test
    void testEquals_DifferentClass() {
        assertFalse(marketDataList.equals("not a MarketDataList"));
    }

    @Test
    void testEquals_EqualObjects() {
        MarketDataList list1 = new MarketDataList();
        MarketDataList list2 = new MarketDataList();

        // Set same timestamp
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 1, 12, 0, 0);
        list1.setRequestTimeStamp(timestamp);
        list2.setRequestTimeStamp(timestamp);

        assertTrue(list1.equals(list2));
    }

    @Test
    void testEquals_DifferentTimestamps() {
        MarketDataList list1 = new MarketDataList();
        MarketDataList list2 = new MarketDataList();

        list1.setRequestTimeStamp(LocalDateTime.of(2024, 1, 1, 12, 0, 0));
        list2.setRequestTimeStamp(LocalDateTime.of(2024, 1, 2, 12, 0, 0));

        assertFalse(list1.equals(list2));
    }

    @Test
    void testGetPoints_NotNull() {
        assertNotNull(marketDataList.getPoints());
    }

    @Test
    void testInitialRequestTimeStamp() {
        LocalDateTime before = LocalDateTime.now().minusSeconds(1);
        MarketDataList newList = new MarketDataList();
        LocalDateTime after = LocalDateTime.now().plusSeconds(1);

        LocalDateTime timestamp = newList.getRequestTimeStamp();
        assertTrue(timestamp.isAfter(before) || timestamp.isEqual(before));
        assertTrue(timestamp.isBefore(after) || timestamp.isEqual(after));
    }
}
