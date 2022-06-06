package ua.leonidius.taylor.factorial;

import java.math.BigDecimal;

public class SimpleFactorialCalculator implements FactorialCalculator{

    @Override
    public BigDecimal compute(int argument) {
       var result = BigDecimal.ONE;

       var bigArg = new BigDecimal(argument);
        for (BigDecimal i = BigDecimal.ONE; i.compareTo(bigArg) <= 0; i = i.add(BigDecimal.ONE)) {
            result = result.multiply(i);
        }

        return result;
    }

    @Override
    public BigDecimal lastFactorialValue() {
        throw new RuntimeException();
    }

}
