package net.finmath.smartcontract.settlement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ZonedDateTimeAdapter class.
 */
class ZonedDateTimeAdapterTest {

    private ZonedDateTimeAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new ZonedDateTimeAdapter();
    }

    @Test
    void testMarshal_ValidZonedDateTime() {
        ZonedDateTime dateTime = ZonedDateTime.of(2024, 3, 15, 14, 30, 45, 0, ZoneId.systemDefault());

        String result = adapter.marshal(dateTime);

        assertNotNull(result);
        assertEquals("20240315-143045", result);
    }

    @Test
    void testMarshal_MidnightTime() {
        ZonedDateTime dateTime = ZonedDateTime.of(2024, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault());

        String result = adapter.marshal(dateTime);

        assertEquals("20240101-000000", result);
    }

    @Test
    void testMarshal_EndOfDay() {
        ZonedDateTime dateTime = ZonedDateTime.of(2023, 12, 31, 23, 59, 59, 0, ZoneId.systemDefault());

        String result = adapter.marshal(dateTime);

        assertEquals("20231231-235959", result);
    }

    @Test
    void testUnmarshal_ValidString() throws Exception {
        String input = "20240315-143045";

        ZonedDateTime result = adapter.unmarshal(input);

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

        ZonedDateTime result = adapter.unmarshal(input);

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

        ZonedDateTime result = adapter.unmarshal(input);

        assertEquals(2023, result.getYear());
        assertEquals(12, result.getMonthValue());
        assertEquals(31, result.getDayOfMonth());
        assertEquals(23, result.getHour());
        assertEquals(59, result.getMinute());
        assertEquals(59, result.getSecond());
    }

    @Test
    void testRoundTrip() throws Exception {
        ZonedDateTime original = ZonedDateTime.of(2024, 6, 15, 10, 25, 30, 0, ZoneId.systemDefault());

        String marshalled = adapter.marshal(original);
        ZonedDateTime unmarshalled = adapter.unmarshal(marshalled);

        assertEquals(original.getYear(), unmarshalled.getYear());
        assertEquals(original.getMonthValue(), unmarshalled.getMonthValue());
        assertEquals(original.getDayOfMonth(), unmarshalled.getDayOfMonth());
        assertEquals(original.getHour(), unmarshalled.getHour());
        assertEquals(original.getMinute(), unmarshalled.getMinute());
        assertEquals(original.getSecond(), unmarshalled.getSecond());
    }

    @Test
    void testUnmarshal_UsesSystemDefaultZone() throws Exception {
        String input = "20240315-143045";

        ZonedDateTime result = adapter.unmarshal(input);

        assertEquals(ZoneId.systemDefault(), result.getZone());
    }

    @Test
    void testMarshal_PreservesDateTime() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 7, 4, 15, 45, 30);
        ZonedDateTime dateTime = ZonedDateTime.of(localDateTime, ZoneId.of("America/New_York"));

        String result = adapter.marshal(dateTime);

        assertEquals("20240704-154530", result);
    }
}
