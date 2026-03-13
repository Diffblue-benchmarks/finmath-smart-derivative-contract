package net.finmath.smartcontract.fixtures;

import net.finmath.smartcontract.model.*;
import net.finmath.smartcontract.settlement.Settlement;
import net.finmath.smartcontract.settlement.SettlementInfo;
import net.finmath.smartcontract.valuation.marketdata.data.MarketDataPoint;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Test data fixtures for unit and integration tests.
 * Provides reusable test data objects and XML samples.
 */
public class TestDataFixtures {

    // Market Data fixtures
    public static final String SAMPLE_MARKET_DATA_ID = "EUR-SWAP-5Y";
    public static final Double SAMPLE_MARKET_DATA_VALUE = 0.0235;

    // Settlement fixtures
    public static final String SAMPLE_TRADE_ID = "TRADE-12345";
    public static final String SAMPLE_CURRENCY = "EUR";
    public static final BigDecimal SAMPLE_MARGIN_VALUE = new BigDecimal("1000.50");

    /**
     * Creates a sample MarketDataPoint for testing.
     */
    public static MarketDataPoint createSampleMarketDataPoint() {
        return new MarketDataPoint(
            SAMPLE_MARKET_DATA_ID,
            SAMPLE_MARKET_DATA_VALUE,
            LocalDateTime.of(2024, 3, 15, 10, 0, 0)
        );
    }

    /**
     * Creates a sample MarketDataList for testing.
     */
    public static MarketDataList createSampleMarketDataList() {
        MarketDataList marketDataList = new MarketDataList();
        marketDataList.setRequestTimeStamp(LocalDateTime.of(2024, 3, 15, 10, 0, 0));

        marketDataList.add(new MarketDataPoint("EUR-SWAP-5Y", 0.0235, LocalDateTime.of(2024, 3, 15, 10, 0, 0)));
        marketDataList.add(new MarketDataPoint("EUR-SWAP-10Y", 0.0289, LocalDateTime.of(2024, 3, 15, 10, 0, 0)));
        marketDataList.add(new MarketDataPoint("EUR-OIS-1Y", 0.0125, LocalDateTime.of(2024, 3, 15, 10, 0, 0)));

        return marketDataList;
    }

    /**
     * Creates a sample Settlement for testing.
     */
    public static Settlement createSampleSettlement() {
        Settlement settlement = new Settlement();
        settlement.setTradeId(SAMPLE_TRADE_ID);
        settlement.setSettlementType(Settlement.SettlementType.REGULAR);
        settlement.setCurrency(SAMPLE_CURRENCY);
        settlement.setMarginValue(SAMPLE_MARGIN_VALUE);
        settlement.setMarginLimits(Arrays.asList(new BigDecimal("500"), new BigDecimal("500")));
        settlement.setSettlementTime(ZonedDateTime.now());
        settlement.setSettlementNPV(new BigDecimal("5000.75"));
        settlement.setSettlementNPVPrevious(new BigDecimal("4800.25"));
        settlement.setSettlementTimeNext(ZonedDateTime.now().plusDays(1));
        settlement.setSettlementNPVNext(new BigDecimal("5200.00"));
        settlement.setMarketData(createSampleMarketDataList());

        List<SettlementInfo> infos = Arrays.asList(
            new SettlementInfo("risk_factor", new BigDecimal("0.5")),
            new SettlementInfo("volatility", new BigDecimal("0.25"))
        );
        settlement.setSettlementInfos(infos);

        return settlement;
    }

    /**
     * Creates a sample InitialSettlementRequest for testing.
     */
    public static InitialSettlementRequest createSampleInitialSettlementRequest() {
        InitialSettlementRequest request = new InitialSettlementRequest();
        request.setTradeData("<sampleTradeData/>");
        request.setNewProvidedMarketData(null); // Will use default market data
        return request;
    }

    /**
     * Creates a sample RegularSettlementRequest for testing.
     */
    public static RegularSettlementRequest createSampleRegularSettlementRequest() {
        RegularSettlementRequest request = new RegularSettlementRequest();
        request.setTradeData("<sampleTradeData/>");
        request.setSettlementLast("<sampleLastSettlement/>");
        request.setNewProvidedMarketData(null);
        return request;
    }

    /**
     * Creates a sample ValueRequest for testing.
     */
    public static ValueRequest createSampleValueRequest() {
        ValueRequest request = new ValueRequest();
        request.setMarketData("<sampleMarketData/>");
        request.setTradeData("<sampleTradeData/>");
        request.setValuationDate("20240315-100000");
        return request;
    }

    /**
     * Creates a sample MarginRequest for testing.
     */
    public static MarginRequest createSampleMarginRequest() {
        MarginRequest request = new MarginRequest();
        request.setMarketDataStart("<sampleMarketDataStart/>");
        request.setMarketDataEnd("<sampleMarketDataEnd/>");
        request.setTradeData("<sampleTradeData/>");
        return request;
    }

    /**
     * Creates a sample ValueResult for testing.
     */
    public static ValueResult createSampleValueResult() {
        ValueResult result = new ValueResult();
        result.setValue(new BigDecimal("1000.50"));
        result.setCurrency("EUR");
        result.setValuationDate("20240315-100000");
        return result;
    }

    /**
     * Creates a sample MarginResult for testing.
     */
    public static MarginResult createSampleMarginResult() {
        MarginResult result = new MarginResult();
        result.setValue(new BigDecimal("150.25"));
        result.setCurrency("EUR");
        result.setValuationDate("20240315-100000");
        return result;
    }

    /**
     * Creates a sample InitialSettlementResult for testing.
     */
    public static InitialSettlementResult createSampleInitialSettlementResult() {
        InitialSettlementResult result = new InitialSettlementResult();
        result.setGeneratedInitialSettlement("<initialSettlement/>");
        result.setCurrency("EUR");
        result.setMarginValue(BigDecimal.ZERO);
        result.setValuationDate("20240315-100000");
        return result;
    }

    /**
     * Creates a sample RegularSettlementResult for testing.
     */
    public static RegularSettlementResult createSampleRegularSettlementResult() {
        RegularSettlementResult result = new RegularSettlementResult();
        result.setGeneratedRegularSettlement("<regularSettlement/>");
        result.setCurrency("EUR");
        result.setMarginValue(new BigDecimal("1000.50"));
        result.setValuationDate("20240315-100000");
        return result;
    }

    /**
     * Loads XML content from test resources.
     */
    public static String loadXmlFixture(String filename) throws IOException {
        return Files.readString(
            Paths.get("src/test/resources/fixtures/" + filename),
            StandardCharsets.UTF_8
        );
    }
}
