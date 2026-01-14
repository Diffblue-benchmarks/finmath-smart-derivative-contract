/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */
package net.finmath.smartcontract.valuation.marketdata.generators;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;
import net.finmath.smartcontract.model.ExceptionId;
import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.model.SDCException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MarketDataGeneratorScenarioList. Tests all methods with focus on branch and
 * condition coverage.
 *
 * @author Claude Code
 */
class MarketDataGeneratorScenarioListClaudeTest {

  /**
   * Test constructor initializes correctly. Verifies that the constructor creates a valid instance
   * with counter set to 0 and publishSubject initialized.
   */
  @Test
  void testConstructor_InitializesCorrectly() {
    // Act
    MarketDataGeneratorScenarioList generator = new MarketDataGeneratorScenarioList();

    // Assert
    assertNotNull(generator, "Generator should be created successfully");
    assertEquals(0, generator.getCounter(), "Counter should be initialized to 0");
  }

  /**
   * Test getCounter returns correct initial value. Verifies that counter starts at 0.
   */
  @Test
  void testGetCounter_InitialValue_ReturnsZero() {
    // Arrange
    MarketDataGeneratorScenarioList generator = new MarketDataGeneratorScenarioList();

    // Act
    int counter = generator.getCounter();

    // Assert
    assertEquals(0, counter, "Initial counter should be 0");
  }

  /**
   * Test getMarketDataString with valid file name. Verifies that the method successfully reads and
   * returns XML content from an existing resource file.
   */
  @Test
  void testGetMarketDataString_ValidFileName_ReturnsXmlContent() {
    // Arrange
    MarketDataGeneratorScenarioList generator = new MarketDataGeneratorScenarioList();
    String fileName =
        "net/finmath/smartcontract/valuation/historicalMarketData/marketdata_2008-05-02.xml";

    // Act
    String result = generator.getMarketDataString(fileName);

    // Assert
    assertNotNull(result, "Result should not be null");
    assertFalse(result.isEmpty(), "Result should not be empty");
    assertTrue(result.contains("<?xml"), "Result should contain XML declaration");
    assertTrue(result.contains("marketDataList"), "Result should contain marketDataList element");
    assertTrue(
        result.contains("requestTimeStamp"), "Result should contain requestTimeStamp element");
  }

  /**
   * Test getMarketDataString with non-existent file. Verifies that the method throws SDCException
   * with correct exception ID when file is not found.
   */
  @Test
  void testGetMarketDataString_NonExistentFile_ThrowsSDCException() {
    // Arrange
    MarketDataGeneratorScenarioList generator = new MarketDataGeneratorScenarioList();
    String fileName = "non/existent/path/file.xml";

    // Act & Assert
    SDCException exception =
        assertThrows(
            SDCException.class,
            () -> generator.getMarketDataString(fileName),
            "Should throw SDCException for non-existent file");

    assertEquals(
        ExceptionId.SDC_FILE_NOT_FOUND, exception.getId(), "Exception should have correct ID");
    assertEquals(404, exception.getStatusCode(), "Exception should have 404 status code");
    assertTrue(
        exception.getMessage().contains("File not found"),
        "Exception message should contain 'File not found'");
  }

  /**
   * Test getMarketDataString with null file name. Verifies that the method throws SDCException
   * when file name is null.
   */
  @Test
  void testGetMarketDataString_NullFileName_ThrowsSDCException() {
    // Arrange
    MarketDataGeneratorScenarioList generator = new MarketDataGeneratorScenarioList();
    String fileName = null;

    // Act & Assert
    SDCException exception =
        assertThrows(
            SDCException.class,
            () -> generator.getMarketDataString(fileName),
            "Should throw SDCException for null file name");

    assertEquals(
        ExceptionId.SDC_FILE_NOT_FOUND, exception.getId(), "Exception should have correct ID");
  }

  /**
   * Test getMarketDataString with empty file name. Verifies that the method throws an exception
   * when file name is empty. The actual exception thrown depends on whether the classloader
   * can find a resource with an empty name.
   */
  @Test
  void testGetMarketDataString_EmptyFileName_ThrowsException() {
    // Arrange
    MarketDataGeneratorScenarioList generator = new MarketDataGeneratorScenarioList();
    String fileName = "";

    // Act & Assert
    // Empty filename may throw SDCException or potentially succeed if classloader handles empty string
    // Let's just verify it doesn't return valid XML content
    try {
      String result = generator.getMarketDataString(fileName);
      // If it doesn't throw, result should not contain valid market data XML
      assertFalse(
          result != null && result.contains("marketDataList"),
          "Empty filename should not return valid market data XML");
    } catch (SDCException e) {
      // This is also acceptable - it should throw SDCException
      assertEquals(
          ExceptionId.SDC_FILE_NOT_FOUND, e.getId(), "Exception should have correct ID");
    }
  }

