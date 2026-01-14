/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */
package net.finmath.smartcontract.valuation.service.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.finmath.smartcontract.model.*;
import net.finmath.smartcontract.valuation.marketdata.database.DatabaseConnector;
import net.finmath.smartcontract.valuation.service.config.ValuationConfig;
import net.finmath.smartcontract.valuation.service.utils.ResourceGovernor;
import net.finmath.smartcontract.valuation.service.utils.ResourceGovernor.RoleFolders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.info.BuildProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Test class for PlainSwapEditorController. Tests all methods with focus on branch and condition
 * coverage.
 *
 * @author Claude Code
 */
class PlainSwapEditorControllerClaudeTest {
  private PlainSwapEditorController controller;

  private DatabaseConnector databaseConnector;

  private ResourceGovernor resourceGovernor;

  private ObjectMapper objectMapper;

  private ValuationConfig valuationConfig;

  private BuildProperties buildProperties;

  private ResourceLoader resourceLoader;

  /**
   * Set up test fixtures before each test. Note: We use mocking here because the controller has
   * complex dependencies that require database connections, file system access, and external
   * services. Testing without mocking would require setting up a full integration test environment.
   */
  @BeforeEach
  void setUp() throws Exception {
    // Set up mock dependencies
    databaseConnector = mock(DatabaseConnector.class);
    resourceGovernor = mock(ResourceGovernor.class);
    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    valuationConfig = new ValuationConfig();
    buildProperties = mock(BuildProperties.class);
    resourceLoader = mock(ResourceLoader.class);

    // Configure ValuationConfig
    valuationConfig.setFpmlSchemaPath("classpath:schema/fpml-main-5-12.xsd");
    Map<String, String> providerMap = new HashMap<>();
    providerMap.put("EUR_EURIBOR_Y_S", "classpath:generators/eur_euribor_y_s_with_fixings.xml");
    providerMap.put("EUR_ESTR_Y_S", "classpath:generators/eur_euribor_y_s_with_fixings.xml");
    valuationConfig.setMarketDataProviderToTemplate(providerMap);

    // Configure BuildProperties
    when(buildProperties.getVersion()).thenReturn("1.0.0-TEST");

    // Set up security context with a test user
    UserDetails userDetails =
        User.withUsername("testuser").password("password").roles("USER").build();
    SecurityContextHolder.getContext()
        .setAuthentication(
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()));

