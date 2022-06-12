package ua.leonidius.taylor.functions;

import ua.leonidius.taylor.factorial.FactorialCalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Exp implements MathFunction {

    private final BigDecimal anchorPoint;
    private final BigDecimal expOfAnchor;

    public Exp(BigDecimal anchorPoint) {
        this.anchorPoint = anchorPoint;
        this.expOfAnchor = BigDecimal.valueOf(Math.exp(anchorPoint.doubleValue()));
    }

    @Override
    public BigDecimal getNthDerivativeOfAnchor(int n) {
        return expOfAnchor;
    }

    @Override
    public boolean isTrigonometric() {
        return false;
    }

}
