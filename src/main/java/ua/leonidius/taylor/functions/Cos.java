package ua.leonidius.taylor.functions;

import ua.leonidius.taylor.factorial.FactorialCalculator;

import java.math.BigDecimal;

public class Cos implements MathFunction {

    @Override
    public BigDecimal getNthTaylorTerm(int n, BigDecimal x, FactorialCalculator factorialCalc) {
        return null;
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
