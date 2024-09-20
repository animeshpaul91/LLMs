package com.Animesh.Java.Futures.ExceptionHandling;

import java.util.concurrent.CompletableFuture;

public class Example4Handle {
    public static void main(String[] args) throws Exception {
        // runTasks(2);
        runTasks(0);
    }

    private static void runTasks(int i) {
        System.out.printf("-- input: %s --%n", i);
        CompletableFuture
                .supplyAsync(() -> 50 / i).handle((input, exception) -> { // takes a BiFunction, so can return a value to the next CF.
                    // handle will be called regardless of whether an exception is raised or not
                    if (exception != null) {
                        System.out.println("exception block");
                        System.err.println(exception);
                        return -1; // can return a value, so provides more flexibility
                    } else {
                        System.out.println("normal execution  block");
                        return input;
                    }
                })
                .thenApply(input -> input * 3)
                .thenAccept(System.out::println);
    }
}