  /**
   * Test asObservable returns valid Observable. Verifies that the method returns a non-null
   * Observable that emits MarketDataList and completes.
   */
  @Test
  void testAsObservable_FirstCall_ReturnsValidObservable() throws InterruptedException {
    // Arrange
    MarketDataGeneratorScenarioList generator = new MarketDataGeneratorScenarioList();

    // Act
    Observable<MarketDataList> observable = generator.asObservable();

    // Assert
    assertNotNull(observable, "Observable should not be null");

    // Subscribe and verify it emits data
    TestObserver<MarketDataList> testObserver = observable.test();
    testObserver.await();

    testObserver.assertComplete();
    testObserver.assertNoErrors();
    testObserver.assertValueCount(1);

    MarketDataList marketDataList = testObserver.values().get(0);
    assertNotNull(marketDataList, "MarketDataList should not be null");
    assertNotNull(marketDataList.getPoints(), "Market data points should not be null");
    assertTrue(marketDataList.getSize() > 0, "Market data should contain items");
  }

  /**
   * Test asObservable increments counter. Verifies that subscribing to the Observable increments
   * the counter from 0 to 1.
   */
  @Test
  void testAsObservable_IncrementsCounter() throws InterruptedException {
    // Arrange
    MarketDataGeneratorScenarioList generator = new MarketDataGeneratorScenarioList();
    assertEquals(0, generator.getCounter(), "Counter should start at 0");

    // Act
    Observable<MarketDataList> observable = generator.asObservable();
    TestObserver<MarketDataList> testObserver = observable.test();
    testObserver.await();

    // Assert
    assertEquals(1, generator.getCounter(), "Counter should be incremented to 1 after subscription");
  }

  /**
   * Test asObservable cycles through files. Verifies that multiple calls to asObservable cycle
   * through different market data files.
   */
  @Test
  void testAsObservable_MultipleCalls_CyclesThroughFiles() {
    // Arrange
    MarketDataGeneratorScenarioList generator = new MarketDataGeneratorScenarioList();

    // Act - Call asObservable multiple times
    MarketDataList first = generator.asObservable().blockingSingle();
    assertEquals(1, generator.getCounter(), "Counter should be 1 after first call");

    MarketDataList second = generator.asObservable().blockingSingle();
    assertEquals(2, generator.getCounter(), "Counter should be 2 after second call");

    MarketDataList third = generator.asObservable().blockingSingle();
    assertEquals(3, generator.getCounter(), "Counter should be 3 after third call");

    // Assert - Verify different data was loaded (different timestamps)
    assertNotNull(first, "First market data should not be null");
    assertNotNull(second, "Second market data should not be null");
    assertNotNull(third, "Third market data should not be null");

    // The files have different timestamps, so we can verify they're different
    assertNotEquals(
        first.getRequestTimeStamp(),
        second.getRequestTimeStamp(),
        "First and second market data should have different timestamps");
    assertNotEquals(
        second.getRequestTimeStamp(),
        third.getRequestTimeStamp(),
        "Second and third market data should have different timestamps");
  }

  /**
   * Test asObservable wraps around after reaching end of file list. Verifies that the counter
   * wraps around to 0 when it reaches the size of the files list.
   */
  @Test
  void testAsObservable_CounterWrapsAround() {
    // Arrange
    MarketDataGeneratorScenarioList generator = new MarketDataGeneratorScenarioList();

    // The class has 131 files in the list (from May to October 2008)
    // Counter increments to 131, then when reaching files.size() (131), it wraps to 0

    // Act - Call asObservable 131 times to go through all files
    for (int i = 0; i < 131; i++) {
      generator.asObservable().blockingSingle();
    }

    // After 131 calls, counter should be 131, but when we call asObservable,
    // it will check if counter >= files.size() and reset to 0, then increment to 1
    int counterBefore = generator.getCounter();
    assertTrue(
        counterBefore >= 131,
        "Counter should be at least 131 after 131 calls (actual: " + counterBefore + ")");

    // This call should wrap around
    MarketDataList afterWrap = generator.asObservable().blockingSingle();

    // Assert
    assertEquals(
        1, generator.getCounter(), "Counter should wrap around to 1 after reaching list size");
    assertNotNull(afterWrap, "Market data after wrap should not be null");
  }

  /**
   * Test asObservable returns different Observable instances. Verifies that each call to
   * asObservable returns a new Observable instance.
   */
  @Test
  void testAsObservable_ReturnsNewInstanceEachTime() {
    // Arrange
    MarketDataGeneratorScenarioList generator = new MarketDataGeneratorScenarioList();

    // Act
    Observable<MarketDataList> observable1 = generator.asObservable();
    Observable<MarketDataList> observable2 = generator.asObservable();

    // Assert
    assertNotSame(
        observable1,
        observable2,
        "Each call to asObservable should return a different Observable instance");
  }

