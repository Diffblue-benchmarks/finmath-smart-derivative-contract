/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.marketdata.generators.legacy;

import com.neovisionaries.ws.client.WebSocket;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;
import net.finmath.smartcontract.model.MarketDataSet;
import net.finmath.smartcontract.model.MarketDataSetValuesInner;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for ReactiveMarketDataUpdater.
 * Tests all methods with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class ReactiveMarketDataUpdaterClaudeTest {

	@TempDir
	Path tempDir;

	/**
	 * Helper method to create a valid JSONObject for authentication.
	 */
	private JSONObject createValidAuthJson() {
		try {
			JSONObject authJson = new JSONObject();
			authJson.put("access_token", "test_token_123456");
			return authJson;
		} catch (JSONException e) {
			throw new RuntimeException("Failed to create auth JSON", e);
		}
	}

	/**
	 * Helper method to create a list of CalibrationDataItem.Spec.
	 */
	private List<CalibrationDataItem.Spec> createCalibrationSpecs() {
		List<CalibrationDataItem.Spec> specs = new ArrayList<>();
		specs.add(new CalibrationDataItem.Spec("EUR6M=", "EUR-DISCOUNT", "DEPOSIT", "6M"));
		specs.add(new CalibrationDataItem.Spec("EUROND=", "EUR-DISCOUNT", "DEPOSIT", "ON"));
		return specs;
	}

	/**
	 * Test successful construction with valid parameters.
	 */
	@Test
	void testConstructor_validParameters() {
		JSONObject authJson = createValidAuthJson();
		String position = "127.0.0.1";
		List<CalibrationDataItem.Spec> specs = createCalibrationSpecs();

		ReactiveMarketDataUpdater updater = new ReactiveMarketDataUpdater(authJson, position, specs);

		assertNotNull(updater);
		assertNotNull(updater.asObservable());
		assertFalse(updater.requestSent);
	}

	/**
	 * Test constructor with empty calibration specs list.
	 */
	@Test
	void testConstructor_emptyCalibrationSpecs() {
		JSONObject authJson = createValidAuthJson();
		String position = "127.0.0.1";
		List<CalibrationDataItem.Spec> specs = new ArrayList<>();

		ReactiveMarketDataUpdater updater = new ReactiveMarketDataUpdater(authJson, position, specs);

		assertNotNull(updater);
		assertFalse(updater.requestSent);
	}

	/**
	 * Test constructor with single calibration spec.
	 */
	@Test
	void testConstructor_singleCalibrationSpec() {
		JSONObject authJson = createValidAuthJson();
		String position = "127.0.0.1";
		List<CalibrationDataItem.Spec> specs = new ArrayList<>();
		specs.add(new CalibrationDataItem.Spec("EUR6M=", "EUR-DISCOUNT", "DEPOSIT", "6M"));

		ReactiveMarketDataUpdater updater = new ReactiveMarketDataUpdater(authJson, position, specs);

		assertNotNull(updater);
	}

	/**
	 * Test asObservable method returns a valid Observable.
	 */
	@Test
	void testAsObservable_returnsValidObservable() {
		JSONObject authJson = createValidAuthJson();
		String position = "127.0.0.1";
		List<CalibrationDataItem.Spec> specs = createCalibrationSpecs();

		ReactiveMarketDataUpdater updater = new ReactiveMarketDataUpdater(authJson, position, specs);
		Observable<MarketDataSet> observable = updater.asObservable();

		assertNotNull(observable);
	}

	/**
	 * Test asObservable method returns the same instance on multiple calls.
	 */
	@Test
	void testAsObservable_returnsSameInstance() {
		JSONObject authJson = createValidAuthJson();
		String position = "127.0.0.1";
		List<CalibrationDataItem.Spec> specs = createCalibrationSpecs();

		ReactiveMarketDataUpdater updater = new ReactiveMarketDataUpdater(authJson, position, specs);
		Observable<MarketDataSet> observable1 = updater.asObservable();
		Observable<MarketDataSet> observable2 = updater.asObservable();

		assertSame(observable1, observable2);
	}

	/**
	 * Test closeStreamsAndLogoff sends correct message.
	 */
	@Test
	void testCloseStreamsAndLogoff_sendsCloseMessage() {
		JSONObject authJson = createValidAuthJson();
		String position = "127.0.0.1";
		List<CalibrationDataItem.Spec> specs = createCalibrationSpecs();

		ReactiveMarketDataUpdater updater = new ReactiveMarketDataUpdater(authJson, position, specs);
		WebSocket mockWebSocket = mock(WebSocket.class);

		updater.closeStreamsAndLogoff(mockWebSocket);

		verify(mockWebSocket).sendText("{\"ID\":1, \"Type\": \"Close\", \"Domain\":\"Login\"}");
	}

	/**
	 * Test onConnected method with valid websocket and headers.
	 */
	@Test
	void testOnConnected_withValidParameters() throws Exception {
		JSONObject authJson = createValidAuthJson();
		String position = "127.0.0.1";
		List<CalibrationDataItem.Spec> specs = createCalibrationSpecs();

		ReactiveMarketDataUpdater updater = new ReactiveMarketDataUpdater(authJson, position, specs);
		WebSocket mockWebSocket = mock(WebSocket.class);
		Map<String, List<String>> headers = new HashMap<>();

		updater.onConnected(mockWebSocket, headers);

		// Verify that sendText is called multiple times (2 login requests and 1 close)
		verify(mockWebSocket, atLeast(2)).sendText(anyString());
	}

	/**
	 * Test onTextMessage with empty message.
	 */
	@Test
	void testOnTextMessage_emptyMessage() {
		JSONObject authJson = createValidAuthJson();
		String position = "127.0.0.1";
		List<CalibrationDataItem.Spec> specs = createCalibrationSpecs();

		ReactiveMarketDataUpdater updater = new ReactiveMarketDataUpdater(authJson, position, specs);
		WebSocket mockWebSocket = mock(WebSocket.class);

		// Should not throw exception
		assertDoesNotThrow(() -> updater.onTextMessage(mockWebSocket, ""));
	}

	/**
	 * Test onTextMessage with invalid JSON message.
	 */
	@Test
	void testOnTextMessage_invalidJsonMessage() {
		JSONObject authJson = createValidAuthJson();
		String position = "127.0.0.1";
		List<CalibrationDataItem.Spec> specs = createCalibrationSpecs();

		ReactiveMarketDataUpdater updater = new ReactiveMarketDataUpdater(authJson, position, specs);
		WebSocket mockWebSocket = mock(WebSocket.class);

		// Should not throw exception, should log and continue
		assertDoesNotThrow(() -> updater.onTextMessage(mockWebSocket, "not a json"));
	}

	/**
	 * Test onTextMessage with valid market data JSON but request not sent yet.
	 */
	@Test
	void testOnTextMessage_validMarketData_requestNotSent() {
		JSONObject authJson = createValidAuthJson();
		String position = "127.0.0.1";
		List<CalibrationDataItem.Spec> specs = createCalibrationSpecs();

		ReactiveMarketDataUpdater updater = new ReactiveMarketDataUpdater(authJson, position, specs);
		WebSocket mockWebSocket = mock(WebSocket.class);

		// Valid JSON that will be processed
		String jsonMessage = "[{\"Key\":{\"Name\":\"EUR6M=\"},\"Fields\":{\"BID\":2.5,\"ASK\":2.7,\"VALUE_DT1\":\"2024-01-15\",\"VALUE_TS1\":\"10:30:00\"}}]";

		updater.onTextMessage(mockWebSocket, jsonMessage);

		// After processing valid message, request should be sent
		assertTrue(updater.requestSent);
		verify(mockWebSocket).sendText(contains("\"ID\":2"));
	}

	/**
	 * Test onTextMessage with complete market data (all quotes retrieved).
	 */
	@Test
	void testOnTextMessage_allQuotesRetrieved() {
		JSONObject authJson = createValidAuthJson();
		String position = "127.0.0.1";
		List<CalibrationDataItem.Spec> specs = createCalibrationSpecs();

		ReactiveMarketDataUpdater updater = new ReactiveMarketDataUpdater(authJson, position, specs);
		WebSocket mockWebSocket = mock(WebSocket.class);

		TestObserver<MarketDataSet> testObserver = updater.asObservable().test();

		// Send first market data item
		String jsonMessage1 = "[{\"Key\":{\"Name\":\"EUR6M=\"},\"Fields\":{\"BID\":2.5,\"ASK\":2.7,\"VALUE_DT1\":\"2024-01-15\",\"VALUE_TS1\":\"10:30:00\"}}]";
		updater.onTextMessage(mockWebSocket, jsonMessage1);

		// Send second market data item
		String jsonMessage2 = "[{\"Key\":{\"Name\":\"EUROND=\"},\"Fields\":{\"BID\":1.5,\"ASK\":1.7,\"VALUE_DT1\":\"2024-01-15\",\"VALUE_TS1\":\"10:30:00\"}}]";
		updater.onTextMessage(mockWebSocket, jsonMessage2);

		// Verify that data was emitted
		testObserver.assertValueCount(1);
		testObserver.assertNotComplete();

		MarketDataSet emittedData = testObserver.values().get(0);
		assertEquals(2, emittedData.getValues().size());
	}

	/**
	 * Test onTextMessage with market data missing ASK field (should fail gracefully).
	 */
	@Test
	void testOnTextMessage_marketDataMissingAsk() {
		JSONObject authJson = createValidAuthJson();
		String position = "127.0.0.1";
		List<CalibrationDataItem.Spec> specs = createCalibrationSpecs();

		ReactiveMarketDataUpdater updater = new ReactiveMarketDataUpdater(authJson, position, specs);
		WebSocket mockWebSocket = mock(WebSocket.class);

		// JSON with only BID, no ASK
		String jsonMessage = "[{\"Key\":{\"Name\":\"EUR6M=\"},\"Fields\":{\"BID\":2.5,\"VALUE_DT1\":\"2024-01-15\",\"VALUE_TS1\":\"10:30:00\"}}]";

		// Should not throw exception, should log and skip this message
		assertDoesNotThrow(() -> updater.onTextMessage(mockWebSocket, jsonMessage));
	}

	/**
	 * Test onTextMessage with market data missing BID field (should fail gracefully).
	 */
	@Test
	void testOnTextMessage_marketDataMissingBid() {
		JSONObject authJson = createValidAuthJson();
		String position = "127.0.0.1";
		List<CalibrationDataItem.Spec> specs = createCalibrationSpecs();

		ReactiveMarketDataUpdater updater = new ReactiveMarketDataUpdater(authJson, position, specs);
		WebSocket mockWebSocket = mock(WebSocket.class);

		// JSON with only ASK, no BID
		String jsonMessage = "[{\"Key\":{\"Name\":\"EUR6M=\"},\"Fields\":{\"ASK\":2.7,\"VALUE_DT1\":\"2024-01-15\",\"VALUE_TS1\":\"10:30:00\"}}]";

		// Should not throw exception, should log and skip this message
		assertDoesNotThrow(() -> updater.onTextMessage(mockWebSocket, jsonMessage));
	}

	/**
	 * Test onTextMessage with duplicate symbol within same batch (should not add duplicate).
	 */
	@Test
	void testOnTextMessage_duplicateSymbol() {
		JSONObject authJson = createValidAuthJson();
		String position = "127.0.0.1";
		List<CalibrationDataItem.Spec> specs = new ArrayList<>();
		specs.add(new CalibrationDataItem.Spec("EUR6M=", "EUR-DISCOUNT", "DEPOSIT", "6M"));
		specs.add(new CalibrationDataItem.Spec("EUROND=", "EUR-DISCOUNT", "DEPOSIT", "ON"));

		ReactiveMarketDataUpdater updater = new ReactiveMarketDataUpdater(authJson, position, specs);
		WebSocket mockWebSocket = mock(WebSocket.class);

		TestObserver<MarketDataSet> testObserver = updater.asObservable().test();

		// Send EUR6M= data twice in separate messages (same batch before all quotes retrieved)
		String jsonMessage = "[{\"Key\":{\"Name\":\"EUR6M=\"},\"Fields\":{\"BID\":2.5,\"ASK\":2.7,\"VALUE_DT1\":\"2024-01-15\",\"VALUE_TS1\":\"10:30:00\"}}]";
		updater.onTextMessage(mockWebSocket, jsonMessage);
		updater.onTextMessage(mockWebSocket, jsonMessage);

		// Now send the second symbol to trigger emission
		String jsonMessage2 = "[{\"Key\":{\"Name\":\"EUROND=\"},\"Fields\":{\"BID\":1.5,\"ASK\":1.7,\"VALUE_DT1\":\"2024-01-15\",\"VALUE_TS1\":\"10:30:00\"}}]";
		updater.onTextMessage(mockWebSocket, jsonMessage2);

		// Should emit once with two unique values (EUR6M= should not be duplicated)
		testObserver.assertValueCount(1);
		MarketDataSet emittedData = testObserver.values().get(0);
		assertEquals(2, emittedData.getValues().size());
	}

	/**
	 * Test writeDataset method with valid parameters.
	 */
	@Test
	void testWriteDataset_validParameters() throws IOException {
		JSONObject authJson = createValidAuthJson();
		String position = "127.0.0.1";
		List<CalibrationDataItem.Spec> specs = createCalibrationSpecs();

		ReactiveMarketDataUpdater updater = new ReactiveMarketDataUpdater(authJson, position, specs);

		// Create a MarketDataSet
		MarketDataSet marketDataSet = new MarketDataSet();
		marketDataSet.requestTimestamp(OffsetDateTime.now(ZoneOffset.UTC));
		marketDataSet.addValuesItem(new MarketDataSetValuesInner()
				.symbol("EUR6M=")
				.value(2.6)
				.dataTimestamp(OffsetDateTime.of(2024, 1, 15, 10, 30, 0, 0, ZoneOffset.UTC)));

		File outputFile = tempDir.resolve("output.json").toFile();

		updater.writeDataset(outputFile.getAbsolutePath(), marketDataSet, false);

		// Verify file was created
		assertTrue(outputFile.exists());
		String content = Files.readString(outputFile.toPath());
		assertTrue(content.contains("EUR6M="));
		assertTrue(content.contains("2.6"));
	}

	/**
	 * Test writeDataset method with overnight fixing enabled for EUROSTR symbol.
	 */
	@Test
	void testWriteDataset_withOvernightFixing_eurostrSymbol() throws IOException {
		JSONObject authJson = createValidAuthJson();
		String position = "127.0.0.1";
		List<CalibrationDataItem.Spec> specs = createCalibrationSpecs();

		ReactiveMarketDataUpdater updater = new ReactiveMarketDataUpdater(authJson, position, specs);

		// Create a MarketDataSet with EUROSTR= symbol
		MarketDataSet marketDataSet = new MarketDataSet();
		marketDataSet.requestTimestamp(OffsetDateTime.now(ZoneOffset.UTC));
		marketDataSet.addValuesItem(new MarketDataSetValuesInner()
				.symbol("EUROSTR=")
				.value(1.5)
				.dataTimestamp(OffsetDateTime.of(2024, 1, 15, 10, 30, 0, 0, ZoneOffset.UTC)));

		File outputFile = tempDir.resolve("output_overnight.json").toFile();

		updater.writeDataset(outputFile.getAbsolutePath(), marketDataSet, true);

		// Verify file was created
		assertTrue(outputFile.exists());
		String content = Files.readString(outputFile.toPath());
		assertTrue(content.contains("EUROSTR="));
	}

	/**
	 * Test writeDataset method with overnight fixing disabled.
	 */
	@Test
	void testWriteDataset_overnightFixingDisabled() throws IOException {
		JSONObject authJson = createValidAuthJson();
		String position = "127.0.0.1";
		List<CalibrationDataItem.Spec> specs = createCalibrationSpecs();

		ReactiveMarketDataUpdater updater = new ReactiveMarketDataUpdater(authJson, position, specs);

		// Create a MarketDataSet with EUROSTR= symbol
		MarketDataSet marketDataSet = new MarketDataSet();
		marketDataSet.requestTimestamp(OffsetDateTime.now(ZoneOffset.UTC));
		marketDataSet.addValuesItem(new MarketDataSetValuesInner()
				.symbol("EUROSTR=")
				.value(1.5)
				.dataTimestamp(OffsetDateTime.of(2024, 1, 15, 10, 30, 0, 0, ZoneOffset.UTC)));

		File outputFile = tempDir.resolve("output_no_overnight.json").toFile();

		updater.writeDataset(outputFile.getAbsolutePath(), marketDataSet, false);

		// Verify file was created
		assertTrue(outputFile.exists());
	}

	/**
	 * Test writeDataset method with non-EUROSTR symbol and overnight fixing enabled.
	 */
	@Test
	void testWriteDataset_nonEurostrWithOvernightFixing() throws IOException {
		JSONObject authJson = createValidAuthJson();
		String position = "127.0.0.1";
		List<CalibrationDataItem.Spec> specs = createCalibrationSpecs();

		ReactiveMarketDataUpdater updater = new ReactiveMarketDataUpdater(authJson, position, specs);

		// Create a MarketDataSet with non-EUROSTR symbol
		MarketDataSet marketDataSet = new MarketDataSet();
		marketDataSet.requestTimestamp(OffsetDateTime.now(ZoneOffset.UTC));
		marketDataSet.addValuesItem(new MarketDataSetValuesInner()
				.symbol("EUR6M=")
				.value(2.6)
				.dataTimestamp(OffsetDateTime.of(2024, 1, 15, 10, 30, 0, 0, ZoneOffset.UTC)));

		File outputFile = tempDir.resolve("output_non_eurostr.json").toFile();

		updater.writeDataset(outputFile.getAbsolutePath(), marketDataSet, true);

		// Verify file was created
		assertTrue(outputFile.exists());
		String content = Files.readString(outputFile.toPath());
		assertTrue(content.contains("EUR6M="));
	}

	/**
	 * Test onTextMessage with null key in market data.
	 */
	@Test
	void testOnTextMessage_nullKey() {
		JSONObject authJson = createValidAuthJson();
		String position = "127.0.0.1";
		List<CalibrationDataItem.Spec> specs = createCalibrationSpecs();

		ReactiveMarketDataUpdater updater = new ReactiveMarketDataUpdater(authJson, position, specs);
		WebSocket mockWebSocket = mock(WebSocket.class);

		// JSON with null Key
		String jsonMessage = "[{\"Key\":null,\"Fields\":{\"BID\":2.5,\"ASK\":2.7,\"VALUE_DT1\":\"2024-01-15\",\"VALUE_TS1\":\"10:30:00\"}}]";

		// Should not throw exception, should log and skip this message
		assertDoesNotThrow(() -> updater.onTextMessage(mockWebSocket, jsonMessage));
	}

	/**
	 * Test onTextMessage with null fields in market data.
	 */
	@Test
	void testOnTextMessage_nullFields() {
		JSONObject authJson = createValidAuthJson();
		String position = "127.0.0.1";
		List<CalibrationDataItem.Spec> specs = createCalibrationSpecs();

		ReactiveMarketDataUpdater updater = new ReactiveMarketDataUpdater(authJson, position, specs);
		WebSocket mockWebSocket = mock(WebSocket.class);

		// JSON with null Fields
		String jsonMessage = "[{\"Key\":{\"Name\":\"EUR6M=\"},\"Fields\":null}]";

		// Should not throw exception, should log and skip this message
		assertDoesNotThrow(() -> updater.onTextMessage(mockWebSocket, jsonMessage));
	}

	/**
	 * Test onTextMessage processes request sent flag correctly after sending request.
	 */
	@Test
	void testOnTextMessage_requestSentFlagUpdated() {
		JSONObject authJson = createValidAuthJson();
		String position = "127.0.0.1";
		List<CalibrationDataItem.Spec> specs = createCalibrationSpecs();

		ReactiveMarketDataUpdater updater = new ReactiveMarketDataUpdater(authJson, position, specs);
		WebSocket mockWebSocket = mock(WebSocket.class);

		assertFalse(updater.requestSent);

		String jsonMessage = "[{\"Key\":{\"Name\":\"EUR6M=\"},\"Fields\":{\"BID\":2.5,\"ASK\":2.7,\"VALUE_DT1\":\"2024-01-15\",\"VALUE_TS1\":\"10:30:00\"}}]";
		updater.onTextMessage(mockWebSocket, jsonMessage);

		assertTrue(updater.requestSent);
	}

	/**
	 * Test onTextMessage with message that doesn't reset requestSent when already sent.
	 */
	@Test
	void testOnTextMessage_requestAlreadySent() {
		JSONObject authJson = createValidAuthJson();
		String position = "127.0.0.1";
		List<CalibrationDataItem.Spec> specs = createCalibrationSpecs();

		ReactiveMarketDataUpdater updater = new ReactiveMarketDataUpdater(authJson, position, specs);
		WebSocket mockWebSocket = mock(WebSocket.class);

		// First message sends request
		String jsonMessage = "[{\"Key\":{\"Name\":\"EUR6M=\"},\"Fields\":{\"BID\":2.5,\"ASK\":2.7,\"VALUE_DT1\":\"2024-01-15\",\"VALUE_TS1\":\"10:30:00\"}}]";
		updater.onTextMessage(mockWebSocket, jsonMessage);
		assertTrue(updater.requestSent);

		// Second message should not send another request
		updater.onTextMessage(mockWebSocket, jsonMessage);

		// Verify sendText was called only once for the RIC request
		verify(mockWebSocket, times(1)).sendText(contains("\"ID\":2"));
	}
}
