package com.Animesh.Java.FunctionalProgramming.Lamdas;

import java.util.function.Consumer;

public class LambdaVariable1 {
    public static void main(String[] args) {
        int i = 10;
        Consumer<Integer> c1 = (a) -> {
            // int i = 0; // Repeated variable name not allowed
            System.out.println(i + a);
        };
        c1.accept(2);
    }
}
