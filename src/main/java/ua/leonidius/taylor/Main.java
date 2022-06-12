package ua.leonidius.taylor;

import com.google.gson.Gson;
import ua.leonidius.taylor.algorithms.ParallelTaylorAlgorithm;
import ua.leonidius.taylor.algorithms.SequentialTaylorAlgorithm;
import ua.leonidius.taylor.algorithms.TaylorAlgorithm;
import ua.leonidius.taylor.functions.Exp;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;

public class Main {

    public static int numOfThreads = 7; // for parallel algo

    public static void main(String[] args) {
        var input = InputUtil.getInput();

        if (input.isBenchmarkMode()) {
            benchmark();
            return;
        }

        TaylorAlgorithm algorithm = input.useParallelism()
                ? new ParallelTaylorAlgorithm()
                : new SequentialTaylorAlgorithm();

        var argument = input.function().isTrigonometric()
                ? TrigonometricUtils.simplifyAngle(input.argument())
                : input.argument();

        var result = timedExecution(()
                -> algorithm.compute(input.function(), argument,
                input.iterationsNum(), input.anchorPoint()));

        System.out.println("Result: " + result);
    }

    public static <T> T timedExecution(Method<T> method) {
        long startTime = System.nanoTime();
        T result = method.run();
        long endTime = System.nanoTime();

        long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
        System.out.println(duration / 1000 + " micros to run");
        return result;
    }

    public static void benchmark() {
        var function = new Exp(BigDecimal.ZERO);
        double x = 1;

        var report = new ReportData();

        int NUM_OF_POWERS_OF_TEN = 5;

        for (int i = 1; i < NUM_OF_POWERS_OF_TEN; i++) {
             int iterations = (int)Math.pow(10, i);
            //int iterations = 100000 * i;

            var reportItem = new ReportData.SingleReport();
            reportItem.iterations = iterations;

            System.out.print("Sequential algorithm with " + iterations + " iterations takes... ");

            var time = averageTimedExecution(
                    () -> new SequentialTaylorAlgorithm().compute(function, x, iterations, BigDecimal.ZERO));
            System.out.println(time + " microseconds");

            reportItem.titlesAndTimes.put("Sequential", time);

            for (int threads = 2; threads <= 10; threads++) {
                System.out.print("Parallel algorithm with " + iterations + " iterations and " + threads + " threads takes... ");
                Main.numOfThreads = threads;
                var time2 = averageTimedExecution(
                        () -> new ParallelTaylorAlgorithm().compute(function, x, iterations, BigDecimal.ZERO));
                System.out.println(time2 + " microseconds");

                reportItem.titlesAndTimes.put(threads + " threads", time2);
            }

            report.runs.add(reportItem);
        }

        try {
            var writer = new FileWriter("report.json");
            new Gson().toJson(report, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("Could not save the report file");
            e.printStackTrace();
        }
    }

    public static <T> long averageTimedExecution(Method<T> method) {
        var NUM_OF_EXECUTIONS = 3;

        long durationAcc = 0;
        for (int i = 0; i < NUM_OF_EXECUTIONS; i++) {
            long startTime = System.nanoTime();
            method.run();
            long endTime = System.nanoTime();

            long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
            durationAcc += duration;
        }
        return durationAcc / NUM_OF_EXECUTIONS;
    }

    interface Method<T> {
        T run();
    }

}
