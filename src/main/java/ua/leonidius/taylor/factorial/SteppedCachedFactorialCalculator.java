package ua.leonidius.taylor.factorial;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class SteppedCachedFactorialCalculator implements FactorialCalculator {

    private final Map<Integer, BigDecimal> cache = new HashMap<>();
    private final SequentialFactorialCalculator calc = new SequentialFactorialCalculator();
    private final int STEP = 10;

    public void preCompute(int maxFactorial) {
        for (int i = 0; i < maxFactorial; i += STEP) { // step 10
            cache.put(i, calc.compute(i));
        }
    }

    @Override
    public BigDecimal compute(int argument) {
        int index = (argument / STEP) * STEP;
        var value = cache.get(index);

        if (argument == index) return value;

        // possible todo: move cache to different class,make this class unique for
        // each thread and use sequential calc here

        while (argument != index) {
            index++;
            value = value.multiply(new BigDecimal(index));
        }

        return value;
    }

    @Override
    public BigDecimal lastFactorialValue() {
        throw new RuntimeException();
    }

}
