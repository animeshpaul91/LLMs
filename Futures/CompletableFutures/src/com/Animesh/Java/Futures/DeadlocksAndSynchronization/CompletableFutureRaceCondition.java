package com.Animesh.Java.Futures.DeadlocksAndSynchronization;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;


public class CompletableFutureRaceCondition {
    private static int count = 0;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Supplier<String> orderFetcher = () -> { // critical section of code. Race condition Arises
            sleep(200);
            count++; // shared variable
            System.out.println(count);
            return "order fetched " + count + " ";
        };

        Function<String, String> orderEnricher = order -> order + "order Enriched " + Thread.currentThread().getName();
        Function<String, String> paymentAcceptor = order -> order + "payment Accepted " + Thread.currentThread().getName();
        Consumer<String> emailSender = System.out::println;

        for (int numberoforders = 1; numberoforders <= 10; numberoforders++) {
            CompletableFuture.supplyAsync(orderFetcher, executor).thenApply(orderEnricher).thenApply(paymentAcceptor).thenAcceptAsync(emailSender, executor);
        }

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