package ua.leonidius.taylor;

import ua.leonidius.taylor.algorithms.ParallelTaylorAlgorithm;
import ua.leonidius.taylor.algorithms.SequentialTaylorAlgorithm;
import ua.leonidius.taylor.algorithms.TaylorAlgorithm;

import java.util.function.Function;

public class Main {

    public static void main(String[] args) {
        var input = InputUtil.getInput();

        TaylorAlgorithm algorithm = input.useParallelism()
                ? new ParallelTaylorAlgorithm()
                : new SequentialTaylorAlgorithm();

        var argument = input.function().isTrigonometric()
                ? TrigonometricUtils.simplifyAngle(input.argument())
                : input.argument();

        var result = timedExecution(()
                -> algorithm.compute(input.function(), argument, input.iterationsNum()));

        System.out.println("Result: " + result);
    }

    public static <T> T timedExecution(Method<T> method) {
        long startTime = System.nanoTime();
        T result = method.run();
        long endTime = System.nanoTime();

        long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
        System.out.println("It took " + duration / 1000000 + " ms to run");
        return result;
    }

    interface Method<T> {
        T run();
    }

}
