package ua.leonidius.taylor.algorithms;

import ua.leonidius.taylor.factorial.SequentialFactorialCalculator;
import ua.leonidius.taylor.functions.MathFunction;

import java.math.BigDecimal;

public class SequentialTaylorAlgorithm implements TaylorAlgorithm {

    @Override
    public BigDecimal compute(MathFunction function, double argument, int iterationsNum) {
        var bigArgument = new BigDecimal(argument);
        //var bigIterations = new BigDecimal(iterationsNum);

        BigDecimal sum = BigDecimal.ZERO;
        var factorialCalc = new SequentialFactorialCalculator();

        //for (BigDecimal i = BigDecimal.ZERO; i.compareTo(bigIterations) < 0; i = i.add(BigDecimal.ONE)) {
        for (int i = 0; i < iterationsNum; i++) {
            sum = sum.add(function.getNthTaylorTerm(i, bigArgument, factorialCalc));
        }

        return sum;
    }

}
