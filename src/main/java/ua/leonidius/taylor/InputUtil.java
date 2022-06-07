package ua.leonidius.taylor;

import ua.leonidius.taylor.functions.*;

import java.math.BigDecimal;
import java.util.Scanner;

public class InputUtil {

    public static InputParameters getInput() {
        var scanner = new Scanner(System.in);

        boolean isBenchmark = isBenchmarkNode(scanner);
        if (isBenchmark) return new InputParameters(true, null, -1, -1, false);

        var function = getFunction(scanner);

        scanner.nextLine(); // consuming end-of-line char

        var argument = getArgument(scanner);

        var iterationsNum = getIterationsNum(scanner);

        scanner.nextLine(); // consuming end-of-line char

        var parallelism = whetherToUseParallelism(scanner);

        return new InputParameters(false, function, argument, iterationsNum, parallelism);
    }

    private static boolean isBenchmarkNode(Scanner scanner) {
        System.out.print("Do you want to run the benchmark (Y for yes, N for manual mode): ");
        var line = scanner.nextLine();
        return line.toLowerCase().toCharArray()[0] == 'y';
    }

    private static MathFunction getFunction(Scanner scanner) {
        MathFunction function = null;
        while (function == null) {
            System.out.print("Choose function: exp, sin, or cos: ");
            var functionString = scanner.next();
            function = getFunctionFromString(functionString);
        }
        return function;
    }

    private static MathFunction getFunctionFromString(String functionName) {
        switch (functionName.toLowerCase()) {
            case "exp":
                return new Exp();
            case "sin":
                return new Sin();
            case "cos":
                return new Cos();
        }
        System.out.println("Invalid function name. Try again.");
        return null;
    }

    private static double getArgument(Scanner scanner) {
        while (true) {
            System.out.print("Input argument: ");
            var string = scanner.nextLine();
            try {
                return Double.parseDouble(string);
            } catch (Exception e) {
                System.out.println("Wrong number format. Try again.");
                // scanner.nextLine(); // remove end-of-line
            }
        }
    }

    private static int getIterationsNum(Scanner scanner) {
        while (true) {
            System.out.print("Input number of iterations (max 2147483647): ");
            int iterationsNum = scanner.nextInt();
            if (iterationsNum > 0) return iterationsNum;
            System.out.println("Number of iterations has to be bigger than 0.");
        }
    }

    private static boolean whetherToUseParallelism(Scanner scanner) {
        System.out.print("Do you want to use parallelism (Y for yes, anything else for no): ");
        var line = scanner.nextLine();
        return line.toLowerCase().toCharArray()[0] == 'y';
    }


}
