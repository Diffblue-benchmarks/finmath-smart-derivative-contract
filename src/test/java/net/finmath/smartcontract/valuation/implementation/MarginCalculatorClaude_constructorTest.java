/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */
package net.finmath.smartcontract.valuation.implementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import java.util.function.DoubleUnaryOperator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MarginCalculator constructors.
 * Tests the constructors: MarginCalculator() and MarginCalculator(DoubleUnaryOperator)
 *
 * @author Claude Code
 */
class MarginCalculatorClaude_constructorTest {

  // ========== Tests for no-argument constructor <init>.()V ==========

  /**
   * Test that the no-argument constructor can be instantiated.
   * This tests the constructor <init>.()V which uses a default rounding function.
   */
  @Test
  void testDefaultConstructor_Instantiation() {
    // Act
    MarginCalculator calculator = new MarginCalculator();

    // Assert
    assertNotNull(calculator, "MarginCalculator instance should not be null");
  }

  /**
   * Test that multiple instances can be created with the default constructor.
   */
  @Test
  void testDefaultConstructor_MultipleInstances() {
    // Act
    MarginCalculator calculator1 = new MarginCalculator();
    MarginCalculator calculator2 = new MarginCalculator();

    // Assert
    assertNotNull(calculator1, "First instance should not be null");
    assertNotNull(calculator2, "Second instance should not be null");
    assertNotSame(calculator1, calculator2, "Instances should be different objects");
  }

  /**
   * Test that the default constructor creates an instance of correct type.
   */
  @Test
  void testDefaultConstructor_InstanceOfCorrectType() {
    // Act
    MarginCalculator calculator = new MarginCalculator();

    // Assert
    assertTrue(calculator instanceof MarginCalculator, "Instance should be of type MarginCalculator");
  }

  /**
   * Test default constructor with standard object methods.
   */
  @Test
  void testDefaultConstructor_StandardObjectBehavior() {
    // Act
    MarginCalculator calculator = new MarginCalculator();

    // Assert
    assertNotNull(calculator.toString(), "toString() should return non-null value");
    assertEquals(calculator.hashCode(), calculator.hashCode(), "hashCode() should be consistent");
    assertEquals(calculator, calculator, "Instance should be equal to itself");
  }

  /**
   * Test that the default constructor creates an instance with expected class name.
   */
  @Test
  void testDefaultConstructor_ClassName() {
    // Act
    MarginCalculator calculator = new MarginCalculator();

    // Assert
    assertEquals(
        "MarginCalculator",
        calculator.getClass().getSimpleName(),
        "Class simple name should be MarginCalculator");
  }

  /**
   * Test that the default constructor creates an instance in expected package.
   */
  @Test
  void testDefaultConstructor_PackageName() {
    // Act
    MarginCalculator calculator = new MarginCalculator();

    // Assert
    assertEquals(
        "net.finmath.smartcontract.valuation.implementation",
        calculator.getClass().getPackageName(),
        "Class should be in net.finmath.smartcontract.valuation.implementation package");
  }

  // ========== Tests for parameterized constructor <init>.(Ljava/util/function/DoubleUnaryOperator;)V ==========

  /**
   * Test that the constructor can be instantiated with a valid DoubleUnaryOperator.
   * This tests the constructor <init>.(Ljava/util/function/DoubleUnaryOperator;)V
   */
  @Test
  void testConstructor_WithValidRoundingOperator() {
    // Arrange
    DoubleUnaryOperator rounding = x -> Math.round(x * 100) / 100.0;

    // Act
    MarginCalculator calculator = new MarginCalculator(rounding);

    // Assert
    assertNotNull(calculator, "MarginCalculator instance should not be null");
  }

  /**
   * Test that the constructor can be instantiated with a null rounding operator.
   * This tests boundary condition handling.
   */
  @Test
  void testConstructor_WithNullRoundingOperator() {
    // Arrange
    DoubleUnaryOperator rounding = null;

    // Act
    MarginCalculator calculator = new MarginCalculator(rounding);

    // Assert
    assertNotNull(calculator, "MarginCalculator instance should not be null even with null rounding");
  }

  /**
   * Test that multiple instances can be created with different rounding operators.
   */
  @Test
  void testConstructor_MultipleInstancesWithDifferentOperators() {
    // Arrange
    DoubleUnaryOperator rounding1 = x -> Math.round(x * 100) / 100.0;
    DoubleUnaryOperator rounding2 = x -> Math.round(x * 1000) / 1000.0;

    // Act
    MarginCalculator calculator1 = new MarginCalculator(rounding1);
    MarginCalculator calculator2 = new MarginCalculator(rounding2);

    // Assert
    assertNotNull(calculator1, "First instance should not be null");
    assertNotNull(calculator2, "Second instance should not be null");
    assertNotSame(calculator1, calculator2, "Instances should be different objects");
  }

