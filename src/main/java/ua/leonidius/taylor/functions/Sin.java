package ua.leonidius.taylor.functions;

import ua.leonidius.taylor.factorial.FactorialCalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Sin implements MathFunction {
    @Override
    public BigDecimal getNthTaylorTerm(int n, BigDecimal x, FactorialCalculator factorialCalc) {
        var sign = new BigDecimal(-1).pow(n);
        var numerator = x.pow(2 * n + 1);
        var denominator = factorialCalc.compute(getNthFactorialArgument(n));

        return numerator.divide(denominator, 100, RoundingMode.FLOOR).multiply(sign);
    }

    @Override
    public int getNthFactorialArgument(int n) {
        return (2 * n) + 1;
    }

    @Override
    public boolean isTrigonometric() {
        return true;
    }
}
