package ua.leonidius.taylor.algorithms;

import ua.leonidius.taylor.Main;
import ua.leonidius.taylor.factorial.FactorialCalculator;
import ua.leonidius.taylor.factorial.SimpleFactorialCalculator;
import ua.leonidius.taylor.functions.MathFunction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class ParallelTaylorAlgorithm implements TaylorAlgorithm {

    @Override
    public BigDecimal compute(MathFunction function, double argument, int iterationsNum) {
        var numOfThreads = Math.min(Main.numOfThreads, iterationsNum);

        var pool = Executors.newFixedThreadPool(numOfThreads);

        var bigArgument = new BigDecimal(argument);

        // divide equally

        var tasks = new ArrayList<ComputeTask>(numOfThreads);
        int partSize = iterationsNum / numOfThreads;

        var calc = new SimpleFactorialCalculator();

        for (int i = 0; i < numOfThreads; i++) {
            int startIndex = i * partSize;
            int endIndex = (i + 1) * partSize;

            tasks.add(new ComputeTask(startIndex, endIndex, function, bigArgument, calc));
        }

        try {
            var accumulator = BigDecimal.ZERO;
            var futuresList = pool.invokeAll(tasks);
            pool.shutdown();

            for (var future : futuresList) {
                var result = future.get();

                accumulator = accumulator.add(result);
            }

            return accumulator;
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Computation was interrupted or an error happened");
            e.printStackTrace();
        }

        return null;
    }

    private static class ComputeTask implements Callable<BigDecimal> {

        private final int startIndex; // inclusive
        private final int endIndex;   // exclusive
        private final MathFunction function;
        private final BigDecimal argument;
        private final FactorialCalculator factorialCalc;

        private ComputeTask(int startIndex, int endIndex, MathFunction function,
                            BigDecimal argument, FactorialCalculator factorialCalc) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.function = function;
            this.argument = argument;
            this.factorialCalc = factorialCalc;
        }

        @Override
        public BigDecimal call() {
            var sum = BigDecimal.ZERO;
            for (int i = startIndex; i < endIndex; i++) {
                sum = sum.add(function.getNthTaylorTerm(i, argument, factorialCalc));
            }
            return sum;
        }

    }

}
