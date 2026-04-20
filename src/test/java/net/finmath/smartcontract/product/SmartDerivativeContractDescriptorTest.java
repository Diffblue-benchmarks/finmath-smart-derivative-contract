package net.finmath.smartcontract.product;

import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SmartDerivativeContractDescriptorTest {

	private Node createDummyNode() throws Exception {
		String xml = "<underlying>test</underlying>";
		Document doc = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder()
				.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
		return doc.getDocumentElement();
	}

	private SmartDerivativeContractDescriptor createDescriptor() throws Exception {
		String dltTradeId = "trade-123";
		String dltAddress = "0xABC";
		String uniqueTradeIdentifier = "UTI-456";
		LocalDate tradeDate = LocalDate.of(2024, 1, 15);
		OffsetTime settlementTime = OffsetTime.of(17, 0, 0, 0, ZoneOffset.UTC);

		SmartDerivativeContractDescriptor.Party party1 = new SmartDerivativeContractDescriptor.Party("party1", "Party One", "href1", "addr1");
		SmartDerivativeContractDescriptor.Party party2 = new SmartDerivativeContractDescriptor.Party("party2", "Party Two", "href2", "addr2");
		List<SmartDerivativeContractDescriptor.Party> counterparties = List.of(party1, party2);

		Map<String, Double> marginAccounts = Map.of("party1", 1000.0, "party2", 2000.0);
		Map<String, Double> penaltyFees = Map.of("party1", 100.0, "party2", 200.0);

		String receiverPartyID = "party1";
		Node underlying = createDummyNode();

		CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("key1", "curve1", "product1", "1Y");
		List<CalibrationDataItem.Spec> marketdataItems = List.of(spec);

		String currency = "EUR";
		String marketDataProvider = "refinitiv";
		String tradeType = "SWAP";

		return new SmartDerivativeContractDescriptor(
				dltTradeId, dltAddress, uniqueTradeIdentifier, tradeDate, settlementTime,
				counterparties, marginAccounts, penaltyFees, receiverPartyID, underlying,
				marketdataItems, currency, marketDataProvider, tradeType
		);
	}

	@Test
	void testConstructorAndGetters() throws Exception {
		SmartDerivativeContractDescriptor descriptor = createDescriptor();

		assertEquals("trade-123", descriptor.getDltTradeId());
		assertEquals("0xABC", descriptor.getDltAddress());
		assertEquals("UTI-456", descriptor.getUniqueTradeIdentifier());
		assertEquals(LocalDate.of(2024, 1, 15), descriptor.getTradeDate());
		assertEquals(OffsetTime.of(17, 0, 0, 0, ZoneOffset.UTC), descriptor.getSettlementTime());
		assertEquals(2, descriptor.getCounterparties().size());
		assertEquals("party1", descriptor.getCounterparties().get(0).getId());
		assertEquals("party2", descriptor.getCounterparties().get(1).getId());
		assertNotNull(descriptor.getUnderlying());
		assertEquals("party1", descriptor.getUnderlyingReceiverPartyID());
		assertEquals(1, descriptor.getMarketdataItemList().size());
		assertEquals("EUR", descriptor.getCurrency());
		assertEquals("refinitiv", descriptor.getMarketDataProvider());
		assertEquals("SWAP", descriptor.getTradeType());
	}

	@Test
	void testGetMarginAccount() throws Exception {
		SmartDerivativeContractDescriptor descriptor = createDescriptor();

		assertEquals(1000.0, descriptor.getMarginAccount("party1"));
		assertEquals(2000.0, descriptor.getMarginAccount("party2"));
		assertNull(descriptor.getMarginAccount("unknown"));
	}

	@Test
	void testGetPenaltyFee() throws Exception {
		SmartDerivativeContractDescriptor descriptor = createDescriptor();

		assertEquals(100.0, descriptor.getPenaltyFee("party1"));
		assertEquals(200.0, descriptor.getPenaltyFee("party2"));
		assertNull(descriptor.getPenaltyFee("unknown"));
	}
}
