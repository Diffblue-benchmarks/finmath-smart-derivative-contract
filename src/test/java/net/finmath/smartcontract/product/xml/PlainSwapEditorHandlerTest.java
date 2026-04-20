package net.finmath.smartcontract.product.xml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.finmath.smartcontract.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlainSwapEditorHandlerTest {

	private static final String TEMPLATE_PATH = "net.finmath.smartcontract.product.xml/smartderivativecontract.xml";
	private static final String SCHEMA_PATH = "net.finmath.smartcontract.product.xml/smartderivativecontract.xsd";
	private static final String PROJECT_VERSION = "1.0.0-test";

	private String loadTemplateXml() throws java.lang.Exception {
		return new String(
				PlainSwapEditorHandlerTest.class.getClassLoader().getResourceAsStream(TEMPLATE_PATH).readAllBytes(),
				StandardCharsets.UTF_8);
	}

	private List<FrontendItemSpec> loadValuationSymbols() throws java.lang.Exception {
		ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
		String fullSymbolListFromTemplate = new ClassPathResource(
				"references" + File.separator + "template2_symbolslist.json").getContentAsString(StandardCharsets.UTF_8);
		return objectMapper.readerForListOf(FrontendItemSpec.class).readValue(fullSymbolListFromTemplate);
	}

	private PlainSwapOperationRequest buildBaseRequest() {
		Counterparty firstCounterparty = new Counterparty().baseUrl("aaa").bicCode("ABCDXXXX")
				.fullName("PartyDoubleTest").dltAddress("0x00001");
		Counterparty secondCounterparty = new Counterparty().baseUrl("bbb").bicCode("EFDGXXXX")
				.fullName("PartyTest").dltAddress("0x00002");

		return new PlainSwapOperationRequest()
				.firstCounterparty(firstCounterparty)
				.secondCounterparty(secondCounterparty)
				.marginBufferAmount(30000.0)
				.terminationFeeAmount(10000.0)
				.currency("EUR")
				.tradeType("SDCPledgedBalance")
				.uniqueTradeIdentifier("UTI123789")
				.tradeDate(OffsetDateTime.of(LocalDateTime.of(2022, Month.SEPTEMBER, 5, 14, 35), ZoneOffset.UTC))
				.effectiveDate(OffsetDateTime.of(LocalDateTime.of(2022, Month.SEPTEMBER, 7, 14, 35), ZoneOffset.UTC))
				.terminationDate(OffsetDateTime.of(LocalDateTime.of(2032, Month.FEBRUARY, 7, 14, 35), ZoneOffset.UTC))
				.dailySettlementTime("12:00")
				.fixedPayingParty(secondCounterparty)
				.floatingPayingParty(firstCounterparty)
				.fixedRate(3.95)
				.fixedDayCountFraction("30E/360")
				.floatingRateIndex("EURIBOR 6M")
				.floatingDayCountFraction("ACT/360")
				.floatingFixingDayOffset(-2)
				.notionalAmount(10000000.00)
				.marketDataProvider("refinitiv")
				.receiverPartyID("party2")
				.fixPayerPartyID("party2");
	}

	@Test
	void testConstructorWithValuationSymbols() throws java.lang.Exception {
		PlainSwapOperationRequest request = buildBaseRequest();
		request.setValuationSymbols(loadValuationSymbols());
		request.setFixedPaymentFrequency(new PaymentFrequency().period("Y").periodMultiplier(1).fullName("Annual"));
		request.setFloatingPaymentFrequency(new PaymentFrequency().period("M").periodMultiplier(6).fullName("Semiannual"));

		PlainSwapEditorHandler handler = new PlainSwapEditorHandler(request, loadTemplateXml(), SCHEMA_PATH, PROJECT_VERSION);

		assertNotNull(handler);
		assertNotNull(handler.getContract());
		assertEquals("party2", handler.getContract().getReceiverPartyID());
	}

	@Test
	void testConstructorWithoutValuationSymbols() throws java.lang.Exception {
		PlainSwapOperationRequest request = buildBaseRequest();
		request.setValuationSymbols(null);
		request.setFixedPaymentFrequency(new PaymentFrequency().period("Y").periodMultiplier(1).fullName("Annual"));
		request.setFloatingPaymentFrequency(new PaymentFrequency().period("M").periodMultiplier(6).fullName("Semiannual"));

		PlainSwapEditorHandler handler = new PlainSwapEditorHandler(request, loadTemplateXml(), SCHEMA_PATH, PROJECT_VERSION);

		assertNotNull(handler);
		assertNotNull(handler.getContract());
	}

	@Test
	void testConstructorWithNullPaymentFrequencies() throws java.lang.Exception {
		PlainSwapOperationRequest request = buildBaseRequest();
		request.setValuationSymbols(loadValuationSymbols());
		request.setFixedPaymentFrequency(null);
		request.setFloatingPaymentFrequency(null);

		PlainSwapEditorHandler handler = new PlainSwapEditorHandler(request, loadTemplateXml(), SCHEMA_PATH, PROJECT_VERSION);

		assertNotNull(handler);
		assertNotNull(handler.getContract());
	}

	@Test
	void testConstructorWithFixPayerPartyNotMatchingParty2() throws java.lang.Exception {
		PlainSwapOperationRequest request = buildBaseRequest();
		request.setValuationSymbols(loadValuationSymbols());
		request.setFixPayerPartyID("party1");
		request.setFixedPaymentFrequency(new PaymentFrequency().period("Y").periodMultiplier(1).fullName("Annual"));
		request.setFloatingPaymentFrequency(new PaymentFrequency().period("M").periodMultiplier(6).fullName("Semiannual"));

		PlainSwapEditorHandler handler = new PlainSwapEditorHandler(request, loadTemplateXml(), SCHEMA_PATH, PROJECT_VERSION);

		assertNotNull(handler);
	}

	@Test
	void testConstructorWithNullUniqueTradeIdentifier() throws java.lang.Exception {
		PlainSwapOperationRequest request = buildBaseRequest();
		request.setUniqueTradeIdentifier(null);
		request.setValuationSymbols(loadValuationSymbols());
		request.setFixedPaymentFrequency(new PaymentFrequency().period("Y").periodMultiplier(1).fullName("Annual"));
		request.setFloatingPaymentFrequency(new PaymentFrequency().period("M").periodMultiplier(6).fullName("Semiannual"));

		PlainSwapEditorHandler handler = new PlainSwapEditorHandler(request, loadTemplateXml(), SCHEMA_PATH, PROJECT_VERSION);

		assertEquals("tbd", handler.getContract().getUniqueTradeIdentifier());
	}

	@Test
	void testGetContractAsXmlString() throws java.lang.Exception {
		PlainSwapOperationRequest request = buildBaseRequest();
		request.setValuationSymbols(loadValuationSymbols());
		request.setFixedPaymentFrequency(new PaymentFrequency().period("Y").periodMultiplier(1).fullName("Annual"));
		request.setFloatingPaymentFrequency(new PaymentFrequency().period("M").periodMultiplier(6).fullName("Semiannual"));

		PlainSwapEditorHandler handler = new PlainSwapEditorHandler(request, loadTemplateXml(), SCHEMA_PATH, PROJECT_VERSION);
		String xmlString = handler.getContractAsXmlString();

		assertNotNull(xmlString);
		assertFalse(xmlString.isEmpty());
		assertTrue(xmlString.contains("EUR"));
	}

	@Test
	void testGetContract() throws java.lang.Exception {
		PlainSwapOperationRequest request = buildBaseRequest();
		request.setValuationSymbols(loadValuationSymbols());
		request.setFixedPaymentFrequency(new PaymentFrequency().period("Y").periodMultiplier(1).fullName("Annual"));
		request.setFloatingPaymentFrequency(new PaymentFrequency().period("M").periodMultiplier(6).fullName("Semiannual"));

		PlainSwapEditorHandler handler = new PlainSwapEditorHandler(request, loadTemplateXml(), SCHEMA_PATH, PROJECT_VERSION);
		Smartderivativecontract contract = handler.getContract();

		assertNotNull(contract);
		assertEquals("EUR", contract.getSettlementCurrency());
		assertEquals("SDCPledgedBalance", contract.getTradeType());
		assertEquals("UTI123789", contract.getUniqueTradeIdentifier());
	}

	@Test
	void testConstructorWithEmptyValuationSymbols() throws java.lang.Exception {
		PlainSwapOperationRequest request = buildBaseRequest();
		request.setValuationSymbols(List.of());
		request.setFixedPaymentFrequency(new PaymentFrequency().period("Y").periodMultiplier(1).fullName("Annual"));
		request.setFloatingPaymentFrequency(new PaymentFrequency().period("M").periodMultiplier(6).fullName("Semiannual"));

		PlainSwapEditorHandler handler = new PlainSwapEditorHandler(request, loadTemplateXml(), SCHEMA_PATH, PROJECT_VERSION);

		assertNotNull(handler);
		assertNotNull(handler.getContract());
	}

	@Test
	void testContractPartiesAreSet() throws java.lang.Exception {
		PlainSwapOperationRequest request = buildBaseRequest();
		request.setValuationSymbols(loadValuationSymbols());
		request.setFixedPaymentFrequency(new PaymentFrequency().period("Y").periodMultiplier(1).fullName("Annual"));
		request.setFloatingPaymentFrequency(new PaymentFrequency().period("M").periodMultiplier(6).fullName("Semiannual"));

		PlainSwapEditorHandler handler = new PlainSwapEditorHandler(request, loadTemplateXml(), SCHEMA_PATH, PROJECT_VERSION);
		Smartderivativecontract contract = handler.getContract();

		assertNotNull(contract.getParties());
		assertEquals(2, contract.getParties().getParty().size());
		assertEquals("PartyDoubleTest", contract.getParties().getParty().get(0).getName());
		assertEquals("PartyTest", contract.getParties().getParty().get(1).getName());
		assertEquals("0x00001", contract.getParties().getParty().get(0).getAddress());
		assertEquals("0x00002", contract.getParties().getParty().get(1).getAddress());
	}

	@Test
	void testValuationHeaderIsSet() throws java.lang.Exception {
		PlainSwapOperationRequest request = buildBaseRequest();
		request.setValuationSymbols(loadValuationSymbols());
		request.setFixedPaymentFrequency(new PaymentFrequency().period("Y").periodMultiplier(1).fullName("Annual"));
		request.setFloatingPaymentFrequency(new PaymentFrequency().period("M").periodMultiplier(6).fullName("Semiannual"));

		PlainSwapEditorHandler handler = new PlainSwapEditorHandler(request, loadTemplateXml(), SCHEMA_PATH, PROJECT_VERSION);
		Smartderivativecontract contract = handler.getContract();

		assertNotNull(contract.getValuation());
		assertEquals("net.finmath", contract.getValuation().getArtefact().getGroupId());
		assertEquals("finmath-smart-derivative-contract", contract.getValuation().getArtefact().getArtifactId());
		assertEquals(PROJECT_VERSION, contract.getValuation().getArtefact().getVersion());
	}

	@Test
	void testConstructorWithQuarterlyFloatingFrequency() throws java.lang.Exception {
		PlainSwapOperationRequest request = buildBaseRequest();
		request.setValuationSymbols(loadValuationSymbols());
		request.setFixedPaymentFrequency(new PaymentFrequency().period("Y").periodMultiplier(1).fullName("Annual"));
		request.setFloatingPaymentFrequency(new PaymentFrequency().period("M").periodMultiplier(3).fullName("Quarterly"));

		PlainSwapEditorHandler handler = new PlainSwapEditorHandler(request, loadTemplateXml(), SCHEMA_PATH, PROJECT_VERSION);

		assertNotNull(handler);
	}
}
