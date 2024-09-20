package com.Animesh.Java.FunctionalProgramming.DeclarativeProgramming;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DeclarativeProgramming {
    public static void main(String[] args) {
        // write your code here
        // Sum of numbers from 0 to 100
        int sum = IntStream.rangeClosed(0, 100).sum();
        System.out.println(sum);

        // Remove Duplicates from a list
        List<Integer> duplicates = Arrays.asList(1, 2, 3, 3, 4, 5, 6, 7, 8, 8, 9, 10);
        List<Integer> uniques = duplicates.stream()
                .distinct().collect(Collectors.toList());
        System.out.println(uniques);
    }
}