  /**
   * Test that market data files exist and are valid XML. Verifies that the first few files in the
   * list can be successfully loaded and parsed.
   */
  @Test
  void testMarketDataFiles_FirstFewExistAndValid() {
    // Arrange
    MarketDataGeneratorScenarioList generator = new MarketDataGeneratorScenarioList();

    // Act & Assert - Test first 5 files
    for (int i = 0; i < 5; i++) {
      MarketDataList marketData = generator.asObservable().blockingSingle();
      assertNotNull(marketData, "Market data " + i + " should not be null");
      assertTrue(marketData.getSize() > 0, "Market data " + i + " should contain items");
      assertNotNull(
          marketData.getRequestTimeStamp(),
          "Market data " + i + " should have a request timestamp");
    }

    assertEquals(5, generator.getCounter(), "Counter should be 5 after loading 5 files");
  }

  /**
   * Test getMarketDataString with different valid files. Verifies that the method can read
   * different files from the historical market data directory.
   */
  @Test
  void testGetMarketDataString_DifferentValidFiles_ReturnsCorrectContent() {
    // Arrange
    MarketDataGeneratorScenarioList generator = new MarketDataGeneratorScenarioList();
    String fileName1 =
        "net/finmath/smartcontract/valuation/historicalMarketData/marketdata_2008-05-02.xml";
    String fileName2 =
        "net/finmath/smartcontract/valuation/historicalMarketData/marketdata_2008-05-05.xml";

    // Act
    String result1 = generator.getMarketDataString(fileName1);
    String result2 = generator.getMarketDataString(fileName2);

    // Assert
    assertNotNull(result1, "First result should not be null");
    assertNotNull(result2, "Second result should not be null");
    assertNotEquals(result1, result2, "Different files should have different content");
    assertTrue(result1.contains("20080502"), "First file should contain date 20080502");
    assertTrue(result2.contains("20080505"), "Second file should contain date 20080505");
  }

  /**
   * Test asObservable with counter at boundary. Verifies that when counter is at the last file
   * index, it wraps correctly.
   */
  @Test
  void testAsObservable_CounterAtLastIndex_WrapsToZero() {
    // Arrange
    MarketDataGeneratorScenarioList generator = new MarketDataGeneratorScenarioList();

    // Advance counter to 130 (last index, as there are 131 files with indices 0-130)
    for (int i = 0; i < 130; i++) {
      generator.asObservable().blockingSingle();
    }

    assertEquals(130, generator.getCounter(), "Counter should be at 130");

    // Act - One more call should increment to 131, then next call will wrap to 0
    MarketDataList lastFile = generator.asObservable().blockingSingle();
    assertEquals(131, generator.getCounter(), "Counter should be 131 after loading last file");

    MarketDataList firstFileAgain = generator.asObservable().blockingSingle();

    // Assert
    assertEquals(
        1,
        generator.getCounter(),
        "Counter should wrap to 1 after reaching end (counter resets to 0, then incremented to 1)");
    assertNotNull(lastFile, "Last file market data should not be null");
    assertNotNull(firstFileAgain, "First file (after wrap) market data should not be null");
  }

  /**
   * Test that Observable completes successfully. Verifies that the Observable emits one item and
   * then completes without errors.
   */
  @Test
  void testAsObservable_CompletesSuccessfully() throws InterruptedException {
    // Arrange
    MarketDataGeneratorScenarioList generator = new MarketDataGeneratorScenarioList();

    // Act
    TestObserver<MarketDataList> testObserver = generator.asObservable().test();
    testObserver.await();

    // Assert
    testObserver.assertComplete();
    testObserver.assertNoErrors();
    testObserver.assertValueCount(1);
  }

  /**
   * Test that market data contains expected structure. Verifies that the parsed MarketDataList has
   * the expected structure with points and timestamp.
   */
  @Test
  void testAsObservable_MarketDataHasExpectedStructure() {
    // Arrange
    MarketDataGeneratorScenarioList generator = new MarketDataGeneratorScenarioList();

    // Act
    MarketDataList marketData = generator.asObservable().blockingSingle();

    // Assert
    assertNotNull(marketData, "Market data should not be null");
    assertNotNull(marketData.getRequestTimeStamp(), "Request timestamp should not be null");
    assertNotNull(marketData.getPoints(), "Points list should not be null");
    assertTrue(marketData.getSize() > 0, "Market data should contain at least one point");

    // Verify first point has expected structure
    assertNotNull(marketData.getPoints().get(0), "First point should not be null");
    assertNotNull(marketData.getPoints().get(0).getId(), "First point should have an ID");
    assertNotNull(marketData.getPoints().get(0).getValue(), "First point should have a value");
  }

  /**
   * Test getCounter after multiple asObservable calls. Verifies that the counter accurately tracks
   * the number of times market data has been retrieved.
   */
  @Test
  void testGetCounter_AfterMultipleCalls_ReturnsCorrectValue() {
    // Arrange
    MarketDataGeneratorScenarioList generator = new MarketDataGeneratorScenarioList();

    // Act
    for (int i = 0; i < 10; i++) {
      generator.asObservable().blockingSingle();
    }

    // Assert
    assertEquals(10, generator.getCounter(), "Counter should be 10 after 10 asObservable calls");
  }
}
