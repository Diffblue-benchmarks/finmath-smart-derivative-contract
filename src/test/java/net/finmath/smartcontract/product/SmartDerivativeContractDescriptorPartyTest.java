package net.finmath.smartcontract.product;

import net.finmath.smartcontract.product.SmartDerivativeContractDescriptor.Party;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SmartDerivativeContractDescriptorPartyTest {

	@Test
	void testPartyConstructorAndGetters() {
		Party party = new Party("party1", "Alice", "http://example.com", "123 Main St");

		assertEquals("party1", party.getId());
		assertEquals("Alice", party.getName());
		assertEquals("http://example.com", party.getHref());
		assertEquals("123 Main St", party.getAddress());
	}

	@Test
	void testPartyToString() {
		Party party = new Party("p2", "Bob", "http://bob.com", "456 Elm St");

		String result = party.toString();
		assertTrue(result.contains("p2"));
		assertTrue(result.contains("Bob"));
		assertTrue(result.contains("http://bob.com"));
		assertTrue(result.contains("456 Elm St"));
		assertTrue(result.startsWith("Party {"));
	}
}
