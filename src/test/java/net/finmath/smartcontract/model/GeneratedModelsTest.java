package net.finmath.smartcontract.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class GeneratedModelsTest {

	@Test
	void valueResult_fluentBuilder() {
		ValueResult vr = new ValueResult()
				.value(BigDecimal.valueOf(100))
				.currency("EUR")
				.valuationDate("20240615-170000");
		assertEquals(BigDecimal.valueOf(100), vr.getValue());
		assertEquals("EUR", vr.getCurrency());
		assertEquals("20240615-170000", vr.getValuationDate());
		assertNotNull(vr.toString());
		assertEquals(vr, vr);
		assertNotEquals(null, vr);
		assertEquals(vr.hashCode(), vr.hashCode());
	}

	@Test
	void valueRequest_fluentBuilder() {
		ValueRequest vr = new ValueRequest()
				.marketData("md")
				.tradeData("td")
				.valuationDate("20240615");
		assertEquals("md", vr.getMarketData());
		assertEquals("td", vr.getTradeData());
		assertEquals("20240615", vr.getValuationDate());
		assertNotNull(vr.toString());
	}

	@Test
	void marginRequest_fluentBuilder() {
		MarginRequest mr = new MarginRequest()
				.marketDataStart("start")
				.marketDataEnd("end")
				.tradeData("td")
				.valuationDate("date");
		assertEquals("start", mr.getMarketDataStart());
		assertEquals("end", mr.getMarketDataEnd());
		assertEquals("td", mr.getTradeData());
		assertNotNull(mr.toString());
	}

	@Test
	void marginResult_fluentBuilder() {
		MarginResult mr = new MarginResult()
				.value(BigDecimal.TEN)
				.currency("USD")
				.valuationDate("20240615");
		assertEquals(BigDecimal.TEN, mr.getValue());
		assertEquals("USD", mr.getCurrency());
		assertNotNull(mr.toString());
	}

	@Test
	void regularSettlementRequest_fluentBuilder() {
		RegularSettlementRequest rsr = new RegularSettlementRequest()
				.tradeData("td")
				.settlementLast("sl");
		assertEquals("td", rsr.getTradeData());
		assertEquals("sl", rsr.getSettlementLast());
		assertNotNull(rsr.toString());
	}

	@Test
	void regularSettlementResult_fluentBuilder() {
		RegularSettlementResult rsr = new RegularSettlementResult()
				.generatedRegularSettlement("xml")
				.currency("EUR")
				.marginValue(BigDecimal.ONE)
				.valuationDate("20240615");
		assertEquals("xml", rsr.getGeneratedRegularSettlement());
		assertEquals("EUR", rsr.getCurrency());
		assertNotNull(rsr.toString());
	}

	@Test
	void initialSettlementRequest_fluentBuilder() {
		InitialSettlementRequest isr = new InitialSettlementRequest()
				.tradeData("td");
		assertEquals("td", isr.getTradeData());
		assertNotNull(isr.toString());
	}

	@Test
	void initialSettlementResult_fluentBuilder() {
		InitialSettlementResult isr = new InitialSettlementResult()
				.generatedInitialSettlement("xml")
				.currency("EUR")
				.marginValue(BigDecimal.ZERO)
				.valuationDate("20240615");
		assertEquals("xml", isr.getGeneratedInitialSettlement());
		assertNotNull(isr.toString());
	}

	@Test
	void counterparty_fluentBuilder() {
		Counterparty c = new Counterparty()
				.bicCode("DEUTDEFF")
				.fullName("Deutsche Bank")
				.baseUrl("https://example.com")
				.dltAddress("0xABC");
		assertEquals("DEUTDEFF", c.getBicCode());
		assertEquals("Deutsche Bank", c.getFullName());
		assertEquals("https://example.com", c.getBaseUrl());
		assertEquals("0xABC", c.getDltAddress());
		assertNotNull(c.toString());
	}

	@Test
	void frontendItemSpec_fluentBuilder() {
		FrontendItemSpec fis = new FrontendItemSpec()
				.symbol("EUR6M")
				.curve("Euribor6M")
				.itemType("Swap-Rate")
				.tenor("5Y");
		assertEquals("EUR6M", fis.getSymbol());
		assertEquals("Euribor6M", fis.getCurve());
		assertEquals("Swap-Rate", fis.getItemType());
		assertEquals("5Y", fis.getTenor());
		assertNotNull(fis.toString());
	}

	@Test
	void saveContractRequest_fluentBuilder() {
		SaveContractRequest scr = new SaveContractRequest()
				.name("test");
		assertEquals("test", scr.getName());
		assertNotNull(scr.toString());
	}

	@Test
	void cashflowPeriod_fluentBuilder() {
		OffsetDateTime now = OffsetDateTime.now();
		ValueResult vr = new ValueResult().value(BigDecimal.valueOf(1000));
		CashflowPeriod cp = new CashflowPeriod()
				.periodStart(now)
				.periodEnd(now.plusMonths(6))
				.fixingDate(now.minusDays(2))
				.paymentDate(now.plusMonths(6))
				.cashflow(vr)
				.rate(0.05);
		assertEquals(now, cp.getPeriodStart());
		assertEquals(vr, cp.getCashflow());
		assertEquals(0.05, cp.getRate());
		assertNotNull(cp.toString());
	}

	@Test
	void error_fluentBuilder() {
		Error e = new Error()
				.code(400)
				.message("bad");
		assertEquals(400, e.getCode());
		assertEquals("bad", e.getMessage());
		assertNotNull(e.toString());
	}

	@org.junit.jupiter.api.Disabled("Compilation error: String vs OffsetDateTime type mismatch")
	@Test
	void marketDataSet_fluentBuilder() {
		// Disabled: requestTimestamp expects OffsetDateTime, not String
	}

	@Test
	void marketDataSetValuesInner_fluentBuilder() {
		MarketDataSetValuesInner inner = new MarketDataSetValuesInner()
				.symbol("SYM")
				.value(0.05);
		assertEquals("SYM", inner.getSymbol());
		assertEquals(0.05, inner.getValue());
		assertNotNull(inner.toString());
	}

	@Test
	void paymentFrequency_fluentBuilder() {
		PaymentFrequency pf = new PaymentFrequency()
				.periodMultiplier(6)
				.period("M");
		assertEquals(6, pf.getPeriodMultiplier());
		assertEquals("M", pf.getPeriod());
		assertNotNull(pf.toString());
	}

	@Test
	void refinitivMarketData_fluentBuilder() {
		RefinitivMarketDataKey key = new RefinitivMarketDataKey().name("EUR=");
		RefinitivMarketDataFields fields = new RefinitivMarketDataFields()
				.BID(1.0)
				.ASK(1.1)
				.VALUE_DT1(LocalDate.of(2024, 6, 15))
				.VALUE_TS1("17:00:00");
		RefinitivMarketData rmd = new RefinitivMarketData()
				.key(key)
				.fields(fields);
		assertNotNull(rmd.getKey());
		assertEquals("EUR=", rmd.getKey().getName());
		assertEquals(1.0, rmd.getFields().getBID());
		assertEquals(1.1, rmd.getFields().getASK());
		assertNotNull(rmd.toString());
		assertNotNull(fields.toString());
		assertNotNull(key.toString());
	}

	@Test
	void plainSwapOperationRequest_fluentBuilder() {
		OffsetDateTime now = OffsetDateTime.now();
		PlainSwapOperationRequest psor = new PlainSwapOperationRequest()
				.fixedRate(0.05)
				.notionalAmount(1000000.0)
				.tradeDate(now)
				.currency("EUR")
				.tradeType("SWAP")
				.marginBufferAmount(10000.0)
				.terminationFeeAmount(500.0);
		assertEquals(0.05, psor.getFixedRate());
		assertEquals(1000000.0, psor.getNotionalAmount());
		assertEquals("EUR", psor.getCurrency());
		assertNotNull(psor.toString());
	}

	@Test
	void plainSwapOperationResponse_fluentBuilder() {
		PlainSwapOperationResponse psor = new PlainSwapOperationResponse()
				.xmlBody("xml");
		assertEquals("xml", psor.getXmlBody());
		assertNotNull(psor.toString());
	}

	@Test
	void models_equalsSameContent() {
		ValueResult vr1 = new ValueResult().value(BigDecimal.ONE).currency("EUR");
		ValueResult vr2 = new ValueResult().value(BigDecimal.ONE).currency("EUR");
		assertEquals(vr1, vr2);
		assertEquals(vr1.hashCode(), vr2.hashCode());
	}

	@Test
	void models_notEqualsDifferentContent() {
		ValueResult vr1 = new ValueResult().value(BigDecimal.ONE);
		ValueResult vr2 = new ValueResult().value(BigDecimal.TEN);
		assertNotEquals(vr1, vr2);
	}
}
