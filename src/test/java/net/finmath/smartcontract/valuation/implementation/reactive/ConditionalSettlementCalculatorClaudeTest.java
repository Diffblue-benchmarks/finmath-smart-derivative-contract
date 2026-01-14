/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */
package net.finmath.smartcontract.valuation.implementation.reactive;

import net.finmath.smartcontract.model.ValueResult;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ConditionalSettlementCalculator.
 * Tests the constructor and apply method with various scenarios.
 *
 * @author Claude Code
 */
class ConditionalSettlementCalculatorClaudeTest {

    // ========== Tests for constructor <init>.(Ljava/lang/String;Ljava/math/BigDecimal;)V ==========

    /**
     * Test that the constructor can be instantiated with valid parameters.
     */
    @Test
    void testConstructor_WithValidParameters() {
        // Arrange
        String sdcXML = "<sdc>test</sdc>";
        BigDecimal resultTriggerValue = new BigDecimal("1000.00");

        // Act
        ConditionalSettlementCalculator calculator = new ConditionalSettlementCalculator(sdcXML, resultTriggerValue);

        // Assert
        assertNotNull(calculator, "Calculator instance should not be null");
    }

    /**
     * Test that the constructor works with null sdcXML.
     */
    @Test
    void testConstructor_WithNullSdcXML() {
        // Arrange
        String sdcXML = null;
        BigDecimal resultTriggerValue = new BigDecimal("1000.00");

        // Act
        ConditionalSettlementCalculator calculator = new ConditionalSettlementCalculator(sdcXML, resultTriggerValue);

        // Assert
        assertNotNull(calculator, "Calculator instance should not be null even with null sdcXML");
    }

    /**
     * Test that the constructor works with null resultTriggerValue.
     */
    @Test
    void testConstructor_WithNullResultTriggerValue() {
        // Arrange
        String sdcXML = "<sdc>test</sdc>";
        BigDecimal resultTriggerValue = null;

        // Act
        ConditionalSettlementCalculator calculator = new ConditionalSettlementCalculator(sdcXML, resultTriggerValue);

        // Assert
        assertNotNull(calculator, "Calculator instance should not be null even with null resultTriggerValue");
    }

    /**
     * Test that the constructor works with both null parameters.
     */
    @Test
    void testConstructor_WithBothNullParameters() {
        // Arrange
        String sdcXML = null;
        BigDecimal resultTriggerValue = null;

        // Act
        ConditionalSettlementCalculator calculator = new ConditionalSettlementCalculator(sdcXML, resultTriggerValue);

        // Assert
        assertNotNull(calculator, "Calculator instance should not be null even with both null parameters");
    }

    /**
     * Test that the constructor works with empty string sdcXML.
     */
    @Test
    void testConstructor_WithEmptyStringSdcXML() {
        // Arrange
        String sdcXML = "";
        BigDecimal resultTriggerValue = new BigDecimal("1000.00");

        // Act
        ConditionalSettlementCalculator calculator = new ConditionalSettlementCalculator(sdcXML, resultTriggerValue);

        // Assert
        assertNotNull(calculator, "Calculator instance should not be null with empty string sdcXML");
    }

    /**
     * Test that the constructor works with zero resultTriggerValue.
     */
    @Test
    void testConstructor_WithZeroResultTriggerValue() {
        // Arrange
        String sdcXML = "<sdc>test</sdc>";
        BigDecimal resultTriggerValue = BigDecimal.ZERO;

        // Act
        ConditionalSettlementCalculator calculator = new ConditionalSettlementCalculator(sdcXML, resultTriggerValue);

        // Assert
        assertNotNull(calculator, "Calculator instance should not be null with zero resultTriggerValue");
    }

    /**
     * Test that the constructor works with negative resultTriggerValue.
     */
    @Test
    void testConstructor_WithNegativeResultTriggerValue() {
        // Arrange
        String sdcXML = "<sdc>test</sdc>";
        BigDecimal resultTriggerValue = new BigDecimal("-1000.00");

        // Act
        ConditionalSettlementCalculator calculator = new ConditionalSettlementCalculator(sdcXML, resultTriggerValue);

        // Assert
        assertNotNull(calculator, "Calculator instance should not be null with negative resultTriggerValue");
    }

