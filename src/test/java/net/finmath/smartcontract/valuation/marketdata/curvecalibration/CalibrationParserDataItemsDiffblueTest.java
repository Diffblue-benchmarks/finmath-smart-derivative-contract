package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CalibrationParserDataItems.class})
@ExtendWith(SpringExtension.class)
class CalibrationParserDataItemsDiffblueTest {
  @Autowired private CalibrationParserDataItems calibrationParserDataItems;

  /**
   * Test {@link CalibrationParserDataItems#parse(Stream)}.
   *
   * <p>Method under test: {@link CalibrationParserDataItems#parse(Stream)}
   */
  @Test
  @DisplayName("Test parse(Stream)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Stream CalibrationParserDataItems.parse(Stream)"})
  void testParse() {
    // Arrange
    ArrayList<CalibrationDataItem> calibrationDataItemList = new ArrayList<>();
    Stream<CalibrationDataItem> datapoints = calibrationDataItemList.stream();

    // Act
    Stream<CalibrationSpecProvider> actualParseResult =
        calibrationParserDataItems.parse(datapoints);

    // Assert
    assertTrue(actualParseResult.limit(5).collect(Collectors.toList()).isEmpty());
  }

  /**
   * Test {@link CalibrationParserDataItems#getScenariosFromJsonFile(String)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationParserDataItems#getScenariosFromJsonFile(String)}
   */
  @Test
  @DisplayName(
      "Test getScenariosFromJsonFile(String); when empty string; then throw IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List CalibrationParserDataItems.getScenariosFromJsonFile(String)"})
  void testGetScenariosFromJsonFile_whenEmptyString_thenThrowIllegalArgumentException()
      throws IOException {
    // Arrange, Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> CalibrationParserDataItems.getScenariosFromJsonFile(""));
  }

  /**
   * Test {@link CalibrationParserDataItems#getScenariosFromCSVFile(String)}.
   *
   * <p>Method under test: {@link CalibrationParserDataItems#getScenariosFromCSVFile(String)}
   */
  @Test
  @DisplayName("Test getScenariosFromCSVFile(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List CalibrationParserDataItems.getScenariosFromCSVFile(String)"})
  void testGetScenariosFromCSVFile() throws IOException {
    // Arrange, Act and Assert
    assertThrows(
        IOException.class, () -> CalibrationParserDataItems.getScenariosFromCSVFile("foo.txt"));
  }

  /**
   * Test {@link CalibrationParserDataItems#getScenariosFromJsonString(String)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationParserDataItems#getScenariosFromJsonString(String)}
   */
  @Test
  @DisplayName("Test getScenariosFromJsonString(String); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List CalibrationParserDataItems.getScenariosFromJsonString(String)"})
  void testGetScenariosFromJsonString_thenReturnEmpty() throws JsonProcessingException {
    // Arrange
    JsonMapper buildResult = JsonMapper.builder().findAndAddModules().build();

    // Act
    List<CalibrationDataset> actualScenariosFromJsonString =
        CalibrationParserDataItems.getScenariosFromJsonString(
            buildResult.writeValueAsString(new HashMap<>()));

    // Assert
    assertTrue(actualScenariosFromJsonString.isEmpty());
  }

  /**
   * Test {@link CalibrationParserDataItems#getScenariosFromJsonString(String)}.
   *
   * <ul>
   *   <li>When {@code 1D}.
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationParserDataItems#getScenariosFromJsonString(String)}
   */
  @Test
  @DisplayName(
      "Test getScenariosFromJsonString(String); when '1D'; then throw IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List CalibrationParserDataItems.getScenariosFromJsonString(String)"})
  void testGetScenariosFromJsonString_when1d_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> CalibrationParserDataItems.getScenariosFromJsonString("1D"));
  }

  /**
   * Test {@link CalibrationParserDataItems#getScenariosFromJsonString(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationParserDataItems#getScenariosFromJsonString(String)}
   */
  @Test
  @DisplayName(
      "Test getScenariosFromJsonString(String); when '42'; then throw IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List CalibrationParserDataItems.getScenariosFromJsonString(String)"})
  void testGetScenariosFromJsonString_when42_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> CalibrationParserDataItems.getScenariosFromJsonString("42"));
  }

  /**
   * Test {@link CalibrationParserDataItems#getScenariosFromJsonString(String)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationParserDataItems#getScenariosFromJsonString(String)}
   */
  @Test
  @DisplayName(
      "Test getScenariosFromJsonString(String); when empty string; then throw IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List CalibrationParserDataItems.getScenariosFromJsonString(String)"})
  void testGetScenariosFromJsonString_whenEmptyString_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> CalibrationParserDataItems.getScenariosFromJsonString(""));
  }

  /**
   * Test {@link CalibrationParserDataItems#getScenariosFromJsonString(String)}.
   *
   * <ul>
   *   <li>When {@code Not all who wander are lost}.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationParserDataItems#getScenariosFromJsonString(String)}
   */
  @Test
  @DisplayName("Test getScenariosFromJsonString(String); when 'Not all who wander are lost'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List CalibrationParserDataItems.getScenariosFromJsonString(String)"})
  void testGetScenariosFromJsonString_whenNotAllWhoWanderAreLost() {
    // Arrange, Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> CalibrationParserDataItems.getScenariosFromJsonString("Not all who wander are lost"));
  }
}
