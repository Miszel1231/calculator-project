package org.zadanko.operations;

import org.zadanko.CalculationResult;
import org.zadanko.Operation;
import org.zadanko.enums.CalculationStatus;

public class Division extends Operation {

    @Override
    public CalculationResult calculate(double a, double b) {
        if (b == 0.0) {
            return CalculationResult.failure(CalculationStatus.DIVIDE_BY_ZERO, "Nie można dzielić przez zero.");
        }
        return CalculationResult.success(a / b);
    }
}
