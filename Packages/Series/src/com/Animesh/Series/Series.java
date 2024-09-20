package com.Animesh.Series;

public class Series {
    public static int nsum(int n) {
        if (n < 1)
            return 0;
        return n + nsum(n - 1);
    }
    public static int factorial(int n) {
        if (n == 0)
            return 1;
        return n * factorial(n - 1);
    }

    public static int fibonacci(int n) {
        if (n < 1)
            return 0;
        if (n == 1)
            return 1;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}
