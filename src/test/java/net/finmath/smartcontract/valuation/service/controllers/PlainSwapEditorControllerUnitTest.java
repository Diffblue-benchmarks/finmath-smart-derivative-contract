package net.finmath.smartcontract.valuation.service.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.finmath.smartcontract.model.PlainSwapOperationRequest;
import net.finmath.smartcontract.model.SaveContractRequest;
import net.finmath.smartcontract.valuation.marketdata.database.DatabaseConnector;
import net.finmath.smartcontract.valuation.service.config.ValuationConfig;
import net.finmath.smartcontract.valuation.service.utils.ResourceGovernor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.info.BuildProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.ErrorResponseException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlainSwapEditorControllerUnitTest {

	private DatabaseConnector databaseConnector;
	private ResourceGovernor resourceGovernor;
	private ObjectMapper objectMapper;
	private ValuationConfig valuationConfig;
	private ValuationConfig valuationConfig1;
	private BuildProperties buildProperties;
	private ResourceLoader resourceLoader;
	private PlainSwapEditorController controller;

	@BeforeEach
	void setUp() {
		databaseConnector = mock(DatabaseConnector.class);
		resourceGovernor = mock(ResourceGovernor.class);
		objectMapper = new ObjectMapper();
		valuationConfig = mock(ValuationConfig.class);
		valuationConfig1 = mock(ValuationConfig.class);
		buildProperties = mock(BuildProperties.class);
		resourceLoader = mock(ResourceLoader.class);

		when(valuationConfig.getFpmlSchemaPath()).thenReturn("schemas/fpml");
		when(buildProperties.getVersion()).thenReturn("1.0.0-TEST");

		controller = new PlainSwapEditorController(
				databaseConnector, resourceGovernor, objectMapper,
				valuationConfig, valuationConfig1, buildProperties, resourceLoader
		);

		UserDetails userDetails = User.builder()
				.username("testuser")
				.password("password")
				.authorities("ROLE_USER")
				.build();
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
		);
	}

	@AfterEach
	void tearDown() {
		SecurityContextHolder.clearContext();
	}

	@Test
	void testConstructor() {
		assertNotNull(controller);
	}

	@Test
	void testChangeDatasetWithUseLive() {
		ResponseEntity<String> response = controller.changeDataset("USELIVE");
		assertEquals(200, response.getStatusCode().value());
		assertEquals("idle ok", response.getBody());
	}

	@Test
	void testChangeDatasetWithFileIoException() throws IOException {
		Resource sourceResource = mock(Resource.class);
		when(resourceGovernor.getReadableResource("testuser",
				ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER, "test.json"))
				.thenReturn(sourceResource);
		when(sourceResource.getFile()).thenThrow(new IOException("file not found"));

		assertThrows(ErrorResponseException.class, () -> controller.changeDataset("test.json"));
	}

	@Test
	void testSaveContractWithInvalidName() {
		SaveContractRequest request = new SaveContractRequest();
		request.setName("invalid name with spaces!");
		request.setPlainSwapOperationRequest(new PlainSwapOperationRequest());

		ResponseEntity<String> response = controller.saveContract(request);
		assertEquals(200, response.getStatusCode().value());
		assertEquals("Request not fulfilled.", response.getBody());
	}

	@Test
	void testGetSavedContractsIoException() throws IOException {
		when(resourceGovernor.listContentsOfUserFolder("testuser",
				ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER))
				.thenThrow(new IOException("storage error"));

		assertThrows(ErrorResponseException.class, () -> controller.getSavedContracts());
	}

	@Test
	void testGetSavedContractsSuccess() throws IOException {
		Resource mockResource = mock(Resource.class);
		when(mockResource.getFilename()).thenReturn("contract1.json");
		when(resourceGovernor.listContentsOfUserFolder("testuser",
				ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER))
				.thenReturn(new Resource[]{mockResource});

		ResponseEntity<?> response = controller.getSavedContracts();
		assertEquals(200, response.getStatusCode().value());
	}

	@Test
	void testGetSavedMarketDataIoException() throws IOException {
		when(resourceGovernor.listContentsOfUserFolder("testuser",
				ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER))
				.thenThrow(new IOException("storage error"));

		assertThrows(ErrorResponseException.class, () -> controller.getSavedMarketData());
	}

	@Test
	void testGetSavedMarketDataSuccess() throws IOException {
		Resource mockResource = mock(Resource.class);
		when(mockResource.getFilename()).thenReturn("marketdata1.json");
		when(resourceGovernor.listContentsOfUserFolder("testuser",
				ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER))
				.thenReturn(new Resource[]{mockResource});

		ResponseEntity<?> response = controller.getSavedMarketData();
		assertEquals(200, response.getStatusCode().value());
	}

	@Test
	void testGrabMarketDataIoException() throws IOException {
		Resource activeDataset = mock(Resource.class);
		when(resourceGovernor.getActiveDatasetAsResourceInReadMode("testuser"))
				.thenReturn(activeDataset);
		when(activeDataset.getContentAsString(StandardCharsets.UTF_8))
				.thenThrow(new IOException("read error"));

		assertThrows(ErrorResponseException.class, () -> controller.grabMarketData());
	}

	@Test
	void testGeneratePlainSwapSdcmlResourceLoadingFails() throws IOException {
		Map<String, String> templateMap = Map.of("refinitiv", "classpath:template.xml");
		when(valuationConfig1.getMarketDataProviderToTemplate()).thenReturn(templateMap);
		Resource mockResource = mock(Resource.class);
		when(resourceLoader.getResource("classpath:template.xml")).thenReturn(mockResource);
		when(mockResource.getInputStream()).thenThrow(new IOException("template not found"));

		PlainSwapOperationRequest request = new PlainSwapOperationRequest();
		request.setMarketDataProvider("refinitiv");

		assertThrows(RuntimeException.class, () -> controller.generatePlainSwapSdcml(request));
	}

	@Test
	void testLoadContractIoException() throws IOException {
		when(resourceGovernor.listContentsOfUserFolder("testuser",
				ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER))
				.thenThrow(new IOException("storage error"));

		assertThrows(ErrorResponseException.class, () -> controller.loadContract("test.json"));
	}

	@Test
	void testGetFixedScheduleMarketDataIoException() throws IOException {
		Resource activeDataset = mock(Resource.class);
		when(resourceGovernor.getActiveDatasetAsResourceInReadMode("testuser"))
				.thenReturn(activeDataset);
		when(activeDataset.getContentAsString(StandardCharsets.UTF_8))
				.thenThrow(new IOException("read error"));

		PlainSwapOperationRequest request = new PlainSwapOperationRequest();
		assertThrows(ErrorResponseException.class, () -> controller.getFixedSchedule(request));
	}

	@Test
	void testGetFloatingScheduleMarketDataIoException() throws IOException {
		Resource activeDataset = mock(Resource.class);
		when(resourceGovernor.getActiveDatasetAsResourceInReadMode("testuser"))
				.thenReturn(activeDataset);
		when(activeDataset.getContentAsString(StandardCharsets.UTF_8))
				.thenThrow(new IOException("read error"));

		PlainSwapOperationRequest request = new PlainSwapOperationRequest();
		assertThrows(ErrorResponseException.class, () -> controller.getFloatingSchedule(request));
	}

	@Test
	void testGetParRateMarketDataIoException() throws IOException {
		Resource activeDataset = mock(Resource.class);
		when(resourceGovernor.getActiveDatasetAsResourceInReadMode("testuser"))
				.thenReturn(activeDataset);
		when(activeDataset.getContentAsString(StandardCharsets.UTF_8))
				.thenThrow(new IOException("read error"));

		PlainSwapOperationRequest request = new PlainSwapOperationRequest();
		assertThrows(ErrorResponseException.class, () -> controller.getParRate(request));
	}

	@Test
	void testUploadMarketDataIoException() throws IOException {
		org.springframework.web.multipart.MultipartFile multipartFile =
				mock(org.springframework.web.multipart.MultipartFile.class);
		when(multipartFile.getOriginalFilename()).thenReturn("test.json");
		org.springframework.core.io.WritableResource writableResource =
				mock(org.springframework.core.io.WritableResource.class);
		when(resourceGovernor.getImportCandidateAsResourceInWriteMode()).thenReturn(writableResource);
		when(writableResource.getOutputStream()).thenThrow(new IOException("write error"));

		assertThrows(ErrorResponseException.class, () -> controller.uploadMarketData(multipartFile));
	}

	@Test
	void testUploadMarketDataSuccess() throws IOException, SQLException {
		org.springframework.web.multipart.MultipartFile multipartFile =
				mock(org.springframework.web.multipart.MultipartFile.class);
		when(multipartFile.getOriginalFilename()).thenReturn("test.json");
		when(multipartFile.getBytes()).thenReturn("market data content".getBytes(StandardCharsets.UTF_8));

		org.springframework.core.io.WritableResource importResource =
				mock(org.springframework.core.io.WritableResource.class);
		org.springframework.core.io.WritableResource userResource =
				mock(org.springframework.core.io.WritableResource.class);
		ByteArrayOutputStream importOut = new ByteArrayOutputStream();
		ByteArrayOutputStream userOut = new ByteArrayOutputStream();

		when(resourceGovernor.getImportCandidateAsResourceInWriteMode()).thenReturn(importResource);
		when(importResource.getOutputStream()).thenReturn(importOut);
		when(resourceGovernor.getWritableResource("testuser",
				ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER, "test.json"))
				.thenReturn(userResource);
		when(userResource.getOutputStream()).thenReturn(userOut);

		ResponseEntity<String> response = controller.uploadMarketData(multipartFile);

		assertEquals(200, response.getStatusCode().value());
		assertEquals("ok", response.getBody());
		verify(databaseConnector).updateDatabase();
		assertArrayEquals("market data content".getBytes(StandardCharsets.UTF_8), importOut.toByteArray());
		assertArrayEquals("market data content".getBytes(StandardCharsets.UTF_8), userOut.toByteArray());
	}

	@Test
	void testUploadMarketDataSqlException() throws IOException, SQLException {
		org.springframework.web.multipart.MultipartFile multipartFile =
				mock(org.springframework.web.multipart.MultipartFile.class);
		when(multipartFile.getOriginalFilename()).thenReturn("test.json");
		when(multipartFile.getBytes()).thenReturn("data".getBytes(StandardCharsets.UTF_8));

		org.springframework.core.io.WritableResource importResource =
				mock(org.springframework.core.io.WritableResource.class);
		org.springframework.core.io.WritableResource userResource =
				mock(org.springframework.core.io.WritableResource.class);

		when(resourceGovernor.getImportCandidateAsResourceInWriteMode()).thenReturn(importResource);
		when(importResource.getOutputStream()).thenReturn(new ByteArrayOutputStream());
		when(resourceGovernor.getWritableResource("testuser",
				ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER, "test.json"))
				.thenReturn(userResource);
		when(userResource.getOutputStream()).thenReturn(new ByteArrayOutputStream());
		doThrow(new SQLException("db error")).when(databaseConnector).updateDatabase();

		assertThrows(ErrorResponseException.class, () -> controller.uploadMarketData(multipartFile));
	}
}
