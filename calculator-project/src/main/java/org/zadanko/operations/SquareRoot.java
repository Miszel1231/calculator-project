package org.zadanko.operations;

import org.zadanko.CalculationResult;
import org.zadanko.Operation;
import org.zadanko.enums.CalculationStatus;

public class SquareRoot extends Operation {

    @Override
    public CalculationResult calculate(double a, double b) {
        if (a < 0) {
            return CalculationResult.failure(CalculationStatus.INVALID_DOMAIN,
                    "Nie można obliczyć pierwiastka z liczby ujemnej");
        }
        return CalculationResult.success(Math.sqrt(a));
    }
}