package net.finmath.smartcontract.valuation.marketdata.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for LocalDateTimeAdapter class.
 */
class LocalDateTimeAdapterTest {

    private LocalDateTimeAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new LocalDateTimeAdapter();
    }

    @Test
    void testMarshal_ValidLocalDateTime() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 3, 15, 14, 30, 45);

        String result = adapter.marshal(dateTime);

        assertNotNull(result);
        assertEquals("20240315-143045", result);
    }

    @Test
    void testMarshal_MidnightTime() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0);

        String result = adapter.marshal(dateTime);

        assertEquals("20240101-000000", result);
    }

    @Test
    void testMarshal_EndOfDay() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 12, 31, 23, 59, 59);

        String result = adapter.marshal(dateTime);

        assertEquals("20231231-235959", result);
    }

    @Test
    void testUnmarshal_ValidString() throws Exception {
        String input = "20240315-143045";

        LocalDateTime result = adapter.unmarshal(input);

        assertNotNull(result);
        assertEquals(2024, result.getYear());
        assertEquals(3, result.getMonthValue());
        assertEquals(15, result.getDayOfMonth());
        assertEquals(14, result.getHour());
        assertEquals(30, result.getMinute());
        assertEquals(45, result.getSecond());
    }

    @Test
    void testUnmarshal_MidnightString() throws Exception {
        String input = "20240101-000000";

        LocalDateTime result = adapter.unmarshal(input);

        assertEquals(2024, result.getYear());
        assertEquals(1, result.getMonthValue());
        assertEquals(1, result.getDayOfMonth());
        assertEquals(0, result.getHour());
        assertEquals(0, result.getMinute());
        assertEquals(0, result.getSecond());
    }

    @Test
    void testUnmarshal_EndOfDayString() throws Exception {
        String input = "20231231-235959";

        LocalDateTime result = adapter.unmarshal(input);

        assertEquals(2023, result.getYear());
        assertEquals(12, result.getMonthValue());
        assertEquals(31, result.getDayOfMonth());
        assertEquals(23, result.getHour());
        assertEquals(59, result.getMinute());
        assertEquals(59, result.getSecond());
    }

    @Test
    void testRoundTrip() throws Exception {
        LocalDateTime original = LocalDateTime.of(2024, 6, 15, 10, 25, 30);

        String marshalled = adapter.marshal(original);
        LocalDateTime unmarshalled = adapter.unmarshal(marshalled);

        assertEquals(original, unmarshalled);
    }

    @Test
    void testMarshal_LeapYearDate() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 2, 29, 12, 0, 0);

        String result = adapter.marshal(dateTime);

        assertEquals("20240229-120000", result);
    }

    @Test
    void testUnmarshal_LeapYearDate() throws Exception {
        String input = "20240229-120000";

        LocalDateTime result = adapter.unmarshal(input);

        assertEquals(2024, result.getYear());
        assertEquals(2, result.getMonthValue());
        assertEquals(29, result.getDayOfMonth());
    }

    @Test
    void testUnmarshal_EarlyMorningTime() throws Exception {
        String input = "20240101-010101";

        LocalDateTime result = adapter.unmarshal(input);

        assertEquals(1, result.getHour());
        assertEquals(1, result.getMinute());
        assertEquals(1, result.getSecond());
    }
}
