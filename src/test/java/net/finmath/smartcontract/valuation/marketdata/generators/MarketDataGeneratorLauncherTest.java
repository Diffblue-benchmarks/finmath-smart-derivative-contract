package net.finmath.smartcontract.valuation.marketdata.generators;

import net.finmath.smartcontract.product.SmartDerivativeContractDescriptor;
import net.finmath.smartcontract.product.xml.SDCXMLParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MarketDataGeneratorLauncherTest {

	@Test
	void instantiateMarketDataGeneratorWebsocketThrowsRuntimeExceptionOnNullSdc() {
		Properties properties = new Properties();

		assertThrows(RuntimeException.class, () ->
				MarketDataGeneratorLauncher.instantiateMarketDataGeneratorWebsocket(properties, null));
	}

	@Test
	void instantiateMarketDataGeneratorWebsocketThrowsRuntimeExceptionWithEmptyProperties() throws Exception {
		String sdcXML = new String(MarketDataGeneratorLauncherTest.class.getClassLoader()
				.getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract.xml")
				.readAllBytes(), StandardCharsets.UTF_8);
		SmartDerivativeContractDescriptor sdc = SDCXMLParser.parse(sdcXML);

		Properties properties = new Properties();

		assertThrows(RuntimeException.class, () ->
				MarketDataGeneratorLauncher.instantiateMarketDataGeneratorWebsocket(properties, sdc));
	}
}
