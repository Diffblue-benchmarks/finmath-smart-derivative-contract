package net.finmath.smartcontract.valuation.marketdata;

import net.finmath.smartcontract.product.SmartDerivativeContractDescriptor;
import net.finmath.smartcontract.product.xml.SDCXMLParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

class LaunchAGeneratorTest {

	@Test
	void testResourceLoading() throws Exception {
		// Given: The SDC XML resource should be available on classpath
		InputStream inputStream = LaunchAGenerator.class.getClassLoader()
				.getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract.xml");

		// When: Load and parse the resource
		Assertions.assertNotNull(inputStream, "SDC XML resource should exist");
		String sdcXML = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
		SmartDerivativeContractDescriptor sdc = SDCXMLParser.parse(sdcXML);

		// Then: Verify the descriptor is created and has market data items
		Assertions.assertNotNull(sdc, "SDC descriptor should be created");
		Assertions.assertNotNull(sdc.getMarketdataItemList(), "Market data item list should not be null");
	}
}
