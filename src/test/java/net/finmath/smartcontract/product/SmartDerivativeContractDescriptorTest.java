package net.finmath.smartcontract.product;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for SmartDerivativeContractDescriptor.Party class.
 */
class SmartDerivativeContractDescriptorTest {

    @Test
    void testPartyConstructor() {
        String id = "PARTY-001";
        String name = "Acme Corp";
        String href = "http://example.com/party/001";
        String address = "0x1234567890";

        SmartDerivativeContractDescriptor.Party party = new SmartDerivativeContractDescriptor.Party(id, name, href, address);

        assertNotNull(party);
        assertEquals(id, party.getId());
        assertEquals(name, party.getName());
        assertEquals(href, party.getHref());
        assertEquals(address, party.getAddress());
    }

    @Test
    void testPartyGetId() {
        SmartDerivativeContractDescriptor.Party party = new SmartDerivativeContractDescriptor.Party("ID-1", "Name", "Href", "Address");

        assertEquals("ID-1", party.getId());
    }

    @Test
    void testPartyGetName() {
        SmartDerivativeContractDescriptor.Party party = new SmartDerivativeContractDescriptor.Party("ID-1", "Alice Corp", "Href", "Address");

        assertEquals("Alice Corp", party.getName());
    }

    @Test
    void testPartyGetHref() {
        SmartDerivativeContractDescriptor.Party party = new SmartDerivativeContractDescriptor.Party("ID-1", "Name", "http://test.com", "Address");

        assertEquals("http://test.com", party.getHref());
    }

    @Test
    void testPartyGetAddress() {
        SmartDerivativeContractDescriptor.Party party = new SmartDerivativeContractDescriptor.Party("ID-1", "Name", "Href", "0xABCDEF");

        assertEquals("0xABCDEF", party.getAddress());
    }

    @Test
    void testPartyToString() {
        SmartDerivativeContractDescriptor.Party party = new SmartDerivativeContractDescriptor.Party(
            "PARTY-001",
            "Test Company",
            "http://example.com",
            "0x123456"
        );

        String result = party.toString();

        assertNotNull(result);
        assertTrue(result.contains("Party"));
        assertTrue(result.contains("PARTY-001"));
        assertTrue(result.contains("Test Company"));
        assertTrue(result.contains("http://example.com"));
        assertTrue(result.contains("0x123456"));
    }

    @Test
    void testPartyWithNullValues() {
        SmartDerivativeContractDescriptor.Party party = new SmartDerivativeContractDescriptor.Party(null, null, null, null);

        assertNull(party.getId());
        assertNull(party.getName());
        assertNull(party.getHref());
        assertNull(party.getAddress());
    }

    @Test
    void testPartyWithEmptyStrings() {
        SmartDerivativeContractDescriptor.Party party = new SmartDerivativeContractDescriptor.Party("", "", "", "");

        assertEquals("", party.getId());
        assertEquals("", party.getName());
        assertEquals("", party.getHref());
        assertEquals("", party.getAddress());
    }

    @Test
    void testPartyWithSpecialCharacters() {
        SmartDerivativeContractDescriptor.Party party = new SmartDerivativeContractDescriptor.Party(
            "ID@#$%",
            "Company & Associates, Ltd.",
            "http://example.com/party?id=123&type=corporate",
            "0xABCDEF1234567890"
        );

        assertEquals("ID@#$%", party.getId());
        assertEquals("Company & Associates, Ltd.", party.getName());
        assertTrue(party.getHref().contains("?"));
        assertTrue(party.getHref().contains("&"));
    }

    @Test
    void testPartyWithLongStrings() {
        String longId = "A".repeat(100);
        String longName = "B".repeat(200);
        String longHref = "http://example.com/" + "C".repeat(300);
        String longAddress = "0x" + "D".repeat(400);

        SmartDerivativeContractDescriptor.Party party = new SmartDerivativeContractDescriptor.Party(
            longId, longName, longHref, longAddress
        );

        assertEquals(100, party.getId().length());
        assertEquals(200, party.getName().length());
        assertTrue(party.getHref().length() > 300);
        assertTrue(party.getAddress().length() > 400);
    }
}
