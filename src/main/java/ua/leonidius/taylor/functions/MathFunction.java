package ua.leonidius.taylor.functions;

import ua.leonidius.taylor.factorial.FactorialCalculator;

import java.math.BigDecimal;

public interface MathFunction {

    BigDecimal getNthTaylorTerm(int n, BigDecimal x, FactorialCalculator factorialCalculator);

    int getNthFactorialArgument(int n);

    boolean isTrigonometric();

}
