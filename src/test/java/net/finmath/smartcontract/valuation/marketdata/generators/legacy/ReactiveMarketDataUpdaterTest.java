package net.finmath.smartcontract.valuation.marketdata.generators.legacy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neovisionaries.ws.client.WebSocket;
import io.reactivex.rxjava3.core.Observable;
import net.finmath.smartcontract.model.MarketDataSet;
import net.finmath.smartcontract.model.MarketDataSetValuesInner;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReactiveMarketDataUpdaterTest {

	private ReactiveMarketDataUpdater updater;
	private JSONObject authJson;
	private List<CalibrationDataItem.Spec> itemList;
	private WebSocket mockWebSocket;

	@BeforeEach
	void setUp() throws Exception {
		authJson = new JSONObject();
		authJson.put("access_token", "test-token-123");

		itemList = List.of(
				new CalibrationDataItem.Spec("EUR1Y", "Euribor6M", "Swap", "1Y"),
				new CalibrationDataItem.Spec("EUR2Y", "Euribor6M", "Swap", "2Y")
		);

		updater = new ReactiveMarketDataUpdater(authJson, "127.0.0.1", itemList);
		mockWebSocket = mock(WebSocket.class);
	}

	@Test
	void testConstructorInitializesCorrectly() {
		assertNotNull(updater);
		assertNotNull(updater.asObservable());
		assertFalse(updater.requestSent);
	}

	@Test
	void testAsObservableReturnsNonNull() {
		Observable<MarketDataSet> observable = updater.asObservable();
		assertNotNull(observable);
	}

	@Test
	void testCloseStreamsAndLogoff() {
		updater.closeStreamsAndLogoff(mockWebSocket);
		verify(mockWebSocket).sendText("{\"ID\":1, \"Type\": \"Close\", \"Domain\":\"Login\"}");
	}

	@Test
	void testOnConnectedSendsLoginAndResetsConnection() throws Exception {
		updater.onConnected(mockWebSocket, new HashMap<>());
		// Should call sendText 3 times: login, logoff, login again
		verify(mockWebSocket, times(3)).sendText(anyString());
	}

	@Test
	void testOnTextMessageWithEmptyString() {
		updater.onTextMessage(mockWebSocket, "");
		// Empty message should be ignored, no interaction with websocket
		verify(mockWebSocket, never()).sendText(anyString());
	}

	@Test
	void testOnTextMessageWithNonQuoteMessage() {
		// A message that cannot be parsed as RefinitivMarketData list - triggers the catch block
		updater.onTextMessage(mockWebSocket, "{\"Type\":\"Ping\"}");
		// After non-parseable message, sendRICRequest should be called since requestSent is false
		verify(mockWebSocket).sendText(contains("\"Key\""));
		assertTrue(updater.requestSent);
	}

	@Test
	void testOnTextMessageWithValidMarketData() {
		String message = "[{\"Key\":{\"Name\":\"EUR1Y\"},\"Fields\":{\"BID\":1.5,\"ASK\":1.6,\"VALUE_DT1\":\"2024-01-15\",\"VALUE_TS1\":\"10:30:00\"}},"
				+ "{\"Key\":{\"Name\":\"EUR2Y\"},\"Fields\":{\"BID\":2.0,\"ASK\":2.2,\"VALUE_DT1\":\"2024-01-15\",\"VALUE_TS1\":\"10:30:00\"}}]";

		List<MarketDataSet> received = new ArrayList<>();
		updater.asObservable().subscribe(received::add);

		updater.onTextMessage(mockWebSocket, message);

		// Both quotes retrieved -> should emit via publishSubject
		assertEquals(1, received.size());
		MarketDataSet result = received.get(0);
		assertEquals(2, result.getValues().size());
	}

	@Test
	void testOnTextMessagePartialQuotesDoNotEmit() {
		// Only 1 of 2 required quotes
		String message = "[{\"Key\":{\"Name\":\"EUR1Y\"},\"Fields\":{\"BID\":1.5,\"ASK\":1.6,\"VALUE_DT1\":\"2024-01-15\",\"VALUE_TS1\":\"10:30:00\"}}]";

		List<MarketDataSet> received = new ArrayList<>();
		updater.asObservable().subscribe(received::add);

		updater.onTextMessage(mockWebSocket, message);

		// Not all quotes retrieved, should not emit
		assertEquals(0, received.size());
	}

	@Test
	void testOnTextMessageResetsAfterEmit() {
		String message = "[{\"Key\":{\"Name\":\"EUR1Y\"},\"Fields\":{\"BID\":1.5,\"ASK\":1.6,\"VALUE_DT1\":\"2024-01-15\",\"VALUE_TS1\":\"10:30:00\"}},"
				+ "{\"Key\":{\"Name\":\"EUR2Y\"},\"Fields\":{\"BID\":2.0,\"ASK\":2.2,\"VALUE_DT1\":\"2024-01-15\",\"VALUE_TS1\":\"10:30:00\"}}]";

		List<MarketDataSet> received = new ArrayList<>();
		updater.asObservable().subscribe(received::add);

		updater.onTextMessage(mockWebSocket, message);
		assertEquals(1, received.size());
		// requestSent should be reset after all quotes retrieved
		assertFalse(updater.requestSent);
	}

	@Test
	void testOnTextMessageDuplicateSymbolsIgnored() {
		// Send same symbol twice in one message
		String message = "[{\"Key\":{\"Name\":\"EUR1Y\"},\"Fields\":{\"BID\":1.5,\"ASK\":1.6,\"VALUE_DT1\":\"2024-01-15\",\"VALUE_TS1\":\"10:30:00\"}},"
				+ "{\"Key\":{\"Name\":\"EUR1Y\"},\"Fields\":{\"BID\":1.7,\"ASK\":1.8,\"VALUE_DT1\":\"2024-01-15\",\"VALUE_TS1\":\"10:30:00\"}}]";

		List<MarketDataSet> received = new ArrayList<>();
		updater.asObservable().subscribe(received::add);

		updater.onTextMessage(mockWebSocket, message);

		// Duplicate symbol should be skipped, only 1 of 2 specs met
		assertEquals(0, received.size());
	}

	@Test
	void testWriteDataset(@TempDir Path tempDir) throws Exception {
		File outputFile = tempDir.resolve("market-data.json").toFile();

		MarketDataSet dataset = new MarketDataSet();
		dataset.requestTimestamp(OffsetDateTime.of(2024, 1, 15, 10, 0, 0, 0, ZoneOffset.UTC));
		dataset.addValuesItem(new MarketDataSetValuesInner()
				.symbol("EUR1Y")
				.value(1.55)
				.dataTimestamp(OffsetDateTime.of(2024, 1, 15, 10, 0, 0, 0, ZoneOffset.UTC)));

		updater.writeDataset(outputFile.getAbsolutePath(), dataset, false);

		assertTrue(outputFile.exists());
		assertTrue(outputFile.length() > 0);

		// Verify the file contains valid JSON with the expected data
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		MarketDataSet read = mapper.readValue(outputFile, MarketDataSet.class);
		assertEquals(1, read.getValues().size());
		assertEquals("EUR1Y", read.getValues().get(0).getSymbol());
	}

	@Test
	void testWriteDatasetWithOvernightFixing(@TempDir Path tempDir) throws Exception {
		File outputFile = tempDir.resolve("market-data-overnight.json").toFile();

		MarketDataSet dataset = new MarketDataSet();
		dataset.requestTimestamp(OffsetDateTime.of(2024, 1, 15, 10, 0, 0, 0, ZoneOffset.UTC));
		// Use EUROSTR= symbol to trigger overnight fixing post-processing
		dataset.addValuesItem(new MarketDataSetValuesInner()
				.symbol("EUROSTR=")
				.value(3.9)
				.dataTimestamp(OffsetDateTime.of(2024, 1, 15, 10, 0, 0, 0, ZoneOffset.UTC)));

		updater.writeDataset(outputFile.getAbsolutePath(), dataset, true);

		assertTrue(outputFile.exists());
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		MarketDataSet read = mapper.readValue(outputFile, MarketDataSet.class);
		assertEquals(1, read.getValues().size());
		assertEquals("EUROSTR=", read.getValues().get(0).getSymbol());
	}

	@Test
	void testOvernightFixingNotAppliedWhenFlagIsFalse(@TempDir Path tempDir) throws Exception {
		File outputFile = tempDir.resolve("market-data-no-overnight.json").toFile();

		OffsetDateTime originalTimestamp = OffsetDateTime.of(2024, 1, 15, 10, 0, 0, 0, ZoneOffset.UTC);
		MarketDataSet dataset = new MarketDataSet();
		dataset.requestTimestamp(originalTimestamp);
		dataset.addValuesItem(new MarketDataSetValuesInner()
				.symbol("EUROSTR=")
				.value(3.9)
				.dataTimestamp(originalTimestamp));

		updater.writeDataset(outputFile.getAbsolutePath(), dataset, false);

		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		MarketDataSet read = mapper.readValue(outputFile, MarketDataSet.class);
		// When isOvernightFixing is false, timestamp should remain unchanged
		assertEquals(originalTimestamp, read.getValues().get(0).getDataTimestamp());
	}
}
