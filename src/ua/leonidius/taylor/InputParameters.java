package ua.leonidius.taylor;

import java.math.BigDecimal;

public record InputParameters(
        MathFunction function,
        BigDecimal argument,
        int iterationsNum
        ) {}
