package com.Animesh.Java.FunctionalProgramming.FunctionalInterfaces;

import java.util.function.Function;

public class FunctionExample {

    private final static Function<String, String> upperCase = (name) -> name.toUpperCase();
    public final static Function<String, String> addSomeString = (name) -> name.concat(" default");
    private static final Function<String, Integer> strLength = (name) -> name.length();

    public static void main(String[] args) {
        System.out.println("Result is : " + upperCase.apply("java8"));
        System.out.println("Result of and then : " + upperCase.andThen(addSomeString).apply("java8"));
        System.out.println("Result of compose : " + upperCase.compose(addSomeString).apply("java8"));
        Function<String, String> abc = Function.identity();
        System.out.println(abc.apply("ABC"));
    }
}
