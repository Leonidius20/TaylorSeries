package ua.leonidius.taylor.functions;

import java.math.BigDecimal;

public class Cos implements MathFunction {

    private final BigDecimal anchorPoint;

    private final BigDecimal[] robin;

    public Cos(BigDecimal anchorPoint) {
        this.anchorPoint = anchorPoint;

        BigDecimal sinA = new BigDecimal(Math.sin(anchorPoint.doubleValue()));
        BigDecimal negativeSinA = sinA.negate();
        BigDecimal cosA = new BigDecimal(Math.cos(anchorPoint.doubleValue()));
        BigDecimal negativeCosA = cosA.negate();

        this.robin = new BigDecimal[]{cosA, negativeSinA, negativeCosA, sinA};
    }

    @Override
    public BigDecimal getNthDerivativeOfAnchor(int n) {
        var roundRobin = n % 4;
        return robin[roundRobin];
    }

    @Override
    public boolean isTrigonometric() {
        return true;
    }

    private interface Derivative {
        BigDecimal compute(BigDecimal arg);
    }

}
