/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */
package net.finmath.smartcontract.valuation.marketdata.generators;

import net.finmath.smartcontract.model.ExceptionId;
import net.finmath.smartcontract.model.SDCException;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MarketDataGeneratorRandomFeed constructor. Tests the constructor with focus on
 * branch and condition coverage.
 *
 * @author Claude Code
 */
class MarketDataGeneratorRandomFeedClaude_constructorTest {

  /**
   * Test constructor with valid parameters. Verifies that the object is created successfully with
   * valid XML market data and specifications.
   */
  @Test
  void testConstructor_ValidParameters_Success() throws Exception {
    // Arrange
    Period processingPeriod = Period.ofDays(1);
    String referenceMarketDataStr =
        """
        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
        <marketDataList>
            <requestTimeStamp>20240115-170000</requestTimeStamp>
            <item>
                <id>ESTR_Swap-Rate_1Y</id>
                <value>0.045</value>
                <timeStamp>20240115-170000</timeStamp>
            </item>
            <item>
                <id>Euribor6M_Swap-Rate_5Y</id>
                <value>0.05</value>
                <timeStamp>20240115-170000</timeStamp>
            </item>
        </marketDataList>
        """;

    List<CalibrationDataItem.Spec> mdSpecs = new ArrayList<>();
    mdSpecs.add(new CalibrationDataItem.Spec("ESTR_Swap-Rate_1Y", "ESTR", "Swap-Rate", "1Y"));
    mdSpecs.add(
        new CalibrationDataItem.Spec("Euribor6M_Swap-Rate_5Y", "Euribor6M", "Swap-Rate", "5Y"));

    LocalDateTime beforeCreation = LocalDateTime.now();

    // Act
    MarketDataGeneratorRandomFeed generator =
        new MarketDataGeneratorRandomFeed(processingPeriod, referenceMarketDataStr, mdSpecs);

    LocalDateTime afterCreation = LocalDateTime.now();

    // Assert
    assertNotNull(generator, "Generator should be created successfully");
    // Verify endTime is set correctly (should be approximately now + processingPeriod)
    assertNotNull(generator.endTime, "endTime should be set");
    assertTrue(
        generator.endTime.isAfter(beforeCreation.plus(processingPeriod).minusSeconds(1)),
        "endTime should be after now + processingPeriod");
    assertTrue(
        generator.endTime.isBefore(afterCreation.plus(processingPeriod).plusSeconds(1)),
        "endTime should be before now + processingPeriod with small buffer");

    // Verify referenceSet is initialized
    assertNotNull(generator.referenceSet, "referenceSet should be initialized");
    assertEquals(
        2,
        generator.referenceSet.getCalibrationDataItems().size(),
        "referenceSet should contain two calibration data items");

    // Verify simulationFrequencySec is set to 3
    assertEquals(3, generator.simulationFrequencySec, "simulationFrequencySec should be 3");
  }

  /**
   * Test constructor with zero period. Verifies that endTime is set to current time when period is
   * zero.
   */
  @Test
  void testConstructor_ZeroPeriod_EndTimeIsNow() throws Exception {
    // Arrange
    Period processingPeriod = Period.ZERO;
    String referenceMarketDataStr =
        """
        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
        <marketDataList>
            <requestTimeStamp>20240115-170000</requestTimeStamp>
            <item>
                <id>ESTR_Swap-Rate_1Y</id>
                <value>0.045</value>
                <timeStamp>20240115-170000</timeStamp>
            </item>
        </marketDataList>
        """;

    List<CalibrationDataItem.Spec> mdSpecs = new ArrayList<>();
    mdSpecs.add(new CalibrationDataItem.Spec("ESTR_Swap-Rate_1Y", "ESTR", "Swap-Rate", "1Y"));

    LocalDateTime beforeCreation = LocalDateTime.now();

    // Act
    MarketDataGeneratorRandomFeed generator =
        new MarketDataGeneratorRandomFeed(processingPeriod, referenceMarketDataStr, mdSpecs);

    LocalDateTime afterCreation = LocalDateTime.now();

    // Assert
    assertNotNull(generator, "Generator should be created successfully");
    assertTrue(
        generator.endTime.isAfter(beforeCreation.minusSeconds(1)),
        "endTime should be approximately now");
    assertTrue(
        generator.endTime.isBefore(afterCreation.plusSeconds(1)),
        "endTime should be approximately now");
  }

