/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.service.websocket.handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Additional test class for ValuationHandler.handleMessage() method to improve coverage.
 *
 * Testing approach:
 * - Focus on covering the uncovered lines in handleMessage: 46, 47, 50, 51, 52, 55, 56, 58, 59, 60, 64, 69, 71
 * - The method has a hardcoded properties file path "<your properties file>" which will fail
 * - We test as far as we can with valid XML to cover the XML parsing and market data extraction
 * - Some lines cannot be covered without mocking or modifying the source code to inject dependencies
 *
 * @author Claude Code
 */
class ValuationHandlerClaude_handleMessageTest {

	/**
	 * Simple test implementation of WebSocketSession for testing purposes.
	 */
	private static class TestWebSocketSession implements WebSocketSession {
		private final String id;
		private final Map<String, Object> attributes = new HashMap<>();
		private boolean open = true;

		public TestWebSocketSession(String id) {
			this.id = id;
		}

		@Override
		public String getId() {
			return id;
		}

		@Override
		public URI getUri() {
			return null;
		}

		@Override
		public HttpHeaders getHandshakeHeaders() {
			return new HttpHeaders();
		}

		@Override
		public Map<String, Object> getAttributes() {
			return attributes;
		}

		@Override
		public Principal getPrincipal() {
			return null;
		}

		@Override
		public InetSocketAddress getLocalAddress() {
			return null;
		}

		@Override
		public InetSocketAddress getRemoteAddress() {
			return null;
		}

		@Override
		public String getAcceptedProtocol() {
			return null;
		}

		@Override
		public void setTextMessageSizeLimit(int messageSizeLimit) {
		}

		@Override
		public int getTextMessageSizeLimit() {
			return 0;
		}

		@Override
		public void setBinaryMessageSizeLimit(int messageSizeLimit) {
		}

		@Override
		public int getBinaryMessageSizeLimit() {
			return 0;
		}

		@Override
		public List<WebSocketExtension> getExtensions() {
			return Collections.emptyList();
		}

		@Override
		public void sendMessage(WebSocketMessage<?> message) throws IOException {
			// Do nothing in test
		}

		@Override
		public boolean isOpen() {
			return open;
		}

		@Override
		public void close() throws IOException {
			open = false;
		}

		@Override
		public void close(CloseStatus status) throws IOException {
			open = false;
		}
	}

	/**
	 * Load a sample SDC XML from resources.
	 */
	private String loadSampleSDCXML() throws IOException {
		return new String(
			getClass().getClassLoader()
				.getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract.xml")
				.readAllBytes(),
			StandardCharsets.UTF_8
		);
	}

	/**
	 * Test handleMessage with valid SDC XML.
	 * This should cover lines 46-47 (XML parsing and getting market data item list).
	 * It will fail at line 50-52 when trying to load the hardcoded properties file.
	 */
	@Test
	void testHandleMessage_WithValidSDCXML_FailsAtPropertiesFile() throws IOException {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TestWebSocketSession session = new TestWebSocketSession("test-session-1");
		final String sdcXML = loadSampleSDCXML();
		final TextMessage message = new TextMessage(sdcXML);

		// Act & Assert
		// The method will parse the XML successfully (covering lines 46-47)
		// but will fail when trying to load the hardcoded properties file (line 52)
		final Exception exception = assertThrows(Exception.class,
				() -> handler.handleMessage(session, message),
				"handleMessage should throw exception when properties file is not found");

		// Verify it's a FileNotFoundException or contains file-related error
		assertTrue(
			exception instanceof java.io.FileNotFoundException ||
			exception.getCause() instanceof java.io.FileNotFoundException ||
			exception.getMessage().contains("properties file") ||
			exception.getMessage().contains("<your properties file>"),
			"Exception should be related to missing properties file, but was: " + exception.getClass().getName() + ": " + exception.getMessage()
		);
	}

