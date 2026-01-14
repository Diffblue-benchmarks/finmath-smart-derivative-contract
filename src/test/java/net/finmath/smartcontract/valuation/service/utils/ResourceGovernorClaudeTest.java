/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.service.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Test class for ResourceGovernor.
 * Tests all methods with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class ResourceGovernorClaudeTest {

	private ResourcePatternResolver mockResolver;
	private ResourceGovernor resourceGovernor;

	@BeforeEach
	void setUp() {
		// Create a mock ResourcePatternResolver
		mockResolver = mock(ResourcePatternResolver.class);

		// Create ResourceGovernor instance
		resourceGovernor = new ResourceGovernor(mockResolver);

		// Set the @Value fields using reflection since they're normally injected by Spring
		// Note: storageBaseDir should end with / to match the actual behavior
		ReflectionTestUtils.setField(resourceGovernor, "storageBaseDir", "/test/storage/");
		ReflectionTestUtils.setField(resourceGovernor, "importDir", "/test/import");
		ReflectionTestUtils.setField(resourceGovernor, "refinitivConnectionPropertiesFile", "/test/refinitiv.properties");
		ReflectionTestUtils.setField(resourceGovernor, "databaseConnectionPropertiesFile", "/test/database.properties");
	}

	/**
	 * Test constructor creates a valid instance.
	 */
	@Test
	void testConstructor_CreatesValidInstance() {
		// Arrange
		ResourcePatternResolver resolver = mock(ResourcePatternResolver.class);

		// Act
		ResourceGovernor governor = new ResourceGovernor(resolver);

		// Assert
		assertNotNull(governor, "Constructor should create a non-null instance");
	}

	/**
	 * Test constructor with null resolver.
	 * Verifies that the constructor accepts null (though it would fail at runtime when methods are called).
	 */
	@Test
	void testConstructor_WithNullResolver() {
		// Arrange & Act & Assert
		assertDoesNotThrow(() -> {
			new ResourceGovernor(null);
		}, "Constructor should not throw exception with null resolver");
	}

	/**
	 * Test constructor creates independent instances.
	 */
	@Test
	void testConstructor_MultipleInstances_AreIndependent() {
		// Arrange
		ResourcePatternResolver resolver1 = mock(ResourcePatternResolver.class);
		ResourcePatternResolver resolver2 = mock(ResourcePatternResolver.class);

		// Act
		ResourceGovernor governor1 = new ResourceGovernor(resolver1);
		ResourceGovernor governor2 = new ResourceGovernor(resolver2);

		// Assert
		assertNotNull(governor1, "First governor should not be null");
		assertNotNull(governor2, "Second governor should not be null");
		assertNotSame(governor1, governor2, "Governors should be different instances");
	}

	/**
	 * Test getActiveDatasetAsResourceInReadMode with valid username.
	 */
	@Test
	void testGetActiveDatasetAsResourceInReadMode_ValidUsername() {
		// Arrange
		String username = "testuser";
		Resource mockResource = mock(Resource.class);
		when(mockResolver.getResource(anyString())).thenReturn(mockResource);

		// Act
		Resource result = resourceGovernor.getActiveDatasetAsResourceInReadMode(username);

		// Assert
		assertNotNull(result, "Result should not be null");
		assertSame(mockResource, result, "Should return the mock resource");
		verify(mockResolver).getResource("file:////test/storage//testuser.marketdata/active_dataset.json");
	}

	/**
	 * Test getActiveDatasetAsResourceInReadMode with different usernames.
	 */
	@Test
	void testGetActiveDatasetAsResourceInReadMode_DifferentUsernames() {
		// Arrange
		Resource mockResource1 = mock(Resource.class);
		Resource mockResource2 = mock(Resource.class);
		when(mockResolver.getResource(contains("user1"))).thenReturn(mockResource1);
		when(mockResolver.getResource(contains("user2"))).thenReturn(mockResource2);

		// Act
		Resource result1 = resourceGovernor.getActiveDatasetAsResourceInReadMode("user1");
		Resource result2 = resourceGovernor.getActiveDatasetAsResourceInReadMode("user2");

		// Assert
		assertSame(mockResource1, result1, "Should return resource for user1");
		assertSame(mockResource2, result2, "Should return resource for user2");
		verify(mockResolver).getResource("file:////test/storage//user1.marketdata/active_dataset.json");
		verify(mockResolver).getResource("file:////test/storage//user2.marketdata/active_dataset.json");
	}

	/**
	 * Test getActiveDatasetAsResourceInReadMode with special characters in username.
	 */
	@Test
	void testGetActiveDatasetAsResourceInReadMode_SpecialCharactersInUsername() {
		// Arrange
		String username = "test.user-123";
		Resource mockResource = mock(Resource.class);
		when(mockResolver.getResource(anyString())).thenReturn(mockResource);

		// Act
		Resource result = resourceGovernor.getActiveDatasetAsResourceInReadMode(username);

		// Assert
		assertNotNull(result, "Result should not be null");
		verify(mockResolver).getResource("file:////test/storage//test.user-123.marketdata/active_dataset.json");
	}

	/**
	 * Test getActiveDatasetAsResourceInWriteMode with valid username.
	 */
	@Test
	void testGetActiveDatasetAsResourceInWriteMode_ValidUsername() {
		// Arrange
		String username = "testuser";
		WritableResource mockResource = mock(WritableResource.class);
		when(mockResolver.getResource(anyString())).thenReturn(mockResource);

		// Act
		WritableResource result = resourceGovernor.getActiveDatasetAsResourceInWriteMode(username);

		// Assert
		assertNotNull(result, "Result should not be null");
		assertSame(mockResource, result, "Should return the mock writable resource");
		verify(mockResolver).getResource("file:////test/storage//testuser.marketdata/active_dataset.json");
	}

	/**
	 * Test getActiveDatasetAsResourceInWriteMode with different usernames.
	 */
	@Test
	void testGetActiveDatasetAsResourceInWriteMode_DifferentUsernames() {
		// Arrange
		WritableResource mockResource1 = mock(WritableResource.class);
		WritableResource mockResource2 = mock(WritableResource.class);
		when(mockResolver.getResource(contains("admin"))).thenReturn(mockResource1);
		when(mockResolver.getResource(contains("guest"))).thenReturn(mockResource2);

		// Act
		WritableResource result1 = resourceGovernor.getActiveDatasetAsResourceInWriteMode("admin");
		WritableResource result2 = resourceGovernor.getActiveDatasetAsResourceInWriteMode("guest");

		// Assert
		assertSame(mockResource1, result1, "Should return writable resource for admin");
		assertSame(mockResource2, result2, "Should return writable resource for guest");
		verify(mockResolver).getResource("file:////test/storage//admin.marketdata/active_dataset.json");
		verify(mockResolver).getResource("file:////test/storage//guest.marketdata/active_dataset.json");
	}

	/**
	 * Test getImportCandidateAsResourceInReadMode.
	 */
	@Test
	void testGetImportCandidateAsResourceInReadMode() {
		// Arrange
		Resource mockResource = mock(Resource.class);
		when(mockResolver.getResource(anyString())).thenReturn(mockResource);

		// Act
		Resource result = resourceGovernor.getImportCandidateAsResourceInReadMode();

		// Assert
		assertNotNull(result, "Result should not be null");
		assertSame(mockResource, result, "Should return the mock resource");
		verify(mockResolver).getResource("file:////test/import/import_candidate.json");
	}

	/**
	 * Test getImportCandidateAsResourceInReadMode multiple times.
	 */
	@Test
	void testGetImportCandidateAsResourceInReadMode_MultipleCalls() {
		// Arrange
		Resource mockResource1 = mock(Resource.class);
		Resource mockResource2 = mock(Resource.class);
		when(mockResolver.getResource(anyString()))
			.thenReturn(mockResource1)
			.thenReturn(mockResource2);

		// Act
		Resource result1 = resourceGovernor.getImportCandidateAsResourceInReadMode();
		Resource result2 = resourceGovernor.getImportCandidateAsResourceInReadMode();

		// Assert
		assertSame(mockResource1, result1, "First call should return first mock resource");
		assertSame(mockResource2, result2, "Second call should return second mock resource");
		verify(mockResolver, times(2)).getResource("file:////test/import/import_candidate.json");
	}

	/**
	 * Test getImportCandidateAsResourceInWriteMode.
	 */
	@Test
	void testGetImportCandidateAsResourceInWriteMode() {
		// Arrange
		WritableResource mockResource = mock(WritableResource.class);
		when(mockResolver.getResource(anyString())).thenReturn(mockResource);

		// Act
		WritableResource result = resourceGovernor.getImportCandidateAsResourceInWriteMode();

		// Assert
		assertNotNull(result, "Result should not be null");
		assertSame(mockResource, result, "Should return the mock writable resource");
		verify(mockResolver).getResource("file:////test/import/import_candidate.json");
	}

	/**
	 * Test getImportCandidateAsResourceInWriteMode multiple times.
	 */
	@Test
	void testGetImportCandidateAsResourceInWriteMode_MultipleCalls() {
		// Arrange
		WritableResource mockResource1 = mock(WritableResource.class);
		WritableResource mockResource2 = mock(WritableResource.class);
		when(mockResolver.getResource(anyString()))
			.thenReturn(mockResource1)
			.thenReturn(mockResource2);

		// Act
		WritableResource result1 = resourceGovernor.getImportCandidateAsResourceInWriteMode();
		WritableResource result2 = resourceGovernor.getImportCandidateAsResourceInWriteMode();

		// Assert
		assertSame(mockResource1, result1, "First call should return first mock writable resource");
		assertSame(mockResource2, result2, "Second call should return second mock writable resource");
		verify(mockResolver, times(2)).getResource("file:////test/import/import_candidate.json");
	}

	/**
	 * Test getRefinitivPropertiesAsResourceInReadMode.
	 */
	@Test
	void testGetRefinitivPropertiesAsResourceInReadMode() {
		// Arrange
		Resource mockResource = mock(Resource.class);
		when(mockResolver.getResource(anyString())).thenReturn(mockResource);

		// Act
		Resource result = resourceGovernor.getRefinitivPropertiesAsResourceInReadMode();

		// Assert
		assertNotNull(result, "Result should not be null");
		assertSame(mockResource, result, "Should return the mock resource");
		verify(mockResolver).getResource("file:////test/refinitiv.properties");
	}

	/**
	 * Test getRefinitivPropertiesAsResourceInReadMode with different file paths.
	 */
	@Test
	void testGetRefinitivPropertiesAsResourceInReadMode_DifferentPath() {
		// Arrange
		ReflectionTestUtils.setField(resourceGovernor, "refinitivConnectionPropertiesFile", "/custom/path/refinitiv.cfg");
		Resource mockResource = mock(Resource.class);
		when(mockResolver.getResource(anyString())).thenReturn(mockResource);

		// Act
		Resource result = resourceGovernor.getRefinitivPropertiesAsResourceInReadMode();

		// Assert
		assertNotNull(result, "Result should not be null");
		verify(mockResolver).getResource("file:////custom/path/refinitiv.cfg");
	}

	/**
	 * Test getDatabasePropertiesAsResourceInReadMode.
	 */
	@Test
	void testGetDatabasePropertiesAsResourceInReadMode() {
		// Arrange
		Resource mockResource = mock(Resource.class);
		when(mockResolver.getResource(anyString())).thenReturn(mockResource);

		// Act
		Resource result = resourceGovernor.getDatabasePropertiesAsResourceInReadMode();

		// Assert
		assertNotNull(result, "Result should not be null");
		assertSame(mockResource, result, "Should return the mock resource");
		verify(mockResolver).getResource("file:////test/database.properties");
	}

	/**
	 * Test getDatabasePropertiesAsResourceInReadMode with different file paths.
	 */
	@Test
	void testGetDatabasePropertiesAsResourceInReadMode_DifferentPath() {
		// Arrange
		ReflectionTestUtils.setField(resourceGovernor, "databaseConnectionPropertiesFile", "/another/db.props");
		Resource mockResource = mock(Resource.class);
		when(mockResolver.getResource(anyString())).thenReturn(mockResource);

		// Act
		Resource result = resourceGovernor.getDatabasePropertiesAsResourceInReadMode();

		// Assert
		assertNotNull(result, "Result should not be null");
		verify(mockResolver).getResource("file:////another/db.props");
	}

	/**
	 * Test getReadableResource with MARKET_DATA_FOLDER.
	 */
	@Test
	void testGetReadableResource_MarketDataFolder() {
		// Arrange
		String username = "testuser";
		String filename = "test.json";
		Resource mockResource = mock(Resource.class);
		when(mockResolver.getResource(anyString())).thenReturn(mockResource);

		// Act
		Resource result = resourceGovernor.getReadableResource(username,
			ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER, filename);

		// Assert
		assertNotNull(result, "Result should not be null");
		assertSame(mockResource, result, "Should return the mock resource");
		verify(mockResolver).getResource("file:////test/storage//testuser.marketdata/test.json");
	}

	/**
	 * Test getReadableResource with SAVED_CONTRACTS_FOLDER.
	 */
	@Test
	void testGetReadableResource_SavedContractsFolder() {
		// Arrange
		String username = "john";
		String filename = "contract123.xml";
		Resource mockResource = mock(Resource.class);
		when(mockResolver.getResource(anyString())).thenReturn(mockResource);

		// Act
		Resource result = resourceGovernor.getReadableResource(username,
			ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER, filename);

		// Assert
		assertNotNull(result, "Result should not be null");
		assertSame(mockResource, result, "Should return the mock resource");
		verify(mockResolver).getResource("file:////test/storage//john.savedcontracts/contract123.xml");
	}

	/**
	 * Test getReadableResource with different usernames and filenames.
	 */
	@Test
	void testGetReadableResource_DifferentParameters() {
		// Arrange
		Resource mockResource1 = mock(Resource.class);
		Resource mockResource2 = mock(Resource.class);
		when(mockResolver.getResource(contains("user1"))).thenReturn(mockResource1);
		when(mockResolver.getResource(contains("user2"))).thenReturn(mockResource2);

		// Act
		Resource result1 = resourceGovernor.getReadableResource("user1",
			ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER, "data1.json");
		Resource result2 = resourceGovernor.getReadableResource("user2",
			ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER, "data2.json");

		// Assert
		assertSame(mockResource1, result1, "Should return first mock resource");
		assertSame(mockResource2, result2, "Should return second mock resource");
		verify(mockResolver).getResource("file:////test/storage//user1.marketdata/data1.json");
		verify(mockResolver).getResource("file:////test/storage//user2.savedcontracts/data2.json");
	}

	/**
	 * Test getReadableResource with empty filename.
	 */
	@Test
	void testGetReadableResource_EmptyFilename() {
		// Arrange
		Resource mockResource = mock(Resource.class);
		when(mockResolver.getResource(anyString())).thenReturn(mockResource);

		// Act
		Resource result = resourceGovernor.getReadableResource("testuser",
			ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER, "");

		// Assert
		assertNotNull(result, "Result should not be null");
		verify(mockResolver).getResource("file:////test/storage//testuser.marketdata/");
	}

	/**
	 * Test getWritableResource with MARKET_DATA_FOLDER.
	 */
	@Test
	void testGetWritableResource_MarketDataFolder() {
		// Arrange
		String username = "testuser";
		String filename = "output.json";
		WritableResource mockResource = mock(WritableResource.class);
		when(mockResolver.getResource(anyString())).thenReturn(mockResource);

		// Act
		WritableResource result = resourceGovernor.getWritableResource(username,
			ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER, filename);

		// Assert
		assertNotNull(result, "Result should not be null");
		assertSame(mockResource, result, "Should return the mock writable resource");
		verify(mockResolver).getResource("file:////test/storage//testuser.marketdata/output.json");
	}

	/**
	 * Test getWritableResource with SAVED_CONTRACTS_FOLDER.
	 */
	@Test
	void testGetWritableResource_SavedContractsFolder() {
		// Arrange
		String username = "alice";
		String filename = "new_contract.xml";
		WritableResource mockResource = mock(WritableResource.class);
		when(mockResolver.getResource(anyString())).thenReturn(mockResource);

		// Act
		WritableResource result = resourceGovernor.getWritableResource(username,
			ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER, filename);

		// Assert
		assertNotNull(result, "Result should not be null");
		assertSame(mockResource, result, "Should return the mock writable resource");
		verify(mockResolver).getResource("file:////test/storage//alice.savedcontracts/new_contract.xml");
	}

	/**
	 * Test getWritableResource with different parameters.
	 */
	@Test
	void testGetWritableResource_DifferentParameters() {
		// Arrange
		WritableResource mockResource1 = mock(WritableResource.class);
		WritableResource mockResource2 = mock(WritableResource.class);
		when(mockResolver.getResource(contains("bob"))).thenReturn(mockResource1);
		when(mockResolver.getResource(contains("charlie"))).thenReturn(mockResource2);

		// Act
		WritableResource result1 = resourceGovernor.getWritableResource("bob",
			ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER, "file1.txt");
		WritableResource result2 = resourceGovernor.getWritableResource("charlie",
			ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER, "file2.txt");

		// Assert
		assertSame(mockResource1, result1, "Should return first mock writable resource");
		assertSame(mockResource2, result2, "Should return second mock writable resource");
		verify(mockResolver).getResource("file:////test/storage//bob.marketdata/file1.txt");
		verify(mockResolver).getResource("file:////test/storage//charlie.savedcontracts/file2.txt");
	}

	/**
	 * Test getWritableResource with filename containing path separators.
	 */
	@Test
	void testGetWritableResource_FilenameWithPath() {
		// Arrange
		WritableResource mockResource = mock(WritableResource.class);
		when(mockResolver.getResource(anyString())).thenReturn(mockResource);

		// Act
		WritableResource result = resourceGovernor.getWritableResource("user",
			ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER, "subdir/file.json");

		// Assert
		assertNotNull(result, "Result should not be null");
		verify(mockResolver).getResource("file:////test/storage//user.marketdata/subdir/file.json");
	}

	/**
	 * Test listContentsOfUserFolder with MARKET_DATA_FOLDER.
	 */
	@Test
	void testListContentsOfUserFolder_MarketDataFolder() throws IOException {
		// Arrange
		String username = "testuser";
		Resource[] mockResources = new Resource[] {
			mock(Resource.class),
			mock(Resource.class),
			mock(Resource.class)
		};
		when(mockResolver.getResources(anyString())).thenReturn(mockResources);

		// Act
		Resource[] result = resourceGovernor.listContentsOfUserFolder(username,
			ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER);

		// Assert
		assertNotNull(result, "Result should not be null");
		assertEquals(3, result.length, "Should return 3 resources");
		assertSame(mockResources, result, "Should return the mock resources array");
		verify(mockResolver).getResources("file:////test/storage//testuser.marketdata/*");
	}

	/**
	 * Test listContentsOfUserFolder with SAVED_CONTRACTS_FOLDER.
	 */
	@Test
	void testListContentsOfUserFolder_SavedContractsFolder() throws IOException {
		// Arrange
		String username = "alice";
		Resource[] mockResources = new Resource[] {
			mock(Resource.class),
			mock(Resource.class)
		};
		when(mockResolver.getResources(anyString())).thenReturn(mockResources);

		// Act
		Resource[] result = resourceGovernor.listContentsOfUserFolder(username,
			ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER);

		// Assert
		assertNotNull(result, "Result should not be null");
		assertEquals(2, result.length, "Should return 2 resources");
		assertSame(mockResources, result, "Should return the mock resources array");
		verify(mockResolver).getResources("file:////test/storage//alice.savedcontracts/*");
	}

	/**
	 * Test listContentsOfUserFolder with empty folder.
	 */
	@Test
	void testListContentsOfUserFolder_EmptyFolder() throws IOException {
		// Arrange
		String username = "newuser";
		Resource[] emptyResources = new Resource[0];
		when(mockResolver.getResources(anyString())).thenReturn(emptyResources);

		// Act
		Resource[] result = resourceGovernor.listContentsOfUserFolder(username,
			ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER);

		// Assert
		assertNotNull(result, "Result should not be null");
		assertEquals(0, result.length, "Should return empty array for empty folder");
		verify(mockResolver).getResources("file:////test/storage//newuser.marketdata/*");
	}

	/**
	 * Test listContentsOfUserFolder throws IOException.
	 */
	@Test
	void testListContentsOfUserFolder_ThrowsIOException() throws IOException {
		// Arrange
		String username = "testuser";
		when(mockResolver.getResources(anyString())).thenThrow(new IOException("Test IO error"));

		// Act & Assert
		assertThrows(IOException.class, () -> {
			resourceGovernor.listContentsOfUserFolder(username,
				ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER);
		}, "Should throw IOException when resolver throws IOException");

		verify(mockResolver).getResources("file:////test/storage//testuser.marketdata/*");
	}

	/**
	 * Test listContentsOfUserFolder with different users.
	 */
	@Test
	void testListContentsOfUserFolder_DifferentUsers() throws IOException {
		// Arrange
		Resource[] resources1 = new Resource[] { mock(Resource.class) };
		Resource[] resources2 = new Resource[] { mock(Resource.class), mock(Resource.class) };
		when(mockResolver.getResources(contains("user1"))).thenReturn(resources1);
		when(mockResolver.getResources(contains("user2"))).thenReturn(resources2);

		// Act
		Resource[] result1 = resourceGovernor.listContentsOfUserFolder("user1",
			ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER);
		Resource[] result2 = resourceGovernor.listContentsOfUserFolder("user2",
			ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER);

		// Assert
		assertEquals(1, result1.length, "User1 should have 1 resource");
		assertEquals(2, result2.length, "User2 should have 2 resources");
		verify(mockResolver).getResources("file:////test/storage//user1.marketdata/*");
		verify(mockResolver).getResources("file:////test/storage//user2.savedcontracts/*");
	}

	/**
	 * Test that RoleFolders enum has expected values.
	 */
	@Test
	void testRoleFolders_EnumValues() {
		// Act
		ResourceGovernor.RoleFolders[] values = ResourceGovernor.RoleFolders.values();

		// Assert
		assertEquals(2, values.length, "Should have 2 role folders");
		assertEquals(ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER, values[0],
			"First value should be MARKET_DATA_FOLDER");
		assertEquals(ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER, values[1],
			"Second value should be SAVED_CONTRACTS_FOLDER");
	}

	/**
	 * Test RoleFolders toString method.
	 */
	@Test
	void testRoleFolders_ToString() {
		// Act & Assert
		assertEquals("/%s.marketdata/",
			ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER.toString(),
			"MARKET_DATA_FOLDER toString should return template");
		assertEquals("/%s.savedcontracts/",
			ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER.toString(),
			"SAVED_CONTRACTS_FOLDER toString should return template");
	}

	/**
	 * Test RoleFolders can be used with String.format.
	 */
	@Test
	void testRoleFolders_FormattingWithUsername() {
		// Act
		String marketDataPath = ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER.toString().formatted("john");
		String savedContractsPath = ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER.toString().formatted("jane");

		// Assert
		assertEquals("/john.marketdata/", marketDataPath, "Should format username into market data path");
		assertEquals("/jane.savedcontracts/", savedContractsPath, "Should format username into saved contracts path");
	}

	/**
	 * Test RoleFolders valueOf with valid MARKET_DATA_FOLDER name.
	 */
	@Test
	void testRoleFolders_ValueOf_MarketDataFolder() {
		// Act
		ResourceGovernor.RoleFolders result = ResourceGovernor.RoleFolders.valueOf("MARKET_DATA_FOLDER");

		// Assert
		assertNotNull(result, "valueOf should return non-null value");
		assertEquals(ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER, result,
			"valueOf should return MARKET_DATA_FOLDER constant");
		assertEquals("/%s.marketdata/", result.toString(), "Should have correct toString value");
	}

	/**
	 * Test RoleFolders valueOf with valid SAVED_CONTRACTS_FOLDER name.
	 */
	@Test
	void testRoleFolders_ValueOf_SavedContractsFolder() {
		// Act
		ResourceGovernor.RoleFolders result = ResourceGovernor.RoleFolders.valueOf("SAVED_CONTRACTS_FOLDER");

		// Assert
		assertNotNull(result, "valueOf should return non-null value");
		assertEquals(ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER, result,
			"valueOf should return SAVED_CONTRACTS_FOLDER constant");
		assertEquals("/%s.savedcontracts/", result.toString(), "Should have correct toString value");
	}

	/**
	 * Test RoleFolders valueOf with invalid name throws IllegalArgumentException.
	 */
	@Test
	void testRoleFolders_ValueOf_InvalidName() {
		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> {
			ResourceGovernor.RoleFolders.valueOf("INVALID_FOLDER");
		}, "valueOf should throw IllegalArgumentException for invalid enum name");
	}

	/**
	 * Test RoleFolders valueOf with null throws NullPointerException.
	 */
	@Test
	void testRoleFolders_ValueOf_Null() {
		// Act & Assert
		assertThrows(NullPointerException.class, () -> {
			ResourceGovernor.RoleFolders.valueOf(null);
		}, "valueOf should throw NullPointerException for null parameter");
	}

	/**
	 * Test RoleFolders valueOf with empty string throws IllegalArgumentException.
	 */
	@Test
	void testRoleFolders_ValueOf_EmptyString() {
		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> {
			ResourceGovernor.RoleFolders.valueOf("");
		}, "valueOf should throw IllegalArgumentException for empty string");
	}

	/**
	 * Test RoleFolders valueOf with lowercase name throws IllegalArgumentException.
	 * valueOf is case-sensitive and requires exact match.
	 */
	@Test
	void testRoleFolders_ValueOf_CaseSensitive() {
		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> {
			ResourceGovernor.RoleFolders.valueOf("market_data_folder");
		}, "valueOf should throw IllegalArgumentException for lowercase enum name (case-sensitive)");
	}

	/**
	 * Test RoleFolders valueOf returns same instance as enum constant.
	 */
	@Test
	void testRoleFolders_ValueOf_ReturnsSameInstance() {
		// Act
		ResourceGovernor.RoleFolders result1 = ResourceGovernor.RoleFolders.valueOf("MARKET_DATA_FOLDER");
		ResourceGovernor.RoleFolders result2 = ResourceGovernor.RoleFolders.valueOf("MARKET_DATA_FOLDER");

		// Assert
		assertSame(ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER, result1,
			"valueOf should return same instance as enum constant");
		assertSame(result1, result2, "Multiple valueOf calls should return same instance");
	}

	/**
	 * Test RoleFolders valueOf with whitespace throws IllegalArgumentException.
	 */
	@Test
	void testRoleFolders_ValueOf_WithWhitespace() {
		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> {
			ResourceGovernor.RoleFolders.valueOf(" MARKET_DATA_FOLDER ");
		}, "valueOf should throw IllegalArgumentException for name with whitespace");
	}

	/**
	 * Test that null storageBaseDir causes NullPointerException in getActiveDatasetAsResourceInReadMode.
	 */
	@Test
	void testGetActiveDatasetAsResourceInReadMode_NullStorageBaseDir() {
		// Arrange
		ReflectionTestUtils.setField(resourceGovernor, "storageBaseDir", null);

		// Act & Assert
		assertThrows(NullPointerException.class, () -> {
			resourceGovernor.getActiveDatasetAsResourceInReadMode("user");
		}, "Should throw NullPointerException when storageBaseDir is null");
	}

	/**
	 * Test that null importDir causes NullPointerException in getImportCandidateAsResourceInReadMode.
	 */
	@Test
	void testGetImportCandidateAsResourceInReadMode_NullImportDir() {
		// Arrange
		ReflectionTestUtils.setField(resourceGovernor, "importDir", null);

		// Act & Assert
		assertThrows(NullPointerException.class, () -> {
			resourceGovernor.getImportCandidateAsResourceInReadMode();
		}, "Should throw NullPointerException when importDir is null");
	}
}
