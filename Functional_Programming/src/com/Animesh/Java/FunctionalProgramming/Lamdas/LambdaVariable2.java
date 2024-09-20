package com.Animesh.Java.FunctionalProgramming.Lamdas;

import java.util.function.Consumer;

public class LambdaVariable2 {
    private static int value = 4;

    public static void main(String[] args) {
        int localValue = 4; //effectively final
        Consumer<Integer> c1 = (a) -> {
            value = 6;
            System.out.println(value); // reading and writing instance and static values are allowed within lambda
            // localValue++; // modifying local value is not allowed within lambda
            // local variables inside lambda functions are treated as effectively final.
            System.out.println(localValue); // reading local value is allowed within lambda
        };
        // value = 6 // modifying local value outside lambda is allowed
        c1.accept(2);
    }
}
