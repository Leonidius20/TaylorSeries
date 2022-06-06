package ua.leonidius.taylor.factorial;

import ua.leonidius.taylor.factorial.FactorialCalculator;

import java.math.BigDecimal;

public class SequentialFactorialCalculator implements FactorialCalculator {

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

    @Override
    public BigDecimal lastFactorialValue() {
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

    public void setLastValues(int arg, BigDecimal value) {
        this.lastArgument = arg;
        this.lastValue = value;
    }

}
