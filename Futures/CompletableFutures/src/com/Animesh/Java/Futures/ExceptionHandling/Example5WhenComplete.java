package com.Animesh.Java.Futures.ExceptionHandling;

import java.util.concurrent.CompletableFuture;

public class Example5WhenComplete {
    public static void main(String[] args) throws Exception {
        // runTasks(2);
        runTasks(0);
    }

    private static void runTasks(int i) {
        System.out.printf("-- input: %s --%n", i);
        CompletableFuture
                .supplyAsync(() -> 50 / i).whenComplete((input, exception) -> { // whenComplete accepts a BiConsumer
                    // whenComplete gets called regardless of whether an exception is thrown or not
                    if (exception != null) {
                        System.out.println("exception block");
                        System.err.println(exception); // this will be the end of the program when an exception is raised
                    } else {
                        System.out.println("normal execution  block");
                        //System.out.println("increasing input by 2");
                    }
                })
                .thenApply(input -> input * 3)
                .thenAccept(System.out::println);
    }
}