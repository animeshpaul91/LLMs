package com.Animesh.Java.Futures.ExceptionHandling;

import java.util.concurrent.CompletableFuture;

public class Example3Exceptionally {
    public static void main(String[] args) throws Exception {
        //runTasks(2);
        runTasks(0);
    }

    //Propagated till exceptionally is called
    private static void runTasks(int i) {
        System.out.printf("-- input: %s --%n", i);
        CompletableFuture.supplyAsync(() -> 50 / i) // potential exception raised here
                .thenApply(input -> input + 1) // skipped when exception is raised
                .thenApply(input -> input + 3) // skipped when exception is raised
                .thenApply((input) -> { // skipped when exception is raised
                    System.out.println(input);
                    return input;
                })
                .exceptionally(exception -> { // exception swallowed here
                    System.out.println("in exceptionally");
                    System.err.println(exception);
                    return -1;
                })
                .thenApply(input -> input * 3) // executed normally
                .thenAccept(System.out::println); // executed normally
    }
}