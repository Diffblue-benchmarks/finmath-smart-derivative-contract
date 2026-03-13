package net.finmath.smartcontract.valuation.service.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ResourceGovernor.RoleFolders enum.
 */
class ResourceGovernorTest {

    @Test
    void testRoleFoldersEnum() {
        assertNotNull(ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER);
        assertNotNull(ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER);
    }

    @Test
    void testRoleFoldersMarketDataFolderToString() {
        String result = ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER.toString();

        assertEquals("/%s.marketdata/", result);
    }

    @Test
    void testRoleFoldersSavedContractsFolderToString() {
        String result = ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER.toString();

        assertEquals("/%s.savedcontracts/", result);
    }

    @Test
    void testRoleFoldersValues() {
        ResourceGovernor.RoleFolders[] values = ResourceGovernor.RoleFolders.values();

        assertEquals(2, values.length);
        assertTrue(values[0] == ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER ||
                   values[0] == ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER);
        assertTrue(values[1] == ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER ||
                   values[1] == ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER);
    }

    @Test
    void testRoleFoldersValueOf() {
        assertEquals(ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER,
                     ResourceGovernor.RoleFolders.valueOf("MARKET_DATA_FOLDER"));
        assertEquals(ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER,
                     ResourceGovernor.RoleFolders.valueOf("SAVED_CONTRACTS_FOLDER"));
    }

    @Test
    void testRoleFoldersInvalidValueOf() {
        assertThrows(IllegalArgumentException.class, () -> {
            ResourceGovernor.RoleFolders.valueOf("INVALID_FOLDER");
        });
    }

    @Test
    void testRoleFoldersTemplateFormat() {
        String marketDataResult = String.format(ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER.toString(), "testuser");
        String savedContractsResult = String.format(ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER.toString(), "testuser");

        assertEquals("/testuser.marketdata/", marketDataResult);
        assertEquals("/testuser.savedcontracts/", savedContractsResult);
    }

    @Test
    void testRoleFoldersTemplateWithDifferentUsers() {
        String user1MarketData = String.format(ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER.toString(), "alice");
        String user2MarketData = String.format(ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER.toString(), "bob");

        assertEquals("/alice.marketdata/", user1MarketData);
        assertEquals("/bob.marketdata/", user2MarketData);
        assertNotEquals(user1MarketData, user2MarketData);
    }
}
