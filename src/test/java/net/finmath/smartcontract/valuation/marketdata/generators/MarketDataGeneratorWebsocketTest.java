package net.finmath.smartcontract.valuation.marketdata.generators;

import com.neovisionaries.ws.client.WebSocket;
import io.reactivex.rxjava3.core.Observable;
import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MarketDataGeneratorWebsocketTest {

	private MarketDataGeneratorWebsocket generator;
	private WebSocket mockWebSocket;
	private List<CalibrationDataItem.Spec> specList;

	@BeforeEach
	void setUp() throws Exception {
		JSONObject authJson = new JSONObject();
		authJson.put("access_token", "test_token");

		specList = new ArrayList<>();
		specList.add(new CalibrationDataItem.Spec("EUR6MFRA1X7", "Euribor6M", "Fra", "7M"));
		specList.add(new CalibrationDataItem.Spec("EUROSTR=", "ESTR", "Fixing", "1D"));

		generator = new MarketDataGeneratorWebsocket(authJson, "127.0.0.1", specList);
		mockWebSocket = mock(WebSocket.class);
	}

	@Test
	void testConstructorInitializesFields() {
		assertFalse(generator.allQuotesRetrieved());
		assertFalse(generator.requestSent);
	}

	@Test
	void testAllQuotesRetrievedReturnsFalseWhenEmpty() {
		assertFalse(generator.allQuotesRetrieved());
	}

	@Test
	void testAsObservableReturnsNonNull() {
		Observable<MarketDataList> observable = generator.asObservable();
		assertNotNull(observable);
	}

	@Test
	void testCloseStreamsAndLogoff() {
		generator.closeStreamsAndLogoff(mockWebSocket);
		verify(mockWebSocket).sendText("{\"ID\":1, \"Type\": \"Close\", \"Domain\":\"Login\"}");
	}

	@Test
	void testWriteDatasetThrowsRuntimeException() {
		assertThrows(RuntimeException.class, () -> generator.writeDataset("dir", new MarketDataList(), false));
	}

	@Test
	void testOnConnectedSendsLoginRequest() throws Exception {
		generator.onConnected(mockWebSocket, new HashMap<>());
		verify(mockWebSocket).sendText(argThat(text -> text.contains("AuthenticationToken") && text.contains("test_token")));
	}

	@Test
	void testSendRICRequest() {
		generator.sendRICRequest(mockWebSocket);
		verify(mockWebSocket).sendText(argThat(text ->
				text.contains("EUR6MFRA1X7") && text.contains("EUROSTR=") && text.contains("BID") && text.contains("ASK")));
	}

	@Test
	void testSendLoginRequestFirstLogin() throws Exception {
		generator.sendLoginRequest(mockWebSocket, "myToken", true);
		verify(mockWebSocket).sendText(argThat(text ->
				text.contains("myToken") && text.contains("256") && text.contains("127.0.0.1") && !text.contains("\"Refresh\"")));
	}

	@Test
	void testSendLoginRequestSubsequentLogin() throws Exception {
		generator.sendLoginRequest(mockWebSocket, "myToken", false);
		verify(mockWebSocket).sendText(argThat(text ->
				text.contains("myToken") && text.contains("Refresh")));
	}

	@Test
	void testOnTextMessageEmptyMessage() throws Exception {
		generator.onTextMessage(mockWebSocket, "");
		assertFalse(generator.allQuotesRetrieved());
	}

	@Test
	void testOnTextMessageWithFieldsData() throws Exception {
		String message = "[{\"ID\":2,\"Type\":\"Refresh\",\"Key\":{\"Name\":\"EUR6MFRA1X7\"},\"Fields\":{\"BID\":2.5,\"ASK\":2.6,\"VALUE_DT1\":\"2024-01-15\",\"VALUE_TS1\":\"10:30:00\"}},"
				+ "{\"ID\":2,\"Type\":\"Refresh\",\"Key\":{\"Name\":\"EUROSTR=\"},\"Fields\":{\"BID\":3.9,\"ASK\":3.91,\"VALUE_DT1\":\"2024-01-15\",\"VALUE_TS1\":\"10:30:00\"}}]";

		AtomicReference<MarketDataList> received = new AtomicReference<>();
		generator.asObservable().subscribe(received::set);

		generator.onTextMessage(mockWebSocket, message);

		// requestSent is reset to false after all quotes are retrieved
		assertFalse(generator.requestSent);
		assertNotNull(received.get());
		assertEquals(2, received.get().getSize());
	}

	@Test
	void testOnTextMessageWithOnlyBid() throws Exception {
		String message = "[{\"ID\":2,\"Key\":{\"Name\":\"EUR6MFRA1X7\"},\"Fields\":{\"BID\":2.5,\"VALUE_DT1\":\"2024-01-15\",\"VALUE_TS1\":\"10:30:00\"}},"
				+ "{\"ID\":2,\"Key\":{\"Name\":\"EUROSTR=\"},\"Fields\":{\"BID\":3.9,\"VALUE_DT1\":\"2024-01-15\",\"VALUE_TS1\":\"10:30:00\"}}]";

		AtomicReference<MarketDataList> received = new AtomicReference<>();
		generator.asObservable().subscribe(received::set);

		generator.onTextMessage(mockWebSocket, message);

		assertNotNull(received.get());
	}

	@Test
	void testOnTextMessageWithOnlyAsk() throws Exception {
		String message = "[{\"ID\":2,\"Key\":{\"Name\":\"EUR6MFRA1X7\"},\"Fields\":{\"ASK\":2.6,\"VALUE_DT1\":\"2024-01-15\",\"VALUE_TS1\":\"10:30:00\"}},"
				+ "{\"ID\":2,\"Key\":{\"Name\":\"EUROSTR=\"},\"Fields\":{\"ASK\":3.91,\"VALUE_DT1\":\"2024-01-15\",\"VALUE_TS1\":\"10:30:00\"}}]";

		AtomicReference<MarketDataList> received = new AtomicReference<>();
		generator.asObservable().subscribe(received::set);

		generator.onTextMessage(mockWebSocket, message);

		assertNotNull(received.get());
	}

	@Test
	void testOnTextMessageWithoutFields() throws Exception {
		String message = "[{\"ID\":1,\"Type\":\"Refresh\",\"Domain\":\"Login\"}]";
		generator.onTextMessage(mockWebSocket, message);
		assertFalse(generator.allQuotesRetrieved());
	}

	@Test
	void testResetAfterAllQuotesRetrieved() throws Exception {
		String message = "[{\"ID\":2,\"Key\":{\"Name\":\"EUR6MFRA1X7\"},\"Fields\":{\"BID\":2.5,\"ASK\":2.6,\"VALUE_DT1\":\"2024-01-15\",\"VALUE_TS1\":\"10:30:00\"}},"
				+ "{\"ID\":2,\"Key\":{\"Name\":\"EUROSTR=\"},\"Fields\":{\"BID\":3.9,\"ASK\":3.91,\"VALUE_DT1\":\"2024-01-15\",\"VALUE_TS1\":\"10:30:00\"}}]";

		generator.onTextMessage(mockWebSocket, message);

		// After all quotes retrieved, reset should have been called
		assertFalse(generator.allQuotesRetrieved());
		assertFalse(generator.requestSent);
	}
}
