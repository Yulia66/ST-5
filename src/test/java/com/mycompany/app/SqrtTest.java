package com.mycompany.app;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class SquareRootCalculatorTest {
    
    private static final double TOLERANCE = 1e-8;
    private SquareRootCalculator calculator;
    
    @Before
    public void initialize() {
        calculator = new SquareRootCalculator(4.0);
    }
    
    @Test
    public void testArithmeticMeanCalculation() {
        assertEquals(2.5, calculator.arithmeticMean(2.0, 3.0), TOLERANCE);
        assertEquals(5.0, calculator.arithmeticMean(4.0, 6.0), TOLERANCE);
        assertEquals(0.0, calculator.arithmeticMean(-1.0, 1.0), TOLERANCE);
        assertEquals(10.0, calculator.arithmeticMean(8.0, 12.0), TOLERANCE);
    }
    
    @Test
    public void testIsAcceptableWithExactValues() {
        assertTrue(calculator.isAcceptable(2.0, 4.0));
        assertTrue(calculator.isAcceptable(3.0, 9.0));
        assertTrue(calculator.isAcceptable(5.0, 25.0));
    }
    
    @Test
    public void testIsAcceptableWithCloseValues() {
        assertTrue(calculator.isAcceptable(2.000000001, 4.0));
        assertTrue(calculator.isAcceptable(1.999999999, 4.0));
        assertTrue(calculator.isAcceptable(2.000000005, 4.0));
    }
    
    @Test
    public void testIsAcceptableWithBadEstimates() {
        assertFalse(calculator.isAcceptable(2.1, 4.0));
        assertFalse(calculator.isAcceptable(1.9, 4.0));
        assertFalse(calculator.isAcceptable(3.5, 9.0));
    }
    
    @Test
    public void testRefineEstimateCalculation() {
        double refined = calculator.refineEstimate(2.0, 4.0);
        assertEquals(2.0, refined, TOLERANCE);
        
        SquareRootCalculator calcTwo = new SquareRootCalculator(2.0);
        assertEquals(1.5, calcTwo.refineEstimate(1.0, 2.0), TOLERANCE);
        
        SquareRootCalculator calcNine = new SquareRootCalculator(9.0);
        assertEquals(3.0, calcNine.refineEstimate(3.0, 9.0), TOLERANCE);
        
        SquareRootCalculator calcSixteen = new SquareRootCalculator(16.0);
        assertEquals(4.0625, calcSixteen.refineEstimate(5.0, 16.0), TOLERANCE);
    }
    
    @Test
    public void testIterateConvergesQuickly() {
        SquareRootCalculator calcFour = new SquareRootCalculator(4.0);
        double result = calcFour.iterate(2.0, 4.0);
        assertEquals(2.0, result, TOLERANCE);
    }
    
    @Test
    public void testComputeSquareRootOfFour() {
        SquareRootCalculator calcFour = new SquareRootCalculator(4.0);
        assertEquals(2.0, calcFour.compute(), TOLERANCE);
    }
    
    @Test
    public void testComputeSquareRootOfTwo() {
        SquareRootCalculator calcTwo = new SquareRootCalculator(2.0);
        double actual = calcTwo.compute();
        double expected = Math.sqrt(2.0);
        assertEquals(expected, actual, TOLERANCE);
    }
    
    @Test
    public void testComputeSquareRootOfNine() {
        SquareRootCalculator calcNine = new SquareRootCalculator(9.0);
        assertEquals(3.0, calcNine.compute(), 1e-6);
    }
    
    @Test
    public void testComputeSquareRootOfZeroPointTwentyFive() {
        SquareRootCalculator calcQuarter = new SquareRootCalculator(0.25);
        assertEquals(0.5, calcQuarter.compute(), TOLERANCE);
    }
    
    @Test
    public void testComputeSquareRootOfOne() {
        SquareRootCalculator calcOne = new SquareRootCalculator(1.0);
        assertEquals(1.0, calcOne.compute(), TOLERANCE);
    }
    
    @Test
    public void testComputeSquareRootOfMillion() {
        SquareRootCalculator calcLarge = new SquareRootCalculator(1000000.0);
        assertEquals(1000.0, calcLarge.compute(), TOLERANCE);
    }
    
    @Test
    public void testNewtonMethodConvergenceBehavior() {
        SquareRootCalculator calcTwo = new SquareRootCalculator(2.0);
        double currentEstimate = 1.0;
        double previousEstimate;
        double exactValue = Math.sqrt(2.0);
        
        for (int step = 0; step < 6; step++) {
            previousEstimate = currentEstimate;
            currentEstimate = calcTwo.refineEstimate(currentEstimate, 2.0);
            double currentError = Math.abs(currentEstimate - exactValue);
            double previousError = Math.abs(previousEstimate - exactValue);
            assertTrue("Error decreases at iteration " + step, 
                      currentError < previousError);
        }
    }
    
    @Test
    public void testPrecisionControlsAccuracy() {
        SquareRootCalculator calcTwo = new SquareRootCalculator(2.0);
        double computedRoot = calcTwo.compute();
        double calculationError = Math.abs(computedRoot * computedRoot - 2.0);
        assertTrue("Error should be less than precision setting", 
                  calculationError < calcTwo.getPrecision());
    }
    
    @Test
    public void testPrecisionCanBeModified() {
        SquareRootCalculator calc = new SquareRootCalculator(2.0);
        calc.setPrecision(0.001);
        double result = calc.compute();
        double error = Math.abs(result * result - 2.0);
        assertTrue(error <= 0.001);
        
        calc.setPrecision(0.000001);
        result = calc.compute();
        error = Math.abs(result * result - 2.0);
        assertTrue(error <= 0.000001);
    }
    
    @Test
    public void testComputeWithVariousInputs() {
        double[] testNumbers = {0.01, 0.1, 0.5, 1.5, 2.5, 10, 100, 10000, 0.04, 144.0};
        for (double value : testNumbers) {
            SquareRootCalculator sqrtCalc = new SquareRootCalculator(value);
            double computedValue = sqrtCalc.compute();
            double expectedValue = Math.sqrt(value);
            assertEquals("Square root of " + value, expectedValue, computedValue, 1e-6);
        }
    }
    
    @Test
    public void testComputeForVerySmallNumber() {
        SquareRootCalculator calcSmall = new SquareRootCalculator(0.000001);
        assertEquals(0.001, calcSmall.compute(), 1e-8);
    }
    
    @Test
    public void testComputeForPerfectSquare() {
        int[] perfectSquares = {16, 25, 36, 49, 64, 81, 100, 121, 144, 169};
        for (int square : perfectSquares) {
            SquareRootCalculator calc = new SquareRootCalculator((double)square);
            assertEquals((double)Math.sqrt(square), calc.compute(), 1e-6);
        }
    }
    
    @Test
    public void testComputeForNonPerfectSquare() {
        double[] nonSquares = {3, 5, 6, 7, 8, 10, 11, 12, 13, 14, 15};
        for (double value : nonSquares) {
            SquareRootCalculator calc = new SquareRootCalculator(value);
            double computed = calc.compute();
            double squared = computed * computed;
            assertTrue("Result squared should be close to original",
                      Math.abs(squared - value) < 1e-6);
        }
    }
}

