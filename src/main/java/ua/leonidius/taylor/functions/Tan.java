package ua.leonidius.taylor.functions;

import ua.leonidius.taylor.factorial.FactorialCalculator;

import java.math.BigDecimal;

public class Tan implements MathFunction {
    @Override
    public BigDecimal getNthTaylorTerm(int n, BigDecimal x, FactorialCalculator factorialCalc) {
        return null;
    }

    @Override
    public int getNthFactorialArgument(int n) {
        return 2 * n;
    }

    @Override
    public boolean isTrigonometric() {
        return true;
    }
}
