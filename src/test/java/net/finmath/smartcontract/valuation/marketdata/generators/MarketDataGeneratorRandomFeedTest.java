package net.finmath.smartcontract.valuation.marketdata.generators;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;
import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.product.SmartDerivativeContractDescriptor;
import net.finmath.smartcontract.product.xml.SDCXMLParser;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataset;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class MarketDataGeneratorRandomFeedTest {

	static String marketDataXml;
	static List<CalibrationDataItem.Spec> mdSpecs;

	@BeforeAll
	static void setUp() throws Exception {
		String productData = new String(
				MarketDataGeneratorRandomFeedTest.class.getClassLoader()
						.getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract.xml")
						.readAllBytes(), StandardCharsets.UTF_8);
		SmartDerivativeContractDescriptor descriptor = SDCXMLParser.parse(productData);
		mdSpecs = descriptor.getMarketdataItemList();

		marketDataXml = new String(
				MarketDataGeneratorRandomFeedTest.class.getClassLoader()
						.getResourceAsStream("net/finmath/smartcontract/valuation/client/md_testset_with_fixings.xml")
						.readAllBytes(), StandardCharsets.UTF_8);
	}

	@Test
	void testConstructor() throws Exception {
		MarketDataGeneratorRandomFeed feed = new MarketDataGeneratorRandomFeed(
				Period.ofDays(1), marketDataXml, mdSpecs);

		assertNotNull(feed);
		assertNotNull(feed.referenceSet);
		assertNotNull(feed.endTime);
		assertEquals(3, feed.simulationFrequencySec);
	}

	@Test
	void testAsObservableCompletesWithZeroPeriod() throws Exception {
		MarketDataGeneratorRandomFeed feed = new MarketDataGeneratorRandomFeed(
				Period.ofDays(0), marketDataXml, mdSpecs);

		// Set simulationFrequencySec to 0 to avoid waiting
		Field freqField = MarketDataGeneratorRandomFeed.class.getDeclaredField("simulationFrequencySec");
		freqField.setAccessible(true);
		freqField.setInt(feed, 0);

		Observable<MarketDataList> observable = feed.asObservable();
		assertNotNull(observable);

		TestObserver<MarketDataList> testObserver = observable.test();
		testObserver.awaitDone(10, TimeUnit.SECONDS);
		testObserver.assertComplete();
		testObserver.assertNoErrors();
	}

	@Test
	void testAsObservableEmitsItems() throws Exception {
		MarketDataGeneratorRandomFeed feed = new MarketDataGeneratorRandomFeed(
				Period.ofDays(0), marketDataXml, mdSpecs);

		// Set endTime slightly in the future so the while loop executes at least once
		Field endTimeField = MarketDataGeneratorRandomFeed.class.getDeclaredField("endTime");
		endTimeField.setAccessible(true);
		endTimeField.set(feed, LocalDateTime.now().plusSeconds(1));

		// Set simulationFrequencySec to 0 to avoid delay
		Field freqField = MarketDataGeneratorRandomFeed.class.getDeclaredField("simulationFrequencySec");
		freqField.setAccessible(true);
		freqField.setInt(feed, 0);

		Observable<MarketDataList> observable = feed.asObservable();
		TestObserver<MarketDataList> testObserver = observable.test();
		testObserver.awaitDone(10, TimeUnit.SECONDS);
		testObserver.assertComplete();
		testObserver.assertNoErrors();

		assertTrue(testObserver.values().size() > 0, "Expected at least one emitted item");
		MarketDataList firstItem = testObserver.values().get(0);
		assertNotNull(firstItem);
	}

	@Test
	void testGetShiftedReferenceSet() throws Exception {
		MarketDataGeneratorRandomFeed feed = new MarketDataGeneratorRandomFeed(
				Period.ofDays(1), marketDataXml, mdSpecs);

		Method method = MarketDataGeneratorRandomFeed.class.getDeclaredMethod("getShiftedReferenceSet");
		method.setAccessible(true);

		CalibrationDataset shifted = (CalibrationDataset) method.invoke(feed);
		assertNotNull(shifted);
		assertNotNull(shifted.getDate());
		assertFalse(shifted.getDataPoints().isEmpty(), "Shifted dataset should contain data points");
	}
}
