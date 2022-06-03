package ua.leonidius.taylor.functions;

import ua.leonidius.taylor.FactorialCalculator;

import java.math.BigDecimal;

public interface MathFunction {

    BigDecimal getNthTaylorTerm(int n, BigDecimal x, FactorialCalculator factorialCalculator);

    boolean isTrigonometric();

}
