package com.Animesh.Java.Futures.CompletableFutures;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;


public class SupplierExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        //Anonymous Class implementation
        Supplier<Integer> orderFetcherAnonymousClass = new Supplier<Integer>() {
            @Override
            public Integer get() {
                sleep(200);
                return new Random().nextInt(6);
            }
        };

        //Lambda Implementation
        Supplier<Integer> orderFetcherLambda = () -> {
            sleep(200);
            System.out.println("Child Thread ID: " + Thread.currentThread().getId());
            return new Random().nextInt(6);
        };

        System.out.println("Main Thread ID: " + Thread.currentThread().getId());
        //Replace orderFetcherLambda with orderFetcherAnonymousClass to run the Anonymous Class implementation
        CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(orderFetcherLambda, executor);
        // supplyAsync will ensure that the supplier task runs in a separate thread controlled by executor service
        System.out.println(cf1.get()); // blocking call. Will block main thread
        sleep(900);
        executor.shutdown();
    }

    private static void sleep(int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException ignored) {
        }
    }
}