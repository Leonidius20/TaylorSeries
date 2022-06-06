package ua.leonidius.taylor.functions;

import ua.leonidius.taylor.factorial.FactorialCalculator;

import java.math.BigDecimal;

public class Sin implements MathFunction {
    @Override
    public BigDecimal getNthTaylorTerm(int n, BigDecimal x, FactorialCalculator factorialCalc) {
        return null;
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