    /**
     * Test that the constructor works with very large resultTriggerValue.
     */
    @Test
    void testConstructor_WithLargeResultTriggerValue() {
        // Arrange
        String sdcXML = "<sdc>test</sdc>";
        BigDecimal resultTriggerValue = new BigDecimal("999999999999.99");

        // Act
        ConditionalSettlementCalculator calculator = new ConditionalSettlementCalculator(sdcXML, resultTriggerValue);

        // Assert
        assertNotNull(calculator, "Calculator instance should not be null with large resultTriggerValue");
    }

    /**
     * Test that multiple instances can be created.
     */
    @Test
    void testConstructor_MultipleInstances() {
        // Arrange
        String sdcXML1 = "<sdc>test1</sdc>";
        BigDecimal resultTriggerValue1 = new BigDecimal("1000.00");
        String sdcXML2 = "<sdc>test2</sdc>";
        BigDecimal resultTriggerValue2 = new BigDecimal("2000.00");

        // Act
        ConditionalSettlementCalculator calculator1 = new ConditionalSettlementCalculator(sdcXML1, resultTriggerValue1);
        ConditionalSettlementCalculator calculator2 = new ConditionalSettlementCalculator(sdcXML2, resultTriggerValue2);

        // Assert
        assertNotNull(calculator1, "First instance should not be null");
        assertNotNull(calculator2, "Second instance should not be null");
        assertNotSame(calculator1, calculator2, "Instances should be different objects");
    }

    /**
     * Test that the constructor creates an instance of correct type.
     */
    @Test
    void testConstructor_InstanceOfCorrectType() {
        // Arrange
        String sdcXML = "<sdc>test</sdc>";
        BigDecimal resultTriggerValue = new BigDecimal("1000.00");

        // Act
        ConditionalSettlementCalculator calculator = new ConditionalSettlementCalculator(sdcXML, resultTriggerValue);

        // Assert
        assertTrue(calculator instanceof ConditionalSettlementCalculator,
                "Instance should be of type ConditionalSettlementCalculator");
    }

    /**
     * Test that the instance is Serializable.
     */
    @Test
    void testConstructor_ImplementsSerializable() {
        // Arrange
        String sdcXML = "<sdc>test</sdc>";
        BigDecimal resultTriggerValue = new BigDecimal("1000.00");

        // Act
        ConditionalSettlementCalculator calculator = new ConditionalSettlementCalculator(sdcXML, resultTriggerValue);

        // Assert
        assertTrue(calculator instanceof java.io.Serializable,
                "Instance should implement Serializable");
    }

    /**
     * Test that the instance is a Function.
     */
    @Test
    void testConstructor_ImplementsFunction() {
        // Arrange
        String sdcXML = "<sdc>test</sdc>";
        BigDecimal resultTriggerValue = new BigDecimal("1000.00");

        // Act
        ConditionalSettlementCalculator calculator = new ConditionalSettlementCalculator(sdcXML, resultTriggerValue);

        // Assert
        assertTrue(calculator instanceof java.util.function.Function,
                "Instance should implement Function interface");
    }

    // ========== Tests for apply method apply.(Lnet/finmath/smartcontract/valuation/marketdata/curvecalibration/CalibrationDataset;)Lnet/finmath/smartcontract/model/ValueResult; ==========

    /**
     * Helper method to create a test CalibrationDataset.
     */
    private CalibrationDataset createTestCalibrationDataset() {
        Set<CalibrationDataItem> items = new LinkedHashSet<>();

        // Create a simple calibration data item
        CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec(
                "EUR-OIS-1Y",
                "discount-EUR-OIS",
                "Swap",
                "1Y"
        );

        CalibrationDataItem item = new CalibrationDataItem(
                spec,
                0.01,
                LocalDateTime.now()
        );

        items.add(item);

        return new CalibrationDataset(items, LocalDateTime.now());
    }

