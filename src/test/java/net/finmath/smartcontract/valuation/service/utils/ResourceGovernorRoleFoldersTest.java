package net.finmath.smartcontract.valuation.service.utils;

import net.finmath.smartcontract.valuation.service.utils.ResourceGovernor.RoleFolders;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResourceGovernorRoleFoldersTest {

	@Test
	void testMarketDataFolderToString() {
		RoleFolders folder = RoleFolders.MARKET_DATA_FOLDER;
		assertEquals("/%s.marketdata/", folder.toString());
	}

	@Test
	void testSavedContractsFolderToString() {
		RoleFolders folder = RoleFolders.SAVED_CONTRACTS_FOLDER;
		assertEquals("/%s.savedcontracts/", folder.toString());
	}
}