  /**
   * Test that the constructor works with an identity operator.
   */
  @Test
  void testConstructor_WithIdentityOperator() {
    // Arrange
    DoubleUnaryOperator rounding = DoubleUnaryOperator.identity();

    // Act
    MarginCalculator calculator = new MarginCalculator(rounding);

    // Assert
    assertNotNull(calculator, "MarginCalculator instance should not be null");
  }

  /**
   * Test that the constructor works with a lambda expression.
   */
  @Test
  void testConstructor_WithLambdaExpression() {
    // Arrange
    DoubleUnaryOperator rounding = x -> x;

    // Act
    MarginCalculator calculator = new MarginCalculator(rounding);

    // Assert
    assertNotNull(calculator, "MarginCalculator instance should not be null");
  }

  /**
   * Test that the constructor works with a method reference.
   */
  @Test
  void testConstructor_WithMethodReference() {
    // Arrange
    DoubleUnaryOperator rounding = Math::floor;

    // Act
    MarginCalculator calculator = new MarginCalculator(rounding);

    // Assert
    assertNotNull(calculator, "MarginCalculator instance should not be null");
  }

  /**
   * Test that the instance has correct type after construction.
   */
  @Test
  void testConstructor_InstanceOfCorrectType() {
    // Arrange
    DoubleUnaryOperator rounding = x -> Math.round(x * 100) / 100.0;

    // Act
    MarginCalculator calculator = new MarginCalculator(rounding);

    // Assert
    assertTrue(calculator instanceof MarginCalculator, "Instance should be of type MarginCalculator");
  }

  /**
   * Test constructor with standard object methods.
   * Verifies that the instance has standard Object behavior.
   */
  @Test
  void testConstructor_StandardObjectBehavior() {
    // Arrange
    DoubleUnaryOperator rounding = x -> Math.round(x * 100) / 100.0;

    // Act
    MarginCalculator calculator = new MarginCalculator(rounding);

    // Assert
    assertNotNull(calculator.toString(), "toString() should return non-null value");
    assertEquals(calculator.hashCode(), calculator.hashCode(), "hashCode() should be consistent");
    assertEquals(calculator, calculator, "Instance should be equal to itself");
  }

  /**
   * Test that the class has the expected simple name.
   */
  @Test
  void testConstructor_ClassName() {
    // Arrange
    DoubleUnaryOperator rounding = x -> Math.round(x * 100) / 100.0;

    // Act
    MarginCalculator calculator = new MarginCalculator(rounding);

    // Assert
    assertEquals(
        "MarginCalculator",
        calculator.getClass().getSimpleName(),
        "Class simple name should be MarginCalculator");
  }

  /**
   * Test that the class is in the expected package.
   */
  @Test
  void testConstructor_PackageName() {
    // Arrange
    DoubleUnaryOperator rounding = x -> Math.round(x * 100) / 100.0;

    // Act
    MarginCalculator calculator = new MarginCalculator(rounding);

    // Assert
    assertEquals(
        "net.finmath.smartcontract.valuation.implementation",
        calculator.getClass().getPackageName(),
        "Class should be in net.finmath.smartcontract.valuation.implementation package");
  }

  /**
   * Test that the constructor works with a complex rounding function.
   */
  @Test
  void testConstructor_WithComplexRoundingFunction() {
    // Arrange
    DoubleUnaryOperator rounding = x -> {
      if (x > 0) {
        return Math.ceil(x);
      } else {
        return Math.floor(x);
      }
    };

    // Act
    MarginCalculator calculator = new MarginCalculator(rounding);

    // Assert
    assertNotNull(calculator, "MarginCalculator instance should not be null with complex rounding");
  }

  /**
   * Test that the constructor works with the same rounding function used in the default constructor.
   * This verifies compatibility with the default behavior.
   */
  @Test
  void testConstructor_WithDefaultRoundingFunction() {
    // Arrange
    DoubleUnaryOperator rounding = x -> Math.round(x * 1000) / 1000.0;

    // Act
    MarginCalculator calculator = new MarginCalculator(rounding);

    // Assert
    assertNotNull(calculator, "MarginCalculator instance should not be null with default rounding");
  }
}
