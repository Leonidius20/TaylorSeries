package ua.leonidius.taylor.functions;

import ua.leonidius.taylor.FactorialCalculator;

import java.math.BigDecimal;

public class Cos implements MathFunction {

    @Override
    public BigDecimal getNthTaylorTerm(int n, BigDecimal x, FactorialCalculator factorialCalc) {
        return null;
    }

    @Override
    public boolean isTrigonometric() {
        return true;
    }

}
