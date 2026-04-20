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
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;

import org.springframework.core.io.WritableResource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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
	void testChangeDatasetSuccess() throws IOException {
		Resource sourceResource = mock(Resource.class);
		File sourceFile = File.createTempFile("changeDatasetSource", ".json");
		sourceFile.deleteOnExit();
		java.nio.file.Files.writeString(sourceFile.toPath(), "test market data");
		when(resourceGovernor.getReadableResource("testuser",
				ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER, "data.json"))
				.thenReturn(sourceResource);
		when(sourceResource.getFile()).thenReturn(sourceFile);

		WritableResource destResource = mock(WritableResource.class);
		File destFile = File.createTempFile("changeDatasetDest", ".json");
		destFile.deleteOnExit();
		when(resourceGovernor.getActiveDatasetAsResourceInWriteMode("testuser"))
				.thenReturn(destResource);
		when(destResource.getFile()).thenReturn(destFile);

		ResponseEntity<String> response = controller.changeDataset("data.json");

		assertEquals(200, response.getStatusCode().value());
		assertEquals("ok", response.getBody());
		assertEquals("test market data", java.nio.file.Files.readString(destFile.toPath()));
	}

	@Test
	void testChangeDatasetDestinationIoException() throws IOException {
		Resource sourceResource = mock(Resource.class);
		File sourceFile = File.createTempFile("changeDatasetSrc2", ".json");
		sourceFile.deleteOnExit();
		when(resourceGovernor.getReadableResource("testuser",
				ResourceGovernor.RoleFolders.MARKET_DATA_FOLDER, "data2.json"))
				.thenReturn(sourceResource);
		when(sourceResource.getFile()).thenReturn(sourceFile);

		WritableResource destResource = mock(WritableResource.class);
		when(resourceGovernor.getActiveDatasetAsResourceInWriteMode("testuser"))
				.thenReturn(destResource);
		when(destResource.getFile()).thenThrow(new IOException("destination error"));

		assertThrows(ErrorResponseException.class, () -> controller.changeDataset("data2.json"));
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
	void testSaveContractWithValidNameSuccess() throws IOException {
		SaveContractRequest request = new SaveContractRequest();
		request.setName("myContract123");
		PlainSwapOperationRequest operationRequest = new PlainSwapOperationRequest();
		request.setPlainSwapOperationRequest(operationRequest);

		WritableResource writableResource = mock(WritableResource.class);
		File tempFile = File.createTempFile("saveContractTest", ".json");
		tempFile.deleteOnExit();
		when(resourceGovernor.getWritableResource(eq("testuser"),
				eq(ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER),
				contains("myContract123.json")))
				.thenReturn(writableResource);
		when(writableResource.getFile()).thenReturn(tempFile);

		ResponseEntity<String> response = controller.saveContract(request);

		assertEquals(200, response.getStatusCode().value());
		assertTrue(response.getBody().endsWith("myContract123.json"));
	}

	@Test
	void testSaveContractWithValidNameIoException() throws IOException {
		SaveContractRequest request = new SaveContractRequest();
		request.setName("validName");
		request.setPlainSwapOperationRequest(new PlainSwapOperationRequest());

		WritableResource writableResource = mock(WritableResource.class);
		when(resourceGovernor.getWritableResource(eq("testuser"),
				eq(ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER),
				contains("validName.json")))
				.thenReturn(writableResource);
		when(writableResource.getFile()).thenThrow(new IOException("storage error"));

		assertThrows(ErrorResponseException.class, () -> controller.saveContract(request));
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
	void testLoadContractSuccess() throws IOException {
		PlainSwapOperationRequest expectedRequest = new PlainSwapOperationRequest();
		expectedRequest.setTradeType("Payer");
		String json = new ObjectMapper().writeValueAsString(expectedRequest);

		Resource mockResource = mock(Resource.class);
		when(mockResource.getFilename()).thenReturn("myContract.json");
		when(mockResource.getContentAsString(StandardCharsets.UTF_8)).thenReturn(json);
		when(resourceGovernor.listContentsOfUserFolder("testuser",
				ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER))
				.thenReturn(new Resource[]{mockResource});

		ResponseEntity<PlainSwapOperationRequest> response = controller.loadContract("myContract.json");

		assertEquals(200, response.getStatusCode().value());
		assertNotNull(response.getBody());
		assertEquals("Payer", response.getBody().getTradeType());
	}

	@Test
	void testLoadContractNotFound() throws IOException {
		Resource mockResource = mock(Resource.class);
		when(mockResource.getFilename()).thenReturn("otherContract.json");
		when(resourceGovernor.listContentsOfUserFolder("testuser",
				ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER))
				.thenReturn(new Resource[]{mockResource});

		assertThrows(NullPointerException.class, () -> controller.loadContract("nonexistent.json"));
	}

	@Test
	void testLoadContractReadIoException() throws IOException {
		Resource mockResource = mock(Resource.class);
		when(mockResource.getFilename()).thenReturn("badContract.json");
		when(mockResource.getContentAsString(StandardCharsets.UTF_8))
				.thenThrow(new IOException("read error"));
		when(resourceGovernor.listContentsOfUserFolder("testuser",
				ResourceGovernor.RoleFolders.SAVED_CONTRACTS_FOLDER))
				.thenReturn(new Resource[]{mockResource});

		assertThrows(ErrorResponseException.class, () -> controller.loadContract("badContract.json"));
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
	void testGetParRateValuationErrorInLambda() throws IOException {
		Resource activeDataset = mock(Resource.class);
		when(resourceGovernor.getActiveDatasetAsResourceInReadMode("testuser"))
				.thenReturn(activeDataset);
		when(activeDataset.getContentAsString(StandardCharsets.UTF_8))
				.thenReturn("{\"some\":\"marketdata\"}");

		Resource templateResource = mock(Resource.class);
		when(valuationConfig1.getMarketDataProviderToTemplate())
				.thenReturn(Map.of("refinitiv", "classpath:template.xml"));
		when(resourceLoader.getResource("classpath:template.xml")).thenReturn(templateResource);
		when(templateResource.getInputStream())
				.thenReturn(new ByteArrayInputStream("<invalid/>".getBytes(StandardCharsets.UTF_8)));

		PlainSwapOperationRequest request = new PlainSwapOperationRequest();
		request.setMarketDataProvider("refinitiv");
		request.setFixedRate(0.01);
		request.setNotionalAmount(1000000.0);

		assertThrows(ErrorResponseException.class, () -> controller.getParRate(request));
	}

	@Test
	void testGetParRateOuterCatchWhenTemplateResolutionFails() throws IOException {
		Resource activeDataset = mock(Resource.class);
		when(resourceGovernor.getActiveDatasetAsResourceInReadMode("testuser"))
				.thenReturn(activeDataset);
		when(activeDataset.getContentAsString(StandardCharsets.UTF_8))
				.thenReturn("{\"some\":\"marketdata\"}");

		when(valuationConfig1.getMarketDataProviderToTemplate()).thenReturn(null);

		PlainSwapOperationRequest request = new PlainSwapOperationRequest();
		request.setMarketDataProvider("refinitiv");

		assertThrows(ErrorResponseException.class, () -> controller.getParRate(request));
	}

	@Test
	void testRefreshMarketDataIoExceptionFromHandler() throws IOException {
		Map<String, String> templateMap = Map.of("refinitiv", "classpath:template.xml");
		when(valuationConfig1.getMarketDataProviderToTemplate()).thenReturn(templateMap);
		Resource mockResource = mock(Resource.class);
		when(resourceLoader.getResource("classpath:template.xml")).thenReturn(mockResource);
		when(mockResource.getInputStream())
				.thenReturn(new ByteArrayInputStream("<template/>".getBytes(StandardCharsets.UTF_8)));

		PlainSwapOperationRequest request = new PlainSwapOperationRequest();
		request.setMarketDataProvider("refinitiv");

		assertThrows(ErrorResponseException.class, () -> controller.refreshMarketData(request));
	}

	@Test
	void testRefreshMarketDataWhenTemplateResolutionFails() {
		when(valuationConfig1.getMarketDataProviderToTemplate()).thenReturn(null);

		PlainSwapOperationRequest request = new PlainSwapOperationRequest();
		request.setMarketDataProvider("refinitiv");

		assertThrows(NullPointerException.class, () -> controller.refreshMarketData(request));
	}

	@Test
	void testRefreshMarketDataWhenTemplateLoadingThrowsIoException() throws IOException {
		Map<String, String> templateMap = Map.of("refinitiv", "classpath:missing-template.xml");
		when(valuationConfig1.getMarketDataProviderToTemplate()).thenReturn(templateMap);
		Resource mockResource = mock(Resource.class);
		when(resourceLoader.getResource("classpath:missing-template.xml")).thenReturn(mockResource);
		when(mockResource.getInputStream()).thenThrow(new IOException("template not found"));

		PlainSwapOperationRequest request = new PlainSwapOperationRequest();
		request.setMarketDataProvider("refinitiv");

		assertThrows(RuntimeException.class, () -> controller.refreshMarketData(request));
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
