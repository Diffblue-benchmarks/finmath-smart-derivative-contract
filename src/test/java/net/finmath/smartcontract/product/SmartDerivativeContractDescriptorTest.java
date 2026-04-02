package net.finmath.smartcontract.product;

import net.finmath.smartcontract.product.xml.SDCXMLParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

class SmartDerivativeContractDescriptorTest {

	private SmartDerivativeContractDescriptor getDescriptor() throws Exception {
		String sdcXML = new String(SmartDerivativeContractDescriptorTest.class.getClassLoader()
				.getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract.xml")
				.readAllBytes(), StandardCharsets.UTF_8);
		return SDCXMLParser.parse(sdcXML);
	}

	@Test
	void testGetDltAddress() throws Exception {
		SmartDerivativeContractDescriptor sdc = getDescriptor();

		Assertions.assertEquals("0x000000001", sdc.getDltAddress());
	}

	@Test
	void testGetUniqueTradeIdentifier() throws Exception {
		SmartDerivativeContractDescriptor sdc = getDescriptor();

		Assertions.assertEquals("UTI12345", sdc.getUniqueTradeIdentifier());
	}

	@Test
	void testGetPenaltyFee() throws Exception {
		SmartDerivativeContractDescriptor sdc = getDescriptor();

		Assertions.assertEquals(50000.0, sdc.getPenaltyFee("party1"), 1e-10);
		Assertions.assertEquals(50000.0, sdc.getPenaltyFee("party2"), 1e-10);
	}

	@Test
	void testGetTradeType() throws Exception {
		SmartDerivativeContractDescriptor sdc = getDescriptor();

		Assertions.assertEquals("SDCPledgedBalance", sdc.getTradeType());
	}

	@Test
	void testGetInitialSettlementDate() throws Exception {
		SmartDerivativeContractDescriptor sdc = getDescriptor();

		Assertions.assertEquals("2011-12-03T10:15:30", sdc.getInitialSettlementDate());
	}
}
