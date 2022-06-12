package ua.leonidius.taylor.algorithms;

import ua.leonidius.taylor.factorial.FactorialCalculator;
import ua.leonidius.taylor.factorial.SimpleFactorialCalculator;
import ua.leonidius.taylor.functions.MathFunction;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SequentialTaylorAlgorithm implements TaylorAlgorithm {

    @Override
    public BigDecimal compute(MathFunction function, double argument,
                              int iterationsNum, BigDecimal anchorPoint) {
        var bigArgument = new BigDecimal(argument);

        BigDecimal sum = BigDecimal.ZERO;
        var factorialCalc = new SimpleFactorialCalculator();

        for (int i = 0; i < iterationsNum; i++) {
            sum = sum.add(getNthTaylorTerm(function, i, bigArgument, factorialCalc, anchorPoint));
        }

        return sum;
    }

    private BigDecimal getNthTaylorTerm(MathFunction function, int n, BigDecimal x,
                                        FactorialCalculator factorialCalc,
                                        BigDecimal anchorPoint) {
        var numerator = x.subtract(anchorPoint).pow(n);
        var denominator = factorialCalc.compute(n);

        return numerator.divide(denominator, 100, RoundingMode.FLOOR)
                .multiply(function.getNthDerivativeOfAnchor(n));
    }

}
