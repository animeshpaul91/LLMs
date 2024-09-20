package com.Animesh.Java.Futures.CombiningCompletableFutures;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;
import java.util.function.Supplier;


public class CombiningCompletableFutureExample2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Supplier<Integer> orderFetcher = () -> 1000;
        Supplier<Double> discountFetcher = () -> 0.1;

        BiFunction<Integer, Double, Double> discountApplier = (total, discount) -> total - discount * total;
        CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(orderFetcher, executor);
        CompletableFuture<Double> cf2 = CompletableFuture.supplyAsync(discountFetcher, executor);

        cf1.thenCombine(cf2, discountApplier).thenAccept(System.out::println);

        // As  inline lambda
        // cf1.thenCombine(cf2, (total, discount) -> total - discount * total).thenAccept(System.out::println);
        sleep();
        executor.shutdown();
    }

    private static void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}