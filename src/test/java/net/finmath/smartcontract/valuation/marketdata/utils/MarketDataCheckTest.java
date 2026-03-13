package net.finmath.smartcontract.valuation.marketdata.utils;

import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.product.xml.Smartderivativecontract;
import net.finmath.smartcontract.valuation.marketdata.data.MarketDataPoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link MarketDataCheck}.
 * Verifies static method {@code checkMarketData} behavior for empty data,
 * matching data, and missing data point scenarios.
 */
class MarketDataCheckTest {

	private Smartderivativecontract sdc;
	private Smartderivativecontract.Settlement settlement;
	private Smartderivativecontract.Settlement.Marketdata marketdata;
	private Smartderivativecontract.Settlement.Marketdata.Marketdataitems marketdataitems;

	@BeforeEach
	void setUp() {
		sdc = mock(Smartderivativecontract.class);
		settlement = mock(Smartderivativecontract.Settlement.class);
		marketdata = mock(Smartderivativecontract.Settlement.Marketdata.class);
		marketdataitems = mock(Smartderivativecontract.Settlement.Marketdata.Marketdataitems.class);

		when(sdc.getSettlement()).thenReturn(settlement);
		when(settlement.getMarketdata()).thenReturn(marketdata);
		when(marketdata.getMarketdataitems()).thenReturn(marketdataitems);
	}

	@Test
	@DisplayName("Empty market data list returns errors with 'all, no data provided'")
	void checkMarketData_emptyList_returnsNoDataError() {
		MarketDataList emptyList = new MarketDataList();

		MarketDataErrors errors = MarketDataCheck.checkMarketData(emptyList, sdc);

		assertTrue(errors.hasErrors(), "Expected errors flag to be true for empty market data");
		assertEquals("error in marketData service - no data generated", errors.getErrorMessage());
		assertTrue(errors.getMissingDataPoints().contains("all, no data provided"),
				"Missing data points should contain 'all, no data provided'");
	}

	@Test
	@DisplayName("Market data with all matching points returns no errors")
	void checkMarketData_allPointsPresent_returnsNoErrors() {
		MarketDataList dataList = new MarketDataList();
		LocalDateTime now = LocalDateTime.now();
		dataList.add(new MarketDataPoint("EUR-EURIBOR-6M-Swap-10Y", 0.025, now));
		dataList.add(new MarketDataPoint("EUR-EURIBOR-6M-Swap-5Y", 0.015, now));

		Smartderivativecontract.Settlement.Marketdata.Marketdataitems.Item item1 =
				mock(Smartderivativecontract.Settlement.Marketdata.Marketdataitems.Item.class);
		Smartderivativecontract.Settlement.Marketdata.Marketdataitems.Item item2 =
				mock(Smartderivativecontract.Settlement.Marketdata.Marketdataitems.Item.class);

		when(item1.getSymbol()).thenReturn(List.of("EUR-EURIBOR-6M-Swap-10Y"));
		when(item2.getSymbol()).thenReturn(List.of("EUR-EURIBOR-6M-Swap-5Y"));
		when(marketdataitems.getItem()).thenReturn(List.of(item1, item2));

		MarketDataErrors errors = MarketDataCheck.checkMarketData(dataList, sdc);

		assertFalse(errors.hasErrors(), "Expected no errors when all market data points are present");
		assertNull(errors.getErrorMessage());
		assertTrue(errors.getMissingDataPoints().isEmpty(), "Expected no missing data points");
	}

	@Test
	@DisplayName("Market data with missing points returns errors listing missing IDs")
	void checkMarketData_missingPoints_returnsErrorsWithMissingIds() {
		MarketDataList dataList = new MarketDataList();
		LocalDateTime now = LocalDateTime.now();
		dataList.add(new MarketDataPoint("EUR-EURIBOR-6M-Swap-10Y", 0.025, now));

		Smartderivativecontract.Settlement.Marketdata.Marketdataitems.Item item1 =
				mock(Smartderivativecontract.Settlement.Marketdata.Marketdataitems.Item.class);
		Smartderivativecontract.Settlement.Marketdata.Marketdataitems.Item item2 =
				mock(Smartderivativecontract.Settlement.Marketdata.Marketdataitems.Item.class);
		Smartderivativecontract.Settlement.Marketdata.Marketdataitems.Item item3 =
				mock(Smartderivativecontract.Settlement.Marketdata.Marketdataitems.Item.class);

		when(item1.getSymbol()).thenReturn(List.of("EUR-EURIBOR-6M-Swap-10Y"));
		when(item2.getSymbol()).thenReturn(List.of("EUR-EURIBOR-6M-Swap-5Y"));
		when(item3.getSymbol()).thenReturn(List.of("EUR-EURIBOR-6M-FRA-1Y"));
		when(marketdataitems.getItem()).thenReturn(List.of(item1, item2, item3));

		MarketDataErrors errors = MarketDataCheck.checkMarketData(dataList, sdc);

		assertTrue(errors.hasErrors(), "Expected errors when market data points are missing");
		assertEquals("error in marketData service - missing points in marketData", errors.getErrorMessage());
		assertEquals(2, errors.getMissingDataPoints().size(), "Expected 2 missing data points");
		assertTrue(errors.getMissingDataPoints().contains("EUR-EURIBOR-6M-Swap-5Y"));
		assertTrue(errors.getMissingDataPoints().contains("EUR-EURIBOR-6M-FRA-1Y"));
	}

	@Test
	@DisplayName("Market data with single missing point returns that ID in errors")
	void checkMarketData_singleMissingPoint_returnsErrorWithThatId() {
		MarketDataList dataList = new MarketDataList();
		LocalDateTime now = LocalDateTime.now();
		dataList.add(new MarketDataPoint("EUR-EURIBOR-6M-Swap-10Y", 0.025, now));

		Smartderivativecontract.Settlement.Marketdata.Marketdataitems.Item item1 =
				mock(Smartderivativecontract.Settlement.Marketdata.Marketdataitems.Item.class);
		Smartderivativecontract.Settlement.Marketdata.Marketdataitems.Item item2 =
				mock(Smartderivativecontract.Settlement.Marketdata.Marketdataitems.Item.class);

		when(item1.getSymbol()).thenReturn(List.of("EUR-EURIBOR-6M-Swap-10Y"));
		when(item2.getSymbol()).thenReturn(List.of("MISSING-SYMBOL"));
		when(marketdataitems.getItem()).thenReturn(List.of(item1, item2));

		MarketDataErrors errors = MarketDataCheck.checkMarketData(dataList, sdc);

		assertTrue(errors.hasErrors());
		assertEquals(1, errors.getMissingDataPoints().size());
		assertEquals("MISSING-SYMBOL", errors.getMissingDataPoints().get(0));
	}
}
