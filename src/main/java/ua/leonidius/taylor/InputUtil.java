package ua.leonidius.taylor;

import ua.leonidius.taylor.functions.*;

import java.math.BigDecimal;
import java.util.Scanner;

public class InputUtil {

    public static InputParameters getInput() {
        var scanner = new Scanner(System.in);

        boolean isBenchmark = isBenchmarkNode(scanner);
        if (isBenchmark) return new InputParameters(true, null, -1, -1, false, null);

        var anchorPoint = getBigDecimal(scanner, "Input the point, at which the derivatives will be calculated: ");

        var function = getFunction(scanner, anchorPoint);

        scanner.nextLine(); // consuming end-of-line char

        var argument = getArgument(scanner);

        var iterationsNum = getIterationsNum(scanner);

        scanner.nextLine(); // consuming end-of-line char

        var parallelism = whetherToUseParallelism(scanner);

        return new InputParameters(false, function, argument, iterationsNum, parallelism, anchorPoint);
    }

    private static boolean isBenchmarkNode(Scanner scanner) {
        System.out.print("Do you want to run the benchmark (Y for yes, N for manual mode): ");
        var line = scanner.nextLine();
        return line.toLowerCase().toCharArray()[0] == 'y';
    }

    private static MathFunction getFunction(Scanner scanner, BigDecimal anchorPoint) {
        MathFunction function = null;
        while (function == null) {
            System.out.print("Choose function: exp, sin, or cos: ");
            var functionString = scanner.next();
            function = getFunctionFromString(functionString, anchorPoint);
        }
        return function;
    }

    private static MathFunction getFunctionFromString(String functionName, BigDecimal anchorPoint) {
        switch (functionName.toLowerCase()) {
            case "exp":
                return new Exp(anchorPoint);
            case "sin":
                return new Sin(new BigDecimal(TrigonometricUtils.simplifyAngle(anchorPoint.doubleValue())));
            case "cos":
                return new Cos(new BigDecimal(TrigonometricUtils.simplifyAngle(anchorPoint.doubleValue())));
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

    private static BigDecimal getBigDecimal(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            var string = scanner.nextLine();
            try {
                return new BigDecimal(Double.parseDouble(string));
            } catch (Exception e) {
                System.out.println("Wrong number format. Try again.");
                // scanner.nextLine(); // remove end-of-line
            }
        }
    }


}
