package ua.leonidius.taylor.factorial;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * This is a helper class that stores the last computed value of factorial
 * and uses it to compute the next value requested (if possible)
 */
public interface FactorialCalculator {

    BigDecimal compute(int argument);

    BigDecimal lastFactorialValue();


}
