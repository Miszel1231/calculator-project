package org.zadanko;

import org.zadanko.enums.CalculationStatus;

public class Power extends Operation {

    @Override
    public CalculationResult calculate(double a, double b) {
        double result = Math.pow(a, b);

        if (result != result) {return CalculationResult.failure(CalculationStatus.INVALID_DOMAIN,
                "Nieprawidłowe argumenty potęgowania.");
        }
        return CalculationResult.success(result);
    }
}
