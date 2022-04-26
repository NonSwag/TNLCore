package net.nonswag.tnl.core.api.math;

import javax.annotation.Nonnull;
import java.util.Random;

public class MathUtil {

    public static <N extends Number> boolean isInLine(@Nonnull N line, @Nonnull N number) {
        return number.doubleValue() % line.doubleValue() == 0;
    }

    public static boolean chance(double chance) {
        return randomDouble(0d, 99d) < chance;
    }

    public static boolean chance(long chance) {
        return randomLong(0L, 99L) < chance;
    }

    public static boolean chance(float chance) {
        return randomFloat(0F, 99F) < chance;
    }

    public static boolean chance(int chance) {
        return randomInteger(0, 99) < chance;
    }

    public static int randomInteger(int from, int to) {
        return (new Random().nextInt(to + 1 - from) + from);
    }

    public static double randomDouble(double from, double to) {
        return ((from + (to - from)) * (new Random().nextDouble()));
    }

    public static float randomFloat(float from, float to) {
        return ((from + (to - from)) * (new Random().nextFloat()));
    }

    public static long randomLong(long from, long to) {
        return ((from + (to - from)) * (new Random().nextLong()));
    }

    public static double factorial(int number) {
        boolean negative = number < 0;
        if (negative) number = -number;
        double result = number;
        for (double d = number - 1; d > 0; d--) result *= d;
        return (negative ? -result : result);
    }

    public static boolean isInt(@Nonnull Number number) {
        return number.longValue() == number.doubleValue() || Math.ceil(number.doubleValue()) == Math.floor(number.doubleValue());
    }

    public static boolean isEven(double d) {
        return !isInt(d) && isInt((long) d / 2d);
    }

    public static boolean isPrime(double d) {
        if (d <= 1 || isEven(d)) return false;
        for (double i1 = 2; i1 < d / 2; i1++) if (isInt(d / i1)) return false;
        return true;
    }
}
