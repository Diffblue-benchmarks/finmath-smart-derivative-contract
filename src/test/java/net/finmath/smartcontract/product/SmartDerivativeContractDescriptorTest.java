package net.finmath.smartcontract.product;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for SmartDerivativeContractDescriptor and its inner classes.
 */
class SmartDerivativeContractDescriptorTest {

	@Test
	void testPartyConstructorAndGetters() {
		final String id = "party1";
		final String name = "Test Party";
		final String href = "http://example.com/party1";
		final String address = "0x1234567890abcdef";

		final SmartDerivativeContractDescriptor.Party party = new SmartDerivativeContractDescriptor.Party(id, name, href, address);

		assertEquals(id, party.getId());
		assertEquals(name, party.getName());
		assertEquals(href, party.getHref());
		assertEquals(address, party.getAddress());
	}

	@Test
	void testPartyToString() {
		final String id = "party1";
		final String name = "Test Party";
		final String href = "http://example.com/party1";
		final String address = "0x1234567890abcdef";

		final SmartDerivativeContractDescriptor.Party party = new SmartDerivativeContractDescriptor.Party(id, name, href, address);

		final String result = party.toString();

		assertTrue(result.contains("Party {"));
		assertTrue(result.contains("id='party1'"));
		assertTrue(result.contains("name='Test Party'"));
		assertTrue(result.contains("href='http://example.com/party1'"));
		assertTrue(result.contains("address='0x1234567890abcdef'"));
	}

	@Test
	void testPartyWithNullValues() {
		final SmartDerivativeContractDescriptor.Party party = new SmartDerivativeContractDescriptor.Party(null, null, null, null);

		assertEquals(null, party.getId());
		assertEquals(null, party.getName());
		assertEquals(null, party.getHref());
		assertEquals(null, party.getAddress());
	}

	@Test
	void testPartyToStringWithNullValues() {
		final SmartDerivativeContractDescriptor.Party party = new SmartDerivativeContractDescriptor.Party(null, null, null, null);

		final String result = party.toString();

		assertTrue(result.contains("Party {"));
		assertTrue(result.contains("id='null'"));
		assertTrue(result.contains("name='null'"));
		assertTrue(result.contains("href='null'"));
		assertTrue(result.contains("address='null'"));
	}
}
