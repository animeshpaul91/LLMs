package com.Animesh.Java.FunctionalProgramming.Lamdas;

import java.util.Comparator;

public class ComparatorLambdaImpl {
    public static void main(String[] args) {
        // The Traditional Way
        Comparator<Integer> comparator = new Comparator<>() { // Anonymous Class
            @Override
            public int compare(Integer a, Integer b) {
                return a.compareTo(b);
            }
        };
        System.out.println(comparator.compare(2, 3));

        Comparator<Integer> comparatorLambda = (a, b) -> a.compareTo(b);
        // This is an anonymous implementation of the abstract method compare in Comparator Functional Interface
        System.out.println(comparatorLambda.compare(2, 3));
    }
}
