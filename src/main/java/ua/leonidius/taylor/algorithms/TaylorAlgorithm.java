package ua.leonidius.taylor.algorithms;

import ua.leonidius.taylor.functions.MathFunction;

import java.math.BigDecimal;

public interface TaylorAlgorithm {

    BigDecimal compute(MathFunction function, double argument,
                       int iterationsNum, BigDecimal anchorPoint);

}
