package ua.leonidius.taylor;

import ua.leonidius.taylor.functions.*;

import java.math.BigDecimal;
import java.util.Scanner;

public class InputUtil {

    public static InputParameters getInput() {
        var scanner = new Scanner(System.in);

        var function = getFunction(scanner);

        scanner.nextLine(); // consuming end-of-line char

        var argument = getArgument(scanner);

        var iterationsNum = getIterationsNum(scanner);

        return new InputParameters(function, argument, iterationsNum);
    }

    private static MathFunction getFunction(Scanner scanner) {
        MathFunction function = null;
        while (function == null) {
            System.out.print("Choose function: exp, sin, cos, tan or sec: ");
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
            case  "tan":
                return new Tan();
            case "sec":
                return new Sec();
        }
        System.out.println("Invalid function name. Try again.");
        return null;
    }

    private static BigDecimal getArgument(Scanner scanner) {
        while (true) {
            System.out.print("Input argument: ");
            var string = scanner.nextLine();
            try {
                return new BigDecimal(string);
            } catch (Exception e) {
                System.out.println("Wrong number format. Try again.");
            }
        }
    }

    private static int getIterationsNum(Scanner scanner) {
        while (true) {
            System.out.print("Input number of iterations: ");
            int iterationsNum = scanner.nextInt();
            if (iterationsNum > 0) return iterationsNum;
            System.out.println("Number of iterations has to be bigger than 0.");
        }
    }

}
