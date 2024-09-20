package com.Animesh.Java.FunctionalProgramming.NumericStreams;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class NumericStreamMapExample {
    public static List<Integer> mapToObj() {
        return IntStream.rangeClosed(1, 5)
                .mapToObj(i -> i) // this converts the primitive type to Integer Type Wrapper
                .collect(toList());
    }

    public static double mapToDouble() {
        return IntStream.rangeClosed(1, 5)
                .mapToDouble(i -> i)
                .sum();
    }

    public static long mapToLong() {
        return IntStream.rangeClosed(1, 5)
                .mapToLong(i -> i)
                .sum();
    }

    public static void main(String[] args) {
        System.out.println("mapToObj : " + mapToObj());
        System.out.println("mapToDouble() : " + mapToDouble());
        System.out.println("mapToLong() : " + mapToLong());
    }
}
