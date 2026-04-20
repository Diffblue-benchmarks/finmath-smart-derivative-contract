package net.finmath.smartcontract.valuation.service.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ResourceGovernorTest {

	private ResourcePatternResolver resolver;
	private ResourceGovernor governor;

	@BeforeEach
	void setUp() throws Exception {
		resolver = mock(ResourcePatternResolver.class);
		governor = new ResourceGovernor(resolver);

		setField("storageBaseDir", "/tmp/storage");
		setField("importDir", "/tmp/import");
		setField("refinitivConnectionPropertiesFile", "/tmp/refinitiv.properties");
		setField("databaseConnectionPropertiesFile", "/tmp/database.properties");
	}

	private void setField(String fieldName, String value) throws Exception {
		Field field = ResourceGovernor.class.getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(governor, value);
	}

	@Test
	void testGetActiveDatasetAsResourceInReadMode() {
		Resource mockResource = mock(Resource.class);
		when(resolver.getResource(anyString())).thenReturn(mockResource);

		Resource result = governor.getActiveDatasetAsResourceInReadMode("testuser");

		assertSame(mockResource, result);
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(resolver).getResource(captor.capture());
		assertTrue(captor.getValue().contains("testuser"));
		assertTrue(captor.getValue().contains("active_dataset.json"));
	}

	@Test
	void testGetActiveDatasetAsResourceInWriteMode() {
		WritableResource mockResource = mock(WritableResource.class);
		when(resolver.getResource(anyString())).thenReturn(mockResource);

		WritableResource result = governor.getActiveDatasetAsResourceInWriteMode("testuser");

		assertSame(mockResource, result);
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(resolver).getResource(captor.capture());
		assertTrue(captor.getValue().contains("testuser"));
		assertTrue(captor.getValue().contains("active_dataset.json"));
	}

	@Test
	void testGetImportCandidateAsResourceInReadMode() {
		Resource mockResource = mock(Resource.class);
		when(resolver.getResource(anyString())).thenReturn(mockResource);

		Resource result = governor.getImportCandidateAsResourceInReadMode();

		assertSame(mockResource, result);
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(resolver).getResource(captor.capture());
		assertTrue(captor.getValue().contains("import_candidate.json"));
	}

	@Test
	void testGetImportCandidateAsResourceInWriteMode() {
		WritableResource mockResource = mock(WritableResource.class);
		when(resolver.getResource(anyString())).thenReturn(mockResource);

		WritableResource result = governor.getImportCandidateAsResourceInWriteMode();

		assertSame(mockResource, result);
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(resolver).getResource(captor.capture());
		assertTrue(captor.getValue().contains("import_candidate.json"));
	}

	@Test
	void testGetRefinitivPropertiesAsResourceInReadMode() {
		Resource mockResource = mock(Resource.class);
		when(resolver.getResource(anyString())).thenReturn(mockResource);

		Resource result = governor.getRefinitivPropertiesAsResourceInReadMode();

		assertSame(mockResource, result);
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(resolver).getResource(captor.capture());
		assertTrue(captor.getValue().contains("refinitiv.properties"));
	}

	@Test
	void testGetDatabasePropertiesAsResourceInReadMode() {
		Resource mockResource = mock(Resource.class);
		when(resolver.getResource(anyString())).thenReturn(mockResource);

		Resource result = governor.getDatabasePropertiesAsResourceInReadMode();

		assertSame(mockResource, result);
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(resolver).getResource(captor.capture());
		assertTrue(captor.getValue().contains("database.properties"));
	}

	@Test
	void testGetReadableResource() {
		Resource mockResource = mock(Resource.class);
		when(resolver.getResource(anyString())).thenReturn(mockResource);

		Resource result = governor.getReadableResource("alice", ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER, "data.json");

		assertSame(mockResource, result);
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(resolver).getResource(captor.capture());
		assertTrue(captor.getValue().contains("alice"));
		assertTrue(captor.getValue().contains("data.json"));
	}

	@Test
	void testGetWritableResource() {
		WritableResource mockResource = mock(WritableResource.class);
		when(resolver.getResource(anyString())).thenReturn(mockResource);

		WritableResource result = governor.getWritableResource("bob", ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER, "contract.xml");

		assertSame(mockResource, result);
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(resolver).getResource(captor.capture());
		assertTrue(captor.getValue().contains("bob"));
		assertTrue(captor.getValue().contains("contract.xml"));
	}

	@Test
	void testListContentsOfUserFolder() throws IOException {
		Resource[] mockResources = new Resource[]{mock(Resource.class), mock(Resource.class)};
		when(resolver.getResources(anyString())).thenReturn(mockResources);

		Resource[] result = governor.listContentsOfUserFolder("testuser", ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER);

		assertArrayEquals(mockResources, result);
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(resolver).getResources(captor.capture());
		assertTrue(captor.getValue().contains("testuser"));
	}
}
