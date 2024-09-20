package com.Animesh.Java.Futures.ExceptionHandling;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

public class Example2Exceptionally {
    public static void main(String[] args) throws Exception {
        // runTasks(2);
        runTasks(0);
    }

    //exceptionally called only during exceptions
    private static void runTasks(int i) {
        System.out.printf("-- input: %s --%n", i);
        Supplier<Integer> divider = () -> {
            System.out.println("running task");
            return 50 / i;
        };

        Function<Throwable, Integer> exceptionChecker = exception -> {
            System.out.println("in exceptionally");
            System.err.println(exception);
            return -1;
        };

        CompletableFuture<Void> completableFuture = CompletableFuture
                .supplyAsync(divider).exceptionally(exceptionChecker)
                .thenApply(input -> input * 3)
                .thenAccept(System.out::println);

        System.out.println("is completedExceptionally: " + completableFuture.isCompletedExceptionally());
    }
}