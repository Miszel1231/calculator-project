package org.zadanko.operations;

import org.zadanko.CalculationResult;
import org.zadanko.Operation;

public class Addition extends Operation {

    @Override
    public CalculationResult calculate(double a, double b) {
        return CalculationResult.success(a + b);
    }
}
