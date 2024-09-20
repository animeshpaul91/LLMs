package com.Animesh.Java.Futures.CompletableFutures;

import java.util.Random;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Supplier;

//Timer
//Change number of threads

//OrderFetcher
//PaymentAcceptor
//InventoryChecker

public class AsyncExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Supplier<Integer> orderFetcher = () -> {
            sleep(200);
            System.out.println("Current thead:" + Thread.currentThread().getName());
            return new Random().nextInt(6);
        };

        Function<Integer, Boolean> inventoryChecker1 = new Function<Integer, Boolean>() {
            @Override
            public Boolean apply(Integer orderNumber) {
                sleep(200);
                System.out.println("Current thead:" + Thread.currentThread().getName());
                return orderNumber % 2 == 0;
            }
        };

        Function<Integer, Boolean> inventoryChecker2 = orderNumber -> {
            sleep(200);
            return orderNumber % 2 == 0;
        };


        CompletableFuture<Boolean> cf1 = CompletableFuture.supplyAsync(orderFetcher, executor).thenApply(inventoryChecker1);
        // same threads from executor service

//        CompletableFuture<Boolean> cf2 = CompletableFuture.supplyAsync(orderFetcher,executor).thenApplyAsync(inventoryChecker1,executor);
        // different threads from executor service

//        CompletableFuture<Boolean> cf3 = CompletableFuture.supplyAsync(orderFetcher).thenApply(inventoryChecker1);
        // same threads from ForkJoinPool

//        CompletableFuture<Boolean> cf4 = CompletableFuture.supplyAsync(orderFetcher).thenApplyAsync(inventoryChecker1,executor);
        // Different threads from different pools

        sleep(1400);
        executor.shutdown();
    }

    private static void sleep(int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException ignored) {
        }
    }
}