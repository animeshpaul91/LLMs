package com.Animesh.Java.Futures.CombiningCompletableFutures;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Supplier;


public class CompletableFutureAllOfExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(1);

        Supplier<Integer> orderFetcher = () -> {
            sleep(200);
            return new Random().nextInt(6);
        };

        Function<Integer, Boolean> inventoryChecker2 = orderNumber -> {
            sleep(200);
            return orderNumber % 2 == 0;
        };

        CompletableFuture<Boolean> cf1 = CompletableFuture.supplyAsync(orderFetcher, executor).thenApply(inventoryChecker2);
        CompletableFuture<Boolean> cf2 = CompletableFuture.supplyAsync(orderFetcher, executor).thenApply(inventoryChecker2);
        CompletableFuture<Boolean> cf3 = CompletableFuture.supplyAsync(orderFetcher, executor).thenApply(inventoryChecker2);


        CompletableFuture<Object> anyOf = CompletableFuture.anyOf(cf1, cf2, cf3); // anyOf CF object cannot be used for chaining
        // anyOf.get(); // blocking call

        CompletableFuture<Void> allOf = CompletableFuture.allOf(cf1, cf2, cf3); // allOf CF object cannot be used for chaining
        allOf.get(); // blocking call

        System.out.println("cf1:" + cf1);
        System.out.println("cf2:" + cf2);
        System.out.println("cf3:" + cf3);

        sleep(1000);
        executor.shutdown();
    }

    private static void sleep(int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException ignored) {
        }
    }
}