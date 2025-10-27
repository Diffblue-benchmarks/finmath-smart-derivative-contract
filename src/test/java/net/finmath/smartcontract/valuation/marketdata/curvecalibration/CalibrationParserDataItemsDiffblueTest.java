package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Test;

class CalibrationParserDataItemsDiffblueTest {
  /**
   * Method under test:
   * {@link CalibrationParserDataItems#getScenariosFromJsonFile(String)}
   */
  @Test
  void testGetScenariosFromJsonFile() throws IOException {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> CalibrationParserDataItems.getScenariosFromJsonFile(""));
  }

  /**
   * Method under test:
   * {@link CalibrationParserDataItems#getScenariosFromCSVFile(String)}
   */
  @Test
  void testGetScenariosFromCSVFile() throws IOException {
    // Arrange, Act and Assert
    assertThrows(IOException.class, () -> CalibrationParserDataItems.getScenariosFromCSVFile("foo.txt"));
  }

  /**
   * Method under test:
   * {@link CalibrationParserDataItems#getScenariosFromJsonString(String)}
   */
  @Test
  void testGetScenariosFromJsonString() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> CalibrationParserDataItems.getScenariosFromJsonString("Not all who wander are lost"));
    assertThrows(IllegalArgumentException.class, () -> CalibrationParserDataItems.getScenariosFromJsonString("1D"));
    assertThrows(IllegalArgumentException.class, () -> CalibrationParserDataItems.getScenariosFromJsonString("42"));
    assertThrows(IllegalArgumentException.class, () -> CalibrationParserDataItems.getScenariosFromJsonString(""));
  }

  /**
   * Method under test:
   * {@link CalibrationParserDataItems#getScenariosFromJsonString(String)}
   */
  @Test
  void testGetScenariosFromJsonString2() throws JsonProcessingException {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();

    // Act
    List<CalibrationDataset> actualScenariosFromJsonString = CalibrationParserDataItems
        .getScenariosFromJsonString(objectMapper.writeValueAsString(new HashMap<>()));

    // Assert
    assertTrue(actualScenariosFromJsonString.isEmpty());
  }
}
