package ua.leonidius.taylor;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * This is a helper class that stores the last computed value of factorial
 * and uses it to compute the next value requested (if possible)
 */
public class FactorialCalculator {

    private BigDecimal lastValue = BigDecimal.ONE;
    private int lastArgument = 0;

    public BigDecimal compute(int argument) {
        //if (argument.compareTo(lastArgument) == 0)
        if (argument == lastArgument)
            return lastValue;

        // if (argument.compareTo(lastArgument) < 0) {
        if (argument < lastArgument) {
            lastValue = BigDecimal.ONE;     // resetting
            lastArgument = 0;
        }
        increaseSavedValueTo(argument);
        return lastValue;
    }

    /**
     * Increases the saved value from what it is right now to what is requested
     */
    private void increaseSavedValueTo(int argument) {
        // while (lastArgument.compareTo(argument) != 0) {
        while (argument != lastArgument) {
            // lastArgument = lastArgument.add(BigDecimal.ONE);
            lastArgument++;
            // lastValue = lastValue.multiply(lastArgument);
            lastValue = lastValue.multiply(new BigDecimal(lastArgument));
        }
    }

    // possible TODO: add a setter function for argument+value


}
