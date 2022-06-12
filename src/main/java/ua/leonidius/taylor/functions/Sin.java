package ua.leonidius.taylor.functions;

import java.math.BigDecimal;

public class Sin implements MathFunction {

    private final BigDecimal anchorPoint;

    private final BigDecimal[] robin;

    public Sin(BigDecimal anchorPoint) {
        this.anchorPoint = anchorPoint;

        var sinA = new BigDecimal(Math.sin(anchorPoint.doubleValue()));
        var negativeSinA = sinA.negate();
        var cosA = new BigDecimal(Math.cos(anchorPoint.doubleValue()));
        var negativeCosA = cosA.negate();

        this.robin = new BigDecimal[]{sinA, cosA, negativeSinA, negativeCosA};
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
}
