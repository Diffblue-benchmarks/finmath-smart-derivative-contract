/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.service.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ResourceGovernor.RoleFolders constructor coverage.
 * Tests public methods that trigger the private enum constructor to achieve coverage.
 *
 * @author Claude Code
 */
class ResourceGovernorClaude_constructorTest {

	/**
	 * Test that accessing MARKET_DATA_FOLDER constant triggers constructor.
	 * This covers the enum constructor initialization for MARKET_DATA_FOLDER.
	 */
	@Test
	void testRoleFoldersConstructor_MarketDataFolder() {
		// Act - Access the enum constant which triggers constructor
		ResourceGovernor.RoleFolders folder = ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER;

		// Assert - Verify the constructor properly initialized the field
		assertNotNull(folder, "Enum constant should not be null");
		assertEquals("/%s.marketdata/", folder.toString(),
			"Constructor should have initialized folderTemplate correctly");
	}

	/**
	 * Test that accessing SAVED_CONTRACTS_FOLDER constant triggers constructor.
	 * This covers the enum constructor initialization for SAVED_CONTRACTS_FOLDER.
	 */
	@Test
	void testRoleFoldersConstructor_SavedContractsFolder() {
		// Act - Access the enum constant which triggers constructor
		ResourceGovernor.RoleFolders folder = ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER;

		// Assert - Verify the constructor properly initialized the field
		assertNotNull(folder, "Enum constant should not be null");
		assertEquals("/%s.savedcontracts/", folder.toString(),
			"Constructor should have initialized folderTemplate correctly");
	}

	/**
	 * Test that values() method returns all enum constants created by constructor.
	 * This ensures both constructor invocations are covered.
	 */
	@Test
	void testRoleFoldersConstructor_AllValuesInitialized() {
		// Act - Call values() which accesses all enum constants
		ResourceGovernor.RoleFolders[] values = ResourceGovernor.RoleFolders.values();

		// Assert - Verify all constants were properly constructed
		assertNotNull(values, "Values array should not be null");
		assertEquals(2, values.length, "Should have 2 enum constants");

		// Verify each constant is properly initialized via constructor
		for (ResourceGovernor.RoleFolders folder : values) {
			assertNotNull(folder, "Each enum constant should be non-null");
			assertNotNull(folder.toString(), "Each constant should have non-null folderTemplate");
			assertFalse(folder.toString().isEmpty(), "Each constant should have non-empty folderTemplate");
			assertTrue(folder.toString().contains("%s"), "Each folderTemplate should contain format placeholder");
		}
	}

	/**
	 * Test that valueOf() accesses enum constants created by constructor.
	 * This verifies constructor was called during enum initialization.
	 */
	@Test
	void testRoleFoldersConstructor_ValueOfAccessesConstructedInstance() {
		// Act - valueOf accesses the pre-constructed enum constant
		ResourceGovernor.RoleFolders folder = ResourceGovernor.RoleFolders.valueOf("MARKET_DATA_FOLDER");

		// Assert - Verify the constructor properly initialized this instance
		assertNotNull(folder, "valueOf should return constructed instance");
		assertEquals("/%s.marketdata/", folder.toString(),
			"Constructor should have set folderTemplate field correctly");
		assertSame(ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER, folder,
			"valueOf should return same instance created by constructor");
	}

	/**
	 * Test that the folderTemplate field is immutable after construction.
	 * Verifies constructor properly assigned the final field.
	 */
	@Test
	void testRoleFoldersConstructor_FolderTemplateIsImmutable() {
		// Act - Access enum constants
		ResourceGovernor.RoleFolders folder1 = ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER;
		ResourceGovernor.RoleFolders folder2 = ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER;

		// Get the toString values multiple times
		String template1a = folder1.toString();
		String template1b = folder1.toString();
		String template2a = folder2.toString();
		String template2b = folder2.toString();

		// Assert - Verify values are consistent (constructor set final field correctly)
		assertEquals(template1a, template1b, "MARKET_DATA_FOLDER template should be consistent");
		assertEquals(template2a, template2b, "SAVED_CONTRACTS_FOLDER template should be consistent");
		assertNotEquals(template1a, template2a, "Different constants should have different templates");
	}

	/**
	 * Test that constructor parameter is properly stored in the field.
	 * Verifies the assignment in the constructor works correctly.
	 */
	@Test
	void testRoleFoldersConstructor_ParameterStoredCorrectly() {
		// Act - Access both enum constants
		ResourceGovernor.RoleFolders marketData = ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER;
		ResourceGovernor.RoleFolders savedContracts = ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER;

		// Assert - Verify constructor parameter was stored correctly
		String marketDataTemplate = marketData.toString();
		String savedContractsTemplate = savedContracts.toString();

		assertTrue(marketDataTemplate.contains("marketdata"),
			"MARKET_DATA_FOLDER should contain 'marketdata' as passed to constructor");
		assertTrue(savedContractsTemplate.contains("savedcontracts"),
			"SAVED_CONTRACTS_FOLDER should contain 'savedcontracts' as passed to constructor");
	}

	/**
	 * Test that both enum constants are distinct instances created by separate constructor calls.
	 */
	@Test
	void testRoleFoldersConstructor_CreatesDistinctInstances() {
		// Act - Access both enum constants
		ResourceGovernor.RoleFolders folder1 = ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER;
		ResourceGovernor.RoleFolders folder2 = ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER;

		// Assert - Verify they are distinct instances
		assertNotSame(folder1, folder2, "Constructor should create distinct instances");
		assertNotEquals(folder1, folder2, "Enum constants should not be equal");
		assertNotEquals(folder1.toString(), folder2.toString(),
			"Constructor should have set different folderTemplate values");
	}

	/**
	 * Test that the toString method returns the value set by constructor.
	 * This directly tests the constructor's initialization of folderTemplate.
	 */
	@Test
	void testRoleFoldersConstructor_ToStringReturnsConstructorParameter() {
		// Act - Access enum and call toString
		String marketDataString = ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER.toString();
		String savedContractsString = ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER.toString();

		// Assert - Verify toString returns the exact value passed to constructor
		assertEquals("/%s.marketdata/", marketDataString,
			"toString should return exact constructor parameter");
		assertEquals("/%s.savedcontracts/", savedContractsString,
			"toString should return exact constructor parameter");

		// Verify format placeholders are present (as passed to constructor)
		assertTrue(marketDataString.startsWith("/"), "Template should start with /");
		assertTrue(marketDataString.endsWith("/"), "Template should end with /");
		assertTrue(savedContractsString.startsWith("/"), "Template should start with /");
		assertTrue(savedContractsString.endsWith("/"), "Template should end with /");
	}
}