  /**
   * Test constructor with large period. Verifies that endTime is set correctly for large periods
   * (months and years).
   */
  @Test
  void testConstructor_LargePeriod_EndTimeSetCorrectly() throws Exception {
    // Arrange
    Period processingPeriod = Period.of(1, 6, 15); // 1 year, 6 months, 15 days
    String referenceMarketDataStr =
        """
        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
        <marketDataList>
            <requestTimeStamp>20240115-170000</requestTimeStamp>
            <item>
                <id>ESTR_Swap-Rate_1Y</id>
                <value>0.045</value>
                <timeStamp>20240115-170000</timeStamp>
            </item>
        </marketDataList>
        """;

    List<CalibrationDataItem.Spec> mdSpecs = new ArrayList<>();
    mdSpecs.add(new CalibrationDataItem.Spec("ESTR_Swap-Rate_1Y", "ESTR", "Swap-Rate", "1Y"));

    LocalDateTime beforeCreation = LocalDateTime.now();

    // Act
    MarketDataGeneratorRandomFeed generator =
        new MarketDataGeneratorRandomFeed(processingPeriod, referenceMarketDataStr, mdSpecs);

    LocalDateTime afterCreation = LocalDateTime.now();

    // Assert
    assertNotNull(generator, "Generator should be created successfully");
    assertTrue(
        generator.endTime.isAfter(beforeCreation.plus(processingPeriod).minusSeconds(1)),
        "endTime should be approximately now + large period");
    assertTrue(
        generator.endTime.isBefore(afterCreation.plus(processingPeriod).plusSeconds(1)),
        "endTime should be approximately now + large period");
  }

  /**
   * Test constructor with empty specs list. Should throw SDCException when no matching items are
   * found in the XML.
   */
  @Test
  void testConstructor_EmptySpecsList_ThrowsSDCException() {
    // Arrange
    Period processingPeriod = Period.ofDays(1);
    String referenceMarketDataStr =
        """
        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
        <marketDataList>
            <requestTimeStamp>20240115-170000</requestTimeStamp>
            <item>
                <id>ESTR_Swap-Rate_1Y</id>
                <value>0.045</value>
                <timeStamp>20240115-170000</timeStamp>
            </item>
        </marketDataList>
        """;

    List<CalibrationDataItem.Spec> mdSpecs = new ArrayList<>();

    // Act & Assert
    SDCException exception =
        assertThrows(
            SDCException.class,
            () -> {
              new MarketDataGeneratorRandomFeed(processingPeriod, referenceMarketDataStr, mdSpecs);
            },
            "Should throw SDCException for empty specs list");

    assertEquals(
        ExceptionId.SDC_CALIBRATION_DATA_EMPTY,
        exception.getId(),
        "Exception should have correct ID");
  }

  /**
   * Test constructor with specs that don't match XML items. Should throw SDCException when no
   * matching items are found.
   */
  @Test
  void testConstructor_NonMatchingSpecs_ThrowsSDCException() {
    // Arrange
    Period processingPeriod = Period.ofDays(1);
    String referenceMarketDataStr =
        """
        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
        <marketDataList>
            <requestTimeStamp>20240115-170000</requestTimeStamp>
            <item>
                <id>ESTR_Swap-Rate_1Y</id>
                <value>0.045</value>
                <timeStamp>20240115-170000</timeStamp>
            </item>
        </marketDataList>
        """;

    List<CalibrationDataItem.Spec> mdSpecs = new ArrayList<>();
    mdSpecs.add(
        new CalibrationDataItem.Spec("NonExistent_Swap-Rate_1Y", "NonExistent", "Swap-Rate", "1Y"));

    // Act & Assert
    SDCException exception =
        assertThrows(
            SDCException.class,
            () -> {
              new MarketDataGeneratorRandomFeed(processingPeriod, referenceMarketDataStr, mdSpecs);
            },
            "Should throw SDCException when specs don't match XML items");

    assertEquals(
        ExceptionId.SDC_CALIBRATION_DATA_EMPTY,
        exception.getId(),
        "Exception should have correct ID");
  }

  /**
   * Test constructor with invalid XML format. Should throw Exception when XML cannot be parsed.
   */
  @Test
  void testConstructor_InvalidXmlFormat_ThrowsException() {
    // Arrange
    Period processingPeriod = Period.ofDays(1);
    String referenceMarketDataStr = "{ this is not valid XML }";

    List<CalibrationDataItem.Spec> mdSpecs = new ArrayList<>();
    mdSpecs.add(new CalibrationDataItem.Spec("ESTR_Swap-Rate_1Y", "ESTR", "Swap-Rate", "1Y"));

    // Act & Assert
    assertThrows(
        Exception.class,
        () -> {
          new MarketDataGeneratorRandomFeed(processingPeriod, referenceMarketDataStr, mdSpecs);
        },
        "Should throw Exception for invalid XML format");
  }

