package net.finmath.smartcontract.valuation.service.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;
import net.finmath.smartcontract.model.ExceptionId;
import net.finmath.smartcontract.model.MarginRequest;
import net.finmath.smartcontract.model.SDCException;
import net.finmath.smartcontract.model.ValueRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {ValuationController.class})
@ExtendWith(SpringExtension.class)
class ValuationControllerDiffblueTest {
  @Autowired private ValuationController valuationController;

  /**
   * Test {@link ValuationController#margin(MarginRequest)}.
   *
   * <ul>
   *   <li>Given {@code Market Data End}.
   *   <li>Then calls {@link MarginRequest#getMarketDataEnd()}.
   * </ul>
   *
   * <p>Method under test: {@link ValuationController#margin(MarginRequest)}
   */
  @Test
  @DisplayName("Test margin(MarginRequest); given 'Market Data End'; then calls getMarketDataEnd()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.margin(MarginRequest)"})
  void testMargin_givenMarketDataEnd_thenCallsGetMarketDataEnd() {
    // Arrange
    MarginRequest marginRequest = mock(MarginRequest.class);
    when(marginRequest.getMarketDataEnd()).thenReturn("Market Data End");
    when(marginRequest.getMarketDataStart()).thenReturn("Market Data Start");
    when(marginRequest.getTradeData()).thenReturn("Trade Data");
    when(marginRequest.marketDataStart(Mockito.<String>any())).thenReturn(new MarginRequest());
    marginRequest.marketDataStart("Market Data Start");

    // Act and Assert
    assertThrows(SDCException.class, () -> valuationController.margin(marginRequest));
    verify(marginRequest).getMarketDataEnd();
    verify(marginRequest).getMarketDataStart();
    verify(marginRequest).getTradeData();
    verify(marginRequest).marketDataStart("Market Data Start");
  }

  /**
   * Test {@link ValuationController#margin(MarginRequest)}.
   *
   * <ul>
   *   <li>Given {@code Responded}.
   *   <li>When {@link MarginRequest} (default constructor) marketDataStart {@code Responded}.
   * </ul>
   *
   * <p>Method under test: {@link ValuationController#margin(MarginRequest)}
   */
  @Test
  @DisplayName(
      "Test margin(MarginRequest); given 'Responded'; when MarginRequest (default constructor) marketDataStart 'Responded'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.margin(MarginRequest)"})
  void testMargin_givenResponded_whenMarginRequestMarketDataStartResponded() {
    // Arrange
    MarginRequest marginRequest = new MarginRequest();
    marginRequest.marketDataStart("Responded");

    // Act and Assert
    assertThrows(SDCException.class, () -> valuationController.margin(marginRequest));
  }

  /**
   * Test {@link ValuationController#margin(MarginRequest)}.
   *
   * <ul>
   *   <li>Given {@code Responded}.
   *   <li>When {@link MarginRequest} (default constructor) tradeData {@code Responded}.
   * </ul>
   *
   * <p>Method under test: {@link ValuationController#margin(MarginRequest)}
   */
  @Test
  @DisplayName(
      "Test margin(MarginRequest); given 'Responded'; when MarginRequest (default constructor) tradeData 'Responded'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.margin(MarginRequest)"})
  void testMargin_givenResponded_whenMarginRequestTradeDataResponded() {
    // Arrange
    MarginRequest marginRequest = new MarginRequest();
    marginRequest.tradeData("Responded");

    // Act and Assert
    assertThrows(SDCException.class, () -> valuationController.margin(marginRequest));
  }

  /**
   * Test {@link ValuationController#margin(MarginRequest)}.
   *
   * <ul>
   *   <li>When {@link MarginRequest} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ValuationController#margin(MarginRequest)}
   */
  @Test
  @DisplayName("Test margin(MarginRequest); when MarginRequest (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.margin(MarginRequest)"})
  void testMargin_whenMarginRequest() {
    // Arrange, Act and Assert
    assertThrows(SDCException.class, () -> valuationController.margin(new MarginRequest()));
  }

  /**
   * Test {@link ValuationController#value(ValueRequest)}.
   *
   * <ul>
   *   <li>Given {@code Responded}.
   *   <li>When {@link ValueRequest} (default constructor) tradeData {@code Responded}.
   * </ul>
   *
   * <p>Method under test: {@link ValuationController#value(ValueRequest)}
   */
  @Test
  @DisplayName(
      "Test value(ValueRequest); given 'Responded'; when ValueRequest (default constructor) tradeData 'Responded'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.value(ValueRequest)"})
  void testValue_givenResponded_whenValueRequestTradeDataResponded() {
    // Arrange
    ValueRequest valueRequest = new ValueRequest();
    valueRequest.tradeData("Responded");

    // Act and Assert
    assertThrows(SDCException.class, () -> valuationController.value(valueRequest));
  }

  /**
   * Test {@link ValuationController#value(ValueRequest)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ValuationController#value(ValueRequest)}
   */
  @Test
  @DisplayName("Test value(ValueRequest); when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.value(ValueRequest)"})
  void testValue_whenNull() {
    // Arrange, Act and Assert
    assertThrows(SDCException.class, () -> valuationController.value(null));
  }

  /**
   * Test {@link ValuationController#value(ValueRequest)}.
   *
   * <ul>
   *   <li>When {@link ValueRequest} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ValuationController#value(ValueRequest)}
   */
  @Test
  @DisplayName("Test value(ValueRequest); when ValueRequest (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.value(ValueRequest)"})
  void testValue_whenValueRequest() {
    // Arrange, Act and Assert
    assertThrows(SDCException.class, () -> valuationController.value(new ValueRequest()));
  }

  /**
   * Test {@link ValuationController#valueAtTime(ValueRequest)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ValuationController#valueAtTime(ValueRequest)}
   */
  @Test
  @DisplayName("Test valueAtTime(ValueRequest); when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.valueAtTime(ValueRequest)"})
  void testValueAtTime_whenNull() {
    // Arrange, Act and Assert
    assertThrows(SDCException.class, () -> valuationController.valueAtTime(null));
  }

  /**
   * Test {@link ValuationController#valueAtTime(ValueRequest)}.
   *
   * <ul>
   *   <li>When {@link ValueRequest} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ValuationController#valueAtTime(ValueRequest)}
   */
  @Test
  @DisplayName("Test valueAtTime(ValueRequest); when ValueRequest (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.valueAtTime(ValueRequest)"})
  void testValueAtTime_whenValueRequest() {
    // Arrange, Act and Assert
    assertThrows(SDCException.class, () -> valuationController.valueAtTime(new ValueRequest()));
  }

  /**
   * Test {@link ValuationController#testProductValue(MultipartFile)}.
   *
   * <ul>
   *   <li>Then calls {@link DataInputStream#readAllBytes()}.
   * </ul>
   *
   * <p>Method under test: {@link ValuationController#testProductValue(MultipartFile)}
   */
  @Test
  @DisplayName("Test testProductValue(MultipartFile); then calls readAllBytes()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.testProductValue(MultipartFile)"})
  void testTestProductValue_thenCallsReadAllBytes() throws IOException {
    // Arrange
    DataInputStream dataInputStream = mock(DataInputStream.class);
    when(dataInputStream.readAllBytes())
        .thenThrow(new SDCException(ExceptionId.SDC_AUTH_ERROR, "An error occurred"));
    MultipartFile tradeData = mock(MultipartFile.class);
    when(tradeData.getInputStream()).thenReturn(dataInputStream);

    // Act and Assert
    assertThrows(SDCException.class, () -> valuationController.testProductValue(tradeData));
    verify(dataInputStream).readAllBytes();
    verify(tradeData).getInputStream();
  }

  /**
   * Test {@link ValuationController#testProductValue(MultipartFile)}.
   *
   * <ul>
   *   <li>When {@link ByteArrayInputStream#ByteArrayInputStream(byte[])} with {@code AXAXAXAX}
   *       Bytes is {@code UTF-8}.
   * </ul>
   *
   * <p>Method under test: {@link ValuationController#testProductValue(MultipartFile)}
   */
  @Test
  @DisplayName(
      "Test testProductValue(MultipartFile); when ByteArrayInputStream(byte[]) with 'AXAXAXAX' Bytes is 'UTF-8'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.testProductValue(MultipartFile)"})
  void testTestProductValue_whenByteArrayInputStreamWithAxaxaxaxBytesIsUtf8() throws IOException {
    // Arrange, Act and Assert
    assertThrows(
        SDCException.class,
        () ->
            valuationController.testProductValue(
                new MockMultipartFile(
                    "Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")))));
  }

  /**
   * Test {@link ValuationController#testProductValue(MultipartFile)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ValuationController#testProductValue(MultipartFile)}
   */
  @Test
  @DisplayName("Test testProductValue(MultipartFile); when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.testProductValue(MultipartFile)"})
  void testTestProductValue_whenNull() {
    // Arrange, Act and Assert
    assertThrows(SDCException.class, () -> valuationController.testProductValue(null));
  }

  /**
   * Test {@link ValuationController#test()}.
   *
   * <p>Method under test: {@link ValuationController#test()}
   */
  @Test
  @DisplayName("Test test()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.test()"})
  void testTest() {
    // Arrange and Act
    ResponseEntity<String> actualTestResult = valuationController.test();

    // Assert
    HttpStatusCode statusCode = actualTestResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals("Connect successful", actualTestResult.getBody());
    HttpHeaders headers = actualTestResult.getHeaders();
    assertEquals(2, headers.size());
    List<String> getResult = headers.get(HttpHeaders.CONTENT_TYPE);
    assertEquals(1, getResult.size());
    assertEquals("application/json", getResult.get(0));
    List<String> getResult2 = headers.get("Responded");
    assertEquals(1, getResult2.size());
    assertEquals("test", getResult2.get(0));
    assertEquals(200, actualTestResult.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(actualTestResult.hasBody());
  }
}
