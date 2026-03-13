package net.finmath.smartcontract.settlement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Settlements class.
 */
class SettlementsTest {

    private Settlements settlements;
    private Settlement settlement1;
    private Settlement settlement2;
    private Settlement settlement3;

    @BeforeEach
    void setUp() {
        settlements = new Settlements();

        settlement1 = new Settlement();
        settlement1.setTradeId("TRADE-001");

        settlement2 = new Settlement();
        settlement2.setTradeId("TRADE-002");

        settlement3 = new Settlement();
        settlement3.setTradeId("TRADE-003");
    }

    @Test
    void testSetAndGetSettlements() {
        List<Settlement> settlementList = Arrays.asList(settlement1, settlement2, settlement3);
        settlements.setSettlements(settlementList);

        assertEquals(settlementList, settlements.getSettlements());
        assertEquals(3, settlements.getSettlements().size());
    }

    @Test
    void testGetPrevious_FirstSettlement() {
        settlements.setSettlements(Arrays.asList(settlement1, settlement2, settlement3));

        Optional<Settlement> previous = settlements.getPrevious(settlement1);

        assertFalse(previous.isPresent());
    }

    @Test
    void testGetPrevious_MiddleSettlement() {
        settlements.setSettlements(Arrays.asList(settlement1, settlement2, settlement3));

        Optional<Settlement> previous = settlements.getPrevious(settlement2);

        assertTrue(previous.isPresent());
        assertEquals(settlement1, previous.get());
    }

    @Test
    void testGetPrevious_LastSettlement() {
        settlements.setSettlements(Arrays.asList(settlement1, settlement2, settlement3));

        Optional<Settlement> previous = settlements.getPrevious(settlement3);

        assertTrue(previous.isPresent());
        assertEquals(settlement2, previous.get());
    }

    @Test
    void testGetNext_FirstSettlement() {
        settlements.setSettlements(Arrays.asList(settlement1, settlement2, settlement3));

        Optional<Settlement> next = settlements.getNext(settlement1);

        assertTrue(next.isPresent());
        assertEquals(settlement2, next.get());
    }

    @Test
    void testGetNext_MiddleSettlement() {
        settlements.setSettlements(Arrays.asList(settlement1, settlement2, settlement3));

        Optional<Settlement> next = settlements.getNext(settlement2);

        assertTrue(next.isPresent());
        assertEquals(settlement3, next.get());
    }

    @Test
    void testGetNext_LastSettlement() {
        settlements.setSettlements(Arrays.asList(settlement1, settlement2, settlement3));

        Optional<Settlement> next = settlements.getNext(settlement3);

        assertFalse(next.isPresent());
    }

    @Test
    void testGetPrevious_SingleSettlement() {
        settlements.setSettlements(Arrays.asList(settlement1));

        Optional<Settlement> previous = settlements.getPrevious(settlement1);

        assertFalse(previous.isPresent());
    }

    @Test
    void testGetNext_SingleSettlement() {
        settlements.setSettlements(Arrays.asList(settlement1));

        Optional<Settlement> next = settlements.getNext(settlement1);

        assertFalse(next.isPresent());
    }

    @Test
    void testEmptySettlementsList() {
        settlements.setSettlements(new ArrayList<>());

        assertNotNull(settlements.getSettlements());
        assertEquals(0, settlements.getSettlements().size());
    }

    @Test
    void testGetPrevious_SettlementNotInList() {
        settlements.setSettlements(Arrays.asList(settlement1, settlement2));
        Settlement outsideSettlement = new Settlement();

        Optional<Settlement> previous = settlements.getPrevious(outsideSettlement);

        assertFalse(previous.isPresent());
    }

    @Test
    void testGetNext_SettlementNotInList() {
        settlements.setSettlements(Arrays.asList(settlement1, settlement2));
        Settlement outsideSettlement = new Settlement();

        Optional<Settlement> next = settlements.getNext(outsideSettlement);

        // When settlement is not found (indexOf returns -1),
        // the condition currentIndex > settlements.size() - 2 becomes -1 > 0, which is false
        // So it returns settlements.get(0), not empty
        assertTrue(next.isPresent());
    }
}