  /**
   * Test constructor with malformed XML (missing closing tags). Should throw Exception when XML
   * cannot be parsed.
   */
  @Test
  void testConstructor_MalformedXml_ThrowsException() {
    // Arrange
    Period processingPeriod = Period.ofDays(1);
    String referenceMarketDataStr =
        """
        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
        <marketDataList>
            <requestTimeStamp>20240115-170000</requestTimeStamp>
            <item>
                <id>ESTR_Swap-Rate_1Y</id>
                <value>0.045</value>
                <timeStamp>20240115-170000</timeStamp>
            </item>
        """; // Missing closing </marketDataList>

    List<CalibrationDataItem.Spec> mdSpecs = new ArrayList<>();
    mdSpecs.add(new CalibrationDataItem.Spec("ESTR_Swap-Rate_1Y", "ESTR", "Swap-Rate", "1Y"));

    // Act & Assert
    assertThrows(
        Exception.class,
        () -> {
          new MarketDataGeneratorRandomFeed(processingPeriod, referenceMarketDataStr, mdSpecs);
        },
        "Should throw Exception for malformed XML");
  }

  /**
   * Test constructor with null period. Should throw NullPointerException when period is null.
   */
  @Test
  void testConstructor_NullPeriod_ThrowsNullPointerException() {
    // Arrange
    Period processingPeriod = null;
    String referenceMarketDataStr =
        """
        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
        <marketDataList>
            <requestTimeStamp>20240115-170000</requestTimeStamp>
            <item>
                <id>ESTR_Swap-Rate_1Y</id>
                <value>0.045</value>
                <timeStamp>20240115-170000</timeStamp>
            </item>
        </marketDataList>
        """;

    List<CalibrationDataItem.Spec> mdSpecs = new ArrayList<>();
    mdSpecs.add(new CalibrationDataItem.Spec("ESTR_Swap-Rate_1Y", "ESTR", "Swap-Rate", "1Y"));

    // Act & Assert
    assertThrows(
        NullPointerException.class,
        () -> {
          new MarketDataGeneratorRandomFeed(processingPeriod, referenceMarketDataStr, mdSpecs);
        },
        "Should throw NullPointerException for null period");
  }

  /**
   * Test constructor with null XML string. Should throw Exception when referenceMarketDataStr is
   * null.
   */
  @Test
  void testConstructor_NullXmlString_ThrowsException() {
    // Arrange
    Period processingPeriod = Period.ofDays(1);
    String referenceMarketDataStr = null;

    List<CalibrationDataItem.Spec> mdSpecs = new ArrayList<>();
    mdSpecs.add(new CalibrationDataItem.Spec("ESTR_Swap-Rate_1Y", "ESTR", "Swap-Rate", "1Y"));

    // Act & Assert
    assertThrows(
        Exception.class,
        () -> {
          new MarketDataGeneratorRandomFeed(processingPeriod, referenceMarketDataStr, mdSpecs);
        },
        "Should throw Exception for null XML string");
  }

  /**
   * Test constructor with null specs list. Should throw NullPointerException when mdSpecs is null.
   */
  @Test
  void testConstructor_NullSpecsList_ThrowsNullPointerException() {
    // Arrange
    Period processingPeriod = Period.ofDays(1);
    String referenceMarketDataStr =
        """
        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
        <marketDataList>
            <requestTimeStamp>20240115-170000</requestTimeStamp>
            <item>
                <id>ESTR_Swap-Rate_1Y</id>
                <value>0.045</value>
                <timeStamp>20240115-170000</timeStamp>
            </item>
        </marketDataList>
        """;

    List<CalibrationDataItem.Spec> mdSpecs = null;

    // Act & Assert
    assertThrows(
        NullPointerException.class,
        () -> {
          new MarketDataGeneratorRandomFeed(processingPeriod, referenceMarketDataStr, mdSpecs);
        },
        "Should throw NullPointerException for null specs list");
  }

