package net.finmath.smartcontract.valuation.marketdata.generators;

import io.reactivex.rxjava3.core.Observable;
import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.model.SDCException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link MarketDataGeneratorScenarioList}.
 * Tests classpath resource loading, counter state, and observable creation.
 */
class MarketDataGeneratorScenarioListTest {

	private MarketDataGeneratorScenarioList generator;

	@BeforeEach
	void setUp() {
		generator = new MarketDataGeneratorScenarioList();
	}

	@Test
	void testGetMarketDataStringWithValidFile() {
		String fileName = "net/finmath/smartcontract/valuation/historicalMarketData/marketdata_2008-05-02.xml";

		String result = generator.getMarketDataString(fileName);

		assertNotNull(result, "Market data string should not be null for a valid file");
		assertFalse(result.isEmpty(), "Market data string should not be empty for a valid file");
	}

	@Test
	void testGetMarketDataStringContainsXmlContent() {
		String fileName = "net/finmath/smartcontract/valuation/historicalMarketData/marketdata_2008-05-02.xml";

		String result = generator.getMarketDataString(fileName);

		assertTrue(result.contains("<?xml") || result.contains("<"), "Market data should contain XML content");
	}

	@Test
	void testGetMarketDataStringWithInvalidFileThrowsSDCException() {
		String invalidFileName = "nonexistent/path/does_not_exist.xml";

		SDCException exception = assertThrows(SDCException.class,
				() -> generator.getMarketDataString(invalidFileName));

		assertNotNull(exception.getMessage());
	}

	@Test
	void testGetCounterStartsAtZero() {
		assertEquals(0, generator.getCounter(), "Counter should start at 0");
	}

	@Test
	void testAsObservableReturnsNonNull() {
		Observable<MarketDataList> observable = generator.asObservable();

		assertNotNull(observable, "asObservable() should return a non-null Observable");
	}
}