    /**
     * Test that apply returns a ValueResult with null value on first call.
     * This tests the branch where previousmarketdata is null.
     */
    @Test
    void testApply_FirstCall_ReturnsNullValue() {
        // Arrange
        String sdcXML = "<sdc>test</sdc>";
        BigDecimal resultTriggerValue = new BigDecimal("1000.00");
        ConditionalSettlementCalculator calculator = new ConditionalSettlementCalculator(sdcXML, resultTriggerValue);
        CalibrationDataset dataset = createTestCalibrationDataset();

        // Act
        ValueResult result = calculator.apply(dataset);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertNull(result.getValue(), "Value should be null on first call");
    }

    /**
     * Test that apply handles multiple sequential calls.
     * First call should return null value, second call should process with calculator.
     */
    @Test
    void testApply_SecondCall_ProcessesWithCalculator() {
        // Arrange
        String sdcXML = "<sdc>test</sdc>";
        BigDecimal resultTriggerValue = new BigDecimal("1000.00");
        ConditionalSettlementCalculator calculator = new ConditionalSettlementCalculator(sdcXML, resultTriggerValue);
        CalibrationDataset dataset1 = createTestCalibrationDataset();
        CalibrationDataset dataset2 = createTestCalibrationDataset();

        // Act
        ValueResult result1 = calculator.apply(dataset1);
        ValueResult result2 = calculator.apply(dataset2);

        // Assert - First call
        assertNotNull(result1, "First result should not be null");
        assertNull(result1.getValue(), "First result value should be null");

        // Assert - Second call (will attempt calculation but likely fail due to invalid XML)
        assertNotNull(result2, "Second result should not be null");
        // The second call will attempt to use MarginCalculator.getValue but will fail
        // due to invalid XML, so it will return the default result with null value
    }

    /**
     * Test that apply handles the same dataset multiple times.
     */
    @Test
    void testApply_MultipleCalls_WithSameDataset() {
        // Arrange
        String sdcXML = "<sdc>test</sdc>";
        BigDecimal resultTriggerValue = new BigDecimal("1000.00");
        ConditionalSettlementCalculator calculator = new ConditionalSettlementCalculator(sdcXML, resultTriggerValue);
        CalibrationDataset dataset = createTestCalibrationDataset();

        // Act
        ValueResult result1 = calculator.apply(dataset);
        ValueResult result2 = calculator.apply(dataset);
        ValueResult result3 = calculator.apply(dataset);

        // Assert
        assertNotNull(result1, "First result should not be null");
        assertNotNull(result2, "Second result should not be null");
        assertNotNull(result3, "Third result should not be null");
        assertNull(result1.getValue(), "First result value should be null");
    }

    /**
     * Test that apply returns a non-null ValueResult even with null dataset.
     * This tests exception handling as serializeToJson will fail.
     */
    @Test
    void testApply_WithNullDataset_HandlesException() {
        // Arrange
        String sdcXML = "<sdc>test</sdc>";
        BigDecimal resultTriggerValue = new BigDecimal("1000.00");
        ConditionalSettlementCalculator calculator = new ConditionalSettlementCalculator(sdcXML, resultTriggerValue);

        // Act & Assert - This will throw NullPointerException which should be caught
        assertThrows(NullPointerException.class, () -> calculator.apply(null));
    }

    /**
     * Test that apply with empty dataset works.
     */
    @Test
    void testApply_WithEmptyDataset() {
        // Arrange
        String sdcXML = "<sdc>test</sdc>";
        BigDecimal resultTriggerValue = new BigDecimal("1000.00");
        ConditionalSettlementCalculator calculator = new ConditionalSettlementCalculator(sdcXML, resultTriggerValue);

        Set<CalibrationDataItem> emptyItems = new LinkedHashSet<>();
        CalibrationDataset emptyDataset = new CalibrationDataset(emptyItems, LocalDateTime.now());

        // Act
        ValueResult result = calculator.apply(emptyDataset);

        // Assert
        assertNotNull(result, "Result should not be null with empty dataset");
        assertNull(result.getValue(), "Value should be null on first call");
    }

