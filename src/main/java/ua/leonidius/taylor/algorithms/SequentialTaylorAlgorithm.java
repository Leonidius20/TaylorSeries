package ua.leonidius.taylor.algorithms;

import ua.leonidius.taylor.factorial.SimpleFactorialCalculator;
import ua.leonidius.taylor.functions.MathFunction;

import java.math.BigDecimal;

public class SequentialTaylorAlgorithm implements TaylorAlgorithm {

    @Override
    public BigDecimal compute(MathFunction function, double argument, int iterationsNum) {
        var bigArgument = new BigDecimal(argument);

        BigDecimal sum = BigDecimal.ZERO;
        var factorialCalc = new SimpleFactorialCalculator();

        for (int i = 0; i < iterationsNum; i++) {
            sum = sum.add(function.getNthTaylorTerm(i, bigArgument, factorialCalc));
        }

        return sum;
    }

}
