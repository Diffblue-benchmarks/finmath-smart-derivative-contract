package net.finmath.smartcontract.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TradeUtilsTest {

	@Test
	void getUniqueTradeId_shouldStartWithIDPrefix() {
		String tradeId = TradeUtils.getUniqueTradeId();
		assertTrue(tradeId.startsWith("ID-"));
	}

	@Test
	void getUniqueTradeId_shouldHaveCorrectLength() {
		String tradeId = TradeUtils.getUniqueTradeId();
		// "ID-" (3 chars) + 17 chars = 20 total
		assertEquals(20, tradeId.length());
	}

	@Test
	void getUniqueTradeId_shouldNotContainHyphensAfterPrefix() {
		String tradeId = TradeUtils.getUniqueTradeId();
		String afterPrefix = tradeId.substring(3);
		assertFalse(afterPrefix.contains("-"));
	}

	@Test
	void getUniqueTradeId_shouldBeUnique() {
		String id1 = TradeUtils.getUniqueTradeId();
		String id2 = TradeUtils.getUniqueTradeId();
		assertNotEquals(id1, id2);
	}
}
