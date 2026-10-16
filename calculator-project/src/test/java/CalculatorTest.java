import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.zadanko.*;
import org.zadanko.enums.CalculationStatus;
import org.zadanko.operations.*;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }
    // dodawanie

    @Test
    void shouldAddNumbers() {
        CalculationResult result = calculator.calculate(new Addition(), 5, 3);

        assertEquals(8.0, result.getValue());
        assertEquals(CalculationStatus.OK, result.getStatus());
    }

    @Test
    void shouldAddZero() {
        CalculationResult result = calculator.calculate(new Addition(), 5, 0);

        assertEquals(5.0, result.getValue());
    }

    @Test
    void shouldAddNegativeNumbers() {
        CalculationResult result = calculator.calculate(new Addition(), -5, -3);

        assertEquals(-8.0, result.getValue());
    }

    @Test
    void shouldAddNumbersWithDifferentSigns() {
        CalculationResult result = calculator.calculate(new Addition(), -5, 8);

        assertEquals(3.0, result.getValue());
    }

    @Test
    void shouldDetectAdditionOverflow() {
        CalculationResult result = calculator.calculate(new Addition(), Double.MAX_VALUE, Double.MAX_VALUE);

        assertEquals(CalculationStatus.OVERFLOW, result.getStatus());
    }

    @Test
    void shouldAcceptSmallestPositiveDouble() {
        CalculationResult result = calculator.calculate(new Addition(), Double.MIN_VALUE, 0);

        assertEquals(CalculationStatus.OK, result.getStatus());
        assertEquals(Double.MIN_VALUE, result.getValue());
    }

    // odejmowanie

    @Test
    void shouldSubtractNumbers() {
        CalculationResult result = calculator.calculate(new Subtraction(), 10, 4);

        assertEquals(6.0, result.getValue());
    }

    // mnozeninie

    @Test
    void shouldMultiplyNumbers() {
        CalculationResult result = calculator.calculate(new Multiplication(), 4, 3);

        assertEquals(12.0, result.getValue());
    }

    @Test
    void shouldReturnZeroWhenMultiplyingByZero() {
        CalculationResult result = calculator.calculate(new Multiplication(), 100, 0);

        assertEquals(0.0, result.getValue());
    }

    @Test
    void shouldReturnSameNumberWhenMultiplyingByOne() {
        CalculationResult result = calculator.calculate(new Multiplication(), 7, 1);

        assertEquals(7.0, result.getValue());
    }

    @Test
    void shouldMultiplyNumbersWithDifferentSigns() {
        CalculationResult result = calculator.calculate(new Multiplication(), -4, 3);
        assertEquals(-12.0, result.getValue());
    }

    @Test
    void shouldDetectMultiplicationOverflow() {
        CalculationResult result = calculator.calculate(new Multiplication(), Double.MAX_VALUE, 2);
        assertEquals(CalculationStatus.OVERFLOW, result.getStatus());
    }

    // dzielenie

    @Test
    void shouldDivideNumbers() {
        CalculationResult result = calculator.calculate(new Division(), 10, 2);

        assertEquals(5.0, result.getValue());
    }

    @Test
    void shouldReturnErrorWhenDividingByZero() {
        CalculationResult result = calculator.calculate(new Division(), 10, 0);

        assertEquals(CalculationStatus.DIVIDE_BY_ZERO, result.getStatus());
    }

    @Test
    void shouldDivideNegativeNumbers() {
        CalculationResult result = calculator.calculate(new Division(), -12, 3);

        assertEquals(-4.0, result.getValue());
    }

    // potegowanie

    @Test
    void shouldCalculatePower() {
        CalculationResult result = calculator.calculate(new Power(), 2, 3);
        assertEquals(8.0, result.getValue());
    }

    @Test
    void shouldCalculateNumberToPowerZero() {
        CalculationResult result = calculator.calculate(new Power(), 5, 0);

        assertEquals(1.0, result.getValue());
    }

    @Test
    void shouldDetectInvalidPowerDomain() {
        CalculationResult result = calculator.calculate(new Power(), -2, 0.5);

        assertEquals(CalculationStatus.INVALID_DOMAIN, result.getStatus());
    }

    @Test
    void shouldDetectPowerOverflow() {
        CalculationResult result = calculator.calculate(new Power(), 10, 1000);

        assertEquals(CalculationStatus.OVERFLOW, result.getStatus());
    }

    // pierwiastki

    @Test
    void shouldCalculateSquareRoot() {
        CalculationResult result = calculator.calculate(new SquareRoot(), 25, 0);
        assertEquals(5.0, result.getValue());
    }

    @Test
    void shouldReturnZeroForSquareRootOfZero() {
        CalculationResult result = calculator.calculate(new SquareRoot(), 0, 0);

        assertEquals(0.0, result.getValue());
    }

    @Test
    void shouldRejectSquareRootOfNegativeNumber() {
        CalculationResult result = calculator.calculate(new SquareRoot(), -9, 0);

        assertEquals(CalculationStatus.INVALID_DOMAIN, result.getStatus());
    }

    // wprowadzone bledne informacje

    @Test
    void shouldRejectNaNInput() {
        CalculationResult result = calculator.calculate(new Addition(), Double.NaN, 2);
        assertEquals(CalculationStatus.INVALID_INPUT, result.getStatus());
    }

    @Test
    void shouldRejectInfiniteInput() {
        CalculationResult result = calculator.calculate(new Addition(), Double.POSITIVE_INFINITY, 2);

        assertEquals(CalculationStatus.INVALID_INPUT, result.getStatus());
    }

    @Test
    void shouldRejectNullOperation() {
        CalculationResult result = calculator.calculate(null, 2, 3);

        assertEquals(CalculationStatus.INVALID_INPUT,
                result.getStatus());
    }

    // stan kalkulatora i wynik

    @Test
    void shouldPreserveLastResultAfterError() {
        calculator.calculate(new Addition(), 2, 3);

        calculator.calculate(new Division(), 10, 0);

        assertEquals(5.0, calculator.getLastResult());
        assertEquals(CalculationStatus.DIVIDE_BY_ZERO,
                calculator.getStatus());
    }

    @Test
    void shouldReturnToOkAfterSuccessfulCalculation() {
        calculator.calculate(new Division(), 10, 0);

        calculator.calculate(new Addition(), 2, 3);

        assertEquals(CalculationStatus.OK, calculator.getStatus());
        assertEquals("", calculator.getErrorMessage());
        assertEquals(5.0, calculator.getLastResult());
    }

    @Test
    void shouldResetCalculator() {
        calculator.calculate(new Addition(), 2, 3);
        calculator.calculate(new Division(), 10, 0);

        calculator.reset();

        assertEquals(0.0, calculator.getLastResult());
        assertEquals(CalculationStatus.OK, calculator.getStatus());
        assertEquals("", calculator.getErrorMessage());
    }

    // pamiec kalkulatorta

    @Test
    void shouldSaveAndRecallResult() {
        calculator.calculate(new Addition(), 4, 6);

        calculator.memorySave();

        assertEquals(10.0, calculator.memoryRecall());
    }

    @Test
    void shouldClearMemory() {
        calculator.calculate(new Addition(), 4, 6);
        calculator.memorySave();

        calculator.memoryClear();

        assertEquals(0.0, calculator.memoryRecall());
    }

    @Test
    void shouldOverwriteMemory() {
        calculator.calculate(new Addition(), 2, 3);
        calculator.memorySave();

        calculator.calculate(new Addition(), 10, 5);
        calculator.memorySave();

        assertEquals(15.0, calculator.memoryRecall());
    }

    @Test
    void shouldAddResultToMemory() {
        calculator.calculate(new Addition(), 2, 3);
        calculator.memorySave();

        calculator.calculate(new Addition(), 4, 6);
        boolean success = calculator.memoryAdd();

        assertTrue(success);

        double result = calculator.memoryRecall();
        System.out.println("Wynik w pamięci: " + result);

        assertEquals(15.0, result);
    }

    @Test
    void shouldUseRecalledMemoryValueInCalculation() {
        calculator.calculate(new Addition(), 4, 6);
        calculator.memorySave();

        double memoryValue = calculator.memoryRecall();

        CalculationResult result = calculator.calculate(new Multiplication(), memoryValue, 2);
        System.out.println(result.getValue());
        assertEquals(20.0, result.getValue());
    }

    @Test
    void shouldNotChangeMemoryWhenAdditionOverflows() {
        calculator.calculate(new Addition(), Double.MAX_VALUE, 0);
        calculator.memorySave();

        calculator.calculate(new Multiplication(), Double.MAX_VALUE, 2);
        boolean success = calculator.memoryAdd();

        assertFalse(success);
        assertEquals(Double.MAX_VALUE, calculator.memoryRecall());
        assertEquals(CalculationStatus.OVERFLOW, calculator.getStatus());
        System.out.println(calculator.memoryRecall());
    }
}