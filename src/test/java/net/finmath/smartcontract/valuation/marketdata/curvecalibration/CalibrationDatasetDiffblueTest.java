package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem.Spec;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CalibrationDatasetDiffblueTest {
  @InjectMocks
  private CalibrationDataset calibrationDataset;

  @Mock
  private Set<CalibrationDataItem> set;

  /**
   * Test {@link CalibrationDataset#CalibrationDataset(Set, LocalDateTime)}.
   * <ul>
   *   <li>Then return CalibrationDataItems is {@link HashSet#HashSet()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CalibrationDataset#CalibrationDataset(Set, LocalDateTime)}
   */
  @Test
  @DisplayName("Test new CalibrationDataset(Set, LocalDateTime); then return CalibrationDataItems is HashSet()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CalibrationDataset.<init>(Set, LocalDateTime)"})
  void testNewCalibrationDataset_thenReturnCalibrationDataItemsIsHashSet() {
    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    Spec spec = new Spec("Key", "Curve Name", "Product Name", "Maturity");

    curveDataPointSet.add(new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    LocalDateTime scenarioDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    CalibrationDataset actualCalibrationDataset = new CalibrationDataset(curveDataPointSet, scenarioDate);

    // Assert
    assertTrue(actualCalibrationDataset.getFixingDataItems().isEmpty());
    assertEquals(curveDataPointSet, actualCalibrationDataset.getCalibrationDataItems());
    assertEquals(curveDataPointSet, actualCalibrationDataset.getDataPoints());
    assertSame(scenarioDate, actualCalibrationDataset.getDate());
  }

  /**
   * Test {@link CalibrationDataset#CalibrationDataset(Set, LocalDateTime)}.
   * <ul>
   *   <li>Then return FixingDataItems is {@link HashSet#HashSet()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CalibrationDataset#CalibrationDataset(Set, LocalDateTime)}
   */
  @Test
  @DisplayName("Test new CalibrationDataset(Set, LocalDateTime); then return FixingDataItems is HashSet()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CalibrationDataset.<init>(Set, LocalDateTime)"})
  void testNewCalibrationDataset_thenReturnFixingDataItemsIsHashSet() {
    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    Spec spec = new Spec("Key", "Curve Name", "Fixing", "Maturity");

    curveDataPointSet.add(new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    LocalDateTime scenarioDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    CalibrationDataset actualCalibrationDataset = new CalibrationDataset(curveDataPointSet, scenarioDate);

    // Assert
    assertTrue(actualCalibrationDataset.getCalibrationDataItems().isEmpty());
    assertEquals(curveDataPointSet, actualCalibrationDataset.getDataPoints());
    assertEquals(curveDataPointSet, actualCalibrationDataset.getFixingDataItems());
    assertSame(scenarioDate, actualCalibrationDataset.getDate());
  }

  /**
   * Test {@link CalibrationDataset#CalibrationDataset(Set, LocalDateTime)}.
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.</li>
   *   <li>Then return DataPoints Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link CalibrationDataset#CalibrationDataset(Set, LocalDateTime)}
   */
  @Test
  @DisplayName("Test new CalibrationDataset(Set, LocalDateTime); when HashSet(); then return DataPoints Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CalibrationDataset.<init>(Set, LocalDateTime)"})
  void testNewCalibrationDataset_whenHashSet_thenReturnDataPointsEmpty() {
    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    LocalDateTime scenarioDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    CalibrationDataset actualCalibrationDataset = new CalibrationDataset(curveDataPointSet, scenarioDate);

    // Assert
    assertTrue(actualCalibrationDataset.getCalibrationDataItems().isEmpty());
    assertTrue(actualCalibrationDataset.getDataPoints().isEmpty());
    assertTrue(actualCalibrationDataset.getFixingDataItems().isEmpty());
    assertSame(scenarioDate, actualCalibrationDataset.getDate());
  }

  /**
   * Test {@link CalibrationDataset#getScaled(double)}.
   * <p>
   * Method under test: {@link CalibrationDataset#getScaled(double)}
   */
  @Test
  @DisplayName("Test getScaled(double)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"CalibrationDataset CalibrationDataset.getScaled(double)"})
  void testGetScaled() {
    // Arrange and Act
    CalibrationDataset actualScaled = calibrationDataset.getScaled(10.0d);

    // Assert
    assertNull(actualScaled.getDate());
    assertTrue(actualScaled.getCalibrationDataItems().isEmpty());
    assertTrue(actualScaled.getDataPoints().isEmpty());
    assertTrue(actualScaled.getFixingDataItems().isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CalibrationDataset#getCalibrationDataItems()}
   *   <li>{@link CalibrationDataset#getDate()}
   *   <li>{@link CalibrationDataset#getFixingDataItems()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Set CalibrationDataset.getCalibrationDataItems()", "LocalDateTime CalibrationDataset.getDate()",
      "Set CalibrationDataset.getFixingDataItems()"})
  void testGettersAndSetters() {
    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    CalibrationDataset calibrationDataset = new CalibrationDataset(curveDataPointSet,
        LocalDate.of(1970, 1, 1).atStartOfDay());

    // Act
    Set<CalibrationDataItem> actualCalibrationDataItems = calibrationDataset.getCalibrationDataItems();
    LocalDateTime actualDate = calibrationDataset.getDate();
    Set<CalibrationDataItem> actualFixingDataItems = calibrationDataset.getFixingDataItems();

    // Assert
    assertTrue(actualCalibrationDataItems.isEmpty());
    assertTrue(actualFixingDataItems.isEmpty());
    assertSame(calibrationDataset.scenarioDate, actualDate);
  }

  /**
   * Test {@link CalibrationDataset#getClonedFixingsAdded(Set)}.
   * <p>
   * Method under test: {@link CalibrationDataset#getClonedFixingsAdded(Set)}
   */
  @Test
  @DisplayName("Test getClonedFixingsAdded(Set)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"CalibrationDataset CalibrationDataset.getClonedFixingsAdded(Set)"})
  void testGetClonedFixingsAdded() {
    // Arrange
    HashSet<CalibrationDataItem> newFixingDataItems = new HashSet<>();
    Spec spec = new Spec("Key", "Curve Name", "Fixing", "Maturity");

    newFixingDataItems.add(new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));

    // Act
    CalibrationDataset actualClonedFixingsAdded = calibrationDataset.getClonedFixingsAdded(newFixingDataItems);

    // Assert
    assertNull(actualClonedFixingsAdded.getDate());
    assertTrue(actualClonedFixingsAdded.getCalibrationDataItems().isEmpty());
    assertEquals(newFixingDataItems, actualClonedFixingsAdded.getDataPoints());
    assertEquals(newFixingDataItems, actualClonedFixingsAdded.getFixingDataItems());
  }

  /**
   * Test {@link CalibrationDataset#getClonedFixingsAdded(Set)}.
   * <p>
   * Method under test: {@link CalibrationDataset#getClonedFixingsAdded(Set)}
   */
  @Test
  @DisplayName("Test getClonedFixingsAdded(Set)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"CalibrationDataset CalibrationDataset.getClonedFixingsAdded(Set)"})
  void testGetClonedFixingsAdded2() {
    // Arrange
    HashSet<CalibrationDataItem> newFixingDataItems = new HashSet<>();
    Spec spec = new Spec("net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem$Spec",
        "Curve Name", "Product Name", "Maturity");

    newFixingDataItems.add(new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    Spec spec2 = new Spec("Key", "Curve Name", "Product Name", "Maturity");

    newFixingDataItems.add(new CalibrationDataItem(spec2, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));

    // Act
    CalibrationDataset actualClonedFixingsAdded = calibrationDataset.getClonedFixingsAdded(newFixingDataItems);

    // Assert
    assertNull(actualClonedFixingsAdded.getDate());
    assertTrue(actualClonedFixingsAdded.getCalibrationDataItems().isEmpty());
    assertTrue(actualClonedFixingsAdded.getDataPoints().isEmpty());
    assertTrue(actualClonedFixingsAdded.getFixingDataItems().isEmpty());
  }

  /**
   * Test {@link CalibrationDataset#getClonedFixingsAdded(Set)}.
   * <p>
   * Method under test: {@link CalibrationDataset#getClonedFixingsAdded(Set)}
   */
  @Test
  @DisplayName("Test getClonedFixingsAdded(Set)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"CalibrationDataset CalibrationDataset.getClonedFixingsAdded(Set)"})
  void testGetClonedFixingsAdded3() {
    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getProductName()).thenReturn("Product Name");

    HashSet<CalibrationDataItem> newFixingDataItems = new HashSet<>();
    newFixingDataItems.add(calibrationDataItem);

    // Act
    CalibrationDataset actualClonedFixingsAdded = calibrationDataset.getClonedFixingsAdded(newFixingDataItems);

    // Assert
    verify(calibrationDataItem).getProductName();
    assertNull(actualClonedFixingsAdded.getDate());
    assertTrue(actualClonedFixingsAdded.getCalibrationDataItems().isEmpty());
    assertTrue(actualClonedFixingsAdded.getDataPoints().isEmpty());
    assertTrue(actualClonedFixingsAdded.getFixingDataItems().isEmpty());
  }

  /**
   * Test {@link CalibrationDataset#getClonedFixingsAdded(Set)}.
   * <ul>
   *   <li>Given {@link CalibrationDataItem} {@link CalibrationDataItem#getProductName()} return {@code Fixing}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CalibrationDataset#getClonedFixingsAdded(Set)}
   */
  @Test
  @DisplayName("Test getClonedFixingsAdded(Set); given CalibrationDataItem getProductName() return 'Fixing'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"CalibrationDataset CalibrationDataset.getClonedFixingsAdded(Set)"})
  void testGetClonedFixingsAdded_givenCalibrationDataItemGetProductNameReturnFixing() {
    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getProductName()).thenReturn("Fixing");

    HashSet<CalibrationDataItem> newFixingDataItems = new HashSet<>();
    newFixingDataItems.add(calibrationDataItem);

    // Act
    CalibrationDataset actualClonedFixingsAdded = calibrationDataset.getClonedFixingsAdded(newFixingDataItems);

    // Assert
    verify(calibrationDataItem, atLeast(1)).getProductName();
    assertNull(actualClonedFixingsAdded.getDate());
    assertTrue(actualClonedFixingsAdded.getCalibrationDataItems().isEmpty());
    assertEquals(newFixingDataItems, actualClonedFixingsAdded.getDataPoints());
    assertEquals(newFixingDataItems, actualClonedFixingsAdded.getFixingDataItems());
  }

  /**
   * Test {@link CalibrationDataset#getClonedFixingsAdded(Set)}.
   * <ul>
   *   <li>Given {@link Spec#Spec(String, String, String, String)} with {@code Key} and {@code Curve Name} and {@code Product Name} and {@code Maturity}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CalibrationDataset#getClonedFixingsAdded(Set)}
   */
  @Test
  @DisplayName("Test getClonedFixingsAdded(Set); given Spec(String, String, String, String) with 'Key' and 'Curve Name' and 'Product Name' and 'Maturity'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"CalibrationDataset CalibrationDataset.getClonedFixingsAdded(Set)"})
  void testGetClonedFixingsAdded_givenSpecWithKeyAndCurveNameAndProductNameAndMaturity() {
    // Arrange
    HashSet<CalibrationDataItem> newFixingDataItems = new HashSet<>();
    Spec spec = new Spec("Key", "Curve Name", "Product Name", "Maturity");

    newFixingDataItems.add(new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));

    // Act
    CalibrationDataset actualClonedFixingsAdded = calibrationDataset.getClonedFixingsAdded(newFixingDataItems);

    // Assert
    assertNull(actualClonedFixingsAdded.getDate());
    assertTrue(actualClonedFixingsAdded.getCalibrationDataItems().isEmpty());
    assertTrue(actualClonedFixingsAdded.getDataPoints().isEmpty());
    assertTrue(actualClonedFixingsAdded.getFixingDataItems().isEmpty());
  }

  /**
   * Test {@link CalibrationDataset#getClonedFixingsAdded(Set)}.
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.</li>
   *   <li>Then return DataPoints Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link CalibrationDataset#getClonedFixingsAdded(Set)}
   */
  @Test
  @DisplayName("Test getClonedFixingsAdded(Set); when HashSet(); then return DataPoints Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"CalibrationDataset CalibrationDataset.getClonedFixingsAdded(Set)"})
  void testGetClonedFixingsAdded_whenHashSet_thenReturnDataPointsEmpty() {
    // Arrange and Act
    CalibrationDataset actualClonedFixingsAdded = calibrationDataset.getClonedFixingsAdded(new HashSet<>());

    // Assert
    assertNull(actualClonedFixingsAdded.getDate());
    assertTrue(actualClonedFixingsAdded.getCalibrationDataItems().isEmpty());
    assertTrue(actualClonedFixingsAdded.getDataPoints().isEmpty());
    assertTrue(actualClonedFixingsAdded.getFixingDataItems().isEmpty());
  }

  /**
   * Test {@link CalibrationDataset#toMarketDataList()}.
   * <p>
   * Method under test: {@link CalibrationDataset#toMarketDataList()}
   */
  @Test
  @DisplayName("Test toMarketDataList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"MarketDataList CalibrationDataset.toMarketDataList()"})
  void testToMarketDataList() {
    // Arrange and Act
    MarketDataList actualToMarketDataListResult = calibrationDataset.toMarketDataList();

    // Assert
    assertEquals(0, actualToMarketDataListResult.getSize());
    assertTrue(actualToMarketDataListResult.getPoints().isEmpty());
  }

  /**
   * Test {@link CalibrationDataset#serializeToJson()}.
   * <ul>
   *   <li>Then calls {@link CalibrationDataItem#getDaysToMaturity()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CalibrationDataset#serializeToJson()}
   */
  @Test
  @DisplayName("Test serializeToJson(); then calls getDaysToMaturity()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.lang.String CalibrationDataset.serializeToJson()"})
  void testSerializeToJson_thenCallsGetDaysToMaturity() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getQuote()).thenReturn(10.0d);
    when(calibrationDataItem.getSpec()).thenReturn(new Spec("Key", "Curve Name", "Product Name", "Maturity"));
    when(calibrationDataItem.getDaysToMaturity()).thenReturn(1);
    when(calibrationDataItem.getProductName()).thenReturn("Product Name");
    curveDataPointSet.add(calibrationDataItem);
    CalibrationDataItem calibrationDataItem2 = mock(CalibrationDataItem.class);
    when(calibrationDataItem2.getQuote()).thenReturn(10.0d);
    when(calibrationDataItem2.getDaysToMaturity()).thenReturn(1);
    when(calibrationDataItem2.getSpec()).thenReturn(new Spec("Key", "Curve Name", "Product Name", "Maturity"));
    when(calibrationDataItem2.getProductName()).thenReturn("Product Name");
    curveDataPointSet.add(calibrationDataItem2);

    // Act
    (new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay())).serializeToJson();

    // Assert
    verify(calibrationDataItem).getDaysToMaturity();
    verify(calibrationDataItem2).getDaysToMaturity();
    verify(calibrationDataItem, atLeast(1)).getProductName();
    verify(calibrationDataItem2, atLeast(1)).getProductName();
    verify(calibrationDataItem).getQuote();
    verify(calibrationDataItem2).getQuote();
    verify(calibrationDataItem, atLeast(1)).getSpec();
    verify(calibrationDataItem2, atLeast(1)).getSpec();
  }

  /**
   * Test {@link CalibrationDataset#getDataAsCalibrationDataPointStream(CalibrationParser)}.
   * <p>
   * Method under test: {@link CalibrationDataset#getDataAsCalibrationDataPointStream(CalibrationParser)}
   */
  @Test
  @DisplayName("Test getDataAsCalibrationDataPointStream(CalibrationParser)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Stream CalibrationDataset.getDataAsCalibrationDataPointStream(CalibrationParser)"})
  void testGetDataAsCalibrationDataPointStream() {
    // Arrange
    CalibrationParser parser = mock(CalibrationParser.class);

    ArrayList<CalibrationSpecProvider> calibrationSpecProviderList = new ArrayList<>();
    Stream<CalibrationSpecProvider> streamResult = calibrationSpecProviderList.stream();
    when(parser.parse(Mockito.<Stream<CalibrationDataItem>>any())).thenReturn(streamResult);

    // Act
    Stream<CalibrationSpecProvider> actualDataAsCalibrationDataPointStream = calibrationDataset
        .getDataAsCalibrationDataPointStream(parser);

    // Assert
    verify(parser).parse(isA(Stream.class));
    assertTrue(actualDataAsCalibrationDataPointStream.limit(5).collect(Collectors.toList()).isEmpty());
  }

  /**
   * Test {@link CalibrationDataset#getDataPoints()}.
   * <p>
   * Method under test: {@link CalibrationDataset#getDataPoints()}
   */
  @Test
  @DisplayName("Test getDataPoints()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Set CalibrationDataset.getDataPoints()"})
  void testGetDataPoints() {
    // Arrange, Act and Assert
    assertTrue(calibrationDataset.getDataPoints().isEmpty());
  }
}
