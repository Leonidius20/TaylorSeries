package ua.leonidius.taylor.arithmetic;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class KaratsubaMultiplication {


    public static BigDecimal parallelKaratsubaMultiply(BigDecimal xDec, BigDecimal yDec) {
        var xScale = xDec.scale();
        var x = xDec.unscaledValue();

        var yScale = yDec.scale();
        var y = yDec.unscaledValue();

        int N = Math.max(x.bitLength(), y.bitLength());
        if (N <= 2000) return xDec.multiply(yDec);

        // var resultUnscaled = _karatsuba(x, y);
        var pool = ForkJoinPool.commonPool();
        var task = new KaratsubaParallelTask(x, y);

        var resultUnscaled = pool.invoke(task);

        return new BigDecimal(resultUnscaled, xScale + yScale);
    }

    static class KaratsubaParallelTask extends RecursiveTask<BigInteger> {

        private final BigInteger x;
        private final BigInteger y;

        KaratsubaParallelTask(BigInteger x, BigInteger y) {
            this.x = x;
            this.y = y;
        }

        @Override
        protected BigInteger compute() {
            int N = Math.max(x.bitLength(), y.bitLength());
            if (N <= 2000) return x.multiply(y);                // optimize this parameter

            // number of bits divided by 2, rounded up
            N = (N / 2) + (N % 2);

            // x = a + 2^N b,   y = c + 2^N d
            BigInteger b = x.shiftRight(N);
            BigInteger a = x.subtract(b.shiftLeft(N));
            BigInteger d = y.shiftRight(N);
            BigInteger c = y.subtract(d.shiftLeft(N));

            // compute sub-expressions
            var acTask = new KaratsubaParallelTask(a, c);
            var bdTask = new KaratsubaParallelTask(b, d);
            var abcdTask = new KaratsubaParallelTask(a.add(b), c.add(d));

            acTask.fork();
            bdTask.fork();
            abcdTask.fork();

            BigInteger ac    = acTask.join();
            BigInteger bd    = bdTask.join();
            BigInteger abcd  = abcdTask.join();

            var resultUnscaled = ac.add(abcd.subtract(ac).subtract(bd).shiftLeft(N)).add(bd.shiftLeft(2*N));
            return resultUnscaled;
        }

    }

    public static BigInteger _karatsuba(BigInteger x, BigInteger y) {
        // todo: paralelise
        // cutoff to brute force
        int N = Math.max(x.bitLength(), y.bitLength());
        if (N <= 2000) return x.multiply(y);                // optimize this parameter

        // number of bits divided by 2, rounded up
        N = (N / 2) + (N % 2);

        // x = a + 2^N b,   y = c + 2^N d
        BigInteger b = x.shiftRight(N);
        BigInteger a = x.subtract(b.shiftLeft(N));
        BigInteger d = y.shiftRight(N);
        BigInteger c = y.subtract(d.shiftLeft(N));

        // compute sub-expressions
        BigInteger ac    = _karatsuba(a, c);
        BigInteger bd    = _karatsuba(b, d);
        BigInteger abcd  = _karatsuba(a.add(b), c.add(d));

        var resultUnscaled = ac.add(abcd.subtract(ac).subtract(bd).shiftLeft(N)).add(bd.shiftLeft(2*N));
        return resultUnscaled;
    }



    public static void main(String[] args) {
        long start, stop, elapsed;
        Random random = new Random();
        int N = 3000;
        BigInteger a = new BigInteger(N, random);
        BigInteger b = new BigInteger(N, random);

        start = System.currentTimeMillis();
        var pool = ForkJoinPool.commonPool();
        var task = new KaratsubaParallelTask(a, b);

        var c = pool.invoke(task);
        stop = System.currentTimeMillis();
        System.out.println(stop - start);

        start = System.currentTimeMillis();
        BigInteger d = a.multiply(b);
        stop = System.currentTimeMillis();
        System.out.println(stop - start);

        System.out.println((c.equals(d)));
    }
}