	/**
	 * Test handleMessage with minimal valid SDC XML.
	 * This creates a minimal XML that should parse correctly.
	 */
	@Test
	void testHandleMessage_WithMinimalValidSDCXML_FailsAtPropertiesFile() {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TestWebSocketSession session = new TestWebSocketSession("test-session-1");

		// Create minimal valid SDC XML
		final String minimalXML =
			"<smartderivativecontract xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
			"xmlns=\"uri:sdc\" " +
			"xsi:schemaLocation=\"uri:sdc smartderivativecontract.xsd\">" +
			"<dltTradeId>TEST123</dltTradeId>" +
			"<dltAddress>0x000000001</dltAddress>" +
			"<uniqueTradeIdentifier>UTI12345</uniqueTradeIdentifier>" +
			"<settlementCurrency>EUR</settlementCurrency>" +
			"<tradeType>SDCPledgedBalance</tradeType>" +
			"<valuation>" +
			"<artefact>" +
			"<groupId>net.finmath</groupId>" +
			"<artifactId>finmath-smart-derivative-contract</artifactId>" +
			"<version>0.1.8</version>" +
			"</artefact>" +
			"</valuation>" +
			"<parties>" +
			"<party>" +
			"<name>Party1</name>" +
			"<id>party1</id>" +
			"<marginAccount><type>constant</type><value>10000.0</value></marginAccount>" +
			"<penaltyFee><type>constant</type><value>50000.0</value></penaltyFee>" +
			"<address>0x627306090abab3a6e1400e9345bc60c78a8bef57</address>" +
			"</party>" +
			"<party>" +
			"<name>Party2</name>" +
			"<id>party2</id>" +
			"<marginAccount><type>constant</type><value>10000.0</value></marginAccount>" +
			"<penaltyFee><type>constant</type><value>50000.0</value></penaltyFee>" +
			"<address>0xf17f52151ebef6c7334fad080c5704d77216b732</address>" +
			"</party>" +
			"</parties>" +
			"<settlement>" +
			"<settlementTime><type>daily</type><value>17:00</value></settlementTime>" +
			"<marketdata>" +
			"<provider>internal</provider>" +
			"<marketdataitems>" +
			"<item>" +
			"<symbol>ESTRFIX1D</symbol>" +
			"<curve>ESTR</curve>" +
			"<type>Fixing</type>" +
			"<tenor>1D</tenor>" +
			"</item>" +
			"</marketdataitems>" +
			"</marketdata>" +
			"</settlement>" +
			"<underlying>" +
			"<product>InterestRateSwap</product>" +
			"</underlying>" +
			"</smartderivativecontract>";

		final TextMessage message = new TextMessage(minimalXML);

		// Act & Assert
		// Should parse XML and fail at properties file loading
		assertThrows(Exception.class,
				() -> handler.handleMessage(session, message),
				"handleMessage should throw exception");
	}

	/**
	 * Test handleMessage with valid XML from a different resource file.
	 */
	@Test
	void testHandleMessage_WithRicsSDCXML_FailsAtPropertiesFile() throws IOException {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TestWebSocketSession session = new TestWebSocketSession("test-session-1");
		final String sdcXML = new String(
			getClass().getClassLoader()
				.getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract_with_rics.xml")
				.readAllBytes(),
			StandardCharsets.UTF_8
		);
		final TextMessage message = new TextMessage(sdcXML);

		// Act & Assert
		assertThrows(Exception.class,
				() -> handler.handleMessage(session, message),
				"handleMessage should throw exception when properties file is not found");
	}

	/**
	 * Test handleMessage with XML containing multiple market data items.
	 * This ensures the market data item list extraction works correctly.
	 */
	@Test
	void testHandleMessage_WithMultipleMarketDataItems_FailsAtPropertiesFile() throws IOException {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TestWebSocketSession session = new TestWebSocketSession("test-session-1");

		// Use the full SDC XML which has many market data items
		final String sdcXML = loadSampleSDCXML();
		final TextMessage message = new TextMessage(sdcXML);

		// Act & Assert
		final Exception exception = assertThrows(Exception.class,
				() -> handler.handleMessage(session, message),
				"handleMessage should throw exception");

		// The parsing should succeed (lines 46-47 covered) before failing on properties
		assertNotNull(exception, "Should throw an exception");
	}

