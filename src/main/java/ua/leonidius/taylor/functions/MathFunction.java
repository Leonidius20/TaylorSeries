package ua.leonidius.taylor.functions;

import ua.leonidius.taylor.factorial.FactorialCalculator;

import java.math.BigDecimal;

public interface MathFunction {

    BigDecimal getNthDerivativeOfAnchor(int n);

    boolean isTrigonometric();

}
