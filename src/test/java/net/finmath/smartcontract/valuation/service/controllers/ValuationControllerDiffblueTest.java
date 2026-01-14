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
import net.finmath.smartcontract.settlement.SettlementGeneratorDiffblueBase;
import net.finmath.smartcontract.valuation.implementation.MarginCalculatorDiffblueBase;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationParserDataItemsDiffblueBase;
import net.finmath.smartcontract.valuation.marketdata.data.LocalDateTimeAdapterDiffblueBase;
import net.finmath.smartcontract.valuation.marketdata.generators.MarketDataGeneratorLauncherDiffblueBase;
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
   * <p>Method under test: {@link ValuationController#margin(MarginRequest)}
   */
  @Test
  @DisplayName("Test margin(MarginRequest)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.margin(MarginRequest)"})
  void testMargin() {
    // Arrange
    MarginRequest marginRequest = new MarginRequest();
    marginRequest.marketDataStart(
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    // Act and Assert
    assertThrows(SDCException.class, () -> valuationController.margin(marginRequest));
  }

  /**
   * Test {@link ValuationController#margin(MarginRequest)}.
   *
   * <ul>
   *   <li>Given createAlternativeSmartDerivativeContractXml.
   * </ul>
   *
   * <p>Method under test: {@link ValuationController#margin(MarginRequest)}
   */
  @Test
  @DisplayName("Test margin(MarginRequest); given createAlternativeSmartDerivativeContractXml")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.margin(MarginRequest)"})
  void testMargin_givenCreateAlternativeSmartDerivativeContractXml() {
    // Arrange
    MarginRequest marginRequest = new MarginRequest();
    marginRequest.tradeData(
        MarginCalculatorDiffblueBase.createAlternativeSmartDerivativeContractXml());

    // Act and Assert
    assertThrows(SDCException.class, () -> valuationController.margin(marginRequest));
  }

  /**
   * Test {@link ValuationController#margin(MarginRequest)}.
   *
   * <ul>
   *   <li>Given createValidSmartDerivativeContractXml.
   * </ul>
   *
   * <p>Method under test: {@link ValuationController#margin(MarginRequest)}
   */
  @Test
  @DisplayName("Test margin(MarginRequest); given createValidSmartDerivativeContractXml")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.margin(MarginRequest)"})
  void testMargin_givenCreateValidSmartDerivativeContractXml() {
    // Arrange
    MarginRequest marginRequest = new MarginRequest();
    marginRequest.tradeData(MarginCalculatorDiffblueBase.createValidSmartDerivativeContractXml());

    // Act and Assert
    assertThrows(SDCException.class, () -> valuationController.margin(marginRequest));
  }

  /**
   * Test {@link ValuationController#margin(MarginRequest)}.
   *
   * <ul>
   *   <li>Given createValidSmartDerivativeContractXml.
   * </ul>
   *
   * <p>Method under test: {@link ValuationController#margin(MarginRequest)}
   */
  @Test
  @DisplayName("Test margin(MarginRequest); given createValidSmartDerivativeContractXml")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.margin(MarginRequest)"})
  void testMargin_givenCreateValidSmartDerivativeContractXml2() {
    // Arrange
    MarginRequest marginRequest = new MarginRequest();
    marginRequest.marketDataStart(
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());
    marginRequest.tradeData(MarginCalculatorDiffblueBase.createValidSmartDerivativeContractXml());

    // Act and Assert
    assertThrows(SDCException.class, () -> valuationController.margin(marginRequest));
  }

  /**
   * Test {@link ValuationController#margin(MarginRequest)}.
   *
   * <ul>
   *   <li>Given {@link MarginRequest} (default constructor).
   *   <li>Then calls {@link MarginRequest#getMarketDataEnd()}.
   * </ul>
   *
   * <p>Method under test: {@link ValuationController#margin(MarginRequest)}
   */
  @Test
  @DisplayName(
      "Test margin(MarginRequest); given MarginRequest (default constructor); then calls getMarketDataEnd()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.margin(MarginRequest)"})
  void testMargin_givenMarginRequest_thenCallsGetMarketDataEnd() {
    // Arrange
    MarginRequest marginRequest = mock(MarginRequest.class);
    when(marginRequest.getMarketDataEnd())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());
    when(marginRequest.getMarketDataStart())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());
    when(marginRequest.getTradeData())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());
    when(marginRequest.tradeData(Mockito.<String>any())).thenReturn(new MarginRequest());
    marginRequest.tradeData(LocalDateTimeAdapterDiffblueBase.createValidDateTimeString());

    // Act and Assert
    assertThrows(SDCException.class, () -> valuationController.margin(marginRequest));
    verify(marginRequest).getMarketDataEnd();
    verify(marginRequest).getMarketDataStart();
    verify(marginRequest).getTradeData();
    verify(marginRequest).tradeData("20220905-170000");
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
   *   <li>When {@link MarginRequest} (default constructor) tradeData
   *       createMinimalSmartDerivativeContractXml.
   * </ul>
   *
   * <p>Method under test: {@link ValuationController#margin(MarginRequest)}
   */
  @Test
  @DisplayName(
      "Test margin(MarginRequest); when MarginRequest (default constructor) tradeData createMinimalSmartDerivativeContractXml")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.margin(MarginRequest)"})
  void testMargin_whenMarginRequestTradeDataCreateMinimalSmartDerivativeContractXml() {
    // Arrange
    MarginRequest marginRequest = new MarginRequest();
    marginRequest.tradeData(
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    // Act and Assert
    assertThrows(SDCException.class, () -> valuationController.margin(marginRequest));
  }

  /**
   * Test {@link ValuationController#margin(MarginRequest)}.
   *
   * <ul>
   *   <li>When {@link MarginRequest} (default constructor) tradeData createValidDateTimeString.
   * </ul>
   *
   * <p>Method under test: {@link ValuationController#margin(MarginRequest)}
   */
  @Test
  @DisplayName(
      "Test margin(MarginRequest); when MarginRequest (default constructor) tradeData createValidDateTimeString")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.margin(MarginRequest)"})
  void testMargin_whenMarginRequestTradeDataCreateValidDateTimeString() {
    // Arrange
    MarginRequest marginRequest = new MarginRequest();
    marginRequest.tradeData(LocalDateTimeAdapterDiffblueBase.createValidDateTimeString());

    // Act and Assert
    assertThrows(SDCException.class, () -> valuationController.margin(marginRequest));
  }

  /**
   * Test {@link ValuationController#value(ValueRequest)}.
   *
   * <ul>
   *   <li>Given createMinimalSmartDerivativeContractXml.
   * </ul>
   *
   * <p>Method under test: {@link ValuationController#value(ValueRequest)}
   */
  @Test
  @DisplayName("Test value(ValueRequest); given createMinimalSmartDerivativeContractXml")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.value(ValueRequest)"})
  void testValue_givenCreateMinimalSmartDerivativeContractXml() {
    // Arrange
    ValueRequest valueRequest = new ValueRequest();
    valueRequest.tradeData(
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    // Act and Assert
    assertThrows(SDCException.class, () -> valuationController.value(valueRequest));
  }

  /**
   * Test {@link ValuationController#value(ValueRequest)}.
   *
   * <ul>
   *   <li>Given createValidDateTimeString.
   * </ul>
   *
   * <p>Method under test: {@link ValuationController#value(ValueRequest)}
   */
  @Test
  @DisplayName("Test value(ValueRequest); given createValidDateTimeString")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.value(ValueRequest)"})
  void testValue_givenCreateValidDateTimeString() {
    // Arrange
    ValueRequest valueRequest = new ValueRequest();
    valueRequest.tradeData(LocalDateTimeAdapterDiffblueBase.createValidDateTimeString());

    // Act and Assert
    assertThrows(SDCException.class, () -> valuationController.value(valueRequest));
  }

  /**
   * Test {@link ValuationController#value(ValueRequest)}.
   *
   * <ul>
   *   <li>Given createValidMarketDataXml.
   * </ul>
   *
   * <p>Method under test: {@link ValuationController#value(ValueRequest)}
   */
  @Test
  @DisplayName("Test value(ValueRequest); given createValidMarketDataXml")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.value(ValueRequest)"})
  void testValue_givenCreateValidMarketDataXml() {
    // Arrange
    ValueRequest valueRequest = new ValueRequest();
    valueRequest.marketData(CalibrationParserDataItemsDiffblueBase.createValidMarketDataXml());
    valueRequest.tradeData(MarginCalculatorDiffblueBase.createValidSmartDerivativeContractXml());

    // Act and Assert
    assertThrows(SDCException.class, () -> valuationController.value(valueRequest));
  }

  /**
   * Test {@link ValuationController#value(ValueRequest)}.
   *
   * <ul>
   *   <li>Given createValidMarketDataXml.
   * </ul>
   *
   * <p>Method under test: {@link ValuationController#value(ValueRequest)}
   */
  @Test
  @DisplayName("Test value(ValueRequest); given createValidMarketDataXml")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.value(ValueRequest)"})
  void testValue_givenCreateValidMarketDataXml2() {
    // Arrange
    ValueRequest valueRequest = new ValueRequest();
    valueRequest.marketData(SettlementGeneratorDiffblueBase.createValidMarketDataXml());
    valueRequest.tradeData(MarginCalculatorDiffblueBase.createValidSmartDerivativeContractXml());

    // Act and Assert
    assertThrows(SDCException.class, () -> valuationController.value(valueRequest));
  }

  /**
   * Test {@link ValuationController#value(ValueRequest)}.
   *
   * <ul>
   *   <li>Given createValidSmartDerivativeContractXml.
   * </ul>
   *
   * <p>Method under test: {@link ValuationController#value(ValueRequest)}
   */
  @Test
  @DisplayName("Test value(ValueRequest); given createValidSmartDerivativeContractXml")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.value(ValueRequest)"})
  void testValue_givenCreateValidSmartDerivativeContractXml() {
    // Arrange
    ValueRequest valueRequest = new ValueRequest();
    valueRequest.tradeData(MarginCalculatorDiffblueBase.createValidSmartDerivativeContractXml());

    // Act and Assert
    assertThrows(SDCException.class, () -> valuationController.value(valueRequest));
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
   *   <li>Given createMinimalSmartDerivativeContractXml.
   * </ul>
   *
   * <p>Method under test: {@link ValuationController#valueAtTime(ValueRequest)}
   */
  @Test
  @DisplayName("Test valueAtTime(ValueRequest); given createMinimalSmartDerivativeContractXml")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.valueAtTime(ValueRequest)"})
  void testValueAtTime_givenCreateMinimalSmartDerivativeContractXml() {
    // Arrange
    ValueRequest valueRequest = new ValueRequest();
    valueRequest.tradeData(
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());
    valueRequest.valuationDate(LocalDateTimeAdapterDiffblueBase.createValidDateTimeString());

    // Act and Assert
    assertThrows(SDCException.class, () -> valuationController.valueAtTime(valueRequest));
  }

  /**
   * Test {@link ValuationController#valueAtTime(ValueRequest)}.
   *
   * <ul>
   *   <li>Given createValidDateTimeString.
   * </ul>
   *
   * <p>Method under test: {@link ValuationController#valueAtTime(ValueRequest)}
   */
  @Test
  @DisplayName("Test valueAtTime(ValueRequest); given createValidDateTimeString")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.valueAtTime(ValueRequest)"})
  void testValueAtTime_givenCreateValidDateTimeString() {
    // Arrange
    ValueRequest valueRequest = new ValueRequest();
    valueRequest.valuationDate(LocalDateTimeAdapterDiffblueBase.createValidDateTimeString());

    // Act and Assert
    assertThrows(SDCException.class, () -> valuationController.valueAtTime(valueRequest));
  }

  /**
   * Test {@link ValuationController#valueAtTime(ValueRequest)}.
   *
   * <ul>
   *   <li>Given createValidSmartDerivativeContractXml.
   * </ul>
   *
   * <p>Method under test: {@link ValuationController#valueAtTime(ValueRequest)}
   */
  @Test
  @DisplayName("Test valueAtTime(ValueRequest); given createValidSmartDerivativeContractXml")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.valueAtTime(ValueRequest)"})
  void testValueAtTime_givenCreateValidSmartDerivativeContractXml() {
    // Arrange
    ValueRequest valueRequest = new ValueRequest();
    valueRequest.tradeData(MarginCalculatorDiffblueBase.createValidSmartDerivativeContractXml());
    valueRequest.valuationDate(LocalDateTimeAdapterDiffblueBase.createValidDateTimeString());

    // Act and Assert
    assertThrows(SDCException.class, () -> valuationController.valueAtTime(valueRequest));
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
   * Test {@link ValuationController#valueAtTime(ValueRequest)}.
   *
   * <ul>
   *   <li>When {@link ValueRequest} (default constructor) tradeData createValidDateTimeString.
   * </ul>
   *
   * <p>Method under test: {@link ValuationController#valueAtTime(ValueRequest)}
   */
  @Test
  @DisplayName(
      "Test valueAtTime(ValueRequest); when ValueRequest (default constructor) tradeData createValidDateTimeString")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity ValuationController.valueAtTime(ValueRequest)"})
  void testValueAtTime_whenValueRequestTradeDataCreateValidDateTimeString() {
    // Arrange
    ValueRequest valueRequest = new ValueRequest();
    valueRequest.tradeData(LocalDateTimeAdapterDiffblueBase.createValidDateTimeString());
    valueRequest.valuationDate(LocalDateTimeAdapterDiffblueBase.createValidDateTimeString());

    // Act and Assert
    assertThrows(SDCException.class, () -> valuationController.valueAtTime(valueRequest));
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
        .thenThrow(
            new SDCException(
                ExceptionId.SDC_AUTH_ERROR,
                MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml()));

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
    // Arrange
    String name = MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    MockMultipartFile tradeData =
        new MockMultipartFile(name, new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

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