  /**
   * Test constructor with empty XML string. Should throw Exception when XML is empty.
   */
  @Test
  void testConstructor_EmptyXmlString_ThrowsException() {
    // Arrange
    Period processingPeriod = Period.ofDays(1);
    String referenceMarketDataStr = "";

    List<CalibrationDataItem.Spec> mdSpecs = new ArrayList<>();
    mdSpecs.add(new CalibrationDataItem.Spec("ESTR_Swap-Rate_1Y", "ESTR", "Swap-Rate", "1Y"));

    // Act & Assert
    assertThrows(
        Exception.class,
        () -> {
          new MarketDataGeneratorRandomFeed(processingPeriod, referenceMarketDataStr, mdSpecs);
        },
        "Should throw Exception for empty XML string");
  }

  /**
   * Test constructor with single spec and matching XML item. Verifies basic functionality with
   * minimal valid input.
   */
  @Test
  void testConstructor_SingleSpecMatchingXml_Success() throws Exception {
    // Arrange
    Period processingPeriod = Period.ofDays(7);
    String referenceMarketDataStr =
        """
        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
        <marketDataList>
            <requestTimeStamp>20240115-170000</requestTimeStamp>
            <item>
                <id>ESTR_Swap-Rate_1Y</id>
                <value>0.045</value>
                <timeStamp>20240115-170000</timeStamp>
            </item>
        </marketDataList>
        """;

    List<CalibrationDataItem.Spec> mdSpecs = new ArrayList<>();
    mdSpecs.add(new CalibrationDataItem.Spec("ESTR_Swap-Rate_1Y", "ESTR", "Swap-Rate", "1Y"));

    // Act
    MarketDataGeneratorRandomFeed generator =
        new MarketDataGeneratorRandomFeed(processingPeriod, referenceMarketDataStr, mdSpecs);

    // Assert
    assertNotNull(generator, "Generator should be created successfully");
    assertNotNull(generator.referenceSet, "referenceSet should be initialized");
    assertEquals(
        1,
        generator.referenceSet.getCalibrationDataItems().size(),
        "referenceSet should contain one calibration data item");
    assertEquals(3, generator.simulationFrequencySec, "simulationFrequencySec should be 3");
  }

  /**
   * Test constructor with multiple specs where only some match XML. Verifies that only matching
   * specs are included in the reference set.
   */
  @Test
  void testConstructor_PartiallyMatchingSpecs_IncludesOnlyMatching() throws Exception {
    // Arrange
    Period processingPeriod = Period.ofDays(1);
    String referenceMarketDataStr =
        """
        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
        <marketDataList>
            <requestTimeStamp>20240115-170000</requestTimeStamp>
            <item>
                <id>ESTR_Swap-Rate_1Y</id>
                <value>0.045</value>
                <timeStamp>20240115-170000</timeStamp>
            </item>
        </marketDataList>
        """;

    List<CalibrationDataItem.Spec> mdSpecs = new ArrayList<>();
    mdSpecs.add(new CalibrationDataItem.Spec("ESTR_Swap-Rate_1Y", "ESTR", "Swap-Rate", "1Y"));
    mdSpecs.add(
        new CalibrationDataItem.Spec(
            "NonExistent_Swap-Rate_5Y", "NonExistent", "Swap-Rate", "5Y"));

    // Act
    MarketDataGeneratorRandomFeed generator =
        new MarketDataGeneratorRandomFeed(processingPeriod, referenceMarketDataStr, mdSpecs);

    // Assert
    assertNotNull(generator, "Generator should be created successfully");
    assertNotNull(generator.referenceSet, "referenceSet should be initialized");
    assertEquals(
        1,
        generator.referenceSet.getCalibrationDataItems().size(),
        "referenceSet should contain only one matching calibration data item");
  }