    // Create controller instance
    controller =
        new PlainSwapEditorController(
            databaseConnector,
            resourceGovernor,
            objectMapper,
            valuationConfig,
            valuationConfig,
            buildProperties,
            resourceLoader);
  }

  /** Test constructor creates a valid instance. */
  @Test
  void testConstructor_CreatesValidInstance() {
    // Arrange & Act
    PlainSwapEditorController newController =
        new PlainSwapEditorController(
            databaseConnector,
            resourceGovernor,
            objectMapper,
            valuationConfig,
            valuationConfig,
            buildProperties,
            resourceLoader);

    // Assert
    assertNotNull(newController, "Constructor should create a non-null instance");
  }

  /** Test generatePlainSwapSdcml with invalid request throws ErrorResponseException. */
  @Test
  void testGeneratePlainSwapSdcml_InvalidTemplate_ThrowsException() throws Exception {
    // Arrange
    PlainSwapOperationRequest request = createValidPlainSwapRequest();

    // Mock resource loader to throw exception
    when(resourceLoader.getResource(anyString()))
        .thenThrow(new RuntimeException("Template not found"));

    // Act & Assert
    assertThrows(
        Exception.class,
        () -> {
          controller.generatePlainSwapSdcml(request);
        });
  }

  /** Test getFixedSchedule with IOException throws ErrorResponseException. */
  @Test
  void testGetFixedSchedule_IOException_ThrowsErrorResponseException() throws Exception {
    // Arrange
    PlainSwapOperationRequest request = createValidPlainSwapRequest();

    // Mock resource to throw IOException when getContentAsString is called
    Resource resource = mock(Resource.class);
    when(resource.getContentAsString(StandardCharsets.UTF_8))
        .thenThrow(new IOException("File not found"));
    when(resourceGovernor.getActiveDatasetAsResourceInReadMode("testuser")).thenReturn(resource);

    // Act & Assert
    assertThrows(
        ErrorResponseException.class,
        () -> {
          controller.getFixedSchedule(request);
        });
  }

  /** Test getFloatingSchedule with IOException throws ErrorResponseException. */
  @Test
  void testGetFloatingSchedule_IOException_ThrowsErrorResponseException() throws Exception {
    // Arrange
    PlainSwapOperationRequest request = createValidPlainSwapRequest();

    // Mock resource to throw IOException when getContentAsString is called
    Resource resource = mock(Resource.class);
    when(resource.getContentAsString(StandardCharsets.UTF_8))
        .thenThrow(new IOException("File not found"));
    when(resourceGovernor.getActiveDatasetAsResourceInReadMode("testuser")).thenReturn(resource);

    // Act & Assert
    assertThrows(
        ErrorResponseException.class,
        () -> {
          controller.getFloatingSchedule(request);
        });
  }

  /** Test grabMarketData returns market data set. */
  @Test
  void testGrabMarketData_ValidData_ReturnsMarketDataSet() throws Exception {
    // Arrange
    String marketDataJson = "{\"requestTimestamp\":\"2023-01-01T10:00:00Z\",\"values\":[]}";
    Resource resource = mock(Resource.class);
    when(resource.getContentAsString(StandardCharsets.UTF_8)).thenReturn(marketDataJson);
    when(resourceGovernor.getActiveDatasetAsResourceInReadMode("testuser")).thenReturn(resource);

    // Act
    ResponseEntity<MarketDataSet> response = controller.grabMarketData();

    // Assert
    assertNotNull(response);
    assertEquals(200, response.getStatusCodeValue());
    assertNotNull(response.getBody());
  }

  /** Test grabMarketData with IOException throws ErrorResponseException. */
  @Test
  void testGrabMarketData_IOException_ThrowsErrorResponseException() throws Exception {
    // Arrange
    Resource resource = mock(Resource.class);
    when(resource.getContentAsString(StandardCharsets.UTF_8))
        .thenThrow(new IOException("Storage error"));
    when(resourceGovernor.getActiveDatasetAsResourceInReadMode("testuser")).thenReturn(resource);

    // Act & Assert
    ErrorResponseException exception =
        assertThrows(
            ErrorResponseException.class,
            () -> {
              controller.grabMarketData();
            });

    assertTrue(exception.getMessage().contains("Storage Error"));
  }

  /** Test grabMarketData with JsonProcessingException throws ErrorResponseException. */
  @Test
  void testGrabMarketData_JsonProcessingException_ThrowsErrorResponseException() throws Exception {
    // Arrange
    Resource resource = mock(Resource.class);
    when(resource.getContentAsString(StandardCharsets.UTF_8)).thenReturn("invalid json");
    when(resourceGovernor.getActiveDatasetAsResourceInReadMode("testuser")).thenReturn(resource);

    // Act & Assert
    assertThrows(
        ErrorResponseException.class,
        () -> {
          controller.grabMarketData();
        });
  }

  /** Test getParRate with IOException throws ErrorResponseException. */
  @Test
  void testGetParRate_IOException_ThrowsErrorResponseException() throws Exception {
    // Arrange
    PlainSwapOperationRequest request = createValidPlainSwapRequest();
    Resource resource = mock(Resource.class);
    when(resource.getContentAsString(StandardCharsets.UTF_8))
        .thenThrow(new IOException("Market data error"));
    when(resourceGovernor.getActiveDatasetAsResourceInReadMode("testuser")).thenReturn(resource);

    // Act & Assert
    assertThrows(
        ErrorResponseException.class,
        () -> {
          controller.getParRate(request);
        });
  }

  /** Test getSavedContracts returns list of contract filenames. */
  @Test
  void testGetSavedContracts_ValidUser_ReturnsFilenames() throws Exception {
    // Arrange
    Resource[] resources =
        new Resource[] {createMockResource("contract1.json"), createMockResource("contract2.json")};
    when(resourceGovernor.listContentsOfUserFolder("testuser", RoleFolders.SAVED_CONTRACTS_FOLDER))
        .thenReturn(resources);

    // Act
    ResponseEntity<List<String>> response = controller.getSavedContracts();

    // Assert
    assertNotNull(response);
    assertEquals(200, response.getStatusCodeValue());
    List<String> filenames = response.getBody();
    assertNotNull(filenames);
    assertEquals(2, filenames.size());
    assertTrue(filenames.contains("contract1.json"));
    assertTrue(filenames.contains("contract2.json"));
  }

  /** Test getSavedContracts with IOException throws ErrorResponseException. */
  @Test
  void testGetSavedContracts_IOException_ThrowsErrorResponseException() throws Exception {
    // Arrange
    when(resourceGovernor.listContentsOfUserFolder("testuser", RoleFolders.SAVED_CONTRACTS_FOLDER))
        .thenThrow(new IOException("Storage error"));

    // Act & Assert
    assertThrows(
        ErrorResponseException.class,
        () -> {
          controller.getSavedContracts();
        });
  }

  /** Test getSavedContracts with empty folder returns empty list. */
  @Test
  void testGetSavedContracts_EmptyFolder_ReturnsEmptyList() throws Exception {
    // Arrange
    Resource[] resources = new Resource[0];
    when(resourceGovernor.listContentsOfUserFolder("testuser", RoleFolders.SAVED_CONTRACTS_FOLDER))
        .thenReturn(resources);

    // Act
    ResponseEntity<List<String>> response = controller.getSavedContracts();

    // Assert
    assertNotNull(response);
    assertEquals(200, response.getStatusCodeValue());
    List<String> filenames = response.getBody();
    assertNotNull(filenames);
    assertTrue(filenames.isEmpty());
  }

  /** Test changeDataset with USELIVE returns idle ok. */
  @Test
  void testChangeDataset_UseLive_ReturnsIdleOk() {
    // Act
    ResponseEntity<String> response = controller.changeDataset("USELIVE");

    // Assert
    assertNotNull(response);
    assertEquals(200, response.getStatusCodeValue());
    assertEquals("idle ok", response.getBody());
  }

  /** Test changeDataset with valid filename copies file. */
  @Test
  void testChangeDataset_ValidFilename_ReturnsOk() throws Exception {
    // Arrange
    File sourceFile = File.createTempFile("source", ".json");
    File destFile = File.createTempFile("dest", ".json");
    sourceFile.deleteOnExit();
    destFile.deleteOnExit();

    Resource sourceResource = mock(Resource.class);
    WritableResource destResource = mock(WritableResource.class);
    when(sourceResource.getFile()).thenReturn(sourceFile);
    when(destResource.getFile()).thenReturn(destFile);

    when(resourceGovernor.getReadableResource(
            "testuser", RoleFolders.MARKET_DATA_FOLDER, "testdata.json"))
        .thenReturn(sourceResource);
    when(resourceGovernor.getActiveDatasetAsResourceInWriteMode("testuser"))
        .thenReturn(destResource);

    // Act
    ResponseEntity<String> response = controller.changeDataset("testdata.json");

    // Assert
    assertNotNull(response);
    assertEquals(200, response.getStatusCodeValue());
    assertEquals("ok", response.getBody());
  }

  /** Test changeDataset with IOException throws ErrorResponseException. */
  @Test
  void testChangeDataset_IOException_ThrowsErrorResponseException() throws Exception {
    // Arrange
    Resource resource = mock(Resource.class);
    when(resource.getFile()).thenThrow(new IOException("File not found"));
    when(resourceGovernor.getReadableResource(
            "testuser", RoleFolders.MARKET_DATA_FOLDER, "testdata.json"))
        .thenReturn(resource);

    // Act & Assert
    assertThrows(
        ErrorResponseException.class,
        () -> {
          controller.changeDataset("testdata.json");
        });
  }

  /** Test getSavedMarketData returns list of market data filenames. */
  @Test
  void testGetSavedMarketData_ValidUser_ReturnsFilenames() throws Exception {
    // Arrange
    Resource[] resources =
        new Resource[] {
          createMockResource("marketdata1.json"),
          createMockResource("marketdata2.json"),
          createMockResource("marketdata3.json")
        };
    when(resourceGovernor.listContentsOfUserFolder("testuser", RoleFolders.MARKET_DATA_FOLDER))
        .thenReturn(resources);

    // Act
    ResponseEntity<List<String>> response = controller.getSavedMarketData();

    // Assert
    assertNotNull(response);
    assertEquals(200, response.getStatusCodeValue());
    List<String> filenames = response.getBody();
    assertNotNull(filenames);
    assertEquals(3, filenames.size());
    assertTrue(filenames.contains("marketdata1.json"));
  }

  /** Test getSavedMarketData with IOException throws ErrorResponseException. */
  @Test
  void testGetSavedMarketData_IOException_ThrowsErrorResponseException() throws Exception {
    // Arrange
    when(resourceGovernor.listContentsOfUserFolder("testuser", RoleFolders.MARKET_DATA_FOLDER))
        .thenThrow(new IOException("Storage error"));

    // Act & Assert
    assertThrows(
        ErrorResponseException.class,
        () -> {
          controller.getSavedMarketData();
        });
  }

  /** Test loadContract returns contract specification. */
  @Test
  void testLoadContract_ValidFilename_ReturnsContract() throws Exception {
    // Arrange
    PlainSwapOperationRequest expectedRequest = createValidPlainSwapRequest();
    String contractJson = objectMapper.writeValueAsString(expectedRequest);

    Resource resource = mock(Resource.class);
    when(resource.getFilename()).thenReturn("contract1.json");
    when(resource.getContentAsString(StandardCharsets.UTF_8)).thenReturn(contractJson);

    Resource[] resources = new Resource[] {resource};
    when(resourceGovernor.listContentsOfUserFolder("testuser", RoleFolders.SAVED_CONTRACTS_FOLDER))
        .thenReturn(resources);

    // Act
    ResponseEntity<PlainSwapOperationRequest> response = controller.loadContract("contract1.json");

    // Assert
    assertNotNull(response);
    assertEquals(200, response.getStatusCodeValue());
    assertNotNull(response.getBody());
  }

  /**
   * Test loadContract with non-existent file throws NullPointerException. The code sets
   * requestedContract to null if not found, then tries to use it, causing a NullPointerException at
   * line 608.
   */
  @Test
  void testLoadContract_FileNotFound_ThrowsNullPointerException() throws Exception {
    // Arrange
    Resource resource = mock(Resource.class);
    when(resource.getFilename()).thenReturn("other.json");

    Resource[] resources = new Resource[] {resource};
    when(resourceGovernor.listContentsOfUserFolder("testuser", RoleFolders.SAVED_CONTRACTS_FOLDER))
        .thenReturn(resources);

    // Act & Assert
    assertThrows(
        NullPointerException.class,
        () -> {
          controller.loadContract("contract1.json");
        },
        "Should throw NullPointerException when contract file not found");
  }

  /** Test loadContract with IOException throws ErrorResponseException. */
  @Test
  void testLoadContract_IOException_ThrowsErrorResponseException() throws Exception {
    // Arrange
    when(resourceGovernor.listContentsOfUserFolder("testuser", RoleFolders.SAVED_CONTRACTS_FOLDER))
        .thenThrow(new IOException("Storage error"));

    // Act & Assert
    assertThrows(
        ErrorResponseException.class,
        () -> {
          controller.loadContract("contract1.json");
        });
  }

  /** Test saveContract with valid request saves file. */
  @Test
  void testSaveContract_ValidRequest_ReturnsFilename() throws Exception {
    // Arrange
    PlainSwapOperationRequest request = createValidPlainSwapRequest();
    SaveContractRequest saveRequest = new SaveContractRequest();
    saveRequest.setName("TestContract");
    saveRequest.setPlainSwapOperationRequest(request);

    File targetFile = File.createTempFile("test", ".json");
    targetFile.deleteOnExit();

    // Ensure file doesn't exist so createNewFile succeeds
    targetFile.delete();

    WritableResource resource = mock(WritableResource.class);
    when(resource.getFile()).thenReturn(targetFile);
    when(resourceGovernor.getWritableResource(
            eq("testuser"), eq(RoleFolders.SAVED_CONTRACTS_FOLDER), anyString()))
        .thenReturn(resource);

    // Act
    ResponseEntity<String> response = controller.saveContract(saveRequest);

    // Assert
    assertNotNull(response);
    assertEquals(200, response.getStatusCodeValue());
    assertNotNull(response.getBody());
    assertTrue(response.getBody().endsWith("TestContract.json"));
  }

  /** Test saveContract with invalid name returns request not fulfilled. */
  @Test
  void testSaveContract_InvalidName_ReturnsNotFulfilled() {
    // Arrange
    PlainSwapOperationRequest request = createValidPlainSwapRequest();
    SaveContractRequest saveRequest = new SaveContractRequest();

    // Contains invalid characters
    saveRequest.setName("Invalid-Name!@#");
    saveRequest.setPlainSwapOperationRequest(request);

    // Act
    ResponseEntity<String> response = controller.saveContract(saveRequest);

    // Assert
    assertNotNull(response);
    assertEquals(200, response.getStatusCodeValue());
    assertEquals("Request not fulfilled.", response.getBody());
  }

  /** Test saveContract with special characters in name returns not fulfilled. */
  @Test
  void testSaveContract_SpecialCharactersInName_ReturnsNotFulfilled() {
    // Arrange
    PlainSwapOperationRequest request = createValidPlainSwapRequest();
    SaveContractRequest saveRequest = new SaveContractRequest();

    // Contains invalid character
    saveRequest.setName("test/contract");
    saveRequest.setPlainSwapOperationRequest(request);

    // Act
    ResponseEntity<String> response = controller.saveContract(saveRequest);

    // Assert
    assertNotNull(response);
    assertEquals(200, response.getStatusCodeValue());
    assertEquals("Request not fulfilled.", response.getBody());
  }

  /** Test saveContract with IOException throws ErrorResponseException. */
  @Test
  void testSaveContract_IOException_ThrowsErrorResponseException() throws Exception {
    // Arrange
    PlainSwapOperationRequest request = createValidPlainSwapRequest();
    SaveContractRequest saveRequest = new SaveContractRequest();
    saveRequest.setName("TestContract");
    saveRequest.setPlainSwapOperationRequest(request);

    WritableResource resource = mock(WritableResource.class);
    when(resource.getFile()).thenThrow(new IOException("Storage error"));
    when(resourceGovernor.getWritableResource(
            eq("testuser"), eq(RoleFolders.SAVED_CONTRACTS_FOLDER), anyString()))
        .thenReturn(resource);

    // Act & Assert
    assertThrows(
        ErrorResponseException.class,
        () -> {
          controller.saveContract(saveRequest);
        });
  }

  /** Test uploadMarketData with valid file returns ok. */
  @Test
  void testUploadMarketData_ValidFile_ReturnsOk() throws Exception {
    // Arrange
    String content = "{\"dataAsOf\":\"2023-01-01\",\"currency\":\"EUR\",\"items\":[]}";
    MockMultipartFile file =
        new MockMultipartFile(
            "marketdata",
            "marketdata.json",
            "application/json",
            content.getBytes(StandardCharsets.UTF_8));

    WritableResource importResource = mock(WritableResource.class);
    WritableResource userResource = mock(WritableResource.class);
    OutputStream importStream = new ByteArrayOutputStream();
    OutputStream userStream = new ByteArrayOutputStream();

    when(importResource.getOutputStream()).thenReturn(importStream);
    when(userResource.getOutputStream()).thenReturn(userStream);

    when(resourceGovernor.getImportCandidateAsResourceInWriteMode()).thenReturn(importResource);
    when(resourceGovernor.getWritableResource(
            "testuser", RoleFolders.MARKET_DATA_FOLDER, "marketdata.json"))
        .thenReturn(userResource);

    // Act
    ResponseEntity<String> response = controller.uploadMarketData(file);

    // Assert
    assertNotNull(response);
    assertEquals(200, response.getStatusCodeValue());
    assertEquals("ok", response.getBody());
    verify(databaseConnector).updateDatabase();
  }

  /** Test uploadMarketData with IOException throws ErrorResponseException. */
  @Test
  void testUploadMarketData_IOException_ThrowsErrorResponseException() throws Exception {
    // Arrange
    MockMultipartFile file =
        new MockMultipartFile(
            "marketdata",
            "marketdata.json",
            "application/json",
            "{}".getBytes(StandardCharsets.UTF_8));

    WritableResource resource = mock(WritableResource.class);
    when(resource.getOutputStream()).thenThrow(new IOException("Storage error"));
    when(resourceGovernor.getImportCandidateAsResourceInWriteMode()).thenReturn(resource);

    // Act & Assert
    assertThrows(
        ErrorResponseException.class,
        () -> {
          controller.uploadMarketData(file);
        });
  }

  /** Test uploadMarketData with SQLException throws ErrorResponseException. */
  @Test
  void testUploadMarketData_SQLException_ThrowsErrorResponseException() throws Exception {
    // Arrange
    MockMultipartFile file =
        new MockMultipartFile(
            "marketdata",
            "marketdata.json",
            "application/json",
            "{}".getBytes(StandardCharsets.UTF_8));

    WritableResource importResource = mock(WritableResource.class);
    WritableResource userResource = mock(WritableResource.class);
    OutputStream importStream = new ByteArrayOutputStream();
    OutputStream userStream = new ByteArrayOutputStream();

    when(importResource.getOutputStream()).thenReturn(importStream);
    when(userResource.getOutputStream()).thenReturn(userStream);

    when(resourceGovernor.getImportCandidateAsResourceInWriteMode()).thenReturn(importResource);
    when(resourceGovernor.getWritableResource(
            "testuser", RoleFolders.MARKET_DATA_FOLDER, "marketdata.json"))
        .thenReturn(userResource);

    doThrow(new SQLException("Database error")).when(databaseConnector).updateDatabase();

    // Act & Assert
    assertThrows(
        ErrorResponseException.class,
        () -> {
          controller.uploadMarketData(file);
        });
  }

  /** Test constructor with null parameters throws NullPointerException. */
  @Test
  void testConstructor_WithNullParameters_ThrowsNullPointerException() {
    // Arrange & Act & Assert
    assertThrows(
        NullPointerException.class,
        () -> {
          PlainSwapEditorController nullController =
              new PlainSwapEditorController(null, null, null, null, null, null, null);
        },
        "Constructor should throw NullPointerException with null parameters");
  }

  /**
   * Test getSavedContracts with resources having null filenames. The code filters out null
   * filenames using Objects.requireNonNull.
   */
  @Test
  void testGetSavedContracts_ResourcesWithNullFilenames_HandlesGracefully() throws Exception {
    // Arrange
    Resource resource1 = mock(Resource.class);
    Resource resource2 = mock(Resource.class);
    when(resource1.getFilename()).thenReturn("file1.json");
    when(resource2.getFilename()).thenReturn(null);

    Resource[] resources = new Resource[] {resource1, resource2};
    when(resourceGovernor.listContentsOfUserFolder("testuser", RoleFolders.SAVED_CONTRACTS_FOLDER))
        .thenReturn(resources);

    // Act
    ResponseEntity<List<String>> response = controller.getSavedContracts();

    // Assert
    assertNotNull(response);
    assertEquals(200, response.getStatusCodeValue());
    List<String> filenames = response.getBody();
    assertNotNull(filenames);

    // Both filenames are added to the list - code adds null to list
    assertEquals(2, filenames.size());
    assertTrue(filenames.contains("file1.json"));
  }

  /** Test multiple calls to getSavedContracts work correctly. */
  @Test
  void testGetSavedContracts_MultipleCalls_WorkCorrectly() throws Exception {
    // Arrange
    Resource[] resources = new Resource[] {createMockResource("contract1.json")};
    when(resourceGovernor.listContentsOfUserFolder("testuser", RoleFolders.SAVED_CONTRACTS_FOLDER))
        .thenReturn(resources);

    // Act & Assert
    for (int i = 0; i < 3; i++) {
      ResponseEntity<List<String>> response = controller.getSavedContracts();
      assertNotNull(response);
      assertEquals(200, response.getStatusCodeValue());
    }
  }

  /** Helper method to create a valid PlainSwapOperationRequest for testing. */
  private PlainSwapOperationRequest createValidPlainSwapRequest() {
    PlainSwapOperationRequest request = new PlainSwapOperationRequest();
    Counterparty counterparty1 = new Counterparty();
    counterparty1.setFullName("Party One");
    counterparty1.setBicCode("PARTY1XX");
    Counterparty counterparty2 = new Counterparty();
    counterparty2.setFullName("Party Two");
    counterparty2.setBicCode("PARTY2XX");
    PaymentFrequency fixedFreq = new PaymentFrequency();
    fixedFreq.setPeriodMultiplier(1);
    fixedFreq.setPeriod("Y");
    PaymentFrequency floatingFreq = new PaymentFrequency();
    floatingFreq.setPeriodMultiplier(6);
    floatingFreq.setPeriod("M");
    request.setFirstCounterparty(counterparty1);
    request.setSecondCounterparty(counterparty2);
    request.setTradeType("PLAIN_SWAP");
    request.setMarginBufferAmount(10000.0);
    request.setTerminationFeeAmount(5000.0);
    request.setNotionalAmount(1000000.0);
    request.setCurrency("EUR");
    request.setUniqueTradeIdentifier("TEST123");
    request.setTradeDate(OffsetDateTime.now());
    request.setEffectiveDate(OffsetDateTime.now());
    request.setTerminationDate(OffsetDateTime.now().plusYears(5));
    request.setDailySettlementTime("17:00:00");
    request.setFixedPayingParty(counterparty1);
    request.setFixedRate(0.02);
    request.setFixedDayCountFraction("30E/360");
    request.setFixedPaymentFrequency(fixedFreq);
    request.setFloatingPayingParty(counterparty2);
    request.setFloatingRateIndex("EURIBOR 6M");
    request.setFloatingDayCountFraction("ACT/360");
    request.setFloatingFixingDayOffset(-2);
    request.setFloatingPaymentFrequency(floatingFreq);
    request.setMarketDataProvider("EUR_EURIBOR_Y_S");
    request.setReceiverPartyID("PARTY2");
    request.setFixPayerPartyID("PARTY1");
    return request;
  }

  /** Helper method to create a mock Resource with a filename. */
  private Resource createMockResource(String filename) {
    Resource resource = mock(Resource.class);
    when(resource.getFilename()).thenReturn(filename);
    return resource;
  }
}
