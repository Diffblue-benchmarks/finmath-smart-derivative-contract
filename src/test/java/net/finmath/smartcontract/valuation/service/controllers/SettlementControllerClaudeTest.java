/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */
package net.finmath.smartcontract.valuation.service.controllers;

import net.finmath.smartcontract.model.InitialSettlementRequest;
import net.finmath.smartcontract.model.InitialSettlementResult;
import net.finmath.smartcontract.model.RegularSettlementRequest;
import net.finmath.smartcontract.model.RegularSettlementResult;
import net.finmath.smartcontract.model.SDCException;
import net.finmath.smartcontract.valuation.service.utils.SettlementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for SettlementController. Tests all methods with focus on branch and condition
 * coverage.
 *
 * Note: We use mocking here because the SettlementService has complex dependencies that require
 * database connections, market data providers, and extensive setup. Testing without mocking would
 * require a full integration test environment.
 *
 * @author Claude Code
 */
class SettlementControllerClaudeTest {

  private SettlementController controller;
  private SettlementService settlementService;

  /**
   * Set up test fixtures before each test.
   */
  @BeforeEach
  void setUp() {
    settlementService = mock(SettlementService.class);
    controller = new SettlementController(settlementService);
  }

  /**
   * Test constructor creates a valid instance.
   */
  @Test
  void testConstructor_CreatesValidInstance() {
    SettlementController newController = new SettlementController(settlementService);
    assertNotNull(newController, "Constructor should create a non-null instance");
  }

  /**
   * Test constructor with null service parameter.
   * The controller will store null but will fail when methods are called.
   */
  @Test
  void testConstructor_WithNullService_CreatesInstance() {
    SettlementController newController = new SettlementController(null);
    assertNotNull(newController, "Constructor should create instance even with null service");
  }

  /**
   * Test generateRegularSettlementResult with valid request returns successful response.
   */
  @Test
  void testGenerateRegularSettlementResult_ValidRequest_ReturnsOk() {
    // Arrange
    RegularSettlementRequest request = new RegularSettlementRequest();
    request.setSettlementLast("<settlement></settlement>");
    request.setTradeData("<trade></trade>");

    RegularSettlementResult expectedResult = new RegularSettlementResult();
    expectedResult.setGeneratedRegularSettlement("<settlement></settlement>");
    expectedResult.setCurrency("EUR");
    expectedResult.setMarginValue(BigDecimal.valueOf(100.0));
    expectedResult.setValuationDate("20250114-120000");

    when(settlementService.generateRegularSettlementResult(any(RegularSettlementRequest.class)))
        .thenReturn(expectedResult);

    // Act
    ResponseEntity<RegularSettlementResult> response =
        controller.generateRegularSettlementResult(request);

    // Assert
    assertNotNull(response, "Response should not be null");
    assertEquals(200, response.getStatusCodeValue(), "Status code should be 200");
    assertNotNull(response.getBody(), "Response body should not be null");
    assertEquals(expectedResult, response.getBody(), "Response body should match expected result");
    verify(settlementService, times(1)).generateRegularSettlementResult(request);
  }

  /**
   * Test generateRegularSettlementResult with request containing new provided market data.
   */
  @Test
  void testGenerateRegularSettlementResult_WithProvidedMarketData_ReturnsOk() {
    // Arrange
    RegularSettlementRequest request = new RegularSettlementRequest();
    request.setSettlementLast("<settlement></settlement>");
    request.setTradeData("<trade></trade>");
    request.setNewProvidedMarketData("<marketDataList></marketDataList>");

    RegularSettlementResult expectedResult = new RegularSettlementResult();
    expectedResult.setGeneratedRegularSettlement("<settlement></settlement>");
    expectedResult.setCurrency("USD");
    expectedResult.setMarginValue(BigDecimal.valueOf(200.0));
    expectedResult.setValuationDate("20250114-120000");

    when(settlementService.generateRegularSettlementResult(any(RegularSettlementRequest.class)))
        .thenReturn(expectedResult);

    // Act
    ResponseEntity<RegularSettlementResult> response =
        controller.generateRegularSettlementResult(request);

    // Assert
    assertNotNull(response, "Response should not be null");
    assertEquals(200, response.getStatusCodeValue(), "Status code should be 200");
    assertNotNull(response.getBody(), "Response body should not be null");
    assertEquals("USD", response.getBody().getCurrency(), "Currency should match");
    verify(settlementService, times(1)).generateRegularSettlementResult(request);
  }

