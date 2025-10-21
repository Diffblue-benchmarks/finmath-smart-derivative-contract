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
   *   <li>Given {@code "2022-12-31T23:59:59Z"}.
   *   <li>Then calls {@link MarginRequest#getMarketDataEnd()}.
   * </ul>
   *
   * <p>Method under test: {@link ValuationController#margin(MarginRequest)}
   */
  @Test
  @DisplayName(
      "Test margin(MarginRequest); given '\"2022-12-31T23:59:59Z\"'; then calls getMarketDataEnd()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.margin(MarginRequest)"})
  void testMargin_given20221231t235959z_thenCallsGetMarketDataEnd() {
    // Arrange
    MarginRequest marginRequest = mock(MarginRequest.class);
    when(marginRequest.getMarketDataEnd()).thenReturn("\"2022-12-31T23:59:59Z\"");
    when(marginRequest.getMarketDataStart()).thenReturn("\"2022-01-01T00:00:00Z\"");
    when(marginRequest.getTradeData())
        .thenReturn(
            "\"tradeId:12345,tradeDate:2022-01-01,tradeType:BUY,tradeQuantity:100,tradePrice:50,tradeCounterparty:XYZ"
                + " Corp,tradeAsset:Apple Inc.,tradeCurrency:USD\"");
    when(marginRequest.marketDataStart(Mockito.<String>any())).thenReturn(new MarginRequest());
    marginRequest.marketDataStart("\"2022-01-01T00:00:00Z\"");

    // Act and Assert
    assertThrows(SDCException.class, () -> valuationController.margin(marginRequest));
    verify(marginRequest).getMarketDataEnd();
    verify(marginRequest).getMarketDataStart();
    verify(marginRequest).getTradeData();
    verify(marginRequest).marketDataStart("\"2022-01-01T00:00:00Z\"");
  }

  /**
   * Test {@link ValuationController#margin(MarginRequest)}.
   *
   * <ul>
   *   <li>Given a string.
   *   <li>When {@link MarginRequest} (default constructor) tradeData a string.
   * </ul>
   *
   * <p>Method under test: {@link ValuationController#margin(MarginRequest)}
   */
  @Test
  @DisplayName(
      "Test margin(MarginRequest); given a string; when MarginRequest (default constructor) tradeData a string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.margin(MarginRequest)"})
  void testMargin_givenAString_whenMarginRequestTradeDataAString() {
    // Arrange
    MarginRequest marginRequest = new MarginRequest();
    marginRequest.tradeData(
        "\"{\\\"tradeId\\\":\\\"T12345\\\",\\\"tradeType\\\":\\\"Futures\\\",\\\"tradeDate\\\":\\\"2022-01-01\\\",\\\"quantity\\\":100,\\"
            + "\"price\\\":1500,\\\"marginRate\\\":0.1,\\\"currency\\\":\\\"USD\\\"}\"");

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
   * Test {@link ValuationController#margin(MarginRequest)}.
   *
   * <ul>
   *   <li>When {@link MarginRequest} (default constructor) marketDataStart {@code
   *       "2022-01-01T00:00:00Z"}.
   * </ul>
   *
   * <p>Method under test: {@link ValuationController#margin(MarginRequest)}
   */
  @Test
  @DisplayName(
      "Test margin(MarginRequest); when MarginRequest (default constructor) marketDataStart '\"2022-01-01T00:00:00Z\"'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.margin(MarginRequest)"})
  void testMargin_whenMarginRequestMarketDataStart20220101t000000z() {
    // Arrange
    MarginRequest marginRequest = new MarginRequest();
    marginRequest.marketDataStart("\"2022-01-01T00:00:00Z\"");

    // Act and Assert
    assertThrows(SDCException.class, () -> valuationController.margin(marginRequest));
  }

  /**
   * Test {@link ValuationController#value(ValueRequest)}.
   *
   * <ul>
   *   <li>Given {@code "BUY,100,GOOG,2025-10-21,1500.00,USD"}.
   * </ul>
   *
   * <p>Method under test: {@link ValuationController#value(ValueRequest)}
   */
  @Test
  @DisplayName("Test value(ValueRequest); given '\"BUY,100,GOOG,2025-10-21,1500.00,USD\"'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.value(ValueRequest)"})
  void testValue_givenBuy100Goog20251021150000Usd() {
    // Arrange
    ValueRequest valueRequest = new ValueRequest();
    valueRequest.tradeData("\"BUY,100,GOOG,2025-10-21,1500.00,USD\"");

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
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ValuationController valuationController = new ValuationController();

    DataInputStream dataInputStream = mock(DataInputStream.class);
    when(dataInputStream.readAllBytes())
        .thenThrow(
            new SDCException(
                ExceptionId.SDC_AUTH_ERROR,
                "\"Invalid smart contract detected: Contract ID #12345 does not comply with the standard financial model."
                    + " Please review and correct the contract details.\""));

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
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ValuationController valuationController = new ValuationController();
    MockMultipartFile tradeData =
        new MockMultipartFile(
            "\"testFile.txt\"", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

    // Act and Assert
    assertThrows(SDCException.class, () -> valuationController.testProductValue(tradeData));
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
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange, Act and Assert
    assertThrows(SDCException.class, () -> new ValuationController().testProductValue(null));
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
