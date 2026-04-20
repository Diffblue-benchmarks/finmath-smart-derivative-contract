package net.finmath.smartcontract.valuation.marketdata.generators;

import com.neovisionaries.ws.client.WebSocket;
import io.reactivex.rxjava3.core.Observable;
import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.product.SmartDerivativeContractDescriptor;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MarketDataGeneratorLauncherTest {

	@Test
	void testInstantiateMarketDataGeneratorWebsocketSuccess() throws Exception {
		Properties props = new Properties();
		SmartDerivativeContractDescriptor sdc = mock(SmartDerivativeContractDescriptor.class);
		List<CalibrationDataItem.Spec> emptySpecs = Collections.emptyList();
		when(sdc.getMarketdataItemList()).thenReturn(emptySpecs);

		WebSocket mockSocket = mock(WebSocket.class);
		when(mockSocket.isOpen()).thenReturn(true);
		JSONObject mockAuthJson = new JSONObject("{\"access_token\":\"test\"}");
		MarketDataList expectedResult = new MarketDataList();

		try (MockedConstruction<WebSocketConnector> connectorMock = mockConstruction(
				WebSocketConnector.class,
				(mock, context) -> {
					when(mock.getWebSocket()).thenReturn(mockSocket);
					when(mock.getAuthJson()).thenReturn(mockAuthJson);
					when(mock.getPosition()).thenReturn("127.0.0.1");
				});
			 MockedConstruction<MarketDataGeneratorWebsocket> emitterMock = mockConstruction(
				MarketDataGeneratorWebsocket.class,
				(mock, context) -> {
					when(mock.asObservable()).thenReturn(Observable.just(expectedResult));
				})
		) {
			MarketDataList result = MarketDataGeneratorLauncher.instantiateMarketDataGeneratorWebsocket(props, sdc);

			assertNotNull(result);
			assertEquals(1, connectorMock.constructed().size());
			assertEquals(1, emitterMock.constructed().size());
			verify(mockSocket).addListener(emitterMock.constructed().get(0));
			verify(mockSocket).connect();
		}
	}

	@Test
	void testInstantiateMarketDataGeneratorWebsocketIOException() throws Exception {
		Properties props = new Properties();
		SmartDerivativeContractDescriptor sdc = mock(SmartDerivativeContractDescriptor.class);
		when(sdc.getMarketdataItemList()).thenReturn(Collections.emptyList());

		try (MockedConstruction<WebSocketConnector> connectorMock = mockConstruction(
				WebSocketConnector.class,
				(mock, context) -> {
					when(mock.getWebSocket()).thenThrow(new IOException("connection failed"));
					when(mock.getAuthJson()).thenReturn(new JSONObject());
					when(mock.getPosition()).thenReturn("127.0.0.1");
				})
		) {
			assertThrows(RuntimeException.class,
					() -> MarketDataGeneratorLauncher.instantiateMarketDataGeneratorWebsocket(props, sdc));
		}
	}

	@Test
	void testInstantiateMarketDataGeneratorWebsocketGenericException() throws Exception {
		Properties props = new Properties();
		SmartDerivativeContractDescriptor sdc = mock(SmartDerivativeContractDescriptor.class);
		when(sdc.getMarketdataItemList()).thenReturn(Collections.emptyList());

		try (MockedConstruction<WebSocketConnector> connectorMock = mockConstruction(
				WebSocketConnector.class,
				(mock, context) -> {
					when(mock.getWebSocket()).thenThrow(new RuntimeException("unexpected error"));
					when(mock.getAuthJson()).thenReturn(new JSONObject());
					when(mock.getPosition()).thenReturn("127.0.0.1");
				})
		) {
			RuntimeException ex = assertThrows(RuntimeException.class,
					() -> MarketDataGeneratorLauncher.instantiateMarketDataGeneratorWebsocket(props, sdc));
			assertNotNull(ex.getCause());
		}
	}
}
