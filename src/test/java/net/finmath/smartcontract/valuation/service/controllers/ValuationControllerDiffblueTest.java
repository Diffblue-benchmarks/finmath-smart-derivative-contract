package net.finmath.smartcontract.valuation.service.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import net.finmath.smartcontract.model.MarginRequest;
import net.finmath.smartcontract.model.SDCException;
import net.finmath.smartcontract.model.ValueRequest;
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
  @Autowired
  private ValuationController valuationController;

  /**
   * Method under test: {@link ValuationController#margin(MarginRequest)}
   */
  @Test
  void testMargin() {
    // Arrange, Act and Assert
    assertThrows(SDCException.class, () -> valuationController.margin(new MarginRequest()));
  }

  /**
   * Method under test: {@link ValuationController#margin(MarginRequest)}
   */
  @Test
  void testMargin2() {
    // Arrange
    MarginRequest marginRequest = new MarginRequest();
    marginRequest.marketDataStart("Responded");

    // Act and Assert
    assertThrows(SDCException.class, () -> valuationController.margin(marginRequest));
  }

  /**
   * Method under test: {@link ValuationController#margin(MarginRequest)}
   */
  @Test
  void testMargin3() {
    // Arrange
    MarginRequest marginRequest = new MarginRequest();
    marginRequest.tradeData("Responded");

    // Act and Assert
    assertThrows(SDCException.class, () -> valuationController.margin(marginRequest));
  }

  /**
   * Method under test: {@link ValuationController#margin(MarginRequest)}
   */
  @Test
  void testMargin4() {
    // Arrange
    MarginRequest marginRequest = mock(MarginRequest.class);
    when(marginRequest.getMarketDataEnd()).thenReturn("Market Data End");
    when(marginRequest.getMarketDataStart()).thenReturn("Market Data Start");
    when(marginRequest.getTradeData()).thenReturn("Trade Data");
    when(marginRequest.marketDataStart(Mockito.<String>any())).thenReturn(new MarginRequest());
    marginRequest.marketDataStart("margin");

    // Act and Assert
    assertThrows(SDCException.class, () -> valuationController.margin(marginRequest));
    verify(marginRequest).getMarketDataEnd();
    verify(marginRequest).getMarketDataStart();
    verify(marginRequest).getTradeData();
    verify(marginRequest).marketDataStart(eq("margin"));
  }

  /**
   * Method under test: {@link ValuationController#test()}
   */
  @Test
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

  /**
   * Method under test:
   * {@link ValuationController#testProductValue(MultipartFile)}
   */
  @Test
  void testTestProductValue() throws IOException {
    // Arrange, Act and Assert
    assertThrows(SDCException.class, () -> valuationController
        .testProductValue(new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")))));
    assertThrows(SDCException.class, () -> valuationController.testProductValue(null));
  }

  /**
   * Method under test:
   * {@link ValuationController#testProductValue(MultipartFile)}
   */
  @Test
  void testTestProductValue2() throws IOException {
    // Arrange
    MultipartFile tradeData = mock(MultipartFile.class);
    when(tradeData.getInputStream()).thenReturn(null);

    // Act and Assert
    assertThrows(SDCException.class, () -> valuationController.testProductValue(tradeData));
    verify(tradeData).getInputStream();
  }

  /**
   * Method under test: {@link ValuationController#value(ValueRequest)}
   */
  @Test
  void testValue() {
    // Arrange, Act and Assert
    assertThrows(SDCException.class, () -> valuationController.value(new ValueRequest()));
    assertThrows(SDCException.class, () -> valuationController.value(null));
  }

  /**
   * Method under test: {@link ValuationController#value(ValueRequest)}
   */
  @Test
  void testValue2() {
    // Arrange
    ValueRequest valueRequest = new ValueRequest();
    valueRequest.tradeData("Responded");

    // Act and Assert
    assertThrows(SDCException.class, () -> valuationController.value(valueRequest));
  }

  /**
   * Method under test: {@link ValuationController#valueAtTime(ValueRequest)}
   */
  @Test
  void testValueAtTime() {
    // Arrange, Act and Assert
    assertThrows(SDCException.class, () -> valuationController.valueAtTime(new ValueRequest()));
    assertThrows(SDCException.class, () -> valuationController.valueAtTime(null));
  }
}
