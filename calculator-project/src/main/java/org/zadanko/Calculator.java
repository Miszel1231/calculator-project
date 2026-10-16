package org.zadanko;

import org.zadanko.enums.CalculationStatus;

public class Calculator {

    private double lastResult;
    private CalculationStatus status;
    private String errorMessage;

    private final CalculatorMemory memory;

    public Calculator() {
        lastResult = 0;
        status = CalculationStatus.OK;
        errorMessage = "";
        memory = new CalculatorMemory();
    }

    public CalculationResult calculate(Operation operation, double a, double b) {

        if (operation == null || !Double.isFinite(a) || !Double.isFinite(b)) {

            return setError(CalculationStatus.INVALID_INPUT, "Operacja lub argumenty są nieprawidłowe.");
        }

        CalculationResult result = operation.calculate(a, b);

        if (!result.isSuccess()) {
            return setError(result.getStatus(), result.getMessage());
        }
        if (!Double.isFinite(result.getValue())) {
            return setError(CalculationStatus.OVERFLOW, "Wynik przekracza zakres typu double.");
        }

        lastResult = result.getValue();
        status = CalculationStatus.OK;
        errorMessage = "";

        return result;
    }

    private CalculationResult setError(CalculationStatus newStatus, String message) {
        status = newStatus;
        errorMessage = message;

        return CalculationResult.failure(newStatus, message);
    }

    public double getLastResult() {
        return lastResult;
    }

    public CalculationStatus getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void reset() {
        lastResult = 0;
        status = CalculationStatus.OK;
        errorMessage = "";
    }

    // zapisuje ostani wynik
    public void memorySave() {
        memory.save(lastResult);
    }

    // czyta pamiec
    public double memoryRecall() {
        return memory.recall();
    }

    // czysci pamiec
    public void memoryClear() {
        memory.clear();
    }

    // dodaje ostatni wynik do pamieci
    public boolean memoryAdd() {
        if (!memory.add(lastResult)) {setError(CalculationStatus.OVERFLOW, "Przepełnienie podczas dodawania do pamięci.");
            return false;
        }
        return true;
    }
}
