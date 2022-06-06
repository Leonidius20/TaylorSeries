package ua.leonidius.taylor.algorithms;

import ua.leonidius.taylor.factorial.FactorialCalculator;
import ua.leonidius.taylor.Main;
import ua.leonidius.taylor.factorial.SequentialFactorialCalculator;
import ua.leonidius.taylor.factorial.SteppedCachedFactorialCalculator;
import ua.leonidius.taylor.functions.MathFunction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class ParallelTaylorAlgorithm implements TaylorAlgorithm {

    @Override
    public BigDecimal compute(MathFunction function, double argument, int iterationsNum) {
        System.out.println("Parallel");

        var numOfThreads = Math.min(Main.numOfThreads, iterationsNum);

        var pool = Executors.newFixedThreadPool(numOfThreads);

        var bigArgument = new BigDecimal(argument);

        // var factorialCalc = precomputeFactorials(function, iterationsNum);

        // only compute first factorials from range and supply em
        // with unique calc for each thread

        // var globalFactorialCalc = new SequentialFactorialCalculator();

        // divide equally

        var tasks = new ArrayList<ComputeTask>(numOfThreads);
        int partSize = iterationsNum / numOfThreads;

        for (int i = 0; i < numOfThreads; i++) {
            int startIndex = i * partSize;
            int endIndex = (i + 1) * partSize;

            var startFactorialArg = function.getNthFactorialArgument(i);

            // var startFactorial = globalFactorialCalc.compute(startFactorialArg);

            var calc = new SequentialFactorialCalculator();
            if (startIndex > 0) {
                calc.setLastValues(startFactorialArg, new BigDecimal(startFactorialArg));
                // this is not actually a factorial value. We will divide the whole
                // thing by the biggest factorial value from the previous thread
            }

            tasks.add(new ComputeTask(startIndex, endIndex, function, bigArgument, calc));
        }

        try {
            var accumulator = BigDecimal.ZERO;
            var futuresList = pool.invokeAll(tasks);
            pool.shutdown();

            BigDecimal lastFactorialValue = BigDecimal.ONE;

            for (var future : futuresList) {
                var result = future.get();

                var partialSum = result.sum;

                if (future != futuresList.get(0)) { // if not first
                   partialSum = partialSum.divide(lastFactorialValue, 1000, RoundingMode.FLOOR);
                    // and all factorials before that!!!
                }

                accumulator = accumulator.add(partialSum);

                lastFactorialValue = lastFactorialValue.multiply(result.lastFactorial);

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

    private static class ComputeTask implements Callable<ComputeTaskResult> {

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
        public ComputeTaskResult call() {
            var sum = BigDecimal.ZERO;
            for (int i = startIndex; i < endIndex; i++) {
                sum = sum.add(function.getNthTaylorTerm(i, argument, factorialCalc));
            }
            return new ComputeTaskResult(sum, factorialCalc.lastFactorialValue());
        }

    }

    private static record ComputeTaskResult(
            BigDecimal sum,
            BigDecimal lastFactorial
    ) {}

}
