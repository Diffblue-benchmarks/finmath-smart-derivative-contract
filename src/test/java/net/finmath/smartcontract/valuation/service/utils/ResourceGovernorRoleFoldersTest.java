package net.finmath.smartcontract.valuation.service.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceGovernorRoleFoldersTest {

	@Test
	void marketDataFolder_shouldContainPlaceholder() {
		String template = ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER.toString();
		assertTrue(template.contains("%s"));
		assertEquals("/testuser.marketdata/", template.formatted("testuser"));
	}

	@Test
	void savedContractsFolder_shouldContainPlaceholder() {
		String template = ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER.toString();
		assertTrue(template.contains("%s"));
		assertEquals("/admin.savedcontracts/", template.formatted("admin"));
	}

	@Test
	void values_shouldContainTwoEntries() {
		assertEquals(2, ResourceGovernor.RoleFolders.values().length);
	}
}
