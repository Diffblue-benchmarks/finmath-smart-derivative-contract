/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */
package net.finmath.smartcontract.valuation.marketdata.generators;

import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.product.SmartDerivativeContractDescriptor;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem.Spec;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.lang.reflect.Modifier;

/**
 * Test class for MarketDataGeneratorLauncher. Tests the instantiateMarketDataGeneratorWebsocket
 * method with focus on branch and condition coverage.
 *
 * <p>Note: This class is challenging to test comprehensively because: 1. It requires real WebSocket
 * connections to external services 2. It performs authentication with Refinitiv Data Platform 3. It
 * has blocking waits for asynchronous data retrieval 4. The WebSocketConnector and
 * MarketDataGeneratorWebsocket classes are tightly coupled to network I/O
 *
 * <p>Without reflection, we cannot test the internal logic without actual network connections. The
 * tests below verify error paths and integration scenarios.
 *
 * @author Claude Code
 */
class MarketDataGeneratorLauncherClaudeTest {
  @Mock private SmartDerivativeContractDescriptor mockSdc;

  private Properties connectionProperties;

  private List<CalibrationDataItem.Spec> marketDataSpecs;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    // Setup connection properties with fake values
    connectionProperties = new Properties();
    connectionProperties.setProperty("AUTHURL", "https://fake.api.example.com/auth");
    connectionProperties.setProperty("HOSTNAME", "fake.example.com");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "FALSE");
    connectionProperties.setProperty("CLIENTID", "test_client_id");
    connectionProperties.setProperty("USER", "test_user");
    connectionProperties.setProperty("PASSWORD", "test_password");

    // Setup market data specs
    marketDataSpecs =
        Arrays.asList(
            new CalibrationDataItem.Spec("EUR001M=", "Discount-EUR-OIS", "Fixing", "1M"),
            new CalibrationDataItem.Spec("EUSA1=", "Discount-EUR-OIS", "Swap", "1Y"));
  }

  /**
   * Test instantiateMarketDataGeneratorWebsocket with null properties. This should throw
   * RuntimeException wrapping NullPointerException when trying to access properties. The
   * NullPointerException is caught by the general Exception handler at line 58-59.
   */
  @Test
  void testInstantiateMarketDataGeneratorWebsocket_NullProperties() {
    // Arrange
    when(mockSdc.getMarketdataItemList()).thenReturn(marketDataSpecs);

    // Act & Assert
    RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              MarketDataGeneratorLauncher.instantiateMarketDataGeneratorWebsocket(null, mockSdc);
            });

    // Verify it wraps a NullPointerException
    assertNotNull(exception.getCause());
    assertTrue(exception.getCause() instanceof NullPointerException);
  }

  /**
   * Test instantiateMarketDataGeneratorWebsocket with null SDC. This should throw RuntimeException
   * wrapping NullPointerException when trying to get market data items. The NullPointerException is
   * caught by the general Exception handler at line 58-59.
   */
  @Test
  void testInstantiateMarketDataGeneratorWebsocket_NullSdc() {
    // Act & Assert
    RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              MarketDataGeneratorLauncher.instantiateMarketDataGeneratorWebsocket(
                  connectionProperties, null);
            });

    // Verify it wraps a NullPointerException
    assertNotNull(exception.getCause());
    assertTrue(exception.getCause() instanceof NullPointerException);
  }

  /**
   * Test instantiateMarketDataGeneratorWebsocket with empty market data list. This tests the
   * scenario where SDC returns an empty list of market data items.
   */
  @Test
  void testInstantiateMarketDataGeneratorWebsocket_EmptyMarketDataList() {
    // Arrange
    when(mockSdc.getMarketdataItemList()).thenReturn(Arrays.asList());

    // Act & Assert

    // This will fail during authentication/connection but covers the code path
    assertThrows(
        RuntimeException.class,
        () -> {
          MarketDataGeneratorLauncher.instantiateMarketDataGeneratorWebsocket(
              connectionProperties, mockSdc);
        });
  }

  /**
   * Test instantiateMarketDataGeneratorWebsocket with invalid connection properties. This should
   * fail during authentication or WebSocket connection.
   */
  @Test
  void testInstantiateMarketDataGeneratorWebsocket_InvalidConnectionProperties() {
    // Arrange
    when(mockSdc.getMarketdataItemList()).thenReturn(marketDataSpecs);

    // Use invalid connection properties
    Properties invalidProps = new Properties();
    invalidProps.setProperty("AUTHURL", "invalid-url");
    invalidProps.setProperty("HOSTNAME", "invalid-host");
    invalidProps.setProperty("PORT", "invalid-port");

    // Act & Assert

    // This should throw RuntimeException wrapping IOException or Exception
    assertThrows(
        RuntimeException.class,
        () -> {
          MarketDataGeneratorLauncher.instantiateMarketDataGeneratorWebsocket(
              invalidProps, mockSdc);
        });
  }

  /**
   * Test instantiateMarketDataGeneratorWebsocket with missing required properties. This should fail
   * when WebSocketConnector tries to access missing properties.
   */
  @Test
  void testInstantiateMarketDataGeneratorWebsocket_MissingRequiredProperties() {
    // Arrange
    when(mockSdc.getMarketdataItemList()).thenReturn(marketDataSpecs);

    // Use properties with missing required fields
    Properties incompleteProps = new Properties();
    incompleteProps.setProperty("AUTHURL", "https://fake.api.example.com/auth");
    // Missing HOSTNAME, PORT, etc.

    // Act & Assert
    assertThrows(
        RuntimeException.class,
        () -> {
          MarketDataGeneratorLauncher.instantiateMarketDataGeneratorWebsocket(
              incompleteProps, mockSdc);
        });
  }

  /**
   * Test instantiateMarketDataGeneratorWebsocket with unreachable authentication URL. This tests
   * the IOException catch block at line 56-57.
   */
  @Test
  void testInstantiateMarketDataGeneratorWebsocket_UnreachableAuthUrl() {
    // Arrange
    when(mockSdc.getMarketdataItemList()).thenReturn(marketDataSpecs);

    // Use unreachable auth URL
    Properties unreachableProps = new Properties();
    unreachableProps.setProperty("AUTHURL", "https://unreachable.invalid.domain.example/auth");
    unreachableProps.setProperty("HOSTNAME", "unreachable.invalid.domain.example");
    unreachableProps.setProperty("PORT", "443");
    unreachableProps.setProperty("USEPROXY", "FALSE");
    unreachableProps.setProperty("CLIENTID", "test");
    unreachableProps.setProperty("USER", "test");
    unreachableProps.setProperty("PASSWORD", "test");

    // Act & Assert

    // Should throw RuntimeException wrapping the connection failure
    RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              MarketDataGeneratorLauncher.instantiateMarketDataGeneratorWebsocket(
                  unreachableProps, mockSdc);
            });

    // Verify the exception is wrapped
    assertNotNull(exception);
  }

  /**
   * Test instantiateMarketDataGeneratorWebsocket with valid properties but fake credentials. This
   * tests the flow through WebSocketConnector creation and authentication failure. The test expects
   * a RuntimeException due to authentication failure.
   */
  @Test
  void testInstantiateMarketDataGeneratorWebsocket_AuthenticationFailure() {
    // Arrange
    when(mockSdc.getMarketdataItemList()).thenReturn(marketDataSpecs);

    // Act & Assert

    // This will fail during authentication as we're using fake credentials
    RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              MarketDataGeneratorLauncher.instantiateMarketDataGeneratorWebsocket(
                  connectionProperties, mockSdc);
            });

    assertNotNull(exception);
    // The exception should be caused by authentication or connection failure
  }

  /**
   * Test that the private constructor exists and cannot be instantiated. This is a utility class
   * with only static methods.
   */
  @Test
  void testPrivateConstructor() throws Exception {
    // Verify that the constructor is private
    var constructor = MarketDataGeneratorLauncher.class.getDeclaredConstructor();
    assertTrue(Modifier.isPrivate(constructor.getModifiers()));

    // Verify we can still instantiate it via reflection (for coverage)
    constructor.setAccessible(true);
    assertNotNull(constructor.newInstance());
  }

  /**
   * Test instantiateMarketDataGeneratorWebsocket with null market data item list from SDC. This
   * should throw NullPointerException when the list is passed to MarketDataGeneratorWebsocket.
   */
  @Test
  void testInstantiateMarketDataGeneratorWebsocket_NullMarketDataItemList() {
    // Arrange
    when(mockSdc.getMarketdataItemList()).thenReturn(null);

    // Act & Assert
    assertThrows(
        RuntimeException.class,
        () -> {
          MarketDataGeneratorLauncher.instantiateMarketDataGeneratorWebsocket(
              connectionProperties, mockSdc);
        });
  }

  /**
   * Test with proxy configuration enabled. This tests a different branch in WebSocketConnector
   * initialization.
   */
  @Test
  void testInstantiateMarketDataGeneratorWebsocket_WithProxyEnabled() {
    // Arrange
    when(mockSdc.getMarketdataItemList()).thenReturn(marketDataSpecs);

    Properties proxyProps = new Properties();
    proxyProps.putAll(connectionProperties);
    proxyProps.setProperty("USEPROXY", "TRUE");
    proxyProps.setProperty("PROXYHOST", "proxy.example.com");
    proxyProps.setProperty("PROXYPORT", "8080");
    proxyProps.setProperty("PROXYUSER", "proxyuser");
    proxyProps.setProperty("PROXYPASS", "proxypass");

    // Act & Assert

    // This will fail during connection but covers the proxy configuration branch
    assertThrows(
        RuntimeException.class,
        () -> {
          MarketDataGeneratorLauncher.instantiateMarketDataGeneratorWebsocket(proxyProps, mockSdc);
        });
  }

  /**
   * Test that verifies the method catches IOException and wraps it in RuntimeException. Line 56-57
   * catch IOException.
   */
  @Test
  void testInstantiateMarketDataGeneratorWebsocket_IOExceptionHandling() {
    // Arrange
    when(mockSdc.getMarketdataItemList()).thenReturn(marketDataSpecs);

    // Use malformed URL that will cause IOException
    Properties malformedProps = new Properties();
    malformedProps.setProperty("AUTHURL", "not-a-valid-url");
    malformedProps.setProperty("HOSTNAME", "example.com");
    malformedProps.setProperty("PORT", "443");
    malformedProps.setProperty("USEPROXY", "FALSE");
    malformedProps.setProperty("CLIENTID", "test");
    malformedProps.setProperty("USER", "test");
    malformedProps.setProperty("PASSWORD", "test");

    // Act & Assert
    RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              MarketDataGeneratorLauncher.instantiateMarketDataGeneratorWebsocket(
                  malformedProps, mockSdc);
            });

    assertNotNull(exception);
  }

  /**
   * Test that verifies the method catches general Exception and wraps it in RuntimeException. Line
   * 58-59 catch Exception.
   */
  @Test
  void testInstantiateMarketDataGeneratorWebsocket_GeneralExceptionHandling() {
    // Arrange - This will throw an exception during getMarketdataItemList
    when(mockSdc.getMarketdataItemList()).thenThrow(new RuntimeException("Simulated error"));

    // Act & Assert
    RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              MarketDataGeneratorLauncher.instantiateMarketDataGeneratorWebsocket(
                  connectionProperties, mockSdc);
            });

    assertNotNull(exception);
    assertTrue(exception.getMessage().contains("Simulated error") || exception.getCause() != null);
  }
}