  /**
   * Test generateRegularSettlementResult when service throws SDCException.
   */
  @Test
  void testGenerateRegularSettlementResult_ServiceThrowsSDCException_PropagatesException() {
    // Arrange
    RegularSettlementRequest request = new RegularSettlementRequest();
    request.setSettlementLast("<settlement></settlement>");
    request.setTradeData("<trade></trade>");

    when(settlementService.generateRegularSettlementResult(any(RegularSettlementRequest.class)))
        .thenThrow(new SDCException(
            net.finmath.smartcontract.model.ExceptionId.SDC_XML_PARSE_ERROR,
            "Invalid XML",
            400));

    // Act & Assert
    assertThrows(
        SDCException.class,
        () -> controller.generateRegularSettlementResult(request),
        "Should propagate SDCException from service");
    verify(settlementService, times(1)).generateRegularSettlementResult(request);
  }

  /**
   * Test generateRegularSettlementResult when service throws generic exception.
   */
  @Test
  void testGenerateRegularSettlementResult_ServiceThrowsException_PropagatesException() {
    // Arrange
    RegularSettlementRequest request = new RegularSettlementRequest();
    request.setSettlementLast("<settlement></settlement>");
    request.setTradeData("<trade></trade>");

    when(settlementService.generateRegularSettlementResult(any(RegularSettlementRequest.class)))
        .thenThrow(new RuntimeException("Unexpected error"));

    // Act & Assert
    assertThrows(
        RuntimeException.class,
        () -> controller.generateRegularSettlementResult(request),
        "Should propagate RuntimeException from service");
    verify(settlementService, times(1)).generateRegularSettlementResult(request);
  }

  /**
   * Test generateRegularSettlementResult with null request.
   * The service will likely fail, but the controller passes it through.
   */
  @Test
  void testGenerateRegularSettlementResult_NullRequest_PassesToService() {
    // Arrange
    when(settlementService.generateRegularSettlementResult(null))
        .thenThrow(new NullPointerException("Request cannot be null"));

    // Act & Assert
    assertThrows(
        NullPointerException.class,
        () -> controller.generateRegularSettlementResult(null),
        "Should propagate NullPointerException for null request");
    verify(settlementService, times(1)).generateRegularSettlementResult(null);
  }

  /**
   * Test generateInitialSettlementResult with valid request returns successful response.
   */
  @Test
  void testGenerateInitialSettlementResult_ValidRequest_ReturnsOk() {
    // Arrange
    InitialSettlementRequest request = new InitialSettlementRequest();
    request.setTradeData("<trade></trade>");

    InitialSettlementResult expectedResult = new InitialSettlementResult();
    expectedResult.setGeneratedInitialSettlement("<settlement></settlement>");
    expectedResult.setCurrency("EUR");
    expectedResult.setMarginValue(BigDecimal.ZERO);
    expectedResult.setValuationDate("20250114-120000");

    when(settlementService.generateInitialSettlementResult(any(InitialSettlementRequest.class)))
        .thenReturn(expectedResult);

    // Act
    ResponseEntity<InitialSettlementResult> response =
        controller.generateInitialSettlementResult(request);

    // Assert
    assertNotNull(response, "Response should not be null");
    assertEquals(200, response.getStatusCodeValue(), "Status code should be 200");
    assertNotNull(response.getBody(), "Response body should not be null");
    assertEquals(expectedResult, response.getBody(), "Response body should match expected result");
    verify(settlementService, times(1)).generateInitialSettlementResult(request);
  }

