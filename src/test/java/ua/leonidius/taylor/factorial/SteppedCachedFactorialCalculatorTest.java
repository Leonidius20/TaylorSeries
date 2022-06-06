package ua.leonidius.taylor.factorial;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.leonidius.taylor.factorial.SteppedCachedFactorialCalculator;

import java.math.BigDecimal;

public class SteppedCachedFactorialCalculatorTest {

    @Test
    public void test() {
        var calc = new SteppedCachedFactorialCalculator();
        calc.preCompute(10000);
        Assertions.assertEquals(1, calc.compute(0).intValue());
        Assertions.assertEquals(1, calc.compute(1).intValue());
        Assertions.assertEquals(6, calc.compute(3).intValue());
        Assertions.assertEquals(39916800, calc.compute(11).intValue());

    }

}