  /**
   * Test constructor with negative period. Verifies that negative periods are handled (endTime
   * will be before current time).
   */
  @Test
  void testConstructor_NegativePeriod_EndTimeInPast() throws Exception {
    // Arrange
    Period processingPeriod = Period.ofDays(-5);
    String referenceMarketDataStr =
        """
        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
        <marketDataList>
            <requestTimeStamp>20240115-170000</requestTimeStamp>
            <item>
                <id>ESTR_Swap-Rate_1Y</id>
                <value>0.045</value>
                <timeStamp>20240115-170000</timeStamp>
            </item>
        </marketDataList>
        """;

    List<CalibrationDataItem.Spec> mdSpecs = new ArrayList<>();
    mdSpecs.add(new CalibrationDataItem.Spec("ESTR_Swap-Rate_1Y", "ESTR", "Swap-Rate", "1Y"));

    LocalDateTime beforeCreation = LocalDateTime.now();

    // Act
    MarketDataGeneratorRandomFeed generator =
        new MarketDataGeneratorRandomFeed(processingPeriod, referenceMarketDataStr, mdSpecs);

    LocalDateTime afterCreation = LocalDateTime.now();

    // Assert
    assertNotNull(generator, "Generator should be created successfully");
    // With negative period, endTime should be in the past (before now)
    assertTrue(
        generator.endTime.isBefore(afterCreation),
        "endTime should be approximately now - 5 days (in the past)");
    assertTrue(
        generator.endTime.isAfter(beforeCreation.plus(processingPeriod).minusSeconds(1)),
        "endTime should be approximately now - 5 days (in the past)");
  }

  /**
   * Test constructor with XML containing multiple items but only one spec. Verifies that filtering
   * works correctly.
   */
  @Test
  void testConstructor_MultipleXmlItemsSingleSpec_FiltersCorrectly() throws Exception {
    // Arrange
    Period processingPeriod = Period.ofDays(1);
    String referenceMarketDataStr =
        """
        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
        <marketDataList>
            <requestTimeStamp>20240115-170000</requestTimeStamp>
            <item>
                <id>ESTR_Swap-Rate_1Y</id>
                <value>0.045</value>
                <timeStamp>20240115-170000</timeStamp>
            </item>
            <item>
                <id>Euribor6M_Swap-Rate_5Y</id>
                <value>0.05</value>
                <timeStamp>20240115-170000</timeStamp>
            </item>
            <item>
                <id>OtherItem</id>
                <value>0.055</value>
                <timeStamp>20240115-170000</timeStamp>
            </item>
        </marketDataList>
        """;

    List<CalibrationDataItem.Spec> mdSpecs = new ArrayList<>();
    mdSpecs.add(new CalibrationDataItem.Spec("ESTR_Swap-Rate_1Y", "ESTR", "Swap-Rate", "1Y"));

    // Act
    MarketDataGeneratorRandomFeed generator =
        new MarketDataGeneratorRandomFeed(processingPeriod, referenceMarketDataStr, mdSpecs);

    // Assert
    assertNotNull(generator, "Generator should be created successfully");
    assertNotNull(generator.referenceSet, "referenceSet should be initialized");
    assertEquals(
        1,
        generator.referenceSet.getCalibrationDataItems().size(),
        "referenceSet should contain only the one matching calibration data item");
  }

  /**
   * Test constructor with very large XML containing many items. Verifies that the constructor can
   * handle larger datasets.
   */
  @Test
  void testConstructor_LargeXmlDataset_Success() throws Exception {
    // Arrange
    Period processingPeriod = Period.ofDays(1);

    // Build XML with 10 items
    StringBuilder xmlBuilder = new StringBuilder();
    xmlBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
    xmlBuilder.append("<marketDataList>\n");
    xmlBuilder.append("    <requestTimeStamp>20240115-170000</requestTimeStamp>\n");

    List<CalibrationDataItem.Spec> mdSpecs = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      String itemId = "Item" + i + "_Swap-Rate_" + (i + 1) + "Y";
      xmlBuilder.append("    <item>\n");
      xmlBuilder.append("        <id>").append(itemId).append("</id>\n");
      xmlBuilder.append("        <value>").append(0.04 + i * 0.001).append("</value>\n");
      xmlBuilder.append("        <timeStamp>20240115-170000</timeStamp>\n");
      xmlBuilder.append("    </item>\n");

      mdSpecs.add(
          new CalibrationDataItem.Spec(
              itemId, "Item" + i, "Swap-Rate", (i + 1) + "Y"));
    }

    xmlBuilder.append("</marketDataList>");
    String referenceMarketDataStr = xmlBuilder.toString();

    // Act
    MarketDataGeneratorRandomFeed generator =
        new MarketDataGeneratorRandomFeed(processingPeriod, referenceMarketDataStr, mdSpecs);

    // Assert
    assertNotNull(generator, "Generator should be created successfully");
    assertNotNull(generator.referenceSet, "referenceSet should be initialized");
    assertEquals(
        10,
        generator.referenceSet.getCalibrationDataItems().size(),
        "referenceSet should contain 10 calibration data items");
    assertEquals(3, generator.simulationFrequencySec, "simulationFrequencySec should be 3");
  }
}
