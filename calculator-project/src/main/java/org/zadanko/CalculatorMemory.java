package org.zadanko;

public class CalculatorMemory {

    private double value;

    public CalculatorMemory() {
        value = 0;
    }

    public void save(double number) {
        value = number;
    }

    public double recall() {
        return value;
    }

    public void clear() {
        value = 0;
    }

    public boolean add(double number) {
        double result = value + number;

        if (result == Double.POSITIVE_INFINITY || result == Double.NEGATIVE_INFINITY || Double.isNaN(result)) {
            return false;
        }
        value = result;
        return true;
    }
}