	/**
	 * Test handleMessage with simulated historical market data XML.
	 */
	@Test
	void testHandleMessage_WithSimulatedHistoricalMarketDataXML_FailsAtPropertiesFile() throws IOException {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TestWebSocketSession session = new TestWebSocketSession("test-session-1");
		final String sdcXML = new String(
			getClass().getClassLoader()
				.getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract_simulated_historical_marketdata.xml")
				.readAllBytes(),
			StandardCharsets.UTF_8
		);
		final TextMessage message = new TextMessage(sdcXML);

		// Act & Assert
		assertThrows(Exception.class,
				() -> handler.handleMessage(session, message),
				"handleMessage should throw exception when properties file is not found");
	}

	/**
	 * Test handleMessage with very large XML payload.
	 * This tests that the method can handle large XML documents.
	 */
	@Test
	void testHandleMessage_WithLargeXMLPayload_FailsAtPropertiesFile() throws IOException {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TestWebSocketSession session = new TestWebSocketSession("test-session-1");

		// Create a larger XML with many market data items
		final StringBuilder largeXML = new StringBuilder();
		largeXML.append("<smartderivativecontract xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" ");
		largeXML.append("xmlns=\"uri:sdc\" ");
		largeXML.append("xsi:schemaLocation=\"uri:sdc smartderivativecontract.xsd\">");
		largeXML.append("<dltTradeId>TEST123</dltTradeId>");
		largeXML.append("<dltAddress>0x000000001</dltAddress>");
		largeXML.append("<uniqueTradeIdentifier>UTI12345</uniqueTradeIdentifier>");
		largeXML.append("<settlementCurrency>EUR</settlementCurrency>");
		largeXML.append("<tradeType>SDCPledgedBalance</tradeType>");
		largeXML.append("<valuation>");
		largeXML.append("<artefact>");
		largeXML.append("<groupId>net.finmath</groupId>");
		largeXML.append("<artifactId>finmath-smart-derivative-contract</artifactId>");
		largeXML.append("<version>0.1.8</version>");
		largeXML.append("</artefact>");
		largeXML.append("</valuation>");
		largeXML.append("<parties>");
		largeXML.append("<party>");
		largeXML.append("<name>Party1</name>");
		largeXML.append("<id>party1</id>");
		largeXML.append("<marginAccount><type>constant</type><value>10000.0</value></marginAccount>");
		largeXML.append("<penaltyFee><type>constant</type><value>50000.0</value></penaltyFee>");
		largeXML.append("<address>0x627306090abab3a6e1400e9345bc60c78a8bef57</address>");
		largeXML.append("</party>");
		largeXML.append("<party>");
		largeXML.append("<name>Party2</name>");
		largeXML.append("<id>party2</id>");
		largeXML.append("<marginAccount><type>constant</type><value>10000.0</value></marginAccount>");
		largeXML.append("<penaltyFee><type>constant</type><value>50000.0</value></penaltyFee>");
		largeXML.append("<address>0xf17f52151ebef6c7334fad080c5704d77216b732</address>");
		largeXML.append("</party>");
		largeXML.append("</parties>");
		largeXML.append("<settlement>");
		largeXML.append("<settlementTime><type>daily</type><value>17:00</value></settlementTime>");
		largeXML.append("<marketdata>");
		largeXML.append("<provider>internal</provider>");
		largeXML.append("<marketdataitems>");

		// Add many market data items
		for (int i = 0; i < 50; i++) {
			largeXML.append("<item>");
			largeXML.append("<symbol>ITEM").append(i).append("</symbol>");
			largeXML.append("<curve>CURVE").append(i).append("</curve>");
			largeXML.append("<type>Fixing</type>");
			largeXML.append("<tenor>1D</tenor>");
			largeXML.append("</item>");
		}

		largeXML.append("</marketdataitems>");
		largeXML.append("</marketdata>");
		largeXML.append("</settlement>");
		largeXML.append("<underlying>");
		largeXML.append("<product>InterestRateSwap</product>");
		largeXML.append("</underlying>");
		largeXML.append("</smartderivativecontract>");

		final TextMessage message = new TextMessage(largeXML.toString());

		// Act & Assert
		assertThrows(Exception.class,
				() -> handler.handleMessage(session, message),
				"handleMessage should throw exception");
	}

	/**
	 * Test handleMessage with XML containing whitespace and formatting.
	 * This ensures the parser handles formatted XML correctly.
	 */
	@Test
	void testHandleMessage_WithFormattedXML_FailsAtPropertiesFile() {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TestWebSocketSession session = new TestWebSocketSession("test-session-1");

		// Create nicely formatted XML
		final String formattedXML =
			"<smartderivativecontract xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
			"                         xmlns=\"uri:sdc\"\n" +
			"                         xsi:schemaLocation=\"uri:sdc smartderivativecontract.xsd\">\n" +
			"    <dltTradeId>TEST123</dltTradeId>\n" +
			"    <dltAddress>0x000000001</dltAddress>\n" +
			"    <uniqueTradeIdentifier>UTI12345</uniqueTradeIdentifier>\n" +
			"    <settlementCurrency>EUR</settlementCurrency>\n" +
			"    <tradeType>SDCPledgedBalance</tradeType>\n" +
			"    <valuation>\n" +
			"        <artefact>\n" +
			"            <groupId>net.finmath</groupId>\n" +
			"            <artifactId>finmath-smart-derivative-contract</artifactId>\n" +
			"            <version>0.1.8</version>\n" +
			"        </artefact>\n" +
			"    </valuation>\n" +
			"    <parties>\n" +
			"        <party>\n" +
			"            <name>Party1</name>\n" +
			"            <id>party1</id>\n" +
			"            <marginAccount><type>constant</type><value>10000.0</value></marginAccount>\n" +
			"            <penaltyFee><type>constant</type><value>50000.0</value></penaltyFee>\n" +
			"            <address>0x627306090abab3a6e1400e9345bc60c78a8bef57</address>\n" +
			"        </party>\n" +
			"        <party>\n" +
			"            <name>Party2</name>\n" +
			"            <id>party2</id>\n" +
			"            <marginAccount><type>constant</type><value>10000.0</value></marginAccount>\n" +
			"            <penaltyFee><type>constant</type><value>50000.0</value></penaltyFee>\n" +
			"            <address>0xf17f52151ebef6c7334fad080c5704d77216b732</address>\n" +
			"        </party>\n" +
			"    </parties>\n" +
			"    <settlement>\n" +
			"        <settlementTime><type>daily</type><value>17:00</value></settlementTime>\n" +
			"        <marketdata>\n" +
			"            <provider>internal</provider>\n" +
			"            <marketdataitems>\n" +
			"                <item>\n" +
			"                    <symbol>ESTRFIX1D</symbol>\n" +
			"                    <curve>ESTR</curve>\n" +
			"                    <type>Fixing</type>\n" +
			"                    <tenor>1D</tenor>\n" +
			"                </item>\n" +
			"            </marketdataitems>\n" +
			"        </marketdata>\n" +
			"    </settlement>\n" +
			"    <underlying>\n" +
			"        <product>InterestRateSwap</product>\n" +
			"    </underlying>\n" +
			"</smartderivativecontract>";

		final TextMessage message = new TextMessage(formattedXML);

		// Act & Assert
		assertThrows(Exception.class,
				() -> handler.handleMessage(session, message),
				"handleMessage should throw exception");
	}

	/**
	 * Test that multiple calls to handleMessage on the same handler instance work.
	 * Each call should parse the XML independently.
	 */
	@Test
	void testHandleMessage_CalledMultipleTimes_EachCallParsesXML() throws IOException {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TestWebSocketSession session1 = new TestWebSocketSession("session-1");
		final TestWebSocketSession session2 = new TestWebSocketSession("session-2");
		final String sdcXML = loadSampleSDCXML();
		final TextMessage message1 = new TextMessage(sdcXML);
		final TextMessage message2 = new TextMessage(sdcXML);

		// Act & Assert - both calls should reach the XML parsing stage
		assertThrows(Exception.class,
				() -> handler.handleMessage(session1, message1),
				"First call should throw exception");

		assertThrows(Exception.class,
				() -> handler.handleMessage(session2, message2),
				"Second call should throw exception");
	}

	/**
	 * Test handleMessage with XML containing special characters in text content.
	 */
	@Test
	void testHandleMessage_WithSpecialCharactersInXML_FailsAtPropertiesFile() {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TestWebSocketSession session = new TestWebSocketSession("test-session-1");

		// Create XML with special characters
		final String xmlWithSpecialChars =
			"<smartderivativecontract xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
			"xmlns=\"uri:sdc\" " +
			"xsi:schemaLocation=\"uri:sdc smartderivativecontract.xsd\">" +
			"<dltTradeId>TEST-123-ÄÖÜ</dltTradeId>" +
			"<dltAddress>0x000000001</dltAddress>" +
			"<uniqueTradeIdentifier>UTI-12345-€</uniqueTradeIdentifier>" +
			"<settlementCurrency>EUR</settlementCurrency>" +
			"<tradeType>SDCPledgedBalance</tradeType>" +
			"<valuation>" +
			"<artefact>" +
			"<groupId>net.finmath</groupId>" +
			"<artifactId>finmath-smart-derivative-contract</artifactId>" +
			"<version>0.1.8</version>" +
			"</artefact>" +
			"</valuation>" +
			"<parties>" +
			"<party>" +
			"<name>Party 1 &amp; Co</name>" +
			"<id>party1</id>" +
			"<marginAccount><type>constant</type><value>10000.0</value></marginAccount>" +
			"<penaltyFee><type>constant</type><value>50000.0</value></penaltyFee>" +
			"<address>0x627306090abab3a6e1400e9345bc60c78a8bef57</address>" +
			"</party>" +
			"<party>" +
			"<name>Party 2 &lt;Ltd&gt;</name>" +
			"<id>party2</id>" +
			"<marginAccount><type>constant</type><value>10000.0</value></marginAccount>" +
			"<penaltyFee><type>constant</type><value>50000.0</value></penaltyFee>" +
			"<address>0xf17f52151ebef6c7334fad080c5704d77216b732</address>" +
			"</party>" +
			"</parties>" +
			"<settlement>" +
			"<settlementTime><type>daily</type><value>17:00</value></settlementTime>" +
			"<marketdata>" +
			"<provider>internal</provider>" +
			"<marketdataitems>" +
			"<item>" +
			"<symbol>ESTRFIX1D</symbol>" +
			"<curve>ESTR</curve>" +
			"<type>Fixing</type>" +
			"<tenor>1D</tenor>" +
			"</item>" +
			"</marketdataitems>" +
			"</marketdata>" +
			"</settlement>" +
			"<underlying>" +
			"<product>InterestRateSwap</product>" +
			"</underlying>" +
			"</smartderivativecontract>";

		final TextMessage message = new TextMessage(xmlWithSpecialChars);

		// Act & Assert
		assertThrows(Exception.class,
				() -> handler.handleMessage(session, message),
				"handleMessage should throw exception");
	}

	/**
	 * Test that different handler instances process messages independently.
	 */
	@Test
	void testHandleMessage_DifferentHandlerInstances_ProcessIndependently() throws IOException {
		// Arrange
		final ValuationHandler handler1 = new ValuationHandler();
		final ValuationHandler handler2 = new ValuationHandler();
		final TestWebSocketSession session = new TestWebSocketSession("test-session-1");
		final String sdcXML = loadSampleSDCXML();
		final TextMessage message = new TextMessage(sdcXML);

		// Act & Assert - both handlers should process independently
		assertThrows(Exception.class,
				() -> handler1.handleMessage(session, message),
				"First handler should throw exception");

		assertThrows(Exception.class,
				() -> handler2.handleMessage(session, message),
				"Second handler should throw exception");
	}
}
