package net.finmath.smartcontract.product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SmartDerivativeContractDescriptorPartyTest {

	@Test
	void testGetName() {
		SmartDerivativeContractDescriptor.Party party = new SmartDerivativeContractDescriptor.Party("id1", "Alice", "href1", "addr1");

		Assertions.assertEquals("Alice", party.getName());
	}

	@Test
	void testGetHref() {
		SmartDerivativeContractDescriptor.Party party = new SmartDerivativeContractDescriptor.Party("id1", "Alice", "href1", "addr1");

		Assertions.assertEquals("href1", party.getHref());
	}

	@Test
	void testGetAddress() {
		SmartDerivativeContractDescriptor.Party party = new SmartDerivativeContractDescriptor.Party("id1", "Alice", "href1", "addr1");

		Assertions.assertEquals("addr1", party.getAddress());
	}

	@Test
	void testToString() {
		SmartDerivativeContractDescriptor.Party party = new SmartDerivativeContractDescriptor.Party("id1", "Alice", "href1", "addr1");

		String result = party.toString();

		Assertions.assertTrue(result.contains("id1"));
		Assertions.assertTrue(result.contains("Alice"));
		Assertions.assertTrue(result.contains("href1"));
		Assertions.assertTrue(result.contains("addr1"));
	}
}
