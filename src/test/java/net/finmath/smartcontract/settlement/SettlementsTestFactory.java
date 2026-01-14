package net.finmath.smartcontract.settlement;

import com.diffblue.cover.annotations.InterestingTestFactory;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Factory class to provide test instances for Settlements and related classes.
 */
public class SettlementsTestFactory {

    /**
     * Creates a SettlementInfo instance with valid BigDecimal values.
     * This avoids NumberFormatException issues during construction.
     */
    @InterestingTestFactory
    public static SettlementInfo createSettlementInfo() {
        return new SettlementInfo("testKey", new BigDecimal("100.50"));
    }

    /**
     * Creates a SettlementInfo instance with zero value.
     */
    @InterestingTestFactory
    public static SettlementInfo createSettlementInfoWithZero() {
        return new SettlementInfo("zeroKey", BigDecimal.ZERO);
    }

    /**
     * Creates a SettlementInfo instance with large value.
     */
    @InterestingTestFactory
    public static SettlementInfo createSettlementInfoWithLargeValue() {
        return new SettlementInfo("largeKey", new BigDecimal("999999.99"));
    }

    /**
     * Creates a Settlements instance with valid Settlement objects that won't cause NumberFormatException.
     * This factory helps test Settlements.getNext() method.
     */
    @InterestingTestFactory
    public static Settlements createSettlementsWithValidBigDecimals() {
        Settlements settlements = new Settlements();
        List<Settlement> settlementList = new ArrayList<>();

        // Create first settlement with valid BigDecimal values
        Settlement settlement1 = new Settlement();
        settlement1.setTradeId("TRADE-001");
        settlement1.setSettlementType(Settlement.SettlementType.INITIAL);
        settlement1.setCurrency("USD");
        settlement1.setMarginValue(new BigDecimal("1000.00"));
        settlement1.setSettlementNPV(new BigDecimal("5000.00"));
        settlement1.setSettlementNPVPrevious(new BigDecimal("4000.00"));
        settlement1.setSettlementNPVNext(new BigDecimal("5100.00"));
        settlement1.setSettlementTime(ZonedDateTime.now());
        settlement1.setSettlementTimeNext(ZonedDateTime.now().plusDays(1));

        List<BigDecimal> marginLimits1 = new ArrayList<>();
        marginLimits1.add(new BigDecimal("500.00"));
        marginLimits1.add(new BigDecimal("1500.00"));
        settlement1.setMarginLimits(marginLimits1);

        // Create second settlement with valid BigDecimal values
        Settlement settlement2 = new Settlement();
        settlement2.setTradeId("TRADE-001");
        settlement2.setSettlementType(Settlement.SettlementType.REGULAR);
        settlement2.setCurrency("USD");
        settlement2.setMarginValue(new BigDecimal("1100.00"));
        settlement2.setSettlementNPV(new BigDecimal("5100.00"));
        settlement2.setSettlementNPVPrevious(new BigDecimal("5000.00"));
        settlement2.setSettlementNPVNext(new BigDecimal("5200.00"));
        settlement2.setSettlementTime(ZonedDateTime.now().plusDays(1));
        settlement2.setSettlementTimeNext(ZonedDateTime.now().plusDays(2));

        List<BigDecimal> marginLimits2 = new ArrayList<>();
        marginLimits2.add(new BigDecimal("600.00"));
        marginLimits2.add(new BigDecimal("1600.00"));
        settlement2.setMarginLimits(marginLimits2);

        // Create third settlement with valid BigDecimal values
        Settlement settlement3 = new Settlement();
        settlement3.setTradeId("TRADE-001");
        settlement3.setSettlementType(Settlement.SettlementType.TERMINAL);
        settlement3.setCurrency("USD");
        settlement3.setMarginValue(new BigDecimal("1200.00"));
        settlement3.setSettlementNPV(new BigDecimal("5200.00"));
        settlement3.setSettlementNPVPrevious(new BigDecimal("5100.00"));
        settlement3.setSettlementTime(ZonedDateTime.now().plusDays(2));

        List<BigDecimal> marginLimits3 = new ArrayList<>();
        marginLimits3.add(new BigDecimal("700.00"));
        marginLimits3.add(new BigDecimal("1700.00"));
        settlement3.setMarginLimits(marginLimits3);

        settlementList.add(settlement1);
        settlementList.add(settlement2);
        settlementList.add(settlement3);

        settlements.setSettlements(settlementList);
        return settlements;
    }
}
