package ua.leonidius.taylor.algorithms;

import ua.leonidius.taylor.factorial.FactorialCalculator;
import ua.leonidius.taylor.Main;
import ua.leonidius.taylor.factorial.SequentialFactorialCalculator;
import ua.leonidius.taylor.factorial.SteppedCachedFactorialCalculator;
import ua.leonidius.taylor.functions.MathFunction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class ParallelTaylorAlgorithm implements TaylorAlgorithm {

    @Override
    public BigDecimal compute(MathFunction function, double argument, int iterationsNum) {
        var pool = Executors.newFixedThreadPool(8);

        var bigArgument = new BigDecimal(argument);

        // var factorialCalc = precomputeFactorials(function, iterationsNum);

        // only compute first factorials from range and supply em
        // with unique calc for each thread

        var globalFactorialCalc = new SequentialFactorialCalculator();

        // divide equally
        var tasks = new ArrayList<ComputeTask>(Main.numOfThreads);
        int partSize = iterationsNum / Main.numOfThreads;
        for (int i = 0; i < Main.numOfThreads; i++) {
            int startIndex = i * partSize;
            int endIndex = (i + 1) * partSize;

            var startFactorialArg = function.getNthFactorialArgument(i);
            var startFactorial = globalFactorialCalc.compute(startFactorialArg);
            var calc = new SequentialFactorialCalculator();
            calc.setLastValues(startFactorialArg, startFactorial);

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
        }

        return null;
    }

    private FactorialCalculator precomputeFactorials(MathFunction function, int iterations) {
        /*var calc = new CachedFactorialCalculator();
        for (int i = 0; i < iterations; i++) {
            calc.preCompute(function.getNthFactorialArgument(i));
        }
        return calc;*/
        var calc = new SteppedCachedFactorialCalculator();
        calc.preCompute(function.getNthFactorialArgument(iterations - 1));
        return calc;
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