  /**
   * Test generateInitialSettlementResult with request containing new provided market data.
   */
  @Test
  void testGenerateInitialSettlementResult_WithProvidedMarketData_ReturnsOk() {
    // Arrange
    InitialSettlementRequest request = new InitialSettlementRequest();
    request.setTradeData("<trade></trade>");
    request.setNewProvidedMarketData("<marketDataList></marketDataList>");

    InitialSettlementResult expectedResult = new InitialSettlementResult();
    expectedResult.setGeneratedInitialSettlement("<settlement></settlement>");
    expectedResult.setCurrency("USD");
    expectedResult.setMarginValue(BigDecimal.ZERO);
    expectedResult.setValuationDate("20250114-120000");

    when(settlementService.generateInitialSettlementResult(any(InitialSettlementRequest.class)))
        .thenReturn(expectedResult);

    // Act
    ResponseEntity<InitialSettlementResult> response =
        controller.generateInitialSettlementResult(request);

    // Assert
    assertNotNull(response, "Response should not be null");
    assertEquals(200, response.getStatusCodeValue(), "Status code should be 200");
    assertNotNull(response.getBody(), "Response body should not be null");
    assertEquals("USD", response.getBody().getCurrency(), "Currency should match");
    verify(settlementService, times(1)).generateInitialSettlementResult(request);
  }

  /**
   * Test generateInitialSettlementResult when service throws SDCException.
   */
  @Test
  void testGenerateInitialSettlementResult_ServiceThrowsSDCException_PropagatesException() {
    // Arrange
    InitialSettlementRequest request = new InitialSettlementRequest();
    request.setTradeData("<trade></trade>");

    when(settlementService.generateInitialSettlementResult(any(InitialSettlementRequest.class)))
        .thenThrow(new SDCException(
            net.finmath.smartcontract.model.ExceptionId.SDC_XML_PARSE_ERROR,
            "Invalid XML",
            400));

    // Act & Assert
    assertThrows(
        SDCException.class,
        () -> controller.generateInitialSettlementResult(request),
        "Should propagate SDCException from service");
    verify(settlementService, times(1)).generateInitialSettlementResult(request);
  }

  /**
   * Test generateInitialSettlementResult when service throws generic exception.
   */
  @Test
  void testGenerateInitialSettlementResult_ServiceThrowsException_PropagatesException() {
    // Arrange
    InitialSettlementRequest request = new InitialSettlementRequest();
    request.setTradeData("<trade></trade>");

    when(settlementService.generateInitialSettlementResult(any(InitialSettlementRequest.class)))
        .thenThrow(new RuntimeException("Unexpected error"));

    // Act & Assert
    assertThrows(
        RuntimeException.class,
        () -> controller.generateInitialSettlementResult(request),
        "Should propagate RuntimeException from service");
    verify(settlementService, times(1)).generateInitialSettlementResult(request);
  }

  /**
   * Test generateInitialSettlementResult with null request.
   * The service will likely fail, but the controller passes it through.
   */
  @Test
  void testGenerateInitialSettlementResult_NullRequest_PassesToService() {
    // Arrange
    when(settlementService.generateInitialSettlementResult(null))
        .thenThrow(new NullPointerException("Request cannot be null"));

    // Act & Assert
    assertThrows(
        NullPointerException.class,
        () -> controller.generateInitialSettlementResult(null),
        "Should propagate NullPointerException for null request");
    verify(settlementService, times(1)).generateInitialSettlementResult(null);
  }

