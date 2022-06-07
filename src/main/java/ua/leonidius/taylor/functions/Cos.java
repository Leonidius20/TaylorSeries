package ua.leonidius.taylor.functions;

import ua.leonidius.taylor.factorial.FactorialCalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Cos implements MathFunction {

    @Override
    public BigDecimal getNthTaylorTerm(int n, BigDecimal x, FactorialCalculator factorialCalc) {
        var sign = new BigDecimal(-1).pow(n);
        var numerator = x.pow(2 * n);
        var denominator = factorialCalc.compute(getNthFactorialArgument(n));

        return numerator.divide(denominator, 100, RoundingMode.FLOOR).multiply(sign);
    }

    @Override
    public int getNthFactorialArgument(int n) {
        return n * 2;
    }

    @Override
    public boolean isTrigonometric() {
        return true;
    }

}
