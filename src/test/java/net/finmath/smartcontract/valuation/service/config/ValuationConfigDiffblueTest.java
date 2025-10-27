package net.finmath.smartcontract.valuation.service.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ValuationConfigDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ValuationConfig#setFpmlSchemaPath(String)}
   *   <li>{@link ValuationConfig#setInternalMarketDataProvider(String)}
   *   <li>{@link ValuationConfig#setLiveMarketData(boolean)}
   *   <li>{@link ValuationConfig#setLiveMarketDataProvider(String)}
   *   <li>{@link ValuationConfig#setMarketDataProviderToTemplate(Map)}
   *   <li>{@link ValuationConfig#setProductFixingType(String)}
   *   <li>{@link ValuationConfig#setSettlementCurrency(String)}
   *   <li>{@link ValuationConfig#getFpmlSchemaPath()}
   *   <li>{@link ValuationConfig#getInternalMarketDataProvider()}
   *   <li>{@link ValuationConfig#getLiveMarketDataProvider()}
   *   <li>{@link ValuationConfig#getMarketDataProviderToTemplate()}
   *   <li>{@link ValuationConfig#getProductFixingType()}
   *   <li>{@link ValuationConfig#getSettlementCurrency()}
   *   <li>{@link ValuationConfig#isLiveMarketData()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    ValuationConfig valuationConfig = new ValuationConfig();

    // Act
    valuationConfig.setFpmlSchemaPath("Fpml Schema Path");
    valuationConfig.setInternalMarketDataProvider("Internal Market Data Provider");
    valuationConfig.setLiveMarketData(true);
    valuationConfig.setLiveMarketDataProvider("Live Market Data Provider");
    HashMap<String, String> marketDataProviderToTemplate = new HashMap<>();
    valuationConfig.setMarketDataProviderToTemplate(marketDataProviderToTemplate);
    valuationConfig.setProductFixingType("Product Fixing Type");
    valuationConfig.setSettlementCurrency("GBP");
    String actualFpmlSchemaPath = valuationConfig.getFpmlSchemaPath();
    String actualInternalMarketDataProvider = valuationConfig.getInternalMarketDataProvider();
    String actualLiveMarketDataProvider = valuationConfig.getLiveMarketDataProvider();
    Map<String, String> actualMarketDataProviderToTemplate = valuationConfig.getMarketDataProviderToTemplate();
    String actualProductFixingType = valuationConfig.getProductFixingType();
    String actualSettlementCurrency = valuationConfig.getSettlementCurrency();
    boolean actualIsLiveMarketDataResult = valuationConfig.isLiveMarketData();

    // Assert that nothing has changed
    assertEquals("Fpml Schema Path", actualFpmlSchemaPath);
    assertEquals("GBP", actualSettlementCurrency);
    assertEquals("Internal Market Data Provider", actualInternalMarketDataProvider);
    assertEquals("Live Market Data Provider", actualLiveMarketDataProvider);
    assertEquals("Product Fixing Type", actualProductFixingType);
    assertTrue(actualMarketDataProviderToTemplate.isEmpty());
    assertTrue(actualIsLiveMarketDataResult);
    assertSame(marketDataProviderToTemplate, actualMarketDataProviderToTemplate);
  }
}
