package net.finmath.smartcontract.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for TradeUtils class.
 */
class TradeUtilsTest {

    @Test
    void testGetUniqueTradeId_NotNull() {
        String tradeId = TradeUtils.getUniqueTradeId();
        assertNotNull(tradeId, "Trade ID should not be null");
    }

    @Test
    void testGetUniqueTradeId_StartsWithPrefix() {
        String tradeId = TradeUtils.getUniqueTradeId();
        assertTrue(tradeId.startsWith("ID-"), "Trade ID should start with 'ID-' prefix");
    }

    @Test
    void testGetUniqueTradeId_CorrectLength() {
        String tradeId = TradeUtils.getUniqueTradeId();
        // "ID-" (3 chars) + 17 chars from UUID substring = 20 total
        assertEquals(20, tradeId.length(), "Trade ID should be exactly 20 characters long");
    }

    @Test
    void testGetUniqueTradeId_NoHyphens() {
        String tradeId = TradeUtils.getUniqueTradeId();
        String idPart = tradeId.substring(3); // Remove "ID-" prefix
        assertFalse(idPart.contains("-"), "Trade ID should not contain hyphens in the ID part");
    }

    @RepeatedTest(100)
    void testGetUniqueTradeId_GeneratesUniqueIds() {
        Set<String> generatedIds = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            String tradeId = TradeUtils.getUniqueTradeId();
            assertTrue(generatedIds.add(tradeId), "Trade IDs should be unique");
        }
    }

    @Test
    void testGetUniqueTradeId_ContainsOnlyValidCharacters() {
        String tradeId = TradeUtils.getUniqueTradeId();
        String idPart = tradeId.substring(3); // Remove "ID-" prefix
        assertTrue(idPart.matches("[0-9a-f]+"), "Trade ID should contain only hexadecimal characters");
    }
}
