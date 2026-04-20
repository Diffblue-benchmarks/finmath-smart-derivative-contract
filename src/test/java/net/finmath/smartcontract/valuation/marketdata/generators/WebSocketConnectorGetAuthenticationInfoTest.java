package net.finmath.smartcontract.valuation.marketdata.generators;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicHeader;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WebSocketConnectorGetAuthenticationInfoTest {

	private Properties connectionProperties;
	private WebSocketConnector connector;
	private CloseableHttpClient mockHttpClient;
	private HttpClientBuilder mockHttpClientBuilder;
	private ClassicHttpResponse mockResponse;

	@BeforeEach
	void setUp() throws Exception {
		connectionProperties = new Properties();
		connectionProperties.setProperty("AUTHURL", "https://example.com/auth");
		connectionProperties.setProperty("HOSTNAME", "localhost");
		connectionProperties.setProperty("PORT", "443");
		connectionProperties.setProperty("USEPROXY", "FALSE");
		connectionProperties.setProperty("CLIENTID", "testClient");
		connectionProperties.setProperty("USER", "testUser");
		connectionProperties.setProperty("PASSWORD", "testPass");

		connector = new WebSocketConnector(connectionProperties);

		mockHttpClient = mock(CloseableHttpClient.class);
		mockHttpClientBuilder = mock(HttpClientBuilder.class);
		mockResponse = mock(ClassicHttpResponse.class);

		when(mockHttpClientBuilder.setConnectionManager(any())).thenReturn(mockHttpClientBuilder);
		when(mockHttpClientBuilder.build()).thenReturn(mockHttpClient);
		when(mockHttpClient.executeOpen(any(), any(), any())).thenReturn(mockResponse);
	}

	@Test
	void testGetAuthenticationInfoReturns200() throws Exception {
		JSONObject expectedJson = new JSONObject();
		expectedJson.put("access_token", "test_token");
		expectedJson.put("refresh_token", "test_refresh");

		when(mockResponse.getCode()).thenReturn(HttpStatus.SC_OK);
		when(mockResponse.getEntity()).thenReturn(new StringEntity(expectedJson.toString()));

		try (MockedStatic<HttpClientBuilder> httpClientBuilderMock = Mockito.mockStatic(HttpClientBuilder.class);
			 MockedStatic<PoolingHttpClientConnectionManagerBuilder> connMgrBuilderMock = mockPoolingBuilder()) {
			httpClientBuilderMock.when(HttpClientBuilder::create).thenReturn(mockHttpClientBuilder);

			JSONObject result = connector.getAuthenticationInfo(null, "https://example.com/auth");

			assertNotNull(result);
			assertEquals("test_token", result.getString("access_token"));
			assertEquals("test_refresh", result.getString("refresh_token"));
		}
	}

	@Test
	void testGetAuthenticationInfoRedirectWithLocationHeader() throws Exception {
		// First call returns 301 with Location header, second call returns 200
		JSONObject expectedJson = new JSONObject();
		expectedJson.put("access_token", "redirected_token");

		ClassicHttpResponse redirectResponse = mock(ClassicHttpResponse.class);
		when(redirectResponse.getCode()).thenReturn(HttpStatus.SC_MOVED_PERMANENTLY);
		Header locationHeader = new BasicHeader("Location", "https://new-host.com/auth");
		when(redirectResponse.getFirstHeader("Location")).thenReturn(locationHeader);

		ClassicHttpResponse okResponse = mock(ClassicHttpResponse.class);
		when(okResponse.getCode()).thenReturn(HttpStatus.SC_OK);
		when(okResponse.getEntity()).thenReturn(new StringEntity(expectedJson.toString()));

		when(mockHttpClient.executeOpen(any(), any(), any()))
				.thenReturn(redirectResponse)
				.thenReturn(okResponse);

		try (MockedStatic<HttpClientBuilder> httpClientBuilderMock = Mockito.mockStatic(HttpClientBuilder.class);
			 MockedStatic<PoolingHttpClientConnectionManagerBuilder> connMgrBuilderMock = mockPoolingBuilder()) {
			httpClientBuilderMock.when(HttpClientBuilder::create).thenReturn(mockHttpClientBuilder);

			JSONObject result = connector.getAuthenticationInfo(null, "https://example.com/auth");

			assertNotNull(result);
			assertEquals("redirected_token", result.getString("access_token"));
		}
	}

	@Test
	void testGetAuthenticationInfoRedirectWithNullLocationHeader() throws Exception {
		when(mockResponse.getCode()).thenReturn(HttpStatus.SC_MOVED_TEMPORARILY);
		when(mockResponse.getFirstHeader("Location")).thenReturn(null);

		try (MockedStatic<HttpClientBuilder> httpClientBuilderMock = Mockito.mockStatic(HttpClientBuilder.class);
			 MockedStatic<PoolingHttpClientConnectionManagerBuilder> connMgrBuilderMock = mockPoolingBuilder()) {
			httpClientBuilderMock.when(HttpClientBuilder::create).thenReturn(mockHttpClientBuilder);

			JSONObject result = connector.getAuthenticationInfo(null, "https://example.com/auth");

			assertNull(result);
		}
	}

	@Test
	void testGetAuthenticationInfoRedirectWithNullHeaderValue() throws Exception {
		when(mockResponse.getCode()).thenReturn(HttpStatus.SC_TEMPORARY_REDIRECT);
		Header locationHeader = new BasicHeader("Location", null);
		when(mockResponse.getFirstHeader("Location")).thenReturn(locationHeader);

		try (MockedStatic<HttpClientBuilder> httpClientBuilderMock = Mockito.mockStatic(HttpClientBuilder.class);
			 MockedStatic<PoolingHttpClientConnectionManagerBuilder> connMgrBuilderMock = mockPoolingBuilder()) {
			httpClientBuilderMock.when(HttpClientBuilder::create).thenReturn(mockHttpClientBuilder);

			JSONObject result = connector.getAuthenticationInfo(null, "https://example.com/auth");

			assertNull(result);
		}
	}

	@Test
	void testGetAuthenticationInfoBadRequestWithNoPreviousAuth() throws Exception {
		when(mockResponse.getCode()).thenReturn(HttpStatus.SC_BAD_REQUEST);
		when(mockResponse.getReasonPhrase()).thenReturn("Bad Request");

		try (MockedStatic<HttpClientBuilder> httpClientBuilderMock = Mockito.mockStatic(HttpClientBuilder.class);
			 MockedStatic<PoolingHttpClientConnectionManagerBuilder> connMgrBuilderMock = mockPoolingBuilder()) {
			httpClientBuilderMock.when(HttpClientBuilder::create).thenReturn(mockHttpClientBuilder);

			JSONObject result = connector.getAuthenticationInfo(null, "https://example.com/auth");

			assertNull(result);
		}
	}

	@Test
	void testGetAuthenticationInfoUnauthorizedWithPreviousAuth() throws Exception {
		// First call (with previousAuth) returns 401, retry (without previousAuth) returns 200
		ClassicHttpResponse unauthorizedResponse = mock(ClassicHttpResponse.class);
		when(unauthorizedResponse.getCode()).thenReturn(HttpStatus.SC_UNAUTHORIZED);
		when(unauthorizedResponse.getReasonPhrase()).thenReturn("Unauthorized");

		JSONObject expectedJson = new JSONObject();
		expectedJson.put("access_token", "new_token");

		ClassicHttpResponse okResponse = mock(ClassicHttpResponse.class);
		when(okResponse.getCode()).thenReturn(HttpStatus.SC_OK);
		when(okResponse.getEntity()).thenReturn(new StringEntity(expectedJson.toString()));

		when(mockHttpClient.executeOpen(any(), any(), any()))
				.thenReturn(unauthorizedResponse)
				.thenReturn(okResponse);

		JSONObject previousAuth = new JSONObject();
		previousAuth.put("refresh_token", "old_refresh");

		try (MockedStatic<HttpClientBuilder> httpClientBuilderMock = Mockito.mockStatic(HttpClientBuilder.class);
			 MockedStatic<PoolingHttpClientConnectionManagerBuilder> connMgrBuilderMock = mockPoolingBuilder()) {
			httpClientBuilderMock.when(HttpClientBuilder::create).thenReturn(mockHttpClientBuilder);

			JSONObject result = connector.getAuthenticationInfo(previousAuth, "https://example.com/auth");

			assertNotNull(result);
			assertEquals("new_token", result.getString("access_token"));
		}
	}

	@Test
	void testGetAuthenticationInfoForbidden() throws Exception {
		when(mockResponse.getCode()).thenReturn(HttpStatus.SC_FORBIDDEN);
		when(mockResponse.getReasonPhrase()).thenReturn("Forbidden");

		try (MockedStatic<HttpClientBuilder> httpClientBuilderMock = Mockito.mockStatic(HttpClientBuilder.class);
			 MockedStatic<PoolingHttpClientConnectionManagerBuilder> connMgrBuilderMock = mockPoolingBuilder()) {
			httpClientBuilderMock.when(HttpClientBuilder::create).thenReturn(mockHttpClientBuilder);

			JSONObject result = connector.getAuthenticationInfo(null, "https://example.com/auth");

			assertNull(result);
		}
	}

	@Test
	void testGetAuthenticationInfoNotFound() throws Exception {
		when(mockResponse.getCode()).thenReturn(HttpStatus.SC_NOT_FOUND);
		when(mockResponse.getReasonPhrase()).thenReturn("Not Found");

		try (MockedStatic<HttpClientBuilder> httpClientBuilderMock = Mockito.mockStatic(HttpClientBuilder.class);
			 MockedStatic<PoolingHttpClientConnectionManagerBuilder> connMgrBuilderMock = mockPoolingBuilder()) {
			httpClientBuilderMock.when(HttpClientBuilder::create).thenReturn(mockHttpClientBuilder);

			JSONObject result = connector.getAuthenticationInfo(null, "https://example.com/auth");

			assertNull(result);
		}
	}

	@Test
	void testGetAuthenticationInfoGone() throws Exception {
		when(mockResponse.getCode()).thenReturn(HttpStatus.SC_GONE);
		when(mockResponse.getReasonPhrase()).thenReturn("Gone");

		try (MockedStatic<HttpClientBuilder> httpClientBuilderMock = Mockito.mockStatic(HttpClientBuilder.class);
			 MockedStatic<PoolingHttpClientConnectionManagerBuilder> connMgrBuilderMock = mockPoolingBuilder()) {
			httpClientBuilderMock.when(HttpClientBuilder::create).thenReturn(mockHttpClientBuilder);

			JSONObject result = connector.getAuthenticationInfo(null, "https://example.com/auth");

			assertNull(result);
		}
	}

	private MockedStatic<PoolingHttpClientConnectionManagerBuilder> mockPoolingBuilder() {
		MockedStatic<PoolingHttpClientConnectionManagerBuilder> mock = Mockito.mockStatic(PoolingHttpClientConnectionManagerBuilder.class);
		PoolingHttpClientConnectionManagerBuilder mockBuilder = Mockito.mock(PoolingHttpClientConnectionManagerBuilder.class);
		when(mockBuilder.setSSLSocketFactory(any())).thenReturn(mockBuilder);
		when(mockBuilder.build()).thenReturn(Mockito.mock(PoolingHttpClientConnectionManager.class));
		mock.when(PoolingHttpClientConnectionManagerBuilder::create).thenReturn(mockBuilder);
		return mock;
	}
}
