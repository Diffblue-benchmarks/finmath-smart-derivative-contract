/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.util;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for TradeUtils.
 * Tests all methods with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class TradeUtilsClaudeTest {

	// ========== Tests for getUniqueTradeId method ==========

	/**
	 * Test that getUniqueTradeId returns a non-null value.
	 */
	@Test
	void testGetUniqueTradeId_ReturnsNonNull() {
		// Act
		String tradeId = TradeUtils.getUniqueTradeId();

		// Assert
		assertNotNull(tradeId, "getUniqueTradeId should return a non-null value");
	}

	/**
	 * Test that getUniqueTradeId returns a value that starts with "ID-".
	 */
	@Test
	void testGetUniqueTradeId_StartsWithPrefix() {
		// Act
		String tradeId = TradeUtils.getUniqueTradeId();

		// Assert
		assertTrue(tradeId.startsWith("ID-"),
				"Trade ID should start with 'ID-' prefix, but got: " + tradeId);
	}

	/**
	 * Test that getUniqueTradeId returns a value with correct length.
	 * Expected format: "ID-" (3 chars) + 17 chars from UUID = 20 chars total.
	 */
	@Test
	void testGetUniqueTradeId_HasCorrectLength() {
		// Act
		String tradeId = TradeUtils.getUniqueTradeId();

		// Assert
		assertEquals(20, tradeId.length(),
				"Trade ID should have length 20 (ID- + 17 chars), but got length: " + tradeId.length());
	}

	/**
	 * Test that the part after "ID-" contains only valid hexadecimal characters.
	 * UUID.randomUUID().toString() produces hexadecimal characters (0-9, a-f).
	 */
	@Test
	void testGetUniqueTradeId_ContainsOnlyHexCharacters() {
		// Act
		String tradeId = TradeUtils.getUniqueTradeId();
		String hexPart = tradeId.substring(3); // Remove "ID-" prefix

		// Assert
		assertTrue(hexPart.matches("[0-9a-f]+"),
				"The part after 'ID-' should contain only hexadecimal characters (0-9, a-f), but got: " + hexPart);
	}

	/**
	 * Test that getUniqueTradeId generates unique IDs on successive calls.
	 * Tests uniqueness by generating multiple IDs and checking for duplicates.
	 */
	@Test
	void testGetUniqueTradeId_GeneratesUniqueIds() {
		// Arrange
		int numberOfIds = 1000;
		Set<String> uniqueIds = new HashSet<>();

		// Act
		for (int i = 0; i < numberOfIds; i++) {
			String tradeId = TradeUtils.getUniqueTradeId();
			uniqueIds.add(tradeId);
		}

		// Assert
		assertEquals(numberOfIds, uniqueIds.size(),
				"All generated trade IDs should be unique. Generated " + numberOfIds +
				" IDs but got " + uniqueIds.size() + " unique ones");
	}

	/**
	 * Test that getUniqueTradeId consistently produces the correct format
	 * across multiple invocations.
	 */
	@Test
	void testGetUniqueTradeId_ConsistentFormat() {
		// Arrange
		int numberOfTests = 100;

		// Act & Assert
		for (int i = 0; i < numberOfTests; i++) {
			String tradeId = TradeUtils.getUniqueTradeId();

			assertNotNull(tradeId, "Trade ID should not be null");
			assertTrue(tradeId.startsWith("ID-"), "Trade ID should start with 'ID-'");
			assertEquals(20, tradeId.length(), "Trade ID should have length 20");

			String hexPart = tradeId.substring(3);
			assertEquals(17, hexPart.length(), "Hex part should have length 17");
			assertTrue(hexPart.matches("[0-9a-f]+"),
					"Hex part should contain only hexadecimal characters");
		}
	}

	/**
	 * Test that the hex part does not contain any dashes.
	 * This verifies that the replace("-", "") operation works correctly.
	 */
	@Test
	void testGetUniqueTradeId_NoDashesInHexPart() {
		// Act
		String tradeId = TradeUtils.getUniqueTradeId();
		String hexPart = tradeId.substring(3); // Remove "ID-" prefix

		// Assert
		assertFalse(hexPart.contains("-"),
				"The hex part should not contain any dashes, but got: " + hexPart);
	}

	/**
	 * Test that the entire trade ID does not contain spaces.
	 */
	@Test
	void testGetUniqueTradeId_NoSpaces() {
		// Act
		String tradeId = TradeUtils.getUniqueTradeId();

		// Assert
		assertFalse(tradeId.contains(" "),
				"Trade ID should not contain spaces, but got: " + tradeId);
	}

	/**
	 * Test that getUniqueTradeId is not empty.
	 */
	@Test
	void testGetUniqueTradeId_NotEmpty() {
		// Act
		String tradeId = TradeUtils.getUniqueTradeId();

		// Assert
		assertFalse(tradeId.isEmpty(), "Trade ID should not be empty");
	}

	/**
	 * Test pattern matching for the complete trade ID format.
	 * Pattern: "ID-" followed by exactly 17 hexadecimal characters.
	 */
	@Test
	void testGetUniqueTradeId_MatchesExpectedPattern() {
		// Act
		String tradeId = TradeUtils.getUniqueTradeId();

		// Assert
		assertTrue(tradeId.matches("ID-[0-9a-f]{17}"),
				"Trade ID should match pattern 'ID-[0-9a-f]{17}', but got: " + tradeId);
	}

	/**
	 * Test that multiple calls produce different IDs in rapid succession.
	 * This is important for systems that generate multiple trade IDs quickly.
	 */
	@Test
	void testGetUniqueTradeId_DifferentIdsInRapidSuccession() {
		// Act
		String id1 = TradeUtils.getUniqueTradeId();
		String id2 = TradeUtils.getUniqueTradeId();
		String id3 = TradeUtils.getUniqueTradeId();

		// Assert
		assertNotEquals(id1, id2, "First and second IDs should be different");
		assertNotEquals(id2, id3, "Second and third IDs should be different");
		assertNotEquals(id1, id3, "First and third IDs should be different");
	}

	/**
	 * Test statistical properties: verify reasonable distribution of characters.
	 * Generate many IDs and check that different characters appear in the hex part.
	 */
	@Test
	void testGetUniqueTradeId_CharacterDistribution() {
		// Arrange
		int numberOfIds = 100;
		Set<Character> observedChars = new HashSet<>();

		// Act
		for (int i = 0; i < numberOfIds; i++) {
			String tradeId = TradeUtils.getUniqueTradeId();
			String hexPart = tradeId.substring(3);

			for (char c : hexPart.toCharArray()) {
				observedChars.add(c);
			}
		}

		// Assert
		// With 100 IDs * 17 chars = 1700 random hex characters,
		// we should see most of the 16 possible hex digits (0-9, a-f)
		assertTrue(observedChars.size() >= 10,
				"Expected to see at least 10 different hex characters in " + numberOfIds +
				" IDs, but only saw: " + observedChars.size());
	}
}
