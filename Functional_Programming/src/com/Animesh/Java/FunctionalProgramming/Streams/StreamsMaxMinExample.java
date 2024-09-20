package com.Animesh.Java.FunctionalProgramming.Streams;

import java.util.*;

public class StreamsMaxMinExample {
    public static Optional<Integer> maxValue(List<Integer> integerList) {
        return integerList.stream()
                //.reduce(0,(a,b)->(a>b) ? a : b);
                .reduce(Integer::max);
    }

    public static Optional<Integer> minValue(List<Integer> integerList) {
        return integerList.stream()
                // .reduce((a, b) -> (a < b) ? a : b);
                .reduce(Integer::min);
    }

    private static void driver(List<Integer> integers) {
        Optional<Integer> maxValue = maxValue(integers);
        int max = maxValue.orElse(Integer.MAX_VALUE);
        System.out.println("Max Value is : " + max);

        Optional<Integer> minValue = minValue(integers);
        int min = minValue.orElse(Integer.MIN_VALUE);
        System.out.println("Min Value is : " + min);
    }

    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(6, 7, 8, 9, 10);
        List<Integer> emptyList = new ArrayList<>();
        driver(integers);
        driver(emptyList);
    }
}
