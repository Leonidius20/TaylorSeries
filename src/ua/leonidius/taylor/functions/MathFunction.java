package ua.leonidius.taylor.functions;

import java.math.BigDecimal;

public interface MathFunction {

    BigDecimal getNthTaylorTerm();

    boolean isTrigonometric();

}
