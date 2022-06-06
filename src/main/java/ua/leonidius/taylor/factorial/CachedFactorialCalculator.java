package ua.leonidius.taylor.factorial;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CachedFactorialCalculator implements FactorialCalculator {

    private final Map<Integer, BigDecimal> cache = new HashMap<>();
    private final SequentialFactorialCalculator calc = new SequentialFactorialCalculator();

    public void preCompute(int n) {
        cache.put(n, calc.compute(n));
    }

    @Override
    public BigDecimal compute(int argument) {
        return cache.get(argument);
    }

}
