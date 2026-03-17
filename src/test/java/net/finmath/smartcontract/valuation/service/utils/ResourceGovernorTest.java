package net.finmath.smartcontract.valuation.service.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ResourceGovernorTest {

	@Test
	void testMarketDataFolderToString() {
		// Given
		final ResourceGovernor.RoleFolders marketDataFolder = ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER;

		// When
		final String result = marketDataFolder.toString();

		// Then
		assertNotNull(result);
		assertEquals("/%s.marketdata/", result);
	}

	@Test
	void testSavedContractsFolderToString() {
		// Given
		final ResourceGovernor.RoleFolders savedContractsFolder = ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER;

		// When
		final String result = savedContractsFolder.toString();

		// Then
		assertNotNull(result);
		assertEquals("/%s.savedcontracts/", result);
	}

	@Test
	void testEnumValues() {
		// Given / When
		final ResourceGovernor.RoleFolders[] values = ResourceGovernor.RoleFolders.values();

		// Then
		assertNotNull(values);
		assertEquals(2, values.length);
		assertEquals(ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER, values[0]);
		assertEquals(ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER, values[1]);
	}
}
