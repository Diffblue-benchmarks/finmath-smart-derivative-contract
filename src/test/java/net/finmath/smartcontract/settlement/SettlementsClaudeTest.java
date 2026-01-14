/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.settlement;

import net.finmath.smartcontract.settlement.Settlement.SettlementType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Settlements.
 * Tests all methods with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class SettlementsClaudeTest {

	/**
	 * Test the default constructor creates a non-null instance.
	 */
	@Test
	void testConstructor() {
		Settlements settlements = new Settlements();
		assertNotNull(settlements, "Constructor should create a non-null Settlements instance");
	}

	/**
	 * Test getSettlements and setSettlements methods.
	 */
	@Test
	void testGetSetSettlements() {
		Settlements settlements = new Settlements();

		// Test with null value (initial state)
		assertNull(settlements.getSettlements(), "Initial settlements should be null");

		// Test setting an empty list
		List<Settlement> emptyList = new ArrayList<>();
		settlements.setSettlements(emptyList);
		assertNotNull(settlements.getSettlements(), "getSettlements should not be null after setting empty list");
		assertTrue(settlements.getSettlements().isEmpty(), "getSettlements should return empty list");

		// Test setting a list with values
		List<Settlement> settlementList = createSettlementList(3);
		settlements.setSettlements(settlementList);
		assertEquals(settlementList, settlements.getSettlements(), "getSettlements should return the set list");
		assertEquals(3, settlements.getSettlements().size(), "getSettlements should return list with 3 elements");

		// Test setting a different list
		List<Settlement> newSettlementList = createSettlementList(2);
		settlements.setSettlements(newSettlementList);
		assertEquals(newSettlementList, settlements.getSettlements(),
				"getSettlements should return the updated list");
		assertEquals(2, settlements.getSettlements().size(), "getSettlements should return list with 2 elements");

		// Test setting null
		settlements.setSettlements(null);
		assertNull(settlements.getSettlements(), "getSettlements should return null after setting null");
	}

	/**
	 * Test getPrevious method with various scenarios.
	 */
	@Test
	void testGetPrevious() {
		Settlements settlements = new Settlements();
		List<Settlement> settlementList = createSettlementList(5);
		settlements.setSettlements(settlementList);

		// Test getting previous of first element (index 0) - should return empty
		Optional<Settlement> previousOfFirst = settlements.getPrevious(settlementList.get(0));
		assertTrue(previousOfFirst.isEmpty(), "Previous of first element should be empty");

		// Test getting previous of second element (index 1) - should return first element
		Optional<Settlement> previousOfSecond = settlements.getPrevious(settlementList.get(1));
		assertTrue(previousOfSecond.isPresent(), "Previous of second element should be present");
		assertEquals(settlementList.get(0), previousOfSecond.get(),
				"Previous of second element should be first element");

		// Test getting previous of middle element (index 2) - should return previous element
		Optional<Settlement> previousOfMiddle = settlements.getPrevious(settlementList.get(2));
		assertTrue(previousOfMiddle.isPresent(), "Previous of middle element should be present");
		assertEquals(settlementList.get(1), previousOfMiddle.get(),
				"Previous of middle element should be the previous element");

		// Test getting previous of last element (index 4) - should return second-to-last element
		Optional<Settlement> previousOfLast = settlements.getPrevious(settlementList.get(4));
		assertTrue(previousOfLast.isPresent(), "Previous of last element should be present");
		assertEquals(settlementList.get(3), previousOfLast.get(),
				"Previous of last element should be second-to-last element");

		// Test with single element list - should return empty
		settlements.setSettlements(Arrays.asList(settlementList.get(0)));
		Optional<Settlement> previousInSingleList = settlements.getPrevious(settlementList.get(0));
		assertTrue(previousInSingleList.isEmpty(), "Previous in single element list should be empty");
	}

	/**
	 * Test getPrevious with non-existent settlement (not in list).
	 */
	@Test
	void testGetPreviousWithNonExistentSettlement() {
		Settlements settlements = new Settlements();
		List<Settlement> settlementList = createSettlementList(3);
		settlements.setSettlements(settlementList);

		// Create a new settlement that's not in the list
		Settlement notInList = createSettlement("TRADE-999");

		// indexOf will return -1, and -1 <= 0, so should return empty
		Optional<Settlement> previous = settlements.getPrevious(notInList);
		assertTrue(previous.isEmpty(), "Previous of non-existent settlement should be empty");
	}

	/**
	 * Test getNext method with various scenarios.
	 */
	@Test
	void testGetNext() {
		Settlements settlements = new Settlements();
		List<Settlement> settlementList = createSettlementList(5);
		settlements.setSettlements(settlementList);

		// Test getting next of first element (index 0) - should return second element
		Optional<Settlement> nextOfFirst = settlements.getNext(settlementList.get(0));
		assertTrue(nextOfFirst.isPresent(), "Next of first element should be present");
		assertEquals(settlementList.get(1), nextOfFirst.get(),
				"Next of first element should be second element");

		// Test getting next of middle element (index 2) - should return next element
		Optional<Settlement> nextOfMiddle = settlements.getNext(settlementList.get(2));
		assertTrue(nextOfMiddle.isPresent(), "Next of middle element should be present");
		assertEquals(settlementList.get(3), nextOfMiddle.get(),
				"Next of middle element should be the next element");

		// Test getting next of second-to-last element (index 3) - should return last element
		Optional<Settlement> nextOfSecondToLast = settlements.getNext(settlementList.get(3));
		assertTrue(nextOfSecondToLast.isPresent(), "Next of second-to-last element should be present");
		assertEquals(settlementList.get(4), nextOfSecondToLast.get(),
				"Next of second-to-last element should be last element");

		// Test getting next of last element (index 4) - should return empty
		// currentIndex = 4, size = 5, condition is 4 > 5 - 2 (i.e., 4 > 3) which is true
		Optional<Settlement> nextOfLast = settlements.getNext(settlementList.get(4));
		assertTrue(nextOfLast.isEmpty(), "Next of last element should be empty");

		// Test with single element list - should return empty
		settlements.setSettlements(Arrays.asList(settlementList.get(0)));
		Optional<Settlement> nextInSingleList = settlements.getNext(settlementList.get(0));
		assertTrue(nextInSingleList.isEmpty(), "Next in single element list should be empty");
	}

	/**
	 * Test getNext with non-existent settlement (not in list).
	 */
	@Test
	void testGetNextWithNonExistentSettlement() {
		Settlements settlements = new Settlements();
		List<Settlement> settlementList = createSettlementList(3);
		settlements.setSettlements(settlementList);

		// Create a new settlement that's not in the list
		Settlement notInList = createSettlement("TRADE-999");

		// indexOf will return -1, and -1 > 3 - 2 (i.e., -1 > 1) which is false, so it will try to access index 0
		Optional<Settlement> next = settlements.getNext(notInList);
		assertTrue(next.isPresent(), "Next of non-existent settlement should return first element");
		assertEquals(settlementList.get(0), next.get(), "Should return first element when settlement not found");
	}

	/**
	 * Test getPrevious and getNext with two-element list to test boundary conditions.
	 */
	@Test
	void testGetPreviousAndNextWithTwoElementList() {
		Settlements settlements = new Settlements();
		List<Settlement> settlementList = createSettlementList(2);
		settlements.setSettlements(settlementList);

		// Test getPrevious on first element - should be empty
		Optional<Settlement> previousOfFirst = settlements.getPrevious(settlementList.get(0));
		assertTrue(previousOfFirst.isEmpty(), "Previous of first element in two-element list should be empty");

		// Test getPrevious on second element - should return first
		Optional<Settlement> previousOfSecond = settlements.getPrevious(settlementList.get(1));
		assertTrue(previousOfSecond.isPresent(), "Previous of second element in two-element list should be present");
		assertEquals(settlementList.get(0), previousOfSecond.get(),
				"Previous of second element should be first element");

		// Test getNext on first element - should return second
		Optional<Settlement> nextOfFirst = settlements.getNext(settlementList.get(0));
		assertTrue(nextOfFirst.isPresent(), "Next of first element in two-element list should be present");
		assertEquals(settlementList.get(1), nextOfFirst.get(),
				"Next of first element should be second element");

		// Test getNext on second element - should be empty
		// currentIndex = 1, size = 2, condition is 1 > 2 - 2 (i.e., 1 > 0) which is true
		Optional<Settlement> nextOfSecond = settlements.getNext(settlementList.get(1));
		assertTrue(nextOfSecond.isEmpty(), "Next of second element in two-element list should be empty");
	}

	/**
	 * Test navigation through the list using getPrevious and getNext.
	 */
	@Test
	void testNavigationThroughList() {
		Settlements settlements = new Settlements();
		List<Settlement> settlementList = createSettlementList(4);
		settlements.setSettlements(settlementList);

		// Navigate forward from first to last
		Settlement current = settlementList.get(0);
		Optional<Settlement> next1 = settlements.getNext(current);
		assertTrue(next1.isPresent());
		assertEquals(settlementList.get(1), next1.get());

		Optional<Settlement> next2 = settlements.getNext(next1.get());
		assertTrue(next2.isPresent());
		assertEquals(settlementList.get(2), next2.get());

		Optional<Settlement> next3 = settlements.getNext(next2.get());
		assertTrue(next3.isPresent());
		assertEquals(settlementList.get(3), next3.get());

		Optional<Settlement> next4 = settlements.getNext(next3.get());
		assertTrue(next4.isEmpty(), "Should reach end of list");

		// Navigate backward from last to first
		current = settlementList.get(3);
		Optional<Settlement> prev1 = settlements.getPrevious(current);
		assertTrue(prev1.isPresent());
		assertEquals(settlementList.get(2), prev1.get());

		Optional<Settlement> prev2 = settlements.getPrevious(prev1.get());
		assertTrue(prev2.isPresent());
		assertEquals(settlementList.get(1), prev2.get());

		Optional<Settlement> prev3 = settlements.getPrevious(prev2.get());
		assertTrue(prev3.isPresent());
		assertEquals(settlementList.get(0), prev3.get());

		Optional<Settlement> prev4 = settlements.getPrevious(prev3.get());
		assertTrue(prev4.isEmpty(), "Should reach beginning of list");
	}

	/**
	 * Test that getSettlements returns the same list instance (not a copy).
	 */
	@Test
	void testGetSettlementsReturnsSameInstance() {
		Settlements settlements = new Settlements();
		List<Settlement> settlementList = createSettlementList(2);
		settlements.setSettlements(settlementList);

		List<Settlement> retrieved1 = settlements.getSettlements();
		List<Settlement> retrieved2 = settlements.getSettlements();

		assertSame(retrieved1, retrieved2, "getSettlements should return the same instance");
	}

	/**
	 * Test with empty list to ensure boundary conditions are handled.
	 */
	@Test
	void testWithEmptyList() {
		Settlements settlements = new Settlements();
		settlements.setSettlements(new ArrayList<>());

		assertTrue(settlements.getSettlements().isEmpty(), "List should be empty");
	}

	/**
	 * Helper method to create a list of settlements for testing.
	 *
	 * @param count The number of settlements to create
	 * @return A list of Settlement objects
	 */
	private List<Settlement> createSettlementList(int count) {
		List<Settlement> list = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			list.add(createSettlement("TRADE-" + i));
		}
		return list;
	}

	/**
	 * Helper method to create a Settlement object with specified trade ID.
	 *
	 * @param tradeId The trade ID for the settlement
	 * @return A Settlement object
	 */
	private Settlement createSettlement(String tradeId) {
		Settlement settlement = new Settlement();
		settlement.setTradeId(tradeId);
		settlement.setSettlementType(SettlementType.REGULAR);
		settlement.setCurrency("USD");
		settlement.setMarginValue(new BigDecimal("1000.00"));
		settlement.setSettlementTime(ZonedDateTime.of(2024, 1, 15, 10, 0, 0, 0, ZoneOffset.UTC));
		settlement.setSettlementNPV(new BigDecimal("50000.00"));
		return settlement;
	}
}