  /**
   * Test generateInitialSettlementResult returns zero margin value.
   * Initial settlements typically have zero margin.
   */
  @Test
  void testGenerateInitialSettlementResult_ReturnsZeroMargin() {
    // Arrange
    InitialSettlementRequest request = new InitialSettlementRequest();
    request.setTradeData("<trade></trade>");

    InitialSettlementResult expectedResult = new InitialSettlementResult();
    expectedResult.setGeneratedInitialSettlement("<settlement></settlement>");
    expectedResult.setCurrency("EUR");
    expectedResult.setMarginValue(BigDecimal.ZERO);
    expectedResult.setValuationDate("20250114-120000");

    when(settlementService.generateInitialSettlementResult(any(InitialSettlementRequest.class)))
        .thenReturn(expectedResult);

    // Act
    ResponseEntity<InitialSettlementResult> response =
        controller.generateInitialSettlementResult(request);

    // Assert
    assertNotNull(response, "Response should not be null");
    assertEquals(BigDecimal.ZERO, response.getBody().getMarginValue(),
        "Initial settlement should have zero margin");
  }

  /**
   * Test generateRegularSettlementResult with multiple calls to ensure statelessness.
   */
  @Test
  void testGenerateRegularSettlementResult_MultipleCalls_AreIndependent() {
    // Arrange
    RegularSettlementRequest request1 = new RegularSettlementRequest();
    request1.setSettlementLast("<settlement1></settlement1>");
    request1.setTradeData("<trade1></trade1>");

    RegularSettlementRequest request2 = new RegularSettlementRequest();
    request2.setSettlementLast("<settlement2></settlement2>");
    request2.setTradeData("<trade2></trade2>");

    RegularSettlementResult result1 = new RegularSettlementResult();
    result1.setMarginValue(BigDecimal.valueOf(100.0));

    RegularSettlementResult result2 = new RegularSettlementResult();
    result2.setMarginValue(BigDecimal.valueOf(200.0));

    when(settlementService.generateRegularSettlementResult(request1)).thenReturn(result1);
    when(settlementService.generateRegularSettlementResult(request2)).thenReturn(result2);

    // Act
    ResponseEntity<RegularSettlementResult> response1 =
        controller.generateRegularSettlementResult(request1);
    ResponseEntity<RegularSettlementResult> response2 =
        controller.generateRegularSettlementResult(request2);

    // Assert
    assertNotNull(response1, "First response should not be null");
    assertNotNull(response2, "Second response should not be null");
    assertEquals(BigDecimal.valueOf(100.0), response1.getBody().getMarginValue(),
        "First result should have correct margin");
    assertEquals(BigDecimal.valueOf(200.0), response2.getBody().getMarginValue(),
        "Second result should have correct margin");
    verify(settlementService, times(1)).generateRegularSettlementResult(request1);
    verify(settlementService, times(1)).generateRegularSettlementResult(request2);
  }

  /**
   * Test generateInitialSettlementResult with multiple calls to ensure statelessness.
   */
  @Test
  void testGenerateInitialSettlementResult_MultipleCalls_AreIndependent() {
    // Arrange
    InitialSettlementRequest request1 = new InitialSettlementRequest();
    request1.setTradeData("<trade1></trade1>");

    InitialSettlementRequest request2 = new InitialSettlementRequest();
    request2.setTradeData("<trade2></trade2>");

    InitialSettlementResult result1 = new InitialSettlementResult();
    result1.setCurrency("EUR");

    InitialSettlementResult result2 = new InitialSettlementResult();
    result2.setCurrency("USD");

    when(settlementService.generateInitialSettlementResult(request1)).thenReturn(result1);
    when(settlementService.generateInitialSettlementResult(request2)).thenReturn(result2);

    // Act
    ResponseEntity<InitialSettlementResult> response1 =
        controller.generateInitialSettlementResult(request1);
    ResponseEntity<InitialSettlementResult> response2 =
        controller.generateInitialSettlementResult(request2);

    // Assert
    assertNotNull(response1, "First response should not be null");
    assertNotNull(response2, "Second response should not be null");
    assertEquals("EUR", response1.getBody().getCurrency(), "First result should have EUR currency");
    assertEquals("USD", response2.getBody().getCurrency(), "Second result should have USD currency");
    verify(settlementService, times(1)).generateInitialSettlementResult(request1);
    verify(settlementService, times(1)).generateInitialSettlementResult(request2);
  }
}