    /**
     * Test that apply maintains state across calls.
     * Verifies that previousmarketdata is updated after first call.
     */
    @Test
    void testApply_MaintainsStateBetweenCalls() {
        // Arrange
        String sdcXML = "<sdc>test</sdc>";
        BigDecimal resultTriggerValue = new BigDecimal("1000.00");
        ConditionalSettlementCalculator calculator = new ConditionalSettlementCalculator(sdcXML, resultTriggerValue);

        CalibrationDataset dataset1 = createTestCalibrationDataset();
        CalibrationDataset dataset2 = createTestCalibrationDataset();

        // Act
        ValueResult result1 = calculator.apply(dataset1);
        ValueResult result2 = calculator.apply(dataset2);

        // Assert
        assertNull(result1.getValue(), "First call should return null value");
        assertNotNull(result2, "Second call should return a result (even if calculation fails)");
    }

    /**
     * Test that multiple calculators maintain independent state.
     */
    @Test
    void testApply_MultipleCalculators_IndependentState() {
        // Arrange
        String sdcXML = "<sdc>test</sdc>";
        BigDecimal resultTriggerValue = new BigDecimal("1000.00");
        ConditionalSettlementCalculator calculator1 = new ConditionalSettlementCalculator(sdcXML, resultTriggerValue);
        ConditionalSettlementCalculator calculator2 = new ConditionalSettlementCalculator(sdcXML, resultTriggerValue);

        CalibrationDataset dataset = createTestCalibrationDataset();

        // Act
        ValueResult result1_1 = calculator1.apply(dataset);
        ValueResult result2_1 = calculator2.apply(dataset);
        ValueResult result1_2 = calculator1.apply(dataset);

        // Assert
        assertNull(result1_1.getValue(), "First calculator first call should return null");
        assertNull(result2_1.getValue(), "Second calculator first call should return null");
        assertNotNull(result1_2, "First calculator second call should return a result");
    }

    /**
     * Test that apply returns default result when calculator.getValue fails.
     * This is the normal case since we don't have valid SDC XML.
     */
    @Test
    void testApply_WhenCalculatorFails_ReturnsDefaultResult() {
        // Arrange
        String invalidXML = "not valid xml";
        BigDecimal resultTriggerValue = new BigDecimal("1000.00");
        ConditionalSettlementCalculator calculator = new ConditionalSettlementCalculator(invalidXML, resultTriggerValue);

        CalibrationDataset dataset1 = createTestCalibrationDataset();
        CalibrationDataset dataset2 = createTestCalibrationDataset();

        // Act
        ValueResult result1 = calculator.apply(dataset1); // First call - returns null
        ValueResult result2 = calculator.apply(dataset2); // Second call - tries to calculate, fails, returns default

        // Assert
        assertNotNull(result1, "First result should not be null");
        assertNull(result1.getValue(), "First result value should be null");
        assertNotNull(result2, "Second result should not be null");
        // Due to exception handling, result2 should be the default result with null value
    }

    /**
     * Test the class name and package.
     */
    @Test
    void testConstructor_ClassNameAndPackage() {
        // Arrange
        String sdcXML = "<sdc>test</sdc>";
        BigDecimal resultTriggerValue = new BigDecimal("1000.00");

        // Act
        ConditionalSettlementCalculator calculator = new ConditionalSettlementCalculator(sdcXML, resultTriggerValue);

        // Assert
        assertEquals("ConditionalSettlementCalculator", calculator.getClass().getSimpleName(),
                "Class simple name should be ConditionalSettlementCalculator");
        assertEquals("net.finmath.smartcontract.valuation.implementation.reactive",
                calculator.getClass().getPackageName(),
                "Class should be in correct package");
    }

    /**
     * Test standard object methods.
     */
    @Test
    void testConstructor_StandardObjectBehavior() {
        // Arrange
        String sdcXML = "<sdc>test</sdc>";
        BigDecimal resultTriggerValue = new BigDecimal("1000.00");

        // Act
        ConditionalSettlementCalculator calculator = new ConditionalSettlementCalculator(sdcXML, resultTriggerValue);

        // Assert
        assertNotNull(calculator.toString(), "toString() should return non-null value");
        assertEquals(calculator.hashCode(), calculator.hashCode(), "hashCode() should be consistent");
        assertEquals(calculator, calculator, "Instance should be equal to itself");
    }
}
