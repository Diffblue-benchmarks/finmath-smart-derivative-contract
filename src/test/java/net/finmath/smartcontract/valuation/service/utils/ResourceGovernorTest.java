package net.finmath.smartcontract.valuation.service.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ResourceGovernorTest {

	@Test
	void testRoleFoldersMarketDataFolderToString() {
		Assertions.assertEquals("/%s.marketdata/", ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER.toString());
	}

	@Test
	void testRoleFoldersSavedContractsFolderToString() {
		Assertions.assertEquals("/%s.savedcontracts/", ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER.toString());
	}

}
