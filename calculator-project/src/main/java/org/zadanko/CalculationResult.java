package org.zadanko;

import org.zadanko.enums.CalculationStatus;

public class CalculationResult {

    private final double value;
    private final CalculationStatus status;
    private final String message;

    public CalculationResult(double value, CalculationStatus status, String message) {
        this.value = value;
        this.status = status;
        this.message = message;
    }

    public static CalculationResult success(double value) {
        return new CalculationResult(value, CalculationStatus.OK, "");
    }

    public static CalculationResult failure(CalculationStatus status, String message) {
        return new CalculationResult(0, status, message);
    }

    public double getValue() {
        return value;
    }

    public CalculationStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return status == CalculationStatus.OK;
    }
}