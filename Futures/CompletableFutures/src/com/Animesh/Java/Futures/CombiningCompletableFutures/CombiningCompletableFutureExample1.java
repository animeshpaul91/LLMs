package com.Animesh.Java.Futures.CombiningCompletableFutures;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.function.Supplier;


public class CombiningCompletableFutureExample1 {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Supplier<Integer> orderTotalFetcher = () -> 1000;
        Supplier<Double> discountFetcher = () -> 0.1;
        BiConsumer<Integer, Double> totalPrinter = (total, discount) -> System.out.println(total - (discount * total));

        CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(orderTotalFetcher, executor);
        CompletableFuture<Double> cf2 = CompletableFuture.supplyAsync(discountFetcher, executor);

        cf1.thenAcceptBoth(cf2, totalPrinter);

        // As inline lambda
        // cf1.thenAcceptBoth(cf2, (total, discount) -> System.out.println(total - discount * total));

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