package net.finmath.smartcontract.product;

import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SmartDerivativeContractDescriptorTest {

	private SmartDerivativeContractDescriptor createDescriptor() throws Exception {
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		org.w3c.dom.Node underlying = doc.createElement("underlying");

		return new SmartDerivativeContractDescriptor(
				"DLT-001",
				"0xABC",
				"UTI-001",
				LocalDate.of(2024, 1, 15),
				OffsetTime.of(17, 0, 0, 0, ZoneOffset.ofHours(1)),
				List.of(
						new SmartDerivativeContractDescriptor.Party("p1", "Party1", "href1", "addr1"),
						new SmartDerivativeContractDescriptor.Party("p2", "Party2", "href2", "addr2")
				),
				Map.of("p1", 10000.0, "p2", 10000.0),
				Map.of("p1", 500.0, "p2", 500.0),
				"p1",
				underlying,
				List.of(new CalibrationDataItem.Spec("key1", "curve1", "product1", "5Y")),
				"EUR",
				"refinitiv",
				"SWAP"
		);
	}

	@Test
	void getters_shouldReturnCorrectValues() throws Exception {
		SmartDerivativeContractDescriptor desc = createDescriptor();

		assertEquals("DLT-001", desc.getDltTradeId());
		assertEquals("0xABC", desc.getDltAddress());
		assertEquals("UTI-001", desc.getUniqueTradeIdentifier());
		assertEquals(LocalDate.of(2024, 1, 15), desc.getTradeDate());
		assertEquals("EUR", desc.getCurrency());
		assertEquals("refinitiv", desc.getMarketDataProvider());
		assertEquals("SWAP", desc.getTradeType());
		assertEquals("p1", desc.getUnderlyingReceiverPartyID());
	}

	@Test
	void counterparties_shouldHaveTwoEntries() throws Exception {
		SmartDerivativeContractDescriptor desc = createDescriptor();
		assertEquals(2, desc.getCounterparties().size());
		assertEquals("p1", desc.getCounterparties().get(0).getId());
		assertEquals("Party1", desc.getCounterparties().get(0).getName());
	}

	@Test
	void marginAccount_shouldReturnCorrectValue() throws Exception {
		SmartDerivativeContractDescriptor desc = createDescriptor();
		assertEquals(10000.0, desc.getMarginAccount("p1"));
		assertEquals(10000.0, desc.getMarginAccount("p2"));
	}

	@Test
	void penaltyFee_shouldReturnCorrectValue() throws Exception {
		SmartDerivativeContractDescriptor desc = createDescriptor();
		assertEquals(500.0, desc.getPenaltyFee("p1"));
	}

	@Test
	void partyToString_shouldContainFields() {
		SmartDerivativeContractDescriptor.Party party =
				new SmartDerivativeContractDescriptor.Party("p1", "PartyName", "href", "address");
		String str = party.toString();
		assertTrue(str.contains("p1"));
		assertTrue(str.contains("PartyName"));
	}

	@Test
	void constructor_shouldRejectSingleCounterparty() throws Exception {
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		org.w3c.dom.Node underlying = doc.createElement("underlying");

		assertThrows(IllegalArgumentException.class, () ->
				new SmartDerivativeContractDescriptor(
						"DLT-001", "0xABC", "UTI-001",
						LocalDate.of(2024, 1, 15),
						OffsetTime.of(17, 0, 0, 0, ZoneOffset.ofHours(1)),
						List.of(new SmartDerivativeContractDescriptor.Party("p1", "Party1", "href1", "addr1")),
						Map.of("p1", 10000.0, "p2", 10000.0),
						Map.of("p1", 500.0, "p2", 500.0),
						"p1", underlying, List.of(), "EUR", "refinitiv", "SWAP"
				));
	}
}
