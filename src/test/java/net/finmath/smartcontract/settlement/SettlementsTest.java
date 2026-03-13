package net.finmath.smartcontract.settlement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SettlementsTest {

	private Settlements settlements;
	private Settlement s1;
	private Settlement s2;
	private Settlement s3;

	@BeforeEach
	void setUp() {
		s1 = new Settlement();
		s1.setTradeId("trade1");

		s2 = new Settlement();
		s2.setTradeId("trade2");

		s3 = new Settlement();
		s3.setTradeId("trade3");

		settlements = new Settlements();
		settlements.setSettlements(List.of(s1, s2, s3));
	}

	@Test
	void getPrevious_shouldReturnEmpty_forFirstElement() {
		Optional<Settlement> previous = settlements.getPrevious(s1);
		assertTrue(previous.isEmpty());
	}

	@Test
	void getPrevious_shouldReturnPrevious_forMiddleElement() {
		Optional<Settlement> previous = settlements.getPrevious(s2);
		assertTrue(previous.isPresent());
		assertEquals("trade1", previous.get().getTradeId());
	}

	@Test
	void getPrevious_shouldReturnPrevious_forLastElement() {
		Optional<Settlement> previous = settlements.getPrevious(s3);
		assertTrue(previous.isPresent());
		assertEquals("trade2", previous.get().getTradeId());
	}

	@Test
	void getNext_shouldReturnNext_forFirstElement() {
		Optional<Settlement> next = settlements.getNext(s1);
		assertTrue(next.isPresent());
		assertEquals("trade2", next.get().getTradeId());
	}

	@Test
	void getNext_shouldReturnNext_forMiddleElement() {
		Optional<Settlement> next = settlements.getNext(s2);
		assertTrue(next.isPresent());
		assertEquals("trade3", next.get().getTradeId());
	}

	@Test
	void getNext_shouldReturnEmpty_forLastElement() {
		Optional<Settlement> next = settlements.getNext(s3);
		assertTrue(next.isEmpty());
	}

	@Test
	void getSettlements_shouldReturnAllSettlements() {
		assertEquals(3, settlements.getSettlements().size());
	}
}
