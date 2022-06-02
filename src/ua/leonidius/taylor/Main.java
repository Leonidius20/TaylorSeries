package ua.leonidius.taylor;

import ua.leonidius.taylor.algorithms.ParallelTaylorAlgorithm;
import ua.leonidius.taylor.algorithms.SequentialTaylorAlgorithm;
import ua.leonidius.taylor.algorithms.TaylorAlgorithm;

public class Main {

    public static void main(String[] args) {
        var input = InputUtil.getInput();

        TaylorAlgorithm algorithm = input.useParallelism()
                ? new ParallelTaylorAlgorithm()
                : new SequentialTaylorAlgorithm();

        var argument = input.function().isTrigonometric()
                ? TrigonometricUtils.simplifyAngle(input.argument())
                : input.argument();

        var result = algorithm.compute(input.function(), argument, input.iterationsNum());

        System.out.println("Result: " + result);
    }

}
