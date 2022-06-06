package ua.leonidius.taylor.functions;

import ua.leonidius.taylor.factorial.FactorialCalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Exp implements MathFunction {

    @Override
    public BigDecimal getNthTaylorTerm(int n, BigDecimal x, FactorialCalculator factorialCalc) {
        var numerator = x.pow(n); // convert to bigdecimal
        var denominator = factorialCalc.compute(getNthFactorialArgument(n));

        return numerator.divide(denominator, 100, RoundingMode.FLOOR);
    }

    @Override
    public int getNthFactorialArgument(int n) {
        return n;
    }

    @Override
    public boolean isTrigonometric() {
        return false;
    }

}