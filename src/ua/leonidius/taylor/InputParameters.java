package ua.leonidius.taylor;

import ua.leonidius.taylor.functions.MathFunction;

import java.math.BigDecimal;

public record InputParameters(
        MathFunction function,
        BigDecimal argument,
        int iterationsNum,
        boolean useParallelism
        ) {}
