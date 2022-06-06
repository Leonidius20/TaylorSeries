package ua.leonidius.taylor;

import ua.leonidius.taylor.functions.MathFunction;

import java.math.BigDecimal;

public record InputParameters(
        boolean isBenchmarkMode,
        MathFunction function,
        double argument,
        int iterationsNum,
        boolean useParallelism
        ) {}
